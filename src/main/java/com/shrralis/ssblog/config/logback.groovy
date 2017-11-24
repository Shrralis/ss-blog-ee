package com.shrralis.ssblog.config

appender("STDOUT", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{5} Groovy - %msg%n"
    }
}

/*appender("FILE", RollingFileAppender) {
    file = "~/SHRRALIS.log"

    encoder(PatternLayoutEncoder) {
        pattern = "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{5} Groovy - %msg%n"
    }

    rollingPolicy(FixedWindowRollingPolicy) {
        FileNamePattern = "~/SHRRALIS.%i.log.zip"
        MinIndex = 1
        MaxIndex = 10
    }

    triggeringPolicy(SizeBasedTriggeringPolicy) {
        MaxFileSize = "2MB"
    }
}*/

logger("com.shrralis.ssblog", DEBUG)
root(DEBUG, ["STDOUT"])