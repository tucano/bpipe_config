package bpipeconfig

/***********************************************************************
 * SAMPLE SHEET GROOVY TEST CASE
 ***********************************************************************/

class TestSampleSheet extends GroovyTestCase
{
	private sample_sheet
	private multi_sample_sheet
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

	private def multi_expected_sample_list =
	[
		"FCID":"D2A8DACXX",
		"Lane": "3",
		"SampleID":"Sample_test_1",
		"SampleRef":"hg19",
		"Index":"TTAGGC",
		"Description":"niguarda",
		"Control":"N",
		"Recipe":"MeDIP",
		"Operator":"FG",
		"SampleProject":"PI_1A_name"
	]

	void setUp()
	{
		sample_sheet = new File(TestSampleSheet.class.getResource('/SampleSheet.csv').getPath())
		multi_sample_sheet = new File(TestSampleSheet.class.getResource('/MultiSampleSheet.csv').getPath())
	}

	void testSlurpSampleSheet()
	{
		assert Commands.slurpSampleSheet(sample_sheet) == expected_sample_list
	}

	void testSlurpSampleSheetNull()
	{
		assert Commands.slurpSampleSheet(null) == null
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
		assert Commands.validateSampleinfo("FCID=D2A8DACXX,Lane=3,SampleID=B1,SampleRef=hg19,Index=TTAGGC,Description=niguarda,Control=N,Recipe=MeDIP,Operator=FG,SampleProject=PI_1A_name")
	}

	void testValidateSampleInfoInvalidWithEmptyProjectName()
	{
		assert !Commands.validateSampleinfo("FCID=D2A8DACXX,Lane=3,SampleID=B1,SampleRef=hg19,Index=TTAGGC,Description=niguarda,Control=N,Recipe=MeDIP,Operator=FG,SampleProject=")
	}

	void testValidateSampleInfoInvalidWithEmptySampleRef()
	{
		assert !Commands.validateSampleinfo("FCID=D2A8DACXX,Lane=3,SampleID=B1,SampleRef=,Index=TTAGGC,Description=niguarda,Control=N,Recipe=MeDIP,Operator=FG,SampleProject=PI_1A_name")
	}

	void testValidateSampleInfoValidMinimal()
	{
		assert Commands.validateSampleinfo("FCID=,Lane=,SampleID=,SampleRef=hg19,Index=,Description=,Control=,Recipe=,Operator=,SampleProject=PI_1A_name")
	}
}
