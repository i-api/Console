# Console

#### Installation: Consuming a maven/gradle/java package on Github via Jitpack
4. In the "importer/consumer/user repo", import the dependency into build.gradle.kts
```
// build.gradle.kts
repositories {
    mavenCentral()                              // Use Maven Central for resolving dependencies.
    maven { url = uri("https://jitpack.io") }   // CUSTOM EDIT: jitpack, allows github repos to be used as maven java dependencies. Not necessary in this "publish/export/provider/dependency repo". Keeping it here just because it allows access to a wide range of packages. It is used by the "importer/consumer/user repo", which ever repo you want to import dependencies into.
}
dependencies {
    // Use JUnit Jupiter for testing.
    testImplementation(libs.junit.jupiter)

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // This dependency is exported to consumers, that is to say found on their compile classpath.
    api(libs.commons.math3)

    // This dependency is used internally, and not exposed to consumers on their own compile classpath.
    implementation(libs.guava)

    // CUSTOM EDIT:
    implementation("com.github.i-api:Console:1.0.12")    // implementation(files("libs/Console-1.0.11.jar")) // local import, run jar -tf jar tf ~/.m2/repository/com/github/i-api/Console/1.0.11/Console-1.0.11.jar to see import path
}
tasks.withType<Test> {
    useJUnitPlatform()                      // Specifies that JUnit Platform (a.k.a. JUnit 5) should be used to execute tests.
    testLogging.showStandardStreams = true  // gets logging to actually work 😮‍💨😮💀
}
```
5. Import the dependency into <FILENAME>.java
    - intuitively you'd think that the import would follow ```implementation("com.github.i-api:Console:1.0.11")```
    - but this is java. and we don't do that here.
    - If you are confused about the import name of the dependency, run ```jar -tf <PACKAGE_NAME.jar>``` to see the proper import path
```
// <FILENAME>.java
/**
 * Wrapper around for log4j, SLF4j, or any other logger.
 * Provides colorized string functionality.
 */
import org.example.utilities.Console;
public class MyClass {
    public void someMethod() {
        Console console = new Console();
        console.log(Console.Color.PURPLE, "someMethod(): This is console.log!");
    }
}
```



#### usage
```
gradle build --rerun-tasks
gradle clean build --refresh-dependencies --info
gradle clean build publishToMavenLocal
```

#### HELP AND TROUBLESHOOTING
```
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
1. In the "provider/dependency repo", add these lines to build.gradle.kts
```
plugins {
    // Apply the java-library plugin for API and implementation separation.
    `java-library`
    `maven-publish` // need this to publish this as a library. enables command: ./gradlew publish
}
publishing { // need this for jitpack.io to sucessfully build this repo, and it's artifacts
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            groupId = "com.github.i-api"
            artifactId = "Console"
            version = "1.0.12"
        }
    }
}
```
2. Push to github
3. Create a github release with a new tag.
    - for example: 1.0.12
4. Go to https://jitpack.io/#i-api/Console/1.0.12
    - Click on "Get it"
    - copy the import string: "com.github.i-api:Console:1.0.12"




