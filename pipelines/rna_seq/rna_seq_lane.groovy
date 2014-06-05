about title: "RNA-seq complete pipeline for lane: IOS GFU 009 + IOS GFU 007."

// Usage line will be used to infer the correct bpipe command
// USAGE: bpipe run -R softwareschedule $pipeline_filename *.fastq.gz

REFERENCE_GENOME       = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/SOAPsplice/BPIPE_REFERENCE_GENOME.index"
REFERENCE_FAIDX        = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/fa/BPIPE_REFERENCE_GENOME.fa.fai"
PLATFORM               = "illumina"
CENTER                 = "CTGB"
ENVIRONMENT_FILE       = "gfu_environment.sh"
// Used by rseqc QA
BED12_ANNOTATION       = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/BPIPE_REFERENCE_GENOME.ensGene.bed"
ANNOTATION_GFF_FILE    = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/BPIPE_REFERENCE_GENOME.ensGene_withGeneName.gtf"

//--BPIPE_SAMPLE_INFO_HERE--



/*
 * PIPELINE NOTES:
 * Default soapsplice options: SSPLICEOPT_ALN : "-f 2 -q 1 -j 0"
 * Override soapsplice options with: align_soapsplice_gfu.using(paired: true, SSPLICEOPT_ALN : "<options>")
 *
 * We provide an alternatives to MarkDuplicates ro remove duplicates:
 * If you see this error with MarkDuplicates:
 * Exception in thread "main" net.sf.picard.PicardException: Value was put into PairInfoMap more than once.
 * you can switch to rmdup (samtools)
 *
 */
Bpipe.run {
    set_stripe_gfu + "%.fastq.gz" * [soapsplice_prepare_headers_gfu] +
    "L%_R*_%.fastq.gz" * [align_soapsplice_gfu.using(
        paired: true,
        compression:"gz",
        use_shm: false,
        SSPLICEOPT_ALN:"-p 4 -f 2 -q 1 -j 0"
    )] +
    merge_bam_gfu.using(rename: false) + merge_junc_gfu + verify_bam_gfu +
    bam_flagstat_gfu + sort_bam_by_name_gfu + htseq_count_gfu.using(
        stranded: "no",
        mode: "union",
        id_attribute: "gene_name",
        feature_type: "exon") +
    sort_and_convert_sam_gfu + verify_bam_gfu + samtools_index_gfu +
    "%.bam" * [rseqc_bam_stat_gfu, rseqc_gene_coverage_gfu, samtools_idxstats_gfu,
    rseqc_reads_distribution_gfu, rseqc_read_GC_gfu, rseqc_read_quality_gfu, rseqc_read_NVC_gfu]
}
