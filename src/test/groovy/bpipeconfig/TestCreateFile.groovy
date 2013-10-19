package bpipeconfig

/***********************************************************************
 * PROJECT NAME GROOVY TEST CASE
 ***********************************************************************/

import groovy.mock.interceptor.*

class TestCreateFile extends GroovyTestCase
{
	private mock

	void setUp()
	{
		mock = new MockFor(File)
	}

	void testCreateFileNull()
	{
		assert !BpipeConfig.createFile(null, null, false, null)
	}

	void testCreateFileEmpty()
	{
		assert !BpipeConfig.createFile("", null, false, null)
	}

	void testCreateFileNameEmpty()
	{
		assert !BpipeConfig.createFile("pippo", null, false, null)
	}

	void testCreateFileNameSuccess()
	{
		mock.demand.exists { false }
		mock.demand.write { "From mock" }
		mock.use { file ->
			assert BpipeConfig.createFile("pippo", "pippo.txt", false, null)
		}
	}

	void testFileExists()
	{
		mock.demand.exists { true }
		mock.use { file ->
			assert BpipeConfig.createFile("pippo", "pippo.txt", false, null)
		}
	}

	void testFileExistsForce()
	{
		mock.demand.exists { true }
		mock.demand.write { "From mock" }
		mock.use { file ->
			assert BpipeConfig.createFile("pippo", "pippo.txt", true, null)
		}
	}
}