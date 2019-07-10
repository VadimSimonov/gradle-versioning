package ru.glosav.tools.gradle.versioning;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.bundling.Jar;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Grigory Panin
 *
 * Плагин версионирования для Gradle
 */
public class VersioningPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        VersioningPluginExtension extension = project.getExtensions()
            .create("startClass", VersioningPluginExtension.class);

        project.task("customManifest")
            .doLast(task -> {
                Jar jar = (Jar) project.getTasks().getByName(JavaPlugin.JAR_TASK_NAME);

                Map<String, Object> properties = new TreeMap<>();
                if (extension.getStartClass() != null) {
                    properties.put("Start-Class", extension.getStartClass());
                }
                properties.put("Build-Jdk", "" + System.getProperty("java.version") + " " + System.getProperty("java.vendor") + " " + System.getProperty("java.vm.version"));
                properties.put("Build-OS", "" + System.getProperty("os.name") + " " + System.getProperty("os.arch") + " " + System.getProperty("os.version"));
                properties.put("Built-By", System.getProperty("user.name"));
                properties.put("Build-Timestamp", new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(new Date()));

                // TODO attributes

                jar.manifest(manifest -> {
                    manifest.attributes(properties);
                });
            });
    }
}