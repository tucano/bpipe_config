load "../../../modules/make_wgs_report_gfu.groovy"

// TEMPLATE VARS
TEMPLATE                   = "../../../templates/reports/wes/WES_report.Rmd.template"
GENOME_NAMES               = "../../../config/genomes.txt"
CUSTOM_CSS                 = "../../../templates/reports/report.css"
BAM_RECALIBRATION_SCHEMA   = "../../../templates/reports/wes/WES_BAM_RECALIBRATION_SCHEMA.png"
VARIANTS_CALLING_SCHEMA    = "../../../templates/reports/wes/WES_VARIANTS_CALLING_SCHEMA.png"
VARIANTS_ANNOTATION_SCHEMA = "../../../templates/reports/wes/WES_VARIANTS_ANNOTATION_SCHEMA.png"

RATIONALE      = "rationale.md"
STATS          = "stats.groovy"
PEDIGREE       = "pedigree.ped"
SAMPLESHEET    = "SampleSheet.csv"

PROJECTNAME            = "Borghini_113_CAPS"
REFERENCE              = "hg19"

Bpipe.run {
    make_wgs_report_gfu.using(pretend:false, with_healty_exomes:false)
}
