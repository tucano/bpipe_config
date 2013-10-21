package bpipeconfig

/***********************************************************************
 * PROJECT NAME GROOVY TEST CASE
 ***********************************************************************/

class TestProjectName extends GroovyTestCase
{
	void testProjectName()
	{
		assert Commands.validateProjectName("Banfi_25_Medaka")
	}

	void testProjectNameFalse()
	{
		assert !Commands.validateProjectName("25_Medaka")
	}

	void testProjectNameNull()
	{
		assert !Commands.validateProjectName(null)
	}
}