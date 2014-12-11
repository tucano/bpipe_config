package bpipeconfig

/**
 * @author tucano
 * Created: Sun Oct 20 19:21:28 CEST 2013
 */
class Pipelines
{
	/**
	 * LIST PIPELINES
	 *
	 * Directory: pipelines
	 * Subdirectories are different categories of pipelines
	 * All pipelines are loaded with this methods
	 * printPipelines print pipelines with the option verbose (-v)
	 * we print the pipeline in subdir extra
	 *
	 */
	static def listPipelines(String pipelines_root)
	{
		if (pipelines_root == null) return null
		def pipelines_dir = new File(pipelines_root)
		if (!pipelines_dir.exists()) return null

		def push_pipeline = { file, extra, list, dir ->

			def about_title = ""
			def info_usage  = []
			def pattern_title = /about title:\s?"(.*)"/
			def pattern_usage = /.*INFO_USAGE:\s?(.*)\s?/

			file.eachLine { line ->
				def matcher = line =~ pattern_title
				if (matcher.matches()) about_title = matcher[0][1]
				matcher = line =~ pattern_usage
				if (matcher.matches()) info_usage << matcher[0][1]
			}

			if (! list.containsKey( dir.getName() ) ) { list[dir.getName()] = [] }
			// return file_name, name of pipeline and description (about_title)
			// if pipeline filename contains "project" is a project pipeline
			// if pipeline filename contains "report" is a report pipeline
			list[dir.getName()].push ([
				file_name: file.name,
				file_path: file.getPath(),
				name: file.name.replaceFirst(~/\.[^\.]+$/, ''),
				about_title: about_title,
				project_pipeline: (file.name ==~ /.*project.*/),
				report_pipeline: ((file.name ==~ /.*report.*/)),
				extra: extra,
				info_usage: info_usage
			])
		}


		def list = [:]

		pipelines_dir.eachDir() { dir ->
			dir.eachFileMatch( ~/.*.groovy/ ) { file ->
				push_pipeline(file, false, list, dir)
			}

			// EXTRA DIR
			def extradir = new File(dir.absolutePath + "/extra")

			if (extradir.exists())
			{
				if (extradir.isDirectory())
				{
					extradir.eachFileMatch( ~/.*.groovy/ ) { file ->
						push_pipeline(file, true, list, dir)
					}
				}
				else
				{
					println Logger.error("Can't load pipelines in extra dir: $extradir ... Aborting")
					System.exit(1)
				}
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
			// skip module file default_paths_gfu.groovy: is not a module, is ajust alist of variables
			name = file.name.replaceAll(/\..*/,"")
			if (name == "default_paths_gfu")
			{
				// DO SOMETHING WITH PATHS?
				// TODO
			}
			else
			{
				source = file.text.replaceAll(/(?s).*doc/,"[").replaceAll(/(?s)([^"]")[^,]?\n.*/,"\$1]").replaceAll(/(?s)\$/,"")
				if (source != "")
				{
					list[name] = shell.evaluate(source)
				}
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
