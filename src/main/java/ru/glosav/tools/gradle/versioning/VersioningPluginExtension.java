package ru.glosav.tools.gradle.versioning;

/**
 * @author Grigory Panin
 *
 * Расширения плагина версионирования для Gradle
 */
public class VersioningPluginExtension {

    private String startClass;

    public String getStartClass() {
        return startClass;
    }

    public void setStartClass(String startClass) {
        this.startClass = startClass;
    }
}