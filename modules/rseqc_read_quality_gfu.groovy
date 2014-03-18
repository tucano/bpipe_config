// MODULE GENE READ QUALITY RSEQC

@preserve
rseqc_read_quality_gfu =
{
    var pretend : false
    doc title: "Rseqc quality control of bam files: read_quality",
        desc: """
            According to SAM specification, if Q is the character to
            represent “base calling quality” in SAM file, then
            Phred Quality Score = ord(Q) - 33.
            Here ord() is python function that returns an integer
            representing the Unicode code point of the character
            when the argument is a unicode object,
            for example, ord(‘a’) returns 97.
            Phred quality score is widely used to measure “reliability” of base-calling,
            for example, phred quality score of 20 means there is 1/100 chance
            that the base-calling is wrong,
            phred quality score of 30 means there is
            1/1000 chance that the base-calling is wrong.
            In general:
            Phred quality score = -10xlog(10)P
            , here P is probability that base-calling is wrong.
        """,
        constrains: """
            I am forcing export of site-packages to get qcmodule.
            In addition there are some errors in the R script. We must update rseqc?
        """,
        author: "davide.rambaldi@gmail.com"

    transform("qual.boxplot.pdf","qual.r")
    {
        def command = """
            $READQUALITY -i $input.bam -o $input.prefix
        """

        if (pretend)
        {
             println """
                INPUT:   $input
                OUTPUTS: $output1 $output2 $output3
                COMMAND: $command
            """

            command = """
                echo "INPUT: $input" > $output1;
                echo "INPUT: $input" > $output2;
                echo "INPUT: $input" > $output3;
            """
        }
        exec command
    }
    forward input.bam
}
