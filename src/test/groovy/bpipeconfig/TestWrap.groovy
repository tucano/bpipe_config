package bpipeconfig

/***********************************************************************
 * PROJECT NAME GROOVY TEST CASE
 ***********************************************************************/

class TestWrap extends GroovyTestCase
{
	void testWrapNull()
	{
		assert BpipeConfig.wrap(null) == null
	}

	void testWrapEmpty()
	{
		assert BpipeConfig.wrap("") == ""
	}

	void testWrapSmall()
	{
		assert BpipeConfig.wrap("pippo") == "pippo"
	}

	void testWrapBig()
	{
		assert BpipeConfig.wrap("I love to code in Groovy!", 10) == "I love to\ncode in\nGroovy!"
	}

	void testWrapBigWord()
	{
		assert BpipeConfig.wrap("Supermegalongword", 4) == "Supe\nrmeg\nalon\ngwor\nd"
	}

	void testWrapBigWithPad()
	{
		assert BpipeConfig.wrap("I love to code in Groovy!", 10, 10) == "I love to\n          code in\n          Groovy!"
	}
}