// MODULE convert_sam_to_bam_gfu

convert_sam_to_bam_gfu =
{
  var pretend : false

  doc title: "Convert SAM to BAM changing headers with $ADD_OR_REPLACE_READ_GROUPS",
      desc: """
          Align with soapsplice.
          Main options with value:
              pretend      : $pretend,
              paired       : $paired,
              compression  : $compression,
              star_threads : $star_threads
      """,
      constraints: """
        Work with fastq and fastq.gz, single and paired files.
        For paired files assume the presence of _R1_ and _R2_ tags.
      """,
      author: "davide.rambaldi@gmail.com"

  requires PLATFORM: "Please define the PLATFORM variable"
  requires CENTER: "Please define the CENTER variable"
  requires ADD_OR_REPLACE_READ_GROUPS: "Please dfine AddOrReplaceReadGroups path"

  // THIS USE A SAMPLESHEET!
  def samplesheet = new File("SampleSheet.csv")
  def header
  def sample
  def experiment_name
  def command

  if (samplesheet.exists())
  {
    sample = samplesheet.readLines()[1].split(",")
    experiment_name = sample[0] + "_" + sample[2]
    header = '@RG' + "\tID:${experiment_name}\tPL:${PLATFORM}\tPU:${sample[0]}\tLB:${experiment_name}\tSM:${sample[2]}\tCN:${CENTER}"
  }
  else
  {
    fail "Can't find SampleSheet!"
  }

  transform ('Aligned.out.sam') to ("bam")
  {
    command = """
      $ADD_OR_REPLACE_READ_GROUPS I=$input.sam O=$output.bam ID=${experiment_name} LB=${experiment_name} PL=${PLATFORM} CN=${CENTER} SM=${sample[2]} PU=${sample[0]} VALIDATION_STRINGENCY=SILENT SO=coordinate CREATE_INDEX=true
    """

    if (pretend)
    {
      println """
          INPUT FASTQ:  $input
          OUTPUT:       $output
          COMMAND:
              $command
      """

      command = """
        echo "INPUTS: $input" > $output;
      """
    }
    exec "$command", "merge_bam_files"
  }
}
