// MODULE SPLIT FASTQ FILE

@intermediate
split_fastq_gfu =
{
    var SPLIT_READS_SIZE : 2000000
    var paired  : true
    var pretend : false

    doc title: "Split fastq.gz files in $SPLIT_READS_SIZE reads/file",
        desc: "Use split to subdivide a fastq pair (R1 and R2) in chunks (paired: true) or a single file (paired: false)",
        constraints: "Work with fastq.gz and fastq files",
        author: "davide.rambaldi@gmail.com"

    def n_lines = SPLIT_READS_SIZE * 4
    def command = ""

    produce("*.fastq")
    {
        if (input.endsWith(".gz"))
        {
            if (paired)
            {
                command = """
                    zcat $input1.gz | split -l $n_lines -d -a 4 - read1_;
                    for file in read1_*; do mv "$file" "${file}.fastq"; done;
                    zcat $input2.gz | split -l $n_lines -d -a 4 - read2_;
                    for file in read2_*; do mv "$file" "${file}.fastq"; done;
                """
            }
            else
            {
                command = """
                    zcat $input1.gz | split -l $n_lines -d -a 4 - read_;
                    for file in read_*; do mv "$file" "${file}.fastq"; done;
                """
            }
        }
        else
        {
            if (paired)
            {
                command = """
                    split -l $n_lines -d -a 4 $input1.fastq read1_;
                    split -l $n_lines -d -a 4 $input2.fastq read2_;
                    for file in read1_*; do mv "$file" "${file}.fastq"; done;
                    for file in read2_*; do mv "$file" "${file}.fastq"; done;
                """
            }
            else
            {
                command = """
                    split -l $n_lines -d -a 4 $input1.fastq read_;
                    for file in read_*; do mv "$file" "${file}.fastq"; done;
                """
            }
        }

        if (pretend)
        {
            println "COMMAND: $command"

            if (paired)
            {
                command = "touch read1_1.fastq read2_1.fastq"
            }
            else
            {
                command = "touch read_1.fastq"
            }
        }
        exec command
    }
}
