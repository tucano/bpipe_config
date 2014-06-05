about title: "RNA-seq reads count with htseq-count: IOS GFU 007."

// Usage line will be used to infer the correct bpipe command
// USAGE: bpipe run -R softwareschedule $pipeline_filename *.merge.bam

REFERENCE_GENOME    = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/SOAPsplice/BPIPE_REFERENCE_GENOME.index"
REFERENCE_FAIDX     = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/fa/BPIPE_REFERENCE_GENOME.fa.fai"
PLATFORM            = "illumina"
CENTER              = "CTGB"
ENVIRONMENT_FILE    = "gfu_environment.sh"
ANNOTATION_GFF_FILE = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/BPIPE_REFERENCE_GENOME.ensGene_withGeneName.gtf"

//--BPIPE_SAMPLE_INFO_HERE--


/*
 * PIPELINE NOTES:
 * Check the options for htseq_count!
 */
Bpipe.run {
    "%.bam" * [
        sort_bam_by_name_gfu + htseq_count_gfu.using(
            stranded: "no",
            mode: "union",
            id_attribute: "gene_name",
            feature_type: "exon") +
        sort_and_convert_sam_gfu + verify_bam_gfu + samtools_index_gfu
    ]
}
