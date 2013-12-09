#!/usr/bin/env bash

log()  { printf "%b\n" "$*"; }
fail() { log "\nERROR: $*\n" ; exit 1 ; }

PROFILE=$HOME/.profile

if [[ ! -f $PROFILE ]]
then
    log "Creating $PROFILE"
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

if [[ ! -z $BPIPE_CONFIG_HOME ]]
then
	log "JAVA_HOME already set to: $JAVA_HOME skipping."
else
	echo -e "export JAVA_HOME=/lustre1/tools/libexec/jre1.7.0_25" >> $PROFILE
	echo -e "export PATH=\$JAVA_HOME/bin:\$PATH" >> $PROFILE
fi

if [[ -f $PROFILE ]]
then
	log "Sourcing ..."
	source $PROFILE
fi

which bpipe 1>/dev/null 2>&1 || fail "Can't find bpipe binary with which! Something goes wrong here..."

log "All set."
