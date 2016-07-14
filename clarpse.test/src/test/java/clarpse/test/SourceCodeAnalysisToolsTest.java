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
	public void QDoxLoadTest()  {
		
		List<RawFile> files = codebase.getSourceFiles();
		JavaDocBuilder qDoxBuilder = new JavaDocBuilder();
		long totalTime = 0;
		for (RawFile file : files) {
			StringReader sr = new StringReader(file.content());
			final long startTime = new Date().getTime();
			qDoxBuilder.addSource(sr);
			totalTime += new Date().getTime() - startTime;
		}
		
		System.out.println(TestUtils.resultMessage(repositoryUser, repositoryName, totalTime, "QDox"));
	}
	
	@Test
	public void JDTLoadTest()  {
		
		List<RawFile> files = codebase.getSourceFiles();
		long totalTime = 0;
		for (RawFile file : files) {
		    ASTParser parser = ASTParser.newParser(AST.JLS4);
		    parser.setKind(ASTParser.K_COMPILATION_UNIT);
		    parser.setSource(file.content().toCharArray());
		    parser.setResolveBindings(true);
		    final long startTime = new Date().getTime();
		    final CompilationUnit parse = (CompilationUnit) parser.createAST(null);
			totalTime += new Date().getTime() - startTime;
		}
		
		System.out.println(TestUtils.resultMessage(repositoryUser, repositoryName, totalTime, "Eclipse JDT"));
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

}
