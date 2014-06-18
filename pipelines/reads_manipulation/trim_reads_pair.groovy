about title: "Trimmomatic, trim reads in current directory: IOS GFU XXX."

// Usage line will be used to infer the correct bpipe command
// USAGE: bpipe run -r $pipeline_filename *.fastq.gz

PLATFORM         = "illumina"
CENTER           = "CTGB"
ENVIRONMENT_FILE = "gfu_environment.sh"

//--BPIPE_SAMPLE_INFO_HERE--

/*
 * PIPELINE NOTES:
 * This single stage pipeline trim reads in current directory
 */
Bpipe.run {
  set_stripe_gfu + "L%_R*_%.fastq.gz" * [trimmomatic_reads_gfu.using(
    sample_dir:false,
    paired:true,
    leading:"LEADING:3",
    trailing:"TRAILING:3",
    slidingdown:"SLIDINGWINDOW:4:15",
    minlen:"MINLEN:15",
    phred:"-phred33",
    pretend:false,
    compression:"gz"
  )]
}
