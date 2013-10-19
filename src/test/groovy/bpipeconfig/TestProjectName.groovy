package bpipeconfig

/***********************************************************************
 * PROJECT NAME GROOVY TEST CASE
 ***********************************************************************/

class TestProjectName extends GroovyTestCase
{
	void testProjectName()
	{
		assert BpipeConfig.projectName("Banfi_25_Medaka")
	}

	void testProjectNameFalse()
	{
		assert !BpipeConfig.projectName("25_Medaka")
	}

	void testProjectNameNull()
	{
		assert !BpipeConfig.projectName(null)
	}
}