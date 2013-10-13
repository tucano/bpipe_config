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

class BpipeConfig 
{
	final static String version = System.getProperty("bpipeconfig.version")
    final static String builddate = System.getProperty("bpipeconfig.builddate")?:System.currentTimeMillis()

    /**
     * Main Entry Point
     *
     * Use the CliBuilder to handle options And JANSI to colorize output
     *
     */        
	public static void main(String[] args)
	{

		//System.properties.each { k, v -> println("$k = $v") }
		// Test ansi:
		print bold(versionInfo(version))
		println "\t${buildInfo(builddate)}"
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

	/*
	 * COLORIZERS
	 */
	static org.fusesource.jansi.Ansi bold(String s)
	{
		ansi().a(INTENSITY_BOLD).a(s).reset()
	}
}	