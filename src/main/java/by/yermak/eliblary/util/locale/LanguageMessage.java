package by.yermak.eliblary.util.locale;

public class LanguageMessage {
    public static final String DEFAULT_LANG = "en_EN";
    private static LanguageMessage instance;

    private LanguageMessage() {
    }

    public static LanguageMessage getInstance() {
        if (instance == null) {
            instance = new LanguageMessage();
        }
        return instance;
    }

    public String getText(String currentLocale, String textKey) {
        return currentLocale.equals(DEFAULT_LANG) ? LocaleManager.EN.getString(textKey) : LocaleManager.RU.getString(textKey);
    }
}
