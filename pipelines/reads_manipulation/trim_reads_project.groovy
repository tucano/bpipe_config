about title: "Trimmomatic, trim reads in current directory: IOS GFU XXX."

// Usage line will be used to infer the correct bpipe command
// USAGE: bpipe run -r $pipeline_filename input.json

PLATFORM         = "illumina"
CENTER           = "CTGB"
ENVIRONMENT_FILE = "gfu_environment.sh"

//--BPIPE_ENVIRONMENT_HERE--

/*
 * PIPELINE NOTES:
 */
Bpipe.run {
  "%" * [
    sample_dir_gfu +
    "%_L%_R*_%.fastq.gz" * [trimmomatic_reads_gfu.using(
      sample_dir:true,
      paired:true,
      leading:"LEADING:3",
      trailing:"TRAILING:3",
      slidingdown:"SLIDINGWINDOW:4:15",
      minlen:"MINLEN:15",
      phred:"-phred33",
      pretend:false,
      compression:"gz"
    )]
  ]
}
