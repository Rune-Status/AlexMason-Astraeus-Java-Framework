package astraeus.util;

import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.logging.Logger;

public abstract class GsonParser<T> implements Runnable {

	private final Logger logger = LoggerUtils.getLogger(GsonParser.class);

	protected String path;

	private boolean log;

	public GsonParser(String path) {
		this(path, true);
	}

	public GsonParser(String path, boolean log) {
		this.path = path;
		this.log = log;
	}

	public abstract T[] deserialize(Gson gson, FileReader reader) throws IOException;

	public abstract void onRead(T[] array) throws IOException;

	@Override
	public void run() {
		
		final Gson gson = new GsonBuilder().create();
		
		try (FileReader reader = new FileReader(path + ".json")) {
			
			T[] types = deserialize(gson, reader);

			onRead(types);

			if (log) {
				logger.info("Loaded: " + types.length + " " + path.substring(path.lastIndexOf("/")  + 1).replaceAll("_", " "));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
