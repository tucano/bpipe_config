// MODULE GENE READ QUALITY RSEQC
READQUALITY = """
    export PYTHONPATH=/usr/local/cluster/python/usr/lib64/python2.6/site-packages/:\$PYTHONPATH &&
    /usr/local/cluster/python2.7/bin/python2.7 /usr/local/cluster/python/usr/bin/read_quality.py
""".stripIndent().trim()

@preserve
rseqc_read_quality_gfu = {
    var test : false
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

    transform("qual.boxplot.pdf","qual.r") {
        def command = """
            echo -e "[rseqc_read_quality]: read Quality for file  $input.bam";
            $READQUALITY -i $input.bam -o $input.prefix
        """
        if (test) {
            println "INPUT $input, OUTPUTS: $output1 $output2"
            println "COMMAND: $command"
            command = "touch $output1 $output2"
        }
        exec command
    }
    forward input.bam
}
