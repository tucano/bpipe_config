
sample_dir_gfu = {
    def output_dir = input.replaceFirst(/.*\//,"")
    exec "mkdir -p $output_dir"
    produce("${input}/*.fastq.gz") {
        println "Input Dir: $input Custom output dir: $output_dir"
    }
}

@intermediate
align_test = {
    output.dir = (input =~ /(.*)\/(.*)\/(.*)/)[0][2]
    // fix output name removin _R1
    def outputs = [input.prefix.replaceFirst(/_R1/,"") - '.fastq' + '.bam']
    produce(outputs) {
        println "Branch: $branch Inputs: $inputs Output dir $output_dir. Output file $output"
        exec """
            echo "Inputs: $inputs" > $output
        """
    }
}

@intermediate
merge_test = {
    output.dir = input.replaceFirst(/\/.*/,"")
    // remove casava group
    def outputs = [input.prefix.replaceFirst(/_[0-9]+$/,"") + '.merge.bam']
    produce(outputs) {
        println "merge $inputs in $output"
        exec """
            echo "Inputs: $inputs" > $output
        """
    }
}

mark_test = {
    output.dir = input.replaceFirst(/\/.*/,"")
    filter("dedup") {
        exec """
            echo "Input $input" > $output
        """
    }
}

Bpipe.run {
    "%" * [
        sample_dir_gfu +
        "_R*_%.fastq.gz" * [ align_test ] +
        "*.bam" * [merge_test] +
        mark_test
    ]
}
