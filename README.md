# bpipe-config version 0.2

bpip-config is a configuration and reporting software for bpipe.


## INSTANT GRATIFICATION

How to install and use bpipe and bpipe-config

### 1. Install

This will add some environment vars to your __.profile__ file

```
/lustre1/tools/libexec/bpipeconfig/misc/install.sh
```

### 2. List pipelines

To print a list of available pipelines:

```
bpipe-config -p
```

### 3. Generate a pipeline and run it in a single command

```
bpipe-config -b pipe <pipeline-name>
```


## USER GUIDE

### HELP

* bpipe-config help: ```bpipe-config -h```
* bpipe-config commands list: ```bpipe-config -c```
* bpipe-config pipelines list: ```bpipe-config -p```
* bpipe help ```bpipe -h```
* [bpipe documentation](https://code.google.com/p/bpipe/wiki/Reference)


### ENVIRONMENT

You need this in your enviroment (automatically installed in your .profile with the install.sh script):

```
export BPIPE_HOME=/lustre1/tools/libexec/bpipe
export BPIPE_CONFIG_HOME=/lustre1/tools/libexec/bpipeconfig
export BPIPE_LIB=$BPIPE_CONFIG_HOME/modules
export PATH=$BPIPE_CONFIG_HOME/bin:$BPIPE_HOME/bin:$PATH
```

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


#### RUN THE PIPELINE

* Enter working directory (make sure that you have a __SampleSheet.csv__)

* In case you need it, you can generate a __SampleSheet.csv__ with

```
bpipe-config sheet KEY=VALUE
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


####RUN THE PIPELINE

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
