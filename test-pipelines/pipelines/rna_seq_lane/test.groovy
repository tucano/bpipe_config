about title: "RNA-seq complete pipeline for lane: IOS GFU 009 + IOS GFU 007."

// Usage line will be used to infer the correct bpipe command
// USAGE: bpipe run -r $pipeline_filename *.fastq.gz annotation.gtf

// PROJECT VARS will be added by bpipe-config
// I don't wanna templates for a groovy file. Use simple regexp with PLACEHOLDERS
// Don't change my keywords in source pipeline file!

REFERENCE_GENOME = "/lustre1/genomes/hg19/SOAPsplice/hg19.index"
REFERENCE_FAIDX  = "/lustre1/genomes/hg19/fa/hg19.fa.fai"
PLATFORM         = "illumina"
CENTER           = "CTGB"
ENVIRONMENT_FILE = "gfu_environment.sh"

// Used by rseqc QA
BED12_ANNOTATION = "/lustre1/genomes/hg19/annotation/hg19.ensGene.bed"

PROJECTNAME="PI_1A_name"
REFERENCE="hg19"
EXPERIMENT_NAME="D2A8DACXX_B1"
FCID="D2A8DACXX"
LANE="3"
SAMPLEID="B1"

/*
 * PIPELINE NOTES:
 * Default soapsplice options: SSPLICEOPT_ALN : "-f 2 -q 1 -j 0"
 * Override sopasplice options with: align_soapsplice_gfu.using(paired: true, SSPLICEOPT_ALN : "<options>")
 */
Bpipe.run {
    set_stripe_gfu.using(test:true) + "%.fastq.gz" * [soapsplice_prepare_headers_gfu.using(test:true)] +
    "_R*_%.fastq.gz" * [align_soapsplice_gfu.using(paired: true, test:true)] +
    merge_bam_gfu.using(rename: true, test:true) + merge_junc_gfu.using(test:true) + verify_bam_gfu.using(test:true) +
    mark_duplicates_gfu.using(test:true) + bam_flagstat_gfu.using(test:true) +
    sort_bam_by_name_gfu.using(test:true) + htseq_count_gfu.using(
        stranded: "no",
        mode: "union",
        id_attribute: "gene_name",
        feature_type: "exon", test:true) +
    sort_and_convert_sam_gfu.using(test:true) + verify_bam_gfu.using(test:true) + samtools_index_gfu.using(test:true) +
    "%.bam" * [rseqc_bam_stat_gfu.using(test:true), rseqc_gene_coverage_gfu.using(test:true), samtools_idxstats_gfu.using(test:true),
    rseqc_reads_distribution_gfu.using(test:true), rseqc_read_GC_gfu.using(test:true), rseqc_read_quality_gfu.using(test:true), rseqc_read_NVC_gfu.using(test:true)]
}
