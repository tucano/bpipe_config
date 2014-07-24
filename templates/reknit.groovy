CUSTOM_CSS = "/lustre1/tools/libexec/bpipeconfig/templates/reports/report.css"

reknit =
{
  requires CUSTOM_CSS : "Please define path of report.css"
  produce("report.html")
  {
    R{"""
      library(knitr)
      library(markdown)
      md <- knit("report.Rmd")
      markdownToHTML(md, "report.html", encoding=getOption("encoding"), stylesheet="$CUSTOM_CSS")
      sessionInfo()
    """}
  }
}

run {
  "%.Rmd" * [reknit]
}
