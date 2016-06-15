package easp;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Properties;

import de.nixosoft.jlr.JLRConverter;
import de.nixosoft.jlr.JLRGenerator;
import easp.model.Customer;
import easp.model.Position;

public class PDFMaker {

	private File dir, tempDir;
	private File invoice;
	private File template;
	private File latexdir;
	private File desktop;

	public void makePDF(Customer c, ArrayList<Position> positions) {
		// Set Directories. RELATIVE !!!
		Properties prop = System.getProperties();
		String system = prop.getProperty("os.name");
		System.out.println(prop.getProperty("os.name"));
		if (system.contains("Mac"))
			makePDFMac(c, positions);
		else
			makePDFWindows(c, positions);

		// Fill File
		ArrayList<ArrayList<String>> services = new ArrayList<ArrayList<String>>();
		DecimalFormat f = new DecimalFormat("#0.00");
		double sum = 0.00;
		for (Position pos : positions) {
			sum += pos.getPrice();
		}
		for (int i = 0; i < positions.size(); i++) {
			ArrayList<String> subservice = new ArrayList<String>();
			subservice.add(String.valueOf(i + 1));
			subservice.add(f.format((positions.get(i).getAmount())));
			subservice.add(positions.get(i).getUnit());
			if (positions.get(i).getTitle() != "") {
				subservice.add("\\textbf{\\underline{" + positions.get(i).getTitle() + "}}:\\newline "
						+ positions.get(i).getArticle());
			} else {
				subservice.add(positions.get(i).getArticle());
			}
			subservice.add(f.format(positions.get(i).getPricePerUnit()) + "€");
			subservice.add(f.format(positions.get(i).getPrice()) + "€");
			services.add(subservice);
		}

		JLRConverter converter = new JLRConverter(this.dir);
		converter.replace("firstName", c.getFirstName().get());
		converter.replace("lastName", c.getLastName().get());
		converter.replace("adress", c.getStreet().get());
		converter.replace("city", String.valueOf(c.getZipCode().get()) + " " + c.getCity().get());
		converter.replace("services", services);
		converter.replace("netto", f.format(sum));
		converter.replace("mwst", f.format(sum * 0.19));
		converter.replace("brutto", f.format(sum + sum * 0.19));

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

	private void makePDFWindows(Customer c, ArrayList<Position> positions) {
		this.dir = new File(this.getClass().getResource("").toString().substring(5) + File.separator + "templates");
		this.template = new File(dir.getAbsolutePath() + File.separator + "template_pg_1.tex");
		this.tempDir = new File(dir.getAbsolutePath() + File.separator + "tempDir");
		if (!tempDir.isDirectory()) {
			tempDir.mkdir();
		}
		Properties prop = System.getProperties();
		String user = prop.getProperty("user.name");
		this.desktop = new File("C:\\Users\\"+user+"\\Desktop");
		this.latexdir = new File("C:\\texlive\\2016\\bin\\win32\\pdflatex");
		this.invoice = new File(tempDir.getAbsolutePath() + File.separator + "invoice.tex");
	}

	private void makePDFMac(Customer c, ArrayList<Position> positions) {
		this.dir = new File(this.getClass().getResource("").toString().substring(5) + File.separator + "templates");
		this.template = new File(dir.getAbsolutePath() + File.separator + "template_pg_1.tex");
		this.tempDir = new File(dir.getAbsolutePath() + File.separator + "tempDir");
		if (!tempDir.isDirectory()) {
			tempDir.mkdir();
		}
		this.desktop = new File("/Users/lukas/Desktop/");
		this.latexdir = new File("/Library/TeX/texbin/pdflatex");
		this.invoice = new File(tempDir.getAbsolutePath() + File.separator + "invoice.tex");
	}

}
