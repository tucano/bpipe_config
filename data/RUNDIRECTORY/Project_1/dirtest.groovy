
@preserve
dir_test = {
    produce("${input}/*.fastq.gz") {
        println "DIR: $input"
        //println "INPUTS: $input  OUTPUTS: $outputs"
    }
}

@intermediate
sample_test = {
    def output_name = input.prefix.replaceAll(/_R[1-2]_[0-9]*.fastq/,"")
    def sample = input.prefix.replaceAll(/\/.*/,"")
    produce("${sample}_R1_fastqc.zip","${sample}_R2_fastqc.zip") {
        println "SAMPLE INPUTS: $inputs INPUT PREFIX: ${input.prefix} SAMPLE: $sample"
        println "FASTQC OUTPUTS: $output1 $output2"
        exec "touch $output1 $output2"
    }
}

// MODULE FASTQC
FASTQC="/lustre1/tools/bin/fastqc"
@preserve
fastqc_sample_gfu = {
    var test   : false

    produce("${input}_R1_fastqc.zip","${input}_R2_fastqc.zip") {
        command = """
            $FASTQC -f fastq --noextract --casava --nogroup -t 4 -o . ${input}/*.fastq.gz
        """
        if (test) {
            println "INPUTS: $inputs OUTPUTS: $outputs"
            println "COMMAND: $command"
            command = "touch $output1 $output2"
        }
        exec command, "fastqc"
    }
}

Bpipe.run {
    "%" * [ fastqc_sample_gfu.using(test:true) ]
}
