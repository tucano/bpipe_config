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
		sample_sheet = new File(TestSampleSheet.class.getResource('/SampleSheet.csv').getPath())
	}

	void testSlurpSampleSheet()
	{
		assert Commands.slurpSampleSheet(sample_sheet) == expected_sample_list
	}

	void testSlurpSampleSheetNull()
	{
		assert Commands.slurpSampleSheet(null) == null
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
