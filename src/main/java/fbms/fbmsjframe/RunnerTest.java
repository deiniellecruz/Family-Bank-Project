/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fbms.fbmsjframe;

import java.sql.SQLException;

/**
 *
 * @author Izee
 */
public class RunnerTest {
    
    public static void main(String[] args) throws SQLException{
    
        FamilyBank fm = new FamilyMember("Adin", "adin", "adin");
        
        
        
        fm.joinFamily(1, "Martin Fam");
        
        System.out.println(fm.getFamilyID());
        
        
    }
}
