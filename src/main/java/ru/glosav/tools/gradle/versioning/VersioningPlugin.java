package ru.glosav.tools.gradle.versioning;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * @author Grigory Panin
 *
 * Плагин версионирования для Gradle
 */
public class VersioningPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        VersioningPluginExtension extension = project.getExtensions()
            .create("greeting", VersioningPluginExtension.class);

        project.task("jar")
            .doLast(task -> {
                System.out.println(
                    "Test plugin message: " + extension.getMessage());
            });
    }
}