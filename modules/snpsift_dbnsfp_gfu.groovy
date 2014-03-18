// MODULE SNPSIFT DBNSFO
SNPSIFT="java -jar /lustre1/tools/bin/SnpSift-3.5c.jar"

@intermediate
snpsift_dbnsfp_gfu =
{
    var pretend : false

    doc title: "SnpSift annotate dbnsfp from $DBNSFP in the vcf file",
        desc: """
            SnpSift is a toolbox that allows you to filter and manipulate annotated files.
            Main options with value:
                pretend    : $pretend
        """,
        constraints: "...",
        author: "davide.rambaldi@gmail.com"


    filter("dbnsfp")
    {
        def command = """
            $SNPSIFT dbnsfp -f Ancestral_allele,Ensembl_geneid,Polyphen2_HVAR_pred,SIFT_score,GERP++_RS,ESP6500_AA_AF $DBNSFP $input.vcf > $output.vcf
        """

        if (pretend)
        {
            println """
                INPUT:  $input
                OUTPUT: $output
                COMMAND:
                    $command
            """
            command = """
                echo "INPUT: $input" > $output;
            """
        }
        exec command
    }
}
