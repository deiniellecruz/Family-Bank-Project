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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

class FamilyMember extends FamilyBank{


  
  // FamilyBank fm = new FamilyMember()
  // Creating member / personal account
  public FamilyMember(String name, String un, String pass){
    
    //java.sql.SQLIntegrityConstraintViolationException
    super.name = name;  
    super.username = un;
    super.password = pass; 
  
      try {

          this.signup(un, pass);

      } catch (SQLException ex) {
          
        JOptionPane.showMessageDialog(null, "Username already taken");
      
      }
         
  }

   public FamilyMember(){
      super();
  }


  // This method is for creating the account
  // Sign up method is implemented 
  public void signup(String username, String password) throws SQLException { 
    
    // Access the Member Table
    String select = "SELECT username FROM Member";
    ResultSet rs = stmt.executeQuery(select);
    
    // Iterating through the data table to check if the the username is already taken 
    // If none, the data inputed by the user will be added to the database 
    while (rs.next()){

      if (rs.getString("username").equals(username)){
        
        isAlreadyTaken = true;
        
        break;

      } 

    }

    if (!(isAlreadyTaken)){

      String addMember = "INSERT INTO Member(name, username, password) VALUES ('"+name+"',  '" + username + "', '" + password +"')";   
      stmt.executeUpdate(addMember);
    
    }

  }



}
  
