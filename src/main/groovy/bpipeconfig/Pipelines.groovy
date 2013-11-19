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
				// if pipeline filename contains "project" is a project pipeline
				list[dir.getName()].push ([
					file_name: file.name,
					file_path: file.getPath(),
					name: file.name.replaceFirst(~/\.[^\.]+$/, ''),
					about_title: about_title,
					project_pipeline: (file.name ==~ /.*project.*/)
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

	/**
	 * PIPELINE INFO
	 */
	static def pipelineInfo(def pipeline, def modules)
	{
		def collect_stages = { pipe_text ->
			def stages = [:]
			(pipe_text =~ /[A-Za-z_]*_gfu/).collect().each { module_name ->
				stages["$module_name"] = modules["$module_name"]
			}
			return stages
		}

		// pipeline is already validated (it exists)
		String pipeline_text = new File(pipeline["file_path"]).text
		String pipe_code = pipeline_text.replaceAll(/(?s).*Bpipe.run \{/,"").replaceAll(/\}/,"")

		def pipeline_info = [
			"title"     : pipeline["about_title"],
			"pipe_code" : pipe_code,
			"stages"    : collect_stages(pipe_code)
		]

		return pipeline_info
	}
}
