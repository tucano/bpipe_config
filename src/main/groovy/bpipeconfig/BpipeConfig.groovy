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

class BpipeConfig 
{
	final static String sample_sheet_name    = "SampleSheet.csv"
	final static String version              = System.getProperty("bpipeconfig.version")
    final static String builddate            = System.getProperty("bpipeconfig.builddate")?:System.currentTimeMillis()
    final static String[] available_commands = ["config","pipe","info","report","recover"]

    // Options CliBuilder vars
    public static String  user_email
    public static boolean verbose 

    // Args var
    public static String command

    // System Property vars
    public static String user_name
    public static String working_dir
    public static String bpipe_home
    public static String bpipe_gfu_pipelines_home
    public static String java_runtime_version
    public static String project_name

    // Pipelines
    public static def pipelines
    // Samples
    public static def samples

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
		bpipe_gfu_pipelines_home = System.getProperty("bpipe_gfu_pipelines.home")
		java_runtime_version     = System.getProperty("java.runtime.version")

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

		// GET command (first non options argument) and remove it from list
		def extraArguments = opt.arguments()
		command = extraArguments[0]
		extraArguments.remove(0)

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

		// COMMAND SWITCH sending extra arguments
		switch(command) {
			case "config":
				configCommand(extraArguments)
			break
			case "pipe":
				
			break
			case "info":
				
			break
			case "report":
				
			break
			case "recover":
				
			break
		}

		// USER OPTIONS
        if (verbose) {
        	printUserOptions()
        	printSamples()
        }

        // TODO FIXME FROM HERE
        //println extraArguments
		//println "SAMPLE SHEET ${working_dir}/${sample_sheet_name}"
		//println samples
		//System.properties.each { k, v -> println("$k = $v") }
	}

	/*
	 * COMMANDS
	 * Fixme need tests
	 */
	static void configCommand(def args)
	{
		if (args)
		{
			println "Directories"
		}
		else
		{
			println "PWD"
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
		print bold("config ");print "[dir1] [dir2] ...                 "; println green("\tConfigure current directory or directories in list (add bpipe.config file and gfu_enviroment.sh)");
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