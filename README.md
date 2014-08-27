# bpipe-config version 1.0

---
bpip-config is a configuration and reporting software for [bpipe](https://code.google.com/p/bpipe/).

bpipe provides a platform for running big bioinformatics jobs that consist of a series of processing stages - known as 'pipelines', see also the [bpipe documentation](https://code.google.com/p/bpipe/wiki/Reference)

---

## Table of Contents

* [INSTALL](#install)
* [USAGE](#usage)
* [OPTIONS](#options)
* [COMMANDS](#commands)
* [TUTORIALS](#tutorial)
  * [SINGLE DIR](#single)
  * [ILLUMINA PROJECTS](#projects)
  * [REPORTS](#reports)
  * [MULTIPLE DIR](#multi) 
* [DEVELOPERS NOTES](#dev)

---

<br/>
<br/>
<br/>  

## <a name="install"></a>INSTALL


##### AUTOMATIC

How to install and use bpipe and bpipe-config:

```
./lustre1/tools/libexec/bpipeconfig/misc/install.sh
source ~/.bash_profile
```

This will add some environment vars to your __.bash_profile__ file


##### MANUAL

You need this in your enviroment (automatically installed in your .bash_profile with the install.sh script):

```
export BPIPE_HOME=/lustre1/tools/libexec/bpipe
export BPIPE_CONFIG_HOME=/lustre1/tools/libexec/bpipeconfig
export BPIPE_LIB=$BPIPE_CONFIG_HOME/modules
export PATH=$BPIPE_CONFIG_HOME/bin:$BPIPE_HOME/bin:$PATH
```


## <a name="usage"></a> USAGE EXAMPLES
<br/>

Usage:

```
bpipe-config [options] [command] [pipeline_name] [sample_dirs|project_dirs]
```

#### List pipelines

Print a list of available pipelines:

```
bpipe-config -p
```

#### List commands

Print a list of available commands:

```
bpipe-config -c
```

#### Generate a pipeline

Generate a pipeline in local directory:

```
bpipe-config pipe <pipeline-name>
```

#### Generate a "Project" pipeline

Generate a multi-sample project pipeline that works with illumina files structure:

```
bpipe-config pipe <project-pipeline-name> /lustre2/raw_data/Project/Sample_*
```

#### Generate multiple project pipeline

Generate a multi-sample project pipeline that works with illumina files structure for MULTIPLE PROJECTS:

```
bpipe-config project <pipeline-name> <Project1> <Project2>
```

#### Generate a pipeline and run it in batch (-b)

Generate a pipeline and launch it with bpipe in a single command:

```
bpipe-config -b pipe <pipeline-name>
```

#### Force mode to overwrite local files (-f)

Generate a pipeline forcing overwrite of local files.

```
bpipe-config -f pipe <pipeline-name>
```

#### Merge samplesheets from different projects 

```
bpipe-config smerge <Project1> <Project2> ...
```
<br/>
<br/>

## <a name="options"></a> OPTIONS

```
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

<br/>
<br/>

## <a name="tutorial"></a> TUTORIALS

Here we will explore some general uses of bpipe-config. 

Once you have installed bpipe and bpipe-config you can list the avaliable pipelines with:

```
bpipe-config -p
```

That returns a list of pipelines names:

```
bam_recalibration
exome_bam_recalibration_multi ------------> BAM Recalibration for a multiple bams (exomes): IOS GFU 020
exome_bam_recalibration_single -----------> BAM Recalibration for a single bam (exomes): IOS GFU 020
genome_bam_recalibration_multi -----------> BAM Recalibration for a multiple bams (genomes): IOS GFU 020
genome_bam_recalibration_single ----------> BAM Recalibration for a single bam (genomes): IOS GFU 020

dna_alignment
bwa_aln_submit_lane ----------------------> DNA alignment with bwa aln+sampe (lane): IOS GFU 009 [deprecated]
bwa_aln_submit_pair ----------------------> DNA paired ends alignment with bwa aln+sampe: IOS GFU 009 [deprecated]
bwa_aln_submit_pair_nosplit --------------> DNA paired ends alignment with bwa aln+sampe (no splitting): IOS GFU 009 [deprecated]
bwa_aln_submit_single --------------------> DNA alignment with bwa aln+samse (single file): IOS GFU 009 [deprecated]
bwa_aln_submit_single_nosplit ------------> DNA alignment with bwa aln+samse (single file without splitting): IOS GFU 009 [deprecated]
bwa_submit_lane --------------------------> DNA alignment with bwa mem (lane): IOS GFU 009
bwa_submit_pair --------------------------> DNA paired ends alignment with bwa mem: IOS GFU 009
bwa_submit_pair_nosplit ------------------> DNA paired ends alignment with bwa mem without splitting input fastq: IOS GFU 009
bwa_submit_single ------------------------> DNA alignment with bwa mem (single file): IOS GFU 009
bwa_submit_single_nosplit ----------------> DNA alignment with bwa mem (single file): IOS GFU 009

illumina_projects
exome_align_project ----------------------> exome project alignment with bwa: IOS GFU 009
fastqc_project ---------------------------> FASTQC: quality control of fastq files - IOS XXX
genome_align_project ---------------------> genome project alignment with bwa: IOS GFU 009
rnaseq_align_project ---------------------> RNA project alignment with soapsplice: IOS GFU 009.

medip
medip ------------------------------------> meDIP pipeline: IOS GFU XXX.

quality_control
depth_of_coverage ------------------------> GATK: calculate Depth Of Coverage from BAM files - IOS XXX
fastqc_lane ------------------------------> FASTQC: quality control of fastq files (lane) - IOS XXX
hsmetrics --------------------------------> CalculateHsMetrics, Quality control metrics for illumina exomes: IOS GFU XXX.
rseqc ------------------------------------> RNA-seq quality control with rseqc: IOS GFU 009.
vcf_metrics ------------------------------> Quality Control of VCF files: IOS GFU XXX.

reads_manipulation
trim_reads_pair --------------------------> Trimmomatic, trim reads in current directory: IOS GFU XXX.
trim_reads_project -----------------------> Trimmomatic, trim reads in current directory: IOS GFU XXX.

reports
WES_report -------------------------------> Whole Exome Sequencing report: IOS XXX

rna_alignment
soapsplice_submit_lane -------------------> RNA lane alignment with soapsplice: IOS GFU 009.
soapsplice_submit_pair -------------------> RNA paired ends alignment with soapsplice: IOS GFU 009.
soapsplice_submit_single -----------------> RNA single file alignment with soapsplice: IOS GFU 009.

rna_seq
htseq_count ------------------------------> RNA-seq reads count with htseq-count: IOS GFU 007.
rna_seq_lane -----------------------------> RNA-seq complete pipeline for lane: IOS GFU 009 + IOS GFU 007.

structural_variations
xhmm_copy_number_variation ---------------> XHMM: copy number variation - IOS XXX

test
gfu_template_multisamples ----------------> PIPELINE FOR MULTIPLE SAMPLES IN SAME DIR: IOS GFU #NUMBER.
gfu_template_singlesample ----------------> PIPELINE FOR SINGLE SAMPLE IN SAME DIR: IOS GFU #NUMBER.
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
bpipe-config -s pipe hello_world
```

Note the use of **options -s** to skip the SampleSheet validation.

bpipe-config will generate the following files:

1. **hello_world.groovy** : the bpipe pipeline file, see also the [bpipe overview](https://code.google.com/p/bpipe/wiki/Overview)
2. **bpipe.config** : a bpipe configuration file specific for our cluster (Pbs Professional), see also the [bpipe resource manager](https://code.google.com/p/bpipe/wiki/ResourceManagers) if you want to launch the pipeline **locally** without sending jobs with *qsub*, **remove this file**

To launch this Hello World pipeline you use bpipe:

```
bpipe run -r hello_world.groovy
```

Where the option **-r** generate a report in the subdirectory **doc**

![image](misc/important.png) **AFTER PIPELINE EXECUTION USE** `bpipe cleanup` **to remove intermediate files**!

<br/>
<br/>

### <a name="single"></a> SINGLE DIR 

In this example we will create a pipeline for a single sample.

1. **SINGLE SAMPLE, SINGLE DIR**

	**Scenario:** You have a working directory with some input files:
	
	```
	B1_TTAGGC_L003_R1_002.fastq.gz  
	B1_TTAGGC_L003_R2_002.fastq.gz
	B1_TTAGGC_L003_R1_003.fastq.gz  
	B1_TTAGGC_L003_R2_003.fastq.gz 
	SampleSheet.csv
	```
	
	To **align data with the rna_seq pipeline**: enter working directory (make sure that you have a __SampleSheet.csv__ in it) and generate the __pipeline.groovy__ file
	
	
	```
	bpipe-config pipe rna_seq_lane
	```
	
	You can now run the pipeline in interactive mode (use **bg-bpipe** to run it in background)
	
	```
	bpipe run -r rna_seq_lane.groovy *.fastq.gz
	```
	
	Where the option -r produce an HTML report in the local sub-directory __doc/__
	
	![image](misc/important.png) **AFTER PIPELINE EXECUTION USE** `bpipe cleanup` **to remove intermediate files**!

<br/>

2. **MULTIPLE SAMPLES IN SINGLE DIR**

	**Scenario:** You have already aligned your data and you have collected all the bam files and the **SampleSheet.csv** in a directory called **BAM**:
	
	```
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
	bpipe-config pipe exome_bam_recalibration_multi
	```
	
	You can now run the pipeline in interactive mode (use **bg-bpipe** to run it in background)
	
	```
	bpipe run -r exome_bam_recalibration_multi.groovy *.bam
	```
	![image](misc/important.png) **AFTER PIPELINE EXECUTION USE** `bpipe cleanup` **to remove intermediate files**!

<br/>
<br/>

### <a name="projects"></a> ILLUMINA PROJECTS

Pipelines in category **illumina_projects** parallelize on multiple samples and follow the directory structure generated by the Illumina demultiplex software.

**Scenario:** In the raw_data directory `/lustre2/raw_data/RUN_NAME/PINAME_PROJECTID_PROJECTNAME`, after demultiplex, a Project have this structure:

```
Project_113_CAPS
├── Sample_test_1
│   ├── SampleSheet.csv
│   ├── Sample_test_1_L001_R1_001.fastq.gz
│   ├── Sample_test_1_L001_R1_002.fastq.gz
│   ├── Sample_test_1_L001_R2_001.fastq.gz
│   ├── Sample_test_1_L001_R2_002.fastq.gz
│   ├── Sample_test_1_L002_R1_001.fastq.gz
│   ├── Sample_test_1_L002_R1_002.fastq.gz
│   ├── Sample_test_1_L002_R2_001.fastq.gz
│   └── Sample_test_1_L002_R2_002.fastq.gz
├── Sample_test_10
│   ├── SampleSheet.csv
│   ├── Sample_test_10_L001_R1_001.fastq.gz
│   ├── Sample_test_10_L001_R1_002.fastq.gz
│   ├── Sample_test_10_L001_R2_001.fastq.gz
│   └── Sample_test_10_L001_R2_002.fastq.gz
└── Sample_test_11
    ├── SampleSheet.csv
    ├── Sample_test_11_L001_R1_001.fastq.gz
    ├── Sample_test_11_L001_R1_002.fastq.gz
    ├── Sample_test_11_L001_R2_001.fastq.gz
    └── Sample_test_11_L001_R2_002.fastq.gz
```

![image](misc/important.png) Don't launch pipelines directly in the **raw_data directory**, instead create a directory under `/lustre2/scratch/` to launch the pipeline and store intermediate files.

Example:

1. My raw data are in:
 
   ```
   /lustre2/raw_data/131212_SN859_0138_AC2PGHACXX/Project_Kajaste_80_LVTranscription
   ``` 
   
2. I create a scratch dir to run the pipeline: 

   ```
   mkdir /lustre2/scratch/Kajaste/80_LV_Transcription
   ```

3. Enter scratch dir and launch **bpipe-config** pointing to **samples in raw_data**

   ```
   cd /lustre2/scratch/Kajaste/80_LV_Transcription
   bpipe-config pipe exome_align_project /lustre2/raw_data/131212_SN859_0138_AC2PGHACXX/Project_Kajaste_80_LVTranscription/Sample_*
   ```

4. You will find a file called **input.json** in your scratch dir, that file store a list of **SAMPLES** and **FILES RELATED** to each sample:

   ```
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

5. Run the pipeline with:

	```
	bpipe run -r exome_align_project.groovy input.json
	```

    Note that we use the input.json as input file for our pipeline.


**behind the scenes:** 

the bpipe project pipelines make the following steps:

1. Create a **scratch sample dir** for each sample in **raw-data**
2. Copy in the scratch sample dir the SampleSheet.csv
3. Run the aligment, merging and dedupliaction in parallel for each sample
4. Move the final output (generally bam files) in a result directory (default: "BAM")


![image](misc/important.png) **AFTER PIPELINE EXECUTION USE** `bpipe cleanup` **to remove intermediate files**!

<br/>
<br/>

### <a name="reports"></a> REPORTS

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


<br/>
<br/>

### <a name="multi"></a> MULTIPLE DIRS

![image](misc/important.png) BE CAREFUL with this pipelines! For each sample you will create a new bpipe process!


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


1. Configure and generate a pipeline for each directory:
   
   ```
   bpipe-config pipe rna_seq_lane Sample_*
   ```

   This command generate a separate pipeline file for each sample and a file **runner.sh** to launch the pipelines in a single command.
   

2. Run the pipelines:

   ```
   ./runner.sh
   ```
   
3. ![image](misc/important.png) **AFTER PIPELINE EXECUTION USE** `bpipe cleanup` **to remove intermediate files**!

   ```
   for i in Sample_*
   do
   	cd $i
   	bpipe cleanup
   	cd ..
   done
   ```

---
<br/>

## <a name="dev"></a>DEV NOTES


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


### DEVELOPMENT NOTES

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

