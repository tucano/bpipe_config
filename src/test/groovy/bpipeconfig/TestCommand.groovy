package bpipeconfig

/***********************************************************************
 * COMMAND GROOVY TEST CASE
 ***********************************************************************/

class TestCommand extends GroovyTestCase
{
	void testValidateCommand()
	{
		assert BpipeConfig.validateCommand("pipe")
	}

	void testValidateCommandInvalid()
	{
		assert !BpipeConfig.validateCommand("pipssse")
	}

	void testValidateCommandNullToFalse()
	{
		assert !BpipeConfig.validateCommand(null)
	}
}