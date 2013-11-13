// MODULE FASTQC
FASTQC="/lustre1/tools/bin/fastqc"

@preserve
fastqc_lane_gfu = {

    var paired : true
    var test   : false

    doc title: "GFU: fastqc on fastq.gz files in the current directory",
        desc: """
            This module is for current working directory,
            use fastqc_sample_gfu for multiple samples (see also fastqc_project pipeline).
            Produce a zip files with fastqc quality control.
        """,
        constraints: "...",
        author: "davide.rambaldi@gmail.com"

    def output_name
    def command

    if (paired) {
        output_name = input.prefix.replaceAll(/_R[1-2]_[0-9]*.fastq/,"")
        from("*.fastq.gz") produce("${output_name}_R1_fastqc.zip","${output_name}_R2_fastqc.zip") {
            command = """
                $FASTQC -f fastq --noextract --casava --nogroup -t 4 -o . $inputs
            """
            if (test) {
                println "INPUTS: $inputs OUTPUTS: $outputs"
                println "COMMAND: $command"
                command = "touch $output1 $output2"
            }
        }
    } else {
        output_name = input.prefix.replaceAll(/_[0-9]*.fastq/,"")
        from("*.fastq.gz") produce("${output_name}_fastqc.zip") {
            command = """
                $FASTQC -f fastq --noextract --casava --nogroup -t 4 -o . $inputs
            """
            if (test) {
                println "INPUTS: $inputs OUTPUT: $output"
                println "COMMAND: $command"
                command = "touch $output"
            }
        }
    }
    exec command, "fastqc"
}
