// MODULE VCF LIST SAMPLES
import static groovy.io.FileType.*

VCFUTILS="/usr/local/cluster/bin/vcfutils.pl"
SNPSIFT = "java -Xmx16g -jar /lustre1/tools/bin/SnpSift-3.5c.jar"

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
