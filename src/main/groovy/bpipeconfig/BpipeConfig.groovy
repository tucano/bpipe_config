package bpipeconfig

/***********************************************************************
 * @author      davide Rambaldi <rambaldi.davide@hsr.it>
 * @version     0.1
 * @since       2013
 * 
 *
 ***********************************************************************/

import org.fusesource.jansi.AnsiConsole
import static org.fusesource.jansi.Ansi.*
import static org.fusesource.jansi.Ansi.Color.*
import static org.fusesource.jansi.Ansi.Attribute.*
import org.apache.commons.cli.Option
import groovy.text.SimpleTemplateEngine

class BpipeConfig 
{
	final static String sample_sheet_name    = "SampleSheet.csv"
	final static String version              = System.getProperty("bpipeconfig.version")
    final static String builddate            = System.getProperty("bpipeconfig.builddate")?:System.currentTimeMillis()
    final static String[] available_commands = ["config","pipe","info","clean","report","recover"]

    // Options CliBuilder vars
    public static String  user_email
    public static boolean verbose 
    public static boolean force

    // Args var
    public static String command

    // System Property vars
    public static String user_name
    public static String working_dir
    public static String bpipe_home
    public static String bpipe_config_home
    public static String bpipe_gfu_pipelines_home
    public static String java_runtime_version
    public static String project_name

    // Pipelines
    public static def pipelines
    public static def pipeline
    // Samples
    public static def samples
    // Config
    public static def email_config
    //TEMPLATE ENGINE
    public static def engine = new SimpleTemplateEngine()

    /**
     * Main Entry Point
     *
     * Use the CliBuilder to handle options And JANSI to colorize output
     *
     */        
	public static void main(String[] args)
	{
		// GET ENVIRONMENT INFO
		working_dir              = System.getProperty("user.dir")
		user_name                = System.getProperty("user.name")
		bpipe_home               = System.getProperty("bpipe.home")
		bpipe_config_home        = System.getProperty("bpipeconfig.home")
		bpipe_gfu_pipelines_home = System.getProperty("bpipe_gfu_pipelines.home")
		java_runtime_version     = System.getProperty("java.runtime.version")

		// LOAD CONFIG FILE
		def config_email_file = new File("${bpipe_config_home}/config/email_notifier.groovy")
		email_config = new ConfigSlurper().parse(config_email_file.text)

		def cli = new CliBuilder(
			usage: "bpipe-config [options] [config|pipe|info|clean|report|recover] [pipeline_name|*.groovy|dirs]",
    		header: "\nAvailable options (use -h for help):\n",
    		footer: "\n${versionInfo(version)}, ${buildInfo(builddate)}\n",
    		posix:  true,
    		width:  100
		)

		cli.with {
			h   longOpt: 'help'     , 'Usage Information', required: false
			v   longOpt: 'verbose'  , 'Verbose mode', required: false
			f   longOpt: 'force'    , 'Force files overwrite when needed (default=FALSE).', required: false
			p   longOpt: 'pipelines', 'Print a list of available pipelines', required: false
			'P' longOpt: 'project'  , 'Override the project name. If not provided will be extracted from SampleSheet in current directory. Format: <PI_name>_<ProjectID>_<ProjectName>', args: 1, required: false
			m   longOpt: 'email'    , 'User email address (Es: -m user@example.com)', args: 1, required: false
		}

		def opt = cli.parse(args)

		if ( !opt ) System.exit(1)

		// GET MAP OF PIPELINES
		pipelines = listPipelines(bpipe_gfu_pipelines_home)

		// GET SampleSheet.csv		
		samples = slurpSampleSheet("${working_dir}/${sample_sheet_name}")

		// GET OPTIONS: PIPELINES
		if (opt.p) {
			printVersionAndBuild()
			println bold("\nListing Pipelines:\n")
			// println pipelines simply formatted
			pipelines.each { pipeline ->
				print "${ bold(pipeline["name"]) } ".padRight(40, "-");println "--> ${ green(pipeline["about_title"]) }";
			}
			println "\n\n"
			printHelpCommands()
			println "\n"
			System.exit(0)
		}
		// GET OPTIONS: HELP or USAGE (NO ARGUMENTS)
        if ( opt.h || !opt.arguments() ) {
        	cli.usage()
        	printHelpCommands()
        	println "\n"
        	System.exit(1)
        }
        // GET OPTIONS: PROJECT NAME
		if ( projectName(opt.P) == false )
		{
			printVersionAndBuild()
			println()
			print "Project name "; print red(project_name); println " is invalid. Valid format: PI_ID_Name (Es: Banfi_25_Medaka)"
			System.exit(1)
		}
		// GET OPTIONS: MAIL
		if (opt.m) {
			if ( validateEmail(opt.m) ) {
				user_email = opt.m
			} else {
				printVersionAndBuild()
				println()		
				print bold(opt.m)
				print red(" is not a valid email address\n")
				System.exit(1)
			}
		}
		// Set verbose option
		verbose = opt.v
		// Set force options
		force = opt.f

		// GET command (first non options argument) and remove it from list
		def extraArguments = opt.arguments()
		command = extraArguments.remove(0)
		

		// Validate Command
		if (!validateCommand(command)) {
			printVersionAndBuild()
			println()
			print bold(command)
			print red(" is not a valid command\n")
			printHelpCommands()
			System.exit(1)
		}

        // HEADER
        printVersionAndBuild()
        println()

        // USER OPTIONS
        printUserOptions()
        printSamples()

		// COMMAND SWITCH sending extra arguments
		switch(command) {
			case "config":
				configCommand(extraArguments)
			break
			case "pipe":
				pipeCommand(extraArguments)
			break
			case "info":
				
			break
			case "clean":

			break
			case "report":
				
			break
			case "recover":
				
			break
		}
        
        // TODO FIXME FROM HERE
        //println extraArguments
		//println "SAMPLE SHEET ${working_dir}/${sample_sheet_name}"
		//println samples
		//System.properties.each { k, v -> println("$k = $v") }
	}

	/*
	 * COMMAND CONFIG: create bpipe.config file
	 * Fixme need tests
	 */
	static void configCommand(def args)
	{
		// Load config templates
		def file_config  = new File("${bpipe_config_home}/templates/bpipe.config.template")
		
		def binding_config = [ 
			"executor"                 : "pbspro", 
			"username"                 : user_name,
			"queue"                    : "workq",
			"project_name"             : project_name,
			"user_email"               : user_email,
			"bpipe_notifier"           : email_config.bpipe_notifier,
			"bpipe_notifier_password"  : email_config.bpipe_notifier_password
		]

		// make templates
		def template_config  = engine.createTemplate(file_config.text).make(binding_config)

		if (args && args.size > 0) checkDir(args)

		if ( ! createFile(template_config.toString(), "bpipe.config", args) ) {
			println red("Problems creating bpipe.config files!")
		}
	}

	/*
	 * COMMAND PIPE: create pipeline
	 */
	static void pipeCommand(def args)
	{
		if (args.empty) {
			println red("Command pipe need a pipeline name as first argument.")
			System.exit(1)
		}
		
		String pipeline_name = args.remove(0)
		
		// Validate pipeline
		pipeline = pipelines.find {
			it["name"] == pipeline_name
		}

		if (pipeline == null) {
			println red("can't find pipeline $pipeline_name in pipelines.")
			System.exit(1)
		}

		// SOURCING PIPELINE and make some changes
		String pipeline_text = new File(pipeline["file_path"]).text
		String pipeline_filename = "${project_name}_${pipeline["file_name"]}"

		// ADDING VARIABLES TO THE PIPELINE:
		// 1. PROJECT NAME
		def pattern = /(?m)PROJECT_NAME\s+=.*/
		def matcher = (pipeline_text =~ pattern)
		pipeline_text = matcher.replaceAll("PROJECT_NAME = \"$project_name\"")
		// 2. Get usage info
		pattern = /(?m)\/{2}\s*USAGE:(.*)/
		matcher = (pipeline_text =~ pattern)

		def usage = matcher.hasGroup() ? matcher[0][1].trim() : 'bpipe run -r $pipeline_filename *'
		
		def binding_usage = [
			"pipeline_filename" : pipeline_filename
		]

		usage = engine.createTemplate(usage).make(binding_usage)

		// COPY PIPELINE AND GFU_ENVIRONMENT
		def file_gfu_env = new File("${bpipe_config_home}/templates/gfu_environment.sh.template")

		def binding_gfu_env = [
		    "project_name" : project_name
		]

		// GENERATION OF gfu_enviroment.sh FILE with Per pipeline options
		def template_gfu_env = engine.createTemplate(file_gfu_env.text).make(binding_gfu_env)
		
		if (args && args.size > 0) checkDir(args)
		
		if ( ! createFile(template_gfu_env.toString(), "gfu_environment.sh", args) ) {
			println red("Problems creating gfu_environment.sh files!")
		}

		if ( ! createFile(pipeline_text, pipeline_filename, args) ) {
			println red("Problems creating pipeline file $pipeline_filename!")	
		}

		// DIRS are already checked here, then:
		if (args) { 
			// generate a convenience script in current directory
			File runner = new File("runner.sh")
			String content = """
				for i in ${args.join(" ")}
				do
					cd \$i
					$usage
					cd ..
				done
			""".stripIndent().trim()
			runner.write(content)
			println "I create a runner script for your directories. Run with: "
			println greenbold("bash runner.sh")			
		} else {
			println "Now run: "
			println greenbold("$usage") 
		}
	}

	/*
	 * GENERATE N FILES FROM TEMPLATE
	 */
	static boolean createFile(String text, String filename, def args)
	{
		if (text == null || text.empty || filename == null) return false

		// closure to write file
		def write_file = { file ->
			file.write(text)
		}
		// closure to check and write file
		def check_and_write_file = { file ->
			if ( file.exists() ) {
				if (force) {
					println red("File $file already exists. Overwriting ...")
					write_file(file)
				} else {
					println red("File $file already exists. Skipping ...")
				}
			} else {
				write_file(file)
				println green("Created file: $file")
			}
		}
		// DIRECTORIES
		if (args) {
			// we passed check, write configs in each sub dir
			// check for existing files and overwirite or exit according to force
			args.each { dir_path ->
				def new_file = new File("${dir_path}/$filename")
				check_and_write_file(new_file)
			}
		} else {
			def new_file = new File("$filename")
			check_and_write_file(new_file)
		}
		// If I am here, success
		return true
	}

	/*
	 * SLURP SAMPLE SHEET
	 * Return a map of samples or null
	 * Check presence of SampleSheet and Populate Infos
	 * FIXME NOW WORK FOR SINGLE FILE WITH HEADER AND SINGLE RAW
	 * FIXME: Need TESTING
	 */
	static def slurpSampleSheet(String file_path)
	{
		def sample_file = new File(file_path)
		if ( !sample_file.exists() ) return null

		def samples = []

		// load and split the file
		List lines = sample_file.readLines()

		// Store headers and remove line
		String[] headers = lines.remove(0).split(",")
		
		// Get samples
		lines.each { line ->
			def sample = line.split(",")
			def sample_map = [:]
			headers.eachWithIndex { header, i ->
				sample_map.put(header, sample[i])
			}
			samples << sample_map
		}

		return samples
	}

	/*
	 * PROJECT NAME
	 * Get the project name from SampleSheet.csv or generate a random one
	 * Fixme need tests
	 */
	static boolean projectName(def opt)
	{
		def pattern = /([a-zA-Z]+)_([0-9]+)_([a-zA-Z]+)/
		if (opt)
		{
			project_name = opt
		}
		else if (samples)
		{
			project_name = samples[0]["SampleProject"]
		}
		else
		{
			project_name = null
		}
		return project_name ==~ pattern
	}

	/*
	 * LIST PIPELINES
	 * Fixme need tests (only for null)
	 */
	static def listPipelines(String pipelines_root)
	{
		def pipelines_location = pipelines_root + "/pipelines"
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

	/*
	 * PRINTERS
	 */
	static void printVersionAndBuild()
	{
		print bold(versionInfo(version))
		println "\t${buildInfo(builddate)}"
	}

	static void printSamples()
	{
		println()
		println bold("Samples Info: ")

		samples.each { item ->
			print "\tID: "; print bold("${item["SampleID"]}");
			print "\tREFERENCE: "; print bold("${item["SampleRef"]}");
			print "\tDESCRIPTION: "; print bold("${item["Description"]}");
			print "\tRECIPE: "; print bold("${item["Recipe"]}");
			println()
		}
		println()
	}

	static void printUserOptions()
	{
		println()
		println bold("Configuration:")
		println()
		print "\tCommand            = "; println green(command);
		print "\tWorking Directory  = "; println green(working_dir);
		print "\tProject Name       = "; println green(project_name);
		print "\tUsername           = "; println green(user_name);
		if (user_email)	{
			print "\tUser email         = "; println green("$user_email");
		}
		print "\tBpipe Home         = "; println green(bpipe_home);
		print "\tGfu pipelines home = "; println green(bpipe_gfu_pipelines_home);
		print "\tJRE version        = "; println green(java_runtime_version);
	}

	static void printHelpCommands()
	{
		def out = new StringBuffer()
		out << bold("\nAvailable Commands:\n")
		out << bold("config".padRight(10)) << "[dir1] [dir2] ... ".padLeft(15).padRight(40) << green("\tConfigure current directory or directories in list (add bpipe.config file).") << "\n"
		out << bold("pipe".padRight(10)) << "<pipeline name> [dir1] [dir2] ... ".padLeft(15).padRight(40) << green("\tGenerate pipeline file in current directory or directories in list (pipeline.groovy)") << "\n"
		out << bold("info".padRight(10)) << "<pipeline name> ... ".padLeft(15).padRight(40) << green("\tGet info on pipeline stages.") << "\n"
		out << bold("clean".padRight(10)) << "[dir1] [dir2] ... ".padLeft(15).padRight(40) << green("\tClean all in current working directory or in directory list: intermediate files, bpipe.config file, gfu_environment files, bpipe directories.") << "\n"
		out << bold("report".padRight(10)) << "<pipe1.groovy> <pipe2.groovy> ... ".padLeft(15).padRight(40) << green("\tGenerate reports for pipeline.groovy files.") << "\n"
		out << bold("recover".padRight(10)) << " ".padLeft(15).padRight(40) << green("\tRecover log files from .bpipe in current dir (jobs IDs and output file names).") << "\n"
		out << "\nUse: " << green("bpipe-config info <pipeline name>") << " to get info on a pipeline.\n"
		print out.toString()
	}

	/* 
	 * HELPERS
	 */
	static String versionInfo(String version)
	{
		"BpipeConfig GFU Version ${version}"
	}

	static String buildInfo(String builddate)
	{
		def date = builddate ? new Date(Long.parseLong(builddate)) : null
		"Built on $date"
	}

	static boolean validateEmail(String email)
	{
		def emailPattern = /[_A-Za-z0-9-]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{2,})/
		email ==~ emailPattern
	}

	static boolean validateCommand(String command)
	{
		!available_commands.grep(command).empty
	}

	static String wrap(String text, int columns = 80)
	{
		if ( text == null ) return null
		if ( text.empty ) return ""
		String result = ""
		String line = ""
		text.split(" ").each { word ->
			if (word.length() > columns) {
				def part = word.substring(0, columns - line.length())
				line += part
				word = word.substring(part.length())
			}
			if (line.length() + word.length() > columns) {
				result += line.trim() + "\n"
				line = ""
			}
			while (word.length() > columns) {
				result += word.substring(0, columns) + "\n"
				word = word.substring(columns)
			}
			line += word + " "
		}
		result += line.trim()
		result
	}

	static void checkDir(def args)
	{
		// check if all args are directories
		if (args)
		{
			args.each { path ->
				def dir = new File(path)
				if (!dir.exists() || !dir.isDirectory()) {
					println red("Argument $path is not a directory. Command config require directories or no extra arguments")
					System.exit(1)
				}
			}
		}
	}
	/*
	 * COLORIZERS
	 */
	static org.fusesource.jansi.Ansi bold(String s)
	{
		ansi().a(INTENSITY_BOLD).a(s).reset()
	}

	static org.fusesource.jansi.Ansi green(String s)
	{
		ansi().fg(GREEN).a(s).reset()
	}

	static org.fusesource.jansi.Ansi greenbold(String s)
	{
		ansi().fg(GREEN).a(INTENSITY_BOLD).a(s).reset()
	}

	static org.fusesource.jansi.Ansi red(String s)
	{
		ansi().fg(RED).a(s).reset()
	}
}	