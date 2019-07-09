package ru.glosav.tools.gradle.versioning;

import net.nemerosa.versioning.VersioningPlugin;
import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.java.archives.Attributes;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.jvm.tasks.Jar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.singletonMap;
import static org.gradle.api.plugins.JavaPlugin.JAR_TASK_NAME;


public class VersionPlugin extends VersioningPlugin {

    public static final String SETUP_TASK_NAME = "setup";

    private static final Logger logger = LoggerFactory.getLogger(VersionPlugin.class);

    private static final List<String> plugins = Arrays.asList("java", "idea");

    @Override
    public void apply(Project project) {

        for (String plugin : plugins) {
            project.apply(singletonMap("plugin", plugin));
        }
            TaskContainer tasks = project.getTasks();
            Jar ts = (Jar) tasks.findByName(JAR_TASK_NAME);
            System.out.println("1111111111111");
            logger.info("logger");
            ts.doLast(new Action<Task>() {
                @Override
                public void execute(Task task) {
                    try {
                        logger.info("logger 22222");
                        System.out.println("2222222");
                        Project project = task.getProject();

                        Attributes attributes = ts.getManifest().getAttributes();
                        attributes.put("Plugin-Name", project.getName());
                        attributes.put("Plugin-Version", project.getVersion());
                    }catch (Exception e){
                        System.out.println("error =>>>>>>>>>>>>>>>");
                        e.printStackTrace();
                    }

                }
            });
    }
}