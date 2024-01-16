/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fbms.fbmsjframe;

/**
 *
 * @author Izee
 */
import java.sql.*;

class FBMSDataBase {

    private final String DRIVER;
    private final String DB_URL;
    private final String DB_USER;
    private final String DB_PASS;
    
    protected Connection conn;
    protected Statement stmt;

    
    public FBMSDataBase(){
    
        this.DRIVER = "com.mysql.cj.jdbc.Driver";
        this.DB_URL = "jdbc:mysql://localhost:3306/familybank";
        this.DB_USER = "root";
        this.DB_PASS = "admin";
 
        this.conn = null;
        this.stmt = null;

        try{
            
            Class.forName(DRIVER);
        
        }
        catch(ClassNotFoundException e){
        
            System.out.println("Unable to load driver");
            
       
        }

        try{
        
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            stmt = conn.createStatement();
        
        }
        catch(SQLException e){
        
            System.out.println("Unable to connect to database");
            
        
        }
        
        
    }
    
    
    
}

