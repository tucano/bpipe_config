// THIS MODULE STORE ALL THE SOFTWARE PATHS IN USE ON THE CLUSTER
BWA                = "/lustre1/tools/bin/bwa"
FASTQC             = "/lustre1/tools/bin/fastqc"
GATK               = "java -Djava.io.tmpdir=/lustre2/scratch/ -Xmx32g -jar /lustre1/tools/bin/GenomeAnalysisTK.jar"
HTSEQ_COUNT        = "/usr/local/cluster/python2.7/bin/python2.7 /usr/local/cluster/python2.7/bin/htseq-count"
HSMETRICS          = "java -jar /lustre1/tools/bin/CalculateHsMetrics.jar"
LSF                = "/usr/bin/lfs"
MACS               = "/usr/local/cluster/python2.7/bin/macs2"
MARKDUP            = "/lustre1/tools/bin/MarkDuplicates.jar"
MARKVCF            = "/usr/local/cluster/python2.7/bin/python2.7 /lustre1/tools/bin/markvcf.py"
PICMERGE           = "/usr/local/cluster/bin/MergeSamFiles.jar"
SAMTOOLS           = "/usr/local/cluster/bin/samtools"
SSPLICE            = "/lustre1/tools/bin/soapsplice"
SNPEFF             = "java -jar /lustre1/tools/bin/snpEff.jar"
SNPSIFT            = "java -jar /lustre1/tools/bin/SnpSift-3.5c.jar"
VCFCONCAT          = "export PERL5LIB=/lustre1/tools/libexec/vcftools_0.1.9/perl/ && /usr/local/cluster/bin/vcf-concat"
VCFSORT            = "/usr/local/cluster/bin/vcf-sort-mod -t /lustre2/scratch"
VCFUTILS           = "/usr/local/cluster/bin/vcfutils.pl"
VCF2XLS            = "PYTHONPATH=/lustre1/tools/lib/python2.7/site-packages && /usr/local/cluster/python2.7/bin/python2.7 /lustre1/tools/bin/vcf2xls.py"
XHMM               = "/lustre1/tools/bin/xhmm"
XHMM_FIX_VCF       = "PYTHONPATH=/lustre1/tools/lib/python2.7/site-packages && /usr/local/cluster/python2.7/bin/python2.7 /lustre1/tools/bin/xhmm_fix_vcf.py"
FQZ_COMP           = "/lustre1/tools/bin/fqz_comp"

// VCF QUERY
VCFQUERY="""
    export PERL5LIB=/lustre1/tools/libexec/vcftools_0.1.9/perl/ &&
    /usr/local/cluster/bin/vcf-query
""".stripIndent().trim()

// RSEQC PATHS (USED BY RSEQC ONLY)
BAMSTAT="""
     export PYTHONPATH=/usr/local/cluster/python/usr/lib64/python2.6/site-packages/:\$PYTHONPATH &&
     /usr/local/cluster/python2.7/bin/python2.7 /usr/local/cluster/python/usr/bin/bam_stat.py
""".stripIndent().trim()

GENECOVERAGE="""
    export PYTHONPATH=/usr/local/cluster/python/usr/lib64/python2.6/site-packages/:\$PYTHONPATH &&
    /usr/local/cluster/python2.7/bin/python2.7 /usr/local/cluster/python/usr/bin/geneBody_coverage.py
""".stripIndent().trim()

READGC = """
    export PYTHONPATH=/usr/local/cluster/python/usr/lib64/python2.6/site-packages/:\$PYTHONPATH &&
    /usr/local/cluster/python2.7/bin/python2.7 /usr/local/cluster/python/usr/bin/read_GC.py
""".stripIndent().trim()

READNVC = """
    export PYTHONPATH=/usr/local/cluster/python/usr/lib64/python2.6/site-packages/:\$PYTHONPATH &&
    /usr/local/cluster/python2.7/bin/python2.7 /usr/local/cluster/python/usr/bin/read_NVC.py
""".stripIndent().trim()

READQUALITY = """
    export PYTHONPATH=/usr/local/cluster/python/usr/lib64/python2.6/site-packages/:\$PYTHONPATH &&
    /usr/local/cluster/python2.7/bin/python2.7 /usr/local/cluster/python/usr/bin/read_quality.py
""".stripIndent().trim()

READS_DISTRIBUTION = """
    export PYTHONPATH=/usr/local/cluster/python/usr/lib64/python2.6/site-packages/:\$PYTHONPATH &&
    /usr/local/cluster/python2.7/bin/python2.7 /usr/local/cluster/python/usr/bin/read_distribution.py
""".stripIndent().trim()
