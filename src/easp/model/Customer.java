package easp.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Customer {

	private StringProperty firstName, lastName;
	private StringProperty birthday; // Format: dd.mm.yyyy
	private StringProperty street;
	private IntegerProperty zipCode;
	private StringProperty city;
	private StringProperty mail;
	private StringProperty privatePhone;
	private StringProperty mobile;
	private StringProperty work;
	private StringProperty fax;
	private IntegerProperty id = new SimpleIntegerProperty(0); // Continous (->
																// Preferences)

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

		// TODO: Continous customer ids
	}

	public String toString() {
		return (firstName.toString() + "\n" + lastName.toString() + "\n" + birthday.toString() + "\n" + mail.toString()
				+ "\n" + street.toString() + "\n" + zipCode.toString() + "\n" + city.toString() + "\n" + privatePhone.toString());
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
	
	public StringProperty getPrivate(){
		return  privatePhone;
	}
	
	public StringProperty getMobile(){
		return  mobile;
	}
	
	public StringProperty getWork(){
		return  work;
	}
	
	public StringProperty getFax(){
		return  fax;
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
	
	public void setPrivate(StringProperty phone){
		this.privatePhone = phone;
	}
	
	public void setMobile(StringProperty mobile){
		this.mobile = mobile;
	}
	
	public void setFax(StringProperty fax){
		this.fax = fax;
	}
	
	public void setWork(StringProperty work){
		this.work = work;
	}

}
