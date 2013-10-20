package bpipeconfig

/**
 * @author tucano
 * Created: Sun Oct 20 19:21:28 CEST 2013
 */
class Pipelines 
{
	/*
	 * LIST PIPELINES
	 */
	static def listPipelines(String pipelines_root)
	{
		def pipelines_location = pipelines_root
		def pipelines_dir = new File(pipelines_location)
		
		if (!pipelines_dir.exists()) return null

		def list = []
		def pattern_title = /about title:\s?"(.*)"/
		pipelines_dir.eachDir() { dir -> 
			dir.eachFileMatch( ~/.*.groovy/ ) { file ->
				def about_title = ""
				file.eachLine { line ->
					def matcher = line =~ pattern_title
					if (matcher.matches()) about_title = matcher[0][1]
				}
				// return file_name, name of pipiline and description (about_title)
				list << [ 
					file_name: file.name,
					file_path: file.getPath(),
					name: file.name.replaceFirst(~/\.[^\.]+$/, ''),
					about_title: about_title
				]
			}
		}
		return list
	}
}