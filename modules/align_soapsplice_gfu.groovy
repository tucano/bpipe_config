// MODULE SOAPSPLICE ALIGN
SSPLICE="/lustre1/tools/bin/soapsplice"
SAMTOOLS="/usr/local/cluster/bin/samtools"

@intermediate
align_soapsplice_gfu =
{
    // var to define if the sample is paired or not
    var paired : true
    var SSPLICEOPT_ALN : "-p 4 -f 2 -q 1 -j 0"

    doc title: "GFU: Soapsplice alignment task",
        desc: "Align with soapsplice. Generate temporary files in /dev/shm on the node",
        constrains: "...",
        author: "davide.rambaldi@gmail.com"

    def input_extension = input.endsWith(".gz") ? '.fastq.gz' : '.fastq'
    def command = ""

    transform(input_extension) to('.bam') {
        if (paired) {
            command = """
                echo -e "[align_soapsplice_gfu]: soapsplice alignment (pair) on node $HOSTNAME with TMP_SCRATCH: $TMP_SCRATCH and header: ${input.prefix}.header" >&2;
            """
            println "INPUT1:  $input1"
            println "INPUT2:  $input2"
            println "OUTPUT: $output"
            println "COMMAND:\n$command"
        } else {
            command = """
                echo -e "[align_soapsplice_gfu]: soapsplice alignment (single) on node $HOSTNAME with TMP_SCRATCH: $TMP_SCRATCH and header: ${input.prefix}.header" >&2;
            """
            println "INPUT:  $input"
            println "OUTPUT: $output"
            println "COMMAND:\n$command"
        }
        exec "touch $output"
    }

    // if (input.endsWith(".gz")) {
    //     // PAIRED and COMPRESSED
    //     if (paired) {
    //         from("*.fastq.gz","*.fastq.gz") produce(input.prefix - ".fastq" + ".bam")
    //         {
    //             exec"""
    //                 TMP_SCRATCH=\$(/bin/mktemp -d /dev/shm/${PROJECTNAME}.XXXXXXXXXXXXX);
    //                 TMP_OUTPUT_PREFIX=$TMP_SCRATCH/$output.prefix;
    //                 echo -e "[align_soapsplice_gfu]: soapsplice alignment (pair) on node $HOSTNAME with TMP_SCRATCH: $TMP_SCRATCH and header: ${input.prefix}.header" >&2;
    //                 $SSPLICE -d $REFERENCE_GENOME -1 $input1.gz -2 $input2.gz -o $TMP_OUTPUT_PREFIX $SSPLICEOPT_ALN;
    //                 cat ${input1.prefix}.header ${TMP_OUTPUT_PREFIX}.sam | $SAMTOOLS view -Su - | $SAMTOOLS sort - $TMP_OUTPUT_PREFIX;
    //                 mv ${TMP_OUTPUT_PREFIX}.bam $output.bam;
    //                 for F in ${TMP_SCRATCH}/*.junc; do
    //                     if [[ -e $F ]]; then
    //                         mv $F .;
    //                     fi;
    //                 done;
    //                 rm -rf ${TMP_SCRATCH};
    //             ""","soapsplice"
    //         }
    //     // SINGLE and COMPRESSED
    //     } else {
    //         from("*.fastq.gz") produce(input.prefix - ".fastq" + ".bam")
    //         {
    //             exec"""
    //                 TMP_SCRATCH=\$(/bin/mktemp -d /dev/shm/${PROJECTNAME}.XXXXXXXXXXXXX);
    //                 TMP_OUTPUT_PREFIX=$TMP_SCRATCH/$output.prefix;
    //                 echo -e "[align_soapsplice_gfu]: soapsplice alignment (single) on node $HOSTNAME with TMP_SCRATCH: $TMP_SCRATCH and header: ${input.prefix}.header" >&2;
    //                 $SSPLICE -d $REFERENCE_GENOME -1 $input.gz -o $TMP_OUTPUT_PREFIX $SSPLICEOPT_ALN;
    //                 cat ${input.prefix}.header ${TMP_OUTPUT_PREFIX}.sam | $SAMTOOLS view -Su - | $SAMTOOLS sort - $TMP_OUTPUT_PREFIX;
    //                 mv ${TMP_OUTPUT_PREFIX}.bam $output.bam;
    //                 for F in ${TMP_SCRATCH}/*.junc; do
    //                     if [[ -e $F ]]; then
    //                         mv $F .;
    //                     fi;
    //                 done;
    //                 rm -rf ${TMP_SCRATCH};
    //             ""","soapsplice"
    //         }
    //     }
    // // INPUT IS NOT COMPRESSED
    // } else {
    //     // PAIRED
    //     if (paired) {
    //         from("*.fastq","*.fastq") produce(input.prefix - ".fastq" + ".bam")
    //         {
    //             exec"""
    //                 TMP_SCRATCH=\$(/bin/mktemp -d /dev/shm/${PROJECTNAME}.XXXXXXXXXXXXX);
    //                 TMP_OUTPUT_PREFIX=$TMP_SCRATCH/$output.prefix;
    //                 echo -e "[align_soapsplice_gfu]: soapsplice alignment (pair) on node $HOSTNAME with TMP_SCRATCH: $TMP_SCRATCH and header: ${input.prefix}.header" >&2;
    //                 $SSPLICE -d $REFERENCE_GENOME -1 $input1.fastq -2 $input2.fastq -o $TMP_OUTPUT_PREFIX $SSPLICEOPT_ALN;
    //                 cat ${input1.prefix}.header ${TMP_OUTPUT_PREFIX}.sam | $SAMTOOLS view -Su - | $SAMTOOLS sort - $TMP_OUTPUT_PREFIX;
    //                 mv ${TMP_OUTPUT_PREFIX}.bam $output.bam;
    //                 for F in ${TMP_SCRATCH}/*.junc; do
    //                     if [[ -e $F ]]; then
    //                         mv $F .;
    //                     fi;
    //                 done;
    //                 rm -rf ${TMP_SCRATCH};
    //             ""","soapsplice"
    //         }
    //     // SINGLE
    //     } else {
    //         from("*.fastq") produce(input.prefix - ".fastq" + ".bam")
    //         {
    //             exec"""
    //                 TMP_SCRATCH=\$(/bin/mktemp -d /dev/shm/${PROJECTNAME}.XXXXXXXXXXXXX);
    //                 TMP_OUTPUT_PREFIX=$TMP_SCRATCH/$output.prefix;
    //                 echo -e "[align_soapsplice_gfu]: soapsplice alignment (single) on node $HOSTNAME with TMP_SCRATCH: $TMP_SCRATCH and header: ${input.prefix}.header" >&2;
    //                 $SSPLICE -d $REFERENCE_GENOME -1 $input.fastq -o $TMP_OUTPUT_PREFIX $SSPLICEOPT_ALN;
    //                 cat ${input.prefix}.header ${TMP_OUTPUT_PREFIX}.sam | $SAMTOOLS view -Su - | $SAMTOOLS sort - $TMP_OUTPUT_PREFIX;
    //                 mv ${TMP_OUTPUT_PREFIX}.bam $output.bam;
    //                 for F in ${TMP_SCRATCH}/*.junc; do
    //                     if [[ -e $F ]]; then
    //                         mv $F .;
    //                     fi;
    //                 done;
    //                 rm -rf ${TMP_SCRATCH};
    //             ""","soapsplice"
    //         }
    //     }
    // }
}
