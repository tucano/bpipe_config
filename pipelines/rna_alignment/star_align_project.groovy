about title: "RNA project alignment with star: IOS CTGB XXX."
// INFO_USAGE:  bpipe-config pipe rnaseq_align_project /lustre2/raw_data/RUN/Project/Sample_*

// Usage line will be used to infer the correct bpipe command
// USAGE: bpipe run -r $pipeline_filename input.json

REFERENCE_GENOME = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/STAR"
PLATFORM         = "illumina"
CENTER           = "CTGB"
ENVIRONMENT_FILE = "gfu_environment.sh"

//--BPIPE_ENVIRONMENT_HERE--

/*
 * PIPELINE NOTES:
 */

 // USE JSON INPUT FILE
import groovy.json.JsonSlurper
branches = new JsonSlurper().parseText(new File(args[0]).text)

Bpipe.run
{
    branches * [
        sample_dir_gfu +
        "L%_R*_%.fastq.gz" * [align_star_gfu.using(
          paired:true,
          sample_dir:true,
          compression:"gz") + convert_sam_to_bam_gfu.using(sample_dir:true)] +
        "*.bam" * [merge_bam_gfu.using(merge_mode:"samplesheet",sample_dir:true)] +
        verify_bam_gfu.using(sample_dir:true) +
        bam_flagstat_gfu.using(sample_dir:true)
    ] +
    "*.bam" * [flagstat_merge_gfu.using(result_dir: "BAM")] +
    "%.bam" * [move_sample_output_gfu.using(result_dir:"BAM")]
}
