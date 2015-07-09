// MODULE ALIGN STAR

@intermediate
align_star_gfu =
{
  var compression       : "gz"
  var paired            : true
  var pretend           : false
  var star_threads      : 2
  var outSAMstrandField : "intronMotif"
  var outSAMunmapped    : "Within"
  var sample_dir        : false

  doc title: "Align RNA reads with star",
        desc: """
            Align with soapsplice.
            Main options with value:
                pretend      : $pretend,
                paired       : $paired,
                compression  : $compression,
                star_threads : $star_threads,
                sample_dir   : $sample_dir
        """,
        constraints: """
          Work with fastq and fastq.gz, single and paired files.
          For paired files assume the presence of _R1_ and _R2_ tags.
        """,
        author: "davide.rambaldi@gmail.com"

  requires REFERENCE_GENOME: "Please define a REFERENCE_GENOME"
  requires STAR: "Please define STAR path"

  String input_extension    = ""
  String compression_string = ""

  if (compression == "gz")
  {
    input_extension = 'fastq.gz'
    compression_string = "--readFilesCommand zcat"
  }
  else if (compression == "fqz")
  {
    input_extension = 'fqz'
    compression_string = "--readFilesCommand $FQZ_COMP -d"
  }
  else
  {
    input_extension = 'fastq'
  }

  if (sample_dir)
  {
    output.dir = branch.sample
  }

  def command

  if (paired)
  {
    def custom_output_prefix = "$input1".replaceAll(/.*\//,"").replaceFirst("_R[12]_","_") - input_extension
    // ADD output.dir is sample_dir
    if (sample_dir)
    {
      custom_output_prefix = output.dir + "/" + custom_output_prefix
    }

    def outputs = ["Aligned.out.sam","Log.final.out","Log.out","Log.progress.out"].collect() { custom_output_prefix + it }

    from(input_extension, input_extension) produce(outputs)
    {
      command = """
        $STAR --genomeDir $REFERENCE_GENOME
              --readFilesIn $input1 $input2
              --runThreadN $star_threads
              --outSAMstrandField $outSAMstrandField
              --outFileNamePrefix $custom_output_prefix
              --outSAMunmapped $outSAMunmapped
              $compression_string;
      """

      if (pretend)
      {
          println """
              INPUT FASTQ:  $input1 $input2
              OUTPUT:       $output
              COMMAND:
                  $command
          """
          command = """
              echo "INPUTS: $input" > $output1;
              echo "INPUTS: $input" > $output2;
              echo "INPUTS: $input" > $output3;
              echo "INPUTS: $input" > $output4;
          """
      }
      exec "$command", "star"
    }
  }
  else
  {
    def custom_output_prefix = "$input".replaceAll(/.*\//,"").replaceFirst("_R[12]_","_") - input_extension

    if (sample_dir)
    {
      custom_output_prefix = output.dir + "/" + custom_output_prefix
    }

    def outputs = ["Aligned.out.sam","Log.final.out","Log.out","Log.progress.out"].collect() { custom_output_prefix + it }

    from(input_extension, input_extension) produce(outputs)
    {
      command = """
        $STAR --genomeDir $REFERENCE_GENOME
              --readFilesIn $input
              --runThreadN $star_threads
              --outSAMstrandField $outSAMstrandField
              --outFileNamePrefix $custom_output_prefix
              --outSAMunmapped $outSAMunmapped
              $compression_string;
      """

      if (pretend)
      {
          println """
              INPUT FASTQ:  $input
              OUTPUT:       $output1 $output2 $output3 $output4
              COMMAND:
                  $command
          """
          command = """
              echo "INPUTS: $input" > $output1;
              echo "INPUTS: $input" > $output2;
              echo "INPUTS: $input" > $output3;
              echo "INPUTS: $input" > $output4;
          """
      }
      exec "$command", "star"
    }
  }
}
