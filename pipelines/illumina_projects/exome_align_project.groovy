about title: "exome project alignment with bwa: IOS GFU 009"

// Usage line will be used to infer the correct bpipe command
// USAGE: bpipe run -r $pipeline_filename input.json

REFERENCE_GENOME       = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/bwa/BPIPE_REFERENCE_GENOME"
REFERENCE_GENOME_FASTA = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/fa/BPIPE_REFERENCE_GENOME.fa"
BAITS                  = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/exomes_targets/nexterarapidcapture_expandedexome_probes.interval_list"
TARGETS                = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/exomes_targets/nexterarapidcapture_expandedexome_targetedregions.interval_list"
TARGETS_EXPANDED       = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/exomes_targets/nexterarapidcapture_expandedexome_targetedregions_slope200.interval_list"
PLATFORM               = "illumina"
CENTER                 = "CTGB"
ENVIRONMENT_FILE       = "gfu_environment.sh"

//--BPIPE_ENVIRONMENT_HERE--


// USE JSON INPUT FILE
import groovy.json.JsonSlurper
branches = new JsonSlurper().parseText(new File(args[0]).text)

/*
 * PIPELINE NOTES:
 * We provide an alternatives to MarkDuplicates ro remove duplicates:
 * If you see this error with MarkDuplicates:
 * Exception in thread "main" net.sf.picard.PicardException: Value was put into PairInfoMap more than once.
 * you can switch to rmdup (samtools)
 * remove/comment the mark_duplicates_gfu stage and uncomment the rmdup_gfu stage to use it
 * Accepted values for compression: gz, fqz
 */
Bpipe.run {
    branches * [
        sample_dir_gfu +
        "L%_R*_%." * [mem_bwa_gfu.using(
          pretend:false,
          paired:true,
          bwa_threads:2,
          sample_dir:true,
          use_shm:false,
          compression:"gz",
          phred_64: false
        )] + "*.bam" * [merge_bam_gfu.using(rename:true,sample_dir:true)] + verify_bam_gfu.using(sample_dir:true) + mark_duplicates_gfu.using(sample_dir:true,remove_duplicates:false) +
        // rmdup_gfu.using(paired:true,sample_dir:true) +
        bam_flagstat_gfu.using(sample_dir:true)
    ] +
    "*.bam" * [flagstat_merge_gfu.using(result_dir: "BAM")] +
    "%.bam" * [move_sample_output_gfu.using(result_dir:"BAM") + calculate_hsmetrics_gfu.using(output_dir:"HsMetrics") ] +
    "*.hsmetrics" * [make_report_hsmetrics_gfu.using(output_dir:"doc")] + "all_samples.hsmetrics" * [make_html_hsmetrics_gfu.using(output_dir:"doc")]
}
