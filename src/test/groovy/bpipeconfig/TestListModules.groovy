package bpipeconfig

/***********************************************************************
 * LIST MODULES GROOVY TEST CASE
 ***********************************************************************/

class TestListModules extends GroovyTestCase
{
	private modules_root
	private mock_module_root
	private def expected_module_desc = [
		title: "This is a title",
        desc: """
            This is a
            multi
            line description
        """,
        constraints: "This are constraints",
        author: "pippo@example.com"
	]

	void setUp()
	{
		modules_root = System.getProperty("user.dir") + "/modules"
		mock_module_root = TestListModules.class.getResource('/modules').getPath()
	}

	void testListModulesNull()
	{
		assert Pipelines.listModules(null) == null
	}

	void testListModulesDontExists()
	{
		assert Pipelines.listModules("PIPPO") == null
		assert Pipelines.listModules("") == null
	}

	void testListModules()
	{
		assert Pipelines.listModules(modules_root) != null
	}

	void testListModulesIsAList()
	{
		assert Pipelines.listModules(modules_root) instanceof LinkedHashMap
	}

	void testListModulesContainsKey()
	{
		assert Pipelines.listModules(modules_root).containsKey("align_bwa_gfu")
	}

	void testModuleMockList()
	{
		assert Pipelines.listModules(mock_module_root) != null
	}

	void testModuleMockHouldHaveAkeyWithFileBaseName()
	{
		assert Pipelines.listModules(mock_module_root).containsKey("mock_module")
	}

	void testModuleMockListValueAsMap()
	{
		assert Pipelines.listModules(mock_module_root)["mock_module"] instanceof LinkedHashMap
	}

	void testModuleMockParsingDesc()
	{
		assert Pipelines.listModules(mock_module_root)["mock_module"] == expected_module_desc
	}

	void testModuleMockListGetDescription()
	{
		assert Pipelines.listModules(mock_module_root)["mock_module"]["description"] == expected_module_desc["description"]
	}

	void testAllModulesHaveKeyName()
	{
		Pipelines.listModules(modules_root).each { module, values ->
			assert module != ""
		}
	}

	void testAllModulesHaveATitle()
	{
		Pipelines.listModules(modules_root).each { module, values ->
			assert values["title"] != ""
		}
	}

	void testAllModulesHaveADescription()
	{
		Pipelines.listModules(modules_root).each { module, values ->
			assert values["description"] != ""
		}
	}

	void testAllModulesHaveConstraints()
	{
		Pipelines.listModules(modules_root).each { module, values ->
			assert values["constrains"] != ""
		}
	}

	void testAllModulesHaveAuthor()
	{
		Pipelines.listModules(modules_root).each { module, values ->
			assert values["author"] != ""
		}
	}
}
