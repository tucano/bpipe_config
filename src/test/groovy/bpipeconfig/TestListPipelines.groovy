package bpipeconfig

/***********************************************************************
 * LIST PIPELINES GROOVY TEST CASE
 ***********************************************************************/

class TestListPipelines extends GroovyTestCase
{
	private pipelines_root
	
	void setUp()
	{
		pipelines_root = System.getProperty("user.dir") + "/pipelines"
	}

	void testListPipelinesNull()
	{
		assert BpipeConfig.listPipelines("") == null
	}

	void testListPipelinesNullBis()
	{
		assert BpipeConfig.listPipelines("pipes") == null
	}

	void testListPipelines()
	{
		assert BpipeConfig.listPipelines(pipelines_root) != null
	}
}