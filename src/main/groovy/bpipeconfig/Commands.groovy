package bpipeconfig

/**
 * @author tucano
 * Created: Sat Oct 19 16:32:40 CEST 2013
 */

import groovy.text.SimpleTemplateEngine
import bpipeconfig.Logger

class Commands
{
	final static String[] available_commands = ["config","sheet","pipe","info","clean","report","recover"]
	public static def pipeline
	public static def samples
	public static def engine = new SimpleTemplateEngine()

	public static config(def args)
	{
		// Load config templates
		def file_config  = new File("${BpipeConfig.bpipe_config_home}/templates/bpipe.config.template")
		def local_project_name = null
		def binding_config, template_config, sample_sheet

		// CHECK PROJECT NAME
		def check_project_name = { dir ->
			sample_sheet = new File("${dir}/${BpipeConfig.sample_sheet_name}")
			// check for defined global project name
			if (BpipeConfig.project_name)
			{
				println Logger.info("using project name: ${BpipeConfig.project_name}")
			}
			// check for local SampleSheet.csv
			else if (sample_sheet.exists())
			{
				samples =  slurpSampleSheet(sample_sheet)
				BpipeConfig.project_name = samples[0]["SampleProject"]
				println Logger.info("using project name from SampleSheet.csv: ${BpipeConfig.project_name}")
			}
			else
			{
				println Logger.error("No project name (-P option) and No SampleSheet.csv in dir $dir! Aborting ...")
				println Logger.info("You can use the option -P to set a project name or the command: sheet to generate a SampleSheet.scv")
				System.exit(1)
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
		if (args.empty) {
			println Logger.error("Command sheet need a SAMPLE INFO as first argument.")
			println Logger.info("FORMAT: FCID=D2A8DACXX,Lane=3,SampleID=B1,SampleRef=hg19,Index=TTAGGC,Description=niguarda,Control=N,Recipe=MeDIP,Operator=FG,SampleProject=PI_1A_name")
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
			if ( ! createFile(out.toString(), "${dir}/SampleSheet.csv",  BpipeConfig.force) ) {
				println Logger.error("Problems creating SampleSheet.csv in dir $dir")
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

		// check for bpipe.config and create it
		def check_bpipe_config = { dir ->
			if ( ! new File("${dir}/bpipe.config").exists() ) { config([dir]) }
		}

		// CAN BE DEFINED HERE BECAUSE IS THE SAMPE USAGE FOR EACH DIR DON'T CARE FOR OVERWRITE
		def usage = ""
		def generate_pipeline = { dir ->
			def sample_sheet = new File("${dir}/${BpipeConfig.sample_sheet_name}")

			// PROJECT SCOPE: no sample sheet needed!
			if (pipeline["project_pipeline"]) {
				// Loop in Sample dirs
				samples = []
				args.each { sample_dir ->
					def subsample_sheet = new File("${sample_dir}/${BpipeConfig.sample_sheet_name}")
					if ( subsample_sheet.exists() ) {
						samples = (samples << slurpSampleSheet(subsample_sheet)).flatten()
					} else {
						println Logger.error("No SampleSheet.csv in dir $sample_dir! Aborting ...")
						println Logger.info("You can use the command: sheet to generate a SampleSheet.scv")
						System.exit(1)
					}
				}
				if (BpipeConfig.project_name) {
					println Logger.info("Using project name: ${BpipeConfig.project_name}")
				} else {
					BpipeConfig.project_name = samples[0]["SampleProject"]
				}
			} else {
				if ( sample_sheet.exists() ) {
					samples =  slurpSampleSheet(sample_sheet)
					if (BpipeConfig.project_name) {
						println Logger.info("Using project name: ${BpipeConfig.project_name}")
					} else {
						BpipeConfig.project_name = samples[0]["SampleProject"]
					}
				} else {
					println Logger.error("No SampleSheet.csv in dir $dir! Aborting ...")
					println Logger.info("You can use the command: sheet to generate a SampleSheet.scv")
					System.exit(1)
				}
			}

			String pipeline_text = new File(pipeline["file_path"]).text
			String pipeline_filename = "${BpipeConfig.project_name}_${pipeline["file_name"]}"

			// GENERATE USAGE INFO
			def pattern = /(?m)\/{2}\s*USAGE:(.*)/
			def matcher = (pipeline_text =~ pattern)
			usage = matcher.hasGroup() ? matcher[0][1].trim() : 'bpipe run -r $pipeline_filename *'
			def binding_usage = [ "pipeline_filename" : pipeline_filename ]
			usage = engine.createTemplate(usage).make(binding_usage)
			check_bpipe_config(dir)

			// COPY PIPELINE AND GFU_ENVIRONMENT
			def file_gfu_env = new File("${BpipeConfig.bpipe_config_home}/templates/gfu_environment.sh.template")

			def binding_gfu_env = [
			    "project_name"    : '"' + BpipeConfig.project_name + '"',
			    "reference"       : '"' + samples[0]["SampleRef"] + '"',
			    "experiment_name" : '"' + samples[0]["FCID"] + "_" + samples[0]["SampleID"] + '"',
			    "fcid"            : '"' + samples[0]["FCID"] + '"',
			    "lane"            : '"' + samples[0]["Lane"] + '"',
			    "sampleid"        : '"' + samples[0]["SampleID"] + '"'
			]
			// GENERATION OF gfu_enviroment.sh FILE with Per pipeline options
			def template_gfu_env = engine.createTemplate(file_gfu_env.text).make(binding_gfu_env).toString()

			// CREATE gfu_environment.sh
			if ( ! createFile(template_gfu_env, "${dir}/gfu_environment.sh", BpipeConfig.force) ) {
				println Logger.error("Problems creating gfu_environment.sh file!")
			}

			// WRITING THE GFU_ENVIROMENT FILE AS GROOVY VARS in the pipeline (just to be sure)
			pipeline_text = pipeline_text.replaceAll( "//--BPIPE_ENVIRONMENT_HERE--", template_gfu_env )

			// Replace reference genome with first sample reference
			pipeline_text = pipeline_text.replaceAll("BPIPE_REFERENCE_GENOME", samples[0]["SampleRef"])

			// CREATE pipeline
			if ( ! createFile(pipeline_text, "${dir}/$pipeline_filename", BpipeConfig.force) ) {
				println Logger.error("Problems creating pipeline file $pipeline_filename!")
			}
		}

		// DIR MODE
		if ( args && args.size > 0 )
		{
			// check if args are all dirs
			checkDir(args)
			// In case of project pipelines treat args as a list of samples
			if (pipeline["project_pipeline"]) {
				generate_pipeline(BpipeConfig.working_dir)
				println Logger.printUserOptions()
				println Logger.printSamples(samples)
				if ( ! BpipeConfig.batch) {
					println Logger.info("To run the pipeline:")
					println Logger.message(usage.toString())
				} else {
					usage.toString().replaceFirst(/bpipe/,"bg-bpipe").execute()
					println Logger.message("BATCH MODE: Bpipe started in background in ${BpipeConfig.working_dir}")
					println Logger.message("Use 'bpipe log' to monitor execution.")
				}
			} else {
				// else recursively create pipelines in subdirs
				args.each { dir ->
					generate_pipeline(dir)
					if (BpipeConfig.batch) {
						usage.toString().replaceFirst(/bpipe/,"bg-bpipe").execute(null, new File(dir))
						println Logger.message("BATCH MODE: Bpipe started in background in directory ${dir}")
						println Logger.message("Use 'bpipe log' to monitor execution.")
					}
				}
				// runner.sh
				if ( ! BpipeConfig.batch )
				{
					println Logger.printUserOptions()
					println Logger.printSamples(samples)
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

	public static info(def args)
	{
		println "INFO"
	}

	public static clean(def args)
	{
		println "CLEAN"
	}

	public static report(def args)
	{
		println "REPORT"
	}

	public static recover(def args)
	{
		println "RECOVER"
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
					println Logger.error("Argument $path is not a directory. THis command require directories or no extra arguments")
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
		def pattern = /([a-zA-Z]+)_([0-9]+[a-zA-Z]{0,1})_([a-zA-Z]+)/
		return pname ==~ pattern
	}

	public static boolean validateEmail(String email)
	{
		def emailPattern = /[_A-Za-z0-9-]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{2,})/
		email ==~ emailPattern
	}

	public static def slurpSampleSheet(File sample_file)
	{
		if (sample_file == null) return null

		def samples = []

		// load and split the file
		List lines = sample_file.readLines()

		// Store headers and remove line
		String[] headers = lines.remove(0).split(",")

		// Get samples
		lines.each { line ->
			// skip empty lines
			if (!line.trim().empty) {
				def sample = line.split(",")
				def sample_map = [:]
				headers.eachWithIndex { header, i ->
					sample_map.put(header, sample[i])
				}
				samples << sample_map
			}
		}
		return samples
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
					println Logger.error("File $file already exists. Overwriting ...")
					write_file(file)
				} else {
					println Logger.error("File $file already exists. Skipping ...")
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
