package br.com.etyllica.i18n;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.etyllica.core.i18n.Dictionary;
import br.com.etyllica.core.i18n.Language;
import br.com.etyllica.i18n.loader.INIReader;
import br.com.etyllica.i18n.loader.TextReader;
import br.com.etyllica.loader.LoaderImpl;
import br.com.etyllica.util.StringUtils;
import br.com.etyllica.util.io.IOHelper;

public class TextLoader extends LoaderImpl {

	private static TextLoader instance = null;

	private Map<String, TextReader> loaders = new HashMap<String, TextReader>();

	private List<Dictionary> index = new ArrayList<Dictionary>();
	private Map<String, Dictionary> dictionaries = new HashMap<String, Dictionary>();

	private final static String INI = "ini";
	private final static String TXT = "txt";
	private final static String CONF = "conf";

	private TextLoader() {
		super();

		folder = "assets/lang/";

		loaders.put(INI, new INIReader());
		loaders.put(TXT, new INIReader());
		loaders.put(CONF, new INIReader());
	}

	public static TextLoader getInstance() {
		if (instance == null) {
			instance = new TextLoader();
		}

		return instance;
	}

	public Dictionary getDictionary(String path) {
		return loadDictionary(path);
	}

	public Dictionary loadDictionary(String path) {
		String fullPath = fullPath() + path;

		if (dictionaries.containsKey(fullPath)) {
			return dictionaries.get(fullPath);
		} else {

			URL dir = null;

			try {
				dir = new URL(url, fullPath);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}

			String ext = StringUtils.fileExtension(path);
			TextReader reader = loaders.get(ext);

			Dictionary dictionary;
			try {
				dictionary = reader.loadDictionary(dir);
				dictionaries.put(fullPath, dictionary);
				index.add(dictionary);
				return dictionary;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return dictionaries.get(path);
	}

	public void disposeDictionary(String path) {
		Dictionary dictionary = dictionaries.get(path);
		dictionaries.remove(path);
		index.remove(dictionary);
	}

	public String getText(Language language, String key) {
		for (Dictionary dictionary : index) {
			String value = dictionary.getText(language, key);
			if (value != null) {
				return value;
			}
		}
		return "";
	}

}
