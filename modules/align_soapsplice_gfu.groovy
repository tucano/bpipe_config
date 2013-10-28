// MODULE SOAPSPLICE ALIGN
SSPLICE="/lustre1/tools/bin/soapsplice"
SAMTOOLS="/usr/local/cluster/bin/samtools"

@intermediate
align_soapsplice_gfu =
{
    // var to define if the sample is paired or not
    var paired : true
    var SSPLICEOPT_ALN : "-p 4 -f 2 -q 1 -j 0"
    var compressed : true

    doc title: "GFU: Soapsplice alignment task",
        desc: "Align with soapsplice. Generate temporary files in /dev/shm on the node",
        constrains: "...",
        author: "davide.rambaldi@gmail.com"

    def input_extension = compressed ? '.fastq.gz' : '.fastq'

    if (paired) {
        def output = input.replaceFirst(/.*\//,"") - input_extension + ".bam"
        from(input_extension, input_extension) produce(output) {
            command = """
                TMP_SCRATCH=\$(/bin/mktemp -d /dev/shm/${PROJECTNAME}.XXXXXXXXXXXXX);
                TMP_OUTPUT_PREFIX=$TMP_SCRATCH/$output.prefix;
                echo -e "[align_soapsplice_gfu]: soapsplice alignment on node $HOSTNAME";
                echo -e "[align_soapsplice_gfu]: TMP_SCRATCH: $TMP_SCRATCH and header: ${input.header}" >&2;
                $SSPLICE -d $REFERENCE_GENOME -1 $input1 -2 $input2 -o $TMP_OUTPUT_PREFIX $SSPLICEOPT_ALN;
                cat ${input.header} ${TMP_OUTPUT_PREFIX}.sam | $SAMTOOLS view -Su - | $SAMTOOLS sort - $TMP_OUTPUT_PREFIX;
                mv ${TMP_OUTPUT_PREFIX}.bam $output;
                for F in ${TMP_SCRATCH}/*.junc; do
                    if [[ -e $F ]]; then
                        mv $F .;
                    fi;
                done;
                rm -rf ${TMP_SCRATCH};
            """
            if (test) {
                println "INPUTS:  $input1 and $input2, OUTPUT: $output"
                println "COMMAND:"
                println "$command"
                println "I create $output"
                exec "touch $output"
            } else {
                exec command.stripIndent().trim(), "soapsplice"
            }
        }
    } else {
        transform(input_extension) to('.bam') {
            command = """
                TMP_SCRATCH=\$(/bin/mktemp -d /dev/shm/${PROJECTNAME}.XXXXXXXXXXXXX);
                TMP_OUTPUT_PREFIX=$TMP_SCRATCH/$output.prefix;
                echo -e "[align_soapsplice_gfu]: soapsplice alignment on node $HOSTNAME";
                echo -e "[align_soapsplice_gfu]: TMP_SCRATCH: $TMP_SCRATCH and header: ${input.header}" >&2;
                $SSPLICE -d $REFERENCE_GENOME -1 $input -o $TMP_OUTPUT_PREFIX $SSPLICEOPT_ALN;
                cat ${input.header} ${TMP_OUTPUT_PREFIX}.sam | $SAMTOOLS view -Su - | $SAMTOOLS sort - $TMP_OUTPUT_PREFIX;
                mv ${TMP_OUTPUT_PREFIX}.bam $output.bam;
                for F in ${TMP_SCRATCH}/*.junc; do
                    if [[ -e $F ]]; then
                        mv $F .;
                    fi;
                done;
                rm -rf ${TMP_SCRATCH};
            """

            if (test) {
                println "INPUT:  $input, OUTPUT: $output"
                println "COMMAND:"
                println "$command"
                exec "touch $output"
            } else {
                exec command.stripIndent().trim(), "soapsplice"
            }
        }
    }
}
