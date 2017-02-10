package br.com.etyllica.i18n;

import br.com.etyllica.core.i18n.Language;

/**
 * Helper class to load texts
 *
 */
public class I18n {

	private static Language language = Language.ENGLISH_US;
	
	public static String text(String key) {
		return TextLoader.getInstance().getText(language, key);
	}
	
	public static void setLanguage(Language language) {
		I18n.language = language;
	}
	
}
