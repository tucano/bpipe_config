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

    void testPipelineStagesKeys()
    {
        def expected = [ "set_stripe_gfu","soapsplice_prepare_headers_gfu","align_soapsplice_gfu",
                         "merge_bam_gfu","merge_junc_gfu","verify_bam_gfu",
                         "bam_flagstat_gfu","mark_duplicates_gfu","rmdup_gfu",
                         "sort_bam_by_name_gfu","htseq_count_gfu","sort_and_convert_sam_gfu",
                         "samtools_index_gfu","rseqc_bam_stat_gfu", "rseqc_gene_coverage_gfu",
                         "samtools_idxstats_gfu","rseqc_reads_distribution_gfu",
                         "rseqc_read_GC_gfu", "rseqc_read_quality_gfu", "rseqc_read_NVC_gfu"]

        for(int i = 0; i < expected.size(); i++) {
            assert Pipelines.pipelineInfo(pipeline, modules)["stages"].keySet()[i] == expected[i]
        }
    }

    void testPipelineStagesSize()
    {
        assert Pipelines.pipelineInfo(pipeline, modules)["stages"].size() == 20
    }

    void testPipelineInfoModule()
    {
        assert Pipelines.pipelineInfo(pipeline, modules)["stages"]["merge_bam_gfu"] != null
    }

    void testPipelineInfoModuleDescription()
    {
        assert Pipelines.pipelineInfo(pipeline, modules)["stages"]["merge_bam_gfu"]["desc"] != null
    }
}

