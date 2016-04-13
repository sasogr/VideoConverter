package videoconverter.UserServiceImpl;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import videoconverter.dbconfig.Dbconfig;

public  class UserServiceImpl {
	
	 // JDBC driver name and database URL
	static final Dbconfig dbconfig = new Dbconfig();
	
	
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = dbconfig.GetConnectionURL();

	   //  Database credentials
	   static final String USER = dbconfig.GetUsernameDB();
	   static final String PASS = dbconfig.GetPasswordDB();
	   
	public static void createUser(String username,String firstName,String lastName,String password,String birthdate,String email){
		 Connection conn = null;
		   Statement stmt = null;
		   try{
		      //STEP 2: Register JDBC driver
		      Class.forName("com.mysql.jdbc.Driver");
		      
		    //STEP 3: Open a connection
		    
		      conn = (Connection) DriverManager.getConnection(DB_URL, USER, PASS);
		     
		      
		      //STEP 4: Execute a query
		      
		      stmt = (Statement) conn.createStatement();
		      
		      String sql = "INSERT INTO user (username, firstName, lastName,password,birthdate,email)" +
		    	        "VALUES (?, ?, ?,?,?,?)";
		      
		      PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
		      ps.setString(1, username);
		      ps.setString(2, firstName);
		      ps.setString(3, lastName);
		      ps.setString(4, password);
		      ps.setString(5, birthdate);
		      ps.setString(6, email);
		      
		      ps.executeUpdate();
		   }catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		   }catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }finally{
		      //finally block used to close resources
		      try{
		         if(stmt!=null)
		            conn.close();
		      }catch(SQLException se){
		      }// do nothing
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		   }//end try
		  
	}
}
