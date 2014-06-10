// MODULE GATK DEPTH OF COVERAGE (rev1)

@preserve
gatk_detpth_of_coverage_gfu =
{
    var pretend                : false
    var downsampling_type      : "BY_SAMPLE"
    var downsample_to_coverage : 5000
    var min_base_quality       : 0
    var min_mapping_quality    : 20
    var start                  : 1
    var stop                   : 5000
    var nbins                  : 200
    var count_type             : "COUNT_FRAGMENTS"

    doc title: "GATK: DepthOfCoverage",
        desc: """
            Assess sequence coverage by a wide array of metrics, partitioned by sample, read group, or library.
            CONFIGURATION:
                Reference              = $REFERENCE_GENOME_FASTA
                Inputs                 = $inputs.bam
                downsampling_type      = $downsampling_type
                downsample_to_coverage = $downsample_to_coverage
                minBaseQuality         = $min_base_quality
                minMappingQuality      = $min_mapping_quality
                start                  = $start
                stop                   = $stop
                nBins                  = $nbins
                countType              = $count_type
        """,
        constraints: """
            produce tables pertaining to different coverage summaries. Suffix on the table files declares the contents
        """,
        author: "davide.rambaldi@gmail.com"

    requires REFERENCE_GENOME_FASTA: "Please define a REFERENCE_GENOME_FASTA"
    requires GATK: "Please define GATK path"
    requires INTERVALS: "Please define an INTERVALS file"

    def outputs = []

    // SINGLE OR MULTIPLE BAM IN INPUTS?
    def input_string = new StringBuffer()
    if (inputs.bam.toList().size == 1)
    {
        input_string = "-I $input.bam"
        outputs = [
            ("${input.prefix}.DATA.sample_summary"),
            ("${input.prefix}.DATA.sample_statistics"),
            ("${input.prefix}.DATA.sample_interval_statistics"),
            ("${input.prefix}.DATA.sample_interval_summary")
        ]
    }
    else
    {
        def bam_list_filename = "input_bams.${branch}.list"
        def bam_list = new File(bam_list_filename)
        outputs = [
            ("all_samples.DATA.sample_summary"),
            ("all_samples.DATA.sample_statistics"),
            ("all_samples.DATA.sample_interval_statistics"),
            ("all_samples.DATA.sample_interval_summary"),
            bam_list_filename
        ]
        inputs.bam.each { bam ->
            bam_list << bam << "\n"
        }
        input_string = "-I $bam_list_filename"
    }

    produce(outputs)
    {
        def command = """
            $GATK -T DepthOfCoverage $input_string -L $INTERVALS -R $REFERENCE_GENOME_FASTA
            -dt $downsampling_type -dcov $downsample_to_coverage -l INFO --omitDepthOutputAtEachBase --omitLocusTable
            --minBaseQuality $min_base_quality --minMappingQuality $min_mapping_quality --start $start --stop $stop --nBins $nbins
            --includeRefNSites --countType $count_type -o ${input.prefix}.DATA
        """
        if (pretend)
        {
            println """
                COMMAND: $command
                INPUTS: ${inputs.bam}
                OUTPUTS: ${outputs.join(" ")}
            """.stripIndent()
            command = "touch ${outputs.join(" ")}"
        }

        exec "$command","gatk"
    }
}
