# README.md

#### What does it do?
```
/**
 * Wrapper around for log4j, SLF4j, or any other logger.
 * Provides colorized string functionality.
 *
@usage:
package com.my.package.namespace
public class MyClass {
    private final Console console = new Console();
    public void myMethod() {
        console.log(Console.Color.GREEN, "Result from hashPartitionKey():", n);
    }
}
*/
```

#### usage
```
./gradlew clean build --refresh-dependencies --info
gradle build --rerun-tasks
```


#### HELP AND TROUBLESHOOTING
```
/* HELP AND TROUBLESHOOTING
 *
 *
 *
 *
 @note1
// build.gradle.kts: must add the following block to MY_PROJECT_NAME/build.gradle.kts in order for log4j-slf4j to show
tasks.withType<Test> {
    useJUnitPlatform()                      // Specifies that JUnit Platform (a.k.a. JUnit 5) should be used to execute tests.
    testLogging.showStandardStreams = true  // gets logging to actually work 😮‍💨😮💀
}

@note2
// log4j.xml:  Put this file in: MY_PROJECT_NAME/src/test/resources/log4j2.xml     If the folder doesnt exist, make it.
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="INFO">
    <Appenders>
        <Console name="Console" target="SYSTEM_OUT">
            <PatternLayout pattern="%d [%t] %-5level %logger{36} - %msg%n"/>
        </Console>
    </Appenders>
    <Loggers>
        <Root level="info">
            <AppenderRef ref="Console"/>
        </Root>
    </Loggers>
</Configuration>


@see_the_file_tree_example: imagine you are inside the brazil package MY_PROJECT_NAME, which is inside the brazil workspace my_brazil_workspace:
17:20:42 pwd                                                                                ~/my_brazil_workspace/src/MY_PROJECT_NAME mainline[modifieduntrackedahead] via 🅶 via ☕ v21.0.3 via 🅺 on ☁️  (us-east-1) 0
~/my_brazil_workspace/src/MY_PROJECT_NAME
17:20:54 tree                                                                               ~/my_brazil_workspace/src/MY_PROJECT_NAME mainline[modifieduntrackedahead] via 🅶 via ☕ v21.0.3 via 🅺 on ☁️  (us-east-1) 0
.
├── src
│   ├── main
│   │   └── java
│   │       └── com
│   │           └── amazon
│   │               └── MY_PROJECT_NAME
│   │                   ├── activity
│   │                   │   ├── GetTransferActivity.java
│   │                   │   ├── ListAccountMappingsActivity.java
│   │                   │   ├── ListTransfersActivity.java
│   │                   │   └── GetAccountMappingActivity.java
│   │                   ├── MY_PROJECT_NAME.java
│   │                   ├── MYClientModule.java
│   │                   ├── package-info.java
│   │                   └── utilities
│   │                       ├── Console.java
│   │                       └── LoadEnv.java
│   └── test
│       ├── resources
│       │   └── log4j2.xml
│       └── java
│           └── com
│               └── amazon
│                   └── MY_PROJECT_NAME
│                       ├── MY_PROJECT_NAMETest.java
│                       ├── activity
│                       │   └── GetAccountMappingActivityTest.java
│                       └── utilities
│                           ├── ConsoleTest.java
│                           └── LoadEnvTest.java
├── .gitignore
├── Config
├── README.pdf
├── build.gradle.kts
├── example_env.txt
└── settings.gradle.kts
*/
```




#### Publishing a maven/gradle/java package on Github via Jitpack:

1. Add these lines to build.gradle.kts
```
plugins {
    // Apply the java-library plugin for API and implementation separation.
    `java-library`
    `maven-publish` // need this to publish this as a library. enables command: ./gradlew publish
}
publishing { // need this for jitpack.io to sucessfully build this repo, and it's artifacts
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = "com.github.i-api"
            artifactId = "Console"
            version = "1.0.6"
        }
    }
}
```
2. Create a github release with a new tag.
    - for example: 1.0.1
3. Go to https://jitpack.io/#i-api/Console
    - Click on "Get it"
4. Import it into build.gradle.kts of the  "importer/consumer/user repo", not the "provider/dependency repo"
```
repositories {
    mavenCentral()                              // Use Maven Central for resolving dependencies.
    maven { url = uri("https://jitpack.io") }   // jitpack, allows github repos to be used as maven java dependencies. Not necessary in this "publish/export/provider/dependency repo". Keeping it here just because it allows access to a wide range of packages. It is used by the "importer/consumer/user repo", which ever repo you want to import dependencies into.
}
```


