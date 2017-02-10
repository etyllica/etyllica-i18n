package br.com.etyllica.i18n.loader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import br.com.etyllica.core.i18n.Dictionary;
import br.com.etyllica.core.i18n.Language;

public class INIReader implements TextReader {

	private static final String SEPARATOR = "=";
	private static final String PREFIX_COMMENT = "#";
	
	private static final String KEY_LANGUAGE = "lang";
	
	@Override
	public Dictionary loadDictionary(URL url) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
		
		Map<String, String> vocabulary = new HashMap<String, String>();
		Dictionary dictionary = new Dictionary();
		String line;
        while ((line = br.readLine()) != null) {
        	readLine(line, dictionary, vocabulary);
        }
		
		return dictionary;
	}

	private void readLine(String line, Dictionary dictionary, Map<String, String> vocabulary) {
		String[] parts = line.split(SEPARATOR);
		String key = parts[0].trim();
		
		if (key.startsWith(PREFIX_COMMENT)) {
			return;
		}
		
		//Avoid exceptions by comments without separator
		String value = parts[1].trim();
		
		if (KEY_LANGUAGE.equals(key)) {
			Language language = Language.valueOf(value);
			dictionary.setDefaultLanguage(language);
			dictionary.addLanguage(language);
			
			if (!vocabulary.isEmpty()) {
				dictionary.addLanguage(language, vocabulary);
			}
			
			vocabulary = dictionary.getVocabulary(language);
		} else {
			vocabulary.put(key, value);
		}
	}

}
