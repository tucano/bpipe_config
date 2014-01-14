// MODULE MERGE BAM FILES
PICMERGE="/usr/local/cluster/bin/MergeSamFiles.jar"

@preserve
merge_bam_gfu =
{
    var rename  : false
    var pretend : false

    doc title: "Merge bam files with $PICMERGE",
        desc: """
            Reads a SAM or BAM file and combines the output to one file
            If file came from split_fastq_gfu (es: read_0001.bam) you should set rename: true.
            The output will be renamed with the variable SAMPLEID (${SAMPLEID}).
            For Illumina default runs set rename to FALSE, this stage will automatically remove casava groups notation.
            stage options:
                rename  : $rename
                pretend : $pretend
        """,
        constraints: "",
        author: "davide.rambaldi@gmail.com"

    def output_prefix
    if (rename)
    {
        output_prefix = SAMPLEID
    }
    else
    {
        // remove CASAVA groups
        output_prefix = input.prefix.replaceFirst(/_[0-9]+$/,"")
    }

    def outputs = [
        ("${output_prefix}" + ".merge.bam"),
        ("${output_prefix}" + ".merge.bai")
    ]

    // collect input bams
    def input_strings = inputs.collect() { return "I=" + it}.join(" ")


    produce (outputs)
    {
        def command = """
            java -jar $PICMERGE
                $input_strings
                O=$output1
                VALIDATION_STRINGENCY=SILENT
                CREATE_INDEX=true
                MSD=true
                ASSUME_SORTED=true
                USE_THREADING=true
        """

        if (pretend)
        {
            println """
                INPUTS: $inputs
                OUTPUT: $output
                COMMAND: $command
            """
            command = """
                echo "INPUTS: $inputs" > $output1;
                echo "INPUTS: $inputs" > $output2;
            """
        }

        exec command,"merge_bam_files"
    }
}
