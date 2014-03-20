load "../../../modules/make_report_dir_gfu.groovy"

REFERENCE_GENOME="/test/reference/pippo.fa"
EXPERIMENT_NAME="test"
PLATFORM="test"
FCID="1"
EXPERIMENT_NAME="test1"
SAMPLEID="S1"
CENTER="GFU"
PROJECTNAME="TEST_1_TEST"

present_fastqgz =
{
    println "INPUT DIR $input.dir"
    def dataDir = new File(input.dir)
    def outputs = []
    output.dir = input.dir
    dataDir.eachFile { file ->
        if (file.getName().endsWith(".fastq.gz"))
        {
            outputs << file.getName()
        }
    }
    produce(outputs)
    {
        exec "echo 'FORWARDING fastq gz files'"
    }
}

Bpipe.run {
    "%" * [ present_fastqgz + make_report_dir_gfu ]
}
