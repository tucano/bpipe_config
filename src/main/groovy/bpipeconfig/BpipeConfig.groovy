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
	final static String version = System.getProperty("bpipeconfig.version")
    final static String builddate = System.getProperty("bpipeconfig.builddate")?:System.currentTimeMillis()

    public static String user_email

    /**
     * Main Entry Point
     *
     * Use the CliBuilder to handle options And JANSI to colorize output
     *
     */        
	public static void main(String[] args)
	{
		def cli = new CliBuilder(
			usage:  "bpipe-config [pipe|report|recover] [options] [pipeline] ... ", 
    		header: "\nAvailable options (use -h for help):\n",
    		footer: "\n${versionInfo(version)}, ${buildInfo(builddate)}\n", 
    		posix:  true
		)

		cli.with {
			h longOpt: 'help',  'Usage Information', required: false
			m longOpt: 'email', 'User email address (Es: -m user@example.com)', args: 1, required: false
		}

		def opt = cli.parse(args)

		if ( !opt ) System.exit(1)
        if ( opt.h || !opt.arguments() ) {
        	cli.usage()
        	System.exit(1)
        }

		// GET OPTIONS
		if (opt.m) {
			if ( validateEmail(opt.m) ) {
				user_email = opt.m
			} else {
				printVersionAndBuild(version, builddate)
				println()		
				print bold(opt.m)
				print " is not a valid email address\n"
				System.exit(1)
			}
		}

        // HEADER
        printVersionAndBuild()
        // USER OPTIONS
        printUserOptions()
		//System.properties.each { k, v -> println("$k = $v") }
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

	/*
	 * PRINTERS
	 */
	static void printVersionAndBuild()
	{
		print bold(versionInfo(version))
		println "\t${buildInfo(builddate)}"
	}

	static void printUserOptions()
	{
		println()
		println bold("Configuration:")
		println()
		if (user_email)	{
			print "\tUser email: "
			println green("$user_email")
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
}	