package org.example.demo.LOGIC;

import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageManager {

    private static Locale currentLocale = new Locale("es"); // Por defecto español
    private static ResourceBundle messages;

    static {
        loadMessages();
    }

    private static void loadMessages() {
        // Carga los archivos messages_es.properties o messages_en.properties
        try {
            messages = ResourceBundle.getBundle("org.example.demo.messages", currentLocale);
        } catch (Exception e) {
            System.err.println("Error cargando bundle de idioma: " + e.getMessage());
        }
    }

    public static String getString(String key) {
        try {
            return messages.getString(key);
        } catch (Exception e) {
            return "Key not found: " + key;
        }
    }

    public static void toggleLanguage() {
        if (currentLocale.getLanguage().equals("es")) {
            currentLocale = new Locale("en");
        } else {
            currentLocale = new Locale("es");
        }
        loadMessages();
    }

    public static Locale getCurrentLocale() {
        return currentLocale;
    }
}