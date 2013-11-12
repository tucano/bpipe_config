package bpipeconfig

/***********************************************************************
 * LIST PIPELINES GROOVY TEST CASE
 ***********************************************************************/

class TestListPipelines extends GroovyTestCase
{
	private pipelines_root
	private mock_pipelines_root

	private def expected_pipe = [
		file_name   : "dummy_pipeline.groovy",
		file_path   : null,
		name        : "dummy_pipeline",
		about_title : "Dummy"
	]


	void setUp()
	{
		pipelines_root = System.getProperty("user.dir") + "/pipelines"
		mock_pipelines_root = TestListPipelines.class.getResource('/pipelines').getPath()
		expected_pipe["file_path"] = TestListPipelines.class.getResource('/pipelines/dummy_category/dummy_pipeline.groovy').getPath()
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

	void testReturnList()
	{
		assert Pipelines.listPipelines(pipelines_root) instanceof java.util.LinkedHashMap
	}

	void testShouldContainsKey()
	{
		assert Pipelines.listPipelines(pipelines_root)["rseqc"] != null
	}

	void testMockPipelines()
	{
		assert Pipelines.listPipelines(mock_pipelines_root) != null
	}

	void testMockSinglePipelineNotNull()
	{
		assert Pipelines.listPipelines(mock_pipelines_root)["dummy_category"] != null
	}

	void testMockSinglePipelineSizeOne()
	{
		assert Pipelines.listPipelines(mock_pipelines_root)["dummy_category"].size == 1
	}

	void testMockSinglePipelineExpected()
	{
		assert Pipelines.listPipelines(mock_pipelines_root)["dummy_category"][0] == expected_pipe
	}
}
