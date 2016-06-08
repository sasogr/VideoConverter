package videoconverter.users;

/*
 * AUTHOR: Danail Tavcioski
 * v1.0b
 */

import java.io.File;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.io.FileUtils;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import videoconverter.dbconfig.Dbconfig;

public class RemoveUserFromDbHelper {
	static final Dbconfig dbconfig = new Dbconfig();
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = dbconfig.GetConnectionURL();

	static final String USER = dbconfig.GetUsernameDB();
	static final String PASS = dbconfig.GetPasswordDB();

	public static boolean removeUser(String username){
	
	 	   Connection conn = null;
		   Statement stmt = null;
		   boolean deleted = false;
		  
		   try{
			   Class.forName("com.mysql.jdbc.Driver");
		   		    
		      conn = (Connection) DriverManager.getConnection(DB_URL, USER, PASS);
		     
		    	      
		      stmt = (Statement) conn.createStatement();
		      
		      String sql = "DELETE FROM USER " +
		    	        "WHERE username = ?";
		      
		      PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
		      ps.setString(1, username);
		      
		      
		      ps.executeUpdate();
		      
		      sql = "DELETE FROM USER_VIDEOS " +
	    	        "WHERE username = ?";
		      
		      ps = (PreparedStatement) conn.prepareStatement(sql);
		      ps.setString(1, username);
		      
		      
		      ps.executeUpdate();
		      
		      FileUtils.deleteDirectory(new File("/home/videoconverter/"+username));
		      
		      deleted = true;
		      
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
		   
		 return deleted;
		  
	}

}
