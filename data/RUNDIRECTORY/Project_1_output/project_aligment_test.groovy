// USAGE: bpipe run project_aligment_test.groovy ../Project_1/Sample_test_*

LSF="/usr/bin/lfs"

@intermediate
sample_dir_gfu = {

    doc title: "Preapare working dir for a sample and set lustre options for directory",
        desc: """
            Lustre options:
                -c -1 : a stripe_count of -1 means to stripe over all available OSTs.
                -i -1 : a start_ost_index of -1 allows the MDS to choose the starting index and it is strongly recommended, as this allows space and load balancing to be done by the MDS as needed.
                -s 2M : Stripsize 2 megabytes
        """,
        constraints: "setstripe and getstripe as non blocking steps (Fails in non lustre fs, but will return always true).",
        author: "davide.rambaldi@gmail.com"

    def outputs = []
    def output_dir = input.dir.replaceFirst(/.*\//,"")
    output.dir = output_dir
    def dataDir = new File(input.dir)
    // make sample dir and copy SampleSheet.csv
    new File(output_dir).mkdir()
    ['cp', "${input.dir}/SampleSheet.csv", "$output_dir"].execute().waitFor()

    dataDir.eachFile { file ->
        if (file.getName().endsWith(".fastq.gz")) {
            def targetLink = "${output_dir}/${file.getName()}"
            //println "FILE ABS PATH: ${file.absolutePath}"
            ['ln', '-s', file.absolutePath, targetLink ].execute().waitFor()
            // store as output
            outputs << file.getName()
        }
    }

    // ADD set stripe output and SampleSheet.csv to output
    outputs << "setstripe.log"

    produce(outputs) {
        exec """
            $LSF setstripe -c -1 -i -1 -s 2M . 1>/dev/null 2>&1 || true;
            $LSF getstripe . 1> ${output_dir}/setstripe.log 2>&1 || true;
        """
    }
}

@intermediate
align_test = {
    output.dir = input.replaceFirst("/.*","")
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

@preserve
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
