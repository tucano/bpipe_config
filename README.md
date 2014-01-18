# bpipe-config version 0.4

bpip-config is a configuration and reporting software for bpipe.

## Install

How to install and use bpipe and bpipe-config:

```
source /lustre1/tools/libexec/bpipeconfig/misc/install.sh
```

This will add some environment vars to your __.bash_profile__ file


##### ENVIRONMENT

You need this in your enviroment (automatically installed in your .bash_profile with the install.sh script):

```
export BPIPE_HOME=/lustre1/tools/libexec/bpipe
export BPIPE_CONFIG_HOME=/lustre1/tools/libexec/bpipeconfig
export BPIPE_LIB=$BPIPE_CONFIG_HOME/modules
export PATH=$BPIPE_CONFIG_HOME/bin:$BPIPE_HOME/bin:$PATH
```


## Usage

### 1. List pipelines

To print a list of available pipelines:

```
bpipe-config -p
```

### 2. List commands

To print a list of available commands:

```
bpipe-config -c
```

### 3. Generate a pipeline and run it in a single command

```
bpipe-config -b pipe <pipeline-name>
```

### 4. Force mode (overwrite local files)

```
bpipe-config -f pipe <pipeline-name>
```


---

## USER GUIDE

### HELP

* bpipe-config help: ```bpipe-config -h```
* bpipe-config commands list: ```bpipe-config -c```
* bpipe-config pipelines list: ```bpipe-config -p```
* bpipe help ```bpipe -h```
* [bpipe documentation](https://code.google.com/p/bpipe/wiki/Reference)


### 1. PROJECT PIPELINES

Pipelines in category **illumina_projects** parallelize on multiple samples.

One bpipe to run them all...  like the One Ring of Sauron.

#### Scenario:

You have a **raw_data directory** with fastq.gz reads (after demultiplex) and a **scratch directory** to perform analysis: 

```
/lustre2/raw_data/RUN_NAME/PINAME_PROJECTID_PROJECTNAME
/lustre2/scratch/PINAME/PROJECTID_PROJECTNAME
```
Example:

```
/lustre2/raw_data/131212_SN859_0138_AC2PGHACXX/Project_Kajaste_80_LVTranscription
/lustre2/scratch/Kajaste/80_LV_Transcription
```

In the raw_data directory, after demultiplex, a Project have this structure:

```
Project_1
├── Sample_test_1
│   ├── SampleSheet.csv
│   ├── Sample_test_1_testinput_R1_001.fastq.gz
│   ├── Sample_test_1_testinput_R1_002.fastq.gz
│   ├── Sample_test_1_testinput_R2_001.fastq.gz
│   └── Sample_test_1_testinput_R2_002.fastq.gz
├── Sample_test_2
│   ├── SampleSheet.csv
│   ├── Sample_test_2_testinput_R1_001.fastq.gz
│   ├── Sample_test_2_testinput_R1_002.fastq.gz
│   ├── Sample_test_2_testinput_R2_001.fastq.gz
│   └── Sample_test_2_testinput_R2_002.fastq.gz
├── Sample_test_3
│   ├── SampleSheet.csv
│   ├── Sample_test_3_testinput_R1_001.fastq.gz
│   ├── Sample_test_3_testinput_R1_002.fastq.gz
│   ├── Sample_test_3_testinput_R2_001.fastq.gz
│   └── Sample_test_3_testinput_R2_002.fastq.gz
```

####Run the pipeline

* Enter scratch dir: /lustre2/scratch/PINAME/PROJECTID_PROJECTNAME and **run bpipe-config** pointing to **samples in raw_data**

Example:

```
cd /lustre2/scratch/Kajaste/80_LV_Transcription
bpipe-config pipe bwa_submit_project /lustre2/raw_data/131212_SN859_0138_AC2PGHACXX/Project_Kajaste_80_LVTranscription/Sample_*
```

Notice that in **scratch dir** we use as arguments for bpipe the Samples dir in **/lustre2/raw-data** .

behind the scenes the bpipe project pipelines make the following:

1. Create a **scratch sample dir** for each sample in **raw-data**
2. Copy in the scratch sample dir the SampleSheet.csv
3. Link (soft links) the fastq.gz files from raw-data to scratch
4. Run the aligment in parallel for each sample
5. Move the final output (generally bam files) in a result directory (default: "BAM")


### SINGLE SAMPLE

####Scenario:

A working directory in /lustre2/scratch/PI_NAME/PROJECT_NAME/SAMPLE with the following files:

```
B1_TTAGGC_L003_R1_002.fastq.gz  
B1_TTAGGC_L003_R2_002.fastq.gz
B1_TTAGGC_L003_R1_003.fastq.gz  
B1_TTAGGC_L003_R2_003.fastq.gz 
SampleSheet.csv
```

####Run the pipeline

* Enter working directory (make sure that you have a __SampleSheet.csv__)

* In case you need it, you can generate a __SampleSheet.csv__ with

```
bpipe-config sheet FCID=D2A8DACXX,Lane=3,SampleID=B1,SampleRef=hg19,Index=TTAGGC,Description=niguarda,Control=N,Recipe=MeDIP,Operator=FG,SampleProject=PI_1A_name
```

* Generate a __bpipe.config__ file

```
bpipe-config config
```

* Get a list of avaliable pipelines with:

```
bpipe-config -p
```

* Generate the __pipeline.groovy__ file

```
bpipe-config pipe rna_seq_lane
```

* Run the pipeline in interactive mode

```
bpipe run -r pipeline.groovy [INPUTS]
```

Where the option -r produce an HTML report in the local sub-directory __doc/__


* Run the pipeline in background with:

```
bg-bpipe run pipeline.groovy [INPUTS]
```

### MULTIPLE SAMPLES

####Scenario:

A working directory in /lustre2/scratch/PI_NAME/PROJECT_NAME with the following subdirs:

```
Sample_1
Sample_2
Sample_3
Sample_4
Sample_5
Sample_6
```

Where each sample directory contains input files a __SampleSheet.csv__


####Run the pipeline

* Configure, generate and run the pipeline in a single command:

```
bpipe-config -b pipe rna_seq_lane Sample_*
```

---

## DEVELOPERS GUIDE


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

