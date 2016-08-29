package easp;

import easp.exceptions.EASPException;

public class Checker {

	public Checker() {

	}

	/**
	 * 
	 * @param s
	 * @throws EASPException
	 */
	public void checkFirstName(String s) throws EASPException {
		if (!s.matches("[a-zA-Z\\-\\söüäß]*")) {
			throw (new EASPException());
		}
		if (s.equals("")) {
			throw (new EASPException());
		}
	}

	/**
	 * 
	 * @param s
	 * @throws EASPException
	 */
	public void checkLastName(String s) throws EASPException {
		if (!s.matches("[a-zA-Z\\-\\söüäß]*")) {
			throw (new EASPException());
		}
		if (s.equals("")) {
			throw (new EASPException());
		}
	}

	/**
	 * 
	 * @param s
	 * @throws EASPException
	 */
	public void checkBirthday(String s) throws EASPException {
		if (!s.equals("") && !s.matches("\\d\\d\\.\\d\\d\\.\\d\\d\\d\\d")) {
			throw (new EASPException());
		}
	}

	/**
	 * 
	 * @param street
	 * @param zipCity
	 * @throws EASPException
	 */
	public void checkAdress(String street, String zipCity) throws EASPException {
		if (street.equals("") || zipCity.equals("")) {
			throw (new EASPException());
		}
		if (!street.matches("[a-zA-Z_0-9öüäß\\-\\s]*")) {
			throw (new EASPException());
		}
		if (!zipCity.matches("[0-9]*\\s[a-zA-Z_0-9öüäß\\-\\s]*")) {
			throw (new EASPException());
		}
	}

	/**
	 * 
	 * @param n
	 * @throws EASPException
	 */
	public void checkPhoneNumber(String n) throws EASPException {
		if (!n.matches("[0-9\\-_/\\s+]*") && !n.equals("")) {
			throw (new EASPException());
		}
	}

	/**
	 * 
	 * @param m
	 * @throws EASPException
	 */
	public void checkMail(String m) throws EASPException {
		if (!m.matches("[a-z0-9\\.\\-_]*@[a-z0-9\\.\\-_]*") && !m.equals("")) {
			throw (new EASPException());
		}
	}
	
	public void checkTitle(String title) throws EASPException {
		if (title.length() > 25){
			throw (new EASPException());
		}
	}
	
	public void checkArticle(String article) throws EASPException{
		if (article.equals("")){
			throw new EASPException();
		}
	}
	
	public void checkAmount(String amount) throws EASPException{
		if (amount.equals("")){
			throw new EASPException();
		}
	}
	
	public void checkUnit(String unit) throws EASPException{
		if (unit.equals("")){
			throw new EASPException();
		}
	}
	
	public void checkPPU(String ppu) throws EASPException{
		if (ppu.equals("")){
			throw new EASPException();
		}
	}

}
