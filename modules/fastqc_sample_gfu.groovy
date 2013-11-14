// MODULE FASTQC
FASTQC="/lustre1/tools/bin/fastqc"

@intermediate
fastqc_sample_gfu = {
    var test   : false
    var paired : true

    doc title: "GFU: fastqc on fastq.gz files in a sample directory",
        desc: """
            This module is for Illumina project structure,
            use fastqc_lane_gfu for single sample
        """,
        constraints: """
            If there is only one pair of fastq.gz files (001) fastq don't remove the casava notation.
            I produce the fastqc zip file in sample dir and move it with rename to sample name.
        """,
        author: "davide.rambaldi@gmail.com"

    def command

    if (paired) {
        produce("${input}_R1_fastqc.zip","${input}_R2_fastqc.zip") {
            command = """
                $FASTQC -f fastq --noextract --casava --nogroup -t 4 -o $input ${input}/*.fastq.gz;
                mv ${input}/*R1*_fastqc.zip ${input}_R1_fastqc.zip;
                mv ${input}/*R2*_fastqc.zip ${input}_R2_fastqc.zip;
            """
            if (test) {
                println "INPUTS: $inputs OUTPUTS: $outputs"
                println "COMMAND: $command"
                command = "touch $output1 $output2"
            }
        }
    } else {
        produce("${input}_fastqc.zip") {
            command = """
                $FASTQC -f fastq --noextract --casava --nogroup -t 4 -o $input ${input}/*.fastq.gz;
                mv ${input}/*_fastqc.zip ${input}_fastqc.zip
            """
            if (test) {
                println "INPUTS: $inputs OUTPUTS: $output"
                println "COMMAND: $command"
                command = "touch $output"
            }
        }
    }
    exec command, "fastqc"
}
