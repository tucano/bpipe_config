#!/bin/bash

source testsupport.sh

# MODULES
module_succ=0
module_fail=0
module_failures=""
cd modules/
echo -e "TESTING SINGLE MODULES:\n"
TESTS=`find . -maxdepth 1 -type d | grep "[A-Za-z]"`
for t in $TESTS;
do
       echo "============== $t ================"
       cd "$t/";
       ./runner.sh 1>/dev/null 2>&1
       RESULT=$?
       if [ $RESULT == 0 ]; then
               echo "--> OK"
               let 'module_succ=module_succ+1'
       else
               echo "--> FAIL"
               let 'module_fail=module_fail+1'
               module_failures="$module_failures\n$t"
       fi
       cd ..
done
cd ..

# PIPELINES
pipeline_succ=0
pipeline_fail=0
pipeline_failures=""
cd pipelines/
echo -e "\nTESTING PIPELINES:\n"
TESTS=`find . -maxdepth 1 -type d | grep "[A-Za-z]"`
for t in $TESTS;
do
        echo "============== $t ================"
        cd "$t/";
        ./runner.sh 1>/dev/null 2>&1
        RESULT=$?
        if [ $RESULT == 0 ]; then
                echo "--> OK"
                let  "pipeline_succ=pipeline_succ+1"
        else
                echo "--> FAILED"
                let "pipeline_fail=pipeline_fail+1"
                pipeline_failures="$pipeline_failures\n$t"
        fi
        cd ..
done
cd ..

# SUMMARY
echo
echo "=========== Test Modules Summary ==========="
echo
echo "Success: $module_succ"
echo "Fail:    $module_fail"
echo
echo
if [ $module_fail -gt 0 ];
then
  echo "Failed module tests:"
  printf "$module_failures"
  echo
fi


echo "=========== Test Pipelines Summary ==========="
echo
echo "Success: $pipeline_succ"
echo "Fail:    $pipeline_fail"
echo
if [ $pipeline_fail -gt 0 ];
then
  echo "Failed pipeline tests:"
  printf "$pipeline_failures"
  echo
fi
