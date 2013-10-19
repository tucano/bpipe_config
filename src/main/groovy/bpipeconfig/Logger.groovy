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
class Logger {


	public static log(String msg)
	{
		log.info msg
	}

	public static info(String msg)
	{
		
	}

	public static warn(String msg)
	{

	}

	public static error(String msg)
	{
		
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