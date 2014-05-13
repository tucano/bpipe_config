#!/usr/bin/env bash

log()  { printf "%b\n" "$*"; }
fail() { log "\nERROR: $*\n" ; exit 1 ; }

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
  log "Exporting enviroment in current shell:"
  export BPIPE_HOME=/lustre1/tools/libexec/bpipe
  export PATH=\$BPIPE_HOME/bin:\$PATH
	export BPIPE_CONFIG_HOME=/lustre1/tools/libexec/bpipeconfig
  export BPIPE_LIB=\$BPIPE_CONFIG_HOME/modules
  export PATH=\$BPIPE_CONFIG_HOME/bin:\$PATH
fi

which bpipe 1>/dev/null 2>&1 || fail "Can't find bpipe binary with which! Something goes wrong here..."

log "bpipe path: $BPIPE_HOME/bin/bpipe"
log "bpipe-config path: $BPIPE_CONFIG_HOME/bin/bpipe-config"
log "All set."
