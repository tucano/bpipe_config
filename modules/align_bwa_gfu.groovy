// MODULE ALIGN BWA GFU
BWA="/lustre1/tools/bin/bwa"
SAMTOOLS="/usr/local/cluster/bin/samtools"

@intermediate
align_bwa_gfu =
{
    // use -I for base64 Illumina quality
    // use -q for trim quality (Es: -q 30)
    var BWAOPT_ALN  : ""
    var bwa_threads : 2
    var pretend     : false
    var paired      : true
    var compressed  : true

    // INFO
    doc title: "Align DNA reads with bwa",
        desc: """
            Use bwa aln to align reads on the reference genome.

            Main options with default value:
                pretend    : $pretend
                paired     : $paired
                compressed : $compressed

            bwa options: ${BWAOPT_ALN}.
            bwa threads: ${bwa_threads}.

            First step: bwa aln on single or paired files.
            Second step: bwa samse/sampe.
            Generate alignments in the SAM format (repetitive read pairs will be placed randomly).
            Then sort by coordinates and generate a bam file.
        """,
        constraints: """
            Work with fastq and fastq.gz, single and paired files.
            For paired files assume the presence of _R1_ and _R2_ tags
        """,
        author: "davide.rambaldi@gmail.com"


    String header = '@RG' + "\tID:${EXPERIMENT_NAME}\tPL:${PLATFORM}\tPU:${FCID}\tLB:${EXPERIMENT_NAME}\tSM:${SAMPLEID}\tCN:${CENTER}"
    String input_extension = compressed ? '.fastq.gz' : '.fastq'

    // config for multi stages 
    // see https://groups.google.com/forum/#!searchin/bpipe-discuss/multi$20config/bpipe-discuss/6jq6GiHz7oE/uBG32j4VU1oJ
    
    if (paired)
    {
        def outputs = [
            ("$input1" - input_extension + '.sai'),
            ("$input2" - input_extension + '.sai'),
            ("$input1".replaceFirst("_R1_","_") - input_extension + '.bam')
        ]

        produce(outputs)
        {
            def command_alnone = "$BWA aln -t $bwa_threads $BWAOPT_ALN $REFERENCE_GENOME $input1 > $output1"
            def command_alntwo = "$BWA aln -t $bwa_threads $BWAOPT_ALN $REFERENCE_GENOME $input2 > $output2"
            def command_sampe = """
                TMP_SCRATCH=\$(/bin/mktemp -d /dev/shm/${PROJECTNAME}.XXXXXXXXXXXXX);
                TMP_OUTPUT_PREFIX=$TMP_SCRATCH/${output.bam.prefix};
                echo -e "[sam_bwa_gfu]: bwa sampe on node $HOSTNAME with TMP_SCRATCH: $TMP_SCRATCH" >&2;
                echo -e "[sam_bwa_gfu]: header is $header" >&2;
                $BWA sampe $BWAOPT_PE -r \"$header\" $REFERENCE_GENOME $output1 $output2 $input1 $input2 > ${TMP_OUTPUT_PREFIX}.sam;
                $SAMTOOLS view -Su ${TMP_OUTPUT_PREFIX}.sam | $SAMTOOLS sort - ${TMP_OUTPUT_PREFIX};
                mv ${TMP_SCRATCH}/$output.bam $output.bam;
                rm -rf ${TMP_SCRATCH};
            """

            if (pretend) 
            {
                println """
                    HEADER:  $header
                    INPUTS:  $inputs
                    OUTPUTS: $outputs
                    COMMANDS ALN: 
                        $command_alnone
                        $command_alntwo
                    COMAND SAMPE:
                        $command_sampe
                """

                command_alnone = "echo INPUT: $input1 > $output1"
                command_alntwo = "echo INPUT: $input2 > $output2"
                command_sampe  = """
                    echo "INPUTS: $inputs $output1 $output2" > $output3
                """
            }

            config("bwa_aln") 
            {
                multi "$command_alnone", "$command_alntwo"
            }
            exec command_sampe, "bwa_sampe"
        }
    } 
    else 
    {
        def outputs = [
            ("$input1" - input_extension + '.sai'),
            ("$input1" - input_extension + '.bam')
        ]

        produce(outputs) 
        {
            def command = """
                $BWA aln -t $bwa_threads $BWAOPT_ALN $REFERENCE_GENOME $input > $output1;
                TMP_SCRATCH=\$(/bin/mktemp -d /dev/shm/${PROJECTNAME}.XXXXXXXXXXXXX);
                TMP_OUTPUT_PREFIX=$TMP_SCRATCH/${output.bam.prefix};
                echo -e "[sam_bwa_gfu]: bwa samse on node $HOSTNAME with TMP_SCRATCH: $TMP_SCRATCH" >&2;
                echo -e "[sam_bwa_gfu]: header is $header" >&2;
                $BWA samse $BWAOPT_SE -r \"$header\" $REFERENCE_GENOME $output1 $input1 > ${TMP_OUTPUT_PREFIX}.sam;
                $SAMTOOLS view -Su ${TMP_OUTPUT_PREFIX}.sam | $SAMTOOLS sort - ${TMP_OUTPUT_PREFIX};
                mv ${TMP_SCRATCH}/$output.bam $output.bam;
                rm -rf ${TMP_SCRATCH};
            """

            if (pretend)
            {
                println """
                    HEADER:  $header
                    INPUTS:  $input
                    OUTPUTS: $outputs
                    COMMAND: 
                        $command
                """
                command = """
                    echo "INPUT: $input" > $output1
                    echo "INPUTS: $input $output1" > $output2
                """
            }
            exec command, "bwa_samse"
        }
    }
}
