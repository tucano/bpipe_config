// MODULE MERGE BAM FILES (rev1)

@preserve
merge_bam_gfu =
{
    var pretend    : false
    var merge_mode : "none"
    var sample_dir : false

    doc title: "Merge bam files with $PICMERGE",
        desc: """
            Reads a SAM or BAM file and combines the output to one file
            If file came from split_fastq_gfu (es: read_0001.bam) you should set rename: true.
            The output will be renamed with the variable SAMPLEID (${SAMPLEID}).
            For Illumina default runs set rename to FALSE, this stage will automatically remove casava groups notation.
            stage options:
                pretend    : $pretend
                merge_mode : $merge_mode
                sample_dir : $sample_dir

            Merge modes: none, sampleid, samplesheet
        """,
        constraints: "",
        author: "davide.rambaldi@gmail.com"

    requires PICMERGE : "Please define the PICMERGE path"

    def sampleid
    def output_prefix

    if (sample_dir)
    {
      output.dir = branch.sample
    }

    switch(merge_mode)
    {
      case "none":
        // just remove CASAVA groups
        println "MERGE MODE: none"
        output_prefix = input.prefix.replaceFirst(/_[0-9]+$/,"")
      break

      case "sampleid":
        requires SAMPLEID : "Please define the SAMPLEID"
        println "MERGE MODE: sampleid"
        sampleid = SAMPLEID
        output_prefix = sampleid
      break

      case "samplesheet":
        // get SampleSheet.csv
        def samplesheet
        println "MERGE MODE: samplesheet"
        if (sample_dir)
        {
          samplesheet = new File("${branch.sample}/SampleSheet.csv")
        }
        else
        {
          samplesheet = new File("SampleSheet.csv")
        }

        if (samplesheet.exists())
        {
          def sample = samplesheet.readLines()[1].split(",")
          sampleid = sample[2]
        }
        else
        {
          fail "Can't find SampleSheet in directory ${branch.sample} ! Aborting ..."
        }
        output_prefix = sampleid
      break

      default:
        fail "Unrecognized merge mode: $merge_mode"
      break
    }

    def outputs = [
        ("${output_prefix}" + ".merge.bam"),
        ("${output_prefix}" + ".merge.bai")
    ]

    // collect input bams
    def input_strings = inputs.bam.collect() { return "I=" + it}.join(" ")

    produce (outputs)
    {
        def command = """
            java -jar $PICMERGE
                $input_strings
                O=$output1
                VALIDATION_STRINGENCY=SILENT
                CREATE_INDEX=true
                MSD=true
                ASSUME_SORTED=true
                USE_THREADING=true
        """

        if (pretend)
        {
            println """
                INPUTS: $inputs
                OUTPUT: $output
                COMMAND: $command
            """
            command = """
                echo "INPUTS: $inputs" > $output1;
                echo "INPUTS: $inputs" > $output2;
            """
        }
        exec command,"merge_bam_files"
    }
}
