package ru.glosav.tools.gradle.versioning;

import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class VersioningPluginTest {

    @Test
    public void pluginTest() {

        Project project = ProjectBuilder.builder().build();
        project.getPluginManager().apply("gradle-versioning");

        Assertions.assertTrue(project.getPluginManager().hasPlugin("gradle-versioning"));

        //Assertions.assertNotNull(project.getTasks().getByName("hello"));
    }
}