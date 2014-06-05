about title: "A test pipeline"

// Usage line will be used to infer the correct bpipe command
// USAGE: bpipe run -R softwareschedule $pipeline_filename

REFERENCE_GENOME = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/bwa/BPIPE_REFERENCE_GENOME"
PLATFORM         = "illumina"
CENTER           = "CTGB"
ENVIRONMENT_FILE = "gfu_environment.sh"

//--BPIPE_ENVIRONMENT_HERE--


// STAGE HELLO
hello = {
    exec"""
        echo -e "Hello" > $output
    """
}

// STAGE WORLD
world = {
    exec"""
        echo -e "World" > $output
    """
}

// RUNNER
Bpipe.run {
    hello + world
}
