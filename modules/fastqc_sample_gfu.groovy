// MODULE FASTQC SAMPLE
FASTQC="/lustre1/tools/bin/fastqc"

@intermediate
fastqc_sample_gfu =
{
    var paired : true

    doc title: "Fastqc on fastq.gz files in a sample directory",
        desc: """
            This module is for Illumina project structure,
            use fastqc_lane_gfu for single sample
        """,
        constraints: "...",
        author: "davide.rambaldi@gmail.com"


    output.dir = input
    def data_dir = input.replaceAll("_report","")
    def input_files = []
    def dir = new File(data_dir)
    dir.eachFile { file ->
        if (file.name.endsWith('fastq.gz')) {
            input_files << file.name
        }
    }

    def output_prefix
    if (paired) {
        if (input_files.size <= 2) {
            output_prefix = input_files*.replaceAll(".fastq.gz","")
        } else {
            output_prefix = input_files*.replaceAll(/_[0-9]*\.fastq\.gz/,"").unique()
        }
    } else {
        if (input_files.size == 1) {
            output_prefix = input_files*.replaceAll(".fastq.gz","").unique()
        } else {
            output_prefix = input_files*.replaceAll(/_[0-9]*\.fastq\.gz/,"").unique()
        }
    }

    println "INPUT DATA IN DIR: $data_dir INPUT REPORT DIR: $input INPUT FILES: $input_files OUTPUT PREFIX: ${output_prefix}"
    if (paired) {
        produce("${output_prefix[0]}_fastqc.zip","${output_prefix[1]}_fastqc.zip") {
            println "OUTPUTS $outputs"
        }
    } else {
        produce("${output_prefix}_fastqc.zip") {
            println "OUTPUT $output"
        }
    }

    exec "$FASTQC -f fastq --noextract --casava --nogroup -t 4 -o $input ${data_dir}/*.fastq.gz;"
    forward input
}
