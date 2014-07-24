about title: "Whole Exome Sequencing report: IOS XXX"

// Usage line will be used to infer the correct bpipe command
// USAGE: bpipe run -r $pipeline_filename BAM/*.bam VCF/*.vcf

// stats line will be used to infer stats file templates (reporting)
// STATS: wes

/*********************************************************************************************************************************
 *  README FIRST AND FOLLOW INSTRUCTIONS!
 *
 *  This is a complex pipeline that requires a lot of variables/configurations, see below for requested files/variables:
 *
 *  DIRECTORY STRUCTURE REQUESTED TO LAUNCH THE REPORT:
 *  Here an example of directory structure and a description of each file:
 *  .
 *  ├── BAM
 *  │   ├── one.bam
 *  │   └── two.bam
 *  ├── SampleSheet.csv
 *  ├── VCF
 *  │   ├── Tier0.vcf
 *  │   ├── Tier1.vcf
 *  │   └── Tier2.vcf
 *  ├── report_data
 *      ├── pedigree.ped
 *      ├── rationale.md    <-- GENERATED FROM TEMPLATE IF DOESN'T EXISTS
 *      ├── stats.groovy    <-- GENERATED FROM TEMPLATE IF DOESN'T EXISTS
 *
 *  DIRECTORIES:
 *  - BAM: contains the FINAL BAM files of your analysis (ONE BAM FILE for SAMPLE!)
 *  - VCF: contains the FINAL Tier files of your analysis (Tier0.vcf, Tier1.vcf and Tier2.vcf)
 *
 *  INPUT FILES in report_data:
 *  - rationale.md: An Abstract (short description) of the Project
 *  - pedigree.ped: A ped file
 *  - stats.groovy contains a list of variables/infos used by the pipelines
 *
 *  SAMPLESHEET:
 *  - SampleSheet.cvs: PreSampleSheet with list of samples (the one with design columns)
 *
 *  OUTPUTS:
 *  1. report.html file
 *  2. a script to rebuild the report with knitr on the server
 *  3. a ZIP package to rerun everything locally with RStudio
 *
 *********************************************************************************************************************************/

// DATA FILE NAMES and REPORT DATA DIR NAME
RATIONALE                  = "rationale.md"
STATS                      = "stats.groovy"
PEDIGREE                   = "pedigree.ped"
REPORT_DATA_DIR            = "report_data"

// SAMPLESHEET (in working dir as usual)
SAMPLESHEET                = "SampleSheet.csv"

// TEMPLATE VARS (DON'T CHANGE)
GENOME_NAMES               = "/lustre1/tools/libexec/bpipeconfig/config/genomes.txt"
CUSTOM_CSS                 = "/lustre1/tools/libexec/bpipeconfig/templates/reports/report.css"
TEMPLATE                   = "/lustre1/tools/libexec/bpipeconfig/templates/reports/wes/WES_report.Rmd.template"
BAM_RECALIBRATION_SCHEMA   = "/lustre1/tools/libexec/bpipeconfig/templates/reports/wes/WES_BAM_RECALIBRATION_SCHEMA.png"
VARIANTS_CALLING_SCHEMA    = "/lustre1/tools/libexec/bpipeconfig/templates/reports/wes/WES_VARIANTS_CALLING_SCHEMA.png"
VARIANTS_ANNOTATION_SCHEMA = "/lustre1/tools/libexec/bpipeconfig/templates/reports/wes/WES_VARIANTS_ANNOTATION_SCHEMA.png"

// IN ORDER TO RERUN QUALITY WE NEED THIS INFO FROM ALIGNMENT
REFERENCE_GENOME_FASTA     = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/fa/BPIPE_REFERENCE_GENOME.fa"
BAITS                      = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/exomes_targets/nexterarapidcapture_expandedexome_probes.interval_list"
TARGETS                    = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/exomes_targets/nexterarapidcapture_expandedexome_targetedregions.interval_list"
TARGETS_EXPANDED           = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/exomes_targets/nexterarapidcapture_expandedexome_targetedregions_slope200.interval_list"

// VARIANT CALLING AND ANNOTATION VARIABLES
HEALTY_EXOMES_DIR          = "/lustre1/workspace/Stupka/HealthyExomes/"

//--BPIPE_ENVIRONMENT_HERE--


Bpipe.run {
  "Tier2.vcf" * [vcf_coverage_gfu.using(with_name:"all_samples.vcfcoverage",output_dir:"$REPORT_DATA_DIR")] +
  "%.bam" * [bamtools_stats_gfu.using(output_dir:"bamtools_stats"), calculate_hsmetrics_gfu.using(output_dir:"hs_metrics")] +
  "*.hsmetrics" * [make_report_hsmetrics_gfu.using(with_name:"all_samples.hsmetrics",output_dir:"$REPORT_DATA_DIR")] +
  "*.bamstats" * [merge_bamtools_stats_gfu.using(with_name:"all_samples.bamstats",output_dir:"$REPORT_DATA_DIR")] +
  "Tier*.vcf" * [count_variants_gfu.using(output_dir:"$REPORT_DATA_DIR")] +
  bwa_software_version_gfu.using(output_dir:"$REPORT_DATA_DIR") + healty_exomes_info_gfu.using(output_dir:"$REPORT_DATA_DIR") +
  make_wgs_report_gfu.using(
    report_data_dir    : "$REPORT_DATA_DIR",
    report_data_prefix : "all_samples",
    with_pedigree      : true,
    with_healty_exomes : true
  )
}
