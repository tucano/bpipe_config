load "../../../modules/sample_dir_gfu.groovy"
load "../../../modules/default_paths_gfu.groovy"

REFERENCE_GENOME="/test/reference/pippo.fa"
EXPERIMENT_NAME="test"
PLATFORM="test"
FCID="1"
EXPERIMENT_NAME="test1"
SAMPLEID="S1"
CENTER="GFU"
PROJECTNAME="TEST_1_TEST"

// USE JSON INPUT FILE
import groovy.json.JsonSlurper
branches = new JsonSlurper().parseText(new File(args[0]).text)

Bpipe.run {
    branches * [ sample_dir_gfu ]
}
