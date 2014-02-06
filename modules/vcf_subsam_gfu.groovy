// MODULE VCF LIST SAMPLES
import static groovy.io.FileType.*

VCFUTILS="/usr/local/cluster/bin/vcfutils.pl"
SNPSIFT = "java -Xmx16g -jar /lustre1/tools/bin/SnpSift.jar"

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
        /*
         * quick fix:
         * some bam files in $HEALTY_EXOMES_DIR have a filename different form the sample name.
         * Example: C1VVVACXX_AMP5.bam is sample AMP5 in vcf file!
         * I remove everything before the underscore (_)
         */
        healty_samples << bam.getName().replaceAll(/\..*/,"").replaceAll(/.*_/,"")
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
