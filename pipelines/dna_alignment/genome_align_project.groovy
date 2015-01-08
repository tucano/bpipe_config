about title: "genome project alignment with bwa: IOS GFU 009"
// INFO_USAGE:  bpipe-config pipe genome_align_project /lustre2/raw_data/RUN/Project/Sample_*

// Usage line will be used to infer the correct bpipe command
// USAGE: bpipe run -r $pipeline_filename input.json

REFERENCE_GENOME = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/bwa/BPIPE_REFERENCE_GENOME"
PLATFORM         = "illumina"
CENTER           = "CTGB"
ENVIRONMENT_FILE = "gfu_environment.sh"

//--BPIPE_ENVIRONMENT_HERE--


/*
 * PIPELINE NOTES:
 * We provide an alternatives to MarkDuplicates ro remove duplicates:
 * If you see this error with MarkDuplicates:
 * Exception in thread "main" net.sf.picard.PicardException: Value was put into PairInfoMap more than once.
 * you can switch to rmdup (samtools)
 * remove/comment the mark_duplicates_gfu stage and uncomment the rmdup_gfu stage to use it
 * Accepted values for compression: gz, fqz
 */

// USE JSON INPUT FILE
import groovy.json.JsonSlurper
branches = new JsonSlurper().parseText(new File(args[0]).text)

Bpipe.run {
    branches * [
        sample_dir_gfu +
        "L%_R*_%." *  [mem_bwa_gfu.using(
          pretend:false,
          paired:true,
          bwa_threads:2,
          sample_dir:true,
          use_shm:false,
          compression:"gz",
          phred_64: false
        )] + "*.bam" * [merge_bam_gfu.using(merge_mode:"samplesheet",sample_dir:true)] + verify_bam_gfu.using(sample_dir:true) +
        mark_duplicates_gfu.using(sample_dir:true,remove_duplicates:false) +
        // rmdup_gfu.using(paired:true,sample_dir:true) +
        bam_flagstat_gfu.using(sample_dir:true)
    ] +
    "*.bam" * [flagstat_merge_gfu.using(result_dir: "BAM")] +
    "%.bam" * [move_sample_output_gfu.using(result_dir:"BAM")]
}
