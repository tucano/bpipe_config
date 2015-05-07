#!/bin/bash

gradle clean && gradle dist
CURRENT=`pwd -P`
cd /lustre1/tools/libexec/
rm -f bpipeconfig_SNAPSHOT.tar.gz
tar czvf bpipeconfig_SNAPSHOT.tar.gz bpipeconfig/
cd ${CURRENT}/build
rm -rf /lustre1/tools/libexec/bpipeconfig
tar xzvf bpipeconfig-*.tar.gz -C /lustre1/tools/libexec/
chmod -R g-w /lustre1/tools/libexec/bpipeconfig
cd ..
