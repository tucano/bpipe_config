// MODULE MEM BWA GFU
BWA="/usr/local/cluster/bin/bwa"

@intermediate
mem_bwa_gfu =
{
    // use -I for base64 Illumina quality
    // use -q for trim quality (Es: -q 30)
    var BWAOPT_ALN  : ""
    var bwa_threads : 2
    var test        : false
    var paired      : true

    // INFO
    doc title: "GFU: align DNA reads with bwa",
        desc: """
            Use bwa aln to align reads on the reference genome. Bwa options: $BWAOPT_ALN
            Generate alignments in the SAM format given paired-end reads (repetitive read pairs will be placed randomly).
            Sort by coordinates and generate a bam file.
        """,
        constraints: "Work with fastq and fastq.gz single files.",
        author: "davide.rambaldi@gmail.com"

    if (paired) {

    } else {

    }
}
