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

	@Test public void testGreet() 
	{
		assertEquals("Hello mrhaki. Greeting from Groovy.", bpipeconfig.greet("mrhaki"))
	}

	@Test public void testGreetNull() 
	{
		assertEquals("Hello stranger. Greeting from Groovy.", bpipeconfig.greet(null))
	}
}