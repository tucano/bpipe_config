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

	private static StringBuffer out

	static String printVersionAndBuild(String version, String builddate)
	{
		out = new StringBuffer()
		out << bold(versionInfo(version))
		out << "\t${buildInfo(builddate)}"
		return out.toString()
	}

	static String printPipelines(def pipelines)
	{
		out = new StringBuffer()
		out << "\n"
		pipelines.each { category, list ->
			out << "${bold(category)}" << "\n"
			list.each { pipeline ->
				out << "${ pipeline["name"] } ".padRight(40, "-")
				out << "--> ${ green(pipeline["about_title"]) }\n"
			}
			out << "\n"
		}
		out << "\n"
		return out.toString()
	}

	static String printHelpCommands()
	{
		out = new StringBuffer()
		out << bold("\nAvailable Commands:\n")
		out << bold("config".padRight(10)) << "[dir1] [dir2] ... ".padLeft(15).padRight(40)
		out	<< green(wrap("Configure current directory or directories in list (add bpipe.config file).", 60, 50)) << "\n"
		out << bold("sheet".padRight(10)) << "<INFO> [dir1] [dir2] ... ".padLeft(15).padRight(40)
		out	<< green(wrap("Generate a SampleSheet.csv file using the INFO string in current directory or directories in list. SampleProject format: <PI_name>_<ProjectID>_<ProjectName>", 60, 50)) << "\n"
		out << bold("pipe".padRight(10)) << "<pipeline name> [dir1] [dir2] ... ".padLeft(15).padRight(40)
		out << green(wrap("Generate pipeline file in current directory or directories in list (pipeline.groovy)",60, 50)) << "\n"
		out << bold("info".padRight(10)) << "<pipeline name> ... ".padLeft(15).padRight(40)
		out << green("Get info on pipeline stages.") << "\n"
		out << bold("clean".padRight(10)) << "[dir1] [dir2] ... ".padLeft(15).padRight(40)
		out << green(wrap("Clean all in current working directory or in directory list: intermediate files, bpipe.config file, gfu_environment files, bpipe directories.", 60, 50)) << "\n"
		out << bold("report".padRight(10)) << "<pipe1.groovy> <pipe2.groovy> ... ".padLeft(15).padRight(40)
		out << green("Generate reports for pipeline.groovy files.") << "\n"
		out << bold("recover".padRight(10)) << " ".padLeft(15).padRight(40)
		out << green(wrap("\tRecover log files from .bpipe in current dir (jobs IDs and output file names).", 60, 50)) << "\n"
		out << "\nUse: " << green("bpipe-config info <pipeline name>") << " to get info on a pipeline.\n"
		out << green("\nsheet command INFO argument format:\n") << "\tFCID=D2A8DACXX,Lane=3,SampleID=B1,SampleRef=hg19,Index=TTAGGC,Description=niguarda,Control=N,Recipe=MeDIP,Operator=FG,SampleProject=PI_1A_name" << "\n"
		return out.toString()
	}

	static String printSamples(def samples)
	{
		out = new StringBuffer()
		out << "\n" << bold("Samples Info: ")
		samples.each { item ->
			out << "\tID: "; print bold("${item["SampleID"]}");
			out << "\tREFERENCE: "; print bold("${item["SampleRef"]}");
			out << "\tDESCRIPTION: "; print bold("${item["Description"]}");
			out << "\tRECIPE: "; print bold("${item["Recipe"]}");
			out << "\n"
		}
		return out.toString()
	}

	static String printUserOptions()
	{
		out = new StringBuffer()
		out << "\n" << bold("Configuration:") << "\n"
		out << "\tCommand            = " << green(BpipeConfig.command) << "\n"
		out << "\tWorking Directory  = " << green(BpipeConfig.working_dir) << "\n"
		out << "\tProject Name       = " << green(BpipeConfig.project_name) << "\n"
		out << "\tUsername           = " << green(BpipeConfig.user_name) << "\n"

		if (BpipeConfig.user_email)	{
			out << "\tUser email         = " << green("${BpipeConfig.user_email}") << "\n"
		}
		out << "\tBpipe Home         = " << green(BpipeConfig.bpipe_home) << "\n"
		out << "\tJRE version        = " << green(BpipeConfig.java_runtime_version) << "\n"
		return out.toString()
	}

	// MESSAGES (NO TESTS)
	static String log(String msg)
	{
		log.info msg
	}

	static String message(String msg)
	{
		green(msg)
	}

	static String info(String msg)
	{
		msg
	}

	static String warn(String msg)
	{
		magenta(msg)
	}

	static String error(String msg)
	{
		red(msg)
	}

	static String error(String key, String msg)
	{

		out = new StringBuffer()
		out << redbold(key) << red(msg)
		out.toString()
	}

	// HELPERS
	static String wrap(String text, int columns = 80, int padLeft = 0)
	{
		if ( text == null ) return null
		if ( text.empty ) return ""
		String result = ""
		String line = ""
		def counter = 0
		text.split(" ").each { word ->
			if (word.length() > columns) {
				def part = word.substring(0, columns - line.length())
				line += part
				word = word.substring(part.length())
			}
			if (line.length() + word.length() > columns) {
				if (counter == 0) {
					result += line.trim() + "\n"
				} else {
					result += line.trim().padLeft(padLeft + line.length() -1) + "\n"
				}
				counter++
				line = ""
			}
			while (word.length() > columns) {
				if (counter == 0) {
					result += word.substring(0, columns) + "\n"
					word = word.substring(columns)
				} else {
					result += word.substring(0, columns).padLeft(padLeft + line.length() -1) + "\n"
					word = word.substring(columns)
				}
				counter++
			}
			line += word + " "
		}
		result += line.trim().padLeft(padLeft + line.length() -1)
		result
	}

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

	static org.fusesource.jansi.Ansi redbold(String s)
	{
		ansi().fg(RED).a(INTENSITY_BOLD).a(s).reset()
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
