package com.ing.connection;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class JpaUtil {

	private static EntityManagerFactory factory;

	static {
				
		Map<String, String> persistenceMap = new HashMap<String, String>();
//		if ( System.getProperty("DBURL") != null ) {
//			if ( System.getProperty("DBURL").equals("PROD") ) {
//				persistenceMap.put("javax.persistence.jdbc.url", "jdbc:sqlserver://se8d10000361;DatabaseName=sqlutil");
//			} else if ( System.getProperty("DBURL").equals("TEST") ) {
//				persistenceMap.put("javax.persistence.jdbc.url", "jdbc:sqlserver://se8d10000361;DatabaseName=sqlutilTst");
//			}
//		} else {
//			persistenceMap.put("javax.persistence.jdbc.url", "jdbc:sqlserver://localhost;DatabaseName=sqlutil");			
//		}

		if ( System.getProperty("configDir") != null  ) {
			System.out.println("persistence="+System.getProperty("configDir")+"/persistence.properties");
//			if ( new File(System.getProperty("configDir")+"/persistence.properties").getAbsoluteFile().exists() ) {
	
			try { 
				System.out.println("Using persistence.properties from "+System.getProperty("configDir"));
				
				InputStream input = new FileInputStream(System.getProperty("configDir")+"/persistence.properties");

	            Properties prop = new Properties();
	            prop.load(input);
				
	            if ( prop.getProperty("jdbc.url") != null ) {
					persistenceMap.put("javax.persistence.jdbc.url", prop.getProperty("jdbc.url"));
					System.out.println("    url="+prop.getProperty("jdbc.url"));
				} 
	            if ( prop.getProperty("user") != null ) {
					persistenceMap.put("javax.persistence.jdbc.user", prop.getProperty("user"));
				}
	            if ( prop.getProperty("password") != null ) {
	            	if ( prop.getProperty("password").length() > 14 ) {
	            		persistenceMap.put("javax.persistence.jdbc.password", decrypt(prop.getProperty("user"),prop.getProperty("password")));
	            	} else {
	            		persistenceMap.put("javax.persistence.jdbc.password", prop.getProperty("password"));
	            	}
					
				}
	            if ( prop.getProperty("show_sql") != null ) {
					persistenceMap.put("hibernate.show_sql",  prop.getProperty("show_sql"));
				}
	            if ( prop.getProperty("SQL") != null ) {
					persistenceMap.put("hibernate.SQL", prop.getProperty("SQL"));
				}
			} catch ( Exception e) {
				System.out.println("File not found:"+e);
			}
		}

		
		factory = Persistence.createEntityManagerFactory("IngSql", persistenceMap);
	}

	public static EntityManager getEntityManager() {
		return factory.createEntityManager();
	}

	private static String decrypt(String login, String password) throws Exception {
		  
	  StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
	  encryptor.setPassword(login);
	  String ret=encryptor.decrypt(password);
	  return ret;
	  
	}

}