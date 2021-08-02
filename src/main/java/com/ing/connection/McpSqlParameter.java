package com.ing.connection;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import com.unisys.mcpsql.provider.DMConnectParams;

public class McpSqlParameter {
	
	static String usercode="600620360";
	static String password="SWINFOR01";
	static String portNumber="2012";
	static String hostname="uismcp01";	
	
	public static DMConnectParams getParameter(String databaseName) {
		
		Properties prop = new Properties();

		try {
        // load a properties file
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream("mcpsql.properties");			
        prop.load(input);
		
		DMConnectParams p = new DMConnectParams();
	    p.hostName = prop.getProperty("mainframe");
	    p.portNumber = prop.getProperty("port");//"2012";
	    p.usercode = prop.getProperty("usercode");//"DBAMDI";
	    p.password = prop.getProperty("password");//"DBAMDI";
	    if ( databaseName != null ) {
	    	p.databaseName = databaseName;
	    }
	    p.encryptType = Integer.parseInt(prop.getProperty("encrypttype"));//0;
	    p.authType = Integer.parseInt(prop.getProperty("authtype"));//0;
	    p.serviceName = prop.getProperty("mainframe");//hostname;
	    if ( prop.getProperty("readonly").equals("false") ) {
		    p.readOnly = false;
	    } else {
		    p.readOnly = true;
	    }
		
	    return p;

		} catch (Exception e) {
			System.out.println("Error in getParameter:"+e);
			return null;
		}
	}

	public static DMConnectParams getParameter(String databaseName, String hostname) {
		
		Properties prop = new Properties();

		try {
        // load a properties file
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream input = null;
		
		try {
			if ( System.getProperty("configDir") != null ) {
				input = new FileInputStream(System.getProperty("configDir")+"/mcpsql.properties");
			} else {
				input = classLoader.getResourceAsStream("mcpsql.properties");
			}
		} catch (Exception e1) {
			System.out.println(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime())+": Error in getParameter:"+e1);
			input = classLoader.getResourceAsStream("mcpsql.properties");
		}

        prop.load(input);
		
		DMConnectParams p = new DMConnectParams();
	    p.hostName = hostname;
	    p.portNumber = prop.getProperty("port");//"2012";
	    p.usercode = prop.getProperty("usercode");//"DBAMDI";

	    if ( prop.getProperty("password").length() > 14 ) {
    		p.password = decrypt(prop.getProperty("usercode"),prop.getProperty("password"));
    	} else {
    	    p.password = prop.getProperty("password");//"DBAMDI";
    	}

	    if ( databaseName != null ) {
	    	p.databaseName = databaseName;
	    }
	    p.encryptType = Integer.parseInt(prop.getProperty("encrypttype"));//0;
	    p.authType = Integer.parseInt(prop.getProperty("authtype"));//0;
	    p.serviceName = prop.getProperty("mainframe");//hostname;
	    if ( prop.getProperty("readonly").equals("false") ) {
		    p.readOnly = false;
	    } else {
		    p.readOnly = true;
	    }
		
	    return p;

		} catch (Exception e) {
			System.out.println("Error in getParameter:"+e);
			return null;
		}
	}

	public static DMConnectParams getParameter(String usercode, String password, String hostname, String databaseName) {
		
		DMConnectParams p = new DMConnectParams();
	    p.hostName = hostname;
	    p.portNumber = "2012";
	    p.usercode = usercode;
	    p.password = password;
	    if ( databaseName != null ) {
	    	p.databaseName = databaseName;
	    }
	    p.encryptType = 0;
	    p.authType = 0;
	    //p.serviceName = "h05";
	    p.readOnly = false;
		
	    return p;
	}

	private static String decrypt(String login, String password) throws Exception {
		  
	  StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
	  encryptor.setPassword(login);
	  String ret=encryptor.decrypt(password);
	  return ret;
	  
	}

}
