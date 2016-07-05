package astraeus.content.commands

/**
 * A parser specifically designed for [Command]s.

 * @author Seven
 */
class CommandParser

/**
 * Creates a new [CommandParser].

 * @param command
 * *      The command to parse.
 */
(command: String) {

    /**
     * The arguments for this command.
     */
    private val arguments: Array<String>

    /**
     * The index of the current argument.
     */
    private var argumentIndex = 0

    init {
        this.arguments = command.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    }

    /**
     * Gets the name of the [Command].

     * @return The name.
     */
    val command: String
        get() = arguments[0]

    /**
     * Determines if there are a specified number of arguments for the command.

     * @param arguments
     * *      The number of arguments to check.
     * *
     * *
     * @return `true` If there are a specified number of arguments. `false` otherwise.
     */
    @JvmOverloads fun hasNext(arguments: Int = 1): Boolean {
        return argumentIndex + arguments < this.arguments.size
    }

    /**
     * Gets the next byte in the command arguments.

     * @return The next byte.
     * *
     * @throws NumberFormatException
     */
    @Throws(NumberFormatException::class)
    fun nextByte(): Byte {
        return java.lang.Byte.parseByte(nextString())
    }

    /**
     * Gets the next double in the command arguments.

     * @return The next double.
     * *
     * @throws NumberFormatException
     */
    @Throws(NumberFormatException::class)
    fun nextDouble(): Double {
        return java.lang.Double.parseDouble(nextString())
    }

    /**
     * Gets the next integer in the command arguments.

     * @return The next integer.
     * *
     * @throws NumberFormatException
     */
    @Throws(NumberFormatException::class)
    fun nextInt(): Int {
        return Integer.parseInt(nextString())
    }

    /**
     * Gets the next long in the command arguments.

     * @return The next long.
     * *
     * @throws NumberFormatException
     */
    @Throws(NumberFormatException::class)
    fun nextLong(): Long {
        return java.lang.Long.parseLong(nextString())
    }

    /**
     * Gets the next short in the command arguments.

     * @return The next short.
     * *
     * @throws NumberFormatException
     */
    @Throws(NumberFormatException::class)
    fun nextShort(): Short {
        return java.lang.Short.parseShort(nextString())
    }

    /**
     * Gets the next string in the command arguments.

     * @return The next string.
     * *
     * @throws NumberFormatException
     */
    @Throws(ArrayIndexOutOfBoundsException::class)
    fun nextString(): String {
        if (argumentIndex + 1 >= arguments.size) {
            throw ArrayIndexOutOfBoundsException("The next argument does not exist. [Size: " + arguments.size + ", Attempted: " + (argumentIndex + 1) + "]")
        }
        return arguments[++argumentIndex]
    }

    override fun toString(): String {
        var string = ""
        for (argument in arguments) {
            string += argument + " "
        }
        return string.trim { it <= ' ' }
    }

}
