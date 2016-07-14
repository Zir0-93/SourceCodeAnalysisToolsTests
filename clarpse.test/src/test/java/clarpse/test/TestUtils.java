package clarpse.test;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.IOUtils;

public class TestUtils {

	/**
	 * Iterates through the entries in the input stream and extracts the given
	 * project's file based on the project name (simply extracts everything
	 * under the dir named after the project). If a project name is not
	 * specified then it will extract all the files.
	 *
	 * @param is
	 *            InputStream containing project
	 * @param project
	 *            the name of the project you wish to extract
	 * @throws Exception
	 *             Exception
	 * @return linked hash map of <file name, file code>
	 */
	public static CodeBase extractProjectFromArchive(final InputStream is, final String project) throws Exception {

		final CodeBase sourceFiles = new CodeBase();
		ZipInputStream zis = null;
		try {
			zis = new ZipInputStream(is);
			ZipEntry entry = zis.getNextEntry();
			// iterates over entries in the zip file
			while ((entry != null) ) {
				if (!entry.isDirectory() && entry.getName().endsWith(".java")) {
					sourceFiles.insertFile(new RawFile(entry.getName().replace(" ", "_"), new String(IOUtils
							.toByteArray(zis),
							StandardCharsets.UTF_8)));
				} 

				zis.closeEntry();
				entry = zis.getNextEntry();
			}
			// ensure we actually found some valid source files!
			if ((sourceFiles.getSourceFiles().size() < 1)) {
				System.out.println("No source files contained in the uploaded zip project!");
			}

		} catch (final Exception e) {
			throw new Exception("Error while reading source files from zip!", e);
		} finally {
			if (zis != null) {
				zis.close();
			}
			if (is != null) {
				is.close();
			}
		}
		return sourceFiles;
	}

	/**
	 * Returns the URL of the archive download for a given GitHub repository.
	 */
	public static URL retrieveGithubRepoArchiveURL(String ownerId, String repoName, String branch) throws MalformedURLException {

		final String repoUrl = "https://codeload.github.com/" + ownerId + "/" + repoName
				+ "/zip/" + branch;
		return new URL(repoUrl);
	}

	/**
	 * Returns the URL of the archive download for a given GitHub repository.
	 * @throws IOException 
	 */
	public static RepoInputStream downloadRepoContent(String repoUser, String repoName, String branchName) throws IOException {

		final URL repoArchiveURL = retrieveGithubRepoArchiveURL(repoUser, repoName, branchName);
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		IOUtils.copy(new BufferedInputStream(repoArchiveURL.openStream(), 1024), baos);
		RepoInputStream repoStream = new RepoInputStream();
		repoStream.setBaos(baos.toByteArray());
		repoStream.setUserName(repoUser);
		repoStream.setRepoName(repoName);
		return repoStream;
	}
	
	public static String resultMessage (String repoUser, String repoName, Long time, String tool) {
		
		return "Parsed GitHub Repo: " + repoUser + "/" + repoName + " in " + String.valueOf(time) + "ms using tool: " + tool;
	}
}
