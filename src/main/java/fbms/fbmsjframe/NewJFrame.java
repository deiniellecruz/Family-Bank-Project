/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package fbms.fbmsjframe;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPanel;
import java.awt.Font;
import javax.swing.table.JTableHeader;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
/**
 *
 * @author Izee
 */
public class NewJFrame extends javax.swing.JFrame {
    
     final String DRIVER = "com.mysql.cj.jdbc.Driver";
     final String DB_URL = "jdbc:mysql://localhost:3306/familybank";
     final String DB_USER = "root";
     final String DB_PASS = "admin";
    
     Connection conn;
     Statement stmt;
    

    //private OpenJframe newSignIn;
    private FamilyBank family; 
    private int COL;
    /**
     * Creates new form NewJFrame
     */
    public NewJFrame(FamilyBank member) throws SQLException{
            
        family = member;
         try {
             COL = family.columnCount();
         } catch (SQLException ex) {
             Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
         }
         
       
       setUndecorated(true);
        initComponents();
        setLocationRelativeTo(null);
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
        

       tableHeaderChangeColor(expenseTableList);
       tableHeaderChangeColor(assetListTable);
       tableHeaderChangeColor(expenseTableList1);
       tableHeaderChangeColor(liabilitiesListTable);
       tableHeaderChangeColor(membersListTable);

       
        conn = null;
        stmt = null;
        
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
        
        // Calling all the methods
        // It will show all the values from the database
    
        setExpenseTable();
        setMemberTable();
        setAssetTable();
        setLiabilityTable();
       
        
    }
    
public void tableHeaderChangeColor(javax.swing.JTable table){
    JTableHeader tableHeader = table.getTableHeader();
    tableHeader.setFont(new Font("Montserrat", Font.BOLD, 15));
    //table.getTableHeader().setOpaque(true);
    tableHeader.setBackground(new Color(43,43,75));
    tableHeader.setForeground(new Color(38,38,38));
   // ((DefaultTableCellRenderer)tableHeader.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
}
//NewJFrame(FamilyBank fm) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
    
    
     // Setting the table of the expense list
    
   DefaultTableModel modelExpense, modelExpense1;
    public void setExpenseTable() throws SQLException{
        
        ResultSet rsExpenseList = stmt.executeQuery("SELECT * FROM tableexpenselist WHERE family_id = "+family.getFamilyID()+" ");
        
        while (rsExpenseList.next()){
            
            String item = rsExpenseList.getString("expenseList");
            double tempAmount = rsExpenseList.getDouble("expenseValue"); 
            String amount = String.format("%.2f", tempAmount);
            String type = rsExpenseList.getString("expenseType");
            
            String data[] = new String[] {item, amount, type};
            
            // For dashboard list 
            modelExpense = (DefaultTableModel)expenseTableList.getModel(); 
            // For billings and expesne tab list 
            modelExpense1 = (DefaultTableModel)expenseTableList1.getModel(); 
            
            modelExpense.addRow(data);
            modelExpense1.addRow(data); 
              
            
        }
        
        
    }
    
    public void removeExpenseTable(){ 
        
        if (this.modelExpense == null && this.modelExpense1 == null)
            return;
      
        while(modelExpense.getRowCount() > 0 && modelExpense1.getRowCount() > 0){
          
          modelExpense.removeRow(0);
          modelExpense1.removeRow(0); 
          
        }
        
        
    }
    
    
    // Setting the table of the member contribution
    DefaultTableModel modelContrib; 
    public void setMemberTable() throws SQLException{ 
        ResultSet rsMemberList = stmt.executeQuery("SELECT * FROM tablecontributionlist WHERE family_id = "+family.getFamilyID()+" ");
        
        while (rsMemberList.next()){
            
            String item = rsMemberList.getString("contributionList");
            double tempAmount = rsMemberList.getDouble("contributionValue"); 
            String amount = String.format("%.2f", tempAmount);
        
            String data[] = {item, amount};
            
            // For member tab
            modelContrib = (DefaultTableModel)membersListTable.getModel();  
            modelContrib.addRow(data); 
            
        }
    }
    
    public void removeContribList(){ 
       
        if (this.modelContrib == null) 
            return;
       
        while(modelContrib.getRowCount() > 0)
           modelContrib.removeRow(0);

        
       
    }
    
    // Setting up the amount table list
    DefaultTableModel model = null; 
    public void setAssetTable() throws SQLException{
        
        ResultSet rsAssetList = stmt.executeQuery("SELECT * FROM tableassetlist WHERE family_id = "+family.getFamilyID()+" ");
        
        
        while (rsAssetList.next()){
            
            String item = rsAssetList.getString("assetList");
            double tempAmount = rsAssetList.getDouble("assetValue"); 
            String amount = String.format("%.2f", tempAmount);
            String type = rsAssetList.getString("assetType");
            
            String data[] = {item, amount, type};
            
            // For asset tabe 
            model = (DefaultTableModel)assetListTable.getModel();
            model.addRow(data);
            
            
            
        }
        
        
    }
    
    public void removeAssetTable(){ 
           
          
        if (this.model == null)
            return; 
        
        while(model.getRowCount() > 0 ){

            model.removeRow(0);

        }
          
   
    }
    
    // Setting up the liability list
     DefaultTableModel modelLiability; 
     public void setLiabilityTable() throws SQLException{
        
         ResultSet rsLiabilityList = stmt.executeQuery("SELECT * FROM tableliabilitylist WHERE family_id = "+family.getFamilyID()+" ");
        
        while (rsLiabilityList.next()){
            
            String item = rsLiabilityList.getString("liabilityList");
            double tempAmount = rsLiabilityList.getDouble("liabilityValue"); 
            String amount = String.format("%.2f", tempAmount);
            String type = rsLiabilityList.getString("liabilityType");
            
            String data[] = {item, amount, type};
            
            // For liability tab 
            modelLiability = (DefaultTableModel)liabilitiesListTable.getModel();
            modelLiability.addRow(data);
         
            
        }
        
    }
    
    public void removeLiabilityTable(){
    
        if (this.modelLiability == null)
            return;
          
        while(modelLiability.getRowCount() > 0){

            modelLiability.removeRow(0);

        }

         
    }
    
    
  

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    //@SuppressWanings("unchecked")
    
   
    
    // NETBEANS CANNOT BE MODIFIED METHODS
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel16 = new javax.swing.JLabel();
        hidePanel = new javax.swing.JPanel();
        exitButton = new javax.swing.JButton();
        refresh = new javax.swing.JButton();
        jTabbedPane = new javax.swing.JTabbedPane();
        dashboardBoard = new javax.swing.JPanel();
        dashboardTitle = new javax.swing.JLabel();
        totalBudgetPanel = new RoundedPanel(20, new Color(0,0,48));
        totalBudgetLabel = new javax.swing.JLabel();
        totalBudgetAmount = new javax.swing.JLabel();
        totalBillingsPanel = new RoundedPanel(20);
        totalBillingsLabel = new javax.swing.JLabel();
        totalBillingsAmount = new javax.swing.JLabel();
        totlalLiabilitiesPanel = new RoundedPanel(20);
        totlalLiabilitiesLabel = new javax.swing.JLabel();
        totalLiabilitiesAmount = new javax.swing.JLabel();
        totalAssetsPanel = new RoundedPanel(20);
        totalAssetsLabel = new javax.swing.JLabel();
        totalAssetsAmount = new javax.swing.JLabel();
        dashboardContribSharesPanel = new RoundedPanel(20);
        dashboardContribSharesTotalAmount = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        dashboardBudgetConditionMessagePanel = new RoundedPanel(20);
        budgetConditionLabel1 = new javax.swing.JLabel();
        jPanel1 = new RoundedPanel(20);
        jScrollPane2 = new javax.swing.JScrollPane();
        expenseTableList = new javax.swing.JTable();
        jLabel15 = new javax.swing.JLabel();
        membersBoard = new javax.swing.JPanel();
        membersTitle = new javax.swing.JLabel();
        ContributionSharePanel = new RoundedPanel(20);
        totalAmountContributedText = new javax.swing.JLabel();
        showUserContrib1 = new javax.swing.JLabel();
        dashboardContribSharesPanel1 = new RoundedPanel(20, new Color(43,43,75));
        dashboardContribSharesTotalAmount1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new RoundedPanel(20, new Color(43,43,75));
        membersListScrollPane = new javax.swing.JScrollPane();
        membersListTable = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        removeNameLabel = new javax.swing.JLabel();
        memberRemoveTextfield = new javax.swing.JTextField();
        memberRemoveButton = new javax.swing.JButton();
        jPanel4 = new RoundedPanel(20);
        jLabel6 = new javax.swing.JLabel();
        totalBudgetAmount1 = new javax.swing.JLabel();
        statsBoard = new javax.swing.JPanel();
        statsTitle = new javax.swing.JLabel();
        totalAmountReport = new RoundedPanel(20, new Color(43,43,75));
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        totalBudgetAmount3 = new javax.swing.JLabel();
        dashboardContribSharesTotalAmount2 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        budgetConditionMessagePanel = new RoundedPanel(20);
        budgetConditionLabel = new javax.swing.JLabel();
        jPanel5 = new RoundedPanel(20);
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        totalAssetsAmount2 = new javax.swing.JLabel();
        totalBillingsAmount2 = new javax.swing.JLabel();
        totalLiabilitiesAmount2 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel6 = new RoundedPanel(20);
        jLabel8 = new javax.swing.JLabel();
        totalBudgetAmount2 = new javax.swing.JLabel();
        jPanel7 = new RoundedPanel(20);
        jLabel11 = new javax.swing.JLabel();
        budgetSpentTotal = new javax.swing.JLabel();
        assetsBoard = new javax.swing.JPanel();
        assetsTitle = new javax.swing.JLabel();
        assetsStatusPanel = new RoundedPanel(20);
        totalAssetsAmount1 = new javax.swing.JLabel();
        assetsChartPanel = new RoundedPanel(20, new Color(43,43,75));
        assetTypeLabel = new javax.swing.JLabel();
        assetItemLabel = new javax.swing.JLabel();
        assetValueLabel = new javax.swing.JLabel();
        assetsItemTextfield = new java.awt.TextField();
        assetsValueTextfield = new java.awt.TextField();
        assetsTypeTextfield = new java.awt.TextField();
        assetsRemoveButton = new javax.swing.JButton();
        assetsAddButton = new javax.swing.JButton();
        profileAccountLabel1 = new javax.swing.JLabel();
        jPanel8 = new RoundedPanel(20);
        jLabel17 = new javax.swing.JLabel();
        assetsListScrollPane = new javax.swing.JScrollPane();
        assetListTable = new javax.swing.JTable();
        liabilitiesBoard = new javax.swing.JPanel();
        liabilitiesTitle = new javax.swing.JLabel();
        liabilitiesStatusPanel = new RoundedPanel(20);
        totalLiabilitiesAmount1 = new javax.swing.JLabel();
        liabilitiesChartPanel = new RoundedPanel(20, new Color(43,43,75));
        liabilitiesTypeLabel = new javax.swing.JLabel();
        liabilitiesItemLabel = new javax.swing.JLabel();
        liabilitiesValueLabel = new javax.swing.JLabel();
        liabilitiesItemTextfield = new java.awt.TextField();
        liabilitiesValueTextfield = new java.awt.TextField();
        liabilitiesTypeTextfield = new java.awt.TextField();
        liabilitiesAddButton = new javax.swing.JButton();
        liabilitiesRemoveButton = new javax.swing.JButton();
        profileAccountLabel2 = new javax.swing.JLabel();
        jPanel9 = new RoundedPanel(20);
        jLabel18 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        liabilitiesListTable = new javax.swing.JTable();
        billingsBoard = new javax.swing.JPanel();
        billingsTitle = new javax.swing.JLabel();
        billingsStatusPanel = new RoundedPanel(20);
        totalBillingsAmount1 = new javax.swing.JLabel();
        billingsChartPanel = new RoundedPanel(20, new Color(43,43,75));
        billingsItemTextfield = new java.awt.TextField();
        billingsItemLabel = new javax.swing.JLabel();
        billingsValueLabel = new javax.swing.JLabel();
        billingsValueTextfield = new java.awt.TextField();
        billingsTypeTextfield = new java.awt.TextField();
        billingsTypeLabel = new javax.swing.JLabel();
        billingsRemoveButton = new javax.swing.JButton();
        billingsAddButton = new javax.swing.JButton();
        profileAccountLabel3 = new javax.swing.JLabel();
        jPanel10 = new RoundedPanel(20);
        jLabel19 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        expenseTableList1 = new javax.swing.JTable();
        settingsBoard = new javax.swing.JPanel();
        settingsTitle = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        helpBoard = new javax.swing.JPanel();
        profileTitle1 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        profileBoard = new javax.swing.JPanel();
        profileTitle = new javax.swing.JLabel();
        showProfilePanel = new RoundedPanel(20);
        userFullName = new javax.swing.JLabel();
        username = new javax.swing.JLabel();
        familyname = new javax.swing.JLabel();
        accountUsernameLabel1 = new javax.swing.JLabel();
        accountUsernameLabel2 = new javax.swing.JLabel();
        profileAccountPanel = new RoundedPanel(20, new Color(43,43,75));
        profileAccountLabel = new javax.swing.JLabel();
        accountUsernameTextfield = new javax.swing.JTextField();
        accountUsernameLabel = new javax.swing.JLabel();
        accountPasswordLabel = new javax.swing.JLabel();
        accountPasswordTextfield = new javax.swing.JTextField();
        accountNameLabel = new javax.swing.JLabel();
        accountNameTextField = new javax.swing.JTextField();
        accountUsernameSaveButton = new javax.swing.JButton();
        accountNameSaveButton = new javax.swing.JButton();
        accountPasswordSaveButton = new javax.swing.JButton();
        accountFamilyTextfield = new javax.swing.JTextField();
        accountFamilySaveButton = new javax.swing.JButton();
        accountFamilyLabel1 = new javax.swing.JLabel();
        userContribPanel = new RoundedPanel(20, new Color(43,43,75));
        totaluserContribText = new javax.swing.JLabel();
        editUserContribText = new javax.swing.JLabel();
        showUserContrib = new javax.swing.JLabel();
        userContribSeparator = new javax.swing.JSeparator();
        inputEditUserContrib = new javax.swing.JTextField();
        saveEditUserContrib = new javax.swing.JButton();
        totalAmountContributedText1 = new javax.swing.JLabel();
        jFrameBg = new javax.swing.JPanel();
        tabPanel = new javax.swing.JPanel();
        profileTab = new RoundedPanel(20);
        profileNameLabel = new javax.swing.JLabel();
        menuPanel = new RoundedPanel(20, new Color(43,43,75));
        jSeparator1 = new javax.swing.JSeparator();
        dashboardPanel = new javax.swing.JPanel();
        dashboardLabel = new javax.swing.JLabel();
        membersPanel = new javax.swing.JPanel();
        membersLabel = new javax.swing.JLabel();
        statsPanel = new javax.swing.JPanel();
        statsLabel = new javax.swing.JLabel();
        assetsPanel = new javax.swing.JPanel();
        assetsLabel = new javax.swing.JLabel();
        liabilitiesPanel = new javax.swing.JPanel();
        liabilitiesLabel = new javax.swing.JLabel();
        billingsPanel = new javax.swing.JPanel();
        billingsLabel = new javax.swing.JLabel();
        settingsPanel = new javax.swing.JPanel();
        settingsLabel = new javax.swing.JLabel();
        helpPanel = new javax.swing.JPanel();
        helpLabel = new javax.swing.JLabel();
        logoutPanel = new javax.swing.JPanel();
        logoutButton = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();

        jLabel16.setFont(new java.awt.Font("Montserrat Black", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(210, 210, 215));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("Budget & Expenses List");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Family Management System");
        setAlwaysOnTop(true);
        setAutoRequestFocus(false);
        setBackground(new java.awt.Color(0, 0, 48));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setName("jFrame"); // NOI18N
        setResizable(false);
        setSize(new java.awt.Dimension(1100, 650));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        hidePanel.setBackground(new java.awt.Color(0, 0, 48));
        hidePanel.setPreferredSize(new java.awt.Dimension(1100, 650));
        hidePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        exitButton.setBackground(new java.awt.Color(230, 68, 86));
        exitButton.setFont(new java.awt.Font("Montserrat Black", 1, 18)); // NOI18N
        exitButton.setForeground(new java.awt.Color(210, 210, 215));
        exitButton.setText("X");
        exitButton.setBorder(null);
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });
        hidePanel.add(exitButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 10, 40, 30));

        refresh.setBackground(new java.awt.Color(236, 172, 116));
        refresh.setFont(new java.awt.Font("Montserrat Black", 2, 14)); // NOI18N
        refresh.setForeground(new java.awt.Color(38, 38, 38));
        refresh.setText("Refresh");
        refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshActionPerformed(evt);
            }
        });
        hidePanel.add(refresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 10, -1, 30));

        getContentPane().add(hidePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1100, 60));

        jTabbedPane.setBackground(new java.awt.Color(0, 0, 48));
        jTabbedPane.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTabbedPane.setFont(new java.awt.Font("Montserrat", 0, 8)); // NOI18N
        jTabbedPane.setPreferredSize(new java.awt.Dimension(770, 550));

        dashboardBoard.setBackground(new java.awt.Color(0, 0, 48));
        dashboardBoard.setForeground(new java.awt.Color(210, 210, 215));
        dashboardBoard.setMaximumSize(new java.awt.Dimension(770, 550));
        dashboardBoard.setMinimumSize(new java.awt.Dimension(770, 550));
        dashboardBoard.setPreferredSize(new java.awt.Dimension(770, 550));

        dashboardTitle.setFont(new java.awt.Font("Montserrat Black", 0, 60)); // NOI18N
        dashboardTitle.setForeground(new java.awt.Color(210, 210, 215));
        dashboardTitle.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        dashboardTitle.setText("Dashboard");

        totalBudgetPanel.setBackground(new java.awt.Color(0, 0, 48));
        totalBudgetPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(128, 100, 162), 4, true));
        totalBudgetPanel.setForeground(new java.awt.Color(43, 43, 75));
        totalBudgetPanel.setOpaque(false);
        totalBudgetPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        totalBudgetLabel.setFont(new java.awt.Font("Montserrat Black", 0, 14)); // NOI18N
        totalBudgetLabel.setForeground(new java.awt.Color(210, 210, 215));
        totalBudgetLabel.setText("Balance Sheet");
        totalBudgetPanel.add(totalBudgetLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        totalBudgetAmount.setFont(new java.awt.Font("Bebas", 1, 36)); // NOI18N
        totalBudgetAmount.setForeground(new java.awt.Color(210, 210, 215));
        totalBudgetAmount.setText("P 00.00");
        totalBudgetAmount.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                totalBudgetAmountPropertyChange(evt);
            }
        });
        totalBudgetPanel.add(totalBudgetAmount, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 190, -1));

        totalBillingsPanel.setBackground(new java.awt.Color(0, 0, 48));
        totalBillingsPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(49, 107, 196), 4, true));
        totalBillingsPanel.setForeground(new java.awt.Color(43, 43, 75));
        totalBillingsPanel.setOpaque(false);
        totalBillingsPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        totalBillingsLabel.setFont(new java.awt.Font("Montserrat Black", 1, 14)); // NOI18N
        totalBillingsLabel.setForeground(new java.awt.Color(210, 210, 215));
        totalBillingsLabel.setText("Billings & Expenses");
        totalBillingsPanel.add(totalBillingsLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        totalBillingsAmount.setFont(new java.awt.Font("Bebas", 1, 36)); // NOI18N
        totalBillingsAmount.setForeground(new java.awt.Color(230, 68, 86));
        totalBillingsAmount.setText("p 00.00");
        totalBillingsAmount.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                totalBillingsAmountPropertyChange(evt);
            }
        });
        totalBillingsPanel.add(totalBillingsAmount, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 180, -1));

        totlalLiabilitiesPanel.setBackground(new java.awt.Color(0, 0, 48));
        totlalLiabilitiesPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(236, 172, 116), 4, true));
        totlalLiabilitiesPanel.setForeground(new java.awt.Color(43, 43, 75));
        totlalLiabilitiesPanel.setOpaque(false);
        totlalLiabilitiesPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        totlalLiabilitiesLabel.setFont(new java.awt.Font("Montserrat Black", 1, 14)); // NOI18N
        totlalLiabilitiesLabel.setForeground(new java.awt.Color(210, 210, 215));
        totlalLiabilitiesLabel.setText("Liabilities");
        totlalLiabilitiesPanel.add(totlalLiabilitiesLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        totalLiabilitiesAmount.setFont(new java.awt.Font("Bebas", 1, 36)); // NOI18N
        totalLiabilitiesAmount.setForeground(new java.awt.Color(230, 68, 86));
        totalLiabilitiesAmount.setText("P 00.00");
        totalLiabilitiesAmount.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                totalLiabilitiesAmountPropertyChange(evt);
            }
        });
        totlalLiabilitiesPanel.add(totalLiabilitiesAmount, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 190, -1));

        totalAssetsPanel.setBackground(new java.awt.Color(0, 0, 48));
        totalAssetsPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(236, 126, 179), 4, true));
        totalAssetsPanel.setForeground(new java.awt.Color(43, 43, 75));
        totalAssetsPanel.setOpaque(false);
        totalAssetsPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        totalAssetsLabel.setFont(new java.awt.Font("Montserrat Black", 1, 14)); // NOI18N
        totalAssetsLabel.setForeground(new java.awt.Color(210, 210, 215));
        totalAssetsLabel.setText("Assets");
        totalAssetsPanel.add(totalAssetsLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        totalAssetsAmount.setFont(new java.awt.Font("Bebas", 1, 36)); // NOI18N
        totalAssetsAmount.setForeground(new java.awt.Color(89, 228, 147));
        totalAssetsAmount.setText("p 00.00");
        totalAssetsAmount.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                totalAssetsAmountPropertyChange(evt);
            }
        });
        totalAssetsPanel.add(totalAssetsAmount, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 170, -1));

        dashboardContribSharesPanel.setBackground(new java.awt.Color(43, 43, 75));
        dashboardContribSharesPanel.setForeground(new java.awt.Color(43, 43, 75));
        dashboardContribSharesPanel.setOpaque(false);
        dashboardContribSharesPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        dashboardContribSharesTotalAmount.setFont(new java.awt.Font("Bebas", 1, 55)); // NOI18N
        dashboardContribSharesTotalAmount.setForeground(new java.awt.Color(210, 210, 215));
        dashboardContribSharesTotalAmount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dashboardContribSharesTotalAmount.setText("p 00.00");
        dashboardContribSharesTotalAmount.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                dashboardContribSharesTotalAmountComponentAdded(evt);
            }
        });
        dashboardContribSharesTotalAmount.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dashboardContribSharesTotalAmountPropertyChange(evt);
            }
        });
        dashboardContribSharesPanel.add(dashboardContribSharesTotalAmount, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, 330, 100));

        jLabel3.setFont(new java.awt.Font("Montserrat Black", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(210, 210, 215));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Total Family Budget Contribution");
        dashboardContribSharesPanel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 360, -1));

        dashboardBudgetConditionMessagePanel.setBackground(new java.awt.Color(210, 210, 215));
        dashboardBudgetConditionMessagePanel.setForeground(new java.awt.Color(43, 43, 75));
        dashboardBudgetConditionMessagePanel.setOpaque(false);
        dashboardBudgetConditionMessagePanel.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dashboardBudgetConditionMessagePanelPropertyChange(evt);
            }
        });

        budgetConditionLabel1.setFont(new java.awt.Font("Bebas", 1, 33)); // NOI18N
        budgetConditionLabel1.setForeground(new java.awt.Color(38, 38, 38));
        budgetConditionLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        budgetConditionLabel1.setText("Budget Condition Message ");
        budgetConditionLabel1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                budgetConditionLabel1PropertyChange(evt);
            }
        });

        javax.swing.GroupLayout dashboardBudgetConditionMessagePanelLayout = new javax.swing.GroupLayout(dashboardBudgetConditionMessagePanel);
        dashboardBudgetConditionMessagePanel.setLayout(dashboardBudgetConditionMessagePanelLayout);
        dashboardBudgetConditionMessagePanelLayout.setHorizontalGroup(
            dashboardBudgetConditionMessagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(budgetConditionLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        dashboardBudgetConditionMessagePanelLayout.setVerticalGroup(
            dashboardBudgetConditionMessagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardBudgetConditionMessagePanelLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(budgetConditionLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(43, 43, 75));
        jPanel1.setForeground(new java.awt.Color(210, 210, 215));
        jPanel1.setOpaque(false);

        jScrollPane2.setOpaque(false);

        expenseTableList.setBackground(new java.awt.Color(43, 43, 75));
        expenseTableList.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(43, 43, 75), 1, true));
        expenseTableList.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        expenseTableList.setForeground(new java.awt.Color(210, 210, 215));
        expenseTableList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item", "Amount", "Type"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        expenseTableList.setToolTipText("");
        expenseTableList.setFillsViewportHeight(true);
        expenseTableList.setGridColor(new java.awt.Color(43, 43, 75));
        expenseTableList.setSelectionBackground(new java.awt.Color(0, 0, 48));
        expenseTableList.setSelectionForeground(new java.awt.Color(210, 210, 215));
        expenseTableList.setShowGrid(true);
        expenseTableList.setShowVerticalLines(false);
        expenseTableList.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(expenseTableList);
        if (expenseTableList.getColumnModel().getColumnCount() > 0) {
            expenseTableList.getColumnModel().getColumn(0).setResizable(false);
            expenseTableList.getColumnModel().getColumn(1).setResizable(false);
            expenseTableList.getColumnModel().getColumn(2).setResizable(false);
        }

        jLabel15.setFont(new java.awt.Font("Montserrat Black", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(210, 210, 215));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel15.setText("Billings & Expenses Table List");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout dashboardBoardLayout = new javax.swing.GroupLayout(dashboardBoard);
        dashboardBoard.setLayout(dashboardBoardLayout);
        dashboardBoardLayout.setHorizontalGroup(
            dashboardBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, dashboardBoardLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(dashboardBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(dashboardBoardLayout.createSequentialGroup()
                        .addComponent(totalBudgetPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(totalAssetsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(dashboardBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dashboardBoardLayout.createSequentialGroup()
                        .addComponent(totalBillingsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(totlalLiabilitiesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(dashboardBudgetConditionMessagePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dashboardContribSharesPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(dashboardBoardLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(dashboardTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        dashboardBoardLayout.setVerticalGroup(
            dashboardBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardBoardLayout.createSequentialGroup()
                .addComponent(dashboardTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dashboardBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(totalBillingsPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                    .addComponent(totalAssetsPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(totalBudgetPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(totlalLiabilitiesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(dashboardBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(dashboardBoardLayout.createSequentialGroup()
                        .addComponent(dashboardBudgetConditionMessagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(dashboardContribSharesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jTabbedPane.addTab("tab1", dashboardBoard);

        membersBoard.setBackground(new java.awt.Color(0, 0, 48));
        membersBoard.setPreferredSize(new java.awt.Dimension(770, 550));

        membersTitle.setFont(new java.awt.Font("Montserrat Black", 0, 60)); // NOI18N
        membersTitle.setForeground(new java.awt.Color(210, 210, 215));
        membersTitle.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        membersTitle.setText("Members");

        ContributionSharePanel.setBackground(new java.awt.Color(43, 43, 75));
        ContributionSharePanel.setForeground(new java.awt.Color(210, 210, 215));
        ContributionSharePanel.setOpaque(false);

        totalAmountContributedText.setFont(new java.awt.Font("Montserrat", 2, 18)); // NOI18N
        totalAmountContributedText.setForeground(new java.awt.Color(210, 210, 215));
        totalAmountContributedText.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        totalAmountContributedText.setText("You contributed a total amount of :  ");

        showUserContrib1.setFont(new java.awt.Font("Bebas", 1, 50)); // NOI18N
        showUserContrib1.setForeground(new java.awt.Color(210, 210, 215));
        showUserContrib1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        showUserContrib1.setText("P 00.00");
        showUserContrib1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                showUserContrib1PropertyChange(evt);
            }
        });

        javax.swing.GroupLayout ContributionSharePanelLayout = new javax.swing.GroupLayout(ContributionSharePanel);
        ContributionSharePanel.setLayout(ContributionSharePanelLayout);
        ContributionSharePanelLayout.setHorizontalGroup(
            ContributionSharePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ContributionSharePanelLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(showUserContrib1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(ContributionSharePanelLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(totalAmountContributedText)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        ContributionSharePanelLayout.setVerticalGroup(
            ContributionSharePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ContributionSharePanelLayout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addComponent(totalAmountContributedText)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(showUserContrib1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        dashboardContribSharesPanel1.setBackground(new java.awt.Color(43, 43, 75));
        dashboardContribSharesPanel1.setForeground(new java.awt.Color(43, 43, 75));
        dashboardContribSharesPanel1.setOpaque(false);
        dashboardContribSharesPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        dashboardContribSharesTotalAmount1.setFont(new java.awt.Font("Bebas", 1, 50)); // NOI18N
        dashboardContribSharesTotalAmount1.setForeground(new java.awt.Color(210, 210, 215));
        dashboardContribSharesTotalAmount1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        dashboardContribSharesTotalAmount1.setText("p 00.00");
        dashboardContribSharesTotalAmount1.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                dashboardContribSharesTotalAmount1ComponentAdded(evt);
            }
        });
        dashboardContribSharesTotalAmount1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dashboardContribSharesTotalAmount1PropertyChange(evt);
            }
        });
        dashboardContribSharesPanel1.add(dashboardContribSharesTotalAmount1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, 330, 50));

        jLabel4.setFont(new java.awt.Font("Montserrat Black", 1, 17)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(210, 210, 215));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Total Family Budget Contribution");
        dashboardContribSharesPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 360, -1));

        jPanel3.setBackground(new java.awt.Color(43, 43, 75));
        jPanel3.setOpaque(false);

        membersListTable.setBackground(new java.awt.Color(43, 43, 75));
        membersListTable.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(43, 43, 75), 1, true));
        membersListTable.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        membersListTable.setForeground(new java.awt.Color(210, 210, 215));
        membersListTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Contribution"
            }
        ));
        membersListTable.setFillsViewportHeight(true);
        membersListTable.setSelectionBackground(new java.awt.Color(0, 0, 48));
        membersListTable.setSelectionForeground(new java.awt.Color(210, 210, 215));
        membersListTable.setShowGrid(false);
        membersListScrollPane.setViewportView(membersListTable);
        if (membersListTable.getColumnModel().getColumnCount() > 0) {
            membersListTable.getColumnModel().getColumn(0).setResizable(false);
            membersListTable.getColumnModel().getColumn(1).setResizable(false);
        }

        jLabel5.setFont(new java.awt.Font("Montserrat Black", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(210, 210, 215));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("Member Contribution Table List");

        removeNameLabel.setFont(new java.awt.Font("Montserrat", 1, 12)); // NOI18N
        removeNameLabel.setForeground(new java.awt.Color(210, 210, 215));
        removeNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        removeNameLabel.setText("Remove Member:");

        memberRemoveTextfield.setBackground(new java.awt.Color(210, 210, 215));
        memberRemoveTextfield.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        memberRemoveTextfield.setForeground(new java.awt.Color(38, 38, 38));
        memberRemoveTextfield.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        memberRemoveTextfield.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        memberRemoveTextfield.setName("Enter Family Name"); // NOI18N
        memberRemoveTextfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                memberRemoveTextfieldActionPerformed(evt);
            }
        });

        memberRemoveButton.setBackground(new java.awt.Color(230, 68, 86));
        memberRemoveButton.setFont(new java.awt.Font("Montserrat Black", 2, 14)); // NOI18N
        memberRemoveButton.setForeground(new java.awt.Color(38, 38, 38));
        memberRemoveButton.setText("Remove");
        memberRemoveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                memberRemoveButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(removeNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(memberRemoveTextfield))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(membersListScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(memberRemoveButton, javax.swing.GroupLayout.Alignment.TRAILING)))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(membersListScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(removeNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(memberRemoveTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(memberRemoveButton)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(43, 43, 75));
        jPanel4.setOpaque(false);

        jLabel6.setFont(new java.awt.Font("Montserrat Black", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(210, 210, 215));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Balance Sheet");

        totalBudgetAmount1.setFont(new java.awt.Font("Bebas", 1, 50)); // NOI18N
        totalBudgetAmount1.setForeground(new java.awt.Color(210, 210, 215));
        totalBudgetAmount1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        totalBudgetAmount1.setText("P 00.00");
        totalBudgetAmount1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                totalBudgetAmount1PropertyChange(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(totalBudgetAmount1, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(totalBudgetAmount1)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout membersBoardLayout = new javax.swing.GroupLayout(membersBoard);
        membersBoard.setLayout(membersBoardLayout);
        membersBoardLayout.setHorizontalGroup(
            membersBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(membersBoardLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(membersBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ContributionSharePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dashboardContribSharesPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(membersBoardLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(membersTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        membersBoardLayout.setVerticalGroup(
            membersBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(membersBoardLayout.createSequentialGroup()
                .addComponent(membersTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dashboardContribSharesPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(ContributionSharePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(membersBoardLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane.addTab("tab2", membersBoard);

        statsBoard.setBackground(new java.awt.Color(0, 0, 48));
        statsBoard.setForeground(new java.awt.Color(210, 210, 215));
        statsBoard.setPreferredSize(new java.awt.Dimension(770, 550));

        statsTitle.setFont(new java.awt.Font("Montserrat Black", 0, 60)); // NOI18N
        statsTitle.setForeground(new java.awt.Color(210, 210, 215));
        statsTitle.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        statsTitle.setText("Stats");

        totalAmountReport.setBackground(new java.awt.Color(43, 43, 75));
        totalAmountReport.setOpaque(false);

        jLabel10.setFont(new java.awt.Font("Bebas", 1, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(210, 210, 215));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("TOTAL BUDGET:");

        jLabel12.setFont(new java.awt.Font("Bebas", 1, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(210, 210, 215));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("BALANCE SHEET:");

        totalBudgetAmount3.setFont(new java.awt.Font("Bebas", 1, 36)); // NOI18N
        totalBudgetAmount3.setForeground(new java.awt.Color(230, 68, 86));
        totalBudgetAmount3.setText("P 00.00");
        totalBudgetAmount3.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                totalBudgetAmount3PropertyChange(evt);
            }
        });

        dashboardContribSharesTotalAmount2.setFont(new java.awt.Font("Bebas", 1, 36)); // NOI18N
        dashboardContribSharesTotalAmount2.setForeground(new java.awt.Color(89, 228, 147));
        dashboardContribSharesTotalAmount2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        dashboardContribSharesTotalAmount2.setText("p 00.00");
        dashboardContribSharesTotalAmount2.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                dashboardContribSharesTotalAmount2ComponentAdded(evt);
            }
        });
        dashboardContribSharesTotalAmount2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dashboardContribSharesTotalAmount2PropertyChange(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Montserrat", 3, 16)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(210, 210, 215));
        jLabel14.setText("Computation for Total Budget Spent");

        javax.swing.GroupLayout totalAmountReportLayout = new javax.swing.GroupLayout(totalAmountReport);
        totalAmountReport.setLayout(totalAmountReportLayout);
        totalAmountReportLayout.setHorizontalGroup(
            totalAmountReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(totalAmountReportLayout.createSequentialGroup()
                .addGroup(totalAmountReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(totalAmountReportLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, totalAmountReportLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(totalAmountReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(totalAmountReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(totalBudgetAmount3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dashboardContribSharesTotalAmount2, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        totalAmountReportLayout.setVerticalGroup(
            totalAmountReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(totalAmountReportLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(totalAmountReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dashboardContribSharesTotalAmount2))
                .addGap(18, 18, 18)
                .addGroup(totalAmountReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(totalBudgetAmount3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        budgetConditionMessagePanel.setBackground(new java.awt.Color(210, 210, 215));
        budgetConditionMessagePanel.setOpaque(false);
        budgetConditionMessagePanel.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                budgetConditionMessagePanelPropertyChange(evt);
            }
        });

        budgetConditionLabel.setFont(new java.awt.Font("Bebas", 1, 48)); // NOI18N
        budgetConditionLabel.setForeground(new java.awt.Color(38, 38, 38));
        budgetConditionLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        budgetConditionLabel.setText("Budget Condition Message ");
        budgetConditionLabel.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                budgetConditionLabelPropertyChange(evt);
            }
        });

        javax.swing.GroupLayout budgetConditionMessagePanelLayout = new javax.swing.GroupLayout(budgetConditionMessagePanel);
        budgetConditionMessagePanel.setLayout(budgetConditionMessagePanelLayout);
        budgetConditionMessagePanelLayout.setHorizontalGroup(
            budgetConditionMessagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(budgetConditionMessagePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(budgetConditionLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        budgetConditionMessagePanelLayout.setVerticalGroup(
            budgetConditionMessagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, budgetConditionMessagePanelLayout.createSequentialGroup()
                .addComponent(budgetConditionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(43, 43, 75));
        jPanel5.setOpaque(false);

        jLabel2.setFont(new java.awt.Font("Bebas", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(210, 210, 215));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("TOTAL ASSETS:");

        jLabel7.setFont(new java.awt.Font("Bebas", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(210, 210, 215));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("TOTAL BILLINGS:");

        jLabel9.setFont(new java.awt.Font("Bebas", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(210, 210, 215));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("TOTAL LIABILITIES:");

        totalAssetsAmount2.setFont(new java.awt.Font("Bebas", 1, 36)); // NOI18N
        totalAssetsAmount2.setForeground(new java.awt.Color(89, 228, 147));
        totalAssetsAmount2.setText("p 00.00");
        totalAssetsAmount2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                totalAssetsAmount2PropertyChange(evt);
            }
        });

        totalBillingsAmount2.setFont(new java.awt.Font("Bebas", 1, 36)); // NOI18N
        totalBillingsAmount2.setForeground(new java.awt.Color(230, 68, 86));
        totalBillingsAmount2.setText("p 00.00");
        totalBillingsAmount2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                totalBillingsAmount2PropertyChange(evt);
            }
        });

        totalLiabilitiesAmount2.setFont(new java.awt.Font("Bebas", 1, 36)); // NOI18N
        totalLiabilitiesAmount2.setForeground(new java.awt.Color(230, 68, 86));
        totalLiabilitiesAmount2.setText("P 00.00");
        totalLiabilitiesAmount2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                totalLiabilitiesAmount2PropertyChange(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Montserrat", 3, 16)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(210, 210, 215));
        jLabel13.setText("Computation for Total Balance Sheet");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(totalLiabilitiesAmount2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(totalBillingsAmount2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(totalAssetsAmount2, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalAssetsAmount2))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(totalBillingsAmount2))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(19, 19, 19)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalLiabilitiesAmount2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(236, 172, 116));
        jPanel6.setOpaque(false);

        jLabel8.setFont(new java.awt.Font("Bebas", 3, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(38, 38, 38));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("BALANCE SHEET:");

        totalBudgetAmount2.setFont(new java.awt.Font("Bebas", 1, 48)); // NOI18N
        totalBudgetAmount2.setForeground(new java.awt.Color(38, 38, 38));
        totalBudgetAmount2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        totalBudgetAmount2.setText("P 00.00");
        totalBudgetAmount2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                totalBudgetAmount2PropertyChange(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(totalBudgetAmount2, javax.swing.GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(totalBudgetAmount2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        jPanel7.setBackground(new java.awt.Color(236, 172, 116));
        jPanel7.setOpaque(false);

        jLabel11.setFont(new java.awt.Font("Bebas", 3, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(38, 38, 38));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("TOTAL BUDGET SPENT:");

        budgetSpentTotal.setFont(new java.awt.Font("Bebas", 1, 48)); // NOI18N
        budgetSpentTotal.setForeground(new java.awt.Color(38, 38, 38));
        budgetSpentTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        budgetSpentTotal.setText("P 00.00");
        budgetSpentTotal.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                budgetSpentTotalPropertyChange(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(budgetSpentTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(budgetSpentTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout statsBoardLayout = new javax.swing.GroupLayout(statsBoard);
        statsBoard.setLayout(statsBoardLayout);
        statsBoardLayout.setHorizontalGroup(
            statsBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(budgetConditionMessagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statsBoardLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(statsTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statsBoardLayout.createSequentialGroup()
                .addGroup(statsBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(statsBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(totalAmountReport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        statsBoardLayout.setVerticalGroup(
            statsBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(statsBoardLayout.createSequentialGroup()
                .addComponent(statsTitle)
                .addGap(9, 9, 9)
                .addComponent(budgetConditionMessagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(statsBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(totalAmountReport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(statsBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jTabbedPane.addTab("tab3", statsBoard);

        assetsBoard.setBackground(new java.awt.Color(0, 0, 48));
        assetsBoard.setForeground(new java.awt.Color(210, 210, 215));
        assetsBoard.setPreferredSize(new java.awt.Dimension(770, 550));

        assetsTitle.setFont(new java.awt.Font("Montserrat Black", 0, 60)); // NOI18N
        assetsTitle.setForeground(new java.awt.Color(210, 210, 215));
        assetsTitle.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        assetsTitle.setText("Assets");

        assetsStatusPanel.setBackground(new java.awt.Color(89, 228, 147));
        assetsStatusPanel.setMaximumSize(new java.awt.Dimension(331, 124));
        assetsStatusPanel.setOpaque(false);
        assetsStatusPanel.setPreferredSize(new java.awt.Dimension(330, 125));

        totalAssetsAmount1.setFont(new java.awt.Font("Bebas", 1, 50)); // NOI18N
        totalAssetsAmount1.setForeground(new java.awt.Color(38, 38, 38));
        totalAssetsAmount1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalAssetsAmount1.setText("p 00.00");
        totalAssetsAmount1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                totalAssetsAmount1PropertyChange(evt);
            }
        });

        javax.swing.GroupLayout assetsStatusPanelLayout = new javax.swing.GroupLayout(assetsStatusPanel);
        assetsStatusPanel.setLayout(assetsStatusPanelLayout);
        assetsStatusPanelLayout.setHorizontalGroup(
            assetsStatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(assetsStatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, assetsStatusPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(totalAssetsAmount1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        assetsStatusPanelLayout.setVerticalGroup(
            assetsStatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 139, Short.MAX_VALUE)
            .addGroup(assetsStatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, assetsStatusPanelLayout.createSequentialGroup()
                    .addContainerGap(39, Short.MAX_VALUE)
                    .addComponent(totalAssetsAmount1)
                    .addContainerGap(40, Short.MAX_VALUE)))
        );

        assetsChartPanel.setBackground(new java.awt.Color(43, 43, 75));
        assetsChartPanel.setOpaque(false);

        assetTypeLabel.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        assetTypeLabel.setForeground(new java.awt.Color(210, 210, 215));
        assetTypeLabel.setText("Type:");

        assetItemLabel.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        assetItemLabel.setForeground(new java.awt.Color(210, 210, 215));
        assetItemLabel.setText("Item:");

        assetValueLabel.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        assetValueLabel.setForeground(new java.awt.Color(210, 210, 215));
        assetValueLabel.setText("Value:");

        assetsItemTextfield.setBackground(new java.awt.Color(210, 210, 215));
        assetsItemTextfield.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        assetsItemTextfield.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        assetsItemTextfield.setForeground(new java.awt.Color(38, 38, 38));
        assetsItemTextfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assetsItemTextfieldActionPerformed(evt);
            }
        });

        assetsValueTextfield.setBackground(new java.awt.Color(210, 210, 215));
        assetsValueTextfield.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        assetsValueTextfield.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        assetsValueTextfield.setForeground(new java.awt.Color(38, 38, 38));
        assetsValueTextfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assetsValueTextfieldActionPerformed(evt);
            }
        });

        assetsTypeTextfield.setBackground(new java.awt.Color(210, 210, 215));
        assetsTypeTextfield.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        assetsTypeTextfield.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        assetsTypeTextfield.setForeground(new java.awt.Color(38, 38, 38));
        assetsTypeTextfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assetsTypeTextfieldActionPerformed(evt);
            }
        });

        assetsRemoveButton.setBackground(new java.awt.Color(230, 68, 86));
        assetsRemoveButton.setFont(new java.awt.Font("Montserrat Black", 2, 14)); // NOI18N
        assetsRemoveButton.setForeground(new java.awt.Color(38, 38, 38));
        assetsRemoveButton.setText("Remove");
        assetsRemoveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assetsRemoveButtonActionPerformed(evt);
            }
        });

        assetsAddButton.setBackground(new java.awt.Color(89, 228, 147));
        assetsAddButton.setFont(new java.awt.Font("Montserrat Black", 2, 14)); // NOI18N
        assetsAddButton.setForeground(new java.awt.Color(38, 38, 38));
        assetsAddButton.setText("Add");
        assetsAddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assetsAddButtonActionPerformed(evt);
            }
        });

        profileAccountLabel1.setFont(new java.awt.Font("Montserrat Black", 0, 18)); // NOI18N
        profileAccountLabel1.setForeground(new java.awt.Color(210, 210, 215));
        profileAccountLabel1.setText("Edit Assets Table List");

        javax.swing.GroupLayout assetsChartPanelLayout = new javax.swing.GroupLayout(assetsChartPanel);
        assetsChartPanel.setLayout(assetsChartPanelLayout);
        assetsChartPanelLayout.setHorizontalGroup(
            assetsChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(assetsChartPanelLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(assetsChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(assetsChartPanelLayout.createSequentialGroup()
                        .addComponent(assetsRemoveButton)
                        .addGap(18, 18, 18)
                        .addComponent(assetsAddButton, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(assetsChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(profileAccountLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(assetsChartPanelLayout.createSequentialGroup()
                            .addGroup(assetsChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(assetValueLabel)
                                .addComponent(assetItemLabel)
                                .addComponent(assetTypeLabel))
                            .addGap(22, 22, 22)
                            .addGroup(assetsChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(assetsItemTextfield, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(assetsValueTextfield, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(assetsTypeTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(46, Short.MAX_VALUE))
        );
        assetsChartPanelLayout.setVerticalGroup(
            assetsChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, assetsChartPanelLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(profileAccountLabel1)
                .addGap(18, 18, 18)
                .addGroup(assetsChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(assetsItemTextfield, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(assetItemLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(assetsChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(assetsValueTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(assetValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(assetsChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(assetTypeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(assetsTypeTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(assetsChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(assetsRemoveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(assetsAddButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(56, 56, 56))
        );

        jPanel8.setBackground(new java.awt.Color(43, 43, 75));
        jPanel8.setOpaque(false);

        jLabel17.setFont(new java.awt.Font("Montserrat Black", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(210, 210, 215));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setText("Assets Table List");

        assetsListScrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        assetListTable.setBackground(new java.awt.Color(43, 43, 75));
        assetListTable.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        assetListTable.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        assetListTable.setForeground(new java.awt.Color(210, 210, 215));
        assetListTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item", "Value", "Type"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        assetListTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        assetListTable.setFillsViewportHeight(true);
        assetListTable.setGridColor(new java.awt.Color(43, 43, 75));
        assetListTable.setSelectionBackground(new java.awt.Color(0, 0, 48));
        assetListTable.setSelectionForeground(new java.awt.Color(210, 210, 215));
        assetListTable.setShowGrid(true);
        assetsListScrollPane.setViewportView(assetListTable);
        if (assetListTable.getColumnModel().getColumnCount() > 0) {
            assetListTable.getColumnModel().getColumn(0).setResizable(false);
            assetListTable.getColumnModel().getColumn(1).setResizable(false);
            assetListTable.getColumnModel().getColumn(2).setResizable(false);
        }

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addComponent(assetsListScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(assetsListScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );

        javax.swing.GroupLayout assetsBoardLayout = new javax.swing.GroupLayout(assetsBoard);
        assetsBoard.setLayout(assetsBoardLayout);
        assetsBoardLayout.setHorizontalGroup(
            assetsBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(assetsBoardLayout.createSequentialGroup()
                .addGroup(assetsBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(assetsStatusPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE)
                    .addComponent(assetsChartPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, assetsBoardLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(assetsTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        assetsBoardLayout.setVerticalGroup(
            assetsBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(assetsBoardLayout.createSequentialGroup()
                .addComponent(assetsTitle)
                .addGap(18, 18, 18)
                .addGroup(assetsBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(assetsBoardLayout.createSequentialGroup()
                        .addComponent(assetsStatusPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(assetsChartPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jTabbedPane.addTab("tab4", assetsBoard);

        liabilitiesBoard.setBackground(new java.awt.Color(0, 0, 48));
        liabilitiesBoard.setForeground(new java.awt.Color(210, 210, 215));
        liabilitiesBoard.setPreferredSize(new java.awt.Dimension(770, 550));
        liabilitiesBoard.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        liabilitiesTitle.setFont(new java.awt.Font("Montserrat Black", 0, 60)); // NOI18N
        liabilitiesTitle.setForeground(new java.awt.Color(210, 210, 215));
        liabilitiesTitle.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        liabilitiesTitle.setText("Liabilities");
        liabilitiesBoard.add(liabilitiesTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 0, 540, 70));

        liabilitiesStatusPanel.setBackground(new java.awt.Color(230, 68, 86));
        liabilitiesStatusPanel.setOpaque(false);
        liabilitiesStatusPanel.setPreferredSize(new java.awt.Dimension(330, 125));

        totalLiabilitiesAmount1.setFont(new java.awt.Font("Bebas", 1, 50)); // NOI18N
        totalLiabilitiesAmount1.setForeground(new java.awt.Color(38, 38, 38));
        totalLiabilitiesAmount1.setText("P 00.00");
        totalLiabilitiesAmount1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                totalLiabilitiesAmount1PropertyChange(evt);
            }
        });

        javax.swing.GroupLayout liabilitiesStatusPanelLayout = new javax.swing.GroupLayout(liabilitiesStatusPanel);
        liabilitiesStatusPanel.setLayout(liabilitiesStatusPanelLayout);
        liabilitiesStatusPanelLayout.setHorizontalGroup(
            liabilitiesStatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 410, Short.MAX_VALUE)
            .addGroup(liabilitiesStatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(liabilitiesStatusPanelLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(totalLiabilitiesAmount1)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        liabilitiesStatusPanelLayout.setVerticalGroup(
            liabilitiesStatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
            .addGroup(liabilitiesStatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(liabilitiesStatusPanelLayout.createSequentialGroup()
                    .addGap(0, 40, Short.MAX_VALUE)
                    .addComponent(totalLiabilitiesAmount1)
                    .addGap(0, 40, Short.MAX_VALUE)))
        );

        liabilitiesBoard.add(liabilitiesStatusPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 410, 140));

        liabilitiesChartPanel.setBackground(new java.awt.Color(43, 43, 75));
        liabilitiesChartPanel.setMaximumSize(new java.awt.Dimension(331, 124));
        liabilitiesChartPanel.setOpaque(false);
        liabilitiesChartPanel.setPreferredSize(new java.awt.Dimension(330, 125));

        liabilitiesTypeLabel.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        liabilitiesTypeLabel.setForeground(new java.awt.Color(210, 210, 215));
        liabilitiesTypeLabel.setText("Type:");

        liabilitiesItemLabel.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        liabilitiesItemLabel.setForeground(new java.awt.Color(210, 210, 215));
        liabilitiesItemLabel.setText("Item:");

        liabilitiesValueLabel.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        liabilitiesValueLabel.setForeground(new java.awt.Color(210, 210, 215));
        liabilitiesValueLabel.setText("Value:");

        liabilitiesItemTextfield.setBackground(new java.awt.Color(210, 210, 215));
        liabilitiesItemTextfield.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        liabilitiesItemTextfield.setForeground(new java.awt.Color(38, 38, 38));
        liabilitiesItemTextfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                liabilitiesItemTextfieldActionPerformed(evt);
            }
        });

        liabilitiesValueTextfield.setBackground(new java.awt.Color(210, 210, 215));
        liabilitiesValueTextfield.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        liabilitiesValueTextfield.setForeground(new java.awt.Color(38, 38, 38));
        liabilitiesValueTextfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                liabilitiesValueTextfieldActionPerformed(evt);
            }
        });

        liabilitiesTypeTextfield.setBackground(new java.awt.Color(210, 210, 215));
        liabilitiesTypeTextfield.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        liabilitiesTypeTextfield.setForeground(new java.awt.Color(38, 38, 38));
        liabilitiesTypeTextfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                liabilitiesTypeTextfieldActionPerformed(evt);
            }
        });

        liabilitiesAddButton.setBackground(new java.awt.Color(89, 228, 147));
        liabilitiesAddButton.setFont(new java.awt.Font("Montserrat Black", 2, 12)); // NOI18N
        liabilitiesAddButton.setForeground(new java.awt.Color(38, 38, 38));
        liabilitiesAddButton.setText("Add");
        liabilitiesAddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                liabilitiesAddButtonActionPerformed(evt);
            }
        });

        liabilitiesRemoveButton.setBackground(new java.awt.Color(230, 68, 86));
        liabilitiesRemoveButton.setFont(new java.awt.Font("Montserrat Black", 2, 14)); // NOI18N
        liabilitiesRemoveButton.setForeground(new java.awt.Color(38, 38, 38));
        liabilitiesRemoveButton.setText("Remove");
        liabilitiesRemoveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                liabilitiesRemoveButtonActionPerformed(evt);
            }
        });

        profileAccountLabel2.setFont(new java.awt.Font("Montserrat Black", 0, 18)); // NOI18N
        profileAccountLabel2.setForeground(new java.awt.Color(210, 210, 215));
        profileAccountLabel2.setText("Edit Liabilities Table List");

        javax.swing.GroupLayout liabilitiesChartPanelLayout = new javax.swing.GroupLayout(liabilitiesChartPanel);
        liabilitiesChartPanel.setLayout(liabilitiesChartPanelLayout);
        liabilitiesChartPanelLayout.setHorizontalGroup(
            liabilitiesChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(liabilitiesChartPanelLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(liabilitiesChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, liabilitiesChartPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(liabilitiesRemoveButton)
                        .addGap(18, 18, 18)
                        .addComponent(liabilitiesAddButton))
                    .addGroup(liabilitiesChartPanelLayout.createSequentialGroup()
                        .addGroup(liabilitiesChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(liabilitiesChartPanelLayout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(liabilitiesTypeLabel))
                            .addGroup(liabilitiesChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(liabilitiesChartPanelLayout.createSequentialGroup()
                                    .addGap(9, 9, 9)
                                    .addComponent(liabilitiesItemLabel))
                                .addComponent(liabilitiesValueLabel)))
                        .addGap(17, 17, 17)
                        .addGroup(liabilitiesChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(liabilitiesItemTextfield, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
                            .addComponent(liabilitiesValueTextfield, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(liabilitiesTypeTextfield, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(liabilitiesChartPanelLayout.createSequentialGroup()
                        .addComponent(profileAccountLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(40, 40, 40))
        );
        liabilitiesChartPanelLayout.setVerticalGroup(
            liabilitiesChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(liabilitiesChartPanelLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(profileAccountLabel2)
                .addGap(18, 18, 18)
                .addGroup(liabilitiesChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(liabilitiesItemTextfield, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(liabilitiesItemLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(liabilitiesChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(liabilitiesValueTextfield, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(liabilitiesValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(liabilitiesChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(liabilitiesTypeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(liabilitiesTypeTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(liabilitiesChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(liabilitiesAddButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(liabilitiesRemoveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        liabilitiesBoard.add(liabilitiesChartPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 410, 290));

        jPanel9.setBackground(new java.awt.Color(43, 43, 75));
        jPanel9.setOpaque(false);

        jLabel18.setFont(new java.awt.Font("Montserrat Black", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(210, 210, 215));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel18.setText("Liabilities Table List");

        liabilitiesListTable.setBackground(new java.awt.Color(43, 43, 75));
        liabilitiesListTable.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(43, 43, 75), 1, true));
        liabilitiesListTable.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        liabilitiesListTable.setForeground(new java.awt.Color(210, 210, 215));
        liabilitiesListTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item", "Value", "Type"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        liabilitiesListTable.setFillsViewportHeight(true);
        liabilitiesListTable.setGridColor(new java.awt.Color(43, 43, 75));
        liabilitiesListTable.setSelectionBackground(new java.awt.Color(0, 0, 48));
        liabilitiesListTable.setSelectionForeground(new java.awt.Color(210, 210, 215));
        liabilitiesListTable.setShowGrid(true);
        jScrollPane1.setViewportView(liabilitiesListTable);
        if (liabilitiesListTable.getColumnModel().getColumnCount() > 0) {
            liabilitiesListTable.getColumnModel().getColumn(0).setResizable(false);
            liabilitiesListTable.getColumnModel().getColumn(1).setResizable(false);
            liabilitiesListTable.getColumnModel().getColumn(2).setResizable(false);
        }

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        liabilitiesBoard.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 90, 400, 450));

        jTabbedPane.addTab("tab5", liabilitiesBoard);

        billingsBoard.setBackground(new java.awt.Color(0, 0, 48));
        billingsBoard.setForeground(new java.awt.Color(210, 210, 215));
        billingsBoard.setMaximumSize(new java.awt.Dimension(770, 550));
        billingsBoard.setMinimumSize(new java.awt.Dimension(770, 550));
        billingsBoard.setPreferredSize(new java.awt.Dimension(770, 550));

        billingsTitle.setFont(new java.awt.Font("Montserrat Black", 0, 60)); // NOI18N
        billingsTitle.setForeground(new java.awt.Color(210, 210, 215));
        billingsTitle.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        billingsTitle.setText("Billings & Expenses");

        billingsStatusPanel.setBackground(new java.awt.Color(230, 68, 86));
        billingsStatusPanel.setOpaque(false);
        billingsStatusPanel.setPreferredSize(new java.awt.Dimension(330, 125));

        totalBillingsAmount1.setFont(new java.awt.Font("Bebas", 1, 50)); // NOI18N
        totalBillingsAmount1.setForeground(new java.awt.Color(38, 38, 38));
        totalBillingsAmount1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalBillingsAmount1.setText("p 00.00");
        totalBillingsAmount1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                totalBillingsAmount1PropertyChange(evt);
            }
        });

        javax.swing.GroupLayout billingsStatusPanelLayout = new javax.swing.GroupLayout(billingsStatusPanel);
        billingsStatusPanel.setLayout(billingsStatusPanelLayout);
        billingsStatusPanelLayout.setHorizontalGroup(
            billingsStatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(billingsStatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, billingsStatusPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(totalBillingsAmount1, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        billingsStatusPanelLayout.setVerticalGroup(
            billingsStatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 139, Short.MAX_VALUE)
            .addGroup(billingsStatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, billingsStatusPanelLayout.createSequentialGroup()
                    .addContainerGap(39, Short.MAX_VALUE)
                    .addComponent(totalBillingsAmount1)
                    .addContainerGap(40, Short.MAX_VALUE)))
        );

        billingsChartPanel.setBackground(new java.awt.Color(43, 43, 75));
        billingsChartPanel.setOpaque(false);

        billingsItemTextfield.setBackground(new java.awt.Color(210, 210, 215));
        billingsItemTextfield.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        billingsItemTextfield.setForeground(new java.awt.Color(38, 38, 38));
        billingsItemTextfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                billingsItemTextfieldActionPerformed(evt);
            }
        });

        billingsItemLabel.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        billingsItemLabel.setForeground(new java.awt.Color(210, 210, 215));
        billingsItemLabel.setText("Item:");

        billingsValueLabel.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        billingsValueLabel.setForeground(new java.awt.Color(210, 210, 215));
        billingsValueLabel.setText("Value:");

        billingsValueTextfield.setBackground(new java.awt.Color(210, 210, 215));
        billingsValueTextfield.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        billingsValueTextfield.setForeground(new java.awt.Color(38, 38, 38));

        billingsTypeTextfield.setBackground(new java.awt.Color(210, 210, 215));
        billingsTypeTextfield.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        billingsTypeTextfield.setForeground(new java.awt.Color(38, 38, 38));

        billingsTypeLabel.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        billingsTypeLabel.setForeground(new java.awt.Color(210, 210, 215));
        billingsTypeLabel.setText("Type:");

        billingsRemoveButton.setBackground(new java.awt.Color(230, 68, 86));
        billingsRemoveButton.setFont(new java.awt.Font("Montserrat Black", 2, 14)); // NOI18N
        billingsRemoveButton.setForeground(new java.awt.Color(38, 38, 38));
        billingsRemoveButton.setText("Remove");
        billingsRemoveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                billingsRemoveButtonActionPerformed(evt);
            }
        });

        billingsAddButton.setBackground(new java.awt.Color(89, 228, 147));
        billingsAddButton.setFont(new java.awt.Font("Montserrat Black", 2, 14)); // NOI18N
        billingsAddButton.setForeground(new java.awt.Color(38, 38, 38));
        billingsAddButton.setText("Add");
        billingsAddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                billingsAddButtonActionPerformed(evt);
            }
        });

        profileAccountLabel3.setFont(new java.awt.Font("Montserrat Black", 0, 18)); // NOI18N
        profileAccountLabel3.setForeground(new java.awt.Color(210, 210, 215));
        profileAccountLabel3.setText("Edit Billings & Expenses Table List");

        javax.swing.GroupLayout billingsChartPanelLayout = new javax.swing.GroupLayout(billingsChartPanel);
        billingsChartPanel.setLayout(billingsChartPanelLayout);
        billingsChartPanelLayout.setHorizontalGroup(
            billingsChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(billingsChartPanelLayout.createSequentialGroup()
                .addGroup(billingsChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(billingsChartPanelLayout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(billingsChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(billingsChartPanelLayout.createSequentialGroup()
                                .addComponent(billingsRemoveButton)
                                .addGap(18, 18, 18)
                                .addComponent(billingsAddButton))
                            .addGroup(billingsChartPanelLayout.createSequentialGroup()
                                .addGroup(billingsChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(billingsItemLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(billingsValueLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(billingsTypeLabel, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(19, 19, 19)
                                .addGroup(billingsChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(billingsValueTextfield, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                                    .addComponent(billingsTypeTextfield, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(billingsItemTextfield, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(billingsChartPanelLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(profileAccountLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        billingsChartPanelLayout.setVerticalGroup(
            billingsChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, billingsChartPanelLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(profileAccountLabel3)
                .addGap(18, 18, 18)
                .addGroup(billingsChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(billingsItemTextfield, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(billingsItemLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(billingsChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(billingsValueTextfield, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(billingsValueLabel))
                .addGap(18, 18, 18)
                .addGroup(billingsChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(billingsTypeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(billingsTypeTextfield, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(billingsChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(billingsAddButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(billingsRemoveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );

        jPanel10.setBackground(new java.awt.Color(43, 43, 75));
        jPanel10.setOpaque(false);

        jLabel19.setFont(new java.awt.Font("Montserrat Black", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(210, 210, 215));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel19.setText("Billings & Expenses Table List");

        expenseTableList1.setBackground(new java.awt.Color(43, 43, 75));
        expenseTableList1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(43, 43, 75), 1, true));
        expenseTableList1.setFont(new java.awt.Font("Roboto Bk", 0, 14)); // NOI18N
        expenseTableList1.setForeground(new java.awt.Color(210, 210, 215));
        expenseTableList1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item", "Amount", "Type"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        expenseTableList1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        expenseTableList1.setFillsViewportHeight(true);
        expenseTableList1.setGridColor(new java.awt.Color(43, 43, 75));
        expenseTableList1.setSelectionBackground(new java.awt.Color(0, 0, 48));
        expenseTableList1.setSelectionForeground(new java.awt.Color(210, 210, 215));
        expenseTableList1.setShowGrid(true);
        jScrollPane3.setViewportView(expenseTableList1);
        if (expenseTableList1.getColumnModel().getColumnCount() > 0) {
            expenseTableList1.getColumnModel().getColumn(0).setResizable(false);
            expenseTableList1.getColumnModel().getColumn(1).setResizable(false);
            expenseTableList1.getColumnModel().getColumn(2).setResizable(false);
        }

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout billingsBoardLayout = new javax.swing.GroupLayout(billingsBoard);
        billingsBoard.setLayout(billingsBoardLayout);
        billingsBoardLayout.setHorizontalGroup(
            billingsBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(billingsBoardLayout.createSequentialGroup()
                .addGroup(billingsBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(billingsStatusPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
                    .addComponent(billingsChartPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, billingsBoardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(billingsTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 801, Short.MAX_VALUE)
                .addContainerGap())
        );
        billingsBoardLayout.setVerticalGroup(
            billingsBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(billingsBoardLayout.createSequentialGroup()
                .addComponent(billingsTitle)
                .addGap(14, 14, 14)
                .addGroup(billingsBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(billingsBoardLayout.createSequentialGroup()
                        .addComponent(billingsStatusPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(billingsChartPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jTabbedPane.addTab("tab6", billingsBoard);

        settingsBoard.setBackground(new java.awt.Color(0, 0, 48));
        settingsBoard.setForeground(new java.awt.Color(210, 210, 215));
        settingsBoard.setMaximumSize(new java.awt.Dimension(770, 550));
        settingsBoard.setMinimumSize(new java.awt.Dimension(770, 550));
        settingsBoard.setPreferredSize(new java.awt.Dimension(770, 550));

        settingsTitle.setFont(new java.awt.Font("Montserrat Black", 0, 60)); // NOI18N
        settingsTitle.setForeground(new java.awt.Color(210, 210, 215));
        settingsTitle.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        settingsTitle.setText("Settings");

        jLabel20.setFont(new java.awt.Font("Roboto Bk", 0, 150)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(210, 210, 215));
        jLabel20.setText("=(");

        jLabel21.setFont(new java.awt.Font("Roboto Bk", 0, 150)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(210, 210, 215));
        jLabel21.setText("Sorry...");

        jLabel22.setFont(new java.awt.Font("Roboto Bk", 0, 36)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(210, 210, 215));
        jLabel22.setText("This page is still under development.");

        javax.swing.GroupLayout settingsBoardLayout = new javax.swing.GroupLayout(settingsBoard);
        settingsBoard.setLayout(settingsBoardLayout);
        settingsBoardLayout.setHorizontalGroup(
            settingsBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(settingsBoardLayout.createSequentialGroup()
                .addGroup(settingsBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, settingsBoardLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(settingsTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(settingsBoardLayout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 537, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 10, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, settingsBoardLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 626, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(84, 84, 84))
        );
        settingsBoardLayout.setVerticalGroup(
            settingsBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(settingsBoardLayout.createSequentialGroup()
                .addComponent(settingsTitle)
                .addGap(99, 99, 99)
                .addGroup(settingsBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 128, Short.MAX_VALUE))
        );

        jTabbedPane.addTab("tab7", settingsBoard);

        helpBoard.setBackground(new java.awt.Color(0, 0, 48));
        helpBoard.setForeground(new java.awt.Color(210, 210, 215));
        helpBoard.setMaximumSize(new java.awt.Dimension(770, 550));
        helpBoard.setMinimumSize(new java.awt.Dimension(770, 550));
        helpBoard.setPreferredSize(new java.awt.Dimension(770, 550));

        profileTitle1.setFont(new java.awt.Font("Montserrat Black", 1, 60)); // NOI18N
        profileTitle1.setForeground(new java.awt.Color(210, 210, 215));
        profileTitle1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        profileTitle1.setText("Help");

        jScrollPane4.setBackground(new java.awt.Color(0, 0, 48));
        jScrollPane4.setForeground(new java.awt.Color(210, 210, 215));
        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane4.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jTextArea2.setBackground(new java.awt.Color(0, 0, 48));
        jTextArea2.setColumns(20);
        jTextArea2.setFont(new java.awt.Font("DialogInput", 1, 14)); // NOI18N
        jTextArea2.setForeground(new java.awt.Color(210, 210, 215));
        jTextArea2.setRows(5);
        jTextArea2.setText("    --------------------------------------------------------------------------------------------\n    HELP PANEL\n\n    For user concerns and uncertainty about using the application, read the information below:\n    --------------------------------------------------------------------------------------------\n\n    --------------------------------------------------------------------------------------------\n    PROFILE PANEL\n\n    The profile panel shows all your personal information.\n    This includes your contribution, name, username, family and family id \n\n    Modify your personal information by inserting it into the text field \n    Click \"Save\" to change, then click the refresh button \n  \n    Add your contribution by inserting a value you in the text field \n    Click \"Save\" to update, and click the refresh button \n    --------------------------------------------------------------------------------------------\n\n    --------------------------------------------------------------------------------------------\n    ASSET, LIABILITY, BILLINGS PANEL \n\n    The Asset Panel shows all your asset information \n    Asset Panel provides you with an asset list in which you can \"Add\" and \"Remove\" values \n  \n    Add to the list by filling the 3 text fields (Item, Amount, Type)\n    Example: Item = \"Stock\", Amount = \"1000\", Type = \"Investments\"\n    Click the \"Add\" button to include it in the list\n  \n    Remove from the list by filling the 1 text field (Item)\n    Example: Item = \"Stock\", keep the 2 other text field blank \n    Click the \"Remove\" button to remove it from the list\n    --------------------------------------------------------------------------------------------\n\n    --------------------------------------------------------------------------------------------\n    MEMBER PANEL\n\n    The member panel shows all the names of the members and their respective contribution \n    --------------------------------------------------------------------------------------------\n\n    --------------------------------------------------------------------------------------------\n    DASHBOARD PANEL\n\n    The Dashboard Panel shows all financial summary\n    This includes your Total Budget, Asset, Liability, Expense, Contribution \n    It also shows the current expenses list \n    --------------------------------------------------------------------------------------------\n\n    --------------------------------------------------------------------------------------------\n    SCOPES AND DELIMITATIONS\n\n    The scope of our program aims to provide effective financial budget management for the \n\tfamily using a platform, software-based system. \n\n    The program is delimited by the following;  \n\n    [1] Offline Integrated \n      \t> the program is limited to offline only. Online integration such as online hosting of \n\t> the database, wifi base connectivity, or web-based development was disregarded. The \n\t> program was built in a stand-alone manner in which it can only run on local computers.\n \n    [2] Non-Existing E-Wallet \n\t> individual electronic wallets were excluded, meaning there were \n\t> no such real-time electronic money transactions. The money will only be based on the \n\t> inputted value of the respective users, which were only spurious. Thus, cash-in and \n\t> cash-out behavior were also purposely disregarded. \n\n    [3] Lack of Privacy Concerns, Terms, and Conditions, and Privacy Agreement \n\t> our program \n\t> accepts information that can probably violate confidentiality to the user. However,  \n\t> since it is only runnable in the local host, the intent of the program limits only to \n\t> privacy in the local base scope. \n\n    [4] JVM-Based Language \n\t> the project program can only be executed with a device that runs JVM. \n\t> Java languages was used (specifically Java MVC, and Java Swing) to create the entire\n\t> system. No other features were added and supported ( excluding MySQL Workbench) \n \n    [5] IDE Netbeans \n\t> all the source codes and frame design were established inside the Netbeans\n\t> APACHE. Manual Coding for the Model and Controller, while the view was coded \n\t> automaticallywith the support of NETBEANS Maven.\n\n    [6] Limited Scope for Database\n\t> The overall structure of the project’s database can only be run at the local host \n\t> server. The exclusion of online hosting of the server was intended by the group.\n \n    [7] Family-Based System\n\t> the usage of the system is more recommendable within the family. This will provide \n\t> better utilization and experience since the group’s objective was focused to Family. \n\n    --------------------------------------------------------------------------------------------\n\n\tCopyrights @ 2022.");
        jTextArea2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTextArea2.setSelectedTextColor(new java.awt.Color(210, 157, 56));
        jTextArea2.setSelectionColor(new java.awt.Color(38, 38, 38));
        jScrollPane4.setViewportView(jTextArea2);

        javax.swing.GroupLayout helpBoardLayout = new javax.swing.GroupLayout(helpBoard);
        helpBoard.setLayout(helpBoardLayout);
        helpBoardLayout.setHorizontalGroup(
            helpBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(helpBoardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(helpBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 801, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(profileTitle1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        helpBoardLayout.setVerticalGroup(
            helpBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(helpBoardLayout.createSequentialGroup()
                .addComponent(profileTitle1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 446, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jTabbedPane.addTab("tab8", helpBoard);

        profileBoard.setBackground(new java.awt.Color(0, 0, 48));
        profileBoard.setForeground(new java.awt.Color(210, 210, 215));
        profileBoard.setMaximumSize(new java.awt.Dimension(770, 550));
        profileBoard.setMinimumSize(new java.awt.Dimension(770, 550));
        profileBoard.setPreferredSize(new java.awt.Dimension(770, 550));

        profileTitle.setFont(new java.awt.Font("Montserrat Black", 1, 60)); // NOI18N
        profileTitle.setForeground(new java.awt.Color(210, 210, 215));
        profileTitle.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        profileTitle.setText("Profile");

        showProfilePanel.setBackground(new java.awt.Color(49, 107, 196));
        showProfilePanel.setOpaque(false);

        userFullName.setFont(new java.awt.Font("Bebas", 1, 48)); // NOI18N
        userFullName.setForeground(new java.awt.Color(38, 38, 38));
        userFullName.setText("Profile Name");
        userFullName.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                userFullNamePropertyChange(evt);
            }
        });

        username.setFont(new java.awt.Font("SansSerif", 3, 14)); // NOI18N
        username.setForeground(new java.awt.Color(38, 38, 38));
        username.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        username.setText("Username");
        username.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                usernamePropertyChange(evt);
            }
        });

        familyname.setFont(new java.awt.Font("SansSerif", 3, 14)); // NOI18N
        familyname.setForeground(new java.awt.Color(38, 38, 38));
        familyname.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        familyname.setText("Family Name");
        familyname.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                familynamePropertyChange(evt);
            }
        });

        accountUsernameLabel1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        accountUsernameLabel1.setForeground(new java.awt.Color(38, 38, 38));
        accountUsernameLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        accountUsernameLabel1.setText("Username:");

        accountUsernameLabel2.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        accountUsernameLabel2.setForeground(new java.awt.Color(38, 38, 38));
        accountUsernameLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        accountUsernameLabel2.setText("Family Name:");

        javax.swing.GroupLayout showProfilePanelLayout = new javax.swing.GroupLayout(showProfilePanel);
        showProfilePanel.setLayout(showProfilePanelLayout);
        showProfilePanelLayout.setHorizontalGroup(
            showProfilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(showProfilePanelLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(showProfilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(userFullName, javax.swing.GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE)
                    .addGroup(showProfilePanelLayout.createSequentialGroup()
                        .addGroup(showProfilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(accountUsernameLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(accountUsernameLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(showProfilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(username, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(familyname, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(91, 91, 91))))
        );
        showProfilePanelLayout.setVerticalGroup(
            showProfilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(showProfilePanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(userFullName, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(showProfilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(showProfilePanelLayout.createSequentialGroup()
                        .addComponent(accountUsernameLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)
                        .addGroup(showProfilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(accountUsernameLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(familyname, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(username))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        profileAccountPanel.setBackground(new java.awt.Color(43, 43, 75));
        profileAccountPanel.setOpaque(false);

        profileAccountLabel.setFont(new java.awt.Font("Montserrat Black", 0, 18)); // NOI18N
        profileAccountLabel.setForeground(new java.awt.Color(210, 210, 215));
        profileAccountLabel.setText("Edit Account");

        accountUsernameTextfield.setBackground(new java.awt.Color(210, 210, 215));
        accountUsernameTextfield.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        accountUsernameTextfield.setForeground(new java.awt.Color(38, 38, 38));
        accountUsernameTextfield.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        accountUsernameTextfield.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        accountUsernameTextfield.setName("Enter Username"); // NOI18N
        accountUsernameTextfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accountUsernameTextfieldActionPerformed(evt);
            }
        });

        accountUsernameLabel.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        accountUsernameLabel.setForeground(new java.awt.Color(210, 210, 215));
        accountUsernameLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        accountUsernameLabel.setText("Username:");

        accountPasswordLabel.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        accountPasswordLabel.setForeground(new java.awt.Color(210, 210, 215));
        accountPasswordLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        accountPasswordLabel.setText("Password:");

        accountPasswordTextfield.setBackground(new java.awt.Color(210, 210, 215));
        accountPasswordTextfield.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        accountPasswordTextfield.setForeground(new java.awt.Color(38, 38, 38));
        accountPasswordTextfield.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        accountPasswordTextfield.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        accountPasswordTextfield.setName("Enter Password"); // NOI18N
        accountPasswordTextfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accountPasswordTextfieldActionPerformed(evt);
            }
        });

        accountNameLabel.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        accountNameLabel.setForeground(new java.awt.Color(210, 210, 215));
        accountNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        accountNameLabel.setText("Name:");

        accountNameTextField.setBackground(new java.awt.Color(210, 210, 215));
        accountNameTextField.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        accountNameTextField.setActionCommand("<Not Set>");
        accountNameTextField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        accountNameTextField.setName("Enter Full Name"); // NOI18N
        accountNameTextField.setSelectionEnd(20);
        accountNameTextField.setSelectionStart(20);
        accountNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accountNameTextFieldActionPerformed(evt);
            }
        });

        accountUsernameSaveButton.setBackground(new java.awt.Color(89, 228, 147));
        accountUsernameSaveButton.setFont(new java.awt.Font("Montserrat Black", 2, 12)); // NOI18N
        accountUsernameSaveButton.setForeground(new java.awt.Color(38, 38, 38));
        accountUsernameSaveButton.setText("Save");
        accountUsernameSaveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accountUsernameSaveButtonActionPerformed(evt);
            }
        });

        accountNameSaveButton.setBackground(new java.awt.Color(89, 228, 147));
        accountNameSaveButton.setFont(new java.awt.Font("Montserrat Black", 2, 12)); // NOI18N
        accountNameSaveButton.setForeground(new java.awt.Color(38, 38, 38));
        accountNameSaveButton.setText("Save");
        accountNameSaveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accountNameSaveButtonActionPerformed(evt);
            }
        });

        accountPasswordSaveButton.setBackground(new java.awt.Color(89, 228, 147));
        accountPasswordSaveButton.setFont(new java.awt.Font("Montserrat Black", 2, 12)); // NOI18N
        accountPasswordSaveButton.setForeground(new java.awt.Color(38, 38, 38));
        accountPasswordSaveButton.setText("Save");
        accountPasswordSaveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accountPasswordSaveButtonActionPerformed(evt);
            }
        });

        accountFamilyTextfield.setBackground(new java.awt.Color(210, 210, 215));
        accountFamilyTextfield.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        accountFamilyTextfield.setForeground(new java.awt.Color(38, 38, 38));
        accountFamilyTextfield.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        accountFamilyTextfield.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        accountFamilyTextfield.setName("Enter Family Name"); // NOI18N

        accountFamilySaveButton.setBackground(new java.awt.Color(89, 228, 147));
        accountFamilySaveButton.setFont(new java.awt.Font("Montserrat Black", 2, 12)); // NOI18N
        accountFamilySaveButton.setForeground(new java.awt.Color(38, 38, 38));
        accountFamilySaveButton.setText("Save");
        accountFamilySaveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accountFamilySaveButtonActionPerformed(evt);
            }
        });

        accountFamilyLabel1.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        accountFamilyLabel1.setForeground(new java.awt.Color(210, 210, 215));
        accountFamilyLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        accountFamilyLabel1.setText("Family Name:");

        javax.swing.GroupLayout profileAccountPanelLayout = new javax.swing.GroupLayout(profileAccountPanel);
        profileAccountPanel.setLayout(profileAccountPanelLayout);
        profileAccountPanelLayout.setHorizontalGroup(
            profileAccountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(profileAccountPanelLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(profileAccountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(profileAccountLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(profileAccountPanelLayout.createSequentialGroup()
                        .addGroup(profileAccountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(accountFamilyLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(accountNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(accountUsernameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(accountPasswordLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(profileAccountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(accountPasswordTextfield, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                            .addComponent(accountUsernameTextfield)
                            .addComponent(accountNameTextField)
                            .addComponent(accountFamilyTextfield))
                        .addGap(18, 18, 18)
                        .addGroup(profileAccountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, profileAccountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(accountPasswordSaveButton, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(accountFamilySaveButton, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addComponent(accountUsernameSaveButton, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(accountNameSaveButton))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        profileAccountPanelLayout.setVerticalGroup(
            profileAccountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(profileAccountPanelLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(profileAccountLabel)
                .addGap(18, 18, 18)
                .addGroup(profileAccountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(accountNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(accountNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(accountNameSaveButton))
                .addGap(18, 18, 18)
                .addGroup(profileAccountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(accountUsernameTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(accountUsernameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(accountUsernameSaveButton))
                .addGap(16, 16, 16)
                .addGroup(profileAccountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(accountPasswordTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(accountPasswordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(accountPasswordSaveButton))
                .addGap(18, 18, 18)
                .addGroup(profileAccountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(accountFamilyTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(accountFamilySaveButton)
                    .addComponent(accountFamilyLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        userContribPanel.setBackground(new java.awt.Color(43, 43, 75));
        userContribPanel.setOpaque(false);

        totaluserContribText.setFont(new java.awt.Font("Montserrat Black", 1, 18)); // NOI18N
        totaluserContribText.setForeground(new java.awt.Color(210, 210, 215));
        totaluserContribText.setText("Total Contribution ");

        editUserContribText.setFont(new java.awt.Font("Montserrat", 2, 18)); // NOI18N
        editUserContribText.setForeground(new java.awt.Color(210, 210, 215));
        editUserContribText.setText("Edit Contribution:");

        showUserContrib.setFont(new java.awt.Font("Bebas", 1, 48)); // NOI18N
        showUserContrib.setForeground(new java.awt.Color(210, 210, 215));
        showUserContrib.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        showUserContrib.setText("P 00.00");
        showUserContrib.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                showUserContribPropertyChange(evt);
            }
        });

        inputEditUserContrib.setBackground(new java.awt.Color(210, 210, 215));
        inputEditUserContrib.setFont(new java.awt.Font("Bebas", 1, 24)); // NOI18N
        inputEditUserContrib.setForeground(new java.awt.Color(38, 38, 38));
        inputEditUserContrib.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        inputEditUserContrib.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        inputEditUserContrib.setName("P 00.00"); // NOI18N
        inputEditUserContrib.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputEditUserContribActionPerformed(evt);
            }
        });

        saveEditUserContrib.setBackground(new java.awt.Color(89, 228, 147));
        saveEditUserContrib.setFont(new java.awt.Font("Montserrat Black", 2, 14)); // NOI18N
        saveEditUserContrib.setForeground(new java.awt.Color(38, 38, 38));
        saveEditUserContrib.setText("Save");
        saveEditUserContrib.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveEditUserContribActionPerformed(evt);
            }
        });

        totalAmountContributedText1.setFont(new java.awt.Font("Montserrat", 2, 18)); // NOI18N
        totalAmountContributedText1.setForeground(new java.awt.Color(210, 210, 215));
        totalAmountContributedText1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        totalAmountContributedText1.setText("You contributed a total amount of :  ");

        javax.swing.GroupLayout userContribPanelLayout = new javax.swing.GroupLayout(userContribPanel);
        userContribPanel.setLayout(userContribPanelLayout);
        userContribPanelLayout.setHorizontalGroup(
            userContribPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userContribPanelLayout.createSequentialGroup()
                .addGroup(userContribPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(userContribPanelLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(userContribPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(userContribPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(editUserContribText)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, userContribPanelLayout.createSequentialGroup()
                                    .addGap(212, 212, 212)
                                    .addComponent(saveEditUserContrib, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(inputEditUserContrib))
                            .addComponent(userContribSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(userContribPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(showUserContrib, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(userContribPanelLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(userContribPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(totalAmountContributedText1)
                    .addComponent(totaluserContribText))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        userContribPanelLayout.setVerticalGroup(
            userContribPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userContribPanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(totaluserContribText, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(totalAmountContributedText1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(showUserContrib, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(userContribSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(editUserContribText)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(inputEditUserContrib, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                .addGap(28, 28, 28)
                .addComponent(saveEditUserContrib, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43))
        );

        javax.swing.GroupLayout profileBoardLayout = new javax.swing.GroupLayout(profileBoard);
        profileBoard.setLayout(profileBoardLayout);
        profileBoardLayout.setHorizontalGroup(
            profileBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(profileBoardLayout.createSequentialGroup()
                .addGroup(profileBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(showProfilePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(profileAccountPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(userContribPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, profileBoardLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(profileTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        profileBoardLayout.setVerticalGroup(
            profileBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(profileBoardLayout.createSequentialGroup()
                .addComponent(profileTitle)
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(profileBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(profileBoardLayout.createSequentialGroup()
                        .addComponent(showProfilePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(profileAccountPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(userContribPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        userContribPanel.getAccessibleContext().setAccessibleName("");

        jTabbedPane.addTab("tab9", profileBoard);
        profileBoard.getAccessibleContext().setAccessibleParent(jTabbedPane);

        getContentPane().add(jTabbedPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(254, 40, 830, 560));

        jFrameBg.setBackground(new java.awt.Color(0, 0, 48));
        jFrameBg.setMaximumSize(new java.awt.Dimension(1100, 650));
        jFrameBg.setMinimumSize(new java.awt.Dimension(1100, 650));
        jFrameBg.setPreferredSize(new java.awt.Dimension(1100, 650));

        tabPanel.setBackground(new java.awt.Color(0, 0, 48));

        profileTab.setBackground(new java.awt.Color(49, 107, 196));
        profileTab.setForeground(new java.awt.Color(236, 126, 179));
        profileTab.setOpaque(false);
        profileTab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                profileTabMouseClicked(evt);
            }
        });

        profileNameLabel.setFont(new java.awt.Font("Bebas", 1, 24)); // NOI18N
        profileNameLabel.setForeground(new java.awt.Color(38, 38, 38));
        profileNameLabel.setText("Profile Name");
        profileNameLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        profileNameLabel.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                profileNameLabelPropertyChange(evt);
            }
        });

        javax.swing.GroupLayout profileTabLayout = new javax.swing.GroupLayout(profileTab);
        profileTab.setLayout(profileTabLayout);
        profileTabLayout.setHorizontalGroup(
            profileTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(profileTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(profileNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        profileTabLayout.setVerticalGroup(
            profileTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(profileNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
        );

        menuPanel.setBackground(new java.awt.Color(43, 43, 75));
        menuPanel.setForeground(new java.awt.Color(43, 43, 75));
        menuPanel.setOpaque(false);

        dashboardPanel.setBackground(new java.awt.Color(43, 43, 75));
        dashboardPanel.setOpaque(false);
        dashboardPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dashboardPanelMouseClicked(evt);
            }
        });

        dashboardLabel.setFont(new java.awt.Font("Montserrat Black", 0, 14)); // NOI18N
        dashboardLabel.setForeground(new java.awt.Color(210, 210, 215));
        dashboardLabel.setText("Dashboard");
        dashboardLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dashboardLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout dashboardPanelLayout = new javax.swing.GroupLayout(dashboardPanel);
        dashboardPanel.setLayout(dashboardPanelLayout);
        dashboardPanelLayout.setHorizontalGroup(
            dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardPanelLayout.createSequentialGroup()
                .addComponent(dashboardLabel)
                .addGap(0, 99, Short.MAX_VALUE))
        );
        dashboardPanelLayout.setVerticalGroup(
            dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dashboardPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(dashboardLabel))
        );

        membersPanel.setBackground(new java.awt.Color(43, 43, 75));
        membersPanel.setOpaque(false);
        membersPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                membersPanelMouseClicked(evt);
            }
        });

        membersLabel.setFont(new java.awt.Font("Montserrat Black", 0, 14)); // NOI18N
        membersLabel.setForeground(new java.awt.Color(210, 210, 215));
        membersLabel.setText("Members");

        javax.swing.GroupLayout membersPanelLayout = new javax.swing.GroupLayout(membersPanel);
        membersPanel.setLayout(membersPanelLayout);
        membersPanelLayout.setHorizontalGroup(
            membersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(membersPanelLayout.createSequentialGroup()
                .addComponent(membersLabel)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        membersPanelLayout.setVerticalGroup(
            membersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, membersPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(membersLabel))
        );

        statsPanel.setBackground(new java.awt.Color(43, 43, 75));
        statsPanel.setOpaque(false);
        statsPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                statsPanelMouseClicked(evt);
            }
        });

        statsLabel.setFont(new java.awt.Font("Montserrat Black", 0, 14)); // NOI18N
        statsLabel.setForeground(new java.awt.Color(210, 210, 215));
        statsLabel.setText("Stats");

        javax.swing.GroupLayout statsPanelLayout = new javax.swing.GroupLayout(statsPanel);
        statsPanel.setLayout(statsPanelLayout);
        statsPanelLayout.setHorizontalGroup(
            statsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statsPanelLayout.createSequentialGroup()
                .addComponent(statsLabel)
                .addGap(0, 134, Short.MAX_VALUE))
        );
        statsPanelLayout.setVerticalGroup(
            statsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statsPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(statsLabel))
        );

        assetsPanel.setBackground(new java.awt.Color(43, 43, 75));
        assetsPanel.setOpaque(false);
        assetsPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                assetsPanelMouseClicked(evt);
            }
        });

        assetsLabel.setFont(new java.awt.Font("Montserrat Black", 0, 14)); // NOI18N
        assetsLabel.setForeground(new java.awt.Color(210, 210, 215));
        assetsLabel.setText("Assets");

        javax.swing.GroupLayout assetsPanelLayout = new javax.swing.GroupLayout(assetsPanel);
        assetsPanel.setLayout(assetsPanelLayout);
        assetsPanelLayout.setHorizontalGroup(
            assetsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(assetsPanelLayout.createSequentialGroup()
                .addComponent(assetsLabel)
                .addGap(0, 100, Short.MAX_VALUE))
        );
        assetsPanelLayout.setVerticalGroup(
            assetsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, assetsPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(assetsLabel))
        );

        liabilitiesPanel.setBackground(new java.awt.Color(43, 43, 75));
        liabilitiesPanel.setOpaque(false);
        liabilitiesPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                liabilitiesPanelMouseClicked(evt);
            }
        });

        liabilitiesLabel.setFont(new java.awt.Font("Montserrat Black", 0, 14)); // NOI18N
        liabilitiesLabel.setForeground(new java.awt.Color(210, 210, 215));
        liabilitiesLabel.setText("Liabilities");

        javax.swing.GroupLayout liabilitiesPanelLayout = new javax.swing.GroupLayout(liabilitiesPanel);
        liabilitiesPanel.setLayout(liabilitiesPanelLayout);
        liabilitiesPanelLayout.setHorizontalGroup(
            liabilitiesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(liabilitiesPanelLayout.createSequentialGroup()
                .addComponent(liabilitiesLabel)
                .addGap(0, 74, Short.MAX_VALUE))
        );
        liabilitiesPanelLayout.setVerticalGroup(
            liabilitiesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, liabilitiesPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(liabilitiesLabel))
        );

        billingsPanel.setBackground(new java.awt.Color(43, 43, 75));
        billingsPanel.setOpaque(false);
        billingsPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                billingsPanelMouseClicked(evt);
            }
        });

        billingsLabel.setFont(new java.awt.Font("Montserrat Black", 0, 14)); // NOI18N
        billingsLabel.setForeground(new java.awt.Color(210, 210, 215));
        billingsLabel.setText("Billings & Expenses");

        javax.swing.GroupLayout billingsPanelLayout = new javax.swing.GroupLayout(billingsPanel);
        billingsPanel.setLayout(billingsPanelLayout);
        billingsPanelLayout.setHorizontalGroup(
            billingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(billingsLabel)
        );
        billingsPanelLayout.setVerticalGroup(
            billingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, billingsPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(billingsLabel))
        );

        settingsPanel.setBackground(new java.awt.Color(43, 43, 75));
        settingsPanel.setForeground(new java.awt.Color(210, 210, 215));
        settingsPanel.setOpaque(false);
        settingsPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                settingsPanelMouseClicked(evt);
            }
        });

        settingsLabel.setFont(new java.awt.Font("Montserrat Black", 0, 14)); // NOI18N
        settingsLabel.setForeground(new java.awt.Color(210, 210, 215));
        settingsLabel.setText("Settings");

        javax.swing.GroupLayout settingsPanelLayout = new javax.swing.GroupLayout(settingsPanel);
        settingsPanel.setLayout(settingsPanelLayout);
        settingsPanelLayout.setHorizontalGroup(
            settingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(settingsPanelLayout.createSequentialGroup()
                .addComponent(settingsLabel)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        settingsPanelLayout.setVerticalGroup(
            settingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, settingsPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(settingsLabel))
        );

        helpPanel.setBackground(new java.awt.Color(43, 43, 75));
        helpPanel.setForeground(new java.awt.Color(210, 210, 215));
        helpPanel.setOpaque(false);
        helpPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                helpPanelMouseClicked(evt);
            }
        });

        helpLabel.setFont(new java.awt.Font("Montserrat Black", 0, 14)); // NOI18N
        helpLabel.setForeground(new java.awt.Color(210, 210, 215));
        helpLabel.setText("Help");

        javax.swing.GroupLayout helpPanelLayout = new javax.swing.GroupLayout(helpPanel);
        helpPanel.setLayout(helpPanelLayout);
        helpPanelLayout.setHorizontalGroup(
            helpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(helpPanelLayout.createSequentialGroup()
                .addComponent(helpLabel)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        helpPanelLayout.setVerticalGroup(
            helpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, helpPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(helpLabel))
        );

        logoutPanel.setBackground(new java.awt.Color(43, 43, 75));
        logoutPanel.setForeground(new java.awt.Color(210, 210, 215));
        logoutPanel.setOpaque(false);
        logoutPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutPanelMouseClicked(evt);
            }
        });

        logoutButton.setBackground(new java.awt.Color(230, 68, 86));
        logoutButton.setFont(new java.awt.Font("Montserrat Black", 1, 14)); // NOI18N
        logoutButton.setForeground(new java.awt.Color(210, 210, 215));
        logoutButton.setText("Logout");
        logoutButton.setBorder(null);
        logoutButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutButtonMouseClicked(evt);
            }
        });
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout logoutPanelLayout = new javax.swing.GroupLayout(logoutPanel);
        logoutPanel.setLayout(logoutPanelLayout);
        logoutPanelLayout.setHorizontalGroup(
            logoutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(logoutPanelLayout.createSequentialGroup()
                .addComponent(logoutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        logoutPanelLayout.setVerticalGroup(
            logoutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(logoutPanelLayout.createSequentialGroup()
                .addComponent(logoutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout menuPanelLayout = new javax.swing.GroupLayout(menuPanel);
        menuPanel.setLayout(menuPanelLayout);
        menuPanelLayout.setHorizontalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuPanelLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(settingsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(helpPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(logoutPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(menuPanelLayout.createSequentialGroup()
                        .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(dashboardPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(membersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(statsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(billingsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(liabilitiesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(assetsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 15, Short.MAX_VALUE)))
                .addContainerGap())
        );
        menuPanelLayout.setVerticalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuPanelLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(dashboardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(membersPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(statsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(assetsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(liabilitiesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(billingsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(settingsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(helpPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(logoutPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );

        javax.swing.GroupLayout tabPanelLayout = new javax.swing.GroupLayout(tabPanel);
        tabPanel.setLayout(tabPanelLayout);
        tabPanelLayout.setHorizontalGroup(
            tabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(profileTab, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(menuPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        tabPanelLayout.setVerticalGroup(
            tabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(profileTab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(menuPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel1.setFont(new java.awt.Font("SansSerif", 2, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(210, 210, 215));
        jLabel1.setText("Copyrights @ 2022");

        javax.swing.GroupLayout jFrameBgLayout = new javax.swing.GroupLayout(jFrameBg);
        jFrameBg.setLayout(jFrameBgLayout);
        jFrameBgLayout.setHorizontalGroup(
            jFrameBgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrameBgLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(tabPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jFrameBgLayout.createSequentialGroup()
                .addContainerGap(957, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(21, 21, 21))
        );
        jFrameBgLayout.setVerticalGroup(
            jFrameBgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jFrameBgLayout.createSequentialGroup()
                .addContainerGap(57, Short.MAX_VALUE)
                .addComponent(tabPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addGap(17, 17, 17))
        );

        getContentPane().add(jFrameBg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1100, 650));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveEditUserContribActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveEditUserContribActionPerformed
        try {
             // TODO add your handling code here:
             if (Double.parseDouble(inputEditUserContrib.getText()) > 0){
                family.addToList(family.getMemberName(), Double.parseDouble(inputEditUserContrib.getText()),"CONTRIBUTE",null);
                removeContribList(); 
                setMemberTable();
             }
            else 
               JOptionPane.showMessageDialog(this, "Negative Numbers are not allowed");
         
         } catch (SQLException ex) {
             Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
         } catch (NumberFormatException ne){
             JOptionPane.showMessageDialog(this, "Invalid Input");
         }
        inputEditUserContrib.setText("");
    }//GEN-LAST:event_saveEditUserContribActionPerformed

    private void inputEditUserContribActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputEditUserContribActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inputEditUserContribActionPerformed

    private void helpPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_helpPanelMouseClicked
        // TODO add your handling code here:
        jTabbedPane.setSelectedIndex(7);
    }//GEN-LAST:event_helpPanelMouseClicked

    private void settingsPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_settingsPanelMouseClicked
        // TODO add your handling code here:
        jTabbedPane.setSelectedIndex(6);
    }//GEN-LAST:event_settingsPanelMouseClicked

    private void billingsPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_billingsPanelMouseClicked
        // TODO add your handling code here:
        jTabbedPane.setSelectedIndex(5);
    }//GEN-LAST:event_billingsPanelMouseClicked

    private void liabilitiesPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_liabilitiesPanelMouseClicked
        // TODO add your handling code here:
        jTabbedPane.setSelectedIndex(4);
    }//GEN-LAST:event_liabilitiesPanelMouseClicked

    private void assetsPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_assetsPanelMouseClicked
        // TODO add your handling code here:
        jTabbedPane.setSelectedIndex(3);
    }//GEN-LAST:event_assetsPanelMouseClicked

    private void statsPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_statsPanelMouseClicked
        // TODO add your handling code here:
        jTabbedPane.setSelectedIndex(2);
    }//GEN-LAST:event_statsPanelMouseClicked

    private void membersPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_membersPanelMouseClicked
        // TODO add your handling code here:
        jTabbedPane.setSelectedIndex(1);
    }//GEN-LAST:event_membersPanelMouseClicked

    private void dashboardPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashboardPanelMouseClicked
        // TODO add your handling code here:
        jTabbedPane.setSelectedIndex(0);
    }//GEN-LAST:event_dashboardPanelMouseClicked

    private void accountUsernameSaveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accountUsernameSaveButtonActionPerformed
         try {
             // TODO add your handling code here:
             if (accountUsernameTextfield.getText().length() > 0)
                family.changeUsername(family.getUserName(), accountUsernameTextfield.getText());
             else 
                JOptionPane.showMessageDialog(this, "Invalid Input");
                
         } catch (SQLException ex) {
             Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
         }
         accountUsernameTextfield.setText("");
    }//GEN-LAST:event_accountUsernameSaveButtonActionPerformed

    private void accountNameSaveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accountNameSaveButtonActionPerformed
        // TODO add your handling code here:
         try {
             // TODO add your handling code here:
             if (accountNameTextField.getText().length() > 0)
                family.changeName(family.getUserName(), accountNameTextField.getText());
             else 
                JOptionPane.showMessageDialog(this, "Invalid Input");
             
         } catch (SQLException ex) {
             Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
         }
         accountNameTextField.setText("");
         
    }//GEN-LAST:event_accountNameSaveButtonActionPerformed

    private void accountPasswordSaveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accountPasswordSaveButtonActionPerformed
         try {
             // TODO add your handling code here:
             if (accountPasswordTextfield.getText().length() > 0)
                family.changePass(family.getUserName(), accountPasswordTextfield.getText());
             else
                JOptionPane.showMessageDialog(this, "Invalid Input");
         
         } catch (SQLException ex) {
             Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
         }
         accountPasswordTextfield.setText("");
    }//GEN-LAST:event_accountPasswordSaveButtonActionPerformed

    private void assetsItemTextfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assetsItemTextfieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_assetsItemTextfieldActionPerformed

    private void liabilitiesItemTextfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_liabilitiesItemTextfieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_liabilitiesItemTextfieldActionPerformed

    private void billingsItemTextfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_billingsItemTextfieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_billingsItemTextfieldActionPerformed

    private void liabilitiesValueTextfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_liabilitiesValueTextfieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_liabilitiesValueTextfieldActionPerformed

    private void dashboardContribSharesTotalAmountComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_dashboardContribSharesTotalAmountComponentAdded
        try {
            // TODO add your handling code here:
            family.getContribution();
        } catch (SQLException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_dashboardContribSharesTotalAmountComponentAdded
   
    private void totalBudgetAmountPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_totalBudgetAmountPropertyChange
        try {
            // TODO add your handling code here:
            totalBudgetAmount.setText("P " + family.getBalance()); // asset + liabilities + expense
        } catch (SQLException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_totalBudgetAmountPropertyChange

    private void totalAssetsAmountPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_totalAssetsAmountPropertyChange
        try {
            // TODO add your handling code here:
            totalAssetsAmount.setText("P " + family.getAsset());
        } catch (SQLException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }//GEN-LAST:event_totalAssetsAmountPropertyChange

    private void totalBillingsAmountPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_totalBillingsAmountPropertyChange
        try {
            // TODO add your handling code here:
            totalBillingsAmount.setText("P " + family.getExpense());
        } catch (SQLException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_totalBillingsAmountPropertyChange

    private void totalLiabilitiesAmountPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_totalLiabilitiesAmountPropertyChange
        try {
            // TODO add your handling code here:
            totalLiabilitiesAmount.setText("P " + family.getLiability());
        } catch (SQLException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_totalLiabilitiesAmountPropertyChange

    private void dashboardContribSharesTotalAmountPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dashboardContribSharesTotalAmountPropertyChange
        try {
            // TODO add your handling code here:
            dashboardContribSharesTotalAmount.setText("P " + family.getContribution());
        } catch (SQLException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_dashboardContribSharesTotalAmountPropertyChange

    private void showUserContribPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_showUserContribPropertyChange
        try {
             // TODO add your handling code here:
            showUserContrib.setText("P" + family.getIndivContrib());
         } catch (SQLException ex) {
             Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_showUserContribPropertyChange

    private void userFullNamePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_userFullNamePropertyChange
         try {
             // TODO add your handling code here:
             userFullName.setText(family.getMemberName());
         } catch (SQLException ex) {
             Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
         }
    }//GEN-LAST:event_userFullNamePropertyChange

    private void usernamePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_usernamePropertyChange
         try {
             // TODO add your handling code here:
             username.setText(family.getUserName());
         } catch (SQLException ex) {
             Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
         }
        
    }//GEN-LAST:event_usernamePropertyChange

    private void familynamePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_familynamePropertyChange
         try {
             // TODO add your handling code here:
             familyname.setText(family.getFamilyName() + "(ID: " + family.getFamilyID() + ")");
         } catch (SQLException ex) {
             Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
         }
    }//GEN-LAST:event_familynamePropertyChange

    private void accountFamilySaveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accountFamilySaveButtonActionPerformed
        // TODO add your handling code here:
        String temp = accountFamilyTextfield.getText();
        if (temp.length() == 0)
            JOptionPane.showMessageDialog(this, "Invalid Input");    
        else 
            family.changeTo(Integer.parseInt(family.getFamilyID()), accountFamilyTextfield.getText());
        accountFamilyTextfield.setText("");
    }//GEN-LAST:event_accountFamilySaveButtonActionPerformed

    private void accountUsernameTextfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accountUsernameTextfieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_accountUsernameTextfieldActionPerformed

    private void assetsAddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assetsAddButtonActionPerformed
        // TODO add your handling code here:
        
        try{
            // getting the amount of asset 
            double temp = Double.parseDouble(assetsValueTextfield.getText()); 
            if (temp > 0 && Double.parseDouble(family.getContribution()) >= temp){
                try {
                   family.addToList(assetsItemTextfield.getText(), Double.parseDouble(assetsValueTextfield.getText()), "ASSET", assetsTypeTextfield.getText());
                   removeAssetTable();
                   setAssetTable();
                } catch (SQLException ex) {
                    Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
             
            } 
            else 
                JOptionPane.showMessageDialog(this, "Negative Values are Not Allowed");
            
            
            
        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(this, "Invalid Input");
        } catch (SQLException ex) {
             Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        assetsValueTextfield.setText("");
        assetsItemTextfield.setText("");
        assetsTypeTextfield.setText("");
        
    }//GEN-LAST:event_assetsAddButtonActionPerformed

    private void assetsRemoveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assetsRemoveButtonActionPerformed
        // TODO add your handling code here:
        try{
            if (assetsValueTextfield.getText().length() > 0 || assetsItemTextfield.getText().length() == 0 || assetsTypeTextfield.getText().length() > 0 )
             JOptionPane.showMessageDialog(this, "Invalid Input");
         else{
             family.removefromList(assetsItemTextfield.getText(), "ASSET", null);
             removeAssetTable();
             setAssetTable();
         }
       } catch(NumberFormatException ex ){
           JOptionPane.showMessageDialog(this, "Invalid Input");
       } catch (SQLException sq){
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, sq);
       }
        
        assetsValueTextfield.setText("");
        assetsItemTextfield.setText("");
        assetsTypeTextfield.setText("");
    }//GEN-LAST:event_assetsRemoveButtonActionPerformed

    private void liabilitiesAddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_liabilitiesAddButtonActionPerformed
        // TODO add your handling code here:
         try{
            double temp = Double.parseDouble(liabilitiesValueTextfield.getText()); 
            if (Double.parseDouble(liabilitiesValueTextfield.getText()) > 0 && Double.parseDouble(liabilitiesValueTextfield.getText()) >= temp){
               
                try {
                    family.addToList(liabilitiesItemTextfield.getText(), Double.parseDouble(liabilitiesValueTextfield.getText()), "LIABILITY", liabilitiesTypeTextfield.getText());
                    removeLiabilityTable();
                    setLiabilityTable();
                } catch (SQLException ex) {
                    Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
               
            }
            else 
                JOptionPane.showMessageDialog(this, "Negative Values are Not Allowed");
            
        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(this, "Invalid Input");
        }
         
        liabilitiesItemTextfield.setText("");
        liabilitiesValueTextfield.setText("");
        liabilitiesTypeTextfield.setText("");
        
    }//GEN-LAST:event_liabilitiesAddButtonActionPerformed

    private void liabilitiesRemoveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_liabilitiesRemoveButtonActionPerformed
        // TODO add your handling code here:
        
        
     
           
        if (liabilitiesValueTextfield.getText().length() > 0 || liabilitiesItemTextfield.getText().length() <= 0 || liabilitiesTypeTextfield.getText().length() > 0)
            JOptionPane.showMessageDialog(this, "Invalid Input");
        else  {
            try {
              family.removefromList(liabilitiesItemTextfield.getText(), "LIABILITY", liabilitiesTypeTextfield.getText());
              removeLiabilityTable();
              setLiabilityTable();
            } catch (SQLException ex) {
                Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
          
        }
           
     
            
    }//GEN-LAST:event_liabilitiesRemoveButtonActionPerformed

    private void billingsAddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_billingsAddButtonActionPerformed
        // TODO add your handling code here:
       
        try{ 
            double temp = Double.parseDouble(billingsValueTextfield.getText()); 
            if (Double.parseDouble(billingsValueTextfield.getText()) > 0 && Double.parseDouble(billingsValueTextfield.getText()) >= temp){
                try {
                    family.addToList(billingsItemTextfield.getText(), Double.parseDouble(billingsValueTextfield.getText()), "EXPENSE", billingsTypeTextfield.getText());
                    removeExpenseTable(); 
                    setExpenseTable();
                } catch (SQLException ex) {
                    Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            else 
                JOptionPane.showMessageDialog(this, "Negative Values are Not Allowed");
        }catch(NumberFormatException ex){
             JOptionPane.showMessageDialog(this, "Invalid Input");
        }
    }//GEN-LAST:event_billingsAddButtonActionPerformed

    private void billingsRemoveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_billingsRemoveButtonActionPerformed
        // TODO add your handling code here:
      

        if (billingsValueTextfield.getText().length() > 0 || billingsItemTextfield.getText().length() == 0 && billingsTypeTextfield.getText().length() == 0)
            JOptionPane.showMessageDialog(this, "Invalid Input");
        else  {
            try {
                family.removefromList(billingsItemTextfield.getText(), "EXPENSE", billingsTypeTextfield.getText());
                removeExpenseTable();   
                setExpenseTable();
            } catch (SQLException ex) {
                Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
     
              
            
        }
          
    }//GEN-LAST:event_billingsRemoveButtonActionPerformed

    private void accountPasswordTextfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accountPasswordTextfieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_accountPasswordTextfieldActionPerformed

    private void accountNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accountNameTextFieldActionPerformed
        // TODO add your handling code here:
        //accountNameTextFieldActionPerformed.addActionListener()
    }//GEN-LAST:event_accountNameTextFieldActionPerformed

    private void refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshActionPerformed
         try {
             // TODO add your handling code here:
             new NewJFrame(family).setVisible(true);
             this.setVisible(false);
             this.dispose(); 
             
         } catch (SQLException ex) {
             Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
         }
        
        
    }//GEN-LAST:event_refreshActionPerformed

    private void budgetSpentTotalPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_budgetSpentTotalPropertyChange
         try {
             // TODO add your handling code here:
             budgetSpentTotal.setText(family.getBudgetSpent());
         } catch (SQLException ex) {
             Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
         }
    }//GEN-LAST:event_budgetSpentTotalPropertyChange

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        // TODO add your handling code here:
        try{
            stmt.close();
            conn.close();
            }
        catch(SQLException e){
            System.out.println("Unable to close connections");
            e.printStackTrace();
        }
        System.out.print("Program Terminated");
        System.exit(0);
    }//GEN-LAST:event_exitButtonActionPerformed

    private void budgetConditionLabelPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_budgetConditionLabelPropertyChange
        try {
            // TODO add your handling code here:
            budgetConditionLabel.setText(family.getBudgetCondition());
           
        } catch (SQLException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_budgetConditionLabelPropertyChange

    private void budgetConditionLabel1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_budgetConditionLabel1PropertyChange
        // TODO add your handling code here:
          try {
            // TODO add your handling code here:
            budgetConditionLabel1.setText(family.getBudgetCondition());
        } catch (SQLException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_budgetConditionLabel1PropertyChange

    private void totalAssetsAmount1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_totalAssetsAmount1PropertyChange
        // TODO add your handling code here:
         try {
            // TODO add your handling code here:
            totalAssetsAmount1.setText("P " + family.getAsset()); // asset + liabilities + expense
        } catch (SQLException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_totalAssetsAmount1PropertyChange

    private void totalLiabilitiesAmount1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_totalLiabilitiesAmount1PropertyChange
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            totalLiabilitiesAmount1.setText("P " + family.getLiability());
        } catch (SQLException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_totalLiabilitiesAmount1PropertyChange

    private void liabilitiesTypeTextfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_liabilitiesTypeTextfieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_liabilitiesTypeTextfieldActionPerformed

    private void totalBillingsAmount1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_totalBillingsAmount1PropertyChange
        // TODO add your handling code here:
         try {
            // TODO add your handling code here:
            totalBillingsAmount1.setText("P " + family.getExpense());
        } catch (SQLException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_totalBillingsAmount1PropertyChange

    private void showUserContrib1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_showUserContrib1PropertyChange
        // TODO add your handling code here:
          try {
             // TODO add your handling code here:
            showUserContrib1.setText("P" + family.getIndivContrib());
         } catch (SQLException ex) {
             Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_showUserContrib1PropertyChange

    private void dashboardContribSharesTotalAmount1ComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_dashboardContribSharesTotalAmount1ComponentAdded
        // TODO add your handling code here:
       
    }//GEN-LAST:event_dashboardContribSharesTotalAmount1ComponentAdded

    private void dashboardContribSharesTotalAmount1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dashboardContribSharesTotalAmount1PropertyChange
        // TODO add your handling code here:
         try {
            // TODO add your handling code here:
            dashboardContribSharesTotalAmount1.setText("P " + family.getContribution());
        } catch (SQLException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_dashboardContribSharesTotalAmount1PropertyChange

    private void totalBudgetAmount1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_totalBudgetAmount1PropertyChange
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            totalBudgetAmount1.setText("P " + family.getBalance()); // asset + liabilities + expense
        } catch (SQLException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_totalBudgetAmount1PropertyChange

    private void totalAssetsAmount2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_totalAssetsAmount2PropertyChange
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            totalAssetsAmount2.setText("P " + family.getAsset()); // asset + liabilities + expense
        } catch (SQLException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_totalAssetsAmount2PropertyChange

    private void totalBillingsAmount2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_totalBillingsAmount2PropertyChange
        // TODO add your handling code here:
         try {
            // TODO add your handling code here:
            totalBillingsAmount2.setText("P " + family.getExpense());
        } catch (SQLException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_totalBillingsAmount2PropertyChange

    private void totalLiabilitiesAmount2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_totalLiabilitiesAmount2PropertyChange
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            totalLiabilitiesAmount2.setText("P " + family.getLiability());
        } catch (SQLException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_totalLiabilitiesAmount2PropertyChange

    private void totalBudgetAmount2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_totalBudgetAmount2PropertyChange
        // TODO add your handling code here:
         try {
            // TODO add your handling code here:
            totalBudgetAmount2.setText("P " + family.getBalance()); // asset + liabilities + expense
        } catch (SQLException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_totalBudgetAmount2PropertyChange

    private void totalBudgetAmount3PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_totalBudgetAmount3PropertyChange
        // TODO add your handling code here:
         try {
            // TODO add your handling code here:
            totalBudgetAmount3.setText("P " + family.getBalance()); // asset + liabilities + expense
        } catch (SQLException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_totalBudgetAmount3PropertyChange

    private void dashboardContribSharesTotalAmount2ComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_dashboardContribSharesTotalAmount2ComponentAdded
        // TODO add your handling code here:
    
    }//GEN-LAST:event_dashboardContribSharesTotalAmount2ComponentAdded

    private void dashboardContribSharesTotalAmount2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dashboardContribSharesTotalAmount2PropertyChange
        // TODO add your handling code here:
         try {
            // TODO add your handling code here:
            dashboardContribSharesTotalAmount2.setText("P " + family.getContribution());
        } catch (SQLException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_dashboardContribSharesTotalAmount2PropertyChange

    private void assetsValueTextfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assetsValueTextfieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_assetsValueTextfieldActionPerformed

    private void assetsTypeTextfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assetsTypeTextfieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_assetsTypeTextfieldActionPerformed

    private void profileTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profileTabMouseClicked
        // TODO add your handling code here:
         jTabbedPane.setSelectedIndex(8);
    }//GEN-LAST:event_profileTabMouseClicked

    private void memberRemoveTextfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_memberRemoveTextfieldActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_memberRemoveTextfieldActionPerformed

    private void memberRemoveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_memberRemoveButtonActionPerformed
        // TODO add your handling code here:
         if (memberRemoveTextfield.getText().length() <= 0)
            JOptionPane.showMessageDialog(this, "Invalid Input");
        else  {
            try {
                family.removefromList(memberRemoveTextfield.getText(), "CONTRIBUTE", null);
                removeContribList();   
                setMemberTable();
            } catch (SQLException ex) {
                Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
     
              
            
        }
    }//GEN-LAST:event_memberRemoveButtonActionPerformed

    private void budgetConditionMessagePanelPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_budgetConditionMessagePanelPropertyChange
        // TODO add your handling code here:
         if (family.getColor().equals("RED")){
                    budgetConditionMessagePanel.setBackground(new Color(230, 68, 86));
            } else if (family.getColor().equals("GREEN")){
            budgetConditionMessagePanel.setBackground(new Color(89, 228, 147));
            }
    }//GEN-LAST:event_budgetConditionMessagePanelPropertyChange

    private void dashboardBudgetConditionMessagePanelPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dashboardBudgetConditionMessagePanelPropertyChange
        // TODO add your handling code here:
        if (family.getColor().equals("RED")){
                    dashboardBudgetConditionMessagePanel.setBackground(new Color(230, 68, 86));
            } else if (family.getColor().equals("GREEN")){
            dashboardBudgetConditionMessagePanel.setBackground(new Color(89, 228, 147));
            }
    }//GEN-LAST:event_dashboardBudgetConditionMessagePanelPropertyChange

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_logoutButtonActionPerformed

    private void logoutPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutPanelMouseClicked
        // TODO add your handling code here:
                //newSignIn = new OpenJFrame();  
                  
    }//GEN-LAST:event_logoutPanelMouseClicked

    private void logoutButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutButtonMouseClicked
        // TODO add your handling code here:
                  this.dispose();
                  this.setVisible(false);
                  try{
                      stmt.close();
                      conn.close();
                      System.out.println("Connection Closed");
                    }catch(SQLException e){
                        System.out.println("Unable to close connections");
                        e.printStackTrace();}
                  JOptionPane.showMessageDialog(this, "Log out Successful");
                  
                  OpenJframe newSignIn = new OpenJframe();
                  newSignIn.setVisible(true);
                 
                  
                  
    }//GEN-LAST:event_logoutButtonMouseClicked

    private void profileNameLabelPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_profileNameLabelPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_profileNameLabelPropertyChange

    private void dashboardLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashboardLabelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_dashboardLabelMouseClicked
   
  
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(OpenJframe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OpenJframe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OpenJframe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OpenJframe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    FamilyBank fm = new FamilyMember();
                    fm.login("lui", "bowie");
                  
                    new NewJFrame(fm).setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
    }
    
    //tables

      

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ContributionSharePanel;
    private javax.swing.JLabel accountFamilyLabel1;
    private javax.swing.JButton accountFamilySaveButton;
    private javax.swing.JTextField accountFamilyTextfield;
    private javax.swing.JLabel accountNameLabel;
    private javax.swing.JButton accountNameSaveButton;
    private javax.swing.JTextField accountNameTextField;
    private javax.swing.JLabel accountPasswordLabel;
    private javax.swing.JButton accountPasswordSaveButton;
    private javax.swing.JTextField accountPasswordTextfield;
    private javax.swing.JLabel accountUsernameLabel;
    private javax.swing.JLabel accountUsernameLabel1;
    private javax.swing.JLabel accountUsernameLabel2;
    private javax.swing.JButton accountUsernameSaveButton;
    private javax.swing.JTextField accountUsernameTextfield;
    private javax.swing.JLabel assetItemLabel;
    private javax.swing.JTable assetListTable;
    private javax.swing.JLabel assetTypeLabel;
    private javax.swing.JLabel assetValueLabel;
    private javax.swing.JButton assetsAddButton;
    private javax.swing.JPanel assetsBoard;
    private javax.swing.JPanel assetsChartPanel;
    private java.awt.TextField assetsItemTextfield;
    private javax.swing.JLabel assetsLabel;
    private javax.swing.JScrollPane assetsListScrollPane;
    private javax.swing.JPanel assetsPanel;
    private javax.swing.JButton assetsRemoveButton;
    private javax.swing.JPanel assetsStatusPanel;
    private javax.swing.JLabel assetsTitle;
    private java.awt.TextField assetsTypeTextfield;
    private java.awt.TextField assetsValueTextfield;
    private javax.swing.JButton billingsAddButton;
    private javax.swing.JPanel billingsBoard;
    private javax.swing.JPanel billingsChartPanel;
    private javax.swing.JLabel billingsItemLabel;
    private java.awt.TextField billingsItemTextfield;
    private javax.swing.JLabel billingsLabel;
    private javax.swing.JPanel billingsPanel;
    private javax.swing.JButton billingsRemoveButton;
    private javax.swing.JPanel billingsStatusPanel;
    private javax.swing.JLabel billingsTitle;
    private javax.swing.JLabel billingsTypeLabel;
    private java.awt.TextField billingsTypeTextfield;
    private javax.swing.JLabel billingsValueLabel;
    private java.awt.TextField billingsValueTextfield;
    private javax.swing.JLabel budgetConditionLabel;
    private javax.swing.JLabel budgetConditionLabel1;
    private javax.swing.JPanel budgetConditionMessagePanel;
    private javax.swing.JLabel budgetSpentTotal;
    private javax.swing.JPanel dashboardBoard;
    private javax.swing.JPanel dashboardBudgetConditionMessagePanel;
    private javax.swing.JPanel dashboardContribSharesPanel;
    private javax.swing.JPanel dashboardContribSharesPanel1;
    private javax.swing.JLabel dashboardContribSharesTotalAmount;
    private javax.swing.JLabel dashboardContribSharesTotalAmount1;
    private javax.swing.JLabel dashboardContribSharesTotalAmount2;
    private javax.swing.JLabel dashboardLabel;
    private javax.swing.JPanel dashboardPanel;
    private javax.swing.JLabel dashboardTitle;
    private javax.swing.JLabel editUserContribText;
    private javax.swing.JButton exitButton;
    private javax.swing.JTable expenseTableList;
    private javax.swing.JTable expenseTableList1;
    private javax.swing.JLabel familyname;
    private javax.swing.JPanel helpBoard;
    private javax.swing.JLabel helpLabel;
    private javax.swing.JPanel helpPanel;
    private javax.swing.JPanel hidePanel;
    private javax.swing.JTextField inputEditUserContrib;
    private javax.swing.JPanel jFrameBg;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JButton liabilitiesAddButton;
    private javax.swing.JPanel liabilitiesBoard;
    private javax.swing.JPanel liabilitiesChartPanel;
    private javax.swing.JLabel liabilitiesItemLabel;
    private java.awt.TextField liabilitiesItemTextfield;
    private javax.swing.JLabel liabilitiesLabel;
    private javax.swing.JTable liabilitiesListTable;
    private javax.swing.JPanel liabilitiesPanel;
    private javax.swing.JButton liabilitiesRemoveButton;
    private javax.swing.JPanel liabilitiesStatusPanel;
    private javax.swing.JLabel liabilitiesTitle;
    private javax.swing.JLabel liabilitiesTypeLabel;
    private java.awt.TextField liabilitiesTypeTextfield;
    private javax.swing.JLabel liabilitiesValueLabel;
    private java.awt.TextField liabilitiesValueTextfield;
    private javax.swing.JButton logoutButton;
    private javax.swing.JPanel logoutPanel;
    private javax.swing.JButton memberRemoveButton;
    private javax.swing.JTextField memberRemoveTextfield;
    private javax.swing.JPanel membersBoard;
    private javax.swing.JLabel membersLabel;
    private javax.swing.JScrollPane membersListScrollPane;
    private javax.swing.JTable membersListTable;
    private javax.swing.JPanel membersPanel;
    private javax.swing.JLabel membersTitle;
    private javax.swing.JPanel menuPanel;
    private javax.swing.JLabel profileAccountLabel;
    private javax.swing.JLabel profileAccountLabel1;
    private javax.swing.JLabel profileAccountLabel2;
    private javax.swing.JLabel profileAccountLabel3;
    private javax.swing.JPanel profileAccountPanel;
    private javax.swing.JPanel profileBoard;
    private javax.swing.JLabel profileNameLabel;
    private javax.swing.JPanel profileTab;
    private javax.swing.JLabel profileTitle;
    private javax.swing.JLabel profileTitle1;
    private javax.swing.JButton refresh;
    private javax.swing.JLabel removeNameLabel;
    private javax.swing.JButton saveEditUserContrib;
    private javax.swing.JPanel settingsBoard;
    private javax.swing.JLabel settingsLabel;
    private javax.swing.JPanel settingsPanel;
    private javax.swing.JLabel settingsTitle;
    private javax.swing.JPanel showProfilePanel;
    private javax.swing.JLabel showUserContrib;
    private javax.swing.JLabel showUserContrib1;
    private javax.swing.JPanel statsBoard;
    private javax.swing.JLabel statsLabel;
    private javax.swing.JPanel statsPanel;
    private javax.swing.JLabel statsTitle;
    private javax.swing.JPanel tabPanel;
    private javax.swing.JLabel totalAmountContributedText;
    private javax.swing.JLabel totalAmountContributedText1;
    private javax.swing.JPanel totalAmountReport;
    private javax.swing.JLabel totalAssetsAmount;
    private javax.swing.JLabel totalAssetsAmount1;
    private javax.swing.JLabel totalAssetsAmount2;
    private javax.swing.JLabel totalAssetsLabel;
    private javax.swing.JPanel totalAssetsPanel;
    private javax.swing.JLabel totalBillingsAmount;
    private javax.swing.JLabel totalBillingsAmount1;
    private javax.swing.JLabel totalBillingsAmount2;
    private javax.swing.JLabel totalBillingsLabel;
    private javax.swing.JPanel totalBillingsPanel;
    private javax.swing.JLabel totalBudgetAmount;
    private javax.swing.JLabel totalBudgetAmount1;
    private javax.swing.JLabel totalBudgetAmount2;
    private javax.swing.JLabel totalBudgetAmount3;
    private javax.swing.JLabel totalBudgetLabel;
    private javax.swing.JPanel totalBudgetPanel;
    private javax.swing.JLabel totalLiabilitiesAmount;
    private javax.swing.JLabel totalLiabilitiesAmount1;
    private javax.swing.JLabel totalLiabilitiesAmount2;
    private javax.swing.JLabel totaluserContribText;
    private javax.swing.JLabel totlalLiabilitiesLabel;
    private javax.swing.JPanel totlalLiabilitiesPanel;
    private javax.swing.JPanel userContribPanel;
    private javax.swing.JSeparator userContribSeparator;
    private javax.swing.JLabel userFullName;
    private javax.swing.JLabel username;
    // End of variables declaration//GEN-END:variables


    // rounded panel
class RoundedPanel extends JPanel
    {
        private Color backgroundColor;
        private int cornerRadius = 15;
        public RoundedPanel(LayoutManager layout, int radius) {
            super(layout);
            cornerRadius = radius;
        }
        public RoundedPanel(LayoutManager layout, int radius, Color bgColor) {
            super(layout);
            cornerRadius = radius;
            backgroundColor = bgColor;
        }
        public RoundedPanel(int radius) {
            super();
            cornerRadius = radius;
            
        }
        public RoundedPanel(int radius, Color bgColor) {
            super();
            cornerRadius = radius;
            backgroundColor = bgColor;
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Dimension arcs = new Dimension(cornerRadius, cornerRadius);
            int width = getWidth();
            int height = getHeight();
            Graphics2D graphics = (Graphics2D) g;
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            //Draws the rounded panel with borders.
            if (backgroundColor != null) {
                graphics.setColor(backgroundColor);
            } else {
                graphics.setColor(getBackground());
            }
            graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height); //paint background
            graphics.setColor(getForeground());
            //graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height); //paint border
          
        }
    }


// Rounded borders only
class RoundedBorder extends JPanel
    {
        private Color backgroundColor;
        private int cornerRadius = 15;
       
        
        public RoundedBorder(int radius) {
            super();
            cornerRadius = radius;
            
        }
        public RoundedBorder(int radius, Color bgColor) {
            super();
            cornerRadius = radius;
            backgroundColor = bgColor;
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Dimension arcs = new Dimension(cornerRadius, cornerRadius);
            int width = getWidth();
            int height = getHeight();
            Graphics2D graphics = (Graphics2D) g;
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            //Draws the rounded panel with borders.
            if (backgroundColor != null) {
                graphics.setColor(backgroundColor);
            } else {
                graphics.setColor(getBackground());
            }
            //graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height); //paint background
            
            graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height); //paint border
            graphics.setColor(getForeground());
          
        }
    }
}

