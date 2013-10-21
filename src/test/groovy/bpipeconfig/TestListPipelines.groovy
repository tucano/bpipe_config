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
		assert Pipelines.listPipelines("") == null
	}

	void testListPipelinesDontExists()
	{
		assert Pipelines.listPipelines("pipes") == null
	}

	void testListPipelines()
	{
		assert Pipelines.listPipelines(pipelines_root) != null
	}
}