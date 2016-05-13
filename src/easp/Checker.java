package easp;

import easp.InputException;
import easp.model.Position;
import javafx.scene.control.TextField;

public class Checker {

	public Checker() {

	}

	/**
	 * 
	 * @param s
	 * @throws InputException
	 */
	public void checkFirstName(String s) throws InputException {
		if (!s.matches("[a-zA-Z\\-\\söüäß]*")) {
			throw (new InputException());
		}
		if (s.equals("")) {
			throw (new InputException());
		}
	}

	/**
	 * 
	 * @param s
	 * @throws InputException
	 */
	public void checkLastName(String s) throws InputException {
		if (!s.matches("[a-zA-Z\\-\\söüäß]*")) {
			throw (new InputException());
		}
		if (s.equals("")) {
			throw (new InputException());
		}
	}

	/**
	 * 
	 * @param s
	 * @throws InputException
	 */
	public void checkBirthday(String s) throws InputException {
		if (!s.equals("") && !s.matches("\\d\\d\\.\\d\\d\\.\\d\\d\\d\\d")) {
			throw (new InputException());
		}
	}

	/**
	 * 
	 * @param street
	 * @param zipCity
	 * @throws InputException
	 */
	public void checkAdress(String street, String zipCity) throws InputException {
		if (street.equals("") || zipCity.equals("")) {
			throw (new InputException());
		}
		if (!street.matches("[a-zA-Z_0-9\\-\\söüäß]*")) {
			throw (new InputException());
		}
		if (!zipCity.matches("[0-9]*\\s[a-zA-Z\\-]*")) {
			throw (new InputException());
		}
	}

	/**
	 * 
	 * @param n
	 * @throws InputException
	 */
	public void checkPhoneNumber(String n) throws InputException {
		if (!n.matches("[0-9\\-_/\\s+]*") && !n.equals("")) {
			throw (new InputException());
		}
	}

	/**
	 * 
	 * @param m
	 * @throws InputException
	 */
	public void checkMail(String m) throws InputException {
		if (!m.matches("[a-z0-9\\.\\-_]*@[a-z0-9\\.\\-_]*") && !m.equals("")) {
			throw (new InputException());
		}
	}
	
	public void checkArticle(String article) throws InputException{
		if (article.equals("")){
			throw new InputException();
		}
	}
	
	public void checkAmount(String amount) throws InputException{
		if (amount.equals("")){
			throw new InputException();
		}
	}
	
	public void checkUnit(String unit) throws InputException{
		if (unit.equals("")){
			throw new InputException();
		}
	}
	
	public void checkPPU(String ppu) throws InputException{
		if (ppu.equals("")){
			throw new InputException();
		}
	}

}
