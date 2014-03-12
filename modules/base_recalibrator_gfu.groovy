// MODULE BASE RECALIBRATOR GFU
GATK="java -Djava.io.tmpdir=/lustre2/scratch/ -Xmx32g -jar /lustre1/tools/bin/GenomeAnalysisTK.jar"

@intermediate
base_recalibrator_gfu =
{
    // stage vars
    var pretend          : false
    var nct              : 4

    doc title: "Base recalibration with GATK",
        desc: """
            Base recalibration with GATK tool: BaseRecalibrator.
            Inputs: ${inputs.bam}.
            Forward grp output file to next stage.

            Main options with value:
            pretend                : $pretend
            INTERVALS              : $INTERVALS
            REFERENCE_GENOME_FASTA : $REFERENCE_GENOME_FASTA
            DBSNP                  : $DBSNP
            nct                    : $nct
        """,
        constraints: """
            For bam recalibration you should use all the bam files in your Project.
            For multiple bams, output grp file will be renamed using project name: ${PROJECTNAME}.grp.
        """,
        author: "davide.rambaldi@gmail.com"

    def outputs
    if (inputs.toList().size > 1)
    {
        outputs = ["${PROJECTNAME}.grp"]
    }
    else
    {
        outputs = ["${input.prefix}.grp"]
    }

    produce(outputs)
    {
        def command = """
            ulimit -l unlimited;
            ulimit -s unlimited;
            $GATK -R $REFERENCE_GENOME_FASTA
                  -knownSites $DBSNP
                  ${inputs.bam.collect{ "-I $it" }.join(" ")}
                  -L $INTERVALS
                  -T BaseRecalibrator
                  --covariate QualityScoreCovariate
                  --covariate CycleCovariate
                  --covariate ContextCovariate
                  --covariate ReadGroupCovariate
                  --unsafe ALLOW_SEQ_DICT_INCOMPATIBILITY
                  -nct $nct
                  -o $output.grp
        """

        if (pretend)
        {
            println """
                INPUT BAM:  $input.bam
                OUTPUT:     $output
                COMMAND:
                    $command
            """
            command = """
                echo "INPUTS: ${inputs.bam}" > $output.grp;
            """
        }

        exec command, "gatk"
        forward output.grp
    }
}
