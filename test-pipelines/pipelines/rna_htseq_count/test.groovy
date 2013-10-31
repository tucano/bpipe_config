about title: "RNA-seq reads count with htseq-count: IOS GFU 007."

// Usage line will be used to infer the correct bpipe command
// USAGE: bpipe run -r $pipeline_filename input.bam

// PROJECT VARS will be added by bpipe-config
// I don't wanna templates for a groovy file. Use simple regexp with PLACEHOLDERS
// Don't change my keywords in source pipeline file!

REFERENCE_GENOME = "/lustre1/genomes/hg19/SOAPsplice/hg19.index"
REFERENCE_FAIDX  = "/lustre1/genomes/hg19/fa/hg19.fa.fai"
PLATFORM         = "illumina"
CENTER           = "CTGB"
ENVIRONMENT_FILE = "gfu_environment.sh"

PROJECTNAME="test_1_lol"
REFERENCE="hg19"
EXPERIMENT_NAME="D2A8DACXX_B1"
FCID="D2A8DACXX"
LANE="3"
SAMPLEID="B1"

ANNOTATION_GFF_FILE    = "/lustre1/genomes/hg19/annotation/hg19.ensGene_withGeneName.gtf"

/*
 * PIPELINE NOTES:
 * Options for htseq_count:
 * stranded : "no"
 * mode    : "union"
 * id_attribute : "gene_name"
 * feature_type : "exon"
 */
Bpipe.run {
    sort_bam_by_name_gfu.using(test:true) + htseq_count_gfu.using(
        stranded: "no",
        mode: "union",
        id_attribute: "gene_name",
        feature_type: "exon",
        test: true) +
    sort_and_convert_sam_gfu.using(test:true) + verify_bam_gfu.using(test:true) + samtools_index_gfu.using(test:true)
}
