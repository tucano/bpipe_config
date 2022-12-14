# bpipe-config version 1.0

---
bpip-config is a configuration and reporting software for [bpipe](https://code.google.com/p/bpipe/).

bpipe provides a platform for running big bioinformatics jobs that consist of a series of processing stages - known as 'pipelines', see also the [bpipe documentation](https://code.google.com/p/bpipe/wiki/Reference)

---

## Table of Contents

* [INSTALL](https://bitbucket.org/drambaldi/bpipe_config#markdown-header-install)
* [USAGE](https://bitbucket.org/drambaldi/bpipe_config#markdown-header-usage)
* [OPTIONS](https://bitbucket.org/drambaldi/bpipe_config#markdown-header-options)
* [COMMANDS](https://bitbucket.org/drambaldi/bpipe_config#markdown-header-commands)
* [TUTORIALS](https://bitbucket.org/drambaldi/bpipe_config#markdown-header-tutorials)
	* [SINGLE DIR](https://bitbucket.org/drambaldi/bpipe_config#markdown-header-single-dir)
	* [ILLUMINA PROJECTS](https://bitbucket.org/drambaldi/bpipe_config#markdown-header-illumina-projects)
	* [REPORTS](https://bitbucket.org/drambaldi/bpipe_config#markdown-header-reports)
	* [MULTIPLE DIRS](https://bitbucket.org/drambaldi/bpipe_config#markdown-header-multiple-dirs) 
* [DEVELOPER NOTES](https://bitbucket.org/drambaldi/bpipe_config#markdown-header-developer-notes)

--- 

## INSTALL


##### AUTOMATIC

How to install and use bpipe and bpipe-config:

```
#!bash
./lustre1/tools/libexec/bpipeconfig/misc/install.sh
source ~/.bash_profile
```

This will add some environment vars to your __.bash_profile__ file


##### MANUAL

You need this in your enviroment (automatically installed in your .bash_profile with the install.sh script):

```
#!bash
export BPIPE_HOME=/lustre1/tools/libexec/bpipe
export BPIPE_CONFIG_HOME=/lustre1/tools/libexec/bpipeconfig
export BPIPE_LIB=$BPIPE_CONFIG_HOME/modules
export PATH=$BPIPE_CONFIG_HOME/bin:$BPIPE_HOME/bin:$PATH
```


## USAGE


Usage:

```
#!bash
bpipe-config [options] [command] [pipeline_name] [sample_dirs|project_dirs]
```

#### List pipelines

Print a list of available pipelines:

```
#!bash
bpipe-config -p
```

#### List commands

Print a list of available commands:

```
#!bash
bpipe-config -c
```

#### Generate a pipeline

Generate a pipeline in local directory:

```
#!bash
bpipe-config pipe <pipeline-name>
```

#### Generate a "Project" pipeline

Generate a multi-sample project pipeline that works with illumina files structure:

```
#!bash
bpipe-config pipe <project-pipeline-name> /lustre2/raw_data/Project/Sample_*
```

#### Generate multiple project pipeline

Generate a multi-sample project pipeline that works with illumina files structure for MULTIPLE PROJECTS:

```
#!bash
bpipe-config project <pipeline-name> <Project1> <Project2>
```

#### Generate a pipeline and run it in batch (-b)

Generate a pipeline and launch it with bpipe in a single command:

```
#!bash
bpipe-config -b pipe <pipeline-name>
```

#### Force mode to overwrite local files (-f)

Generate a pipeline forcing overwrite of local files.

```
#!bash
bpipe-config -f pipe <pipeline-name>
```

#### Merge samplesheets from different projects 

```
#!bash
bpipe-config smerge <Project1> <Project2> ...
```

## OPTIONS

```
#!bash
Available options:
 -b,--batch           Automatically execute bpipe in background (bg-bpipe)
 -c,--commands        Print a list of available commands
 -f,--force           Force files overwrite when needed (default=FALSE).
 -h,--help            Usage Information
 -m,--email <arg>     User email address (Es: -m user@example.com)
 -P,--project <arg>   Override the project name. If not provided will be extracted from SampleSheet in current
                      directory. Format: <PI_name>_<ProjectID>_<ProjectName>
 -p,--pipelines       Print a list of available pipelines
 -s,--skip-sheet      Skip SampleSheet checks
 -v,--verbose         Verbose mode
```

## COMMANDS

```
#!bash
Available Commands:
config    [dir1] [dir2] ...                       Configure current directory or directories in list (add
                                                  bpipe.config file).
pipe      <pipeline name> [dir1] [dir2] ...       Generate pipeline file in current directory or directories
                                                  in list (pipeline.groovy)
project   <pipeline name> [dir1] [dir2] ...       Generate a project pipeline for each directory in list
                                                  (pipeline.groovy)
sheet     <INFO> [dir1] [dir2] ...                Generate a SampleSheet.csv file using the INFO string in
                                                  current directory or directories in list. SampleProject
                                                  format: <PI_name>_<ProjectID>_<ProjectName>
info      <pipeline name> ...                     Get info on pipeline stages.
clean     [dir1] [dir2] ...                       Clean .bpipe dir in current working directory or in
                                                  directory list.
jvm                                               Get info on the JVM configuration
smerge    <Project1> <Project2> ...               Merge sample sheets from different Projects (for meta analysis)
json      <dir1> <dir2> ...                       Build a input.json file for manual branches definition

Use: bpipe-config info <pipeline name> to generate and html page with info for the pipeline.

sheet command INFO argument format:
    FCID=D2A8DACXX,Lane=3,SampleID=B1,SampleRef=hg19,Index=TTAGGC,Description=description,Control=N,Recipe=MeDIP,Operator=FG,SampleProject=PI_1A_name
```

## TUTORIALS

Here we will explore some general uses of bpipe-config. 

Once you have installed bpipe and bpipe-config you can list the avaliable pipelines with:

```
#!bash
bpipe-config -p
```

That returns a list of pipelines names:

```
#!bash
bam_recalibration
exome_bam_recalibration_multi ------------> BAM Recalibration for a multiple bams (exomes): IOS CTGB 020
exome_bam_recalibration_single -----------> BAM Recalibration for a single bam (exomes): IOS CTGB 020
genome_bam_recalibration_multi -----------> BAM Recalibration for a multiple bams (genomes): IOS CTGB 020
genome_bam_recalibration_single ----------> BAM Recalibration for a single bam (genomes): IOS CTGB 020

dna_alignment
bwa_aln_submit_lane ----------------------> DNA alignment with bwa aln+sampe (lane): IOS CTGB 009 [deprecated]
bwa_aln_submit_pair ----------------------> DNA paired ends alignment with bwa aln+sampe: IOS CTGB 009 [deprecated]
bwa_aln_submit_pair_nosplit --------------> DNA paired ends alignment with bwa aln+sampe (no splitting): IOS CTGB 009 [deprecated]
bwa_aln_submit_single --------------------> DNA alignment with bwa aln+samse (single file): IOS CTGB 009 [deprecated]
bwa_aln_submit_single_nosplit ------------> DNA alignment with bwa aln+samse (single file without splitting): IOS CTGB 009 [deprecated]
bwa_submit_lane --------------------------> DNA alignment with bwa mem (lane): IOS CTGB 009
bwa_submit_pair --------------------------> DNA paired ends alignment with bwa mem: IOS CTGB 009
bwa_submit_pair_nosplit ------------------> DNA paired ends alignment with bwa mem without splitting input fastq: IOS CTGB 009
bwa_submit_single ------------------------> DNA alignment with bwa mem (single file): IOS CTGB 009
bwa_submit_single_nosplit ----------------> DNA alignment with bwa mem (single file): IOS CTGB 009

illumina_projects
exome_align_project ----------------------> exome project alignment with bwa: IOS CTGB 009
fastqc_project ---------------------------> FASTQC: quality control of fastq files - IOS XXX
genome_align_project ---------------------> genome project alignment with bwa: IOS CTGB 009
rnaseq_align_project ---------------------> RNA project alignment with soapsplice: IOS CTGB 009.

medip
medip ------------------------------------> meDIP pipeline: IOS CTGB XXX.

quality_control
depth_of_coverage ------------------------> GATK: calculate Depth Of Coverage from BAM files - IOS XXX
fastqc_lane ------------------------------> FASTQC: quality control of fastq files (lane) - IOS XXX
hsmetrics --------------------------------> CalculateHsMetrics, Quality control metrics for illumina exomes: IOS CTGB XXX.
rseqc ------------------------------------> RNA-seq quality control with rseqc: IOS CTGB 009.
vcf_metrics ------------------------------> Quality Control of VCF files: IOS CTGB XXX.

reads_manipulation
trim_reads_pair --------------------------> Trimmomatic, trim reads in current directory: IOS CTGB XXX.
trim_reads_project -----------------------> Trimmomatic, trim reads in current directory: IOS CTGB XXX.

reports
WES_report -------------------------------> Whole Exome Sequencing report: IOS XXX

rna_alignment
soapsplice_submit_lane -------------------> RNA lane alignment with soapsplice: IOS CTGB 009.
soapsplice_submit_pair -------------------> RNA paired ends alignment with soapsplice: IOS CTGB 009.
soapsplice_submit_single -----------------> RNA single file alignment with soapsplice: IOS CTGB 009.

rna_seq
htseq_count ------------------------------> RNA-seq reads count with htseq-count: IOS CTGB 007.
rna_seq_lane -----------------------------> RNA-seq complete pipeline for lane: IOS CTGB 009 + IOS CTGB 007.

structural_variations
xhmm_copy_number_variation ---------------> XHMM: copy number variation - IOS XXX

test
gfu_template_multisamples ----------------> PIPELINE FOR MULTIPLE SAMPLES IN SAME DIR: IOS CTGB #NUMBER.
gfu_template_singlesample ----------------> PIPELINE FOR SINGLE SAMPLE IN SAME DIR: IOS CTGB #NUMBER.
hello_world ------------------------------> A test pipeline

variants_calling
alleles_variants_calling -----------------> Human variants calling for alleles: IOS XXX
exome_variants_annotation ----------------> Human variants annotation: IOS 005
exome_variants_calling -------------------> Human variants calling for exome: IOS 005
genome_variants_annotation ---------------> Human variants annotation: IOS 005
genome_variants_calling ------------------> Human variants calling for genomes: IOS 015
```

To generate a **hello_world** pipeline in the currect directory you can use:

```
#!bash
bpipe-config -s pipe hello_world
```

Note the use of **options -s** to skip the SampleSheet validation.

bpipe-config will generate the following files:

1. **hello_world.groovy** : the bpipe pipeline file, see also the [bpipe overview](https://code.google.com/p/bpipe/wiki/Overview)
2. **bpipe.config** : a bpipe configuration file specific for our cluster (Pbs Professional), see also the [bpipe resource manager](https://code.google.com/p/bpipe/wiki/ResourceManagers) if you want to launch the pipeline **locally** without sending jobs with *qsub*, **remove this file**

To launch this Hello World pipeline you use bpipe:

```
#!bash
bpipe run -r hello_world.groovy
```

Where the option **-r** generate a report in the subdirectory **doc**

![image](https://bytebucket.org/drambaldi/bpipe_config/raw/14b12fee5507720f3a1c61eb42610dc9a950aab5/misc/important.png) **AFTER PIPELINE EXECUTION USE** `bpipe cleanup` **to remove intermediate files**!


### SINGLE DIR 

In this example we will create a pipeline for a single sample.

#### SINGLE SAMPLE, SINGLE DIR

**Scenario:** You have a working directory with some input files:

```
#!bash
B1_TTAGGC_L003_R1_002.fastq.gz  
B1_TTAGGC_L003_R2_002.fastq.gz
B1_TTAGGC_L003_R1_003.fastq.gz  
B1_TTAGGC_L003_R2_003.fastq.gz 
SampleSheet.csv
```
	

To **align data with the rna_seq pipeline**: enter working directory (make sure that you have a __SampleSheet.csv__ in it) and generate the __pipeline.groovy__ file
	
	
```
#!bash
bpipe-config pipe rna_seq_lane
```

You can now run the pipeline in interactive mode (use **bg-bpipe** to run it in background)

```
#!bash
bpipe run -r rna_seq_lane.groovy *.fastq.gz
```

Where the option -r produce an HTML report in the local sub-directory __doc/__

![image](https://bytebucket.org/drambaldi/bpipe_config/raw/14b12fee5507720f3a1c61eb42610dc9a950aab5/misc/important.png) **AFTER PIPELINE EXECUTION USE** `bpipe cleanup` **to remove intermediate files**!

#### MULTIPLE SAMPLES IN SINGLE DIR

**Scenario:** You have already aligned your data and you have collected all the bam files and the **SampleSheet.csv** in a directory called **BAM**:

```
#!bash
3781_GTGAAA_L005.merge.dedup.bam
3907_ATGTCA_L004.merge.dedup.bam
4002_TGACCA_L003.merge.dedup.bam
4183_GTTTCG_L001.merge.dedup.bam
4273_CATGGC_L004.merge.dedup.bam
4353_GTTTCG_L002.merge.dedup.bam
6112_TCGGCA_L002.merge.dedup.bam
8099_TAGCTT_L007.merge.dedup.bam
SampleSheet.csv
```

To launch a **bam recalibration** on this samples:

```
#!bash
bpipe-config pipe exome_bam_recalibration_multi
```

You can now run the pipeline in interactive mode (use **bg-bpipe** to run it in background)

```
#!bash
bpipe run -r exome_bam_recalibration_multi.groovy *.bam
```
![image](https://bytebucket.org/drambaldi/bpipe_config/raw/14b12fee5507720f3a1c61eb42610dc9a950aab5/misc/important.png) **AFTER PIPELINE EXECUTION USE** `bpipe cleanup` **to remove intermediate files**!


### ILLUMINA PROJECTS

Pipelines in category **illumina_projects** parallelize on multiple samples and follow the directory structure generated by the Illumina demultiplex software.

**Scenario:** In the raw_data directory `/lustre2/raw_data/RUN_NAME/PINAME_PROJECTID_PROJECTNAME`, after demultiplex, a Project have this structure:

```
#!bash
Project_113_CAPS
????????? Sample_test_1
??????? ????????? SampleSheet.csv
??????? ????????? Sample_test_1_L001_R1_001.fastq.gz
??????? ????????? Sample_test_1_L001_R1_002.fastq.gz
??????? ????????? Sample_test_1_L001_R2_001.fastq.gz
??????? ????????? Sample_test_1_L001_R2_002.fastq.gz
??????? ????????? Sample_test_1_L002_R1_001.fastq.gz
??????? ????????? Sample_test_1_L002_R1_002.fastq.gz
??????? ????????? Sample_test_1_L002_R2_001.fastq.gz
??????? ????????? Sample_test_1_L002_R2_002.fastq.gz
????????? Sample_test_10
??????? ????????? SampleSheet.csv
??????? ????????? Sample_test_10_L001_R1_001.fastq.gz
??????? ????????? Sample_test_10_L001_R1_002.fastq.gz
??????? ????????? Sample_test_10_L001_R2_001.fastq.gz
??????? ????????? Sample_test_10_L001_R2_002.fastq.gz
????????? Sample_test_11
 ???? ????????? SampleSheet.csv
 ???? ????????? Sample_test_11_L001_R1_001.fastq.gz
 ???? ????????? Sample_test_11_L001_R1_002.fastq.gz
 ???? ????????? Sample_test_11_L001_R2_001.fastq.gz
 ???? ????????? Sample_test_11_L001_R2_002.fastq.gz
```

![image](https://bytebucket.org/drambaldi/bpipe_config/raw/14b12fee5507720f3a1c61eb42610dc9a950aab5/misc/important.png) Don't launch pipelines directly in the **raw_data directory**, instead create a directory under `/lustre2/scratch/` to launch the pipeline and store intermediate files.

Example:

My raw data are in:
 
```
#!bash
/lustre2/raw_data/131212_SN859_0138_AC2PGHACXX/Project_Kajaste_80_LVTranscription
```

I create a scratch dir to run the pipeline: 

```
#!bash
mkdir /lustre2/scratch/Kajaste/80_LV_Transcription
```

Enter scratch dir and launch **bpipe-config** pointing to **samples in raw_data**

```
#!bash
cd /lustre2/scratch/Kajaste/80_LV_Transcription
bpipe-config pipe exome_align_project /lustre2/raw_data/131212_SN859_0138_AC2PGHACXX/Project_Kajaste_80_LVTranscription/Sample_*
```

4. You will find a file called **input.json** in your scratch dir, that file store a list of **SAMPLES** and **FILES RELATED** to each sample:

```
#!json
{
"Sample_test_1": [
        "/PATH/TO/RAW_DATA/Sample_test_1/SampleSheet.csv",
        "/PATH/TO/RAW_DATA/Sample_test_1/Sample_test_1_L001_R1_001.fastq.gz",
        "/PATH/TO/RAW_DATA/Sample_test_1/Sample_test_1_L001_R1_002.fastq.gz",
        "/PATH/TO/RAW_DATA/Sample_test_1/Sample_test_1_L001_R2_001.fastq.gz",
        "/PATH/TO/RAW_DATA/Sample_test_1/Sample_test_1_L001_R2_002.fastq.gz",
        "/PATH/TO/RAW_DATA/Sample_test_1/Sample_test_1_L002_R1_001.fastq.gz",
        "/PATH/TO/RAW_DATA/Sample_test_1/Sample_test_1_L002_R1_002.fastq.gz",
        "/PATH/TO/RAW_DATA/Sample_test_1/Sample_test_1_L002_R2_001.fastq.gz",
        "/PATH/TO/RAW_DATA/Sample_test_1/Sample_test_1_L002_R2_002.fastq.gz"
    ],
"Sample_test_10": [
        "/PATH/TO/RAW_DATA/Sample_test_10/SampleSheet.csv",
        "/PATH/TO/RAW_DATA/Sample_test_10/Sample_test_10_L001_R1_001.fastq.gz",
        "/PATH/TO/RAW_DATA/Sample_test_10/Sample_test_10_L001_R1_002.fastq.gz",
        "/PATH/TO/RAW_DATA/Sample_test_10/Sample_test_10_L001_R2_001.fastq.gz",
        "/PATH/TO/RAW_DATA/Sample_test_10/Sample_test_10_L001_R2_002.fastq.gz"
    ],
"Sample_test_11": [
        "/PATH/TO/RAW_DATA/Sample_test_11/SampleSheet.csv",
        "/PATH/TO/RAW_DATA/Sample_test_11/Sample_test_11_L001_R1_001.fastq.gz",
        "/PATH/TO/RAW_DATA/Sample_test_11/Sample_test_11_L001_R1_002.fastq.gz",
        "/PATH/TO/RAW_DATA/Sample_test_11/Sample_test_11_L001_R2_001.fastq.gz",
        "/PATH/TO/RAW_DATA/Sample_test_11/Sample_test_11_L001_R2_002.fastq.gz"
    ]
}
```

This files will be used by bpipe to generate the first branches (sample branches) of the pipeline and ensure that each sample use ONLY the files in the correct directory. **To remove a sample just remove it from this file!**

Run the pipeline with:

```
#!bash
bpipe run -r exome_align_project.groovy input.json
```

Note that we use the input.json as input file for our pipeline.


**behind the scenes:** 

the bpipe project pipelines make the following steps:

1. Create a **scratch sample dir** for each sample in **raw-data**
2. Copy in the scratch sample dir the SampleSheet.csv
3. Run the aligment, merging and dedupliaction in parallel for each sample
4. Move the final output (generally bam files) in a result directory (default: "BAM")


![image](https://bytebucket.org/drambaldi/bpipe_config/raw/14b12fee5507720f3a1c61eb42610dc9a950aab5/misc/important.png) **AFTER PIPELINE EXECUTION USE** `bpipe cleanup` **to remove intermediate files**!


### REPORTS

We have added a prototype reporting system in bpipe-config. To report a project you should collect some files/info:

For **Variants Calling Projects**:

1. create a working dir for the report and copy the project **"PRE"** SampleSheet.csv to the directory, the SampleSheet.csv with additional columns (Design, Lab Code, etc...)
2. create a BAM directory and link/copy bam and bai files
3. create VCF directory and link/copy Tier vcf files
4. `bpipe-config pipe WES_report`
5. Modify the files autogenerated in the sub-directory **report_data**:
   * rationale.md: an abstract (short description) for the Project
   * stats.groovy contains a list of variables/infos used by the pipelines
   * pedigree.ped: A ped file (optional)
     

**PEDIGREE:** If you have a pedigree (ped format), put it in the **REPORT_DATA_DIR** (report_data) directory

**HEALTY EXOMES:** If you DON't USE the exomes in **HEALTY_EXOMES_DIR**, please set  *with_healty_exomes:false* and remove stage *healty_exomes_info_gfu*

To launch the pipeline

```
#!bash
bpipe -r run WES_report.groovy BAM/* VCF/*
```

### MULTIPLE DIRS

![image](https://bytebucket.org/drambaldi/bpipe_config/raw/14b12fee5507720f3a1c61eb42610dc9a950aab5/misc/important.png) BE CAREFUL with this pipelines! For each sample you will create a new bpipe process!


**DON'T USE MUTIPLE DIRS FOR MORE THAN 10 SAMPLES (you should use illumina projects pipelines)**

**Scenario:** A working directory in /lustre2/scratch/PI_NAME/PROJECT_NAME with the following subdirs:

```
Sample_1
Sample_2
Sample_3
Sample_4
Sample_5
Sample_6
```

Where each sample directory contains input files and a __SampleSheet.csv__


**Example:**


Configure and generate a pipeline for each directory:

```
#!bash
bpipe-config pipe rna_seq_lane Sample_*
```

This command generate a separate pipeline file for each sample and a file **runner.sh** to launch the pipelines in a single command.
   

Run the pipelines:

```
#!bash
./runner.sh
```
   
![image](https://bytebucket.org/drambaldi/bpipe_config/raw/14b12fee5507720f3a1c61eb42610dc9a950aab5/misc/important.png) **AFTER PIPELINE EXECUTION USE** `bpipe cleanup` **to remove intermediate files**!

```
#!bash
for i in Sample_*
do
	cd $i
	bpipe cleanup
	cd ..
done
```


---

## DEV NOTES


### WHAT IS IN THE BOX

* __bin__ : executables
* __config__ : configuration files
* __data__ : test data
* __local-lib__ : jar files
* __misc__ : miscellaneous scripts
* __modules__ : bpipe modules
* __pipelines__ : bpipe pipelines
* __src__ : bpipe-config source groovy code
* __templates__ : groovy templates
* __test-pipelines__ : modules and pipelines tests


### GRADLE BUILD SYSTEM 

#### GET LIST OF TASKS

```
gradle tasks
```

#### RUN UNIT TEST

```
gradle test
```

#### STAGE IN BUILD DIRECTORY

```
gradle stage
```

#### CREATE A TAR.GZ FILE FOR DISTRIBUTE

```
gradle dist
```


### DEVELOPER NOTES

* Gradle templates: https://github.com/townsfolk/gradle-templates
* JANSI tutorial: http://jameswilliams.be/blog/entry/240
* CSV FILES: http://www.kellyrob99.com/blog/2010/07/01/groovy-and-csv-how-to-get-your-data-out/

#### TEST JAR

* test java compiled run with:

```
java  -cp groovy-all:build/libs/jarfile bpipeconfig.BpipeConfig
```

Example:

```
java  -cp /usr/local/groovy/embeddable/groovy-all-2.1.7.jar:build/libs/bpipe_config.jar bpipeconfig.BpipeConfig
```

#### TEST BASH SCRIPT

From devel root dir:

```
export BPIPE_CONFIG_HOME=./build/stage/bpipeconfig-0.1 && ./build/stage/bpipeconfig-0.1/bin/bpipe-config
```

From data dir:

```
export BPIPE_CONFIG_HOME=../build/stage/bpipeconfig-0.1 && ../build/stage/bpipeconfig-0.1/bin/bpipe-config
```

#### SUBLIME

* sublime-gradle: https://github.com/koizuss/sublime-gradle

* JAVADOC SNIPPETS: https://github.com/ekryski/sublime-comment-snippets

#### Javadoc Snippets

* `todo` - TODO
* `fix` - FIXME
* `note` - NOTE
* `/**` - Nicely formatted block comment
* `/==` - Nice divider comment
* `jdoc:c` - Java Doc style class comment
* `jdoc:m` - Java Doc style method comment

