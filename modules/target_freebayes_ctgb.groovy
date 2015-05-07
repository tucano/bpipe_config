target_freebayes_ctgb =
{
    var min_coverage         : 5
    var min_mapping_quality  : 15
    var min_base_quality     : 20
    var min_supporting_allele_qsum : 0
    var genotype_variant_threshold : 0

    var rename : ""

    doc title: "freebaues for target sequencing",
        desc: "",
        constraints: "",
        author: "davide.rambaldi@gmail.com"


    requires FREEBAYES: "Please define freebayes path"
    requires REFERENCE_GENOME_FASTA: "Please define reference genome fasta path"

    def output_prefix
    if (rename != "")
    {
     output_prefix = rename
    }
    else
    {
     output_prefix = "${input.bam.prefix.replaceAll(/.*\//,"")}"
    }

    produce("${output_prefix}.${chr}.vcf")
    {
        exec """
            $FREEBAYES -f $REFERENCE_GENOME_FASTA
                    --targets $input.bed
                    --min-coverage $min_coverage
                    --min-mapping-quality $min_mapping_quality
                    --min-base-quality $min_base_quality
                    --min-supporting-allele-qsum $min_supporting_allele_qsum
                    --genotype-variant-threshold $genotype_variant_threshold
                    --bam-list $input.txt > $output.vcf
        """
    }
}
