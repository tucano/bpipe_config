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

    def header  = '@RG' + "\tID:${EXPERIMENT_NAME}\tPL:${PLATFORM}\tPU:${FCID}\tLB:${EXPERIMENT_NAME}\tSM:${SAMPLEID}\tCN:${CENTER}"

    if (sample_dir) { output.dir = input.replaceFirst("/.*","") }

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
