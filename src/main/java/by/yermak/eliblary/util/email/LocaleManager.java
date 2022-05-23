package by.yermak.eliblary.util.email;

import java.util.Locale;
import java.util.ResourceBundle;

public enum LocaleManager {
    EN(ResourceBundle.getBundle("locale",new Locale("en","EN"))),
    RU(ResourceBundle.getBundle("locale",new Locale("ru","RU")));

    private ResourceBundle bundle;

    LocaleManager(ResourceBundle bundle){
        this.bundle = bundle;
    }
    public String getString(String key){
        return bundle.getString(key);
    }
}
