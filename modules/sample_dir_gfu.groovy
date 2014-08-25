// MODULE SAMPLE DIR GFU (rev1)

@intermediate
sample_dir_gfu =
{
    doc title: "Preapare working dir for a sample and set lustre options for the directory",
        desc: """
            Lustre options:
                -c -1 : a stripe_count of -1 means to stripe over all available OSTs.
                -i -1 : a start_ost_index of -1 allows the MDS to choose the starting index and it is strongly recommended, as this allows space and load balancing to be done by the MDS as needed.
                -s 2M : Stripsize 2 megabytes
        """,
        constraints: "setstripe and getstripe as non blocking steps (Fails in non lustre fs, but will return always true).",
        author: "davide.rambaldi@gmail.com"

    requires LSF : "Please define path of LSF"

    // keep the sample name as a branch variable
    branch.sample = branch.name

    // Output dir is the sample name
    output.dir = branch.sample

    produce("SampleSheet.csv","setstripe.log")
    {
        if (pretend)
        {
            println """
                SAMPLE: ${branch.sample}
                NAME:   ${branch.name}
                INPUTS: $inputs
                SAMPLESHEET: $input.csv
                OUTPUTS: $outputs
            """
        }

        exec """
            mkdir -p ${branch.sample};
            cp $input.csv $output;
            $LSF setstripe -c -1 -i -1 -s 2M ${branch.sample} 1>/dev/null 2>&1 || true;
            $LSF getstripe -d ${branch.sample} > ${branch.sample}/setstripe.log 2>&1 || true;
        """
    }
}
