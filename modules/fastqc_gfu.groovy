// MODULE FASTQC
FASTQC="/lustre1/tools/bin/fastqc"

@preserve
fastqc_gfu = {

    var local : true
    var test : false

    doc title: "GFU: fastqc on fastq.gz files",
        desc: """
            Set local: false in order to launch fastqc on files in dir:
            /lustre2/raw_data/RUN_NAME/PROJECT_NAME/SAMPLE recovering the RUN NAME.
            Set lane : true to fastqc on lane with input.prefix parsing.
        """,
        constraints: "...",
        author: "davide.rambaldi@gmail.com"

    transform('.fastq.gz') to('_fastqc.zip')  {
        def command = """
            $FASTQC -f fastq --noextract --casava --nogroup -t 4 -o . $inputs.gz
        """
        if (test) {
            println "INPUT $input, OUTPUT: $ouptut"
            println "COMMAND: $command"
            command = "touch $output"
        }
        exec command, "fastqc"
        forward input
    }
}
