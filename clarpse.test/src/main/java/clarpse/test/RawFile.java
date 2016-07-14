package clarpse.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Scanner;

public class RawFile implements Serializable, Cloneable {

    private static final long serialVersionUID = -6310632263943431174L;
    private String content;
    private String name;

    public String content() {
		return content;
	}

	public String name() {
		return name;
	}

	public RawFile(final String fileName, final String fileContent) {

        content = fileContent;
        name = fileName;
    }
    
    public RawFile(final File file) throws FileNotFoundException {

        @SuppressWarnings("resource")
        final Scanner scanner = new Scanner(file, "UTF-8");
        content = scanner.useDelimiter("\\A").next();
        name = file.getName();
    }
}