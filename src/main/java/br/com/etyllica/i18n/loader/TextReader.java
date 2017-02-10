package br.com.etyllica.i18n.loader;

import java.io.IOException;
import java.net.URL;

import br.com.etyllica.core.i18n.Dictionary;

public interface TextReader {
	public Dictionary loadDictionary(URL url) throws IOException;	
}
