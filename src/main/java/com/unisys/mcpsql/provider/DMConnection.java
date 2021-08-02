package com.unisys.mcpsql.provider;

import com.unisys.mcpsql.provider.mcpconnection.MCPConnection;
import com.unisys.mcpsql.provider.mcpconnection.MCPConnectionFactory;
import com.unisys.mcpsql.provider.util.PropertyProvider;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


















public class DMConnection
{
  static Logger logger = LoggerFactory.getLogger(DMConnection.class);
  
  private boolean autoCommit = true;
  
  boolean readOnly = false;
  
  private int isolationLevel = 0;

  
  private String ccsName = "ASERIESEBCDIC";




  int workerMixNum=0;
  
  public int getMixNum() {
	  return this.workerMixNum;
  }






  
  public int getProviderMajorRelease() { return 53; }






  
  public int getProviderMinorRelease() { return 0; }

  
  private int workerMajorRelease = -1;



  
  public int getWorkerMajorRelease() { return this.workerMajorRelease; }

  
  private int workerMinorRelease = -1;



  
  public int getWorkerMinorRelease() { return this.workerMinorRelease; }

  
  private int workerPatchRelease = -1;



  
  public int getWorkerPatchRelease() { return this.workerPatchRelease; }

  
  private int workerProtocolVersion = -1;



  
  public int getWorkerProtocolVersion() { return this.workerProtocolVersion; }

  
  private String MCPUserID = null;


  
  public String getMCPUserID() { return this.MCPUserID; }

  
  private sendBuffer sendBuf = new sendBuffer();
  private receiveBuffer receiveBuf = new receiveBuffer();





  
  public String getCCSName() { return this.ccsName; }

  
  private String hostCCSName = "ASERIESEBCDIC";





  
  public String getHostCCSName() { return this.hostCCSName; }



  
  public String getDatabasename() { return this.databasename; }








  
  public void setCCSName(String ccsName) throws Exception {
    this.mcpInterface.initializeCCS(ccsName);
    logger.debug("setCCSName: {} ", ccsName);
    this.ccsName = ccsName;
  }







  
  public void setHostCCSName(String ccsName) throws Exception {
    this.mcpInterface.initializeHostCCS(ccsName);
    logger.debug("setHostCCSName: {}", ccsName);
    this.hostCCSName = ccsName;
  }







  
  public ArrayList retrieveAndClearWarnings() { return this.mcpInterface.retrieveAndClearWarnings(); }



  
  private MCPInterface mcpInterface = new MCPInterface(this);
  private MCPConnection mcpConnection = (new MCPConnectionFactory()).createConnection();
  public DMConnection() throws Exception {
    if (logger.isTraceEnabled()) {
      
      try {
        
        String myJarName = "provider.jar";
        String myClassPath = System.getProperty("java.class.path");
        StringTokenizer st = new StringTokenizer(myClassPath, ";");
        boolean foundJar = false;
        while (st.hasMoreTokens() && !foundJar) {
          
          String theToken = st.nextToken();
          if (theToken.indexOf("provider.jar") != -1) {
            
            logger.trace("Jar File {}", theToken);
            foundJar = true;
            myJarName = theToken;
          } 
        } 
        JarFile jarfile = new JarFile(myJarName);
        Manifest manifest = jarfile.getManifest();
        Attributes attrs = manifest.getMainAttributes();
        for (Iterator it = attrs.keySet().iterator(); it.hasNext(); )
        {
          Attributes.Name attrName = (Attributes.Name)it.next();
          String attrValue = attrs.getValue(attrName);
          logger.trace("MANIFEST {}: {}", attrName, attrValue);
        }
      
      } catch (Exception e) {}
    }
  }






  
  boolean qdump;





  
  boolean qgraph;




  
  protected String username;




  
  private String databasename;




  
  static final int aProtocolVersion1 = 1;




  
  static final int providerCurrentProtocol = 1;




  
  private static final int providerMajorRelease = 53;




  
  private static final int providerMinorRelease = 0;




  
  public static final int SQL_TXN_READ_UNCOMMITTED = 1;




  
  public static final int SQL_TXN_READ_COMMITTED = 2;




  
  public static final int SQL_TXN_REPEATABLE_READ = 4;




  
  public static final int SQL_TXN_SERIALIZABLE = 8;





  
  public MCPInterface getMCPInterface() { return this.mcpInterface; }




























  
  MCPConnection getMCPConnection() { return this.mcpConnection; }











  
  public void connect(String serverName, int serverPort) throws Exception { this.mcpConnection.connect(serverName, serverPort); }









  
  public void connect(DMConnectParams params) throws Exception { connect(params.hostName, Integer.parseInt(params.portNumber)); }








  
  public boolean isConnected() { return this.mcpConnection.isConnected(); }






  
  public void closeSocketconnection() throws Exception { this.mcpConnection.close(); }





  
  public void open(String usercode, String password, String databaseName) throws IOException, DMProviderException { open(false, 0, 0, null, usercode, password, databaseName); }





  
  public void open(int AuthType, int encryptType, String serviceName, String usercode, String password, String databaseName) throws IOException, DMProviderException { open(false, AuthType, encryptType, serviceName, usercode, password, databaseName); }






















  
  public void open(boolean readOnly, int AuthType, int encryptType, String serviceName, String usercode, String password, String databaseName) throws IOException, DMProviderException {
    this.mcpInterface.setencrypt(0);
    if (AuthType == 1) {
      
      if (serviceName == null || serviceName == "")
        serviceName = PropertyProvider.INSTANCE.getProperty("servicename", ""); 
      String jaasLoginConf = PropertyProvider.INSTANCE.getProperty("jaasloginconf", "unisys.mcpsql.provider");
      this.mcpInterface.initkerberos(serviceName, jaasLoginConf);
    } 
    boolean done = false;
    byte[] buf = null;
    byte[] outToken = null;
    
    this.username = usercode;
    this.databasename = databaseName;
    
    while (!done) {
      
      this.mcpInterface.start(this.sendBuf, 24);
      this.mcpInterface.encodeByte(this.sendBuf, 1, 1);
      this.mcpInterface.encodeByte(this.sendBuf, 6, AuthType);
      this.mcpInterface.encodeByte(this.sendBuf, 7, 1);
      this.mcpInterface.encodeByte(this.sendBuf, 8, encryptType);
      this.mcpInterface.encodeByte(this.sendBuf, 9, readOnly ? 1 : 0);
      
      this.mcpInterface.encodeByte(this.sendBuf, 10, 53);
      this.mcpInterface.encodeByte(this.sendBuf, 11, 0);
      
      logger.debug("providerMajorRelease: {},  providerMinorRelease: {}", Integer.valueOf(53), Integer.valueOf(0));
      logger.debug("providerCurrentProtocol: {}", Integer.valueOf(1));
      this.mcpInterface.encodeString(this.sendBuf, 16, databaseName);
      
      if (AuthType == 1) {
        
        outToken = this.mcpInterface.getCurrentticketToken();
        this.mcpInterface.encodeTicket(this.sendBuf, 12, outToken);
      }
      else {
        
        password = Cloak.PasswordCloak1(password, Cloak.PasswordKey(usercode.getBytes()));
        this.mcpInterface.encodeString(this.sendBuf, 12, usercode);
        this.mcpInterface.encodeString(this.sendBuf, 14, password);
        done = true;
      } 
      
      int len = 0;
      
      try {
        len = this.mcpInterface.send(this.sendBuf, this.receiveBuf, true);
      }
      catch (DMProviderException e) {
        
        if (e.getSqlState() != "S0101") {
          
          buf = this.receiveBuf.getReceiveBuf();
          workerMixNum = this.mcpInterface.decodeShort(buf, 36);
          logger.debug("MCPSQL WORKER mix number {}", Integer.valueOf(workerMixNum));
        } 
        try {
        closeSocketconnection();
        } catch (Exception e1 ) {
        	System.out.println("Error:"+e1);
        }
        throw e;
      } 
      this.readOnly = readOnly;
      if (len == 0)
      {
        
        throw new DMProviderException(DMError.zeroLengthResponse);
      }
      
      buf = this.receiveBuf.getReceiveBuf();
      if (!done) {
        
        byte[] inToken = this.mcpInterface.decodeTicket(buf, 26, 0);
        outToken = this.mcpInterface.getNextticketToken(inToken);
        if (this.mcpInterface.isEstablished()) {
          
          done = true;
          this.MCPUserID = this.mcpInterface.decodeString(buf, 36); continue;
        } 
        if (outToken == null)
        {
          done = true;
        }
      } 
    } 
    
    workerMixNum = this.mcpInterface.decodeShort(buf, 36);
    logger.debug("MCPSQL WORKER mix number {}", Integer.valueOf(workerMixNum));
    
    this.workerMajorRelease = this.mcpInterface.decodeByte(buf, 38);
    if (this.workerMajorRelease == 0)
    {
      this.workerMajorRelease = 53;
    }
    this.workerMinorRelease = this.mcpInterface.decodeByte(buf, 39);
    this.workerPatchRelease = this.mcpInterface.decodeByte(buf, 40);
    this.workerProtocolVersion = this.mcpInterface.decodeByte(buf, 1);
    logger.debug("workerMajorRelease: {},  workerMinorRelease: {}", Integer.valueOf(this.workerMajorRelease), Integer.valueOf(this.workerMinorRelease));
    logger.debug("workerPatchRelease: {}", Integer.valueOf(this.workerPatchRelease));
    logger.debug("workerProtocolVersion: {}", Integer.valueOf(this.workerProtocolVersion));
    
    if (1 != this.workerProtocolVersion) {
      
      logger.error("Provider/Worker protocol version mismatch: {}/{}", Integer.valueOf(1), 
          Integer.valueOf(this.workerProtocolVersion));
      
      throw new DMProviderException("Provider/Worker protocol version mismatch: 1/" + this.workerProtocolVersion);
    } 

    
    int ccsNum = this.mcpInterface.decodeShort(buf, 28);
    String ccsName = this.mcpInterface.decodeString(buf, 30);
    if (ccsNum != 0) {
      
      try {
        
        this.mcpInterface.initializeCCS(ccsName);
        this.ccsName = ccsName;
        if (ccsNum != 4)
        {
          logger.debug("coded character set {}, num {}", ccsName, Integer.valueOf(ccsNum));
        }
      }
      catch (Exception e) {
        
        throw new DMProviderException(e);
      } 
    }
    logger.debug("ccsNum {}, ccsName {}", Integer.valueOf(ccsNum), ccsName);
    
    ccsNum = this.mcpInterface.decodeShort(buf, 32);
    ccsName = this.mcpInterface.decodeString(buf, 34);
    if (ccsNum != 0 && !this.hostCCSName.equalsIgnoreCase(ccsName)) {
      
      try {
        
        this.mcpInterface.initializeHostCCS(ccsName);
        this.hostCCSName = ccsName;
        logger.debug("coded character set {}, num {}", ccsName, Integer.valueOf(ccsNum));
      }
      catch (Exception e) {
        
        throw new DMProviderException(e);
      } 
    }
    logger.debug("coded character set {}, num {}", ccsName, Integer.valueOf(ccsNum));
    
    this.mcpInterface.setencrypt(encryptType);
  }









  
  public void open(DMConnectParams params) throws Exception { open(params.readOnly, params.authType, params.encryptType, params.serviceName, params.usercode, params.password, params.databaseName); }









  
  public void setAutoCommit(boolean setIt) throws IOException, DMProviderException {
    if (setIt == this.autoCommit)
      return; 
    logger.debug("setAutoCommit {}", Boolean.valueOf(setIt));
    
    this.autoCommit = setIt;
    
    this.mcpInterface.start(this.sendBuf, 24);
    this.mcpInterface.encodeShort(this.sendBuf, 6, 6);
    this.mcpInterface.encodeByte(this.sendBuf, 16, 102);
    this.mcpInterface.encodeByte(this.sendBuf, 17, setIt ? 1 : 0);
    this.mcpInterface.send(this.sendBuf, this.receiveBuf, true);
  }


  
  public boolean getReadOnly() { return this.readOnly; }









  
  public boolean getAutoCommit() { return this.autoCommit; }













  
  public void setIsolationLevel(int level) throws IOException, DMProviderException {
    if (level == this.isolationLevel)
      return; 
    logger.debug("setIsolationLevel: {}", Integer.valueOf(level));
    
    this.mcpInterface.start(this.sendBuf, 24);
    this.mcpInterface.encodeShort(this.sendBuf, 6, 6);
    this.mcpInterface.encodeByte(this.sendBuf, 16, 108);
    this.mcpInterface.encodeByte(this.sendBuf, 17, level);
    this.mcpInterface.send(this.sendBuf, this.receiveBuf, true);
    this.isolationLevel = level;
  }










  
  public int getIsolationLevel() {
    logger.debug("getIsolationLevel: {}", Integer.valueOf(this.isolationLevel));
    int level = this.isolationLevel;
    if (level == 0)
    {
      level = 2;
    }
    return level;
  }







  
  public void transact(boolean commit) throws IOException, DMProviderException {
    if (this.autoCommit)
    {
      throw new DMProviderException(DMError.transactAutoCommitMode);
    }
    logger.debug("transact: {}", Boolean.valueOf(commit));
    boolean noresponse = false;
    this.mcpInterface.start(this.sendBuf, 24);
    this.mcpInterface.encodeShort(this.sendBuf, 6, 7);
    this.mcpInterface.encodeByte(this.sendBuf, 16, commit ? 0 : 1);
    
    this.mcpInterface.send(this.sendBuf, this.receiveBuf, !noresponse);
  }











  
  public void savepoint(byte number) throws IOException, DMProviderException {
    if (this.autoCommit)
    {
      throw new DMProviderException(DMError.savepointAutoCommitMode);
    }
    logger.debug("savepoint: ", Byte.valueOf(number));
    this.mcpInterface.start(this.sendBuf, 24);
    this.mcpInterface.encodeShort(this.sendBuf, 6, 22);
    this.mcpInterface.encodeByte(this.sendBuf, 16, number);
    this.mcpInterface.send(this.sendBuf, this.receiveBuf, true);
  }







  
  public void rollbackToSavepoint(byte number) throws IOException, DMProviderException {
    if (this.autoCommit)
    {
      throw new DMProviderException(DMError.rollbackAutoCommitMode);
    }
    logger.debug("rollbackToSavepoint: {}", Byte.valueOf(number));
    this.mcpInterface.start(this.sendBuf, 24);
    this.mcpInterface.encodeShort(this.sendBuf, 6, 23);
    this.mcpInterface.encodeByte(this.sendBuf, 16, number);
    this.mcpInterface.send(this.sendBuf, this.receiveBuf, true);
  }




  
  public void close() throws Exception {
    this.mcpInterface.start(this.sendBuf, 24);
    this.mcpInterface.encodeShort(this.sendBuf, 6, 10);
    this.mcpInterface.send(this.sendBuf, this.receiveBuf, true);
    this.mcpConnection.close();
  }







  
  public DMStatement createStatement() { return new DMStatement(this); }








  
  public DMCatStatement createCatStatement() { return new DMCatStatement(this); }








  
  public DMRDCStatement createRDCStatement() { return new DMRDCStatement(this); }









  
  public void qDumpWanted(boolean yes) throws IOException, DMProviderException { this.qdump = yes; }








  
  public boolean getQDumpWanted() { return this.qdump; }








  
  public void qdController(String msg) throws Exception {
    logger.debug("qdController: {}", msg);
    this.mcpInterface.start(this.sendBuf, 24);
    this.mcpInterface.encodeShort(this.sendBuf, 6, 11);
    this.mcpInterface.encodeByte(this.sendBuf, 16, 4);
    this.mcpInterface.encodeString(this.sendBuf, 12, msg);
    int len = this.mcpInterface.send(this.sendBuf, this.receiveBuf, true);
  }







  
  public void qGraphWanted(boolean yes) throws IOException, DMProviderException {
    if (yes == this.qgraph) {
      return;
    }
    
    logger.debug("qGraphWanted: {}", Boolean.valueOf(yes));
    this.mcpInterface.start(this.sendBuf, 24);
    this.mcpInterface.encodeShort(this.sendBuf, 6, 11);
    this.mcpInterface.encodeByte(this.sendBuf, 16, yes ? 1 : 2);
    
    int len = this.mcpInterface.send(this.sendBuf, this.receiveBuf, true);
    this.qgraph = yes;
  }







  
  public boolean getQGraphWanted() { return this.qgraph; }



  
  public String getUserName() { return this.username; }
}
