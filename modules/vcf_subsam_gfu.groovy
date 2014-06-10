// MODULE VCF LIST SAMPLES (rev1)
import static groovy.io.FileType.*

@intermediate
vcf_subsam_gfu =
{
    var pretend : false

    doc title: "Use vcfutils.pl to extract the samples from the VCF (removing healty exomes)",
        desc: """
            Main options with value:
                pretend    : $pretend
        """,
        constraints: "List the healty exomes from the directory $HEALTY_EXOMES_DIR",
        author: "davide.rambaldi@gmail.com"

    def healty_samples = []

    def required_binds = ["HEALTY_EXOMES_DIR","VCFUTILS","SNPSIFT"]
    def to_fail = false
    required_binds.each { key ->
        if (!binding.variables.containsKey(key))
        {
            to_fail = true
            println """
                This stage require this variable: $key, add this to the groovy file:
                    $key = "VALUE"
            """.stripIndent()
        }
    }
    if (to_fail) { System.exit(1) }

    new File("$HEALTY_EXOMES_DIR").eachFileMatch FILES, ~/.*\.bam/, { bam ->
        healty_samples << bam.getName().replaceAll(/\..*/,"")
    }

    filter("filtered")
    {
        new File("healty_samples.txt").withWriter { out ->
            out.write healty_samples.sort().join("\n")
        }

        def command = """
            $VCFUTILS listsam $input.vcf | sort > all_samples.txt;
            comm  -23 all_samples.txt healty_samples.txt > samples.txt;
            $VCFUTILS subsam $input.vcf `cat samples.txt | tr '\\n' ' '` | $SNPSIFT filter "countVariant()>0" > $output.vcf;
        """

        if (pretend)
        {
            println """
                INPUTS $input
                OUTPUT: $output
                COMMAND: $command
            """

            command = """
                echo "INPUT: $input" > $output
            """
        }
        exec command

    }
}
