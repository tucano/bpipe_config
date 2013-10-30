// MODULE ALIGN BWA GFU
BWA="/usr/local/cluster/bin/bwa"
SAMTOOLS="/usr/local/cluster/bin/samtools"

@intermediate
align_bwa_gfu =
{
    // use -I for base64 Illumina quality
    // use -q for trim quality (Es: -q 30)
    var BWAOPT_ALN : ""
    var bwa_threads: 2
    var test       : false
    var paired     : true

    // INFO
    doc title: "GFU: align DNA reads with bwa",
        desc: """
            Use bwa aln to align reads on the reference genome. Bwa options: $BWAOPT_ALN
            Generate alignments in the SAM format given paired-end reads (repetitive read pairs will be placed randomly).
            Sort by coordinates and generate a bam file.
        """,
        constraints: "Work with fastq and fastq.gz single files.",
        author: "davide.rambaldi@gmail.com"

    String header = '@RG' + "\tID:${EXPERIMENT_NAME}\tPL:${PLATFORM}\tPU:${FCID}\tLB:${EXPERIMENT_NAME}\tSM:${SAMPLEID}\tCN:${CENTER}"

    if (paired) {
         // We are going to transform FASTQ into two .sai files and a .bam file
        transform("sai","sai","bam") {

            // Step 1 - run both bwa aln commands in parallel
            command_one = "$BWA aln -t $bwa_threads $BWAOPT_ALN $REFERENCE_GENOME $input1 > $output1"
            command_two = "$BWA aln -t $bwa_threads $BWAOPT_ALN $REFERENCE_GENOME $input2 > $output2"

            if (test) {
                println "INPUTS:  $input1 and $input2"
                println "OUTPUTS: $output1 and $output2"
                println "COMMAND:\n$command_one\n$command_two"
                command_one = "touch $output1"
                command_two = "touch $output2"
            }
            multi command_one, command_two

            // Step 2 - bwa sampe
            def command = """
                TMP_SCRATCH=\$(/bin/mktemp -d /dev/shm/${PROJECTNAME}.XXXXXXXXXXXXX);
                TMP_OUTPUT_PREFIX=$TMP_SCRATCH/${output.bam.prefix};
                echo -e "[sam_bwa_gfu]: bwa sampe on node $HOSTNAME with TMP_SCRATCH: $TMP_SCRATCH" >&2;
                echo -e "[sam_bwa_gfu]: header is $header" >&2;
                $BWA sampe $BWAOPT_PE -r \"$header\" $REFERENCE_GENOME $output1 $output2 $input1 $input2 > ${TMP_OUTPUT_PREFIX}.sam;
                $SAMTOOLS view -Su ${TMP_OUTPUT_PREFIX}.sam | $SAMTOOLS sort - ${TMP_OUTPUT_PREFIX};
                mv ${TMP_SCRATCH}/$output.bam $output.bam;
                rm -rf ${TMP_SCRATCH};
            """
            if (test) {
                println "INPUTS: $input1, $input2"
                println "OUTPUT: $output1, $output2, $output.bam"
                println "COMMAND: $command"
                command = "touch $output.bam"
            }
            exec command, "bwa_sampe"
        }
    } else {
        transform("sai","bam") {
            // Step 1 - run bwa aln command
            def command = """
                $BWA aln -t $bwa_threads $BWAOPT_ALN $REFERENCE_GENOME $input > $output1
                TMP_SCRATCH=\$(/bin/mktemp -d /dev/shm/${PROJECTNAME}.XXXXXXXXXXXXX);
                TMP_OUTPUT_PREFIX=$TMP_SCRATCH/$output.prefix;
                echo -e "[sam_bwa_gfu]: bwa samse on node $HOSTNAME with TMP_SCRATCH: $TMP_SCRATCH" >&2;
                echo -e "[sam_bwa_gfu]: header is $header" >&2;
                $BWA samse $BWAOPT_SE -r \"$header\" $REFERENCE_GENOME $output1 $input1 > ${TMP_OUTPUT_PREFIX}.sam;
                $SAMTOOLS view -Su ${TMP_OUTPUT_PREFIX}.sam | $SAMTOOLS sort - ${TMP_OUTPUT_PREFIX};
                mv ${TMP_SCRATCH}/$output.bam $output.bam;
                rm -rf ${TMP_SCRATCH};
            """
            if (test) {
                println "INPUTS: $input1"
                println "OUTPUTS: $output1, $output.bam"
                println "COMMAND:\n$command"
                command = "touch $output.bam $output.sai"
            }
            exec command, "bwa_samse"
        }
    }
}
