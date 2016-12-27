package plugin.analyser;

import java.util.ArrayList;
import java.util.List;

import plugin.model.JavaFileContent;
import plugin.model.WordInFile;

public class JavaModelExpander {

	public List<JavaFileContent> addStructuralStatements(List<WordInFile> methodContent) {
		int i=0;
		List<Integer> intlist = new ArrayList<Integer>();
		{ }
		do {i++;} while (i<0);
		while (i<0) {i++;}
		for (int j=0; j<i; j++)
			{i++;}
		for (Integer k : intlist) {k++;}
		if (i==0) i++; else if (i>9) {i--;}
		try {xyz();} catch (Exception e) {i--;} finally {i++;}
		return null;
	}
	
	private void xyz() throws Exception {
		throw new Exception();
	}
}
