// MODULE UNZIP FASTQC GFU

@intermediate
unzip_fastqc_gfu =
{
    var pretend : false

    doc title: "Simply decompress a fastqc report dir",
        desc: "...",
        constraints: "...",
        author: "davide.rambaldi@gmail.com"

    def fastqc_dir = new File("$input").getParent()
    output.dir = fastqc_dir
    def outputs = []
    def command = new StringBuffer()
    inputs.each { izip ->
        command << "unzip -o $izip -d $fastqc_dir;"
        outputs << "${izip.prefix}"
    }

    produce(outputs) {
        if (pretend) {
            exec "mkdir -p ${outputs.join(' ')}"
        } else {
            exec "$command"
        }
    }
    forward input
}
