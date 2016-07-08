package astraeus.game.event.impl;

import astraeus.game.event.Event;
import plugin.commands.CommandParser;

/**
 * Represents a command event.
 * 
 * @author Vult-R
 */
public final class CommandEvent implements Event {

	/**
	 * The parser that will parse the command.
	 */
	private final CommandParser parser;	

	/**
	 * Creates the command.
	 *
	 * @param parser
	 * 		The parser that will parse the command input.
	 */
	public CommandEvent(CommandParser parser) {
		this.parser = parser;
	}	
	
	/**
	 * Gets the name of this command.
	 */
	public String getName() {
		return parser.getCommand();
	}

	/**
	 * Gets the parser for this command.
	 */
	public CommandParser getParser() {
		return parser;
	}

}
