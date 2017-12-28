package clarpse.test;

import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.jboss.forge.roaster.Roaster;
import org.junit.BeforeClass;
import org.junit.Test;

import com.clarity.parser.Lang;
import com.clarity.parser.ParseRequestContent;
import com.clarity.parser.ParseService;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.thoughtworks.qdox.JavaDocBuilder;

public class SourceCodeAnalysisToolsTest {

	// test code base repository to be parsed
	private static String repositoryUser = "zir0-93";
	private static String repositoryName = "java7-sdk";
	private static String branchName = "master";
	private static CodeBase codebase;	
	
	@BeforeClass
	public static void setupCodeBase() throws Exception {

		RepoInputStream repoStream = TestUtils.downloadRepoContent(repositoryUser, repositoryName, branchName);
		codebase = TestUtils.extractProjectFromArchive(new ByteArrayInputStream(repoStream.getBaos()), repositoryName);
	}
	
	
	@Test
	public void JavaParserTest() throws ParseException  {
		
		List<RawFile> files = codebase.getSourceFiles();
		long totalTime = 0;
		for (RawFile file : files) {
		    final long startTime = new Date().getTime();
		    JavaParser.parse(new ByteArrayInputStream(file.content().getBytes(StandardCharsets.UTF_8)));
			totalTime += new Date().getTime() - startTime;
		}
		
		System.out.println(TestUtils.resultMessage(repositoryUser, repositoryName, totalTime, "JavaParser"));
	}
	
	@Test
	public void RoasterTest() throws ParseException  {
		
		List<RawFile> files = codebase.getSourceFiles();
		long totalTime = 0;
		for (RawFile file : files) {
		    final long startTime = new Date().getTime();
		    Roaster.parseUnit(file.content());
			totalTime += new Date().getTime() - startTime;
		}
		
		System.out.println(TestUtils.resultMessage(repositoryUser, repositoryName, totalTime, "Roasterr"));
	}

	@Test
	public void ClarpseLoadTest() throws Exception  {
		ParseRequestContent rawData = new ParseRequestContent(Lang.JAVA);
		List<RawFile> files = codebase.getSourceFiles();
		for (RawFile file : files) {
			rawData.insertFile(new com.clarity.parser.RawFile(file.name(), file.content()));
		}
		final ParseService parser = new ParseService();
		final long startTime = new Date().getTime();
		parser.parseProject(rawData); 
		long totalTime = new Date().getTime() - startTime;
		System.out.println(TestUtils.resultMessage(repositoryUser, repositoryName, totalTime, "Clarpse"));
	}
}
