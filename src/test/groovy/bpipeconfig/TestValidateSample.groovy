package bpipeconfig

/***********************************************************************
 * VALIDATE SAMPLE GROOVY TEST CASE
 ***********************************************************************/

class TestValidateSample extends GroovyTestCase
{
	private def sample =
	[
		"FCID":"D2A8DACXX",
		"Lane": "3",
		"SampleID":"Sample_test_1",
		"SampleRef":"hg19",
		"Index":"TTAGGC",
		"Description":"description",
		"Control":"N",
		"Recipe":"MeDIP",
		"Operator":"FG",
		"SampleProject":"PI_1A_name"
	]

	private def sample_badkeys =
	[
		"F_CID":"D2A8DACXX",
		"La_ne": "3",
		"SampleID":"Sample_test_1",
		"SampleRef":"hg19",
		"Index":"TTAGGC",
		"Description":"description",
		"Control":"N",
		"Recipe":"MeDIP",
		"Operator":"FG",
		"SampleProject":"PI_1A_name"
	]

	private def sample_truncated =
	[
		"FCID":"D2A8DACXX",
		"Lane": "3",
		"SampleID":"Sample_test_1"
	]

	private def sample_bad_pname =
	[
		"FCID":"D2A8DACXX",
		"Lane": "3",
		"SampleID":"Sample_test_1",
		"SampleRef":"hg19",
		"Index":"TTAGGC",
		"Description":"description",
		"Control":"N",
		"Recipe":"MeDIP",
		"Operator":"FG",
		"SampleProject":"54_PI"
	]

	void testValidateSheetNull()
	{
		assert Commands.validateSample(null) == false
	}

	void testValidateSheetBadClass()
	{
		assert Commands.validateSample("pippo") == false
	}

	// CHECK ON HEADERS REMOVED: headers are changing ...
	// void testValidateSheetBadKeysSize()
	// {
	// 	assert Commands.validateSample(sample_truncated) == false
	// }
	// void testValidateSheetBadKeys()
	// {
	// 	assert Commands.validateSample(sample_badkeys) == false
	// }

	void testValidateSheetBadProjectName()
	{
		assert Commands.validateSample(sample_bad_pname) == false
	}

	void testValidateSheetValid()
	{
		assert Commands.validateSample(sample) == true
	}
}
