about title: "Trimmomatic, trim single reads in current directory: IOS GFU XXX."
// INFO_USAGE: bpipe-config pipe trim_reads_single_project /lustre2/raw_data/RUN/Project/Sample_*

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
    sample_dir_gfu +
    "%.fastq.gz" * [trimmomatic_reads_gfu.using(
      sample_dir:true,
      paired:false,
      leading:"LEADING:3",
      trailing:"TRAILING:3",
      slidingdown:"SLIDINGWINDOW:4:15",
      minlen:"MINLEN:15",
      phred:"-phred33",
      pretend:false,
      compression:"gz",
      adapters: "ILLUMINACLIP:/lustre1/genomes/Illumina_Adapters/Adapters.fasta:2:30:10"
    )]
  ]
}
