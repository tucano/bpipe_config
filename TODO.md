# TODO LIST

### Scheduled upgrades

#### 0.3

1. VARIANTS CALLING PIPELINE (under testing now)          [OK]
2. Fastqc for RUNS and single samples                     [OK]
3. Change module align_bwa_gfu to use MEM bwa command     [OK]
4. Add triggers for quality (fail pipeline if quality is low)
5. Fix SampleSheet parser                                 [OK]
6. Variants recalibration on all samples                  [OK]


#### 0.4

1. bpipe-config command info: info on current pipeline (info on single modules in a html template) [OK]
2. bpipe-config command clean: Clean all in current working directory or in directory list: intermediate files, bpipe.config file, .bpipe directory, etc …

#### 0.5

1. MeDIP pipeline
2. Per RUN and Per Project pipelines

#### 0.6

1. bpipe-config command recover: recover a failed job and run diagnostic on node (to check bad nodes)
2. Change index.html report template

#### 0.7

1. bpipe-config command report: generate a complete report (with QA) of the pipeline
