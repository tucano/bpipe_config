// MODULE CLEAN ZIP GFU

@preserve
clean_fastqc_dir_gfu =
{
    doc title: "Clean a fastqc report dir (clean the $inputs for forward)",
        desc: "...",
        constraints: "...",
        author: "davide.rambaldi@gmail.com"

    def fastqc_dir = new File("$input").getParent()
    output.dir = fastqc_dir
    exec "rm $inputs"

    // to avoid fail of stage I fake produce and already present file
    produce("SampleSheet.csv")
    {

    }
}
