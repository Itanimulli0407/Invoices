package test;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import easp.Checker;
import easp.InputException;

public class CheckerTests {

	Checker c;

	public CheckerTests() {

	}

	@Before
	public void initialize() {
		this.c = new Checker();
	}

	@Test
	public void nameTestStandard() {
		try {
			c.checkFirstName("Karl");
			c.checkLastName("Meyer");
		} catch (InputException e) {
			fail("\"Karl Meyer\" is a valid firstname");
		}
	}

	@Test
	public void nameTestComplex() {
		try {
			c.checkFirstName("Karl-Heinz-Günther");
			c.checkLastName("Von und Zu Lüttenhörn");
		} catch (InputException e) {
			fail("\"Karl-Heinz-Günther Von und Zu Lüttenhörn\"");
		}
	}

	@Test
	public void nameTestFailNumbers1() {
		try {
			c.checkFirstName("Annel1ese");
			fail("\"Annel1ese\" is not a valid name");
		} catch (InputException e) {

		}
	}

	@Test
	public void nameTestFailNumbers2() {
		try {
			c.checkLastName("F4G0T");
			fail("\"F4G0T\" is not a valid name");
		} catch (InputException e) {

		}
	}

	@Test
	public void nameTestFailSymbols1() {
		try {
			c.checkFirstName("!Franz");
			fail("\"!Franz\" is not a valid name");
		} catch (InputException e) {

		}
	}

	@Test
	public void nameTestFailSymbols2() {
		try {
			c.checkLastName("()R");
			fail("\"()R\" is not a valid name");
		} catch (InputException e) {

		}
	}

	@Test
	public void birthdayCheckSomeBirthdays() {
		try {
			c.checkBirthday("04.05.1955");
			c.checkBirthday("29.03.2003");
			c.checkBirthday("04.07.1996");
			c.checkBirthday("01.01.1980");
		} catch (InputException e) {
			fail("The given birthdays should all be valid");
		}
	}

	@Test
	public void birthdayCheckWrongOrder() {
		try {
			c.checkBirthday("1996.07.04");
			fail("The given birthday should'nt be valid");
		} catch (InputException e) {
		}
	}

	@Test
	public void birthdayCheckFailDay() {
		try {
			c.checkBirthday("ab.b§.1996");
			fail("The given birthday should'nt be valid");
		} catch (InputException e) {
		}
	}

	@Test
	public void birthdayCheckFailMonth() {
		try {
			c.checkBirthday("01.$a.1996");
			fail("The given birthday should'nt be valid");
		} catch (InputException e) {
		}
	}

	@Test
	public void birthdayCheckFailYear() {
		try {
			c.checkBirthday("01.05.7h\"8");
			fail("The given birthday should'nt be valid");
		} catch (InputException e) {
		}
	}

	@Test
	public void birthdayCheckFailLines() {
		try {
			c.checkBirthday("01-02-1996");
			fail("The given birthday should'nt be valid");
		} catch (InputException e) {
		}
	}

	@Test
	public void birthdayCheckEmpty() {
		try {
			c.checkBirthday("");
		} catch (InputException e) {
			fail("The given birthdays should all be valid");
		}
	}

}
