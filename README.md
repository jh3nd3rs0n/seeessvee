# SeeEssVee

## Contents

-   [Introduction](#introduction)
-   [License](#license)
-   [Requirements](#requirements)
-   [Generating Javadocs](#generating-javadocs)
-   [Automated Testing](#automated-testing)
-   [Installing](#installing)
-   [Building](#building)

## Introduction

SeeEssVee is a simple Java library for reading and writing CSV files.

```java
package com.example;

import com.github.jh3nd3rs0n.seeessvee.CsvFileReader;
import com.github.jh3nd3rs0n.seeessvee.CsvFileWriter;
import com.github.jh3nd3rs0n.seeessvee.Field;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.List;

public class App {

    public static void main(final String[] args) throws IOException {
        File file = new File("payroll.csv");
        file.createNewFile();
        FileWriter fileWriter = new FileWriter(file);
        try {
            CsvFileWriter csvFileWriter = new CsvFileWriter(fileWriter);
            csvFileWriter.writeRecordFromStringsToBeEscaped(
                "Last Name", "First Name", "Salary");
            csvFileWriter.writeRecordFromStrings(
                "Doe", "Jane", "120,000");
            csvFileWriter.writeRecord(
                Field.newInstance("Doe"), 
                Field.newInstance("John"), 
                Field.newEscapedInstance("120,000"));
        } finally {
            fileWriter.close();
        }
        /*
         * payroll.csv:
         * 
         * "Last Name","First Name","Salary"
         * Doe,Jane,"120,000"
         * Doe,John,"120,000"
         *
         */
        FileReader fileReader = new FileReader(file);
        try {
            CsvFileReader csvFileReader = new CsvFileReader(fileReader);
            List<String> csvRecord = csvFileReader.readRecord();
            System.out.printf(
                "\"%s\", \"%s\", \"%s\"%n", 
                csvRecord.get(0), 
                csvRecord.get(1), 
                csvRecord.get(2));
            while ((csvRecord = csvFileReader.readRecord()).size() > 0) {
                System.out.printf(
                    "%s, %s, %s%n", 
                    csvRecord.get(0), 
                    csvRecord.get(1), 
                    csvRecord.get(2));
            }
        } finally {
            fileReader.close();
        }
        /*
         * Standard output:
         *
         * "Last Name", "First Name", "Salary"
         * Doe, Jane, 120,000
         * Doe, John, 120,000
         *
         */
    }

}
```

SeeEssVee follows the standard set out in 
[RFC 4180](https://www.rfc-editor.org/rfc/rfc4180) with a few differences:

-   Line breaks can also be just the line feed character

-   Header lines are read as CSV records

-   Fields that contain only characters that are not double quote characters, 
comma characters, carriage return characters, or line feed characters do not 
need to be enclosed in double quote characters

-   CSV records within a file do not have to have the same number of fields

## License

SeeEssVee is licensed under the 
[MIT license](https://github.com/jh3nd3rs0n/seeessvee/blob/main/LICENSE).

## Requirements

-   Apache Maven 3.3.9 or higher (for generating javadocs, automated testing, 
installing, and building) 
-   Java 9 or higher

## Generating Javadocs

To generate javadocs, run the following commands:

```bash
cd seeessvee
mvn clean javadoc:javadoc
```

After running the aforementioned commands, the javadocs can be found in the 
following path:

```text
target/site/apidocs
```

## Automated Testing

To run automated testing, run the following command:

```bash
mvn clean test
```

## Installing

To install, run the following command:

```bash
mvn clean install
```

To add a dependency on SeeEssVee using Maven, use the following:

```xml
<dependency>
	<groupId>com.github.jh3nd3rs0n</groupId>
	<artifactId>seeessvee</artifactId>
	<version>1.0.0-SNAPSHOT</version>
</dependency>
```

## Building

To build and package SeeEssVee as a JAR file, run the following command:

```bash
mvn clean package
```

After running the aforementioned command, the JAR file can be found in the 
following path:

```text
target/seeessvee-VERSION.jar
```

Where `VERSION` is the actual version shown within the name of the JAR file.
