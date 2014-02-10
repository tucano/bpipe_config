// MODULE SOAPSPLICE PREPARE HEADERS
SSPLICE="/lustre1/tools/bin/soapsplice"

@intermediate
soapsplice_prepare_headers_gfu =
{
    var pretend    : false
    var sample_dir : false

    // INFO
    doc title: "Prepare header file for alignment with soapsplice",
        desc: "Generate a header file for alignment with soapsplice",
        constraints: "...",
        author: "davide.rambaldi@gmail.com"

    def header

    if (sample_dir)
    {
        // Parse input SampleSheet.csv to get SAMPLE INFO
        // If there are no problems with SampleSheet.csv: should be a SampeSheet.csv with one line
        def mdir = input.replaceFirst("/.*","")
        output.dir = mdir
        def samplesheet = new File("${mdir}/SampleSheet.csv")
        if (samplesheet.exists())
        {
            // get first line after headers
            def sample = samplesheet.readLines()[1].split(",")
            def experiment_name = sample[0] + "_" + sample[2]
            header = '@RG' + "\tID:${experiment_name}\tPL:${PLATFORM}\tPU:${sample[0]}\tLB:${experiment_name}\tSM:${sample[2]}\tCN:${CENTER}"
        }
        else
        {
            println "Can't find SampleSheet in directory ${mdir} ! Aborting ..."
            System.exit(1)
        }
    }
    else
    {
        header = '@RG' + "\tID:${EXPERIMENT_NAME}\tPL:${PLATFORM}\tPU:${FCID}\tLB:${EXPERIMENT_NAME}\tSM:${SAMPLEID}\tCN:${CENTER}"
    }

    transform("header")
    {

        def command = """
            SSVERSION=\$($SSPLICE | head -n1 | awk '{print \$3}');
            awk '{OFS="\t";  print "@SQ","SN:"\$1,"LN:"\$2}' $REFERENCE_FAIDX > $output;
            echo -e "$header" >> $output;
            echo -e "@PG\tID:soapsplice\tPN:soapsplice\tVN:$SSVERSION" >> $output;
        """

        if (pretend)
        {
            println """
                INPUT:  $input
                OUTPUT: $output
                COMMAND: $command
            """

            command = """
                echo "INPUT: $input" > $output
            """
        }

        exec command
    }
}
