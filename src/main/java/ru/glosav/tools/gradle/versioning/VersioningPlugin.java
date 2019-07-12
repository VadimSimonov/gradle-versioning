package ru.glosav.tools.gradle.versioning;

import net.nemerosa.versioning.VersioningExtension;
import net.nemerosa.versioning.tasks.VersionDisplayTask;
import net.nemerosa.versioning.tasks.VersionFileTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.bundling.Jar;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.Collections.singletonMap;


public class VersioningPlugin implements Plugin<Project> {
    private static final List<String> plugins = Arrays.asList("java", "idea", "org.springframework.boot");

    @Override
    public void apply(Project project) {
        /*for (String plugin : plugins) {
            project.apply(singletonMap("plugin", plugin));
        }*/

        VersioningPluginExtension extension = project.getExtensions()
            .create("customManifestProperties", VersioningPluginExtension.class);

        String buildDir = System.getProperty("user.dir") + "/build";
        project.setBuildDir(new File(buildDir));
        project.getExtensions().create("versioning", VersioningExtension.class, project);
        project.getTasks().create("versionDisplay", VersionDisplayTask.class);
        project.getTasks().create("versionFile", VersionFileTask.class);

        VersioningExtension versioning = (VersioningExtension) project.getExtensions().getByName("versioning");

        project.task("customManifest")
            .doFirst(task -> {
                Jar jar = null;
                Map<String, Object> properties = new TreeMap<>();
                if (extension.getStartClass() != null) {
                    properties.put("Start-Class", extension.getStartClass());
                    jar = (Jar) project.getTasks().getByName("bootJar");
                } else {
                    jar = (Jar) project.getTasks().getByName(JavaPlugin.JAR_TASK_NAME);
                }

                properties.put("Build-Jdk", "" + System.getProperty("java.version") + " " + System.getProperty("java.vendor") + " " + System.getProperty("java.vm.version"));
                properties.put("Build-OS", "" + System.getProperty("os.name") + " " + System.getProperty("os.arch") + " " + System.getProperty("os.version"));
                properties.put("Built-By", System.getProperty("user.name"));
                properties.put("Build-Timestamp", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(new Date()));
                properties.put("Build-Scm", versioning.getInfo().getScm());
                properties.put("Build-BranchType", versioning.getInfo().getBranchType());
                properties.put("Build-BranchId", versioning.getInfo().getBranchId());
                properties.put("Build-Branch", versioning.getInfo().getBranch());
                properties.put("Build-Commit", versioning.getInfo().getCommit());
                properties.put("Build-Build", versioning.getInfo().getBuild());
                properties.put("Build-Full", versioning.getInfo().getFull());
                properties.put("Build-Base", versioning.getInfo().getBase());
                properties.put("Build-Display", versioning.getInfo().getDisplay());
                properties.put("Build-Dirty", versioning.getInfo().getDirty());
                properties.put("Build-Tag", versioning.getInfo().getTag() == null ? "undefined" : versioning.getInfo().getTag());
                properties.put("Created-Tag", project.getVersion().toString());

                jar.manifest(manifest -> {
                    manifest.attributes(properties);
                });
            });

        Task versionFile = project.getTasks().getByName("versionFile");
        VersionFileTask version = (VersionFileTask) versionFile;
/*        version.dependsOn(project.getTasks().getByName(JAR_TASK_NAME));
        version.dependsOn(project.getTasks().getByName("customManifest"));*/
        version.run();

    }
}