load "../../../modules/rmdup_gfu.groovy"

// test support stage to forward correct outputs
forward_bam =
{
    output.dir = input.dir
    def outputs = []
    def dataDir = new File(input.dir)
    dataDir.eachFile { file ->
        if (file.getName().endsWith(".bam"))
        {
            outputs << file.getName()
        }
    }

    produce(outputs) {
        println "Forwarding bam files: $outputs"
    }
}

Bpipe.run {
	"%" * [
        forward_bam + "*.bam" * [rmdup_gfu.using(pretend:true,paired:true,sample_dir:true)]
    ]
}
