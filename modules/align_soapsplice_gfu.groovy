// MODULE SOAPSPLICE ALIGN
SSPLICE="/lustre1/tools/bin/soapsplice"
SAMTOOLS="/usr/local/cluster/bin/samtools"

@intermediate
align_soapsplice_gfu =
{
    // SoapSplice VARS
    var SSPLICEOPT_ALN : "-p 4 -f 2 -q 1 -j 0"
    var pretend        : false
    var compressed     : true
    var paired         : true

    doc title: "Align RNA reads with soapsplice",
        desc: """
            Align with soapsplice.

            Main options with default value:
                pretend    : $pretend
                paired     : $paired
                compressed : $compressed

            soaplsplice options: ${SSPLICEOPT_ALN}.
            Generate temporary files in /dev/shm on the node.
            Merge the pregenerated header and the output sam file in a bam file sorted by coordinates.
        """,
        constraints: """
            Work with fastq and fastq.gz, single and paired files.
            For paired files assume the presence of _R1_ and _R2_ tags
        """,
        author: "davide.rambaldi@gmail.com"


    String input_extension = compressed ? '.fastq.gz' : '.fastq'

    if (paired) 
    {
        def custom_output = "$input1".replaceFirst("_R1_","_") - input_extension + ".bam"
        
        from(input_extension, input_extension, '.header') produce(custom_output) 
        {
            def command = """
                TMP_SCRATCH=\$(/bin/mktemp -d /dev/shm/${PROJECTNAME}.XXXXXXXXXXXXX);
                TMP_OUTPUT_PREFIX=$TMP_SCRATCH/$output.prefix;
                echo -e "[align_soapsplice_gfu]: soapsplice alignment on node $HOSTNAME";
                echo -e "[align_soapsplice_gfu]: TMP_SCRATCH: $TMP_SCRATCH and header: $input.header" >&2;
                $SSPLICE -d $REFERENCE_GENOME -1 $input1 -2 $input2 -o $TMP_OUTPUT_PREFIX $SSPLICEOPT_ALN;
                cat $input3 ${TMP_OUTPUT_PREFIX}.sam | $SAMTOOLS view -Su - | $SAMTOOLS sort - $TMP_OUTPUT_PREFIX;
                mv ${TMP_OUTPUT_PREFIX}.bam $custom_output;
                for F in ${TMP_SCRATCH}/*.junc; do
                    if [[ -e $F ]]; then
                        mv $F .;
                    fi;
                done;
                rm -rf ${TMP_SCRATCH};
            """
            if (pretend) 
            {
                println """
                    HEADER:       $input3
                    INPUT FASTQ:  $input1 $input2
                    OUTPUT:       $output
                    COMMAND:
                        $command
                """
                command = """
                    echo "INPUTS: $input1 $input2" > $output
                """
            }

            exec command, "soapsplice"
        }
    } 
    else 
    {
        from("$input_extension", '.header') produce(input.header.prefix + '.bam') 
        {
            def command = """
                TMP_SCRATCH=\$(/bin/mktemp -d /dev/shm/${PROJECTNAME}.XXXXXXXXXXXXX);
                TMP_OUTPUT_PREFIX=$TMP_SCRATCH/$output.prefix;
                $SSPLICE -d $REFERENCE_GENOME -1 ${input1} -o $TMP_OUTPUT_PREFIX $SSPLICEOPT_ALN;
                cat ${input2} ${TMP_OUTPUT_PREFIX}.sam | $SAMTOOLS view -Su - | $SAMTOOLS sort - $TMP_OUTPUT_PREFIX;
                mv ${TMP_OUTPUT_PREFIX}.bam $output;
                for F in ${TMP_SCRATCH}/*.junc; do
                    if [[ -e $F ]]; then
                        mv $F .;
                    fi;
                done;
                rm -rf ${TMP_SCRATCH};
            """

            if (pretend) 
            {
                println """
                    HEADER:       $input2
                    INPUT FASTQ:  $input1
                    OUTPUT:       $output
                    COMMAND:
                        $command
                """
                command = """
                    echo "INPUTS: $input1 $input2" > $output
                """
            }
            exec command, "soapsplice"
        }
    }
}
