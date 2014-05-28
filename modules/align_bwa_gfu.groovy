// MODULE ALIGN BWA GFU

@intermediate
align_bwa_gfu =
{
    // use -I for base64 Illumina quality
    // use -q for trim quality (Es: -q 30)
    var BWAOPT_ALN  : ""
    var BWAOPT_PE   : ""
    var BWAOPT_SE   : ""
    var bwa_threads : 2
    var pretend     : false
    var paired      : true
    var compressed  : true

    // INFO
    doc title: "Align DNA reads with bwa",
        desc: """
            Use bwa aln to align reads on the reference genome.

            Main options with value:
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
            For paired files assume the presence of _R1_ and _R2_ tags.
            No support for project piplines (we use mem_bwa_gfu).
        """,
        author: "davide.rambaldi@gmail.com"


    String header = '@RG' + "\tID:${EXPERIMENT_NAME}\tPL:${PLATFORM}\tPU:${FCID}\tLB:${EXPERIMENT_NAME}\tSM:${SAMPLEID}\tCN:${CENTER}"

    if (paired)
    {
        transform("sai","sai","bam")
        {
            if (pretend)
            {
                println """
                    INPUTS:  $inputs
                    OUTPUTS: $output1 $output2 $output3
                    HEADER:  $header
                """
                exec "touch $output1 $output2 $output3"
            }
            else
            {
                if (compressed)
                {
                    multi "gunzip -c $input1.gz | $BWA aln -t $bwa_threads $BWAOPT_ALN $REFERENCE_GENOME - > $output1",
                          "gunzip -c $input2.gz | $BWA aln -t $bwa_threads $BWAOPT_ALN $REFERENCE_GENOME - > $output2"
                    exec  "$BWA sampe $BWAOPT_PE -r \"$header\" $REFERENCE_GENOME  $output1 $output2 $input1.gz $input2.gz |  $SAMTOOLS view -Su - | $SAMTOOLS sort - $output.bam.prefix", "bwa_sampe"
                }
                else
                {
                    multi "$BWA aln -t $bwa_threads $BWAOPT_ALN $REFERENCE_GENOME $input1 > $output1",
                          "$BWA aln -t $bwa_threads $BWAOPT_ALN $REFERENCE_GENOME $input2 > $output2"
                    exec  "$BWA sampe $BWAOPT_PE -r \"$header\" $REFERENCE_GENOME  $output1 $output2 $input1 $input2 |  $SAMTOOLS view -Su - | $SAMTOOLS sort - $output.bam.prefix", "bwa_sampe"
                }
            }
        }
    }
    else
    {
        transform("sai","bam")
        {
            if (pretend)
            {
                println """
                    INPUT:  $input
                    OUTPUTS: $output1 $output2
                    HEADER:  $header
                """
                exec "touch $output1 $output2"
            }
            else
            {
                if (compressed)
                {
                    exec """
                        gunzip -c $input.gz | $BWA aln -t $bwa_threads $BWAOPT_ALN $REFERENCE_GENOME - > $output1;
                        $BWA samse $BWAOPT_SE -r \"$header\" $REFERENCE_GENOME $output1 $input1 > $output2
                    """
                }
                else
                {
                    exec """
                        $BWA aln -t $bwa_threads $BWAOPT_ALN $REFERENCE_GENOME $input.fastq > $output1;
                        $BWA sampe $BWAOPT_PE -r \"$header\" $REFERENCE_GENOME $output1 $input > $output2
                    """
                }
            }
        }
    }
}
