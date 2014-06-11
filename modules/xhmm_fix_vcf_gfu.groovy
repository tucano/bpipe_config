// MODULE XHMM FIX VCF

@preserve
xhmm_fix_vcf_gfu =
{
    var pretend : false

    doc title: "XHMM: genotype cnv",
        desc: """
            CONFIGURATION:
            pretend : $pretend
        """,
        constraints: "",
        author: "davide.rambaldi@gmail.com"

    requires XHMM_FIX_VCF, "Please define the XHMM_FIX_VCF path"
    requires REFERENCE_GENOME_FASTA, "Please define the REFERENCE_GENOME_FASTA path"

    filter("fixed")
    {
        def command = """
            $XHMM_FIX_VCF $input.vcf $REFERENCE_GENOME_FASTA > $output.vcf
        """

        if (pretend)
        {
            println """
                INPUT: $input
                OUTPUT: $output
                COMMAND: $command
            """
            command = "touch $output"
        }

        exec "$command", "xhmm"
    }
}
