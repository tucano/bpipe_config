// MODULE MARKVCF (Rev1)

markvcf_gfu =
{
    var pretend : false

    // INFO
    doc title: "MARKVCF",
        desc: "Mark VCF files for gene properties",
        constraints: "For any question, write to cittaro.davide@hsr.it",
        author: "davide.rambaldi@gmail.com"

    requires MARKVCF : "Please define MARKVCF path"
    requires SQL_GENES_TABLE : "Please define SQL_GENES_TABLE path"
    requires PHI_SCORES : "Please define PHI_SCORES path"

    produce("Tier1.vcf")
    {
        def command = """
            $MARKVCF -v $input.vcf -r $SQL_GENES_TABLE -g $PHI_SCORES -n Phi_ > $output.vcf
        """

        if (pretend)
        {
            println """
                INPUT: $input
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
