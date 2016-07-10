package astraeus.util;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

/**
 * Represents an abstract parser.
 *
 * @author  Seven
 */
public abstract class GenericParser implements Runnable {

    /**
     * The single logger for this class.
     */
    protected final Logger LOGGER = Logger.getLogger(GenericParser.class.getName());

    /**
     * The path of the file to parse.
     */
    protected final Path path;

    /**
     * The index of the current line being parsed.
     */
    protected int index;

    /**
     * The file name extension to parse.
     */
    private final String extension;

    private final boolean log;

    /**
     * Creates a new {@link GenericParser}.
     *
     * @param path
     * 		The path of the file to parse.
     *
     * @param extension
     * 		The file name extension.
     *
     * @param log
     * 		The flag that denotes to log messages.
     */
    public GenericParser(String path, String extension , boolean log) {
        this.path = Paths.get(path + extension);
        this.extension = extension;
        this.log = log;
    }

    /**
     * The method that deserializes the file information.
     */
    public abstract void deserialize();

    @Override
    public void run() {
        deserialize();
        onRead();
        if (log) {
            LOGGER.info(toString());
        }
    }

    /**
     * The method called after all the data has been parsed.
     */
    public void onRead() {

    }

    /**
     * Gets the current index of the line being parsed.
     *
     * @return The index of this line.
     */
    public final int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return String.format("Loaded: %d %s.", index, path.getFileName().toString().replaceAll("_", " ").replaceAll(extension, ""));
    }

}
