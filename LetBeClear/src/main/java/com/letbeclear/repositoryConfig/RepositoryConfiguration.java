package com.letbeclear.repositoryConfig;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.EntityManagerFactoryInfo;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.letbeclear.mail.ForgotPasswordEmailSender;
import com.letbeclear.repository.PostCommentLikeRepository;
import com.letbeclear.utils.LoginDetailsValidator;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
@EnableJpaRepositories(basePackages= {"com.letbeclear.repository"})
public class RepositoryConfiguration 
{
	@Autowired
	Environment environment;
	
	@Autowired
	DataBaseConfiguration databaseConfiguration;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	public String HIBERNATE_HBM2DDl_AUTO;
	
	@Value("${spring.jpa.properties.hibernate.dialect}")
	public String HIBERNATE_DIALECT;
	
	@Value("${spring.jpa.show-sql}")
	public String HIBERNATE_SHOW_SQL;
	
	//Bean for Setting DataSource Object
	@Bean DataSource dataSource()
	{
		System.out.println("Inside RepositoryConfiguration dataSource()");
		DriverManagerDataSource dataSource= new DriverManagerDataSource();
		//dataSource.setDriverClassName(databaseConfiguration.driver);
		dataSource.setUrl(databaseConfiguration.url);
		dataSource.setUsername(databaseConfiguration.username);
		dataSource.setPassword(databaseConfiguration.password);
		
		return dataSource;
	}
	
	@Bean BCryptPasswordEncoder bCryptPasswordEncoder()
	{
		System.out.println("Inside RepositoryConfiguration bCryptPasswordEncoder()");
		BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
		
		return encoder;
	}
	
	@Bean ForgotPasswordEmailSender forgotPasswordEmailSender()
	{
		System.out.println("Inside RepositoryConfiguration forgotPasswordEmailSender()");
		return new ForgotPasswordEmailSender();
	}
	
//	@Bean LoginDetailsValidator loginDetailsValidator()
//	{
//		System.out.println("Inside RepositoryConfiguration loginDetailsValidator()");
//		return new LoginDetailsValidator();
//	}
	
	@Bean 
	public LocalContainerEntityManagerFactoryBean entityManagerFactory()
	{
		/**
		 * {@link org.springframework.beans.factory.FactoryBean} that creates a JPA
		 * {@link javax.persistence.EntityManagerFactory} according to JPA's standard
		 * <i>container</i> bootstrap contract. This is the most powerful way to set
		 * up a shared JPA EntityManagerFactory in a Spring application context;
		 * the EntityManagerFactory can then be passed to JPA-based DAOs via
		 * dependency injection. Note that switching to a JNDI lookup or to a
		 * {@link LocalEntityManagerFactoryBean} definition is just a matter of
		 * configuration!
		 *
		 * <p>As with {@link LocalEntityManagerFactoryBean}, configuration settings
		 * are usually read in from a {@code META-INF/persistence.xml} config file,
		 * residing in the class path, according to the general JPA configuration contract.
		 * However, this FactoryBean is more flexible in that you can override the location
		 * of the {@code persistence.xml} file, specify the JDBC DataSources to link to,
		 * etc. Furthermore, it allows for pluggable class instrumentation through Spring's
		 * {@link org.springframework.instrument.classloading.LoadTimeWeaver} abstraction,
		 * instead of being tied to a special VM agent specified on JVM startup.
		 *
		 * <p>Internally, this FactoryBean parses the {@code persistence.xml} file
		 * itself and creates a corresponding {@link javax.persistence.spi.PersistenceUnitInfo}
		 * object (with further configuration merged in, such as JDBC DataSources and the
		 * Spring LoadTimeWeaver), to be passed to the chosen JPA
		 * {@link javax.persistence.spi.PersistenceProvider}. This corresponds to a
		 * local JPA container with full support for the standard JPA container contract.
		 *
		 * <p>The exposed EntityManagerFactory object will implement all the interfaces of
		 * the underlying native EntityManagerFactory returned by the PersistenceProvider,
		 * plus the {@link EntityManagerFactoryInfo} interface which exposes additional
		 * metadata as assembled by this FactoryBean.
		 *
		 * <p><b>NOTE: Spring's JPA support requires JPA 2.0 or higher, as of Spring 4.0.</b>
		 * JPA 1.0 based applications are still supported; however, a JPA 2.0/2.1 compliant
		 * persistence provider is needed at runtime. Spring's persistence unit bootstrapping
		 * automatically detects JPA 2.0 vs 2.1 through checking the JPA API on the classpath.
		*/
		 
		
		System.out.println("Inside RepositoryConfiguration entityManagerFactory()");
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();//Used to set various features of EntityManagerFactory
		em.setDataSource(dataSource());	//Setting DataSource Object
		em.setPackagesToScan("com.letbeclear.model");	//Setting base package to scan same as Spring's component-scan
		em.setPersistenceProviderClass(HibernatePersistenceProvider.class);	//Set the PersistenceProvider implementation class 
																			//to use for creating the EntityManagerFactory
		em.setJpaProperties(additionalProperties());	//Specify JPA properties, to be passed into
														//{@code Persistence.createEntityManagerFactory} (if any).
		
		return em;
	}
	
	Properties additionalProperties()
	{
		System.out.println("Inside RepositoryConfiguration additionalProperties()");
		Properties properties=new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", databaseConfiguration.HIBERNATE_HBM2DDl_AUTO);
		properties.setProperty("hibernate.dialect", databaseConfiguration.HIBERNATE_DIALECT);
		
		return properties;
	}
}
