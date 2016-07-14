package clarpse.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a file in a project.
 *
 * @author Muntazir Fadhel
 */
public class CodeBase {

    private List<RawFile> sourceFiles = new ArrayList<RawFile>();

    /**
     * @return the sourceFiles
     */
    public List<RawFile> getSourceFiles() {
        return sourceFiles;
    }

    /**
     * Returns a string containing the file's contents.
     *
     * @param fileName
     *            file name of the file whose contents is required
     * @return String containing file contents.
     */
    public String getFileContents(final String fileName) {
        for (final RawFile file : sourceFiles) {
            if (file.name().equals(fileName)) {
                return file.content();
            }
        }
        return "Clarity could not retrieve the contents of that file.";
    }

    /**
     * inserts a source file.
     *
     * @param RawFile
     *            file to insert
     */
    public void insertFile(final RawFile RawFile) {
        sourceFiles.add(RawFile);
    }

    public void setFiles(final List<RawFile> files) {
        sourceFiles = files;
    }

    /**
     * public constructor
     */
    public CodeBase() {
    }
}