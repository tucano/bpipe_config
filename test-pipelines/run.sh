#!/bin/bash

source testsupport.sh


# cd modules/

# echo -e "TESTING SINGLE MODULES:\n\n"

# TESTS=`find . -maxdepth 1 -type d | grep "[A-Za-z]"`

# for t in $TESTS;
# do
#         echo "============== $t ================"
#         cd "$t/";
#         ./runner.sh 1>/dev/null 2>&1
#         RESULT=$?
#         if [ $RESULT == 0 ]; then
#                 echo "--> SUCCEEDED";
#         else
#                 echo "--> FAILED";
#         fi
#         cd ..
# done

# cd ..

# cd pipelines/

# echo -e "\n\n\n\nTESTING PIPELINES:\n\n"

# TESTS=`find . -maxdepth 1 -type d | grep "[A-Za-z]"`

# for t in $TESTS;
# do
#         echo "============== $t ================"
#         cd "$t/";
#         ./runner.sh 1>/dev/null 2>&1
#         RESULT=$?
#         if [ $RESULT == 0 ]; then
#                 echo "--> SUCCEEDED";
#         else
#                 echo "--> FAILED";
#         fi
#         cd ..
# done

# cd ..
