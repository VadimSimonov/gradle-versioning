package ru.glosav.tools.gradle.versioning;

/**
 * @author Grigory Panin
 *
 * Расширение для лагина версионирования для Gradle
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