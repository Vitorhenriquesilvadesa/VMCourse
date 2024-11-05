package org.vmcourse.util;

import org.vmcourse.compiler.component.IOComponent;
import org.vmcourse.compiler.exception.TailRuntimeException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TailFile extends IOComponent<TailFile> {

    private final String source;

    public TailFile(String path) {
        ValidationComponent<String> validator = new FileExtensionValidator("tail");
        validator.validate(path);

        source = getFileContent(path);
    }

    private String getFileContent(String path) {
        StringBuilder builder = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;

            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return builder.toString();
    }

    public String getSource() {
        return source;
    }
}
