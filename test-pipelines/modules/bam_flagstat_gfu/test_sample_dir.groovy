load "../../../modules/bam_flagstat_gfu.groovy"

REFERENCE_GENOME="/test/reference/pippo.fa"
EXPERIMENT_NAME="test"
PLATFORM="test"
FCID="1"
EXPERIMENT_NAME="test1"
SAMPLEID="S1"
CENTER="GFU"
PROJECTNAME="TEST_1_TEST"

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
        forward_bam + "*.bam" * [bam_flagstat_gfu.using(pretend:true,sample_dir:true)]
    ]
}
