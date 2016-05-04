package videoconverter.UserServiceImpl;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

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
	   
	public static void createUser(String username,String firstName,String lastName,String password,String email){
		 Connection conn = null;
		   Statement stmt = null;
		   try{
		      //STEP 2: Register JDBC driver
		      Class.forName("com.mysql.jdbc.Driver");
		      
		    //STEP 3: Open a connection
		    
		      conn = (Connection) DriverManager.getConnection(DB_URL, USER, PASS);
		     
		      
		      //STEP 4: Execute a query
		      
		      stmt = (Statement) conn.createStatement();
		      
		      String sql = "INSERT INTO USER (username, firstName, lastName, password, email)" +
		    	        "VALUES (?, ?, ?, ?, ?)";
		      
		      PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
		      ps.setString(1, username);
		      ps.setString(2, firstName);
		      ps.setString(3, lastName);
		      ps.setString(4, password);
		      ps.setString(5, email);
		      
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
	 public static int check(String username, String password) throws ClassNotFoundException {
		 Connection conn = null;
		   Statement stmt = null;
		   int RESULT = 1;
	    //    String hashPass = org.apache.commons.codec.digest.DigestUtils.sha256Hex(password);
	       String hashPass= org.apache.commons.codec.digest.DigestUtils.sha256Hex(password);
	       
	        JSONObject error = new JSONObject();
	        JSONObject success = new JSONObject();
	        JSONArray response = new JSONArray();

	        if (username.trim().equals("") || password.trim().equals("")) {
	            if (username.trim().equals("")) {
	                JSONObject err = new JSONObject();
	                err.put("error", "error");
	                err.put("errorMessage", "Vnesete username");

	                response.put(err);
	                RESULT = 0;
	            }

	            if (password.trim().equals("")) {
	                JSONObject err = new JSONObject();
	                err.put("error", "errorPassword");
	                err.put("errorMessage", "Vnesete lozinka");
	                RESULT = 0;
	                response.put(err);
	            }
	        }
	        else {
	            try {
	            	 Class.forName("com.mysql.jdbc.Driver");
	   		      
	     		    //STEP 3: Open a connection
	     		    
	     		      conn = (Connection) DriverManager.getConnection(DB_URL, USER, PASS);
	     		     
	     		      
	     		      //STEP 4: Execute a query
	     		      
	     		      stmt = (Statement) conn.createStatement();
	     		      
	     		     String sql = "SELECT username, password FROM USER where username= '" + username + "'";
	     		      
	     		      PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);

	               
	                ResultSet rs = stmt.executeQuery(sql);

	                if (rs.next()) {
	                    String pass = rs.getString("password");
	                   String id = rs.getString("username");
	                  
	                    if (pass.equals(hashPass)) {
	                        // login
	                    	RESULT = 1;
	                        success.put("success", id);
	                        response.put(success);
	                    }
	                    else {
	                        // pogresen password
	                    	RESULT = 0;
	                        error.put("error", "errorPassword");
	                        error.put("errorMessage", "Pogresna lozinka");

	                        response.put(error);
	                    }
	                }
	                else {
	                    // ne postoi takov email
	                	RESULT = 0;
	                    error.put("error", "errorEmail");
	                    error.put("errorMessage", "Ne postoi takov email");

	                    response.put(error);
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }

	        return RESULT;
	    }
}
