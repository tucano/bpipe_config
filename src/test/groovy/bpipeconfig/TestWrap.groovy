package bpipeconfig

/***********************************************************************
 * PROJECT NAME GROOVY TEST CASE
 ***********************************************************************/

class TestWrap extends GroovyTestCase
{
	void testWrapNull()
	{
		assert Logger.wrap(null) == null
	}

	void testWrapEmpty()
	{
		assert Logger.wrap("") == ""
	}

	void testWrapSmall()
	{
		assert Logger.wrap("pippo") == "pippo"
	}

	void testWrapBig()
	{
		assert Logger.wrap("I love to code in Groovy!", 10) == "I love to\ncode in\nGroovy!"
	}

	void testWrapBigWord()
	{
		assert Logger.wrap("Supermegalongword", 4) == "Supe\nrmeg\nalon\ngwor\nd"
	}

	void testWrapBigWithPad()
	{
		assert Logger.wrap("I love to code in Groovy!", 10, 10) == "I love to\n          code in\n          Groovy!"
	}
}