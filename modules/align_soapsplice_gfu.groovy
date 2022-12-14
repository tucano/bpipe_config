// MODULE SOAPSPLICE ALIGN (rev1)

@intermediate
align_soapsplice_gfu =
{
    // SoapSplice VARS
    var SSPLICEOPT_ALN : "-p 4 -f 2 -q 1 -j 0"
    var pretend        : false
    var compression    : ""
    var paired         : true
    var sample_dir     : false
    var use_shm        : false

    doc title: "Align RNA reads with soapsplice",
        desc: """
            Align with soapsplice.
            Main options with value:
                pretend     : $pretend,
                paired      : $paired,
                compression : $compression
            soapsplice options: ${SSPLICEOPT_ALN}.
            With use_shm, generate temporary files in /dev/shm on the node.
            Merge the pregenerated header and the output sam file in a bam file sorted by coordinates.
        """,
        constraints: """
            Work with fastq and fastq.gz, single and paired files.
            For paired files assume the presence of _R1_ and _R2_ tags.
            Don't support MarkDuplicates stages.
        """,
        author: "davide.rambaldi@gmail.com"

    requires REFERENCE_GENOME: "Please define a REFERENCE_GENOME"
    requires SAMTOOLS: "Please define SAMTOOLS path"
    requires SSPLICE: "Please define SSPLICE path"
    requires FQZ_COMP: "Please define FQZ_COMP path"

    String input_extension = ""

    if (compression == "gz")
    {
        input_extension = '.fastq.gz'
    }
    else if (compression == "fqz")
    {
        input_extension = '.fqz'
    }
    else
    {
        input_extension = '.fastq'
    }

    String header_file = ""

    if (sample_dir)
    {
        output.dir = branch.sample
        header_file = "${branch.sample}/${input1.prefix.replaceAll(/.*\//,"")}" + '.header'
    }
    else
    {
        header_file = "${input1.prefix.replaceAll(/.*\//,"")}" + '.header'
    }

    if (paired)
    {
        def custom_output = "$input1".replaceAll(/.*\//,"").replaceFirst("_R[12]_","_") - input_extension + ".bam"

        from(input_extension,input_extension) produce(custom_output)
        {
            def r1 = ""
            def r2 = ""

            if (compression == "fqz")
            {
                r1 = "<( $FQZ_COMP -d $input1 )"
                r2 = "<( $FQZ_COMP -d $input2 )"
            }
            else
            {
                r1 = "$input1"
                r2 = "$input2"
            }

            def command = ""

            if (use_shm)
            {
                command = """
                    TMP_SCRATCH=\$(/bin/mktemp -d /dev/shm/${PROJECTNAME}.XXXXXXXXXXXXX);
                    ${ sample_dir ? "mkdir -p ${TMP_SCRATCH}/${input.replaceFirst("/.*","")};": ""}
                    TMP_OUTPUT_PREFIX=$TMP_SCRATCH/${output.bam.prefix};
                    $SSPLICE -d $REFERENCE_GENOME -1 $r1 -2 $r2 -o $TMP_OUTPUT_PREFIX $SSPLICEOPT_ALN;
                    cat $header_file ${TMP_OUTPUT_PREFIX}.sam | $SAMTOOLS view -Su - | $SAMTOOLS sort - $TMP_OUTPUT_PREFIX;
                    mv ${TMP_OUTPUT_PREFIX}.bam $custom_output;
                    for F in ${TMP_SCRATCH}/*.junc; do
                        if [[ -e $F ]]; then
                            mv $F .;
                        fi;
                    done;
                    rm -rf ${TMP_SCRATCH};
                """
            }
            else
            {
                command = """
                    $SSPLICE -d $REFERENCE_GENOME -1 $r1 -2 $r2 -o $output.bam.prefix $SSPLICEOPT_ALN;
                    cat $header_file ${output.bam.prefix}.sam | $SAMTOOLS view -Su - | $SAMTOOLS sort - $output.bam.prefix;
                    rm ${output.bam.prefix}.sam;
                """
            }

            if (pretend)
            {
                println """
                    HEADER:       $header_file
                    INPUT FASTQ:  $input1 $input2
                    OUTPUT:       $output
                    COMMAND:
                        $command
                """
                command = """
                    echo "INPUTS: $input1 $input2" > $output;
                """
            }

            exec command, "soapsplice"
        }
    }
    else
    {
        def custom_output = "$input".replaceAll(/.*\//,"") - input_extension + ".bam"

        from(input_extension) produce(custom_output)
        {
            def r1 = ""

            if (compression == "fqz")
            {
                 r1 = "<( $FQZ_COMP -d $input1 )"
            }
            else
            {
                r1 = "$input1"
            }

            def command = ""

            if (use_shm)
            {
                command = """
                    TMP_SCRATCH=\$(/bin/mktemp -d /dev/shm/${PROJECTNAME}.XXXXXXXXXXXXX);
                    TMP_OUTPUT_PREFIX=$TMP_SCRATCH/$output.prefix;
                    $SSPLICE -d $REFERENCE_GENOME -1 $r1 -o $TMP_OUTPUT_PREFIX $SSPLICEOPT_ALN;
                    cat ${header_file} ${TMP_OUTPUT_PREFIX}.sam | $SAMTOOLS view -Su - | $SAMTOOLS sort - $TMP_OUTPUT_PREFIX;
                    mv ${TMP_OUTPUT_PREFIX}.bam $output;
                    for F in ${TMP_SCRATCH}/*.junc; do
                        if [[ -e $F ]]; then
                            mv $F .;
                        fi;
                    done;
                    rm -rf ${TMP_SCRATCH};
                """
            }
            else
            {
                command = """
                    $SSPLICE -d $REFERENCE_GENOME -1 $r1 -o ${output.bam.prefix} $SSPLICEOPT_ALN;
                    cat ${header_file} ${output.bam.prefix}.sam |  $SAMTOOLS view -Su - | $SAMTOOLS sort - ${output.bam.prefix};
                    rm ${output.bam.prefix}.sam;
                """
            }

            if (pretend)
            {
                println """
                    HEADER:       $header_file
                    INPUT FASTQ:  $input1
                    OUTPUT:       $output
                    COMMAND:
                        $command
                """
                command = """
                    echo "INPUTS: $input1 $header_file" > $output;
                """
            }
            exec command, "soapsplice"
        }
    }
}
