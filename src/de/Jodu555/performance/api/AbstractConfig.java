package de.Jodu555.performance.api;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

import org.bukkit.configuration.file.YamlConfiguration;


public class AbstractConfig {
	
	File folder, file;

	YamlConfiguration cfg;

	String filename;

	Consumer<AbstractConfig> onCreateConsumer;

	public AbstractConfig(String foldername, String filename, Boolean instcrate) {
		this.filename = filename;
		folder = new File("plugins//" + foldername);
		file = new File("plugins//" + foldername + "//" + filename);
		cfg = YamlConfiguration.loadConfiguration(file);
		if (instcrate) {
			create();
		}
	}

	public AbstractConfig setConsumer(Consumer<AbstractConfig> onCreateConsumer) {
		this.onCreateConsumer = onCreateConsumer;
		create();
		return this;
	}

	public AbstractConfig(String foldername, String filename) {
		this.filename = filename;
		folder = new File("plugins//" + foldername);
		file = new File("plugins//" + foldername + "//" + filename);
		cfg = YamlConfiguration.loadConfiguration(file);
		create();
	}

	public AbstractConfig(String foldername, String filename, Consumer<AbstractConfig> onCreateConsumer) {
		this.filename = filename;
		folder = new File("plugins//" + foldername);
		file = new File("plugins//" + foldername + "//" + filename);
		cfg = YamlConfiguration.loadConfiguration(file);
		this.onCreateConsumer = onCreateConsumer;
		create();
	}

	private void create() {
		if (exists()) {
			return;
		}
		folder.mkdir();
		try {
			file.createNewFile();
			cfg.save(file);
			if (onCreateConsumer != null)
				onCreateConsumer.accept(this);
			save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean exists() {
		return file.exists();
	}

	public void save() {
		try {
			cfg.save(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public YamlConfiguration getCfg() {
		return cfg;
	}

	public String getFilename() {
		return filename;
	}

	public File getFile() {
		return file;
	}
	
}
