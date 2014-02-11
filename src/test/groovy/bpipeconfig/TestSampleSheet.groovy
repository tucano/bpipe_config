package bpipeconfig

/***********************************************************************
 * SAMPLE SHEET GROOVY TEST CASE
 ***********************************************************************/

class TestSampleSheet extends GroovyTestCase
{
	private def expected_sample_list =
	[[
		"FCID":"D2A8DACXX",
		"Lane": "3",
		"SampleID":"B1",
		"SampleRef":"hg19",
		"Index":"TTAGGC",
		"Description":"description",
		"Control":"N",
		"Recipe":"MeDIP",
		"Operator":"FG",
		"SampleProject":"Martinelli_10_Test"
	]]

	private def multi_expected_sample_list =
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

	private def minimal_expected =
	[
		"FCID": "",
		"Lane": "",
		"SampleID":"B1",
		"SampleRef":"hg19",
		"Index": "",
		"Description": "",
		"Control": "",
		"Recipe": "",
		"Operator": "",
		"SampleProject":"Martinelli_10_Test"
	]

	private sample_sheet
	private sample_sheet_error
	private multi_sample_sheet
	private multi_sample_sheet_error
	private minimal_sample_sheet



	void setUp()
	{
		sample_sheet = new File(TestSampleSheet.class.getResource('/SampleSheet.csv').getPath())
		sample_sheet_error = new File(TestSampleSheet.class.getResource('/SampleSheetErrorProject.csv').getPath())
		multi_sample_sheet = new File(TestSampleSheet.class.getResource('/MultiSampleSheet.csv').getPath())
		minimal_sample_sheet = new File(TestSampleSheet.class.getResource('/SampleSheetMinimal.csv').getPath())
		multi_sample_sheet_error = new File(TestSampleSheet.class.getResource('/MultipleSampleSheetErrorProject.csv').getPath())
	}

	void testSlurpSampleSheet()
	{
		assert Commands.slurpSampleSheet(sample_sheet) == expected_sample_list
	}

	void testSlurpSampleSheetNull()
	{
		assert Commands.slurpSampleSheet(null) == null
	}

	void testSlurpSampleSheetErrorProject()
	{
		assert Commands.slurpSampleSheet(sample_sheet_error) == null
	}

	void testMultiSlurpSampleSheetErrorProject()
	{
		assert Commands.slurpSampleSheet(multi_sample_sheet_error) == null
	}

	void testMultiSamplesSheetNotNull()
	{
		assert Commands.slurpSampleSheet(multi_sample_sheet) != null
	}

	void testMultiSamplesSheetSize()
	{
		assert Commands.slurpSampleSheet(multi_sample_sheet).size == 20
	}

	void testMultiSamplesSheetFirstEntry()
	{
		assert Commands.slurpSampleSheet(multi_sample_sheet)[0] == multi_expected_sample_list
	}

	void testMultiSamplesSheetClass()
	{
		assert Commands.slurpSampleSheet(multi_sample_sheet) instanceof java.util.ArrayList
	}

	void testLoopInSamples()
	{
		Commands.slurpSampleSheet(multi_sample_sheet).each { sample ->
			assert sample instanceof java.util.Map
		}
	}

	void testRecoverSampleRef()
	{
		Commands.slurpSampleSheet(multi_sample_sheet).each { sample ->
			assert sample["SampleRef"] == "hg19"
		}
	}

	void testMinimalSampleSheet()
	{
		assert Commands.slurpSampleSheet(minimal_sample_sheet) != null
	}

	void testMinimalSampleSheetExpectedSize()
	{
		assert Commands.slurpSampleSheet(minimal_sample_sheet).size == 5
	}

	void testMinimalSampleSheetExpected()
	{
		assert Commands.slurpSampleSheet(minimal_sample_sheet)[0] == minimal_expected
	}

	void testValidateSampleInfoNull()
	{
		assert !Commands.validateSampleinfo(null)
	}

	void testValidateSampleInfoEmpty()
	{
		assert !Commands.validateSampleinfo("")
	}

	void testValidateSampleInfoInvalid()
	{
		assert !Commands.validateSampleinfo("PIPPO")
	}

	void testValidateSampleInfoInvalidWithKey()
	{
		assert !Commands.validateSampleinfo("PIPPO=PLUTO")
	}

	void testValidateSampleInfoValid()
	{
		assert Commands.validateSampleinfo("FCID=D2A8DACXX,Lane=3,SampleID=B1,SampleRef=hg19,Index=TTAGGC,Description=description,Control=N,Recipe=MeDIP,Operator=FG,SampleProject=PI_1A_name")
	}

	void testValidateSampleInfoInvalidWithEmptyProjectName()
	{
		assert !Commands.validateSampleinfo("FCID=D2A8DACXX,Lane=3,SampleID=B1,SampleRef=hg19,Index=TTAGGC,Description=description,Control=N,Recipe=MeDIP,Operator=FG,SampleProject=")
	}

	void testValidateSampleInfoInvalidWithEmptySampleRef()
	{
		assert !Commands.validateSampleinfo("FCID=D2A8DACXX,Lane=3,SampleID=B1,SampleRef=,Index=TTAGGC,Description=description,Control=N,Recipe=MeDIP,Operator=FG,SampleProject=PI_1A_name")
	}

	void testValidateSampleInfoValidMinimal()
	{
		assert Commands.validateSampleinfo("FCID=,Lane=,SampleID=1A,SampleRef=hg19,Index=,Description=,Control=,Recipe=,Operator=,SampleProject=PI_1A_name")
	}
}
