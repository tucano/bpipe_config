package bpipeconfig

/***********************************************************************
 * VERSION AND BUILDDATE GROOVY TEST CASE
 ***********************************************************************/

class TestVersionAndBuild extends GroovyTestCase
{
	void testVersionInfoNull()
	{
		assert BpipeConfig.versionInfo() == "BpipeConfig GFU Version null"
	}

	void testVersionInfo()
	{
		assert BpipeConfig.versionInfo("1.0") == "BpipeConfig GFU Version 1.0"
	}

	void testBuildInfo()
	{
		assert BpipeConfig.buildInfo("1381672369681") == "Built on Sun Oct 13 15:52:49 CEST 2013"
	}

	void testBuildInfoNull()
	{
		assert BpipeConfig.buildInfo(null) == "Built on null"
	}
}

