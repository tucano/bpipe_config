about title: "Dummy"

// Usage line will be used to infer the correct bpipe command
// USAGE: bpipe run -r $pipeline_filename *.fastq.gz

PLATFORM         = "illumina"
CENTER           = "CTGB"
ENVIRONMENT_FILE = "gfu_environment.sh"

//--BPIPE_ENVIRONMENT_HERE--

/*
 * This is the local version of the fastqc pipeline,
 * is used to add a fastqc quality control on your project data
 */
 Bpipe.run {
    set_stripe_gfu + "%.fastq.gz" * [soapsplice_prepare_headers_gfu] +
    "_R*_%.fastq.gz" * [align_soapsplice_gfu.using(paired: true)] +
    merge_bam_gfu.using(rename: false) + merge_junc_gfu + verify_bam_gfu + bam_flagstat_gfu +

    // an alternative to mark_duplicates_gfu is rmdup: comment this line and uncomment the rmdup_gfu stage to use it
    mark_duplicates_gfu +
    // rmdup_gfu +

    bam_flagstat_gfu + sort_bam_by_name_gfu + htseq_count_gfu.using(
        stranded: "no",
        mode: "union",
        id_attribute: "gene_name",
        feature_type: "exon") +
    sort_and_convert_sam_gfu + verify_bam_gfu + samtools_index_gfu +
    "%.bam" * [rseqc_bam_stat_gfu, rseqc_gene_coverage_gfu, samtools_idxstats_gfu,
    rseqc_reads_distribution_gfu, rseqc_read_GC_gfu, rseqc_read_quality_gfu, rseqc_read_NVC_gfu]
 }
