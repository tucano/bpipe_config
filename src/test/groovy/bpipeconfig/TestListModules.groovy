package bpipeconfig

/***********************************************************************
 * LIST MODULES GROOVY TEST CASE
 ***********************************************************************/

class TestListModules extends GroovyTestCase
{
	private modules_root

	void setUp()
	{
		modules_root = System.getProperty("user.dir") + "/modules"
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
}