// MODULE MEM BWA GFU (rev1)

@intermediate
mem_bwa_gfu =
{
    // stage vars
    var BWAOPT_MEM     : ""
    var bwa_threads    : 2
    var pretend        : false
    var paired         : true
    var sample_dir     : false
    var use_shm        : false
    var compression    : ""
    var phred_64       : false

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
                pretend        : $pretend,
                paired         : $paired,
                compression    : $compression,
                sample_dir     : $sample_dir,
                use_shm        : $use_shm,
                phred_64       : $phred_64

            Compression type: gz, fqz
            With sample_dir true, this stage redefine output.dir using input.dir.
            With use_shm this stage writes intermediate file (sam file) in /dev/shm node.
            With phred_64 convert phred 64 with MAQ ill2sanger
        """,
        constraints: """
            Work with fastq, fastq.gz, fqz single and paired files.
            For paired files assume the presence of _R1_ and _R2_ tags.
        """,
        author: "davide.rambaldi@gmail.com"

    String header
    String input_extension = ""

    if (compression == "gz")
    {
        input_extension = '.fastq.gz'
    }
    else if (compression == "fqz")
    {
        input_extension = '.fqz'
    }
    else
    {
        input_extension = '.fastq'
    }

    requires PLATFORM: "Please define the PLATFORM variable"
    requires CENTER: "Please define the CENTER variable"
    requires REFERENCE_GENOME: "Please define a REFERENCE_GENOME"
    requires BWA: "Please define BWA path"
    requires SAMTOOLS: "Please define SAMTOOLS path"
    if (compression == "fqz") {
        requires FQZ_COMP: "Please define FQZ_COMP path"
    }

    if (sample_dir)
    {
        // TAKE SAMPLE DIR FROM BRANCH.SAMPLE
        // Parse input SampleSheet.csv to get SAMPLE INFO
        output.dir = branch.sample
        def samplesheet = new File("${branch.sample}/SampleSheet.csv")
        if (samplesheet.exists())
        {
            // get first line after headers
            def sample = samplesheet.readLines()[1].split(",")
            def experiment_name = sample[0] + "_" + sample[2]
            header = '@RG' + "\tID:${experiment_name}\tPL:${PLATFORM}\tPU:${sample[0]}\tLB:${experiment_name}\tSM:${sample[2]}\tCN:${CENTER}"
        }
        else
        {
            println "Can't find SampleSheet in directory ${branch.sample} ! Aborting ..."
            System.exit(1)
        }
    }
    else
    {
        requires EXPERIMENT_NAME : "Please define the EXPERIMENT_NAME variable"
        requires FCID: "Please define the FCID variable"
        requires SAMPLEID: "Please define the SAMPLEID variable"
        header  = '@RG' + "\tID:${EXPERIMENT_NAME}\tPL:${PLATFORM}\tPU:${FCID}\tLB:${EXPERIMENT_NAME}\tSM:${SAMPLEID}\tCN:${CENTER}"
    }

    if (paired)
    {
        // the regexp replace first _R1_ with _ and then _R1 with empty, replace also original path
        def outputs = [
            ("$input1".replaceAll(/.*\//,"").replaceFirst("_R[12]_","_").replaceFirst("_R[12]","") - input_extension + '.bam')
        ]

        produce(outputs)
        {
            def command = ""
            // DEFINE INPUTS STRINGS
            def r1 = ""
            def r2 = ""

            if (compression == "fqz")
            {
                if (phred_64)
                {
                    r1 = "<( $FQZ_COMP -d $input1 | $MAQ ill2sanger - - )"
                    r2 = "<( $FQZ_COMP -d $input2 | $MAQ ill2sanger - - )"
                }
                else
                {
                    r1 = "<( $FQZ_COMP -d $input1 )"
                    r2 = "<( $FQZ_COMP -d $input2 )"
                }

            }
            else if (compression == "gz")
            {
                if (phred_64)
                {
                    r1 = "<( zcat $input1 | $MAQ ill2sanger - - )"
                    r2 = "<( zcat $input2 | $MAQ ill2sanger - - )"
                }
                else
                {
                    r1 = "$input1"
                    r2 = "$input2"
                }
            }
            else
            {
                if (phred_64)
                {
                    r1 = "<( cat $input1 | $MAQ ill2sanger - - )"
                    r2 = "<( cat $input2 | $MAQ ill2sanger - - )"
                }
                else
                {
                    r1 = "$input1"
                    r2 = "$input2"
                }
            }

            if (use_shm)
            {
                command = """
                    TMP_SCRATCH=\$(/bin/mktemp -d /dev/shm/${PROJECTNAME}.XXXXXXXXXXXXX);
                    ${ sample_dir ? "mkdir -p ${TMP_SCRATCH}/${input.replaceFirst("/.*","")};": ""}
                    TMP_OUTPUT_PREFIX=$TMP_SCRATCH/${output.bam.prefix};
                    $BWA mem -R \"$header\" -M -t $bwa_threads $BWAOPT_MEM $REFERENCE_GENOME $r1 $r2 > ${TMP_OUTPUT_PREFIX}.sam;
                    $SAMTOOLS view -Su ${TMP_OUTPUT_PREFIX}.sam | $SAMTOOLS sort - ${TMP_OUTPUT_PREFIX};
                    mv ${TMP_SCRATCH}/$output.bam $output.bam;
                    rm -rf ${TMP_SCRATCH};
                """
            }
            else
            {
                command = """
                    $BWA mem -R \"$header\" -M -t $bwa_threads $BWAOPT_MEM $REFERENCE_GENOME $r1 $r2 | $SAMTOOLS view -Su - | $SAMTOOLS sort - ${output.prefix}
                """
            }

            if (pretend)
            {
                println """
                    INPUTS: $inputs
                    INPUT EXTENSION: $input_extension
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
            "$input".replaceAll(/.*\//,"") - input_extension + '.bam'
        ]

        produce(outputs)
        {
            def command = ""
            // DEFINE INPUTS STRINGS
            def r1 = ""

            if (compression == "fqz")
            {
                if (phred_64)
                {
                    r1 = "<( $FQZ_COMP -d $input | $MAQ ill2sanger - -)"
                }
                else
                {
                    r1 = "<( $FQZ_COMP -d $input )"
                }
            }
            else if (compression == "gz")
            {
                if (phred_64)
                {
                    r1 = "<( zcat $input | $MAQ ill2sanger - - )"
                }
                else
                {
                    r1 = "$input"
                }
            }
            else
            {
                if (phred_64)
                {
                    r1 = "<( cat $input | $MAQ ill2sanger - - )"
                }
                else
                {
                    r1 = "$input"
                }
            }

            if (use_shm)
            {
                command = """
                    TMP_SCRATCH=\$(/bin/mktemp -d /dev/shm/${PROJECTNAME}.XXXXXXXXXXXXX);
                    TMP_OUTPUT_PREFIX=$TMP_SCRATCH/${output.bam.prefix};
                    $BWA mem -R \"$header\" -M -t $bwa_threads $BWAOPT_MEM $REFERENCE_GENOME $r1 > ${TMP_OUTPUT_PREFIX}.sam;
                    $SAMTOOLS view -Su ${TMP_OUTPUT_PREFIX}.sam | $SAMTOOLS sort - ${TMP_OUTPUT_PREFIX};
                    mv ${TMP_SCRATCH}/$output.bam $output.bam;
                    rm -rf ${TMP_SCRATCH};
                """

            }
            else
            {
                command = """
                    $BWA mem -R \"$header\" -M -t $bwa_threads $BWAOPT_MEM $REFERENCE_GENOME $r1 | $SAMTOOLS view -Su - | $SAMTOOLS sort - ${output.prefix}
                """
            }

            if (pretend)
            {
                println """
                    INPUT:  $input
                    INPUT EXTENSION: $input_extension
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
