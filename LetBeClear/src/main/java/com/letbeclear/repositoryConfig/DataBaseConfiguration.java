package com.letbeclear.repositoryConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="spring.datasource")
public class DataBaseConfiguration 
{
	//String driver;
	String url;
	String username;
	String password;
//	public String getDriver() {
//		return driver;
//	}
//	public void setDriver(String driver) {
//		this.driver = driver;
//	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	public String HIBERNATE_HBM2DDl_AUTO;
	
	@Value("${spring.jpa.properties.hibernate.dialect}")
	public String HIBERNATE_DIALECT;
	
	@Value("${spring.jpa.show-sql}")
	public String HIBERNATE_SHOW_SQL;
	
}
