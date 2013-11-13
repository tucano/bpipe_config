package bpipeconfig

/**
 * @author tucano
 * Created: Sun Oct 20 19:21:28 CEST 2013
 */
class Pipelines
{
	/**
	 * LIST PIPELINES
	 */
	static def listPipelines(String pipelines_root)
	{
		if (pipelines_root == null) return null
		def pipelines_dir = new File(pipelines_root)
		if (!pipelines_dir.exists()) return null

		def list = [:]
		def pattern_title = /about title:\s?"(.*)"/
		pipelines_dir.eachDir() { dir ->
			dir.eachFileMatch( ~/.*.groovy/ ) { file ->
				def about_title = ""
				file.eachLine { line ->
					def matcher = line =~ pattern_title
					if (matcher.matches()) about_title = matcher[0][1]
				}
				if (! list.containsKey( dir.getName() ) ) { list[dir.getName()] = [] }
				// return file_name, name of pipeline and description (about_title)
				list[dir.getName()].push ([
					file_name: file.name,
					file_path: file.getPath(),
					name: file.name.replaceFirst(~/\.[^\.]+$/, ''),
					about_title: about_title
				])
			}
		}
		return list
	}

	/**
	 * LIST MODULES
	 */
	static def listModules(String modules_root)
	{
		if (modules_root == null) return null
		def modules_dir = new File(modules_root)
		if (!modules_dir.exists()) return null
		GroovyShell shell = new GroovyShell()
		def list = [:]
		String source
		String name
		modules_dir.eachFile { file ->
			name = file.name.replaceAll(/\..*/,"")
			source = file.text.replaceAll(/(?s).*doc/,"[").replaceAll(/(?s)([^"]")[^,]?\n.*/,"\$1]").replaceAll(/(?s)\$/,"")
			if (source != "")
			{
				list[name] = shell.evaluate(source)
			}
		}
		return list
	}
}
