// MODULE SNPSIFT GFU
SNPSIFT = "java -Xmx16g -jar /lustre1/tools/bin/SnpSift.jar"

@intermediate
snpsift_filter_gfu =
{
    var test : false

    // INFO
    doc title: "GFU: SNP SIFT",
        desc: " ... ",
        constraints: " ... ",
        author: "davide.rambaldi@gmail.com"

    filter("dedup") {
        def command = """
            $SNPSIFT -f $input "((exists VQSLOD))" > $output
            if [ -f ${output}.idx ]; then rm ${output}.idx; fi;
        """
        if (test) {
            println "INPUT: $input OUTPUT: $output"
            println "COMMAND: $command"
            command = "touch $output"
        }
        exec command, "snpsift"
    }
}
