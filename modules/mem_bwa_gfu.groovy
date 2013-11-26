// MODULE MEM BWA GFU
BWA="/lustre1/tools/bin/bwa"
SAMTOOLS="/usr/local/cluster/bin/samtools"

@intermediate
mem_bwa_gfu =
{
    // use -I for base64 Illumina quality
    // use -q for trim quality (Es: -q 30)
    var BWAOPT_MEM  : ""
    var bwa_threads : 2
    var test        : false
    var paired      : true
    var compressed  : true

    // INFO
    doc title: "Align DNA reads with bwa using mem",
        desc: """
            Use bwa aln to align reads (fastq.gz) on the reference genome. Bwa options: $BWAOPT_MEM
            Align 70bp-1Mbp query sequences with the BWA-MEM algorithm. Briefly, the algorithm works by seeding
            alignments with maximal exact matches (MEMs) and then extending seeds with the affine-gap Smith-Waterman
            algorithm (SW).
            Sort by coordinates and generate a bam file.
        """,
        constraints: "Work with fastq and fastq.gz single files.",
        author: "davide.rambaldi@gmail.com"

    String header = '@RG' + "\tID:${EXPERIMENT_NAME}\tPL:${PLATFORM}\tPU:${FCID}\tLB:${EXPERIMENT_NAME}\tSM:${SAMPLEID}\tCN:${CENTER}"
    String input_extension = compressed ? '.fastq.gz' : '.fastq'
    def custom_output = input.replaceFirst(/.*\//,"") - input_extension + ".bam"

    produce(custom_output) {
        def command = """
            TMP_SCRATCH=\$(/bin/mktemp -d /dev/shm/${PROJECTNAME}.XXXXXXXXXXXXX);
            TMP_OUTPUT_PREFIX=$TMP_SCRATCH/${output.bam.prefix};
            echo -e "[mem_bwa_gfu] bwa mem on node $HOSTNAME with TMP_SCRATCH: $TMP_SCRATCH" >&2;
            echo -e "[mem_bwa_gfu]: header is $header" >&2;
        """
        if (paired) {
            command += """
                $BWA mem -R \"$header\" -M -t $bwa_threads $BWAOPT_MEM $REFERENCE_GENOME $input1 $input2 > ${TMP_OUTPUT_PREFIX}.sam;
            """
        } else {
            command += """
                $BWA mem -R \"$header\" -M -t $bwa_threads $BWAOPT_MEM $REFERENCE_GENOME $input > ${TMP_OUTPUT_PREFIX}.sam;
            """
        }
        command += """
            $SAMTOOLS view -Su ${TMP_OUTPUT_PREFIX}.sam | $SAMTOOLS sort - ${TMP_OUTPUT_PREFIX};
            mv ${TMP_SCRATCH}/$output.bam $output.bam;
            rm -rf ${TMP_SCRATCH};
        """

        if (test) {
            println "INPUTS: $inputs OUTPUT: $output"
            println "COMMAND: $command"
            command = "touch $output.bam"
        }

        exec command, "bwa_mem"
    }
}
