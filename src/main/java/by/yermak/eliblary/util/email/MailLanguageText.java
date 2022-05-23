package by.yermak.eliblary.util.email;

public class MailLanguageText {
    public static final String DEFAULT_LANG = "en_EN";
    private static MailLanguageText instance;

    private MailLanguageText() {
    }

    public static MailLanguageText getInstance() {
        if (instance == null) {
            instance = new MailLanguageText();
        }
        return instance;
    }

    public String getText(String currentLocale, String textKey) {
        return currentLocale.equals(DEFAULT_LANG) ? LocaleManager.EN.getString(textKey) : LocaleManager.RU.getString(textKey);
    }
}
