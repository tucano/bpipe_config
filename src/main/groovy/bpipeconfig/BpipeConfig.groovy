package bpipeconfig

/***********************************************************************
 * @author      davide Rambaldi <rambaldi.davide@hsr.it>
 * @version     0.1
 * @since       2013
 * 
 *
 ***********************************************************************/

class BpipeConfig 
{

	public static void main(String[] args)
	{
		println this.greet(args[0])
	}

	static String greet(String name)
	{
		"Hello ${name ?: 'stranger'}. Greeting from Groovy."
	}
}