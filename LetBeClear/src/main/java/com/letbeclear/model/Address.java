package com.letbeclear.model;
// Generated Feb 5, 2018 1:20:43 PM by Hibernate Tools 5.1.0.Alpha1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * Address generated by hbm2java
 */
@Entity
@Table(name = "address", catalog = "letbeclear", uniqueConstraints = @UniqueConstraint(columnNames = "EMAIL"))
public class Address implements java.io.Serializable {

	private Integer addressId;
	private Users users;
	private String firstname;
	private String lastname;
	private String addressType;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String country;
	private String zipcode;
	private String email;
	private String mobilephone;
	private Integer field1;
	private String field2;
	private Date field3;
	private Date lastcreate;

	public Address() {
	}

	public Address(Users users, String email, Date field3, Date lastcreate) {
		this.users = users;
		this.email = email;
		this.field3 = field3;
		this.lastcreate = lastcreate;
	}

	public Address(Users users, String firstname, String lastname, String addressType, String address1, String address2,
			String city, String state, String country, String zipcode, String email, String mobilephone, Integer field1,
			String field2, Date field3, Date lastcreate) {
		this.users = users;
		this.firstname = firstname;
		this.lastname = lastname;
		this.addressType = addressType;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.state = state;
		this.country = country;
		this.zipcode = zipcode;
		this.email = email;
		this.mobilephone = mobilephone;
		this.field1 = field1;
		this.field2 = field2;
		this.field3 = field3;
		this.lastcreate = lastcreate;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ADDRESS_ID", unique = true, nullable = false)
	public Integer getAddressId() {
		return this.addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", nullable = false)
	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	@Column(name = "FIRSTNAME", length = 256)
	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	@Column(name = "LASTNAME", length = 256)
	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@Column(name = "ADDRESS_TYPE", length = 50)
	public String getAddressType() {
		return this.addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	@Column(name = "ADDRESS1", length = 512)
	public String getAddress1() {
		return this.address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	@Column(name = "ADDRESS2", length = 512)
	public String getAddress2() {
		return this.address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	@Column(name = "CITY", length = 256)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "STATE", length = 256)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "COUNTRY", length = 256)
	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Column(name = "ZIPCODE", length = 128)
	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	@Column(name = "EMAIL", unique = true, nullable = false, length = 128)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "MOBILEPHONE", length = 128)
	public String getMobilephone() {
		return this.mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	@Column(name = "FIELD1")
	public Integer getField1() {
		return this.field1;
	}

	public void setField1(Integer field1) {
		this.field1 = field1;
	}

	@Column(name = "FIELD2", length = 256)
	public String getField2() {
		return this.field2;
	}

	public void setField2(String field2) {
		this.field2 = field2;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FIELD3", nullable = false, length = 19)
	public Date getField3() {
		return this.field3;
	}

	public void setField3(Date field3) {
		this.field3 = field3;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LASTCREATE", nullable = false, length = 19)
	public Date getLastcreate() {
		return this.lastcreate;
	}

	public void setLastcreate(Date lastcreate) {
		this.lastcreate = lastcreate;
	}

}
