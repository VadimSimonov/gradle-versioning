package ru.glosav.tools.gradle.versioning;

/**
 * @author Grigory Panin
 *
 * Расширения плагина версионирования для Gradle
 */
public class VersioningPluginExtension {

    private String message = "Message from the plugin!";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}