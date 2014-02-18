about title: "RNA project alignment with soapsplice: IOS GFU 009."

// Usage line will be used to infer the correct bpipe command
// USAGE: bpipe run -r $pipeline_filename <INPUT_DIRS>

// PROJECT VARS will be added by bpipe-config
// I don't wanna templates for a groovy file. Use simple regexp with PLACEHOLDERS
// Don't change my keywords in source pipeline file!

REFERENCE_GENOME = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/SOAPsplice/BPIPE_REFERENCE_GENOME.index"
REFERENCE_FAIDX  = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/fa/BPIPE_REFERENCE_GENOME.fa.fai"
PLATFORM         = "illumina"
CENTER           = "CTGB"
ENVIRONMENT_FILE = "gfu_environment.sh"

//--BPIPE_ENVIRONMENT_HERE--

/*
 * PIPELINE NOTES:
 *
 */
Bpipe.run
{
    "%" * [
        sample_dir_gfu +
        "%.fastq.gz" * [ soapsplice_prepare_headers_gfu.using(sample_dir:true) ] +
        "_R*_%.fastq.gz" * [align_soapsplice_gfu.using(paired:true,sample_dir:true,compressed:true,SSPLICEOPT_ALN="-p 4 -f 2 -q 1 -j 0")] +
        "*.bam" * [merge_bam_gfu.using(rename:false,sample_dir:true)] +
        verify_bam_gfu.using(sample_dir:true) + bam_flagstat_gfu.using(sample_dir:true)
    ] + "%.bam" * [move_sample_results.using(result_dir:"BAM")]
}
