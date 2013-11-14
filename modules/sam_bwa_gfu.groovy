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
    var test : false

    doc title: "GFU: sampe/samse bwa: merge paired ends with sampe or single end with samse",
        desc: """
            Generate alignments in the SAM format given paired-end reads (repetitive read pairs will be placed randomly).
            Sort by coordinates and generate a bam file.
        """,
        constraints: "The user should define if files are compressed (compressed: true) and if reads are paired (paired: true)",
        author: "davide.rambaldi@gmail.com"

    def header = '@RG' + "\tID:${EXPERIMENT_NAME}\tPL:${PLATFORM}\tPU:${FCID}\tLB:${EXPERIMENT_NAME}\tSM:${SAMPLEID}\tCN:${CENTER}"
    String input_extension = compressed ? '.fastq.gz' : '.fastq'

    if (paired) {
        def custom_output = input.prefix.replaceFirst(/.*\//,"") + ".bam"
        from ("sai","sai",input_extension, input_extension) produce(custom_output) {
            def command = """
                TMP_SCRATCH=\$(/bin/mktemp -d /dev/shm/${PROJECTNAME}.XXXXXXXXXXXXX);
                TMP_OUTPUT_PREFIX=$TMP_SCRATCH/$output.prefix;
                echo -e "[sam_bwa_gfu]: bwa sampe on node $HOSTNAME with TMP_SCRATCH: $TMP_SCRATCH" >&2;
                echo -e "[sam_bwa_gfu]: header is $header" >&2;
                $BWA sampe $BWAOPT_PE -r \"$header\" $REFERENCE_GENOME $input1 $input2 $input3 $input4 > ${TMP_OUTPUT_PREFIX}.sam;
                $SAMTOOLS view -Su ${TMP_OUTPUT_PREFIX}.sam | $SAMTOOLS sort - ${TMP_OUTPUT_PREFIX};
                mv ${TMP_SCRATCH}/$output.bam $output.bam;
                rm -rf ${TMP_SCRATCH};
            """
            if (test) {
                println "INPUTS: $input1, $input2, $input3, $input4"
                println "OUTPUT: $output"
                command = "touch $output"
            }
            exec command, "bwa_sampe"
        }
    } else {
        transform ("sai",input_extension) to("bam") {
            def command = """
                TMP_SCRATCH=\$(/bin/mktemp -d /dev/shm/${PROJECTNAME}.XXXXXXXXXXXXX);
                TMP_OUTPUT_PREFIX=$TMP_SCRATCH/$output.prefix;
                echo -e "[sam_bwa_gfu]: bwa samse on node $HOSTNAME with TMP_SCRATCH: $TMP_SCRATCH" >&2;
                echo -e "[sam_bwa_gfu]: header is $header" >&2;
                $BWA samse $BWAOPT_SE -r \"$header\" $REFERENCE_GENOME $input1 $input2 > ${TMP_OUTPUT_PREFIX}.sam;
                $SAMTOOLS view -Su ${TMP_OUTPUT_PREFIX}.sam | $SAMTOOLS sort - ${TMP_OUTPUT_PREFIX};
                mv ${TMP_SCRATCH}/$output.bam $output.bam;
                rm -rf ${TMP_SCRATCH};
            """
            if (test) {
                println "INPUTS: $input1, $input2"
                println "OUTPUT: $output"
                command = "touch $output"
            }
            exec command, "bwa_samse"
        }
    }
}
