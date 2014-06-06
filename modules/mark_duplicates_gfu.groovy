// MODULE MARK DUPLICATES IN BAM FILE (rev1)

@preserve
mark_duplicates_gfu =
{
    var pretend               : false
    var remove_duplicates     : false
    var validation_stringency : "SILENT"
    var sample_dir            : false

    doc title: "Mark duplicates in bam files with $MARKDUP : IOS GFU 0019",
        desc: """
            Mark duplicates in bam files with ${MARKDUP}.
            stage options:
                pretend               : $pretend
                remove_duplicates     : $remove_duplicates
                validation_stringency : $validation_stringency
                sample_dir            : $sample_dir
            With sample_dir true, this stage redefine output.dir using input.dir
        """,
        constrains: """
            Require a BAM sorted by coordinate (ASSUME_SORTED=true).
        """,
        author: "davide.rambaldi@gmail.com"

    def required_binds = ["MARKDUP"]
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

    if (sample_dir) { output.dir = input.replaceFirst("/.*","") }

    def outputs = [
        (input.prefix + ".dedup.bam"),
        (input.prefix + ".dedup.bai"),
        (input.prefix + ".dedup.metrics")
    ]

    produce(outputs)
    {
        def command = """
            ulimit -l unlimited;
            ulimit -s unlimited;
            java -Djava.io.tmpdir=/lustre2/scratch -Xmx32g -jar $MARKDUP
                I=$input.bam
                O=$output.bam
                CREATE_INDEX=true
                VALIDATION_STRINGENCY=$validation_stringency
                REMOVE_DUPLICATES=$remove_duplicates
                ASSUME_SORTED=true
                METRICS_FILE=$output.metrics
        """

        if (pretend)
        {
            println """
                INPUT $input
                OUTPUTS: $outputs
                COMMAND: $command
            """
            command = """
                echo "INPUT: $input" > $output1;
                echo "INPUT: $input" > $output2;
                echo "INPUT: $input" > $output3;
            """
        }
        exec command, "mark_duplicates"
    }
}
