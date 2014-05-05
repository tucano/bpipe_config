about title: "exome project alignment with bwa: IOS GFU 009"

// Usage line will be used to infer the correct bpipe command
// USAGE: bpipe run -r $pipeline_filename <INPUT_DIRS>

REFERENCE_GENOME       = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/bwa/BPIPE_REFERENCE_GENOME"
REFERENCE_GENOME_FASTA = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/fa/BPIPE_REFERENCE_GENOME.fa"
BAITS                  = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/exomes_targets/nexterarapidcapture_expandedexome_probes.interval_list"
TARGETS                = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/exomes_targets/nexterarapidcapture_expandedexome_targetedregions.interval_list"
TARGETS_EXPANDED       = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/exomes_targets/nexterarapidcapture_expandedexome_targetedregions_slope200.interval_list"
PLATFORM               = "illumina"
CENTER                 = "CTGB"
ENVIRONMENT_FILE       = "gfu_environment.sh"

//--BPIPE_ENVIRONMENT_HERE--


/*
 * PIPELINE NOTES:
 * We provide an alternatives to MarkDuplicates ro remove duplicates:
 * If you see this error with MarkDuplicates:
 * Exception in thread "main" net.sf.picard.PicardException: Value was put into PairInfoMap more than once.
 * you can switch to rmdup (samtools)
 * remove/comment the mark_duplicates_gfu stage and uncomment the rmdup_gfu stage to use it
 */
Bpipe.run {
    "%" * [
        sample_dir_gfu +
        "L%_R*_%.fastq.gz" * [mem_bwa_gfu.using(sample_dir:true,paired:true,BWAOPT_MEM:"")] +
        "*.bam" * [merge_bam_gfu.using(rename:false,sample_dir:true)] + verify_bam_gfu.using(sample_dir:true) + bam_flagstat_gfu.using(sample_dir:true) +
        mark_duplicates_gfu.using(sample_dir:true,remove_duplicates:false) +
        // rmdup_gfu.using(paired:true,sample_dir:true) +
        bam_flagstat_gfu.using(sample_dir:true)
    ] + "%.bam" * [move_sample_output_gfu.using(result_dir:"BAM") + calculate_hsmetrics_gfu.using(output_dir:"HsMetrics") ] +
    "*.hsmetrics" * [make_report_hsmetrics_gfu.using(output_dir:"doc")] + "%.tsv" * [make_html_hsmetrics_gfu]
}
