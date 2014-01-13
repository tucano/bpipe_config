# support for sh pipelines tests

# LOGGER
function err() {
	TESTNAME=`pwd | sed -e 's/.*\///'`
	echo
	echo -e "${TESTNAME}\t[ERROR] : $1"
	echo
	exit 1
}

function success() {
	TESTNAME=`pwd | sed -e 's/.*\///'`
	echo -e "${TESTNAME}\t[SUCCESS]"
	exit 0
}

# RUNNER
function run() {
	SCRIPT=$1
	shift
	bpipe run -r $SCRIPT $* > test.out 2>&1
}

# check test output
function checkTestOut() {
	[ -e test.out ] || err "Can''t find test output file: test.out"
	grep 'Pipeline failed!' test.out 1>/dev/null 2>&1
	[ $? == 0 ] && err "Pipeline failed!"
}

# FILE EXISTS
function exists() {
  for i in $*;
  do
    [ -e "$i" ] || err "Failed to find expected file $i"
  done
}

function notexists() {
  for i in $*;
  do
    [ -e "$i" ] && err "Found unexpected file $i"
  done
}
