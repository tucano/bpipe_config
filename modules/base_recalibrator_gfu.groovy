// MODULE BASE RECALIBRATOR GFU
import static groovy.io.FileType.*

@intermediate
base_recalibrator_gfu =
{
    // stage vars
    var pretend          : false
    var nct              : 4
    var healty_exomes    : false
    var target_intervals : false

    doc title: "Base recalibration with GATK",
        desc: """
            Base recalibration with GATK tool: BaseRecalibrator.
            Inputs: ${inputs.bam}.
            Forward grp output file to next stage.

            Main options with value:
            pretend                : $pretend
            With target_intervals  : $target_intervals
            INTERVALS              : $INTERVALS
            REFERENCE_GENOME_FASTA : $REFERENCE_GENOME_FASTA
            DBSNP                  : $DBSNP
            nct                    : $nct
            healty_exomes          : $healty_exomes
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

    def healty_exomes_input_string = new StringBuffer()

    if (healty_exomes)
    {
        new File("$HEALTY_EXOMES_DIR").eachFileMatch FILES, ~/.*\.bam/, { bam ->
            healty_exomes_input_string << "-I $bam "
        }
    }

    produce(outputs)
    {
        def intervals_string = target_intervals ? "-L $INTERVALS" : ""
        def command = """
            ulimit -l unlimited;
            ulimit -s unlimited;
            $GATK -R $REFERENCE_GENOME_FASTA
                  -knownSites $DBSNP
                  ${inputs.bam.collect{ "-I $it" }.join(" ")} ${healty_exomes_input_string}
                  -T BaseRecalibrator $intervals_string
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
                INPUT BAM:  $inputs
                OUTPUT:     $output
                COMMAND:
                    $command
            """
            command = """
                echo "INPUTS: ${inputs.bam}" > $output.grp;
            """
        }
        exec command, "gatk"
    }
}
