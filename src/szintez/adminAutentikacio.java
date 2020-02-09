/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szintez;


import com.sun.glass.events.KeyEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author heimdall_rh 2018.08.20.
 */
//adminautentikáció. az előre megadott jelszó és admin névvel lehet belépni.
//Admin==Admin
//Jelszó==root
public class adminAutentikacio extends javax.swing.JFrame {

    public adminAutentikacio() {
        initComponents();    
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPasswordField1 = new javax.swing.JPasswordField();

        setTitle("Admin belépés");
        setBounds(new java.awt.Rectangle(400, 220, 200, 150));

        jButton1.setText("Belép");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jButton1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton1KeyPressed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Admin:");
        jLabel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Jelszó:");
        jLabel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPasswordField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPasswordField1KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField1)
                            .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPasswordField1)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // Belép gomb
       
        
        String uname=jTextField1.getText(); //Admin megadása
        String pass=jPasswordField1.getText();
        String passwd="";//Jelszó
        
        if((!uname.isEmpty())||!pass.isEmpty()){
        for(char c:jPasswordField1.getPassword()) passwd+=c;
        if(uname.equals("Admin")&&passwd.equals("root")){//a helyes jelszó esetén belép az adminisztrációs ablakba
            
            JOptionPane.showMessageDialog(null,"Ön Belépett az Admin felületre!\n  Az adatbázishoz teljes hozzáférése van!\n Törlés Módosítás","Információ",JOptionPane.INFORMATION_MESSAGE);
            
           jTextField1.setText(""); //minden elinduláskor törli a mezők tartalmát
            jPasswordField1.setText("");
            
            Adminisztracio admin=new Adminisztracio();
            admin.setVisible(true);
            
            
        adminAutentikacio.this.setVisible(false);//Ablak bezárása
        }else{
        
          JOptionPane.showMessageDialog(null,"A jelszó nem megfelelő!","Információ",JOptionPane.INFORMATION_MESSAGE);//felugró ablak hiba esetén, és törli a mezők tartalmát
                 jTextField1.setText("");  
                 jPasswordField1.setText("");
                 
        }          
        }else{
         JOptionPane.showMessageDialog(null,"A mező üres!","Információ",JOptionPane.INFORMATION_MESSAGE);//Üres mező esetén
        }         
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jPasswordField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPasswordField1KeyPressed
      if(evt.getKeyCode()==KeyEvent.VK_ENTER){//A passworfield mezőben ha entert nyomunk elindul a login folyamat
           // Belép gomb
       
        
        String uname=jTextField1.getText(); //Admin megadása
        String pass=jPasswordField1.getText();
        String passwd="";//Jelszó
        
        if((!uname.isEmpty())||!pass.isEmpty()){
        for(char c:jPasswordField1.getPassword()) passwd+=c;
        if(uname.equals("Admin")&&passwd.equals("root")){//a helyes jelszó esetén belép az adminisztrációs ablakba
            
            JOptionPane.showMessageDialog(null,"Ön Belépett az Admin felületre!\n  Az adatbázishoz teljes hozzáférése van!\n Törlés Módosítás","Információ",JOptionPane.INFORMATION_MESSAGE);
            
           jTextField1.setText(""); //minden elinduláskor törli a mezők tartalmát
            jPasswordField1.setText("");
            
            Adminisztracio admin=new Adminisztracio();
            admin.setVisible(true);
            
            
        adminAutentikacio.this.setVisible(false);//Ablak bezárása
        }else{
        
          JOptionPane.showMessageDialog(null,"A jelszó nem megfelelő!","Információ",JOptionPane.INFORMATION_MESSAGE);//felugró ablak hiba esetén, és törli a mezők tartalmát
                 jTextField1.setText("");  
                 jPasswordField1.setText("");
                 
        }          
        }else{
         JOptionPane.showMessageDialog(null,"A mező üres!","Információ",JOptionPane.INFORMATION_MESSAGE);//Üres mező esetén
        }         
      }
    }//GEN-LAST:event_jPasswordField1KeyPressed

    private void jButton1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){//Belép gombon enter nyomására indúl a login folyamat
           // Belép gomb
       
        
        String uname=jTextField1.getText(); //Admin megadása
        String pass=jPasswordField1.getText();
        String passwd="";//Jelszó
        
        if((!uname.isEmpty())||!pass.isEmpty()){
        for(char c:jPasswordField1.getPassword()) passwd+=c;
        if(uname.equals("Admin")&&passwd.equals("root")){//a helyes jelszó esetén belép az adminisztrációs ablakba
            
            JOptionPane.showMessageDialog(null,"Ön Belépett az Admin felületre!\n  Az adatbázishoz teljes hozzáférése van!\n Törlés Módosítás","Információ",JOptionPane.INFORMATION_MESSAGE);
            
           
             
            jTextField1.setText(""); //minden elinduláskor törli a mezők tartalmát
            jPasswordField1.setText("");
            Adminisztracio admin=new Adminisztracio();
            admin.setVisible(true);
           
        adminAutentikacio.this.setVisible(false);//Ablak bezárása
        }else{
        
          JOptionPane.showMessageDialog(null,"A jelszó nem megfelelő!","Információ",JOptionPane.INFORMATION_MESSAGE);//felugró ablak hiba esetén, és törli a mezők tartalmát
                 jTextField1.setText("");  
                 jPasswordField1.setText("");
                 
        }          
        }else{
         JOptionPane.showMessageDialog(null,"A mező üres!","Információ",JOptionPane.INFORMATION_MESSAGE);//Üres mező esetén
        }         
      }
    }//GEN-LAST:event_jButton1KeyPressed

        
    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(adminAutentikacio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(adminAutentikacio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(adminAutentikacio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(adminAutentikacio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new adminAutentikacio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
