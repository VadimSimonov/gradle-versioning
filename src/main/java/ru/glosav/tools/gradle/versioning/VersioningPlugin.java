package ru.glosav.tools.gradle.versioning;

import net.nemerosa.versioning.VersionInfo;
import net.nemerosa.versioning.VersioningExtension;
import net.nemerosa.versioning.tasks.VersionDisplayTask;
import net.nemerosa.versioning.tasks.VersionFileTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.bundling.Jar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Grigory Panin
 * <p>
 * Плагин версионирования для Gradle
 */
public class VersioningPlugin implements Plugin<Project> {
    private static final Logger logger = LoggerFactory.getLogger(VersioningPlugin.class);

    @Override
    public void apply(Project project) {
        VersioningPluginExtension extension = project.getExtensions()
            .create("customManifestProperties", VersioningPluginExtension.class);
        System.out.println("inside VersioningPlugin");
        logger.info("logger inside VersioningPlugin");
        String buildDir = System.getProperty("user.dir") + "/build";
        project.setBuildDir(new File(buildDir));
        project.getExtensions().create("versioning", VersioningExtension.class, project);
        project.getTasks().create("versionDisplay", VersionDisplayTask.class);
        project.getTasks().create("versionFile", VersionFileTask.class);

        VersioningExtension versioning = (VersioningExtension) project.getExtensions().getByName("versioning");
        VersionInfo info = versioning.getInfo();
        logger.info("\nversioning info= " + info.toString() + "\n");
        System.out.println("\nversioning info= " + info.toString() + "\n");

        project.task("customManifest")
            .doLast(task -> {
                logger.info("logger inside customManifest task");
                System.out.println("inside customManifest task");
                Jar jar = (Jar) project.getTasks().getByName(JavaPlugin.JAR_TASK_NAME);
                Map<String, Object> properties = new TreeMap<>();
                if (extension.getStartClass() != null) {
                    properties.put("Start-Class", extension.getStartClass());
                }
                properties.put("Build-Jdk", "" + System.getProperty("java.version") + " " + System.getProperty("java.vendor") + " " + System.getProperty("java.vm.version"));
                properties.put("Build-OS", "" + System.getProperty("os.name") + " " + System.getProperty("os.arch") + " " + System.getProperty("os.version"));
                properties.put("Built-By", System.getProperty("user.name"));
                properties.put("Build-Timestamp", new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(new Date()));
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
                properties.put("Created-Tag", versioning.getInfo().getScm());

                // TODO attributes

                jar.manifest(manifest -> {
                    manifest.attributes(properties);
                });
            });
    }

    private void versionProp(VersioningExtension versioning, String buildDir) {
        File file = new File(buildDir, "version.properties");

    }
}