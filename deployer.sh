#!/bin/bash

git pull
gradle clean && gradle test && gradle stage && gradle dist
rm -rf /lustre1/tools/libexec/bpipeconfig
cd build
tar xzvf bpipeconfig-0.5.2.tar.gz -C /lustre1/tools/libexec/
cd ..