package bpipeconfig

/***********************************************************************
 * PIPELINE INFO GROOVY TEST CASE
 ***********************************************************************/

class TestPipelineInfo extends GroovyTestCase
{
    private pipelines_root
    private mock_pipelines_root
    private pipeline
    private modules_root
    private modules

    void setUp()
    {
        pipelines_root = System.getProperty("user.dir") + "/pipelines"
        mock_pipelines_root = TestListPipelines.class.getResource('/pipelines').getPath()
        pipeline = Pipelines.listPipelines(mock_pipelines_root)["dummy_category"][0]
        modules_root = System.getProperty("user.dir") + "/modules"
        modules = Pipelines.listModules(modules_root)
    }

    void testPipelineInfoNotNull()
    {
        assert Pipelines.pipelineInfo(pipeline, modules) != null
    }

    void testPipelineInfoIsMap()
    {
        assert Pipelines.pipelineInfo(pipeline, modules) instanceof java.util.LinkedHashMap
    }

    void testPipelineTitleNotNull()
    {
        assert Pipelines.pipelineInfo(pipeline, modules)["title"] != null
    }

    void testPipelineStagesNotNull()
    {
        assert Pipelines.pipelineInfo(pipeline, modules)["stages"] != null
    }

    void testPipelineStagesIsAList()
    {
        assert Pipelines.pipelineInfo(pipeline, modules)["stages"] instanceof java.util.LinkedHashMap
    }

    void testPipelineCode()
    {
        assert Pipelines.pipelineInfo(pipeline, modules)["pipe_code"] instanceof String
    }

    void testPipelineStagesSize()
    {
        assert Pipelines.pipelineInfo(pipeline, modules)["stages"].size() == 20
    }

    void testPipelineInfoModule()
    {
        def pipelines = Pipelines.listPipelines(pipelines_root)
        pipelines.each { category, pipes ->
            pipes.each { pipeline ->
                assert Pipelines.pipelineInfo(pipeline, modules) != null
            }
        }
    }

    void testPipelineInfoModuleDescription()
    {
        assert Pipelines.pipelineInfo(pipeline, modules)["stages"]["merge_bam_gfu"]["desc"] != null
    }
}

