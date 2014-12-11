# TODO LIST

### PIPELINE DA RIMUOVERE

- RIMUOVERE le submit_lane
- SPOSTARE LE "illumina projects" nelle rispettive categorie


### PIPELINE DA AGGIUNGERE

- RNASEQ con Star

- Trimmomatic single read

- Merge BAM pipeline (con check sui sample names, etc...)
   Esempio: bpipe run merge.groovy /Project1/BAM/*.bam /Project2/BAM/*.bam

   1. Per ogni BAM, cerca altri bam con lo stesso nome NEGLI HEADER del bam (SM)
   2. Costruisci un input.json con i gruppi di BAMs
   3. Merge dei bams

    Esempio: (/lustre1/tools/bin/mergebam.sh)

- FlipFLop e alternative splicing (LONG TERM)




### COSE DA SISTEMARE

- rseqc: geneBodyCoverage e' la morte, lanciare in parallelo e mergiamo le linee di R in un report custom (knitr) facendo una HEATMAP se ci sono > 10 campioni
- SampleSheet parser per Rapid Run
- FINALIZZARE rnaseq report




### TEST PER RNASEQ

- /lustre2/scratch/Gentner/175_Induced_Leukemias

- /lustre2/raw_data/T_140918_SN859_0178_AC42L6ACXX/Project_Gentner_175_Induced_leukemias/

/lustre2/raw_data/141104_SN859_0184_AC4863ACXX/Project_Gentner_175_Induced_leukemias/


# TODO LIST OLD

- TRIGGERS partendo da fastqc with send (fail)
- BRANCH variables
- BAM RECALIBRATION splittando i bam per chromosome? see docs PICMEGRE

- Add triggers for quality (fail pipeline if quality is low) (with FAIL or WARN)

