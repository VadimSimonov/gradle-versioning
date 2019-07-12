gradle-versioning
----
Плагин версионирования

Добавляет в jar файл версионирования versioning.properties и изменяет MANIFEST

---
Пример использования:

Подключение 
classpath group: 'ru.xxx.tools', name: 'gradle-versioning', version: '0.0.1-SNAPSHOT'
apply plugin: 'gradle-versioning'

Для spring boot приложений прописываем в build.gradle:

customManifestProperties{
    startClass = 'ru.xxx.xxx.Application' 
}

bootJar {
    dependsOn 'customManifest'
    from('build') {
        include 'version.properties'
        into 'META-INF'
    }
}

Для остальных:

jar {
    dependsOn 'customManifest'
    from('build') {
        include 'version.properties'
        into 'META-INF'
    }
}
