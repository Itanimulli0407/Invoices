package easp;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Properties;

import de.nixosoft.jlr.JLRConverter;
import de.nixosoft.jlr.JLRGenerator;

public class PDFTester {

	public void testPdf() {
		// Set Directories. RELATIVE !!!
		Properties prop = System.getProperties();
		System.out.println(prop.getProperty("os.name"));
		File dir = new File(this.getClass().getResource("").toString().substring(5) + File.separator + "templates");
		File template = new File(dir.getAbsolutePath() + File.separator + "template_pg_1.tex");
		File tempDir = new File(dir.getAbsolutePath() + File.separator + "tempDir");
		if (!tempDir.isDirectory()) {
			tempDir.mkdir();
		}
		File desktop = new File("/Users/lukas/Desktop/");
		File latexdir = new File("/Library/TeX/texbin/pdflatex");
		File invoice = new File(tempDir.getAbsolutePath() + File.separator + "invoice.tex");

		// Fill File
		JLRConverter converter = new JLRConverter(dir);
		converter.replace("company", "Herr");
		converter.replace("firstName", "Peter");
		converter.replace("lastName", "Müller");
		converter.replace("adress", "Müllerstr. 8");
		converter.replace("city", "66589 Merchweiler");

		try {
			converter.parse(template, invoice);
		} catch (IOException e) {
			System.out.println("Tex-File could not be parsed");
			e.printStackTrace();
		}

		// Output PDF

		JLRGenerator gen = new JLRGenerator();
		try {
			gen.generate(latexdir, invoice, tempDir, dir);
		} catch (IOException e) {
			System.err.println("File could not be generated");
			e.printStackTrace();
		}

		File pdf = gen.getPDF();

		Path source = Paths.get(pdf.getAbsolutePath());
		Path dest = Paths.get(desktop + "/Rechnung.pdf");
		if (Files.exists(dest)) {
			try {
				Files.delete(dest);
			} catch (IOException e) {
				System.err.println("File could not be deleted");
				e.printStackTrace();
			}
		}
		try {
			Files.copy(source, dest);
		} catch (IOException e) {
			System.err.println("Could not move file to desktop");
			e.printStackTrace();
		}

		if (tempDir.isDirectory()) {
			File[] fileList = tempDir.listFiles();
			for (int i = 0; i < fileList.length; i++) {
				try {
					Files.delete(Paths.get((fileList[i].getAbsolutePath())));
				} catch (IOException e) {
					System.err.println("some temp data could not be deleted");
					e.printStackTrace();
				}
			}
		}
		try {
			Files.delete(tempDir.toPath());
		} catch (IOException e) {
			System.err.println("temp data directory could not be deleted");
			e.printStackTrace();
		}

	}

}
