package bpipeconfig

/***********************************************************************
 * @author      davide Rambaldi <rambaldi.davide@hsr.it>
 * @version     0.1
 * @since       2013
 * 
 *
 ***********************************************************************/

import org.junit.Test
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

	@Test public void testListPipelines()
	{
		def pipelines_root = "~/Documents/Devel/Code/bpipe_gfu_pipelines/pipelines"
		assertTrue(bpipeconfig.listPipelines(pipelines_root) == null)
	}

	@Test public void testSlurpSampleSheet()
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
}