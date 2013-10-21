package bpipeconfig

/***********************************************************************
 * PROJECT NAME GROOVY TEST CASE
 ***********************************************************************/

import groovy.mock.interceptor.*

class TestCreateFile extends GroovyTestCase
{
	private mock_file
	private args

	void setUp()
	{
		mock_file = new MockFor(File)
		args = ["Sample_1","Sample_2","Sample_3","Sample_4"]
	}

	void testCreateFileNull()
	{
		assert !Commands.createFile(null, null, false)
	}

	void testCreateFileEmpty()
	{
		assert !Commands.createFile("", null, false)
	}

	void testCreateFileNameEmpty()
	{
		assert !Commands.createFile("pippo", null, false)
	}

	void testCreateFileSuccess()
	{
		mock_file.demand.exists { false }
		mock_file.demand.write { "From mock_file" }
		mock_file.use { file ->
			assert Commands.createFile("pippo", "pippo.txt", false)
		}
	}

	void testFileExists()
	{
		mock_file.demand.exists { true }
		mock_file.use { file ->
			assert Commands.createFile("pippo", "pippo.txt", false)
		}
	}

	void testFileExistsForce()
	{
		mock_file.demand.exists { true }
		mock_file.demand.write { "From mock_file" }
		mock_file.use { file ->
			assert Commands.createFile("pippo", "pippo.txt", true)
		}
	}
}