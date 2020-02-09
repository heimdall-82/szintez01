/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szintez;

import com.sun.glass.events.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;



/**
 *
 * @author heimdall_rh 2018.08.20.
 */
public class Adminisztracio extends javax.swing.JFrame {

    /**
     * Creates new form Adminisztracio
     */
 
          
 
 
  public Adminisztracio() {
        initComponents();
        
     keres();//kilistázza a table szemelyek be a usereket.
    }   
  public Connection getConnection() {//adatbázis kapcsolatért felel
        Connection con=null;
        try{
            con=DriverManager.getConnection("jdbc:ucanaccess://szintezData.accdb");
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return con;
    }
  public ArrayList<adat>ListUsers(int Text_Id){//Az adatbázisban minden elem integer, a text mezőt és integernek deklaráltam .
 
    ArrayList<adat> adatlist=new ArrayList<adat>();//Az adat osztálythoz adja az adatbázi megfelelő sorának az elemeit.
    Statement st;
    ResultSet rs;
     
       try {
    
    Connection con=getConnection();//kapcsolat
    st=con.createStatement();
    
    
  String sql="SELECT adatokid,personid,behigh,below,eq1,at1,eq2,at2,eq3,at3,kilow,kihigh,tilt,sweeplow,sweephigh FROM adatok WHERE personid ='" +Text_Id+ "'";
     rs=st.executeQuery(sql);//A text_id használja a megfelelő sor kiválasztásához. ami a mezőben van azt az id t keresi az adatbázisban.
    
        adat adatok;
        
        while(rs.next())
        {
         adatok=new adat
                 (
            rs.getInt("adatokid"),
            rs.getInt("personid"),
            rs.getInt("behigh"),
            rs.getInt("below"),
            rs.getInt("eq1"),
            rs.getInt("at1"),
            rs.getInt("eq2"),
            rs.getInt("at2"),
            rs.getInt("eq3"),
            rs.getInt("at3"),
            rs.getInt("kilow"),
            rs.getInt("kihigh"),
            rs.getInt("tilt"),
            rs.getInt("sweeplow"),
            rs.getInt("sweephigh")
            
            );//Hozzáadja az értékeket az osztályhoz
            adatlist.add(adatok);
        }
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    return adatlist;
  }  
  public void keres2(){
      int k =Integer.parseInt(Text_Id.getText());  //PROBÉMA: Az admin felületen a text_id minde elemét kilistázza. tehát ha a 12-idt szeretném akkor az 1 és 2-es id adatait is. 
                                                            //MEGOLDVA: az sql utasításban LIKE volt ez nem helyes mert ez stringként olvassa be az intet.
     ArrayList<adat> userlist=ListUsers(k);
     DefaultTableModel model1=new DefaultTableModel();//A táblához hozzáadja a 14 elemet . elnevezi az oszlopokat és hozzáadja az adat osztály elemeit.
     model1.setColumnIdentifiers(new Object[]{"Id","Azonosító","below","behigh","eq1","at1","eq2","at2","eq3","at3","kilow","kihigh","tilt","sweeplow","sweephigh"});
     Object[]row=new Object[15];//14 oszlop létrehozása
     for (int i = 0; i < userlist.size(); i++) {
         row[0]=userlist.get(i).getAdatokid();
         row[1]=userlist.get(i).getPersonid();//ciklus végighalad az adat osztály összes elemén és az átadott értékeket beírja az oszlopokba.
         row[2]=userlist.get(i).getBelow();
         row[3]=userlist.get(i).getBehigh();
         row[4]=userlist.get(i).getEq1();
         row[5]=userlist.get(i).getAt1();
         row[6]=userlist.get(i).getEq2();
         row[7]=userlist.get(i).getAt2();
         row[8]=userlist.get(i).getEq3();
         row[9]=userlist.get(i).getAt3();
         row[10]=userlist.get(i).getKilow();
         row[11]=userlist.get(i).getKihigh();
         row[12]=userlist.get(i).getTilt();
         row[13]=userlist.get(i).getSweeplow();
         row[14]=userlist.get(i).getSweephigh();
         model1.addRow(row);
     }
     Adatok_Table.setModel(model1);
      
 }
  public ArrayList<UserList>ListUsersa(String Data_Keres){
    ArrayList<UserList> userlist=new ArrayList<UserList>();
    Statement st;
    ResultSet rs;   //A metódus a userlist osztályhoz adja a person tábla azon elemeit amik a data_keres textfieldbe beírásra kerülnek.
     
       try {
    
    Connection con=getConnection();
    st=con.createStatement();        
    String sql="SELECT personid,veznev,kernev,email FROM person WHERE veznev LIKE '%" +Data_Keres+ "%'";
     rs=st.executeQuery(sql);
    
        UserList user;
        
        while(rs.next())
        {
         user=new UserList
                 (
            rs.getInt("personid"),
            rs.getString("veznev"),
            rs.getString("kernev"),
            rs.getString("email")
            );
            userlist.add(user);
        }
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    return userlist;
  }
  public void keres(){
     ArrayList<UserList> userlist=ListUsersa(Data_Keres.getText());// a userlist osztály elemeit beírja a létrehozott tábla oszlopaiba.
     DefaultTableModel model=new DefaultTableModel();
     model.setColumnIdentifiers(new Object[]{"Id","Vezetéknév","Keresztnév","Email"});//Megnevezi az oszlopokat
      Object[]row=new Object[4];
     for (int i = 0; i < userlist.size(); i++) {
         row[0]=userlist.get(i).getId();
         row[1]=userlist.get(i).getVeznev();
         row[2]=userlist.get(i).getKernev();
         row[3]=userlist.get(i).getEmail();
         model.addRow(row);
     }
     Table_Szemelyek.setModel(model);
 }
  public void executeSQlQuery(String query, String message){
    Connection con=getConnection();
    Statement st;
    try{
    st=con.createStatement();//Ha a kapcsolat és az adatbázis művelet sikeres vagy sikertelen akkor felugró üzenetek komunikálnak a felhasználóval.
        if (st.executeUpdate(query)==1) {
            
            DefaultTableModel model=(DefaultTableModel)Table_Szemelyek.getModel();
            model.setRowCount(0);
            keres();            
            JOptionPane.showMessageDialog(null, "Az rekord "+message+".\nSikeres!","Információ",JOptionPane.INFORMATION_MESSAGE);
            
        }else{
            JOptionPane.showMessageDialog(null, "A rekord nincs "+message,"Információ",JOptionPane.INFORMATION_MESSAGE);
        }
    }catch(Exception ex){
        ex.printStackTrace();
    }
        
}
   @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        Keres_Button = new javax.swing.JButton();
        Data_Keres = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table_Szemelyek = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        Text_Id = new javax.swing.JTextField();
        Text_Veznev = new javax.swing.JTextField();
        Text_Kernev = new javax.swing.JTextField();
        Text_Email = new javax.swing.JTextField();
        insert_button = new javax.swing.JButton();
        Delete_Button = new javax.swing.JButton();
        Update_Button = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        Adatok_Table = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Adminisztráció");
        setLocation(new java.awt.Point(200, 40));

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setToolTipText("");
        jPanel1.setPreferredSize(new java.awt.Dimension(886, 400));

        Keres_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/szintez_Icon/find user.png"))); // NOI18N
        Keres_Button.setText("Keres");
        Keres_Button.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        Keres_Button.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        Keres_Button.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        Keres_Button.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        Keres_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Keres_ButtonActionPerformed(evt);
            }
        });
        Keres_Button.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Keres_ButtonKeyPressed(evt);
            }
        });

        Data_Keres.setFont(new java.awt.Font("Yu Gothic UI Semibold", 2, 14)); // NOI18N
        Data_Keres.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        Data_Keres.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Data_KeresKeyPressed(evt);
            }
        });

        Table_Szemelyek.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.darcula.selectedButtonForeground"));
        Table_Szemelyek.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        Table_Szemelyek.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        Table_Szemelyek.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table_SzemelyekMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Table_Szemelyek);

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI", 2, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 255));
        jLabel1.setText("Keresztnév  ");

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI", 2, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 255));
        jLabel2.setText("Vezetéknév  ");

        jLabel3.setFont(new java.awt.Font("Yu Gothic UI", 2, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 255));
        jLabel3.setText("Id  ");

        jLabel4.setFont(new java.awt.Font("Yu Gothic UI", 2, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 255));
        jLabel4.setText("Email  ");

        Text_Id.setEditable(false);
        Text_Id.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        Text_Id.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        Text_Veznev.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        Text_Veznev.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        Text_Kernev.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        Text_Kernev.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        Text_Email.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        Text_Email.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        insert_button.setText("Új Rekord");
        insert_button.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        insert_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insert_buttonActionPerformed(evt);
            }
        });

        Delete_Button.setText("Törlés");
        Delete_Button.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        Delete_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Delete_ButtonActionPerformed(evt);
            }
        });

        Update_Button.setText("Frissít");
        Update_Button.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        Update_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Update_ButtonActionPerformed(evt);
            }
        });

        Adatok_Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(Adatok_Table);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(Data_Keres, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(Keres_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 543, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(29, 29, 29)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel1)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel3))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(Text_Id, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(Text_Email)
                                            .addComponent(Text_Kernev)
                                            .addComponent(Text_Veznev, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(insert_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(Update_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(Delete_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(71, 71, 71)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Data_Keres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Keres_Button))
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(Text_Id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(Text_Veznev, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(Text_Kernev, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(Text_Email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(insert_button)
                            .addComponent(Delete_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Update_Button)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {Delete_Button, Update_Button, insert_button});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 537, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Keres_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Keres_ButtonActionPerformed
             keres();// A keres gomb indítja a beirt string keresését.
    }//GEN-LAST:event_Keres_ButtonActionPerformed

    private void Table_SzemelyekMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_SzemelyekMouseClicked
    //sor kiválasztás
    int i=Table_Szemelyek.getSelectedRow();// a személyek táblában ha rákattintunk egy sorra (választunk usert),                                        
                                            //akkor a lenti textfieldekbe kiírodik a kiválasztott felhasználó adatai.
  
    TableModel model=Table_Szemelyek.getModel();

    Text_Id.setText(model.getValueAt(i, 0).toString());//ITT KÉNE INTEGERKÉN, MIVEL EZT VIZSGÁLJA MAJD A KERES2 METÓDUS.
    Text_Veznev.setText(model.getValueAt(i, 1).toString());
    Text_Kernev.setText(model.getValueAt(i, 2).toString());
    Text_Email.setText(model.getValueAt(i, 3).toString());
   //Érték átadás után elindul a keres2 metódus ami megkeresi a text_id textfieldbe a personid párját az adatok táblában.
        keres2();
    }//GEN-LAST:event_Table_SzemelyekMouseClicked

    private void insert_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insert_buttonActionPerformed
       String query="INSERT INTO person (personid,veznev,kernev,email)VALUES ('"+Text_Id.getText()+"','"+Text_Veznev.getText()+"','"+Text_Kernev.getText()+"','"+Text_Email.getText()+"')";
       executeSQlQuery(query,"Hozzáadva");//az adott mező hozzáadása
    }//GEN-LAST:event_insert_buttonActionPerformed

    private void Delete_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Delete_ButtonActionPerformed
                String message = " Biztosan törlöd ? ";//törlés gomb üzenet
                String title = "Rekord törlés";
                int reply = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION)
                {
                    String query="DELETE FROM person WHERE personid="+Text_Id.getText();//Ha igenre nyomunk megtörténik a rekord törlése
                    executeSQlQuery(query,"Törölve");
                }
    }//GEN-LAST:event_Delete_ButtonActionPerformed

    private void Update_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Update_ButtonActionPerformed

        String query="UPDATE person SET veznev='"+Text_Veznev.getText()+"', kernev='"+Text_Kernev.getText()+"',email='"+Text_Email.getText()+"' WHERE  personid='"+Text_Id.getText()+"'";
     executeSQlQuery(query,"Frissítve");//a textfieldekbe írt változtatások felülírják a rekordot a person táblában.
    }//GEN-LAST:event_Update_ButtonActionPerformed

    private void Data_KeresKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Data_KeresKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){//A Data_keres textfieldbe enter nyomására indul a keres() metódus
            keres();// A keres gomb indítja a beirt string keresését.
        }
    }//GEN-LAST:event_Data_KeresKeyPressed

    private void Keres_ButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Keres_ButtonKeyPressed
       if(evt.getKeyCode()==KeyEvent.VK_ENTER){//A keres_Button Enter nyomására
            keres();// A keres gomb indítja a beirt string keresését.
        }
    }//GEN-LAST:event_Keres_ButtonKeyPressed

   
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
            java.util.logging.Logger.getLogger(Adminisztracio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Adminisztracio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Adminisztracio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Adminisztracio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Adminisztracio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Adatok_Table;
    private javax.swing.JTextField Data_Keres;
    private javax.swing.JButton Delete_Button;
    private javax.swing.JButton Keres_Button;
    private javax.swing.JTable Table_Szemelyek;
    private javax.swing.JTextField Text_Email;
    private javax.swing.JTextField Text_Id;
    private javax.swing.JTextField Text_Kernev;
    private javax.swing.JTextField Text_Veznev;
    private javax.swing.JButton Update_Button;
    private javax.swing.JButton insert_button;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables
}
