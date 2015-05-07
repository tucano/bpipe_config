about title: "Human variants calling for exomes with freebayes: IOS XXX"

// INFO_USAGE: bpipe-config pipe freebayes_exome_variant_calling (CWD)
// INFO_USAGE: bpipe-config pipe freebayes_exome_variant_calling Sample_* (runner.sh)

// Usage line will be used to infer the correct bpipe command
// USAGE: bpipe run -r $pipeline_filename *.bam

REFERENCE_GENOME_FASTA = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/fa/BPIPE_REFERENCE_GENOME.fa"
PLATFORM               = "illumina"
CENTER                 = "CTGB"
ENVIRONMENT_FILE       = "gfu_environment.sh"

//--BPIPE_ENVIRONMENT_HERE--


/*
 * PIPELINE NOTES:
 * This pipeline RENAME the output vcf file from freebayes to: all_samples.vcf
 *
 */
run {
  "*.bam" * [make_input_list_ctgb] +
  chr(1..22,'X','Y') * [ generate_bed_intervals_ctgb + target_freebayes_ctgb.using(rename:"all_samples.vcf") ] +
  "*.vcf" * [vcf_concat_gfu] +  [vcf_coverage_gfu.using(output_dir:"doc")]
}
