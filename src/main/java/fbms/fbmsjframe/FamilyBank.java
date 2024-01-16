package fbms.fbmsjframe;

import java.sql.*; 
import java.io.*; 
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

// Interface class  
// With changing to = for changing family name and username's password
interface FBMSControls{

  //public void accessAccount(String username, String password) throws SQLException;
  public void changeTo(int id, String input);
  
}

class FamilyBank extends FBMSDataBase implements FBMSControls{
    
    private int family_id;
    private String family_name; 

    protected String name, username, password;

    private double balance, asset, expense, liability, contribution; 
    private double budgetSpent;

    private String budgetCondtion = null; 

    // For PIE CHART ASSET total amounts
    
    // For PIE CHART EXPENSE total amounts
    private double billing, want, need; 
  
    // For PIE CHART DEBT total amounts
    private double borrowed, loan, installment;
  
    protected boolean isExisting = false; 
    protected boolean isExistingAcc = false;
    protected boolean isFamExisting = false;   
    protected boolean isAlreadyTaken = false;    

    // Creating the database class
    public FamilyBank(){ 

        super();

    }

    // Computing the balance of asset, expense, liability
    public void computeBalance() throws SQLException{
      
      String bal = "UPDATE Family SET balance = assetAmount + expensesAmount + liabilityAmount WHERE family_id = " + this.family_id + "";
      stmt.executeUpdate(bal);

    }

    // Computing the overall Budget spent (balance) from the overall contribution 
    public void computeBudgetSpent() throws SQLException{

      String bs = "UPDATE Family SET budgetSpent = contributionAmount - balance WHERE family_id = " + this.family_id + "   "; 
      stmt.executeUpdate(bs);
      
      String rsbs = "SELECT budgetSpent FROM Family WHERE family_id = " + this.family_id + " ";
      ResultSet rsBudgetSpent = stmt.executeQuery(rsbs);

      while (rsBudgetSpent.next())
        budgetSpent = rsBudgetSpent.getDouble("budgetSpent"); 
      
      
      
        
    }

    // Creating Family
    // User Input will be the name of the family 
    public void createFamily(String name) throws SQLException {
        
      String select = "SELECT Family_name FROM family"; 
      ResultSet rsSlct = stmt.executeQuery(select);

      // Iterate through the family name table
      while (rsSlct.next()){

        // If existing, it would fail the creation since the famname is already exists
        if (rsSlct.getString("Family_name").equals(name)){

          isFamExisting = true;
          break;
        } 
      

      }

      if (!(isFamExisting)){
       
        String cf = "INSERT INTO family (Family_name, balance, assetAmount,expensesAmount, liabilityAmount, contributionAmount, budgetSpent) VALUES('"+name+ "',"+0+", "+0+", "+0+", "+0+","+0+", "+0+")";
        stmt.executeUpdate(cf); 
        
        ResultSet rsID = stmt.executeQuery("Select family_id from family where family_name = '" +name+ "'"); 
        
        while(rsID.next()){

            this.family_id = rsID.getInt("family_id");

        }
        
        family_name = name;
        joinFamily(family_id, username);
      }

    
    }
    

    // Joining family 
    // User Input will be the family id and the user's username 
    public void joinFamily(int famID, String famName) throws SQLException{
        
        this.family_id = famID;
        
        String sqlFamID = "SELECT * FROM Family WHERE family_id = " +this.family_id+" ";
        ResultSet rsID = stmt.executeQuery(sqlFamID);   

        while (rsID.next()){
        
            if (rsID.getInt("family_id") == (famID)){
                
                isExisting = true; 
                this.family_id = rsID.getInt("family_id");
                
                String addMemberToFam = "UPDATE Member SET family_id = "+ this.family_id +" where username = '"+this.username+"'";
                stmt.executeUpdate(addMemberToFam); 

                break; 
                
            } 
            
        }

        if (!(isExisting)){

          isExisting = false; 

        }

    }
    
    // Logging in process 
    // User input will username and password
    public void login(String username, String password) throws SQLException{
    
      String sqlUNandPass = "SELECT name, username, password FROM Member where username = '"+username+"' and password = '"+ password +"'"; 
      ResultSet rsUNandPass = stmt.executeQuery(sqlUNandPass);
      boolean temp = false;

      while (rsUNandPass.next()){
      
          if (rsUNandPass.getString("username").equals(username) && rsUNandPass.getString("password").equals(password)){
              
              isExistingAcc = true;  
              ResultSet rsMemberID = stmt.executeQuery("SELECT family_id FROM Member WHERE username = '"+username+"'and password = '"+password+"' ");

              while (rsMemberID.next())
                family_id = rsMemberID.getInt("family_id");  
              
              ResultSet rsFamName = stmt.executeQuery("SELECT Family_name FROM Family WHERE family_id = "+this.family_id+" ");
              
              while (rsFamName.next())
                family_name = rsFamName.getString("Family_name");
              
              
              
              // Getting the name
              ResultSet rsName = stmt.executeQuery("SELECT name FROM Member WHERE username = '"+username+"'and password = '"+password+"' and family_id = "+this.family_id+" ");
              
              while(rsName.next())
                  name =  rsName.getString("name");
              
              
              // Getting the username
              ResultSet rsUN = stmt.executeQuery("SELECT username FROM Member WHERE username = '"+username+"'and password = '"+password+"' and family_id = "+this.family_id+" "); 
              
              while(rsUN.next())
                  this.username = rsUN.getString("username");
              
              // Getting the pass 
              ResultSet rsPass = stmt.executeQuery("SELECT password FROM Member WHERE username = '"+username+"'and password = '"+password+"' and family_id = "+this.family_id+" "); 
              
              while(rsPass.next())
                  this.password = rsPass.getString("password");
              
              temp = true;
              
              break; 
              
          }
          
      }
      
      if(!(temp)){ 

        isExistingAcc = false;

      }        
                
    }
    
    // Adding To the list 
    // User input will be the name of material, the amount/value of the material,
    // current variable is the type depending on the current frame (ASSET, EXPENSE, LIABILITY, CONTRIBUTE)
    // e.g if user pressed the asset list, the type will be: type = "ASSET"; 
    // PREFERABLE BUTTON: Add 
    public void addToList(String material, double amount, String type, String subType) throws SQLException{
        
      switch(type){
        
          case "ASSET":
            
            String addAssetList = "INSERT INTO TableAssetList value("+ family_id +", '" + material + "', " + amount +", '" + subType + "')";
            stmt.executeUpdate(addAssetList);     
            String addAssetAmount = "UPDATE family SET assetAmount = assetAmount + "+ amount +" WHERE family_id = "+  this.family_id+"";
            stmt.executeUpdate(addAssetAmount);
            
            computeBalance();
            computeBudgetSpent();

            break;
              
          case "EXPENSE":
              
              String addExpenseList = "INSERT INTO TableExpenseList values( " + this.family_id + ",'" + material + "', " + amount +", '" + subType + "')";
              stmt.executeUpdate(addExpenseList);
              //stmt.executeUpdate("UPDATE TableExpenseList set family_id = "+family_id+" where expenseList = '"+ material +"' and expenseValue = "+ amount +"");
              
              String addExpenseAmount = "UPDATE family SET expensesAmount = expensesAmount + " +amount+ " where family_id = "+ this.family_id+"";
              stmt.executeUpdate(addExpenseAmount); 
              
              String minusExpense = "UPDATE family SET balance = balance = " +amount+ " where family_id = "+ this.family_id+" "; 
              stmt.executeUpdate(minusExpense);
              
              computeBalance();
              computeBudgetSpent();
              
              break;
              
          case "LIABILITY":
              
              String addLiabitilyList = "INSERT INTO TableLiabilityList value("+ this.family_id +", '" + material + "', " + amount +", '" + subType + "')";
              stmt.executeUpdate(addLiabitilyList);  
              String addLiabilityAmount = "UPDATE family SET liabilityAmount = liabilityAmount + " +amount+ " where family_id = "+ this.family_id+" ";
              stmt.executeUpdate(addLiabilityAmount);
              
              computeBalance();
              computeBudgetSpent();

              break;

          case "CONTRIBUTE":

              // the "material" value is equal to the name of the contributor
              String addContributeList = "INSERT INTO TableContributionList value("+ this.family_id +", '" + material + "', " + amount +")";
              stmt.executeUpdate(addContributeList);  
              String addContributeAmount = "UPDATE family SET contributionAmount = contributionAmount + " +amount+ " WHERE family_id = "+this.family_id+" ";
              stmt.executeUpdate(addContributeAmount); 
              
              ResultSet rsContribValue = stmt.executeQuery("SELECT SUM(ContributionValue) FROM tablecontributionlist WHERE family_id = "+this.family_id+" and contributionlist = '"+this.name+"' "); 
             
              double contribValueAmount = 0; 
              while(rsContribValue.next())
                contribValueAmount = rsContribValue.getDouble("SUM(contributionValue)");
              
              
              //String addIndiveContrib = "UPDATE tablecontributionlist SET contributionValue = "+contribValueAmount+" WHERE family_id = "+this.family_id+" and contributionlist = '"+this.name+"' ";
              //stmt.executeUpdate(addIndiveContrib);

              computeBalance();
              computeBudgetSpent();

            break;
      
        }   
        
    }
    
    // Removing from the list 
    // User input will be name  
    // current variable is the type depending on the current frame (ASSET, EXPENSE, LIABILITY, CONTRIBUTE)
    // e.g if user pressed the asset list, the type will be: type = "ASSET";
    double valAsset = 0;
    public void removefromList(String name, String type, String subType)throws SQLException{
        
      switch(type){
      
        case "ASSET":
            
            String selectAsset = "SELECT AssetValue FROM TableAssetList WHERE  family_id = "+this.family_id+" and AssetList = '" +name+"' ";
            ResultSet rsAsset = stmt.executeQuery(selectAsset);

            
            while (rsAsset.next())
                valAsset = rsAsset.getDouble("AssetValue");

            String minusAssetAmount = "UPDATE family SET assetAmount = assetAmount - " +valAsset+ " WHERE family_id = "+this.family_id+"";
            stmt.executeUpdate(minusAssetAmount);
            
            String removeAsset = "DELETE FROM TableAssetList WHERE family_id = "+ this.family_id+" and assetList = '" +name+"'";
            stmt.executeUpdate(removeAsset);

            computeBalance();
            computeBudgetSpent();
            break;
            
        case "EXPENSE":

            String selectExpense = "SELECT ExpenseValue FROM TableExpenseList WHERE family_id = "+this.family_id+" and ExpenseList = '" +name+"' ";
            ResultSet rsExpense = stmt.executeQuery(selectExpense);
            
            double valExpense = 0;
            while(rsExpense.next())
              valExpense = rsExpense.getDouble("ExpenseValue");
            String addExpenseAmountToBal = "UPDATE family SET expensesAmount = expensesAmount - " +valExpense+ " where family_id = "+this.family_id+"";
            stmt.executeUpdate(addExpenseAmountToBal);
            
            String removeExpense = "DELETE FROM TableExpenseList WHERE family_id = "+ this.family_id+" and expenseList = '" +name+ "'";
            stmt.executeUpdate(removeExpense);

            if (subType.equals("billing"))
             billing -= valExpense; 
            else if (subType.equals("want"))
              want -= valExpense; 
            else if (subType.equals("need"))
             need -= valExpense;

            computeBalance();
            computeBudgetSpent();
            break;
            
        case "LIABILITY":

            String selectLiability = "SELECT LiabilityValue FROM TableLiabilityList WHERE family_id = "+this.family_id+" and LiabilityList = '" +name+"' ";
            ResultSet rsLiability = stmt.executeQuery(selectLiability);

            double valLiability = 0;
            while(rsLiability.next())
              valLiability = rsLiability.getDouble("LiabilityValue");


            String minusLiabilityAmount = "UPDATE family SET LiabilityAmount = LiabilityAmount - " +valLiability+ " WHERE family_id = "+this.family_id+"";
            stmt.executeUpdate(minusLiabilityAmount);
            
            String removeLiability = "DELETE FROM TableLiabilityList WHERE family_id = "+ this.family_id+" and LiabilityList = '" +name+ "' ";
            stmt.executeUpdate(removeLiability);   
            
            if (subType.equals("borrowed"))
              borrowed -= valLiability; 
            else if (subType.equals("loan"))
              loan -= valLiability; 
            else if (subType.equals("installment"))
              installment -= valLiability;

            computeBalance();
            computeBudgetSpent();
                          
            break;

        case "CONTRIBUTE":

            String selectContribute = "SELECT SUM(contributionValue) FROM tablecontributionlist WHERE family_id = "+this.family_id+" and contributionlist = '" +this.name+"' ";
            ResultSet rsContribute = stmt.executeQuery(selectContribute);
            
            double valContribute = 0;
            while(rsContribute.next())
              valContribute = rsContribute.getDouble("SUM(contributionvalue)");


            String minusContributeAmount = "UPDATE family SET contributionAmount = contributionAmount - " +valContribute+ " WHERE family_id = "+this.family_id+" " ;
            stmt.executeUpdate(minusContributeAmount);
            
            String removeContribute = "DELETE FROM tablecontributionlist  WHERE family_id = "+this.family_id+" and contributionlist = '" +this.name+ "'  ";
            stmt.executeUpdate(removeContribute);  
            
            computeBalance();
            computeBudgetSpent();
          break;
      
      }

    }


    // Changing the family name 
    // the String name here is represented as ID (DON'T MODIFY)
    public void changeTo(int id, String Famname){

      String changeFamName = "UPDATE Family SET Family_name = '" + Famname +"' WHERE family_id = " + id + " "; 
        try { 
            stmt.executeUpdate(changeFamName);
        } catch (SQLException ex) {
            Logger.getLogger(FamilyBank.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }
    
    // Changing name
   public void changeName(String username, String input){

      String changeName = "UPDATE Member SET name = '"+ input +"' WHERE username = '" + username +"' and family_id = "+this.family_id+"  "; 
        try {
            stmt.executeUpdate(changeName);
        } catch (SQLException ex) {
            Logger.getLogger(FamilyMember.class.getName()).log(Level.SEVERE, null, ex);
        }
  
    }
    
    // Cnanging username 
    public void changeUsername(String username, String input){

      String changeUN = "UPDATE Member SET username = '"+ input +"' WHERE username = '" + username +"' and family_id = "+this.family_id+"  "; 
        try {
            stmt.executeUpdate(changeUN);
        } catch (SQLException ex) {
            Logger.getLogger(FamilyMember.class.getName()).log(Level.SEVERE, null, ex);
        }
  
    }
    
    
    // Chaning passoword 
    public void changePass(String username, String input){

      String changePass = "UPDATE Member SET password = '"+ input +"' WHERE username = '" + username +"' and family_id = "+this.family_id+"  "; 
        try {
            stmt.executeUpdate(changePass);
        } catch (SQLException ex) {
            Logger.getLogger(FamilyMember.class.getName()).log(Level.SEVERE, null, ex);
        }
  
    }

    // Getters FOR PROGRAMMER'S INPUT

    // Getting the total budget 
    public String getBalance() throws SQLException {
      
     
      ResultSet rs = stmt.executeQuery("Select balance from Family where family_id = " +this.family_id+ "");
      
      while (rs.next()){
        
        balance = rs.getDouble("balance");
        
      }
      
      
      return String.format("%.2f", balance);

    }

    // Getting the total amount of asset 
    public String getAsset() throws SQLException {

      ResultSet rs = stmt.executeQuery("Select assetAmount from Family where family_id = " +this.family_id+ "");
      
      while (rs.next()){

        asset = rs.getDouble("assetAmount");

      }
     

      return String.format("%.2f", asset);

    }
    
    // Getting the total amount of expense
    public String getExpense() throws SQLException {

      ResultSet rs = stmt.executeQuery("Select expensesAmount from Family where family_id = " +this.family_id+ "");

      while (rs.next()){

        expense = rs.getDouble("expensesAmount");

      }
     
      return String.format("%.2f", expense);

    }

    // Getting the total amount of liability
    public String getLiability() throws SQLException {

      ResultSet rs = stmt.executeQuery("Select liabilityAmount from Family where family_id = " +this.family_id+ "");
      
      while (rs.next()){
        
        liability = rs.getDouble("liabilityAmount");

      }

      return String.format("%.2f", liability);

    }

    // Getting the amount of contribution
    public String getContribution() throws SQLException {

      ResultSet rs = stmt.executeQuery("Select contributionAmount from Family where family_id = " +this.family_id+ "");

      while (rs.next()){
        
        contribution = rs.getDouble("contributionAmount");

      }
    
      return String.format("%.2f", contribution);

    }
    
    public String getIndivContrib() throws SQLException{
        double indivContrib = 0;
        
        ResultSet rsIndiv = stmt.executeQuery("Select SUM(contributionValue) from tablecontributionlist where family_id = " +this.family_id+ " and contributionlist = '"+this.name+"' ");
        
        while(rsIndiv.next())
            indivContrib = rsIndiv.getDouble("SUM(contributionValue)");
        
        return String.format("%.2f",indivContrib);
    }


    // Getting the amount of budget spend te
    public String getBudgetSpent() throws SQLException{

      ResultSet rs = stmt.executeQuery("SELECT budgetSpent FROM Family WHERE family_id = " +this.family_id+ " ");

      while (rs.next()){
        
        budgetSpent = rs.getDouble("budgetSpent");

      }
    

      return String.format("%.2f", budgetSpent);

    }

    // Getting the name of the member
    public String getMemberName() throws SQLException{
        
      ResultSet rsName = stmt.executeQuery("SELECT name FROM member WHERE family_id = "+this.family_id+" and username = '"+this.username+"'");
      
      
      while(rsName.next())
          name = rsName.getString("name");
      
      return name; 

    }

    // getting the username of the member 
    public String getUserName() throws SQLException{
      String tempUN = null;
      ResultSet rsUN = stmt.executeQuery("SELECT username FROM member WHERE family_id = "+this.family_id+" and name = '"+this.name+"'");
      
      while(rsUN.next())
          tempUN = rsUN.getString("username");
      
      
      return username = tempUN; 

    }
    

    // Getting the name of the family
    public String getFamilyName() throws SQLException{
        
      ResultSet rsFamName = stmt.executeQuery("SELECT family_name FROM family WHERE family_id = "+this.family_id+" ");
      
      while(rsFamName.next())
          family_name = rsFamName.getString("family_name");
       

      return family_name; 

    }


    // Getting the family id 
    public String getFamilyID(){
      
      return String.format("%d", family_id);

    }

    // Getting the budget condition
    // The string
    
    private String color = null; 
    
    // GET THE COLOR CONDTION 
    public String getColor(){
        
        return color;
        
    }
    
    
    public String getBudgetCondition() throws SQLException{
       
      String rsc = "SELECT contributionAmount FROM Family WHERE family_id = " + this.family_id + "  ";
      ResultSet rsContribute = stmt.executeQuery(rsc);
      
      double contributionAmount = 0;
      String tempColor = null;
      
      while(rsContribute.next())
        contributionAmount = rsContribute.getDouble("contributionAmount");
      
      String tempString = null; 
      if (budgetSpent > contributionAmount){
          tempString  = "Your Family is Over the Budget";
          tempColor = "RED";
      }
      
      else if (budgetSpent <= contributionAmount && budgetSpent > 0){
          tempString = "Your Family is On the Budget"; 
          tempColor = "GREEN";
      }
      else if (budgetSpent <= 0){
        tempString = "Add your Finances Now"; 
        tempColor = "RED";
        
      }
        
      color = tempColor;
      return budgetCondtion = tempString;     

    }

    // Getting the amount of the input subType (e.g saving, borrower, billing)
    // This is not user input, instead programmer input since it is a getter
    public String getSubAmount(String inputSubType) throws SQLException{
      double returnAmount = 0; 

      switch (inputSubType){

        case "saving":
        //asset
        ResultSet rsSaving = stmt.executeQuery("SELECT sum(Assetvalue) FROM TableAssetList WHERE AssetType = '"+inputSubType+"' and family_id = "+ this.family_id + "");
        
        while(rsSaving.next())
          returnAmount = rsSaving.getDouble("sum(Assetvalue)"); 

        break; 

        case "investment":
        //asset
        ResultSet rsInv = stmt.executeQuery("SELECT sum(Assetvalue) FROM TableAssetList WHERE AssetType = '"+inputSubType+"' and family_id = "+ this.family_id + "");
        while(rsInv.next())
          returnAmount = rsInv.getDouble("sum(Assetvalue)"); 

        break; 
        //asset
        case "otherfund":
        ResultSet rsOF = stmt.executeQuery("SELECT sum(Assetvalue) FROM TableAssetList WHERE AssetType = '"+inputSubType+"' and family_id = "+ this.family_id + "");
        
        while(rsOF.next())
          returnAmount = rsOF.getDouble("sum(Assetvalue)"); 
        
       
        break; 
        // expenses
        case "billing":
        ResultSet rsBill = stmt.executeQuery("SELECT sum(expensevalue) FROM TableExpenseList WHERE expenseType = '"+inputSubType+"' and family_id = "+ this.family_id + "");
        
        while(rsBill.next())
          returnAmount = rsBill.getDouble("sum(expensevalue)"); 
        
      
        break; 
        //expenses
        case "want":
        ResultSet rsWant = stmt.executeQuery("SELECT sum(expensevalue) FROM TableExpenseList WHERE expenseType = '"+inputSubType+"' and family_id = "+ this.family_id + "");
          
        while(rsWant.next())
          returnAmount = rsWant.getDouble("sum(expensevalue)"); 

        break; 
        //expenses
        case "need":
        ResultSet rsNeed = stmt.executeQuery("SELECT sum(expensevalue) FROM TableExpenseList WHERE expenseType = '"+inputSubType+"' and family_id = "+ this.family_id + "");
        
        while(rsNeed.next())
          returnAmount = rsNeed.getDouble("sum(expensevalue)");
        
        break; 
        //liability
        case "borrowed":
        ResultSet rsB = stmt.executeQuery("SELECT sum(liabilityValue) FROM TableLiabilityList WHERE liabilityType = '"+inputSubType+"' and family_id = "+ this.family_id + "");
        
        while(rsB.next())
          returnAmount = rsB.getDouble("sum(liabilityValue)");
        
        break; 
        //liability
        case "loan":
        ResultSet rsLoan = stmt.executeQuery("SELECT sum(liabilityValue) FROM TableLiabilityList WHERE liabilityType = '"+inputSubType+"' and family_id = "+ this.family_id + "");
     
        while(rsLoan.next())
          returnAmount = rsLoan.getDouble("sum(liabilityValue)");
        
        break; 
        //liability
        case "installment":
        ResultSet rsInstallment = stmt.executeQuery("SELECT sum(liabilityValue) FROM TableLiabilityList WHERE liabilityType = '"+inputSubType+"' and family_id = "+ this.family_id + "");
        
        while(rsInstallment.next())
          returnAmount = rsInstallment.getDouble("sum(liabilityValue)");

        break; 

      }


      return String.format("%.2f", returnAmount);

    }
    
    // Return the size of the column 
    public int columnCount() throws SQLException{
        
        
        ResultSet rsCount = stmt.executeQuery("SELECT COUNT(family_id) FROM family");
        
        int returnCount = 0;
        while (rsCount.next())
             returnCount = rsCount.getInt("COUNT(Family_id)");
        
        return returnCount;
        
    }
            
            
      // Returns true if family is existing in the database 
      public boolean getFamilyIsExisting(){
      
        return isExisting;
  
      }
  
      // Returns true if member is existing in the database
      public boolean getMemberIsExisting(){
        
        return isExistingAcc; 
      
      }
  
      // Returns true if inputed fam name is already existing in the database
      public boolean getFam_alreadyExisting(){
  
        return isFamExisting;
  
      }
  
      // Returns true if inputed username is already existing in the database
      public boolean getMember_alreadyExisting(){
        
        return isAlreadyTaken; 
  
      }
      
      
    
}