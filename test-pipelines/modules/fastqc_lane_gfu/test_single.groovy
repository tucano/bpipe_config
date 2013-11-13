load "../../../modules/fastqc_lane_gfu.groovy"

Bpipe.run {
	"*.fastq.gz" * [fastqc_lane_gfu.using(test:true, paired:false)]
}
