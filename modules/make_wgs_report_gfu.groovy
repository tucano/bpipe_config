// MODULE MAKE WGS REPORT GFU
import groovy.text.SimpleTemplateEngine

@preserve
make_wgs_report_gfu =
{
  var pretend            : false
  var report_data_dir    : "report_data"
  var report_data_prefix : "all_samples"
  var with_pedigree      : true
  var with_healty_exomes : true

  doc title: "Make a wgs report",
      desc: "This stage use knitr",
      constraints: "Required packages in R: knitr, kinship2, xtable",
      author: "davide.rambaldi@gmail.com"

  // check requires
  requires TEMPLATE        : "Please define a template for the report (WES_report.Rmd.template)"
  requires GENOME_NAMES    : "Please define a genomes version/names mapping file (genomes.txt)"
  requires CUSTOM_CSS      : "Please define path of custom.css"
  requires RATIONALE       : "Please define a rationale filename for the report (rationale.md)"
  requires STATS           : "Please define a stats filename for the report (stats.groovy)"
  requires SAMPLESHEET     : "Please define SampleSheet filename for the report (SampleSheet.csv)"
  requires PROJECTNAME     : "Please define a PROJECTNAME (Es: Borghini_113_CAPS)"
  requires REFERENCE       : "Please define a REFERENCE (Es: hg19)"

  if (with_pedigree) {
    requires PEDIGREE      : "Please define a pedigree filename for the report (pedogree.ped)"
  }

  // check images requires
  requires BAM_RECALIBRATION_SCHEMA    : "Please define path of image WES_BAM_RECALIBRATION_SCHEMA.png"
  requires VARIANTS_CALLING_SCHEMA     : "Please define path of image WES_VARIANTS_CALLING_SCHEMA.png"
  requires VARIANTS_ANNOTATION_SCHEMA  : "Please define path of image WES_VARIANTS_ANNOTATION_SCHEMA.png"

  // USE GLOB TO FIND REPORT FILES
  from(glob("${report_data_dir}/${report_data_prefix}.*")) produce("report.Rmd","report.html")
  {
    // check static images paths
    if (!new File("$BAM_RECALIBRATION_SCHEMA").isFile()) {
       fail "Can't open templateimage file $BAM_RECALIBRATION_SCHEMA"
    }
    if (!new File("$VARIANTS_CALLING_SCHEMA").isFile()) {
       fail "Can't open templateimage file $VARIANTS_CALLING_SCHEMA"
    }
    if (!new File("$VARIANTS_ANNOTATION_SCHEMA").isFile()) {
       fail "Can't open templateimage file $VARIANTS_ANNOTATION_SCHEMA"
    }

    def mtemplate = ""
    def genomes = [:]
    def stats = [:]
    def bwa_version = ""
    def variants_count = ""
    def healty_exomes_file = ""

    // check TEMPLATE, GENOME_NAMES, CUSTOM_CSS
    if (new File("$TEMPLATE").isFile())
    {
      mtemplate = new File("$TEMPLATE").text
    }
    else
    {
      fail "Can't open template file $TEMPLATE"
    }
    if (new File("$GENOME_NAMES").isFile())
    {
      new File("$GENOME_NAMES").eachLine() { line ->
        genomes[line.split("\t")[0]] = line.split("\t")[1]
      }
    }
    else
    {
      fail "Can't open genome names file $GENOME_NAMES"
    }
    if (!new File("$CUSTOM_CSS").isFile())
    {
      fail "Can't open custom css: $CUSTOM_CSS"
    }


    // check RATIONALE, STATS, PEDIGREE and SAMPLESHEET
    if (!new File("${report_data_dir}/${RATIONALE}").isFile())
    {
      fail "Can't open rationale file ${report_data_dir}/${RATIONALE}"
    }
    if (new File("${report_data_dir}/${STATS}").isFile())
    {
      try
      {
        stats = new ConfigSlurper().parse(new File("${report_data_dir}/${STATS}").toURL())
      }
      catch(Exception e)
      {
        fail "Failed to load stats from ${report_data_dir}/${STATS}. Error: $e"
      }
    }
    else
    {
      fail "Can't open stats file ${report_data_dir}/${STATS}"
    }
    if (with_pedigree && !new File("${report_data_dir}/${PEDIGREE}").isFile())
    {
      fail "Can't open pedigree file ${report_data_dir}/${PEDIGREE}"
    }
    if (!new File("${SAMPLESHEET}").isFile())
    {
      fail "Can't open samplesheet file ${SAMPLESHEET}"
    }

    // CHECK FOR bwa_version.txt, healty_exomes.txt and variants_count.txt
    if (new File("${report_data_dir}/bwa_version.txt").isFile())
    {
      bwa_version = new File("${report_data_dir}/bwa_version.txt").text.trim()
    }
    else
    {
      fail "Can't open bwa_version.txt file in $report_data_dir"
    }
    if (new File("${report_data_dir}/variants_count.txt").isFile())
    {
      variants_count = "${report_data_dir}/variants_count.txt"
    }
    else
    {
      fail "Can't open variants_count.txt file in $report_data_dir"
    }
    if (with_healty_exomes)
    {
      if (new File("${report_data_dir}/healty_exomes.txt").isFile())
      {
        healty_exomes_file = "${report_data_dir}/healty_exomes.txt"
      }
      else
      {
        fail "Can't open healty_exomes.txt file in $report_data_dir"
      }
    }

    // TEMPLATE BINDING
    def binding = [
      "PROJECTNAME"                : "$PROJECTNAME",
      "AUTHOR"                     : stats.AUTHOR,
      "SAMPLESHEET"                : "$SAMPLESHEET",
      "RATIONALE"                  : new File("${report_data_dir}/${RATIONALE}").text,
      "PEDIGREE"                   : "${report_data_dir}/${PEDIGREE}",
      "DISEASE_FREQUENCY"          : stats.DISEASE_FREQUENCY,
      "VARIANT_SELECTION_MODEL"    : stats.VARIANT_SELECTION_MODEL,
      "RECEIVED_SAMPLES"           : stats.RECEIVED_SAMPLES,
      "SEQUENCED_SAMPLES"          : stats.SEQUENCED_SAMPLES,
      "ILLUMINA"                   : stats.ILLUMINA,
      "ILLUMINA_PROTOCOL"          : stats.ILLUMINA_PROTOCOL,
      "KIT_VERSION"                : stats.KIT_VERSION,
      "GENOME"                     : genomes[REFERENCE].toString(),
      "RELEASE"                    : "$REFERENCE",
      "BWA_VERSION"                : bwa_version,
      "BAMSTATS"                   : "$input.bamstats",
      "HSMETRICS"                  : "$input.hsmetrics",
      "VCFCOVERAGE"                : "$input.vcfcoverage",
      "HEALTY_EXOMES"              : healty_exomes_file,
      "VARIANTS_COUNT"             : variants_count,
      "BAM_RECALIBRATION_SCHEMA"   : "${BAM_RECALIBRATION_SCHEMA}",
      "VARIANTS_CALLING_SCHEMA"    : "${VARIANTS_CALLING_SCHEMA}",
      "VARIANTS_ANNOTATION_SCHEMA" : "${VARIANTS_ANNOTATION_SCHEMA}"
    ]

    def engine = new SimpleTemplateEngine()
    def report = engine.createTemplate(mtemplate).make(binding)
    def report_rmd = new File("report.Rmd").write(report.toString())

    if (pretend)
    {
      println """
        INPUTS:  $inputs
        OUTPUTS: $outputs
        TEMPLATE: ${TEMPLATE}
        STATS: ${stats}
        GENOMES: ${genomes}
        BWA_VERSION: ${bwa_version}
        VARIANTS COUNT: $variants_count
        HEALTY_EXOMES_REPORT: $healty_exomes_file
        PROJECTNAME: $PROJECTNAME
        REFERENCE: $REFERENCE
      """
      exec "touch $output.html"
    }
    else
    {
      R {"""
        library(knitr)
        library(markdown)
        md <- knit("report.Rmd")
        markdownToHTML(md, "report.html", encoding=getOption("encoding"), stylesheet="$CUSTOM_CSS")
        sessionInfo()
      """}
    }
  }

}
