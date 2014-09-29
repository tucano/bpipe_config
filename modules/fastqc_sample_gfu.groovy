// MODULE FASTQC SAMPLE

@intermediate
fastqc_sample_gfu =
{
    // stage vars
    var paired  : true
    var pretend : false

    doc title: "Fastqc on fastq.gz files in a sample directory",
        desc: """
            This module is for Illumina project structure, use fastqc_lane_gfu for single sample directory.
            Take as input a directory named: SAMPLE_NAME_report ($input)
            and generate in it fastqc files from the corresponding sample.

            Main options with value:
            pretend          : $pretend
            paired           : $paired
        """,
        constraints: """
            Need a strict directory structure: Sample_1, Sample_1_report, ... , Sample_N, Sample_N_report
        """,
        author: "davide.rambaldi@gmail.com"

    requires FASTQC : "Please define path of FASTQC"

    // GET INPUT DIR (report dir) AND SET OUTPUT DIR()
    def report_dir = branch.sample + '_report'
    output.dir = report_dir
    //println "NAME: ${branch.name}, INPUTS: $inputs.gz"

    // IDENTIFY LANE GROUPS
    def lane_groups = inputs.gz*.replaceAll(".fastq.gz","")*.replaceAll(/.*\//,"").groupBy([{ it.replaceAll(/_R[12].*/,"").replaceAll(/.*\//,"")}])

    def output_prefix = []
    lane_groups.each { prefix, files ->
        if (paired)
        {
            if (files.size <= 2)
            {
                output_prefix << files
            }
            else
            {
                output_prefix << files*.replaceFirst(/_[0-9]*$/,"").unique()
            }
        }
        else
        {
            if (files.size == 1)
            {
                output_prefix << files.unique()
            }
            else
            {
                output_prefix << files*.replaceFirst(/_[0-9]*$/,"").unique()
            }
        }
    }
    // FLATTEN THE ARRAY
    output_prefix = output_prefix.flatten()

    if (paired)
    {
        def outputs = []
        output_prefix.each { prefix ->
            outputs << "${prefix}_fastqc_data.txt"
        }

        produce(outputs)
        {
            def command = new StringBuffer()
            command << """
                $FASTQC -f fastq --noextract --casava --nogroup -t 4 -o ${branch.sample} ${inputs.gz};
            """
            output_prefix.each { prefix ->
                command << """
                    unzip -o ${branch.sample}/${prefix}_fastqc.zip -d ${input};
                    rm ${branch.sample}/${prefix}_fastqc.zip;
                    cp ${report_dir}/${prefix}_fastqc/fastqc_data.txt ${report_dir}/${prefix}_fastqc_data.txt;
                """
            }

            if (pretend)
            {
                println """
                    DATA_DIR: ${branch.sample}
                    REPORT_DIR: ${report_dir}
                    INPUT FILES: ${inputs.gz}
                    OUTPUT PREFIX: ${output_prefix}
                    OUTPUTS: $outputs
                    COMMAND: $command
                    WORKING_DIR: ${System.getProperty("user.dir")}
                """
                command = new StringBuffer()
                output_prefix.each { prefix ->
                    command << """
                        touch ${report_dir}/${prefix}_zipdata.txt;
                        zip -r ${report_dir}/${prefix}_zipdata.zip ${report_dir}/${prefix}_zipdata.txt;
                        unzip -o ${report_dir}/${prefix}_zipdata.zip;
                        touch ${report_dir}/${prefix}_fastqc_data.txt;
                    """
                }
            }
            exec "$command","fastqc"
        }
    }
    else
    {
        def outputs = []
        output_prefix.each { prefix ->
            outputs << "${prefix}_fastqc_data.txt"
        }

        produce(outputs)
        {
            def command = new StringBuffer()
            command << """
                $FASTQC -f fastq --noextract --casava --nogroup -t 4 -o ${branch.sample} ${inputs.gz};
            """
            output_prefix.each { prefix ->
                command << """
                    unzip -o ${branch.sample}/${prefix}_fastqc.zip -d ${report_dir};
                    rm ${branch.sample}/${prefix}_fastqc.zip;
                    cp ${report_dir}/${prefix}_fastqc/fastqc_data.txt ${report_dir}/${prefix}_fastqc_data.txt;
                """
            }

            if (pretend)
            {
                println """
                    DATA_DIR: ${branch.sample}
                    REPORT_DIR: ${report_dir}
                    INPUT FILES: ${inputs.gz}
                    OUTPUT PREFIX: ${output_prefix}
                    OUTPUT: $output
                """
                command = new StringBuffer()
                output_prefix.each { prefix ->
                    command << """
                        touch ${report_dir}/${prefix}_zipdata.txt;
                        zip -r ${report_dir}/${prefix}_zipdata.zip ${report_dir}/${prefix}_zipdata.txt;
                        unzip -o ${report_dir}/${prefix}_zipdata.zip;
                        touch ${report_dir}/${prefix}_fastqc_data.txt;
                    """
                }
            }
            exec "$command", "fastqc"
        }
    }
}
