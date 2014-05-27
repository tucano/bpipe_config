// MODULE XHMM GENOTYPE CNV

xhmm_genotype_cnv_gfu =
{
    var pretend : false
    var params  : "1e-08,6,70,-3,1,0,1,3,1"

    doc title: "XHMM: genotype cnv",
        desc: """
            CONFIGURATION:
            pretend : $pretend
            params  : $params
        """,
        constraints: "",
        author: "davide.rambaldi@gmail.com"


    // GENERATE PARAMS file from string
    new File("params.txt").write(params.split(",").join("\t"))

    produce("DATA.vcf")
    {
        def command = """
            $XHMM --genotype -p ./params.txt -r ./DATA.PCA_normalized.filtered.sample_zscores.RD.txt -R ./DATA.same_filtered.RD.txt
            -g ./DATA.xcnv -F $REFERENCE_GENOME_FASTA -v ./DATA.vcf
        """

        if (pretend)
        {
            println """
                INPUTS: $inputs
                OUTPUTS: $inputs
                COMMAND: $command
            """
            command = "touch $output"
        }

        exec "$command", "xhmm"
    }
}
