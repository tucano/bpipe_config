
// Create a data structure (Map) that maps branches from json file
import groovy.json.JsonSlurper
// parse is for groovy 2.2.0 then I must use parseText
branches = new JsonSlurper().parseText(new File(args[0]).text)

prepare =
{
  // keep the sample name as a branch variable
  branch.sample = branch.name

  // Output dir is the sample name
  output.dir = branch.sample

  produce("SampleSheet.csv")
  {
    println """
      SAMPLE: ${branch.sample}
      NAME:   ${branch.name}
      INPUTS: $inputs
      SAMPLESHEET: $input.csv
      OUTPUT: $output
    """
    exec """
      mkdir -p ${branch.sample};
      cp $input.csv $output;
    """
  }
}

align =
{
  // manually define output: stripping directories and Read Group
  def input_extension = '.fastq.gz'
  def outputs = ["$input1".replaceAll(/.*\//,"").replaceFirst("_R[12]_","_").replaceFirst("_R[12]","") - input_extension + '.bam']
  output.dir = branch.sample

  produce(outputs)
  {
    println """
      SAMPLE: ${branch.sample}
      NAME:   ${branch.name}
      INPUTS: $inputs
      OUTPUT: $output
    """
    exec "touch $output"
  }
}

merge =
{
  output.dir = branch.sample
  def outputs = ["${branch.sample}.merge.bam"]
  produce(outputs)
  {
    println """
      SAMPLE: ${branch.sample}
      NAME:   ${branch.name}
      INPUTS: $inputs
      OUTPUT: $output
    """
    exec "touch $output"
  }
}

run {
  branches * [
    prepare + "L%_R*_%." * [align] + "*.bam" * [merge]
  ]
}
