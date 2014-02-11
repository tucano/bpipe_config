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
// to get JVM info
import java.lang.management.*
import groovy.time.*

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
		out << green(wrap("Clean .bpipe dir in current working directory or in directory list.", 60, 50)) << "\n"
		out << bold("jvm".padRight(10)) << " ".padLeft(15).padRight(40)
		out << green("Get info on the JVM configuration") << "\n"
		out << bold("smerge".padRight(10)) << "<Project1> <Project2> ...".padLeft(15).padRight(40)
		out << green("Merge sample sheets from different Projects (for meta analysis)") << "\n"
		out << "\nUse: " << green("bpipe-config info <pipeline name>") << " to get info on a pipeline.\n"
		out << green("\nsheet command INFO argument format:\n") << "\tFCID=D2A8DACXX,Lane=3,SampleID=B1,SampleRef=hg19,Index=TTAGGC,Description=description,Control=N,Recipe=MeDIP,Operator=FG,SampleProject=PI_1A_name" << "\n"
		return out.toString()
	}

	static String printSamples(def samples)
	{
		out = new StringBuffer()
		out << "\n" << bold("Samples Info: ") << "\n"
		samples.each { item ->
			out << "\tID: " << bold("${item["SampleID"]}");
			out << "\tREFERENCE: " << bold("${item["SampleRef"]}");
			out << "\tDESCRIPTION: " << bold("${item["Description"]}");
			out << "\tRECIPE: " << bold("${item["Recipe"]}");
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

	static String printJVMInfo()
	{
		def os 	 = ManagementFactory.operatingSystemMXBean
		def rt   = ManagementFactory.runtimeMXBean
		def cl   = ManagementFactory.classLoadingMXBean
		def comp = ManagementFactory.compilationMXBean
		def mem  = ManagementFactory.memoryMXBean
		def td   = ManagementFactory.threadMXBean
		def heapUsage    = mem.heapMemoryUsage
		def nonHeapUsage = mem.nonHeapMemoryUsage
		def pad_right  = 30
		def pad_left   = 60
		def pad_center = 90

		out = new StringBuffer()
		out << green("JVM Configuration:".center(pad_center,"-")) << "\n"

		out << bold("OPERATING SYSTEM:\n")
		out << "architecture:".padRight(pad_right) << "${os.arch}".padLeft(pad_left) << "\n"
		out << "name:".padRight(pad_right) << "${os.name}".padLeft(pad_left) << "\n"
		out << "version:".padRight(pad_right) << "${os.version}".padLeft(pad_left) << "\n"
		out << "processors:".padRight(pad_right) << "${os.availableProcessors}".padLeft(pad_left) << "\n"
		out << "\n"

		out << bold("RUNTIME:\n")
		out << "name:".padRight(pad_right) << "${rt.name}".padLeft(pad_left) << "\n"
		out << "spec name:".padRight(pad_right) << "${rt.specName}".padLeft(pad_left) << "\n"
		out << "vendor:".padRight(pad_right) << "${rt.specVendor}".padLeft(pad_left) << "\n"
		out << "spec version:".padRight(pad_right) << "${rt.specVersion}".padLeft(pad_left) << "\n"
		out << "managment spec version:".padRight(pad_right) << "${rt.managementSpecVersion}".padLeft(pad_left) << "\n"
		out << "\n"

		out << bold("CLASS LOADING SYSTEM:\n")
		out << "isVerbose:".padRight(pad_right) << "${cl.isVerbose()}".padLeft(pad_left) << "\n"
		out << "loadedClassCount:".padRight(pad_right) << "${cl.loadedClassCount}".padLeft(pad_left) << "\n"
		out << "totalLoadedClassCount:".padRight(pad_right) <<"${cl.totalLoadedClassCount}".padLeft(pad_left) << "\n"
		out << "unloadedClassCount:".padRight(pad_right) <<"${cl.unloadedClassCount}".padLeft(pad_left) << "\n"
		out << "\n"

		def comp_time = new TimeDuration(0,0,0,comp.totalCompilationTime.toInteger())
		out << bold("COMPILATION:\n")
		out << "totalCompilationTime:".padRight(pad_right) << "${comp_time.toString()}".padLeft(pad_left) << "\n"
		out << "\n"

		def committed = humanReadableByteCount(heapUsage.committed.toLong(), false)
		def init      = humanReadableByteCount(heapUsage.init.toLong(), false)
		def max       = humanReadableByteCount(heapUsage.max.toLong(), false)
		def used      = humanReadableByteCount(heapUsage.used.toLong(), false)

		out << bold("MEMORY HEAP STORAGE:\n")
		out << "committed:".padRight(pad_right) << "${committed}".padLeft(pad_left) << "\n"
		out << "init:".padRight(pad_right) << "${init}".padLeft(pad_left) << "\n"
		out << "max:".padRight(pad_right) << "${max}".padLeft(pad_left) << "\n"
		out << "used:".padRight(pad_right) << "${used}".padLeft(pad_left) << "\n"
		out << "\n"

		committed = humanReadableByteCount(nonHeapUsage.committed.toLong(), false)
		init      = humanReadableByteCount(nonHeapUsage.init.toLong(), false)
		max       = humanReadableByteCount(nonHeapUsage.max.toLong(), false)
		used      = humanReadableByteCount(nonHeapUsage.used.toLong(), false)

		out << bold("MEMORY NON-HEAP STORAGE:\n")
		out << "committed:".padRight(pad_right) << "${committed}".padLeft(pad_left) << "\n"
		out << "init:".padRight(pad_right) << "${init}".padLeft(pad_left) << "\n"
		out << "max:".padRight(pad_right) << "${max}".padLeft(pad_left) << "\n"
		out << "used:".padRight(pad_right) << "${used}".padLeft(pad_left) << "\n"
		out << "\n"

		out << bold("MEMORY MANAGMENT:\n\n")
		ManagementFactory.memoryPoolMXBeans.each{ mp ->
		    out << green("${mp.name}: ") << "\n\t"
		    String[] mmnames = mp.memoryManagerNames
		    mmnames.each{ mmname ->
		        out << "$mmname "
		    }
		    out << "\n"
		    out << "\tmtype = $mp.type\n"
		    out << "\tUsage threshold supported = " << mp.isUsageThresholdSupported() << "\n"
		}
		out << "\n"

		out << bold("THREADS:\n")
		td.allThreadIds.each { tid ->
		    out << "\t${td.getThreadInfo(tid).threadName}\n"
		}
		out << "\n"

		out << bold("GARBAGE COLLECTION:\n\n")
		ManagementFactory.garbageCollectorMXBeans.each { gc ->
		    def gc_time = new TimeDuration(0,0,0,gc.collectionTime.toInteger())
		    out << green("$gc.name\n")
		    out << "\tcollection count = ${gc.collectionCount}\n"
		    out << "\tcollection time = ${gc_time.toString()}\n"
		    String[] mpoolNames = gc.memoryPoolNames
		    mpoolNames.each { mpoolName ->
		        out << "\tmpool name = $mpoolName\n"
		    }
		}
		out << "\n"
		out << green("END Configuration:".center(pad_center,"-")) << "\n"
		return out.toString()
	}

	// HUMAN READABLE BYTES
	public static String humanReadableByteCount(long bytes, boolean si)
	{
	    int unit = si ? 1000 : 1024
	    if (bytes < unit) return bytes + " B"
	    int exp = (int) (Math.log(bytes) / Math.log(unit))
	    String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1).toString() + (si ? "" : "i")
	    return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre)
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
