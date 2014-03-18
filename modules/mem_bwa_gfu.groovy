// MODULE MEM BWA GFU

@intermediate
mem_bwa_gfu =
{
    // stage vars
    var BWAOPT_MEM  : ""
    var bwa_threads : 2
    var pretend     : false
    var paired      : true
    var compressed  : true
    var sample_dir  : false

    // INFO
    doc title: "Align DNA reads with bwa using mem",
        desc: """
            Use bwa aln to align reads (fastq.gz) on the reference genome.
            Align 70bp-1Mbp query sequences with the BWA-MEM algorithm. Briefly, the algorithm works by seeding
            alignments with maximal exact matches (MEMs) and then extending seeds with the affine-gap Smith-Waterman
            algorithm (SW).
            Sort by coordinates and generate a bam file.

            Bwa options: $BWAOPT_MEM
            bwa threads: $bwa_threads

            Main options with value:
                pretend    : $pretend
                paired     : $paired
                compressed : $compressed
                sample_dir : $sample_dir

            With sample_dir true, this stage redefine output.dir using input.dir
        """,
        constraints: """
            Work with fastq and fastq.gz, single and paired files.
            For paired files assume the presence of _R1_ and _R2_ tags
        """,
        author: "davide.rambaldi@gmail.com"

    String header
    String input_extension = compressed ? '.fastq.gz' : '.fastq'

    if (sample_dir)
    {
        // Parse input SampleSheet.csv to get SAMPLE INFO
        // If there are no problems with SampleSheet.csv: should be a SampeSheet.csv with one line
        def mdir = input.replaceFirst("/.*","")
        output.dir = mdir
        def samplesheet = new File("${mdir}/SampleSheet.csv")
        if (samplesheet.exists())
        {
            // get first line after headers
            def sample = samplesheet.readLines()[1].split(",")
            def experiment_name = sample[0] + "_" + sample[2]
            header = '@RG' + "\tID:${experiment_name}\tPL:${PLATFORM}\tPU:${sample[0]}\tLB:${experiment_name}\tSM:${sample[2]}\tCN:${CENTER}"
        }
        else
        {
            println "Can't find SampleSheet in directory ${mdir} ! Aborting ..."
            System.exit(1)
        }
    }
    else
    {
        header  = '@RG' + "\tID:${EXPERIMENT_NAME}\tPL:${PLATFORM}\tPU:${FCID}\tLB:${EXPERIMENT_NAME}\tSM:${SAMPLEID}\tCN:${CENTER}"
    }

    if (paired)
    {
        def outputs = [
            ("$input1".replaceFirst("_R[12]_","_") - input_extension + '.bam')
        ]

        produce(outputs)
        {
            def command = """
                TMP_SCRATCH=\$(/bin/mktemp -d /dev/shm/${PROJECTNAME}.XXXXXXXXXXXXX);
                ${ sample_dir ? "mkdir -p ${TMP_SCRATCH}/${input.replaceFirst("/.*","")};": ""}
                TMP_OUTPUT_PREFIX=$TMP_SCRATCH/${output.bam.prefix};
                $BWA mem -R \"$header\" -M -t $bwa_threads $BWAOPT_MEM $REFERENCE_GENOME $input1 $input2 > ${TMP_OUTPUT_PREFIX}.sam;
                $SAMTOOLS view -Su ${TMP_OUTPUT_PREFIX}.sam | $SAMTOOLS sort - ${TMP_OUTPUT_PREFIX};
                mv ${TMP_SCRATCH}/$output.bam $output.bam;
                rm -rf ${TMP_SCRATCH};
            """

            if (pretend)
            {
                println """
                    INPUTS: $inputs
                    OUTPUT: $output
                    COMMAND: $command
                """
                command = """
                    echo "INPUTS $inputs" > $output
                """
            }
            exec command, "bwa_mem"
        }
    }
    else
    {
        def outputs = [
            "$input" - input_extension + '.bam'
        ]

        produce(outputs)
        {
            def command = """
                TMP_SCRATCH=\$(/bin/mktemp -d /dev/shm/${PROJECTNAME}.XXXXXXXXXXXXX);
                TMP_OUTPUT_PREFIX=$TMP_SCRATCH/${output.bam.prefix};
                $BWA mem -R \"$header\" -M -t $bwa_threads $BWAOPT_MEM $REFERENCE_GENOME $input > ${TMP_OUTPUT_PREFIX}.sam;
                $SAMTOOLS view -Su ${TMP_OUTPUT_PREFIX}.sam | $SAMTOOLS sort - ${TMP_OUTPUT_PREFIX};
                mv ${TMP_SCRATCH}/$output.bam $output.bam;
                rm -rf ${TMP_SCRATCH};
            """

            if (pretend)
            {
                println """
                    INPUT:  $input
                    OUTPUT: $output
                    COMMAND: $command
                """
                command = """
                    echo "INPUT $input" > $output
                """
            }
            exec command, "bwa_mem"
        }
    }
}
