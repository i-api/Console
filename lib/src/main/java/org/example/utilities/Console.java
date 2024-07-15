package org.example.utilities;

import java.lang.reflect.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.invoke.MethodHandles;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
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
@SuppressWarnings("all")
@SuppressFBWarnings
public final class Console {
    private static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    /**
     * @param args varargs, accepts any number of arguments
     * @return output, a colorized output string for log4j to consume
     */
    public String log(final Object... args) {
        if (args.length == 0) {
            return "";
        }
        Object ansiColorString = ""; // we eat our own dogfood using the same class
        Boolean skipFirstArg = false;
        final Class<?> staticClass = Console.Color.class;
        final Field[] fields = staticClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (args[0].equals(field.get(null))) { // If the underlying field is a static field, the obj argument is ignored; it may be null.  https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Field.html#get-java.lang.Object-
                    ansiColorString = args[0];
                    skipFirstArg = true;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        final StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        for (Object arg : args) {
            if (skipFirstArg && i == 0) {
                i += 1; continue;
            }
            stringBuilder.append(arg.toString());
            stringBuilder.append(" ");
        }
        String colorReset = "";
        // If the user doesn't want colorized logging
        // we don't add any escape sequences i.e. output = "" + input + ""
        if (ansiColorString != "") { // if it's NOT empty, then we know the user passed in a colorized string. Thus we need to reset the colorization.
            colorReset = Console.Color.RESET;
        }
        final String output = ansiColorString + stringBuilder.toString() + colorReset;
        logger.info(output);
        return output;
    }

    /**
     * Optional Colorizer Strings
     */
    public static class Color {
        // Color reset
        public static final String RESET = "\033[0m";
        // Regular Colors
        public static final String RED = "\033[0;31m";
        public static final String YELLOW = "\033[0;33m";
        public static final String GREEN = "\033[0;32m";
        public static final String BLUE = "\033[0;34m";
        public static final String PURPLE = "\033[0;35m";
        public static final String CYAN = "\033[0;36m";
        public static final String BLACK = "\033[0;30m";
        public static final String WHITE = "\033[0;37m";
        // Bold
        public static final String BLACK_BOLD = "\033[1;30m";
        public static final String RED_BOLD = "\033[1;31m";
        public static final String GREEN_BOLD = "\033[1;32m";
        public static final String YELLOW_BOLD = "\033[1;33m";
        public static final String BLUE_BOLD = "\033[1;34m";
        public static final String PURPLE_BOLD = "\033[1;35m";
        public static final String CYAN_BOLD = "\033[1;36m";
        public static final String WHITE_BOLD = "\033[1;37m";
        // Background
        public static final String BLACK_BACKGROUND = "\033[40m";
        public static final String RED_BACKGROUND = "\033[41m";
        public static final String GREEN_BACKGROUND = "\033[42m";
        public static final String YELLOW_BACKGROUND = "\033[43m";
        public static final String BLUE_BACKGROUND = "\033[44m";
        public static final String PURPLE_BACKGROUND = "\033[45m";
        public static final String CYAN_BACKGROUND = "\033[46m";
        public static final String WHITE_BACKGROUND = "\033[47m";
    }
}

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
