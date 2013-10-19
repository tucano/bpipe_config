package bpipeconfig

/***********************************************************************
 * MAIL VALIDATION GROOVY TEST CASE
 ***********************************************************************/

class TestMailValidation extends GroovyTestCase
{
	void testValidateEmail()
	{
		assert BpipeConfig.validateEmail("pippo@pippo.com")
	}

	void testValidateEmailBad()
	{
		assert !BpipeConfig.validateEmail("pippopippo.com")
	}

	void testValidateEmailNull()
	{
		assert !BpipeConfig.validateEmail(null)
	}
}