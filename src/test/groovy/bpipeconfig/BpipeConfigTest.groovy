package bpipeconfig

/***********************************************************************
 * @author      davide Rambaldi <rambaldi.davide@hsr.it>
 * @version     0.1
 * @since       2013
 * 
 *
 ***********************************************************************/

import org.junit.Test
import org.junit.Ignore
import static org.junit.Assert.*

class BpipeConfigTest
{
	final BpipeConfig bpipeconfig = new BpipeConfig()

	@Test public void testVersionInfoNull()
	{
		assertEquals("BpipeConfig GFU Version null", bpipeconfig.versionInfo(null))	
	}

	@Test public void testVersionInfo()
	{
		assertEquals("BpipeConfig GFU Version 1.0", bpipeconfig.versionInfo("1.0"))	
	}

	@Test public void testBuildInfo()
	{
		assertEquals("Built on Sun Oct 13 15:52:49 CEST 2013", bpipeconfig.buildInfo("1381672369681"))	
	}

	@Test public void testBuildInfoNull()
	{
		assertEquals("Built on null", bpipeconfig.buildInfo(null))	
	}

	@Test public void testValidateEmail()
	{
		assertTrue(bpipeconfig.validateEmail("pippo@pippo.com"))
	}

	@Test public void testValidateEmailBad()
	{
		assertFalse(bpipeconfig.validateEmail("pippopippo.com"))
	}

	@Test public void testValidateEmailNull()
	{
		assertFalse(bpipeconfig.validateEmail(null))
	}

	@Test public void testValidateCommand()
	{
		assertTrue(bpipeconfig.validateCommand("pipe"))
	}

	@Test public void testValidateCommandInvalid()
	{
		assertFalse(bpipeconfig.validateCommand("pipssse"))
	}

	@Test public void testValidateCommandNull()
	{
		assertFalse(bpipeconfig.validateCommand(null))
	}

	@Test public void testListPipelinesNull()
	{
		assertTrue(bpipeconfig.listPipelines("") == null)
	}

	@Test public void testListPipelinesNullBis()
	{
		assertTrue(bpipeconfig.listPipelines("pipes") == null)
	}

	@Ignore("not ready yet") @Test public void testListPipelines()
	{
		def pipelines_root = "~/Documents/Devel/Code/bpipe_gfu_pipelines/pipelines"
		assertTrue(bpipeconfig.listPipelines(pipelines_root) != null)
	}

	@Ignore("not file") @Test public void testSlurpSampleSheet()
	{
		def sample_sheet = "./data/SampleSheet.csv"
		assertTrue(bpipeconfig.slurpSampleSheet(sample_sheet) != null)
	}

	@Test public void testSlurpSampleSheetNull()
	{
		def sample_sheet = ""
		assertTrue(bpipeconfig.slurpSampleSheet(sample_sheet) == null)
	}

	@Test public void testProjectName()
	{
		assertTrue(bpipeconfig.projectName("Banfi_25_Medaka"))
	}

	@Test public void testProjectNameFalse()
	{
		assertFalse(bpipeconfig.projectName("25_Medaka"))
	}

	@Test public void testProjectNameNull()
	{
		assertFalse(bpipeconfig.projectName(null))
	}

	@Test public void testWrapNull()
	{
		assertTrue(bpipeconfig.wrap(null) == null)
	}

	@Test public void testWrapEmpty()
	{
		assertTrue(bpipeconfig.wrap("") == "")
	}

	@Test public void testWrapSmall()
	{
		assertEquals("pippo", bpipeconfig.wrap("pippo"))
	}

	@Test public void testWrapBig()
	{
		assertEquals("I love to\ncode in\nGroovy!", bpipeconfig.wrap("I love to code in Groovy!", 10))	
	}

	@Test public void testWrapBigWord()
	{
		assertEquals("Supe\nrmeg\nalon\ngwor\nd", bpipeconfig.wrap("Supermegalongword", 4))	
	}

	@Test public void testWrapBigWithPad()
	{
		assertEquals("I love to\n          code in\n          Groovy!", bpipeconfig.wrap("I love to code in Groovy!", 10, 10))	
	}

	@Test public void testCreateFileNull()
	{
		assertTrue( bpipeconfig.createFile(null, null, null) == false )
	}

	@Test public void testCreateFileEmpty()
	{
		assertTrue( bpipeconfig.createFile("", null, null) == false )
	}

	@Test public void testCreateFileNameEmpty()
	{
		assertTrue( bpipeconfig.createFile("pippo", null, null) == false )
	}

	@Ignore("not ready yet, this create file pollution") @Test public void testCreateFileNameSuccess()
	{
		assertTrue( bpipeconfig.createFile("pippo", "pippo.txt", null) == true )
	}

	@Ignore("not ready yet") @Test public void testCreateFileNameDirectories()
	{

	}
}