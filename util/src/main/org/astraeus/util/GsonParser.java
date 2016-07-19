package astraeus.util;

import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.logging.Logger;

public abstract class GsonParser<T> implements Runnable {

	private final Logger logger = LoggerUtils.getLogger(GsonParser.class);

	protected Gson gson;

	protected String path;

	private boolean log;

	public GsonParser(String path) {
		this(path, true);
	}

	public GsonParser(String path, boolean log) {
		this.path = path;
		this.gson = new GsonBuilder().create();
		this.log = log;
	}

	public abstract T[] deserialize(FileReader reader) throws IOException;

	public abstract void onRead(T[] array) throws IOException;

	@Override
	public void run() {
		
		try (FileReader reader = new FileReader(path + ".json")) {
			T[] types = deserialize(reader);

			onRead(types);

			if (log) {
				logger.info("Loaded: " + types.length + " " + path.substring(path.lastIndexOf("/")  + 1).replaceAll("_", " "));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
