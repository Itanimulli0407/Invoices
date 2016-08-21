package easp.model;

import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Customer {

	private StringProperty firstName, lastName;
	private StringProperty birthday; // Format: yyyy.MM.dd
	private StringProperty street;
	private IntegerProperty zipCode;
	private StringProperty city;
	private StringProperty mail;
	private StringProperty privatePhone;
	private StringProperty mobile;
	private StringProperty work;
	private StringProperty fax;
	private IntegerProperty id;

	/**
	 * Creates a new customer with only default values set.
	 */
	public Customer() {
		this.firstName = new SimpleStringProperty("");
		this.lastName = new SimpleStringProperty("");
		this.birthday = new SimpleStringProperty("");
		this.mail = new SimpleStringProperty("");
		this.street = new SimpleStringProperty("");
		this.zipCode = new SimpleIntegerProperty(0);
		this.city = new SimpleStringProperty("");
		this.privatePhone = new SimpleStringProperty("");
		this.mobile = new SimpleStringProperty("");
		this.work = new SimpleStringProperty("");
		this.fax = new SimpleStringProperty("");
		this.id = new SimpleIntegerProperty(0);

		// TODO: Continous customer ids
	}

	@Override
	public String toString() {
		return (firstName.toString() + "\n" + lastName.toString() + "\n" + birthday.toString() + "\n" + mail.toString()
				+ "\n" + street.toString() + "\n" + zipCode.toString() + "\n" + city.toString() + "\n"
				+ privatePhone.toString());
	}

	/**
	 * 
	 * @return A Map with all numbers and their kinds
	 */
	public Map<String, String> getNumbers() {
		Map<String, String> map = new HashMap<>();
		if (!this.getPrivate().get().trim().equals("")) {
			map.put("Privat", this.getPrivate().get());
		}
		if (!this.getMobile().get().trim().equals("")) {
			map.put("Mobil", this.getMobile().get());
		}
		if (!this.getWork().get().trim().equals("")) {
			map.put("Geschaeftlich", this.getWork().get());
		}
		if (!this.getFax().get().trim().equals("")) {
			map.put("Fax", this.getFax().get());
		}
		return map;
	}

	public StringProperty getFirstName() {
		return firstName;
	}

	public StringProperty getLastName() {
		return lastName;
	}

	public StringProperty getBirthday() {
		return birthday;
	}

	public StringProperty getStreet() {
		return street;
	}

	public IntegerProperty getZipCode() {
		return zipCode;
	}

	public StringProperty getCity() {
		return city;
	}

	public StringProperty getMail() {
		return mail;
	}

	public IntegerProperty getId() {
		return id;
	}

	public StringProperty getPrivate() {
		return privatePhone;
	}

	public StringProperty getMobile() {
		return mobile;
	}

	public StringProperty getWork() {
		return work;
	}

	public StringProperty getFax() {
		return fax;
	}

	public void setFirstName(StringProperty firstName) {
		this.firstName = firstName;
	}

	public void setLastName(StringProperty lastName) {
		this.lastName = lastName;
	}

	public void setBirthday(StringProperty birthday) {
		this.birthday = birthday;
	}

	public void setStreet(StringProperty street) {
		this.street = street;
	}

	public void setZipCode(IntegerProperty zipCode) {
		this.zipCode = zipCode;
	}

	public void setCity(StringProperty city) {
		this.city = city;
	}

	public void setMail(StringProperty mail) {
		this.mail = mail;
	}

	public void setId(IntegerProperty id) {
		this.id = id;
	}

	public void setPrivate(StringProperty phone) {
		this.privatePhone = phone;
	}

	public void setMobile(StringProperty mobile) {
		this.mobile = mobile;
	}

	public void setFax(StringProperty fax) {
		this.fax = fax;
	}

	public void setWork(StringProperty work) {
		this.work = work;
	}

}
