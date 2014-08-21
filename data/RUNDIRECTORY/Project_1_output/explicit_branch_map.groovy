#!/usr/bin/env groovy

/*
 * If you don't get the flexibility you need from the above mechanisms, you can set the branch paths yourself explicitly by specifying a
 * Groovy List or a Map that tells Bpipe what paths you want.
 * When you specify a Map, the keys in the map are interpreted as branch names and the values in the Map are interpreted as files,
 * or lists of files, that are supplied to the branch as input.
 */

import static groovy.json.JsonOutput.*

// for (a in this.args) {
//   println("Argument: " + a)
// }

// def branches = [
//   sample1: ["sample1_1.fastq.gz", "sample1_2.fastq.gz"],
//   sample2: ["sample2_1.fastq.gz", "sample2_2.fastq.gz"],
//   sample3: ["sample3_1.fastq.gz", "sample3_2.fastq.gz"]
// ]



// 1. Verify if last arg is a file (should be)
// 2. Verify extension
def extensions = ['fastq','fastq.gz','fqz']
def branches = [:]

args.each { a ->
  def input_file = new File(a)

  // IS FILE?
  if ( !input_file.isFile() )
  {
    println "INPUT: $input_file is not a file."
    println "USAGE: bpipe-config pipe <project_pipeline> /lustre2/raw_data/RUN/PROJECT/Sample_*/*.fastq.gz"
    System.exit(1)
  }

  // println "ARG: $input_file"
  def file_name = input_file.canonicalPath
  def check_extension = extensions.collect() { e -> file_name.endsWith(e) }

  // Iterates over the elements of a collection, and checks whether at least one element is true according to the Groovy Truth.
  if (!check_extension.any())
  {
    println "Input file: ${input_file.absolutePath} don't have a known extension, known extensions are: ${extensions.join(', ')}"
    System.exit(1)
  }

  // MAP KEY IS THE SAMPLE DIR
  def sample_dir = input_file.getParentFile().getName()

  if ( !branches.containsKey(sample_dir) )
  {
    // ADD SAMPLESHEET TO LIST AT FIRST ITERAION ONLY and check if exists
    def samplesheet = input_file.getParentFile().canonicalPath + '/SampleSheet.csv'

    if ( new File(samplesheet).exists() )
    {
      branches[sample_dir] = [samplesheet]
    }
    else
    {
      println "Can't find SampleSheet.csv file"
    }

    branches[sample_dir].push file_name
  }
  else
  {
    branches[sample_dir].push file_name
  }

}



println prettyPrint(toJson(branches))
