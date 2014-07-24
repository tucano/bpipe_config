about title: "Whole Exome Sequencing report: IOS XXX"

// Usage line will be used to infer the correct bpipe command
// USAGE: bpipe run -r $pipeline_filename BAM/*.bam VCF/*.vcf

// stats line will be used to infer stats file templates (reporting)
// STATS: wes

/*********************************************************************************************************************************
 *  README FIRST AND FOLLOW INSTRUCTIONS!
 *
 *  This is a complex pipeline that requires a lot of variables/configurations, see below for requested files/variables.
 *
 *  STEP BY STEP INSTRUCTIONS:
 *  1. Copy SampleSheet.csv in your working dir
 *  2. create BAM directory and link/copy bam and bai files
 *  3. create VCF directory and link/copy Tier vcf files
 *  4. launch bpipe-config pipe WES_report
 *  5. Check files in directory: report_data
 *  6. launch bpipe: bpipe run WES_report.groovy BAM/*.bam VCF/*.vcf
 *
 *  PEDIGREE: if you have a pedigree, put it in REPORT_DATA_DIR directory
 *
 *  HEALTY EXOMES:
 *  if you DON't USE the exomes in HEALTY_EXOMES_DIR, please set  with_healty_exomes:false and
 *  remove stage healty_exomes_info_gfu
 *
 *  FILES in report_data:
 *  - rationale.md: An Abstract (short description) of the Project, auto-generated from template if doesn't exists
 *  - stats.groovy contains a list of variables/infos used by the pipelines, auto-generated from template if doesn't exists
 *  - pedigree.ped: A ped file
 *
 *  MORE INFO:
 *  Here an example of directory structure and a description of each file:
 *  .
 *  |-- BAM
 *  |   |-- one.bam        <-- contains the FINAL BAM files of your analysis (ONE BAM FILE for SAMPLE!)
 *  |   `-- two.bam
 *  |-- SampleSheet.csv
 *  |-- VCF                <-- contains the FINAL Tier files of your analysis (Tier0.vcf, Tier1.vcf and Tier2.vcf)
 *  |   |-- Tier0.vcf
 *  |   |-- Tier1.vcf
 *  |   `-- Tier2.vcf
 *  |-- report_data        <-- contains additional files used by the report
 *      |-- pedigree.ped
 *      |-- rationale.md
 *      `-- stats.groovy
 *
 *  SAMPLESHEET:
 *  - SampleSheet.cvs: PreSampleSheet with list of samples (with design columns)
 *
 *  OUTPUTS:
 *  1. report.html and report.Rmd file
 *  2. a script to rebuild the report with knitr on the server, run with: bpipe run reknit.groovy report.Rmd
 *
 *********************************************************************************************************************************/

// REPORT DATA INFO
REPORT_DATA_DIR            = "report_data"     // This is the directory where we store intermediate files for the report
RATIONALE                  = "rationale.md"    // Inside the REPORT_DATA_DIR you must have a rationale.md file (descrition of the project)
STATS                      = "stats.groovy"    // The file stats.groovy contains all the variables that we need to compile the report
PEDIGREE                   = "pedigree.ped"    // If you have a PED file of the project, put it in the REPORT_DATA_DIR

// SAMPLESHEET
SAMPLESHEET                = "SampleSheet.csv" // This is the SampleSheet with the additional DESIGN colums.

// TEMPLATE VARS (DON'T CHANGE)
GENOME_NAMES               = "/lustre1/tools/libexec/bpipeconfig/config/genomes.txt"
CUSTOM_CSS                 = "/lustre1/tools/libexec/bpipeconfig/templates/reports/report.css"
TEMPLATE                   = "/lustre1/tools/libexec/bpipeconfig/templates/reports/wes/WES_report.Rmd.template"
BAM_RECALIBRATION_SCHEMA   = "/lustre1/tools/libexec/bpipeconfig/templates/reports/wes/WES_BAM_RECALIBRATION_SCHEMA.png"
VARIANTS_CALLING_SCHEMA    = "/lustre1/tools/libexec/bpipeconfig/templates/reports/wes/WES_VARIANTS_CALLING_SCHEMA.png"
VARIANTS_ANNOTATION_SCHEMA = "/lustre1/tools/libexec/bpipeconfig/templates/reports/wes/WES_VARIANTS_ANNOTATION_SCHEMA.png"
REKNIT_TEMPLATE            = "/lustre1/tools/libexec/bpipeconfig/templates/reknit.groovy"
README_TEMPLATE            = "/lustre1/tools/libexec/bpipeconfig/templates/reports/README.md"
STYLE_TEMPLATE             = "/lustre1/tools/libexec/bpipeconfig/templates/reports/Style.R"

// IN ORDER TO RERUN QUALITY WE NEED THIS INFO FROM ALIGNMENT
REFERENCE_GENOME_FASTA     = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/fa/BPIPE_REFERENCE_GENOME.fa"
BAITS                      = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/exomes_targets/nexterarapidcapture_expandedexome_probes.interval_list"
TARGETS                    = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/exomes_targets/nexterarapidcapture_expandedexome_targetedregions.interval_list"
TARGETS_EXPANDED           = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/exomes_targets/nexterarapidcapture_expandedexome_targetedregions_slope200.interval_list"

// VARIANT CALLING NEED TO KNOW WHERE ARE THE HEALTY EXOMES
HEALTY_EXOMES_DIR          = "/lustre1/workspace/Stupka/HealthyExomes/"

//--BPIPE_ENVIRONMENT_HERE--


Bpipe.run {
  "Tier2.vcf" * [vcf_coverage_gfu.using(with_name:"all_samples.vcfcoverage",output_dir:"$REPORT_DATA_DIR")] +
  "%.bam" * [bamtools_stats_gfu.using(output_dir:"bamtools_stats"), calculate_hsmetrics_gfu.using(output_dir:"hs_metrics")] +
  "*.hsmetrics" * [make_report_hsmetrics_gfu.using(with_name:"all_samples.hsmetrics",output_dir:"$REPORT_DATA_DIR")] +
  "*.bamstats" * [merge_bamtools_stats_gfu.using(with_name:"all_samples.bamstats",output_dir:"$REPORT_DATA_DIR")] +
  "Tier*.vcf" * [count_variants_gfu.using(output_dir:"$REPORT_DATA_DIR")] +
  bwa_software_version_gfu.using(output_dir:"$REPORT_DATA_DIR") +
  healty_exomes_info_gfu.using(output_dir:"$REPORT_DATA_DIR") +
  make_wgs_report_gfu.using(
    report_data_dir    : "$REPORT_DATA_DIR",
    report_data_prefix : "all_samples",
    with_pedigree      : true,
    with_healty_exomes : true
  ) + make_reknit_script_gfu
}
