package bpipeconfig

/***********************************************************************
 * COMMAND GROOVY TEST CASE
 ***********************************************************************/

class TestCommand extends GroovyTestCase
{
	void testValidateCommand()
	{
		assert Commands.validateCommand("pipe")
	}

	void testValidateCommandInvalid()
	{
		assert !Commands.validateCommand("pipssse")
	}

	void testValidateCommandNullToFalse()
	{
		assert !Commands.validateCommand(null)
	}
}