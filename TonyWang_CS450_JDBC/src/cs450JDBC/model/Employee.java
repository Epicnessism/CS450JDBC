/**
 * Model Class for an Employee
 * @author Tony Wang
 * CS450 Final Project
 * 4/21/19
 */

package cs450JDBC.model;

import java.time.LocalDate;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;


public class Employee {
	
	private StringProperty firstName;
	private StringProperty middleName;
	private StringProperty lastName;
	private LongProperty ssn;
	private ObjectProperty<LocalDate> birthday;
	private StringProperty address;
	private StringProperty sex;
	private DoubleProperty salary;
	private LongProperty superSsn;
    private IntegerProperty dno;
    private StringProperty email;
    
  //default constructor?? idk //TODO
  public Employee(String firstName, String lastName) {
	  this.firstName = new SimpleStringProperty(firstName);
	  this.lastName = new SimpleStringProperty(lastName);
	  this.middleName = new SimpleStringProperty("J");
	  this.ssn = new SimpleLongProperty(999887777L);
	  this.birthday = new SimpleObjectProperty<LocalDate>(LocalDate.of(1997, 6, 20));
	  this.address = new SimpleStringProperty("some address");
	  this.sex = new SimpleStringProperty("M");
	  this.salary = new SimpleDoubleProperty(82500.95);
	  this.superSsn = new SimpleLongProperty(999887777L);
	  this.dno = new SimpleIntegerProperty(5);
	  this.email = new SimpleStringProperty("twang9@gmu.edu");
	  
	  
  }
    
    
    
	public StringProperty getFirstName() {
		return firstName;
	}
	public void setFirstName(StringProperty firstName) {
		this.firstName = firstName;
	}
	public StringProperty getMiddleName() {
		return middleName;
	}
	public void setMiddleName(StringProperty middleName) {
		this.middleName = middleName;
	}
	public StringProperty getLastName() {
		return lastName;
	}
	public void setLastName(StringProperty lastName) {
		this.lastName = lastName;
	}
	public LongProperty getSsn() {
		return ssn;
	}
	public void setSsn(LongProperty ssn) {
		this.ssn = ssn;
	}
	public ObjectProperty<LocalDate> getBirthday() {
		return birthday;
	}
	public void setBirthday(ObjectProperty<LocalDate> birthday) {
		this.birthday = birthday;
	}
	public StringProperty getAddress() {
		return address;
	}
	public void setAddress(StringProperty address) {
		this.address = address;
	}
	public StringProperty getSex() {
		return sex;
	}
	public void setSex(StringProperty sex) {
		this.sex = sex;
	}
	public DoubleProperty getSalary() {
		return salary;
	}
	public void setSalary(DoubleProperty salary) {
		this.salary = salary;
	}
	public LongProperty getSuperSsn() {
		return superSsn;
	}
	public void setSuperSsn(LongProperty superSsn) {
		this.superSsn = superSsn;
	}
	public IntegerProperty getDno() {
		return dno;
	}
	public void setDno(IntegerProperty dno) {
		this.dno = dno;
	}
	public StringProperty getEmail() {
		return email;
	}
	public void setEmail(StringProperty email) {
		this.email = email;
	}
    

    
}
