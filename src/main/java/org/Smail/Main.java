package org.Smail;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.io.*;

public class Main {

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.print("Enter the path of the text file: ");
            String textFilePath = reader.readLine();
            String outputDirectory = getOutputDirectory();
            generateJavaFiles(textFilePath, outputDirectory);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String getOutputDirectory() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "GeneratedClasses";
        } else {
            return System.getProperty("user.home") + File.separator + "GeneratedClasses";
        }
    }

    public static void generateJavaFiles(String textFilePath, String outputDirectory) {
        try {
            File inputFile = new File(textFilePath);
            // Create the output directory if it doesn't exist
            File outputDir = new File(outputDirectory);
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }

            // Create a temporary file
            File tempFile = File.createTempFile("temp", ".txt");
            tempFile.deleteOnExit();

            // Copy the contents of the input file to the temporary file
            try (BufferedReader inputReader = new BufferedReader(new FileReader(inputFile));
                 BufferedWriter tempWriter = new BufferedWriter(new FileWriter(tempFile))) {

                String line;
                while ((line = inputReader.readLine()) != null) {
                    tempWriter.write(line);
                    tempWriter.newLine();
                }
            }

            // Process the temporary file
            String fileContents = new String(Files.readAllBytes(tempFile.toPath()));
            String[] classDefinitions = fileContents.split("\\}\\s*");

            for (String classDefinition : classDefinitions) {
                String[] lines = classDefinition.trim().split("\n");
                String className = lines[0].split(" ")[2].trim();
                StringBuilder javaCode = new StringBuilder();

                // Generate class body
                javaCode.append("public class ").append(className).append(" {\n\n");

                // Generate fields
                for (String line : lines) {
                    if (line.contains("private")) {
                        javaCode.append("\t").append(line.trim()).append("\n");
                    }
                }

                // Generate constructors
                javaCode.append("\tpublic ").append(className).append("(");

                for (String line : lines) {
                    if (line.contains("private")) {
                        String[] parts = line.split("\\s+");
                        String type = parts[2];
                        String variable = parts[3].substring(0, parts[3].length() - 1);
                        javaCode.append(type).append(" ").append(variable).append(", ");
                    }
                }

                // Remove the trailing comma and space
                javaCode.setLength(javaCode.length() - 2);

                javaCode.append(") {\n");

                for (String line : lines) {
                    if (line.contains("private")) {
                        String[] parts = line.split("\\s+");
                        String variable = parts[3].substring(0, parts[3].length() - 1);
                        javaCode.append("\t\tthis.").append(variable).append(" = ").append(variable).append(";\n");
                    }
                }

                javaCode.append("\t}\n\n");

                // Generate constructor
                javaCode.append("\tpublic ").append(className).append("() {\n");
                javaCode.append("\t\t// Constructor logic\n");
                javaCode.append("\t}\n\n");
                // Generate getters and setters
                for (String line : lines) {
                    if (line.contains("private")) {
                        String[] parts = line.split("\\s+");
                        String type = parts[2];
                        String variable = parts[3].substring(0, parts[3].length() - 1);
                        String capitalizedVariable = variable.substring(0, 1).toUpperCase() + variable.substring(1);

                        // Generate getter
                        javaCode.append("\tpublic ").append(type).append(" get").append(capitalizedVariable).append("() {\n")
                                .append("\t\treturn ").append(variable).append(";\n")
                                .append("\t}\n\n");

                        // Generate setter
                        javaCode.append("\tpublic void set").append(capitalizedVariable).append("(").append(type).append(" ").append(variable).append(") {\n")
                                .append("\t\tthis.").append(variable).append(" = ").append(variable).append(";\n")
                                .append("\t}\n\n");
                    }
                }


                javaCode.append("}");

                String javaFilePath = outputDirectory + File.separator + className + ".java";
                FileWriter fileWriter = new FileWriter(javaFilePath);
                fileWriter.write(javaCode.toString());
                fileWriter.close();

                System.out.println("Successfully generated " + className + ".java");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String generateJavaCode(String className, String classBody) {
        StringBuilder javaCode = new StringBuilder();
        javaCode.append("public class ").append(className).append(" {\n\n");
        javaCode.append(classBody).append("\n");

        // Generate constructor
        javaCode.append("\tpublic ").append(className).append("() {\n");
        javaCode.append("\t\t// Constructor logic\n");
        javaCode.append("\t}\n\n");

        // Generate getters and setters
        String[] lines = classBody.split("\n");
        for (String line : lines) {
            if (line.contains("private")) {
                String[] parts = line.split("\\s+");
                String type = parts[2];
                String variable = parts[3].substring(0, parts[3].length() - 1);
                String capitalizedVariable = variable.substring(0, 1).toUpperCase() + variable.substring(1);

                // Generate getter
                javaCode.append("\tpublic ").append(type).append(" get").append(capitalizedVariable).append("() {\n");
                javaCode.append("\t\treturn ").append(variable).append(";\n");
                javaCode.append("\t}\n\n");

                // Generate setter
                javaCode.append("\tpublic void set").append(capitalizedVariable).append("(").append(type).append(" ").append(variable).append(") {\n");
                javaCode.append("\t\tthis.").append(variable).append(" = ").append(variable).append(";\n");
                javaCode.append("\t}\n\n");
            }
        }

        javaCode.append("}");

        return javaCode.toString();
    }
}
