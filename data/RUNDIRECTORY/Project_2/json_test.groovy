#!/usr/bin/env groovy
def extensions = ['fastq','fastq.gz','fqz']

def build_json_input = { dir ->
  def files = [:]
  extensions.each { files[it] = [] }

  new File(dir).eachFile() { file ->
    extensions.each { extension ->
      if (file.canonicalPath.endsWith(extension)) {
        files[extension].push file.canonicalPath
      }
    }
  }

  // get longer array
  def entry = files.max { it.value.size }
  entry.getValue()
}

working_files = build_json_input(args[0])
working_files.each {
  println it
}
