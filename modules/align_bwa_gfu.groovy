// MODULE ALIGN BWA GFU
BWA="/usr/local/cluster/bin/bwa"

@intermediate
align_bwa_gfu =
{
    // use -I for base64 Illumina quality
    // use -q for trim quality (Es: -q 30)
    var BWAOPT_ALN : ""
    var bwa_threads: 2
    var test       : false
    var compressed : true

    // INFO
    doc title: "GFU: align DNA reads with bwa",
        desc: "Use bwa aln to align reads on the reference genome. Bwa options: $BWAOPT_ALN",
        constraints: "Work with fastq and fastq.gz single files.",
        author: "davide.rambaldi@gmail.com"

    def input_extension = compressed ? '.fastq.gz' : '.fastq'

    transform(input_extension) to('.sai') {

        def command = """
            echo -e "[align_bwa_gfu]: bwa aln on node $HOSTNAME with input (compressed) $input and output $output.sai" >&2;
            $BWA aln -t $bwa_threads $BWAOPT_ALN $REFERENCE_GENOME $input > $output.sai
        """.stripIndent()

        if (test) {
            println "INPUT:  $input"
            println "OUTPUT: $output"
            println "COMMAND:\n$command"
            command = "touch $output"
        }

        exec command, "bwa"
    }
}
