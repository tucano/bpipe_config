// THIS MODULE STORE ALL THE SOFTWARE PATHS IN USE ON THE CLUSTER (rev1)

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
SNPEFF             = "java -jar /usr/local/cluster/src/snpEff/snpEff.jar"
SNPSIFT            = "java -jar /usr/local/cluster/src/snpEff/SnpSift.jar"
VCFCONCAT          = "/lustre1/tools/bin/vcfcat"
VCFSORT            = "/lustre1/tools/bin/vcfsort"
VCFUTILS           = "/usr/local/cluster/bin/vcfutils.pl"
VCF2XLS            = "PYTHONPATH=/lustre1/tools/lib/python2.7/site-packages && /usr/local/cluster/python2.7/bin/python2.7 /lustre1/tools/bin/vcf2xls.py"
XHMM               = "/lustre1/tools/bin/xhmm"
XHMM_FIX_VCF       = "PYTHONPATH=/lustre1/tools/lib/python2.7/site-packages && /usr/local/cluster/python2.7/bin/python2.7 /lustre1/tools/bin/xhmm_fix_vcf.py"
FQZ_COMP           = "/lustre1/tools/bin/fqz_comp"
MAQ                = "/lustre1/tools/bin/maq"
TRIMMOMATIC        = "java -jar /lustre1/tools/libexec/Trimmomatic-0.32/trimmomatic-0.32.jar"
BAMTOOLS           = "/usr/local/cluster/bin/bamtools"
BEDTOOLS           = "/lustre1/tools/bin/bedtools"

// VCF QUERY
VCFQUERY="""
    export PERL5LIB=/lustre1/tools/libexec/vcftools_0.1.9/perl/ &&
    /usr/local/cluster/bin/vcf-query
""".stripIndent().trim()

// GENOTYPE FREQUENCIES
GENOTYPE_FREQUENCY = """
    PYTHONPATH=/lustre1/tools/lib/python2.7/site-packages && /usr/local/cluster/python2.7/bin/python2.7 /lustre1/tools/bin/calculate_genotype_frequency.py
""".stripIndent().trim()

// RSEQC PATHS
BAMSTAT      = "/usr/local/cluster/python2.7/bin/bam_stat.py"
GENECOVERAGE = "/usr/local/cluster/python2.7/bin/geneBody_coverage.py"
READGC       = "/usr/local/cluster/python2.7/bin/read_GC.py"
READNVC      = "/usr/local/cluster/python2.7/bin/read_NVC.py"
READQUALITY  = "/usr/local/cluster/python2.7/bin/read_quality.py"
READS_DISTRIBUTION = "/usr/local/cluster/python2.7/bin/read_distribution.py"
