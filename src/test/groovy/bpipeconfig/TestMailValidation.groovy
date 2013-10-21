package bpipeconfig

/***********************************************************************
 * MAIL VALIDATION GROOVY TEST CASE
 ***********************************************************************/

class TestMailValidation extends GroovyTestCase
{
	void testValidateEmail()
	{
		assert Commands.validateEmail("pippo@pippo.com")
	}

	void testValidateEmailBad()
	{
		assert !Commands.validateEmail("pippopippo.com")
	}

	void testValidateEmailNull()
	{
		assert !Commands.validateEmail(null)
	}
}