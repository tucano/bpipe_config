@intermediate
generate_bed_intervals_ctgb =
{
  var chr_names : true

  requires INTERVALS_BED: "Please define an INTERVALS_BED file"

  produce("${chr}_targets.bed")
  {
    def grep_string = chr_names ? chr : chr.replaceAll("chr","")

    exec """
      grep -P "^${grep_string}\t" $INTERVALS_BED > $output.bed;
    """
  }
}
