@intermediate
make_input_list_ctgb =
{
    doc title: "Generate bed intervals",
        desc: "",
        constraints: "",
        author: "davide.rambaldi@gmail.com"

    def input_files = []

    inputs.each() {
     input_files << file(it).canonicalPath
    }

    produce("input_bam_list.txt")
    {
     exec """
         echo "${input_files.join("\n")}" > $output.txt;
     """, false
    }
}
