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
		greet()
	}

	String greet(String name)
	{
		"Hello ${name ?: 'stranger'}. Greeting from Groovy."
	}
}