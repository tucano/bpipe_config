// MODULE MAKE_REKNIT_SCRIPT_GFU

@preserve
make_reknit_script_gfu =
{
  doc title: "Make a bpipe script to rebuld reports on cluster",
      desc: "...",
      constraints: "...",
      author: "davide.rambaldi@gmail.com"

  requires REKNIT_TEMPLATE : "Please define path of reknit.groovy"

  produce("reknit.groovy")
  {
    println "REBUILD KNITR REPORT WITH:\n\tbpipe run reknit.groovy report.Rmd"
    exec "cp $REKNIT_TEMPLATE $output"
  }
  forward input.Rmd
}
