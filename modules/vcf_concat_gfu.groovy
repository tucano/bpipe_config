// MODULE VCF CONCAT
VCFCONCAT = "export PERL5LIB=/lustre1/tools/libexec/vcftools_0.1.9/perl/ && /usr/local/cluster/bin/vcf-concat"
VCFSORT   = "/usr/local/cluster/bin/vcf-sort-mod -t /lustre2/scratch"

@intermediate
vcf_concat_gfu =
{
    var pretend : false
    var with_suffix : ""

    doc title: "Vcf concat stage",
        desc: "Sort and concatenate VCF files",
        constraints: "...",
        author: "davide.rambaldi@gmail.com"

    if (with_suffix != "")
    {
        produce("${input.bam.prefix}.${with_suffix}.vcf")
        {
            def command = """
                $VCFCONCAT $inputs.vcf | $VCFSORT > $output
            """

            if (pretend)
            {
                println """
                    INPUTS $inputs
                    VCF: $inputs.vcf
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
    else
    {
        produce("${input.bam.prefix}.vcf")
        {
            def command = """
                $VCFCONCAT $inputs.vcf | $VCFSORT > $output
            """

            if (pretend)
            {
                println """
                    INPUTS $inputs
                    VCF: $inputs.vcf
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
}
