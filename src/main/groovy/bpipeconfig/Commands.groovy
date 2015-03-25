package bpipeconfig

/**
 * @author tucano
 * Created: Sat Oct 19 16:32:40 CEST 2013
 */

import groovy.text.SimpleTemplateEngine
import static groovy.json.JsonOutput.*
import bpipeconfig.Logger

class Commands
{
	final static String[] available_commands = [
		"config","sheet","pipe","project",
		"info","clean","report",
		"recover","jvm","smerge", "json"]

	public static def pipeline
	public static def samples
	public static def engine = new SimpleTemplateEngine()

	public static config(def args)
	{
		// Load config templates
		def file_config  = new File("${BpipeConfig.bpipe_config_home}/templates/bpipe.config.template")
		def local_project_name = null
		def binding_config, template_config, sample_sheet

		// CHECK PROJECT NAME OR GENERATE ONE using USERNAME_DIR
		def check_project_name = { dir ->

			sample_sheet = new File("${dir}/${BpipeConfig.sample_sheet_name}")

			// check for defined global project name
			if (BpipeConfig.project_name)
			{
				if (BpipeConfig.verbose) println Logger.info("using project name: ${BpipeConfig.project_name}")
			}
			// check for local SampleSheet.csv
			else if (sample_sheet.exists())
			{
				samples =  slurpSampleSheet(sample_sheet)
				if (samples)
				{
					BpipeConfig.project_name = samples[0]["SampleProject"]
					if (BpipeConfig.verbose) {
						println Logger.info("using project name from ${BpipeConfig.sample_sheet_name}: ${BpipeConfig.project_name}")
					}
				}
				else
				{
					println Logger.error("Error in ${BpipeConfig.sample_sheet_name} file ... Aborting")
					System.exit(1)
				}
			}
			// GENERATE PROJECT NAME
			else
			{
				String current_dir = BpipeConfig.working_dir.replaceFirst(/.*\//,"")
				BpipeConfig.project_name = "${BpipeConfig.user_name}_${current_dir}"

				if (BpipeConfig.verbose) {
					println Logger.info(
						"""
							Using auto-generated project name: ${BpipeConfig.project_name}.
							You can use the option -P to set a project name or the command: sheet to generate a SampleSheet.scv.
						""".stripIndent().trim()
					)
				}
			}
		}

		// GENERATE CONFIG FILE
		def generate_config = { dir ->
			// GENERATE SINGLE CONFIG
			binding_config = [
				"executor"                 : "pbspro",
				"username"                 : BpipeConfig.user_name,
				"queue"                    : "workq",
				"project_name"             : BpipeConfig.project_name,
				"user_email"               : BpipeConfig.user_email,
				"bpipe_notifier"           : BpipeConfig.email_config?.bpipe_notifier,
				"bpipe_notifier_password"  : BpipeConfig.email_config?.bpipe_notifier_password
			]
			template_config  = engine.createTemplate(file_config.text).make(binding_config)
			if ( ! createFile(template_config.toString(), "${dir}/bpipe.config", BpipeConfig.force) ) {
				println Logger.error("Problems creating bpipe.config file in dir ${dir}")
			}
		}

		// DIR MODE
		if ( args && args.size > 0 )
		{
			// check if args are dirs
			checkDir(args)
			// loop in dirs
			args.each { dir ->
				check_project_name(dir)
				generate_config(dir)
			}
		}
		// PWD MODE
		else
		{
			check_project_name(BpipeConfig.working_dir)
			generate_config(BpipeConfig.working_dir)
		}
	}

	public static sheet(def args)
	{
		if (args.empty)
		{
			println Logger.error("Command sheet need a SAMPLE INFO as first argument.")
			println Logger.info("FORMAT: FCID=D2A8DACXX,Lane=3,SampleID=B1,SampleRef=hg19,Index=TTAGGC,Description=description,Control=N,Recipe=MeDIP,Operator=FG,SampleProject=PI_1A_name")
			System.exit(1)
		}

		String sample_info = args.remove(0)

		def info = []
		def header = []
		StringBuffer out

		if (validateSampleinfo(sample_info) )
		{
			sample_info.split(",").each { item ->
			    def terms = item.split("=")
			    header.push terms[0]
			    info.push terms[1]
			}
			out = new StringBuffer()
			out << header.join(",") << "\n" << info.join(",")
		}
		else
		{
			println Logger.error("Error validating SampleSheet for command: sheet.")
			println Logger.error("Malformed INFO: $sample_info")
			System.exit(1)
		}

		def create_sample_sheet = { dir ->
			if ( ! createFile(out.toString(), "${dir}/${BpipeConfig.sample_sheet_name}",  BpipeConfig.force) ) {
				println Logger.error("Problems creating ${BpipeConfig.sample_sheet_name} in dir $dir")
			}
		}

		// DIR MODE
		if ( args && args.size > 0 )
		{
			checkDir(args)
			// Loops in dirs
			args.each { dir ->
				create_sample_sheet(dir)
			}
		}
		// PWD MODE
		else
		{
			create_sample_sheet(BpipeConfig.working_dir)
		}
	}

	public static pipe(def args)
	{
		if (args.empty) {
			println Logger.error("Command pipe need a pipeline name as first argument.")
			if (BpipeConfig.verbose) println Logger.info("Use: bpipe-config -p to get a list of avaliable pipelines")
			System.exit(1)
		}
		String pipeline_name = args.remove(0)

		// Validate pipeline
		def pipeline
		BpipeConfig.pipelines.each { category, pipes ->
			pipes.each { pipe ->
				if (pipe["name"] == pipeline_name) {
					pipeline = pipe
				}
			}
		}

		if (pipeline == null) {
			println Logger.error("can't find pipeline $pipeline_name in pipelines.")
			System.exit(1)
		}

		// check for bpipe.config and create it
		def check_bpipe_config = { dir ->
			if ( ! new File("${dir}/bpipe.config").exists() ) { config([dir]) }
		}

		def usage = ""
		def generate_pipeline = { dir ->

			def sample_sheet = new File("${dir}/${BpipeConfig.sample_sheet_name}")

			// PROJECT SCOPE: no sample sheet needed!
			if (pipeline["project_pipeline"])
			{
				// Loop in Sample dirs
				samples = []
				args.each { sample_dir ->
					def subsample_sheet = new File("${sample_dir}/${BpipeConfig.sample_sheet_name}")
					if ( subsample_sheet.exists() )
					{
						def sample = slurpSampleSheet(subsample_sheet)
						if (sample)
						{
							samples = (samples << sample).flatten()
						}
						else
						{
							println Logger.error("Error in ${BpipeConfig.sample_sheet_name} files ... Aborting")
							System.exit(1)
						}
					}
					else
					{
						if (BpipeConfig.skip_sample_sheet)
						{
							println Logger.warn("No ${BpipeConfig.sample_sheet_name} files ... continue due to skip sample sheet option")
							if (BpipeConfig.verbose) println Logger.info("You can use the command: sheet to generate a SampleSheet.scv")
						}
						else
						{
							println Logger.error("Error in ${BpipeConfig.sample_sheet_name} files ... Aborting")
							if (BpipeConfig.verbose) println Logger.info("You can use the command: sheet to generate a SampleSheet.scv")
							System.exit(1)
						}
					}
				}

				// PRINT SAMPLES TO FILE
				if (BpipeConfig.verbose) println Logger.info("Creating Project ${BpipeConfig.sample_sheet_name}: ${dir}/${BpipeConfig.sample_sheet_name}")

				def samples_csv = new StringBuffer()

				samples_csv << "${samples[0].keySet().join(",")}\n"
				samples.each { sample ->
					samples_csv << "${sample.values().toList().join(",")}\n"
				}

				if ( ! createFile(samples_csv.toString(), "${dir}/${BpipeConfig.sample_sheet_name}", BpipeConfig.force) )
				{
					println Logger.error("Problems creating Project ${BpipeConfig.sample_sheet_name} file!")
				}

				// PROJECT NAME:
				if (BpipeConfig.project_name)
				{
					if (BpipeConfig.verbose) println Logger.info("Using project name: ${BpipeConfig.project_name}")
				}
				else
				{
					BpipeConfig.project_name = samples[0]["SampleProject"]
				}
			}
			// SAMPLE SCOPE and REPORT PIPELINE
			else
			{
				if ( sample_sheet.exists() )
				{
					samples =  slurpSampleSheet(sample_sheet)
					if (!samples)
					{
						println Logger.error("Error in ${BpipeConfig.sample_sheet_name} files ... Aborting")
						System.exit(1)
					}

					if (BpipeConfig.project_name)
					{
						if (BpipeConfig.verbose) println Logger.info("Using project name: ${BpipeConfig.project_name}")
					}
					else
					{
						BpipeConfig.project_name = samples[0]["SampleProject"]
					}
				}
				else
				{
					if (BpipeConfig.skip_sample_sheet)
					{
						println Logger.warn("No ${BpipeConfig.sample_sheet_name} files ... continue due to skip sample sheet option")
						if (BpipeConfig.verbose) println Logger.info("You can use the command: sheet to generate a SampleSheet.scv")
					}
					else
					{
						println Logger.error("Error in ${BpipeConfig.sample_sheet_name} files ... Aborting")
						if (BpipeConfig.verbose) println Logger.info("You can use the command: sheet to generate a SampleSheet.scv")
						System.exit(1)
					}
				}
			}

			String pipeline_text = new File(pipeline["file_path"]).text
			String pipeline_filename = "${pipeline["file_name"]}"

			if (pipeline["report_pipeline"])
			{
				def pattern = /(?m)^REPORT_DATA_DIR\s*=\s*"(.*)"\s*/
				def matcher = (pipeline_text =~ pattern)
				def report_data_dir = matcher.hasGroup() ? matcher[0][1].trim() : 'report_data'
				println Logger.message("""
					Report pipeline, preparing intermediate files in directory: ${System.getProperty("user.dir")}/report_data.
					Report Data Dir is: $report_data_dir.
				""".stripIndent().trim())

				def data_dir = new File(report_data_dir)
				if (data_dir.isDirectory())
				{
					println Logger.message("Report data directory: ${data_dir.getAbsolutePath()} already exists")
				}
				else
				{
					println Logger.message("Creating report data directory: ${data_dir.getAbsolutePath()}")
					data_dir.mkdir()
				}

				// STATS FILE
				pattern = /(?m)\/{2}\s*STATS:(.*)/
				matcher = (pipeline_text =~ pattern)
				def stats_type = ''
				stats_type = matcher.hasGroup() ? matcher[0][1].trim() : ''
				println Logger.message("Stats file type is: $stats_type")
				if (stats_type != '')
				{
					if ( ! createFile(new File("${BpipeConfig.bpipe_config_home}/templates/reports/${stats_type}/stats.groovy").text, "${report_data_dir}/stats.groovy", BpipeConfig.force) ) {
						println Logger.error("Problems creating stats.groovy file!")
					}
				}
				// RATIONALE FILE
				if (! createFile(new File("${BpipeConfig.bpipe_config_home}/templates/reports/rationale.md").text, "${report_data_dir}/rationale.md", BpipeConfig.force)) {
					println Logger.error("Problems creating rationale.md file!")
				}
			}

			// GENERATE USAGE INFO
			def pattern = /(?m)\/{2}\s*USAGE:(.*)/
			def matcher = (pipeline_text =~ pattern)
			usage = matcher.hasGroup() ? matcher[0][1].trim() : 'bpipe run -r $pipeline_filename *'
			def binding_usage = [ "pipeline_filename" : pipeline_filename ]
			usage = engine.createTemplate(usage).make(binding_usage)

			// ADD bpipe.config
			check_bpipe_config(dir)

			// GFU_ENVIRONMENT
			def file_gfu_env = new File("${BpipeConfig.bpipe_config_home}/templates/gfu_environment.sh.template")
			def binding_gfu_env
			def template_gfu_env

			if (pipeline["project_pipeline"])
			{
				// IF force and no sample continue anyway with NA
				if ((!samples) && (BpipeConfig.force))
				{
					samples[0] = [
						"FCID"      : "NA",
						"SampleRef" : "NA",
						"SampleID"  : "NA",
						"Lane"      : "NA"
					]
				}

				// first add PROJECT and REFERENCE to "//--BPIPE_ENVIRONMENT_HERE--"
				def project_info = """
					PROJECTNAME = "${BpipeConfig.project_name}"
					REFERENCE   = "${samples[0]["SampleRef"]}"
				""".stripIndent().trim()
				pipeline_text = pipeline_text.replaceAll("//--BPIPE_ENVIRONMENT_HERE--", project_info)

				// for each sample add a gfu enviroment file
				// IF force and no sample continue anyway
				if ((samples) && (!BpipeConfig.skip_sample_sheet))
				{
					args.each { sample_dir ->
						def single_sample_sheet = new File("${sample_dir}/${BpipeConfig.sample_sheet_name}")
						def single_sample = slurpSampleSheet(single_sample_sheet)
						binding_gfu_env = [
					    	"project_name"    : '"' + single_sample[0]["SampleProject"] + '"',
					    	"reference"       : '"' + single_sample[0]["SampleRef"] + '"',
					    	"experiment_name" : '"' + single_sample[0]["FCID"] + "_" + single_sample[0]["SampleID"] + '"',
					    	"fcid"            : '"' + single_sample[0]["FCID"] + '"',
					    	"lane"            : '"' + single_sample[0]["Lane"] + '"',
					    	"sampleid"        : '"' + single_sample[0]["SampleID"] + '"'
						]
						// GENERATION OF gfu_enviroment.sh FILE with Per pipeline options
						template_gfu_env = engine.createTemplate(file_gfu_env.text).make(binding_gfu_env).toString()

						// CREATE gfu_environment.sh
						if ( ! createFile(template_gfu_env, "${sample_dir}/gfu_environment.sh", BpipeConfig.force) ) {
							println Logger.error("Problems creating gfu_environment.sh file!")
						}
					}
				}

			}
			else
			{
				// ALTERNATIVE TOKENS:
				//
				// //--BPIPE_SAMPLE_INFO_HERE--  <-- is a single sample pipeline
				// //--BPIPE_ENVIRONMENT_HERE--  <-- project_name and reference ONLY
				//
				// FOR MULTIPLE SAMPLES PIPELINES I USE THE SampleSheet.csv in current directory:
				// For single sample stages I use the SampleSheet.csv in Sample dir.
				// For multiple sample stages I will use the SampleSheet.csv in Project dir.
				//
				// FOR MULTIPLE SAMPLES PIPELINES IN SAME DIR NO gfu_environment.sh

				// SINGLE SAMPLE
				if (pipeline_text.contains("//--BPIPE_SAMPLE_INFO_HERE--"))
				{
					// IF force and no sample continue anyway with NA
					if ((!samples) && (BpipeConfig.skip_sample_sheet))
					{
						samples = []
						samples[0] = [
							"FCID"      : "NA",
							"SampleRef" : "NA",
							"SampleID"  : "NA",
							"Lane"      : "NA"
						]
					}

					binding_gfu_env = [
					    "project_name"    : '"' + BpipeConfig.project_name + '"',
					    "reference"       : '"' + samples[0]["SampleRef"] + '"',
					    "experiment_name" : '"' + samples[0]["FCID"] + "_" + samples[0]["SampleID"] + '"',
					    "fcid"            : '"' + samples[0]["FCID"] + '"',
					    "lane"            : '"' + samples[0]["Lane"] + '"',
					    "sampleid"        : '"' + samples[0]["SampleID"] + '"'
					]

					// GENERATION OF gfu_enviroment.sh FILE with Per pipeline options
					template_gfu_env = engine.createTemplate(file_gfu_env.text).make(binding_gfu_env).toString()

					// CREATE gfu_environment.sh
					if ( ! createFile(template_gfu_env, "${dir}/gfu_environment.sh", BpipeConfig.force) ) {
						println Logger.error("Problems creating gfu_environment.sh file!")
					}

					// WRITING THE GFU_ENVIROMENT FILE AS GROOVY VARS in the pipeline
					pipeline_text = pipeline_text.replaceAll( "//--BPIPE_SAMPLE_INFO_HERE--", template_gfu_env )
				}
				// MULTIPLE SAMPLE
				else
				{
					// IF force and no sample continue anyway with NA
					if ((!samples) && (BpipeConfig.skip_sample_sheet))
					{
						samples = []
						samples[0] = [
							"FCID"      : "NA",
							"SampleRef" : "NA",
							"SampleID"  : "NA",
							"Lane"      : "NA"
						]
					}

					// first add PROJECT and REFERENCE to "//--BPIPE_ENVIRONMENT_HERE--"
					def project_info = """
						PROJECTNAME = "${BpipeConfig.project_name}"
						REFERENCE   = "${samples[0]["SampleRef"]}"
					""".stripIndent().trim()
					pipeline_text = pipeline_text.replaceAll("//--BPIPE_ENVIRONMENT_HERE--", project_info)
				}
			}

			// Replace reference genome with first sample reference
			pipeline_text = pipeline_text.replaceAll("BPIPE_REFERENCE_GENOME", samples[0]["SampleRef"])

			// CREATE pipeline
			if ( ! createFile(pipeline_text, "${dir}/$pipeline_filename", BpipeConfig.force) ) {
				println Logger.error("Problems creating pipeline file $pipeline_filename!")
			}
		}

		// FAIL for project  pipelines with o args
		if (pipeline["project_pipeline"] && args.size == 0)
		{
			println Logger.error("A project pipeline require a list of samples (subdirs) as arguments after the pipeline name")
			System.exit(1)
		}

		// DIR MODE
		if ( args && args.size > 0 )
		{
			// check if args are all dirs
			checkDir(args)

			// In case of project pipelines treat args as a list of samples
			if (pipeline["project_pipeline"])
			{
				generate_pipeline(BpipeConfig.working_dir)
				println Logger.printUserOptions()
				println Logger.printSamples(samples)

				// INPUT FILES CAN BE IN OUT DIRECTORY: USE CANONICAL PATHS
				def sample_paths = args.collect { new File(it).canonicalPath }

				// MULTI PROJECTS
				if (BpipeConfig.working_dir != System.getProperty("user.dir"))
				{
					// IS MULTI PROJECT PIPELINE WITH MULTIPLE WORKING DIR

					// BUILD INPUT JSON USING BpipConfig.working_dir
					json(sample_paths)

					// FILL THE RUNNER
					File runner = new File("${System.getProperty("user.dir")}/runner.sh")
					if (! runner.exists() )
					{
						println Logger.error("Problems writing on multi project runner: ${System.getProperty("user.dir")}/runner.sh")
						System.exit(1)
					}
					runner << """
						echo "LAUNCHING bpipe in project dir: ${BpipeConfig.working_dir}"
						cd ${BpipeConfig.working_dir}
						${usage.toString().replaceFirst(/bpipe/,"bg-bpipe")}
						sleep 5
						cd ..
					""".stripIndent()
					"chmod 755 runner.sh".execute()
					println Logger.message("I create a runner script for your projects. Run it with: ")
					println Logger.message("\t./runner.sh")
				}
				// SINGLE PROJECT
				else
				{
					// BUILD INPUT JSON
					json(sample_paths)
					if ( ! BpipeConfig.batch)
					{
						println Logger.info("To run the pipeline:")
						println Logger.message(usage.toString())
					}
					else
					{
						usage.toString().replaceFirst(/bpipe/,"bg-bpipe").replaceFirst(/<INPUT_DIRS>/,sample_paths.join(" ")).execute()
						println Logger.message("BATCH MODE: Bpipe started in background in ${BpipeConfig.working_dir}")
						println Logger.message("Use 'bpipe log' to monitor execution.")
					}
				}
			}
			else
			{
				// else recursively create pipelines in subdirs
				args.each { dir ->
					generate_pipeline(dir)
					if (BpipeConfig.batch)
					{
						usage.toString().replaceFirst(/bpipe/,"bg-bpipe").execute(null, new File(dir))
						println Logger.message("BATCH MODE: Bpipe started in background in directory ${dir}")
						println Logger.message("Use 'bpipe log' to monitor execution.")
					}
				}
				// runner.sh
				if ( ! BpipeConfig.batch )
				{
					println Logger.printUserOptions()

					// generate a convenience script in current directory
					File runner_template = new File("${BpipeConfig.bpipe_config_home}/templates/runner.sh.template")
					File runner = new File("runner.sh")
					def binding_runner = [
						"VERSION" : "${BpipeConfig.version} ${BpipeConfig.builddate}",
						"args"    : args,
						"usage"   : usage.toString().replaceFirst(/bpipe/,"bg-bpipe")
					]
					String content = engine.createTemplate(runner_template.text).make(binding_runner).toString()
					runner.write(content)
					"chmod 755 runner.sh".execute()
					println "I create a runner script for your directories. Run it with: "
					println Logger.message("\t./runner.sh")
				}
			}
		}
		// PWD MODE
		else
		{
			generate_pipeline(BpipeConfig.working_dir)

			if (BpipeConfig.batch)
			{
				usage.toString().replaceFirst(/bpipe/,"bg-bpipe").execute()
				println Logger.message("BATCH MODE: Bpipe started in background in ${BpipeConfig.working_dir}")
				println Logger.message("Use 'bpipe log' to monitor execution.")
			}
			else
			{
				println Logger.printUserOptions()
				println Logger.printSamples(samples)
				println Logger.info("To run the pipeline:")
				println Logger.message(usage.toString())
			}
		}
	}

	public static project(def args)
	{
		if (args.empty) {
			println Logger.error("Command pipe need a pipeline name as first argument.")
			if (BpipeConfig.verbose) println Logger.info("Use: bpipe-config -p to get a list of avaliable pipelines")
			System.exit(1)
		}
		String pipeline_name = args.remove(0)

		// Validate pipeline
		def pipeline
		BpipeConfig.pipelines.each { category, pipes ->
			pipes.each { pipe ->
				if (pipe["name"] == pipeline_name) {
					pipeline = pipe
				}
			}
		}

		if (pipeline == null) {
			println Logger.error("can't find pipeline $pipeline_name in pipelines.")
			System.exit(1)
		}

		if (!pipeline["project_pipeline"])
		{
			println Logger.error("This command work only with illumina_projects pipelines.")
			System.exit(1)
		}

		if (args.size == 0)
		{
			println Logger.error("No input project dirs. Usage:\n\tbpipe-config project <pipeline name> <project1> <project2> ...")
			System.exit(1)
		}

		// check if args are all dirs
		checkDir(args)

		// extract sample dirs
		def projects = [:]
		args.each { project_dir ->
			projects["$project_dir"] = []
			new File(project_dir).eachDir { sample ->
				if (sample.getName() ==~ /^Sample_.*/)
				{
					projects["$project_dir"] << sample.getPath()
				}
			}
		}
		// LOG
		println Logger.printProjects(projects, pipeline_name)

		// CREATE A RUNNER (EMPTY)
		new File("${BpipeConfig.working_dir}/runner.sh").write(new File("${BpipeConfig.bpipe_config_home}/templates/multi_projects_runner.sh.template").text)

		// SEND PIPE COMMAND changing working dir
		def old_path = BpipeConfig.working_dir
		projects.each { project, samples ->
			def pargs = [pipeline_name, samples].flatten()
			def project_dir = project.replaceFirst(/.*\//,"")
			// create project output dir if doesn't exists
			new File(project_dir).mkdir()
			BpipeConfig.working_dir = project_dir
			BpipeConfig.project_name = project_dir
			pipe(pargs)
		}
		BpipeConfig.working_dir = old_path
	}

	public static info(def args)
	{
		if (args.empty) {
			println Logger.error("Command info need a pipeline name as first argument.")
			println Logger.info("Use: bpipe-config -p to get a list of avaliable pipelines")
			System.exit(1)
		}
		String pipeline_name = args.remove(0)

		// Validate pipeline
		def pipeline
		BpipeConfig.pipelines.each { category, pipes ->
			pipes.each { pipe ->
				if (pipe["name"] == pipeline_name) {
					pipeline = pipe
				}
			}
		}

		if (pipeline == null) {
			println Logger.error("can't find pipeline $pipeline_name in pipelines.")
			System.exit(1)
		}

		def pipeline_info = Pipelines.pipelineInfo(pipeline, BpipeConfig.modules)
		def pipeline_info_template = new File("${BpipeConfig.bpipe_config_home}/templates/pipeline_report.html.template")

		def binding_pipeline = [
			"name"           : pipeline["name"].replaceAll(/_/," "),
			"title"          : pipeline["about_title"],
			"pipeline_code"  : pipeline_info["pipe_code"],
			"stages"         : pipeline_info["stages"]
		]

		def template_pipeinfo = engine.createTemplate(pipeline_info_template.text).make(binding_pipeline)
		new File("${BpipeConfig.working_dir}/doc").mkdir()
		def fileinfo = new File("${BpipeConfig.working_dir}/doc/${pipeline["name"]}_info.html")
		fileinfo.write(template_pipeinfo.toString())
		if (BpipeConfig.verbose) println Logger.info("Pipeline info file: ${fileinfo.getPath()}")
	}

	public static clean(def args)
	{

		if ( args && args.size > 0 )
		{
			args.each { dir ->
				if (BpipeConfig.verbose) println Logger.info("Cleaning dir: $dir")
				new File("${dir}/.bpipe").deleteDir()
			}
		}
		else
		{
			if (BpipeConfig.verbose) println Logger.info("Cleaning dir: ${BpipeConfig.working_dir}")
			new File("${BpipeConfig.working_dir}/.bpipe").deleteDir()
		}
	}

	public static jvm(def args)
	{
		println Logger.printJVMInfo()
	}

	// TODO SOME CHECKS:
	// Es: check with samples with same sample ID but different projects
	public static smerge(def args)
	{
		if (args.empty)
		{
			println Logger.error("Command smerge need a list of Projects dirs as first argument.")
			println Logger.info("FORMAT: FCID=D2A8DACXX,Lane=3,SampleID=B1,SampleRef=hg19,Index=TTAGGC,Description=description,Control=N,Recipe=MeDIP,Operator=FG,SampleProject=PI_1A_name")
			System.exit(1)
		}

		if ( args && args.size > 0 )
		{
			checkDir(args)
			// loop in dirs
			samples = []
			args.each { dir ->
				if (BpipeConfig.verbose) println Logger.info("Collecting samples info from project dir: $dir")
				def global_sheet = new File("${dir}/${BpipeConfig.sample_sheet_name}")
				if (global_sheet.exists())
				{
					if (BpipeConfig.verbose) println Logger.info("Using project ${BpipeConfig.sample_sheet_name} from project dir: $dir")
					samples = (samples << slurpSampleSheet(global_sheet)).flatten()
				}
				else
				{
					if (BpipeConfig.verbose) println Logger.info("Using single samples ${BpipeConfig.sample_sheet_name} from project dir: $dir")
					// this is weird but is the only way: look for subdir starting with Sample_
					new File("$dir").eachDir { sample_dir ->
						if (BpipeConfig.verbose) println Logger.info("\tCollecting info from sample dir: $sample_dir")
						def subsample_sheet = new File("${sample_dir}/${BpipeConfig.sample_sheet_name}")
						if ( subsample_sheet.exists() )
						{
							def sample = slurpSampleSheet(subsample_sheet)
							samples = (samples << sample).flatten()
						}
						else
						{
							println Logger.error("Error in ${BpipeConfig.sample_sheet_name} files ... Aborting")
							System.exit(1)
						}
					}
				}
			}

			if (BpipeConfig.verbose) println Logger.info("Creating MetaProject ${BpipeConfig.sample_sheet_name}")

			def samples_csv = new StringBuffer()
			samples_csv << "${samples[0].keySet().join(",")}\n"
			samples.each { sample ->
				samples_csv << "${sample.values().toList().join(",")}\n"
			}

			if ( ! createFile(samples_csv.toString(), "${BpipeConfig.sample_sheet_name}", BpipeConfig.force) )
			{
				println Logger.error("Problems creating Project ${BpipeConfig.sample_sheet_name} file!")
			}
		}
	}

	public static json(def args)
	{
		def branches = [:]

		if (args.empty)
		{
			println Logger.error("json command require a list directories. Example: Sample_*")
			System.exit(1)
		}

		if ( args && args.size > 0 )
		{
			// all directories?
			checkDir(args)

			args.each { dir ->
				def input_dir = new File(dir)
				def files = [:]
  			BpipeConfig.extensions.each { files[it] = [] }
  			new File(dir).eachFile() { file ->
  				BpipeConfig.extensions.each { extension ->
  					if (file.canonicalPath.endsWith(extension)) {
  						files[extension].push file.canonicalPath
  					}
  				}
  			}
  			// get longer array
  			def entry = files.max { it.value.size }
  			// Fix fot Stefania BUG R1 and R2 in incorrect order
  			entry = entry.sort()

  			if (entry.getValue().size == 0) {
  				println Logger.error("No known input files in directory: $dir. Known input file extensions: ${BpipeConfig.extensions.join(', ')}")
  			}
  			// MAP KEY IS THE SAMPLE DIR
  			def sample_dir = input_dir.getName()
  			// MAP VALUE: the files + SampleSheet.csv
  			def samplesheet = input_dir.canonicalPath + '/SampleSheet.csv'
  			if ( new File(samplesheet).exists() )
  			{
  				branches[sample_dir] = [samplesheet, entry.getValue()].flatten()
  			}
  			else
  			{
  				println Logger.error("Can't find SampleSheet.csv file for sample dir: $sample_dir")
  			}
			}

			if ( ! createFile(prettyPrint(toJson(branches)), "${BpipeConfig.working_dir}/input.json", BpipeConfig.force) )
			{
				println Logger.error("Problems creating json input file (input.json)")
			}
		}
	}


	// VALIDATORS
	public static void checkDir(def args)
	{
		// check if all args are directories
		if (args)
		{
			args.each { path ->
				def dir = new File(path)
				if (!dir.exists() || !dir.isDirectory()) {
					println Logger.error("Argument $path is not a directory. This command require directories as extra arguments")
					System.exit(1)
				}
			}
		}
	}

	public static boolean validateSampleinfo(String info)
	{
		if (!info) return false
		def pattern = /^FCID=.*,Lane=.*,SampleID=.+,SampleRef=.+,Index=.*,Description=.*,Control=.*,Recipe=.*,Operator=.*,SampleProject=([a-zA-Z]+)_([0-9]+[a-zA-Z]{0,1})_([a-zA-Z]+)$/
		info ==~ pattern
	}

	public static boolean validateCommand(String command)
	{
		!available_commands.grep(command).empty
	}

	public static boolean validateProjectName(String pname)
	{
		def pattern = /([a-zA-Z]+)_([0-9]+[a-zA-Z]{0,1})_([0-9a-zA-Z_]+)/
		return pname ==~ pattern
	}

	public static boolean validateEmail(String email)
	{
		def emailPattern = /[_A-Za-z0-9-]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{2,})/
		email ==~ emailPattern
	}

	// FIXME more validations
	public static boolean validateSample(def sample)
	{
		if (!sample) return false
		if (sample.getClass() != java.util.LinkedHashMap) return false

		// VALIDATE headers NO, headers are changing
		// FIXME
		// def validKeySet = [
		// 	"FCID", "Lane", "SampleID", "SampleRef", "Index",
		// 	"Description", "Control", "Recipe", "Operator", "SampleProject"
		// ]
		// if (sample.keySet().sort() != validKeySet.sort()) return false

		// VALIDATE sample project name
		if (!validateProjectName(sample["SampleProject"])) return false

		// all check passed
		return true
	}

	public static def slurpSampleSheet(File sample_file)
	{
		if (sample_file == null) return null

		def samples = []

		// load and split the file
		List lines = sample_file.readLines()

		// Store headers and remove line
		String[] headers = lines.remove(0).split(",")
		def sample_line_with_error = []

		// Get samples
		lines.eachWithIndex { line, line_index ->
			// skip empty lines
			if ( !line.trim().empty ) {

				// see: https://stackoverflow.com/questions/5245912/groovy-split-without-final-trim
				def sample = line.split(",", -1)
				def sample_map = [:]

				headers.eachWithIndex { header, i ->
					sample_map.put(header, sample[i])
				}

				if (!validateSample(sample_map))
				{
					sample_line_with_error << "line #${line_index+2}: ${line}"
				}
				else
				{
					samples << sample_map
				}
			}
		}

		if (!sample_line_with_error.empty)
		{
			println Logger.info("File: ${sample_file.getPath()} contains errors:\n${sample_line_with_error.join("\n")}\nPlease verify your SampleSheet.")
			return null
		}
		else
		{
			return samples
		}
	}

	static boolean createFile(String text, String filename, boolean force_overwrite)
	{
		if (text == null || text.empty || filename == null) return false

		// closure to write file
		def write_file = { file ->
			file.write(text)
		}
		// closure to check and write file
		def check_and_write_file = { file ->
			if ( file.exists() ) {
				if (force_overwrite) {
					println Logger.warn("File $file already exists. Overwriting ...")
					write_file(file)
				} else {
					println Logger.warn("File $file already exists. Skipping ...")
				}
			} else {
				write_file(file)
				println Logger.message("Created file: $file")
			}
		}

		def new_file = new File("$filename")
		check_and_write_file(new_file)

		// If I am here, success
		return true
	}
}
