// MODULE FASTQC SAMPLE
FASTQC="/lustre1/tools/bin/fastqc"

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
        def outputs = [
            "${output_prefix[0]}_fastqc_data.txt",
            "${output_prefix[1]}_fastqc_data.txt"
        ]

        produce(outputs)
        {
            def command = """
                $FASTQC -f fastq --noextract --casava --nogroup -t 4 -o $input ${data_dir}/*.fastq.gz;
                unzip -o ${input}/${output_prefix[0]}_fastqc.zip -d ${input};
                rm ${input}/${output_prefix[0]}_fastqc.zip;
                unzip -o ${input}/${output_prefix[1]}_fastqc.zip -d ${input};
                rm ${input}/${output_prefix[1]}_fastqc.zip;
                cp ${input}/${output_prefix[0]}_fastqc/fastqc_data.txt $output1;
                cp ${input}/${output_prefix[1]}_fastqc/fastqc_data.txt $output2;
            """

            if (pretend)
            {
                println """
                    DATA_DIR: $data_dir
                    REPORT_DIR: $input
                    INPUT FILES: $input_files
                    OUTPUT PREFIX: ${output_prefix}
                    OUTPUTS: $outputs
                """
                command = """
                    touch ${input}/${output_prefix[0]}_zipdata.txt;
                    touch ${input}/${output_prefix[1]}_zipdata.txt;
                    zip -r ${input}/${output_prefix[0]}_zipdata.zip ${input}/${output_prefix[0]}_zipdata.txt;
                    zip -r ${input}/${output_prefix[1]}_zipdata.zip ${input}/${output_prefix[1]}_zipdata.txt;
                    unzip -o ${input}/${output_prefix[0]}_zipdata.zip;
                    unzip -o ${input}/${output_prefix[1]}_zipdata.zip;
                    touch $output1 $output2;
                """
            }
            exec command, "fastqc"
        }
    }
    else
    {
        produce("${output_prefix[0]}_fastqc_data.txt")
        {
            def command = """
                $FASTQC -f fastq --noextract --casava --nogroup -t 4 -o $input ${data_dir}/*.fastq.gz;
                unzip -o ${input}/${output_prefix[0]}_fastqc.zip -d ${input};
                rm ${input}/${output_prefix[0]}_fastqc.zip;
                cp ${input}/${output_prefix[0]}_fastqc/fastqc_data.txt $output;
            """

            if (pretend)
            {
                println """
                    DATA_DIR: $data_dir
                    REPORT_DIR: $input
                    INPUT FILES: $input_files
                    OUTPUT PREFIX: ${output_prefix}
                    OUTPUT: $output
                """
                command = """
                    touch ${input}/${output_prefix[0]}_zipdata.txt;                    
                    zip -r ${input}/${output_prefix[0]}_zipdata.zip ${input}/${output_prefix[0]}_zipdata.txt;                    
                    unzip -o ${input}/${output_prefix[0]}_zipdata.zip;
                    touch $output;
                """
            }
            exec command, "fastqc"
        }
    }
}
