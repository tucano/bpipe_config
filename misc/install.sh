#!/usr/bin/env bash

log()  { printf "%b\n" "$*"; }
fail() { log "\nERROR: $*\n"; exit 1;}

PROFILE=$HOME/.bash_profile

if [[ ! -f $PROFILE ]]
then
    log "Creating $PROFILE"
    touch $PROFILE
    chmod 755 $PROFILE
fi

echo -e "Appending BPIPE and BPIPE_CONFIG enviroment vars to $PROFILE"

if [[ ! -z $BPIPE_HOME ]]
then
	log "BPIPE_HOME already set to: $BPIPE_HOME skipping."
else
	echo -e "export BPIPE_HOME=/lustre1/tools/libexec/bpipe" >> $PROFILE
	echo -e "export PATH=\$BPIPE_HOME/bin:\$PATH" >> $PROFILE
fi

if [[ ! -z $BPIPE_CONFIG_HOME ]]
then
	log "BPIPE_CONFIG_HOME already set to: $BPIPE_CONFIG_HOME skipping."
else
	echo -e "export BPIPE_CONFIG_HOME=/lustre1/tools/libexec/bpipeconfig" >> $PROFILE
	echo -e "export BPIPE_LIB=\$BPIPE_CONFIG_HOME/modules" >> $PROFILE
	echo -e "export PATH=\$BPIPE_CONFIG_HOME/bin:\$PATH" >> $PROFILE
fi

if [[ -f $PROFILE ]]
then
	log "BPIPE Environment saved in $PROFILE"
  log "Load enviroment with:"
  log "source ~/.bash_profile"
fi

