about title: "RNA-seq quality control with rseqc: IOS GFU 009."

// Usage line will be used to infer the correct bpipe command
// USAGE: bpipe run -r $pipeline_filename *.bam

REFERENCE_GENOME = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/SOAPsplice/BPIPE_REFERENCE_GENOME.index"
REFERENCE_FAIDX  = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/fa/BPIPE_REFERENCE_GENOME.fa.fai"
PLATFORM         = "illumina"
CENTER           = "CTGB"
ENVIRONMENT_FILE = "gfu_environment.sh"
// Used by rseqc QA
BED12_ANNOTATION = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/BPIPE_REFERENCE_GENOME.ensGene.bed"

//--BPIPE_SAMPLE_INFO_HERE--

/*
 * PIPELINE NOTES:
 */
Bpipe.run {
    set_stripe_gfu + "%" * [rseqc_bam_stat_gfu , rseqc_gene_coverage_gfu , samtools_idxstats_gfu ,
        rseqc_reads_distribution_gfu , rseqc_read_GC_gfu ,
        rseqc_read_quality_gfu , rseqc_read_NVC_gfu]
}
