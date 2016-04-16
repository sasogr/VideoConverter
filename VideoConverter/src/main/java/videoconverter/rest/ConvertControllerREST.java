package videoconverter.rest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import videoconverter.dbconfig.Dbconfig;

@Path("/convert")
public class ConvertControllerREST {
	@GET
	@Path("/getcommands")
	@Produces("application/json")
	public Response getCommands() throws JSONException {
		Dbconfig dbconfig = new Dbconfig();
		
		// Declare the JDBC objects.
        Connection con = null;
        ResultSet rs = null;
        JSONArray responseList = new JSONArray();
        
        try {
        	// Establish the connection.
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(dbconfig.GetConnectionURL(), dbconfig.GetUsernameDB(), dbconfig.GetPasswordDB());
            
            String SQL = 
            		"SELECT * " +
    				"FROM FFMPEG_GENERAL;";
            
            PreparedStatement preparedStatement = con.prepareStatement(SQL);
            rs = preparedStatement.executeQuery();
            
            if(rs.isBeforeFirst()) {
            	// Data returned.
            	ResultSetMetaData rsMetaData = rs.getMetaData();
                int columnCount = rsMetaData.getColumnCount();
                // Holds column names.
                List<String> columnNames = new ArrayList<String>();
                for(int i = 1; i <= columnCount; i++) {
                    columnNames.add(rsMetaData.getColumnName(i));
                }
                
                JSONObject jobj = new JSONObject();
                
                while(rs.next()) {
                	for(int i = 1; i <= columnCount; i++) {
                        String key = columnNames.get(i - 1);
                        String value = rs.getString(i);
                        jobj.put(key, value); 
                    }
                	
                	responseList.put(jobj);
                	jobj = new JSONObject();
                }
                
            }
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
        finally {
        	try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
        
        String response = "" + responseList;
        return Response.status(200).entity(response).build();
	}
	
	@POST
	@Path("/getoptions/{forCommand}")
	@Produces("application/json")
	public Response getOptions(@PathParam("forCommand") int _forCommand) throws JSONException {
		Dbconfig dbconfig = new Dbconfig();
		
		// Declare the JDBC objects.
        Connection con = null;
        ResultSet rs = null;
        JSONArray responseList = new JSONArray();
        
        try {
        	// Establish the connection.
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(dbconfig.GetConnectionURL(), dbconfig.GetUsernameDB(), dbconfig.GetPasswordDB());
            
            String SQL = 
            		"SELECT * " + 
    				"FROM FFMPEG_OPTIONS " +
					"WHERE optionTo = ?";
            
            PreparedStatement preparedStatement = con.prepareStatement(SQL);
            preparedStatement.setInt(1, _forCommand);
            
            rs = preparedStatement.executeQuery();
            
            if(rs.isBeforeFirst()) {
            	// Data returned.
            	ResultSetMetaData rsMetaData = rs.getMetaData();
                int columnCount = rsMetaData.getColumnCount();
                // Holds column names.
                List<String> columnNames = new ArrayList<String>();
                for(int i = 1; i <= columnCount; i++) {
                    columnNames.add(rsMetaData.getColumnName(i));
                }
                
                JSONObject jobj = new JSONObject();
                
                while(rs.next()) {
                	for(int i = 1; i <= columnCount; i++) {
                        String key = columnNames.get(i - 1);
                        String value = rs.getString(i);
                        jobj.put(key, value); 
                    }
                	
                	responseList.put(jobj);
                	jobj = new JSONObject();
                }
            }
            
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
        finally {
        	try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
		
        String response = "" + responseList;
        return Response.status(200).entity(response).build();
	}
}
