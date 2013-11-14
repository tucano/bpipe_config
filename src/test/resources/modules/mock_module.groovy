// MODULE ALIGN BWA GFU
BWA="/lustre1/tools/bin/bwa"
SAMTOOLS="/usr/local/cluster/bin/samtools"

@intermediate
mocked_module =
{
    // use -I for base64 Illumina quality
    // use -q for trim quality (Es: -q 30)
    var BWAOPT_ALN : ""
    var bwa_threads: 2
    var test       : false
    var paired     : true

    // INFO
    doc title: "This is a title",
        desc: """
            This is a
            multi
            line description
        """,
        constraints: "This are constraints",
        author: "pippo@example.com"

    // CODE FROM HERE
    transform("PIPPO") {
        exec "echo PIPPO"
    }
}
