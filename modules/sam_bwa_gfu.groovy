// MODULE SAMPE/SAMSE BWA GFU
BWA="/lustre1/tools/bin/bwa"
SAMTOOLS="/usr/local/cluster/bin/samtools"

@intermediate
sam_bwa_gfu =
{
    var BWAOPT_SE : ""
    var BWAOPT_PE : ""
    var paired : true
    var compressed : true
    var pretend : false

    doc title: "Sampe/Samse bwa: merge paired ends with sampe or single end with samse",
        desc: """
            Generate alignments in the SAM format given paired-end reads (repetitive read pairs will be placed randomly).
            Sort by coordinates and generate a bam file.

            Main options with value:
                pretend    : $pretend
                paired     : $paired
                compressed : $compressed
            BWA options:
                SAMSE      : $BWAOPT_SE
                SAMPE      : $BWAOPT_PE
        """,
        constraints: "The user should define if files are compressed (compressed: true) and if reads are paired (paired: true). No support for project pipelines.",
        author: "davide.rambaldi@gmail.com"


    def header = '@RG' + "\tID:${EXPERIMENT_NAME}\tPL:${PLATFORM}\tPU:${FCID}\tLB:${EXPERIMENT_NAME}\tSM:${SAMPLEID}\tCN:${CENTER}"
    String input_extension = compressed ? '.fastq.gz' : '.fastq'

    if (paired)
    {
        def custom_output = input.prefix.replaceFirst("_R1_","_") + ".bam"
        from ("sai","sai",input_extension, input_extension) produce(custom_output)
        {
            def command = """
                TMP_SCRATCH=\$(/bin/mktemp -d /dev/shm/${PROJECTNAME}.XXXXXXXXXXXXX);
                TMP_OUTPUT_PREFIX=$TMP_SCRATCH/$output.prefix;
                $BWA sampe $BWAOPT_PE -r \"$header\" $REFERENCE_GENOME $input1 $input2 $input3 $input4 > ${TMP_OUTPUT_PREFIX}.sam;
                $SAMTOOLS view -Su ${TMP_OUTPUT_PREFIX}.sam | $SAMTOOLS sort - ${TMP_OUTPUT_PREFIX};
                mv ${TMP_SCRATCH}/$output.bam $output.bam;
                rm -rf ${TMP_SCRATCH};
            """

            if (pretend)
            {
                println """
                    INPUTS:  $input1, $input2, $input3, $input4
                    OUTPUT:  $output
                    COMMAND: $command
                """

                command = """
                    echo "INPUTS: $inputs" > $output
                """
            }
            exec command, "bwa_sampe"
        }
    }
    else
    {
        transform ("sai",input_extension) to("bam")
        {
            def command = """
                TMP_SCRATCH=\$(/bin/mktemp -d /dev/shm/${PROJECTNAME}.XXXXXXXXXXXXX);
                TMP_OUTPUT_PREFIX=$TMP_SCRATCH/$output.prefix;
                $BWA samse $BWAOPT_SE -r \"$header\" $REFERENCE_GENOME $input1 $input2 > ${TMP_OUTPUT_PREFIX}.sam;
                $SAMTOOLS view -Su ${TMP_OUTPUT_PREFIX}.sam | $SAMTOOLS sort - ${TMP_OUTPUT_PREFIX};
                mv ${TMP_SCRATCH}/$output.bam $output.bam;
                rm -rf ${TMP_SCRATCH};
            """

            if (pretend)
            {
                println """
                    INPUTS:  $input1, $input2
                    OUTPUT:  $output
                    COMMAND: $command
                """

                command = """
                    echo "INPUTS: $inputs" > $output
                """
            }
            exec command, "bwa_samse"
        }
    }
}
