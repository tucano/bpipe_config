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
    final static String[] available_commands = ["config","pipe","info","report","recover"]

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
			usage: "bpipe-config [options] [config|pipe|info|report|recover] [pipeline_name|*.groovy|dirs]",
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
				print "${ bold(pipeline["name"]) }".padRight(40);println " --> ${ green(pipeline["about_title"]) }";
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
		def engine = new SimpleTemplateEngine()
		def template_config  = engine.createTemplate(file_config.text).make(binding_config)

		def write_config = { file ->
			file.write(template_config.toString())
		}

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
			// we passed check, write configs in each sub dir
			// check for existing files and overwirite or exit according to force
			args.each { dir_path ->
				// CONFIG FILE
				def bpipe_config_file = new File("${dir_path}/bpipe.config")
				if ( bpipe_config_file.exists() ) {
					if (force) {
						println red("File $bpipe_config_file already exists. Overwriting ...")
						write_config(bpipe_config_file)
					} else {
						println red("File $bpipe_config_file already exists. Skipping ...")
					}
				} else {
					write_config(bpipe_config_file)
					println green("Created file: $bpipe_config_file")
				}
			}
		}
		// put bpipe.config in working dirs
		else
		{
			def bpipe_config_file = new File("bpipe.config")
			if ( bpipe_config_file.exists() ) {
				if (force) {
					println red("File $bpipe_config_file already exists. Overwriting ...")
					write_config(bpipe_config_file)
				} else {
					println red("File $bpipe_config_file already exists. Skipping ...")
				}
			} else {
				write_config(bpipe_config_file)
				println green("Created file: $bpipe_config_file")
			}
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
		
		// ADDING VARIABLES TO THE PIPELINE 
		def pattern = /(?m)PROJECT_NAME\s+=.*/
		def matcher = (pipeline_text =~ pattern)
		pipeline_text = matcher.replaceAll("PROJECT_NAME = \"$project_name\"")

		// COPY PIPELINE AND GFU_ENVIRONMENT
		def file_gfu_env = new File("${bpipe_config_home}/templates/gfu_environment.sh.template")

		def binding_gfu_env = [
		    "project_name"             : project_name
		]

		// GENERATION OF gfu_enviroment.sh FILE with Per pipeline options
		def engine = new SimpleTemplateEngine()
		def template_gfu_env = engine.createTemplate(file_gfu_env.text).make(binding_gfu_env)
		def write_gfu_environment = { file ->
    		file.write(template_gfu_env.toString())
		}

		// MULTIPLE DIRS if we have more args they must be directories
		if (args.size > 0) {
			args.each { path ->
				def dir = new File(path)
				if (!dir.exists() || !dir.isDirectory()) {
					println red("Argument $path is not a directory. Command pipe requirea pipeline name and a list of directories or no extra arguments")
					System.exit(1)
				}
			}
			args.each { dir_path ->
				// generate config if needed
				if ( ! new File("${dir_path}/bpipe.config").exists() ) configCommand( [dir_path] )
				def env_file = new File("${dir_path}/gfu_environment.sh")
				if ( env_file.exists() ) {
				    if (force) {
				        println red("File $env_file already exists. Overwriting ...")
				        write_gfu_environment(env_file)
				    } else {
				        println red("File $env_file already exists. Skipping ...")
				    }
				} else {
				    write_gfu_environment(env_file)
				    println green("Created file: $env_file")
				}
				new File("$dir_path/${project_name}_${pipeline["file_name"]}").write(pipeline_text)
				println green("${pipeline["name"]} installed in dir ${dir_path}")
			}
			println greenbold("Now run: <pipeline command HERE> ")
		} else {
			// generate config if needed
			if ( ! new File("bpipe.config").exists() ) configCommand(false)

			new File("${project_name}_${pipeline["file_name"]}").write(pipeline_text)
			println green("Pipeline ${pipeline["name"]} installed in dir ${working_dir}")

			def env_file = new File("gfu_environment.sh")
			if ( env_file.exists() ) {
			    if (force) {
			        println red("File $env_file already exists. Overwriting ...")
			        write_gfu_environment(env_file)
			    } else {
			        println red("File $env_file already exists. Skipping ...")
			    }
			} else {
			    write_gfu_environment(env_file)
			    println green("Created file: $env_file")
			}
			println greenbold("Now run: <pipeline command HERE> ")
		}
	}

	/*
	 * SAMPLE SHEET
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
		println bold("\nAvailable Commands:\n")
		print bold("config ");print "[dir1] [dir2] ...                 "; println green("\tConfigure current directory or directories in list (add bpipe.config file)");
		print bold("pipe   ");print "<pipeline name> [dir1] [dir2]     "; println green("\tGenerate pipeline file in current directory or directories in list (pipeline.groovy)");
		print bold("info   ");print "<pipeline name>                   "; println green("\tGet info on pipeline stages");
		print bold("report ");print "<pipe1.groovy> <pipe2.groovy> ... "; println green("\tGenerate reports for pipeline.groovy files");
		print bold("recover");print "                                  "; println green("\tRecover log files from .bpipe in current dir (jobs IDs and output file names)");
		print "\nUse: "; print green("bpipe-config info <pipeline name>"); println " to get info on a pipeline."
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