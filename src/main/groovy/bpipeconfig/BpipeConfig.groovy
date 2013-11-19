package bpipeconfig

/**
 * BPIPE-CONFIG
 * <p>
 *
 * BpipeConfig is a comman line application to handle bpipe runs.
 *
 * @see     bpipe.Bpipe
 *
 * @author	Davide Rambaldi <rambaldi.davide@hsr.it>
 *
 */

import org.apache.commons.cli.Option

import bpipeconfig.Commands
import bpipeconfig.Logger
import bpipeconfig.Pipelines

class BpipeConfig
{
	/**
	 * PROPERTIES
	 */
	final static String sample_sheet_name    = "SampleSheet.csv"
	final static String version              = System.getProperty("bpipeconfig.version")
    final static String builddate            = System.getProperty("bpipeconfig.builddate")?:System.currentTimeMillis()

    public static String  user_email
    public static boolean verbose
    public static boolean force
    public static boolean batch
    public static String command
    public static String user_name
    public static String working_dir
    public static String bpipe_home
    public static String bpipe_config_home
    public static String java_runtime_version
    public static String project_name
    public static def pipelines
    public static def modules
    public static def email_config

    /**
     * Main Entry Point
     * <p>
     * Use the CliBuilder to handle options.
     * Use bpipeconfig.Logger to send messages
     * Use bpipeconfig.Commands to execute commands
     *
     * @param	args	command line arguments
 	 * @return	A System.exit status
     */
	public static void main(String[] args)
	{
		// GET ENVIRONMENT INFO
		working_dir              = System.getProperty("user.dir")
		user_name                = System.getProperty("user.name")
		bpipe_home               = System.getProperty("bpipe.home")
		bpipe_config_home        = System.getProperty("bpipeconfig.home")
		java_runtime_version     = System.getProperty("java.runtime.version")

		// LOAD CONFIG FILES
		def config_email_file = new File("${bpipe_config_home}/config/email_notifier.groovy")
		if (config_email_file.exists()) {
			email_config = new ConfigSlurper().parse(config_email_file.text)
		} else {
			println Logger.warn("No email configuration (${config_email_file})")
		}

		// CLI BUILDER
		def cli = new CliBuilder(
			usage: "bpipe-config [options] [config|sheet|pipe|info|clean|report|recover] [pipeline_name|*.groovy|dirs]",
    		header: "\nAvailable options (use -h for help):\n",
    		footer: "\n${Logger.versionInfo(version)}, ${Logger.buildInfo(builddate)}\n",
    		posix:  true,
    		width:  120
		)
		cli.with {
			b	longOpt: 'batch'    , 'Automatically execute bpipe in background (bg-bpipe)', required: false
			c   longOpt: 'commands' , 'Print a list of available commands', required: false
			f   longOpt: 'force'    , 'Force files overwrite when needed (default=FALSE).', required: false
			h   longOpt: 'help'     , 'Usage Information', required: false
			m   longOpt: 'email'    , 'User email address (Es: -m user@example.com)', args: 1, required: false
			p   longOpt: 'pipelines', 'Print a list of available pipelines', required: false
			'P' longOpt: 'project'  , 'Override the project name. If not provided will be extracted from SampleSheet in current directory. Format: <PI_name>_<ProjectID>_<ProjectName>', args: 1, required: false
			v   longOpt: 'verbose'  , 'Verbose mode', required: false
		}
		def opt = cli.parse(args)
		if ( !opt ) System.exit(1)

		// GET MAP OF PIPELINES and MODULES
		pipelines = Pipelines.listPipelines(bpipe_config_home + "/pipelines")
        modules   = Pipelines.listModules(bpipe_config_home + "/modules")

		// PRINT VERSION AND BUILD
		println Logger.printVersionAndBuild(version, builddate)

		// GET HELP OPTIONS
		def help_mode = false
		if ( opt.h ) {
        	cli.usage()
        	help_mode = true
        }
		if ( opt.p ) {
			println Logger.printPipelines(pipelines)
			help_mode = true
		}
		if ( opt.c ) {
			println Logger.printHelpCommands()
			help_mode = true
		}
		if ( help_mode ) System.exit(1)

		// USAGE with no arguments and no help
		if ( !opt.arguments() ) {
			cli.usage()
			System.exit(1)
		}

		// GET OPTIONS: MAIL
		if ( opt.m ) {
			if ( Commands.validateEmail(opt.m) ) {
				user_email = opt.m
			} else {
				print Logger.error(opt.m, " is not a valid email address\n")
				System.exit(1)
			}
		}
		// GET OPTIONS: PROJECT NAME
		if ( opt.P ) {
			if ( Commands.validateProjectName(opt.P) ) {
				project_name = opt.P
			} else {
				print Logger.error(
					"Project name: ${opt.P}",
					" is invalid. Valid format: PI_ID_Name (Es: Banfi_25_Medaka or Banfi_1B_medaka)"
				)
			}
		}

		// SET OTHER OPTIONS
		verbose = opt.v
		force   = opt.f
		batch   = opt.b

		// GET command (first non options argument) and remove it from list
		def extraArguments = opt.arguments()
		command = extraArguments.remove(0)

		// Validate Command
		if (!Commands.validateCommand(command)) {
			println Logger.error(command, " is not a valid command")
			println Logger.printHelpCommands()
			System.exit(1)
		}

		// COMMAND SWITCH sending extra arguments
		switch(command) {
			case "config":
				Commands.config(extraArguments)
			break
			case "sheet":
				Commands.sheet(extraArguments)
			break
			case "pipe":
				Commands.pipe(extraArguments)
			break
			case "info":
				Commands.info(extraArguments)
			break
			case "clean":
				Commands.clean(extraArguments)
			break
			case "report":
				Commands.report(extraArguments)
			break
			case "recover":
				Commands.recover(extraArguments)
			break
		}
	}
}
