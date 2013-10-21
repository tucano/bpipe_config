package bpipeconfig

/**
 * @author tucano
 * Created: Sat Oct 19 17:03:07 CEST 2013
 */

import groovy.util.logging.*
import org.fusesource.jansi.AnsiConsole
import static org.fusesource.jansi.Ansi.*
import static org.fusesource.jansi.Ansi.Color.*
import static org.fusesource.jansi.Ansi.Attribute.*

@Log
class Logger 
{
	// PRINTERS (NO TESTS)
	static void printVersionAndBuild(String version, String builddate)
	{
		print bold(versionInfo(version))
		println "\t${buildInfo(builddate)}"
	}

	static void printPipelines(def pipelines)
	{
		def out = new StringBuffer()
		out << bold("\nListing Pipelines:\n")
		pipelines.each { pipeline ->
			out << "${ bold(pipeline["name"]) } ".padRight(40, "-")
			out << "--> ${ green(pipeline["about_title"]) }\n"
		}
		out << "\n"
		print out.toString()
	}

	// MESSAGES (NO TESTS)
	public static log(String msg)
	{
		log.info msg
	}

	public static info(String msg)
	{
		yellow(msg)
	}

	public static warn(String msg)
	{
		magenta(msg)
	}

	public static error(String msg)
	{
		red(msg)
	}
	
	// HELPERS
	static String versionInfo(String version)
	{
		"BpipeConfig GFU Version ${version}"
	}

	static String buildInfo(String builddate)
	{
		def date = builddate ? new Date(Long.parseLong(builddate)) : null
		"Built on $date"
	}

	// COLORS (NO TESTS)
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

	static org.fusesource.jansi.Ansi magenta(String s)
	{
		ansi().fg(MAGENTA).a(s).reset()
	}

	static org.fusesource.jansi.Ansi yellow(String s)
	{
		ansi().fg(YELLOW).a(s).reset()
	}
}