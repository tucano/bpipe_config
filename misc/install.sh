#!/usr/bin/env bash

log()  { printf "%b\n" "$*"; }
fail() { log "\nERROR: $*\n" ; exit 1 ; }

PROFILE=$HOME/.profile

if [[ ! -f $PROFILE ]]
then
    log "Creating $PROFILE"
fi

echo -e "Appending BPIPE and BPIPE_CONFIG enviroment vars to $PROFILE"

cat >> $PROFILE <<DELIM
export BPIPE_CONFIG_HOME=/lustre1/tools/libexec/bpipeconfig
export BPIPE_LIB=$BPIPE_CONFIG_HOME/modules
export BPIPE_HOME=/lustre1/tools/libexec/bpipe
export PATH=\$BPIPE_HOME/bin:\$BPIPE_CONFIG_HOME/bin:\$PATH
DELIM

log "Sourcing ..."
source $HOME/.profile

which bpipe 1>/dev/null 2>&1 || fail "Can't find bpipe binary with which! Something goes wrong here..."

log "All set."