package br.com.etyllica.i18n.loader;

import br.com.etyllica.core.i18n.Dictionary;
import br.com.etyllica.core.i18n.Language;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

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
            vocabulary = readLine(line, dictionary, vocabulary);
        }

        return dictionary;
    }

    private Map<String, String> readLine(String line, Dictionary dictionary, Map<String, String> vocabulary) {
        if (line.isEmpty()) {
            return vocabulary;
        }
        String[] parts = line.split(SEPARATOR);
        String key = parts[0].trim();

        if (key.startsWith(PREFIX_COMMENT)) {
            return vocabulary;
        }

        //Avoid exceptions by comments without separator
        String value = parts[1].trim();

        if (KEY_LANGUAGE.equals(key)) {
            Language language = Language.byCode(value);

            dictionary.addLanguage(language);
            dictionary.setDefaultLanguage(language);

            return dictionary.getVocabulary(language);
        } else {
            vocabulary.put(key, value);
        }

        return vocabulary;
    }

}
