// MODULE TRIMMOMATIC READS

@intermediate
trimmomatic_reads_gfu =
{
  var adapters    : "ILLUMINACLIP:/lustre1/genomes/Illumina_Adapters/Adapters.fasta:2:30:10"
  var leading     : "LEADING:3"
  var trailing    : "TRAILING:3"
  var slidingdown : "SLIDINGWINDOW:4:15"
  var minlen      : "MINLEN:15"
  var phred       : "-phred33"
  var paired      : true
  var pretend     : false
  var compression : "gz"
  var sample_dir  : false

  doc title: "Trimmomatic: A flexible read trimming tool for Illumina NGS data",
      desc: """
          Trimmomatic performs a variety of useful trimming tasks for illumina paired-end and single ended data.The selection of trimming steps and their associated parameters are supplied on the command line.
          The current trimming steps are:

          ILLUMINACLIP: Cut adapter and other illumina-specific sequences from the read.
          SLIDINGWINDOW: Perform a sliding window trimming, cutting once the average quality within the window falls below a threshold.
          LEADING: Cut bases off the start of a read, if below a threshold quality
          TRAILING: Cut bases off the end of a read, if below a threshold quality
          CROP: Cut the read to a specified length
          HEADCROP: Cut the specified number of bases from the start of the read
          MINLEN: Drop the read if it is below a specified length
          TOPHRED33: Convert quality scores to Phred-33
          TOPHRED64: Convert quality scores to Phred-64

          Main options with value:
          pretend     : $pretend
          adapters    : $adapters
          leading     : $leading
          trailing    : $trailing
          slidingdown : $slidingdown
          minlen      : $minlen
          phred       : $phred
          paired      : $paired
          compression : $compression
          sample_dir  : $sample_dir
      """,
      constraints: "",
      author: "davide.rambaldi@gmail.com"

  requires TRIMMOMATIC: "Please define TRIMMOMATIC path"
  if (compression == "fqz")
  {
    requires FQZ_COMP: "Please define FQZ_COMP path"
  }

  if (sample_dir)
  {
    output.dir = branch.sample
  }

  String input_extension = ""
  if (compression == "gz")
  {
      input_extension = '.fastq.gz'
  }
  else if (compression == "fqz")
  {
      input_extension = '.fqz'
  }
  else
  {
      input_extension = '.fastq'
  }

  def conf_string = "$adapters $leading $trailing $slidingdown $minlen"
  def mode = paired ? "PE" : "SE"

  def outputs
  def command

  if (paired)
  {
    if (compression == "gz")
    {
      outputs = [
        ("$input1".replaceAll(/.*\//,"") - input_extension + '_paired.fastq.gz'),
        ("$input1".replaceAll(/.*\//,"") - input_extension + '_unpaired.fastq.gz'),
        ("$input2".replaceAll(/.*\//,"") - input_extension + '_paired.fastq.gz'),
        ("$input2".replaceAll(/.*\//,"") - input_extension + '_unpaired.fastq.gz')
      ]
    }
    else
    {
      outputs = [
        ("$input1".replaceAll(/.*\//,"") - input_extension + '_paired.fastq'),
        ("$input1".replaceAll(/.*\//,"") - input_extension + '_unpaired.fastq'),
        ("$input2".replaceAll(/.*\//,"") - input_extension + '_paired.fastq'),
        ("$input2".replaceAll(/.*\//,"") - input_extension + '_unpaired.fastq')
      ]
    }

    produce (outputs)
    {
      def r1 = ""
      def r2 = ""

      if (compression == "fqz")
      {
        r1 = "<( $FQZ_COMP -d $input1 )"
        r2 = "<( $FQZ_COMP -d $input2 )"
      }
      else
      {
        r1 = "$input1"
        r2 = "$input2"
      }

      command = """
        $TRIMMOMATIC $mode $phred -trimlog trimming.log $r1 $r2 $output1 $output2 $output3 $output4 $conf_string
      """

      if (pretend)
      {
        println """
          INPUTS: $inputs
          COMMAND: $command
          OUTPUTS: $outputs
        """
        command = sample_dir ? "touch ${(outputs.collect { branch.sample + '/' + it}).join(" ")}" : "touch ${outputs.join(" ")}"
      }

      exec "$command"
    }
  }
  else
  {
    if (compression == "gz")
    {
      outputs = [
        ("$input".replaceAll(/.*\//,"") - input_extension + '_paired.fastq.gz'),
        ("$input".replaceAll(/.*\//,"") - input_extension + '_unpaired.fastq.gz')
      ]
    }
    else
    {
      outputs = [
        ("$input".replaceAll(/.*\//,"") - input_extension + '_paired.fastq'),
        ("$input".replaceAll(/.*\//,"") - input_extension + '_unpaired.fastq')
      ]
    }

    produce (outputs)
    {
      def r1 = ""

      if (compression == "fqz")
      {
        r1 = "<( $FQZ_COMP -d $input )"
      }
      else
      {
        r2 = "$input"
      }

      command = """
        $TRIMMOMATIC $mode $phred -trimlog trimming.log $r1 $output1 $output2 $conf_string
      """

      if (pretend)
      {
        println """
          INPUTS: $input
          COMMAND: $command
          OUTPUTS: $outputs
        """
        command = sample_dir ? "touch ${(outputs.collect { branch.sample + '/' + it}).join(" ")}" : "touch ${outputs.join(" ")}"
      }

      exec "$command"
    }
  }
}
