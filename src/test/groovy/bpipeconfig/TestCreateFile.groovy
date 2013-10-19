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

	void testCreateFileSuccess()
	{
		mock_file.demand.exists { false }
		mock_file.demand.write { "From mock_file" }
		mock_file.use { file ->
			assert BpipeConfig.createFile("pippo", "pippo.txt", false, null)
		}
	}

	void testFileExists()
	{
		mock_file.demand.exists { true }
		mock_file.use { file ->
			assert BpipeConfig.createFile("pippo", "pippo.txt", false, null)
		}
	}

	void testFileExistsForce()
	{
		mock_file.demand.exists { true }
		mock_file.demand.write { "From mock_file" }
		mock_file.use { file ->
			assert BpipeConfig.createFile("pippo", "pippo.txt", true, null)
		}
	}

	void testCreateFileInDirectories()
	{
		args.each {
			mock_file.demand.exists { false }
			mock_file.demand.write { "From mock_file" }	
		}
		mock_file.use { file ->
			assert BpipeConfig.createFile("pippo", "pippo.txt", false, args)
		}
	}

	void testCreateFileInDirectoriesExists()
	{
		args.each {
			mock_file.demand.exists { true }
		}
		mock_file.use { file ->
			assert BpipeConfig.createFile("pippo", "pippo.txt", false, args)
		}
	}

	void testCreateFileInDirectoriesExistsForce()
	{
		args.each {
			mock_file.demand.exists { true }
			mock_file.demand.write { "From mock_file" }	
		}
		mock_file.use { file ->
			assert BpipeConfig.createFile("pippo", "pippo.txt", true, args)
		}
	}
}