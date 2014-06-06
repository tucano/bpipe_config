// MODULE ALIGN BWA GFU (rev1)

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
    var compression : ""

    // INFO
    doc title: "Align DNA reads with bwa",
        desc: """
            Use bwa aln to align reads on the reference genome.

            Main options with value:
                pretend     : $pretend
                paired      : $paired
                compression : $compression

            Compression type: gz, fqz
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
            Take info about sample from binding variables, die if variables are not defined
        """,
        author: "davide.rambaldi@gmail.com"

    def required_binds = ["EXPERIMENT_NAME","PLATFORM","FCID","SAMPLEID","CENTER","REFERENCE_GENOME","BWA"]
    def to_fail = false
    required_binds.each { key ->
        if (!binding.variables.containsKey(key))
        {
            to_fail = true
            println """
                This stage require this variable: $key, add this to the groovy file:
                    $key = "VALUE"
            """.stripIndent()
        }
    }
    if (to_fail) { System.exit(1) }


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
                if (compression == "gz")
                {
                    multi "gunzip -c $input1.gz | $BWA aln -t $bwa_threads $BWAOPT_ALN $REFERENCE_GENOME - > $output1",
                          "gunzip -c $input2.gz | $BWA aln -t $bwa_threads $BWAOPT_ALN $REFERENCE_GENOME - > $output2"
                    exec  "$BWA sampe $BWAOPT_PE -r \"$header\" $REFERENCE_GENOME  $output1 $output2 $input1.gz $input2.gz |  $SAMTOOLS view -Su - | $SAMTOOLS sort - $output.bam.prefix", "bwa_sampe"
                }
                else if (compression == "fqz")
                {
                    multi "$FQZ_COMP -d $input1.fqz | $BWA aln -t $bwa_threads $BWAOPT_ALN $REFERENCE_GENOME - > $output1",
                          "$FQZ_COMP -d $input2.fqz | $BWA aln -t $bwa_threads $BWAOPT_ALN $REFERENCE_GENOME - > $output2"
                    exec "$BWA sampe $BWAOPT_PE -r \"$header\" $REFERENCE_GENOME  $output1 $output2 <( $FQZ_COMP -d $input1.fqz ) <( $FQZ_COMP -d $input2.fqz ) |  $SAMTOOLS view -Su - | $SAMTOOLS sort - $output.bam.prefix", "bwa_sampe"
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
