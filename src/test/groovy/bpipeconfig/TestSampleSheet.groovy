package bpipeconfig

/***********************************************************************
 * SAMPLE SHEET GROOVY TEST CASE
 ***********************************************************************/

class TestSampleSheet extends GroovyTestCase
{
	private sample_sheet
	private def expected_sample_list = 
	[[
		"FCID":"D2A8DACXX", 
		"Lane": "3", 
		"SampleID":"B1", 
		"SampleRef":"hg19", 
		"Index":"TTAGGC", 
		"Description":"niguarda", 
		"Control":"N", 
		"Recipe":"MeDIP", 
		"Operator":"FG", 
		"SampleProject":"Martinelli_10_Test"
	]]

	void setUp()
	{
		sample_sheet = TestSampleSheet.class.getResource('/SampleSheet.csv').getPath()
	}

	void testSlurpSampleSheet()
	{
		assert BpipeConfig.slurpSampleSheet(sample_sheet) == expected_sample_list
	}

	void testSlurpSampleSheetNull()
	{
		assert BpipeConfig.slurpSampleSheet("") == null
	}
}