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

    // GET INPUT DIR (report dir) AND SET OUTPUT DIR()
    output.dir = input
    // GET DATA DIR and locate input files
    def data_dir = input.replaceAll("_report","")
    def input_files = []
    def dir = new File(data_dir)
    dir.eachFile { file ->
        if (file.name.endsWith('fastq.gz')) {
            input_files << file.name
        }
    }

    // DEFINE OUTPUT PREFIX
    def output_prefix
    if (paired)
    {
        if (input_files.size <= 2)
        {
            output_prefix = input_files*.replaceAll(".fastq.gz","")
        }
        else
        {
            output_prefix = input_files*.replaceAll(/_[0-9]*\.fastq\.gz/,"").unique()
        }
    }
    else
    {
        if (input_files.size == 1)
        {
            output_prefix = input_files*.replaceAll(".fastq.gz","").unique()
        }
        else
        {
            output_prefix = input_files*.replaceAll(/_[0-9]*\.fastq\.gz/,"").unique()
        }
    }


    if (paired)
    {
        def outputs = []
        output_prefix.each { prefix ->
            outputs << "${prefix}_fastqc_data.txt"
        }

        produce(outputs)
        {
            if (pretend)
            {
                println """
                    DATA_DIR: $data_dir
                    REPORT_DIR: $input
                    INPUT FILES: $input_files
                    OUTPUT PREFIX: ${output_prefix}
                    OUTPUTS: $outputs
                """
                def command = new StringBuffer()
                output_prefix.each { prefix ->
                    command << """
                        touch ${input}/${prefix}_zipdata.txt;
                        zip -r ${input}/${prefix}_zipdata.zip ${input}/${prefix}_zipdata.txt;
                        unzip -o ${input}/${prefix}_zipdata.zip -d ${input};
                        touch ${input}/${prefix}_fastqc_data.txt;
                    """
                }
                exec "$command"
            }
            else
            {
                def command = new StringBuffer()
                command << """
                    $FASTQC -f fastq --noextract --casava --nogroup -t 4 -o $input ${data_dir}/*.fastq.gz;
                """
                output_prefix.each { prefix ->
                    command << """
                        unzip -o ${input}/${prefix}_fastqc.zip -d ${input};
                        rm ${input}/${prefix}_fastqc.zip;
                        cp ${input}/${prefix}_fastqc/fastqc_data.txt ${input}/${prefix}_fastqc_data.txt;
                    """
                }
                exec "$command", "fastqc"
            }
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
            if (pretend)
            {
                println """
                    DATA_DIR: $data_dir
                    REPORT_DIR: $input
                    INPUT FILES: $input_files
                    OUTPUT PREFIX: ${output_prefix}
                    OUTPUT: $output
                """
                def command = new StringBuffer()
                output_prefix.each { prefix ->
                    command << """
                        touch ${input}/${prefix}_zipdata.txt;
                        zip -r ${input}/${prefix}_zipdata.zip ${input}/${prefix}_zipdata.txt;
                        unzip -o ${input}/${prefix}_zipdata.zip -d ${input};
                        touch ${input}/${prefix}_fastqc_data.txt;
                    """
                }
                exec "$command"
            }
            else
            {
                def command = new StringBuffer()
                command << """
                    $FASTQC -f fastq --noextract --casava --nogroup -t 4 -o $input ${data_dir}/*.fastq.gz;
                """
                output_prefix.each { prefix ->
                    command << """
                        unzip -o ${input}/${prefix}_fastqc.zip -d ${input};
                        rm ${input}/${prefix}_fastqc.zip;
                        cp ${input}/${prefix}_fastqc/fastqc_data.txt $output;
                    """
                }
                exec "$command", "fastqc"
            }
        }
    }
}
