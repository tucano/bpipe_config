about title: "FASTQC: quality control of fastq files - IOS XXX"
// INFO_USAGE:  bpipe-config pipe fastqc_project /lustre2/raw_data/RUN/Project/Sample_*

// Usage line will be used to infer the correct bpipe command
// USAGE: bpipe run -r $pipeline_filename input.json

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

Bpipe.run {
    branches * [
        sample_dir_gfu + make_report_dir_gfu + fastqc_sample_gfu.using(paired:true)
    ] + "*" * [project_report_fastqc_gfu]
}
