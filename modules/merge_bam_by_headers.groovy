// MODULE MERGE BAM (rev1)

merge_bam_by_headers =
{
  var pretend    : false

  def rules = new JsonSlurper().parseText(new File(input.json).text)
  println "INPUT BAMS: ${inputs.bam}"
  // println "INPUT RULES: ${rules}"
  def commands = []
  def outputs = []

  rules.each { sample, files ->
      println "SAMPLE: $sample"
      println "FILES: $files"
      def input_strings = files.collect() { return "I=" + it }.join(" ")
      outputs << "${sample}.bam"

      commands << """
          java -jar $PICMERGE $input_strings
          O=${sample}.bam
          VALIDATION_STRINGENCY=SILENT
          CREATE_INDEX=true
          MSD=true
          ASSUME_SORTED=true
          USE_THREADING=true
      """
  }

  produce(outputs)
  {
      if (pretend)
      {
          println """
              INPUTS: $inputs
              OUTPUTS: $outputs
              COMMANDS: $commands
          """
          exec "touch ${outputs.join(" ")}"
      }
      else
      {
          multiExec commands
      }
  }
}
