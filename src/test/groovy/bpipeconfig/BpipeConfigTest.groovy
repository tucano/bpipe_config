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
		assertEquals("BpipeConfig GFU Version null. Built on null", bpipeconfig.versionInfo(null, null))	
	}

	@Test public void testVersionInfo()
	{
		assertEquals("BpipeConfig GFU Version 1.0. Built on Sun Oct 13 15:52:49 CEST 2013", bpipeconfig.versionInfo("1.0", "1381672369681"))	
	}
}