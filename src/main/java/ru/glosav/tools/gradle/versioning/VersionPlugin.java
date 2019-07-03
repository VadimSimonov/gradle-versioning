package ru.glosav.tools.gradle.versioning;

import net.nemerosa.versioning.VersioningPlugin;
import org.gradle.api.Project;
import org.gradle.tooling.BuildLauncher;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author Grigory Panin
 * <p>
 * Плагин версионирования для Gradle
 */
public class VersionPlugin extends VersioningPlugin {
    private static final Logger logger = LoggerFactory.getLogger(VersionPlugin.class);

    //private static final String GRADLE_INSTALLATION = System.getenv("GRADLE_HOME");
    private static final String GRADLE_INSTALLATION = "/opt/gradle";
    private static final String GRADLE_PROJECT_DIRECTORY = System.getProperty("user.dir");
    private static final String GRADLE_TASK = "jar";

    @Override
    public void apply(Project project) {
        GradleConnector connector = GradleConnector.newConnector();
        connector.useInstallation(new File(GRADLE_INSTALLATION));
        connector.forProjectDirectory(new File(GRADLE_PROJECT_DIRECTORY));

        ProjectConnection connection = connector.connect();
        BuildLauncher build = connection.newBuild();
        build.forTasks(GRADLE_TASK);

        build.run();
        connection.close();

      /*  ToolingAPI toolingAPI = new ToolingAPI(GRADLE_INSTALLATION, GRADLE_PROJECT_DIRECTORY);
        toolingAPI.executeTask(GRADLE_TASK);*/

/*        VersioningExtension versioning = project.getExtensions().create("versioning", VersioningExtension.class, project);
        //  versioning.getInfo().setTag(version);
        //  versioning.getInfo().setDisplay(version);
        //   createManifest(versioning, project);

        // `versionDisplay` task
        project.getTasks().create("versionDisplay", VersionDisplayTask.class);
        // `versionFile` task
        project.getTasks().create("versionFile", VersionFileTask.class);*/
    }


/*    private void createManifest(VersioningExtension versioning, Project project) {
        //String projectPath = System.getProperty("user.dir") + "/build/version.properties";
        //bundleLocation = createPluginBundle("test-plugin-bundle");
        //File manifestFile = new File(bundleLocation, "META-INF/MANIFEST.MF");

        DefaultManifest manifest = new DefaultManifest(new IdentityFileResolver());

        Map map = new HashMap();
        map.put("Build-Jdk", System.getProperty("java.version") + System.getProperty("java.vendor")
            + System.getProperty("java.vm.version"));
        map.put("Build-OS", System.getProperty("os.name") + System.getProperty("os.arch") + System.getProperty("os.version"));
        map.put("Built-By", System.getProperty("user.name"));
        map.put("Build-Timestamp", new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(new Date()));
        map.put("Build-Scm", versioning.getInfo().getScm());
        map.put("Build-BranchType", versioning.getInfo().getBranchType());
        map.put("Build-BranchId", versioning.getInfo().getBranchId());
        map.put("Build-Branch", versioning.getInfo().getBranch());
        map.put("Build-Commit", versioning.getInfo().getCommit());
        map.put("Build-Build", versioning.getInfo().getBuild());
        map.put("Build-Full", versioning.getInfo().getFull());
        map.put("Build-Base", versioning.getInfo().getBase());
        map.put("Build-Display", versioning.getInfo().getDisplay());
        map.put("Build-Dirty", versioning.getInfo().getDirty());
        map.put("Build-Tag", versioning.getInfo().getTag() == null ? "undefined" : versioning.getInfo().getTag());
        map.put("Created-By", "Gradle ${gradle.gradleVersion}");

        manifest.attributes(map);

        //manifest.writeTo(projectPath);

    }*/

}