// MODULE SOAPSPLICE PREPARE HEADERS (rev1)

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

    requires PLATFORM: "Please define the PLATFORM variable"
    requires CENTER: "Please define the CENTER variable"
    requires REFERENCE_FAIDX: "Please define a REFERENCE_FAIDX file"
    requires SSPLICE: "Please define SSPLICE path"

    if (sample_dir)
    {
        /// TAKE SAMPLE DIR FROM BRANCH.SAMPLE
        // Parse input SampleSheet.csv to get SAMPLE INFO
        output.dir = branch.sample
        def samplesheet = new File("${branch.sample}/SampleSheet.csv")
        if (samplesheet.exists())
        {
            // get first line after headers
            def sample = samplesheet.readLines()[1].split(",")
            def experiment_name = sample[0] + "_" + sample[2]
            header = '@RG' + "\tID:${experiment_name}\tPL:${PLATFORM}\tPU:${sample[0]}\tLB:${experiment_name}\tSM:${sample[2]}\tCN:${CENTER}"
        }
        else
        {
            fail "Can't find SampleSheet in directory ${branch.sample} ! Aborting ..."
        }
    }
    else
    {
        requires EXPERIMENT_NAME : "Please define the EXPERIMENT_NAME variable"
        requires FCID: "Please define the FCID variable"
        requires SAMPLEID: "Please define the SAMPLEID variable"

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
