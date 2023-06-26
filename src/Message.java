
//add the class template   Gang Yang for the practice. Success!

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Date;


public class Message implements Serializable {
	
	private Integer id; 
	private int opType;
	private String lastName, firstName;
	private char mi;
	private String address, city, state; 	
	private BigInteger mPhoneNo, hPhoneNo;
	private String mPhoneCarrier, hPhoneCarrier;
	
	
	public Message(int opType, Integer id, String lastName, String firstName, char mi, String address, String city, String state,
			BigInteger mPhoneNo, BigInteger hPhoneNo, String mPhoneCarrier, String hPhoneCarrier) {
		
		this.opType = opType;
		this.id = id;
		this.lastName = lastName;
		this.firstName = firstName;
		this.mi = mi;
		this.address = address;
		this.city = city;
		this.state = state;
		this.mPhoneNo = mPhoneNo;
		this.hPhoneNo = hPhoneNo;
		this.mPhoneCarrier = mPhoneCarrier;
		this.hPhoneCarrier = hPhoneCarrier;
	}


	// provide the getter and setter methods
	
	public int getOpType() {
		return opType;
	}


	public void setOpType(int opType) {
		this.opType = opType;
	}

	public Integer getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstFName) {
		this.firstName = firstName;
	}


	public char getMi() {
		return mi;
	}


	public void setMi(char mi) {
		this.mi = mi;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public BigInteger getmPhoneNo() {
		return mPhoneNo;
	}


	public void setmPhoneNo(BigInteger mPhoneNo) {
		this.mPhoneNo = mPhoneNo;
	}


	public BigInteger gethPhoneNo() {
		return hPhoneNo;
	}


	public void sethPhoneNo(BigInteger hPhoneNo) {
		this.hPhoneNo = hPhoneNo;
	}


	public String getmPhoneCarrier() {
		return mPhoneCarrier;
	}


	public void setmPhoneCarrier(String mPhoneCarrier) {
		this.mPhoneCarrier = mPhoneCarrier;
	}


	public String gethPhoneCarrier() {
		return hPhoneCarrier;
	}


	public void sethPhoneCarrier(String hPhoneCarrier) {
		this.hPhoneCarrier = hPhoneCarrier;
	}
	
	
		
}
