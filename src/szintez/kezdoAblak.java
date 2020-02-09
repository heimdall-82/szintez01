
package szintez;

import com.sun.glass.events.KeyEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;




/**
 *
 * @author heimdall_rh 2018.08.20.
 */
//a kezdoablak biztosítja a regisztrációt és bejelentkezést.

//getConnection==adatbázis kapcsolat
//Reg_Button()== //Regisztráció_Elkuld gomb elindítja a regisztrációs folyamatot (mezők üressége, email egyezőség adatbázisban, password egyezés) ,vizsgálatot.
//email_Check()==az emailvalidácóért felel.
//passwd_hossz()==8 karakternél nagyobbnak kell lennie.hosszt ellenőriz
//passwdEll()==Leütött karakterek ellenőrzése
//urit()==kiüríti a regisztrációs panel textfieldjeit
//login()==bejelentkezés panel. Ellenőrzi a leütütt karaktereket és minden ok megkeresi az emailt az adatbázisban. ha megvan ellenörzi a jelszót. 
//emailcheck()==Ellenőrzi az emailt hogy létezik e az adatbázisban.
//regisztraciogomb()==a person táblába felviszi a textfieldekbe beírt adatokat.
//kezdoAblak()==swing beállított elemek. elindítja a database létrehozását ha még nem létezik.
public class kezdoAblak extends javax.swing.JFrame {
                                    
        //Adatbázis kapcsolat
    PreparedStatement pst=null;
    ResultSet rs=null;
     String ures="";//Ures string. 
     
 public Connection getConnection() {
        Connection kapcsolat=null;
        try{
            kapcsolat=DriverManager.getConnection("jdbc:ucanaccess://szintezData.accdb");
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return kapcsolat;
    }

public void Reg_Button(){ //Regisztráció_Elkuld gomb elindítja a regisztrációs folyamatot (mezők üressége, email egyezőség adatbázisban, password egyezés) ,vizsgálatot.
   
 int a=1;
        
while(true){//A folyamat addig megy amíg nem talál break befejezést.
          
       if(Veznev_Input.getText().isEmpty()||Kernev_Input.getText().isEmpty()||Email_Input.getText().isEmpty()||Password1_Input.getText().isEmpty()||Password2_Input.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"A mező kitöltése kötelező!","Információ",JOptionPane.INFORMATION_MESSAGE);//Felugro üzenet. Ok gomb megnyomására eltűnik.
             //Ez az if ág az összes input ürességét vizsgálja, ha talál üres mezőt felugró ablakkal jelzi, mindaddig míg van üres input.
       }  
       boolean status=kezdoAblak.email_check(Email_Input.getText());  //Az email vizsgáló csak az email_check metódusban megadott 
        if(status==true){                                                   //formátumú emailt fogadja el.34sor
            //status = true--- megjelenik egy labelen az ok felirat ha megfelel az email, és tovább lép.
             Email_Check_jelzes.setText("OK."); 
         
             
        
        }else{
                   
           
           break;//Ha nem megfelelő az email formátum akkor kilép a ciklusból
        }
       if(kezdoAblak.passwd_Hossz(Password1_Input.getText())){//A passwdd_hossz és a passwdEll metódusok végzik a jelszó validálást
          Jelszo_Ell.setText("Ok.");                          //ha rendben van ok felirat lesz látható. A hibákat a metódusok jelzik
                                                              //felugró message formályában.46 és63 sor
       
       if(!Arrays.equals(Password1_Input.getPassword(),Password2_Input.getPassword())) { //Password ellenőrzés, egyezőséget vizsgál             
            JOptionPane.showMessageDialog(null,"A jelszó nem egyezik!","Információ",JOptionPane.INFORMATION_MESSAGE);  
            
         }else{                               
                  
             try {               
                  do{//sikeres regisztráció
                      
                      regisztracioGomb();//elindítja a regisztrációs folyamatot 141sor
                      a++;
                      Regisztracios_Panel.setVisible(false);//Panel beztáródik
                      Bejelentkezo_Panel.setVisible(true);//és előjön a bejelentkezési panel
                  }while(a<=1); 
                                 
                 } catch (ClassNotFoundException ex) {
                          Logger.getLogger(kezdoAblak.class.getName()).log(Level.SEVERE, null, ex);
                 } catch (SQLException ex) {
                          Logger.getLogger(kezdoAblak.class.getName()).log(Level.SEVERE, null, ex);
                 } catch (InterruptedException ex) {
                          Logger.getLogger(kezdoAblak.class.getName()).log(Level.SEVERE, null, ex);
                 }                   
                                         
              }   

       } 
       
           break;   
         } 
}
public static boolean email_check(String email){//Email validáció
     boolean status= false;//Statusz alap false
     String emailEgyezes="^(\\w+\\.)*\\w+@\\w+(\\.\\w+)*\\.[a-zA-Z]{2,6}$";//Email megfelelő formátum
     Pattern mail=Pattern.compile(emailEgyezes);         
     Matcher mailcheck=mail.matcher(email);//Összehasonlitja az egyezést 
     if (mailcheck.matches()) {
         status=true;// ha egyezik a status igazra vált
     }else{
         JOptionPane.showMessageDialog(null,"Az Email formátom nem megfelelő!\n    Ellenőrizd!","Információ",JOptionPane.INFORMATION_MESSAGE); 
         //Felugró ablak jelzi a helytelen email formátumot. Addig nem enged tovább amíg nem javítottuk   
         status=false;
         
     }
  return status;                     
 }
public static boolean passwd_Hossz(String passwd){ //A jelszó hossza ellenőrzés. min 6 karakter        
     if (passwd.length()>5) {//ha a hossz nagyobb mint 6 karakter akkor igaz ágba lép.
         if (passwdEll(passwd)) {//igaz ágban átugrik a karakter ellenőrzésre amit a passwdEll metodus végez
             return true;
         }
         else
         {
           return false;  //hamis ág
         }
     }else{
         JOptionPane.showMessageDialog(null, "A jelszó minimum 6 karakter!","Információ",JOptionPane.INFORMATION_MESSAGE);
         //hamis ág felugró ablak jelzi hogy rövid a jelszó
         return false;
     }
     
 }
public static boolean passwdEll(String passwd){
   boolean szam=false;
   boolean nagyBetu=false;
   boolean kisBetu=false;
   char c;
     for (int i = 0; i < passwd.length(); i++) //A bevitel egész hosszát ellenőrzi
     {
       c=passwd.charAt(i);
         if (Character.isDigit(c))  //Szám ellenőrzés
         {
             szam=true;
         }
         else if(Character.isUpperCase(c))//Nagybetü ellenőrzés
         {
             nagyBetu=true;
         }
         else if(Character.isLowerCase(c))//kisbetü ellenőrzés
         {
             kisBetu=true;
         }  
         if(szam&&nagyBetu&&kisBetu)
         {
             return true;//ha mindhárom feltétel teljesül tovább lép
         }
     }
    JOptionPane.showMessageDialog(null, "A jelszó tartalmazzon kis és nagybetüt és számot!","Információ",JOptionPane.INFORMATION_MESSAGE);//ha nem message ablak bukkan fel. ok gomb megnyomása után folytathatjuk
   return false;
   
 }
public void urit(){//a testfield beviteli mezőket üresre állítja
        Veznev_Input.setText("");
        Kernev_Input.setText("");
        Email_Input.setText("");
        Password1_Input.setText("");
        Password2_Input.setText("");  
       
 }
public void login() throws ClassNotFoundException, SQLException, IOException{
       String em=Login_Email_Input.getText();//az üresség vszgálat miatt
        String ps=Login_Password_Input.getText();//az üresség vszgálat miatt
        Connection kapcsolat=getConnection();
         if((!em.isEmpty())||!ps.isEmpty()){//ha nem üres a beviteli mező akkor lefut az autentikáció
         Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");                                                  
         String sql="SELECT * FROM person WHERE email=? AND passwd=?";
        
         pst=kapcsolat.prepareStatement(sql);
         pst.setString(1, Login_Email_Input.getText());//A mező tartalmát keresi az adatbázisban
         pst.setString(2, Login_Password_Input.getText());//A mező tartalmát keresi az adatbázisban és ellenőrzi a helyességét
         rs=pst.executeQuery();                            
         if(rs.next()){//ha megvan
             
           FoAblak ablak=new FoAblak(em);//sikeres login esetén megnyitja a főablakot
           ablak.setVisible(true);
           Login_Email_Input.setText("");//üresre állítja a mező tartalmát
             Login_Password_Input.setText("");
        
        Bejelentkezo_Panel.setVisible(false);//login panel bezár
         }else{//ha nem talál egyezést akkor felugró ablakban jelzi
             JOptionPane.showMessageDialog(null,"A jelszó vagy az Email nem megfelelő!","Információ",JOptionPane.INFORMATION_MESSAGE);
             Login_Email_Input.setText("");//üresre állítja a mező tartalmát
             Login_Password_Input.setText("");
         }
         }else{
             JOptionPane.showMessageDialog(null,"A mező nem maradhat üresen!","Információ",JOptionPane.INFORMATION_MESSAGE);//üres mező esetén
         }  
    }//belépés panel
public void emailcheck() throws ClassNotFoundException, SQLException{
         //adatbázis kapcsolat 
         Connection kapcsolat=getConnection();//
         String sql="SELECT email FROM person WHERE email=?";//lekérdezés ami megnézi van e már ilyen email
         pst=kapcsolat.prepareStatement(sql);
         pst.setString(1, Email_Input.getText());//vizsgálja a beírt email címet amit a regisztrációs ablakban a jtextfield3 irtunk be.
         rs=pst.executeQuery();
         if(rs.next()){//ha a viszgálat talál egyezést , felugró ablak jelzi.
              JOptionPane.showMessageDialog(null,"Ilyen email már van!","Információ",JOptionPane.INFORMATION_MESSAGE);//ok gombra kattintás után törli a mező tartalmát
              Email_Input.setText("");//Ha létezik az email akkor törli a mező tartalmát
         }else{
             
             //ha nincs egyezés nem történik semmi
         }
         
    }//email ellenőrzés
public void regisztracioGomb() throws ClassNotFoundException, SQLException, InterruptedException{     
      int primkey=0;
      Connection kapcsolat=getConnection();//
    Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");       //adatbázis kapcsolat                                  //        
       try{
             String sql="INSERT INTO person(veznev,kernev,email,passwd)VALUES(?,?,?,?)";//person táblába felviszi a mezőket
             PreparedStatement parancs=kapcsolat.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);//uj id minden regisztrációnak
             String  //Stringként deklarálom a név és emailt
                    Veznev= Veznev_Input.getText(),//
                    Kernev =Kernev_Input.getText(),//A beviteli mezők átadják a bevitelt a stringnek
                    email = Email_Input.getText(),//
                    password =String.valueOf(Password1_Input.getPassword());//passwordot getpassword metodussal a titkosítás miatt.

         parancs.setString(1, Veznev);
         parancs.setString(2, Kernev);
         parancs.setString(3, email);
         parancs.setString(4, password);
       
         if (parancs.executeUpdate() > 0) {
                  java.sql.ResultSet generatedKeys = parancs.getGeneratedKeys();
            if ( generatedKeys.next() ) {
                primkey = generatedKeys.getInt(1);
            }
         }
       
          

         int n=parancs.executeUpdate(sql);
         if (n==1) {
            kapcsolat.close();  
            
         }      
         }catch(Exception e){}
            JOptionPane.showMessageDialog(null,"A regisztráció sikeres!","Információ",JOptionPane.INFORMATION_MESSAGE);     
    }//regisztráció       
public kezdoAblak() throws ClassNotFoundException, IOException, SQLException {
       initComponents(); 
       Regisztracios_Panel.setVisible(false);//Nem látható alapértelmezetten
       Bejelentkezo_Panel.setVisible(false);//Nem látható alapértelmezetten
       szintez.database.data();//A kezdő oldallal együtt elindul az adatbázis létrehozása ha még nem létezne. 
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        Bejelentkezes_Button = new javax.swing.JButton();
        Regisztracios_Panel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        Veznev_Input = new javax.swing.JTextField();
        Kernev_Input = new javax.swing.JTextField();
        Email_Input = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        Reg_Elkuld_Button = new javax.swing.JButton();
        Reg_Megse_Button = new javax.swing.JButton();
        Password1_Input = new javax.swing.JPasswordField();
        Password2_Input = new javax.swing.JPasswordField();
        Email_Check_jelzes = new javax.swing.JLabel();
        Jelszo_Ell = new javax.swing.JLabel();
        Bejelentkezo_Panel = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        Login_Email_Input = new javax.swing.JTextField();
        Login_Bejelentkezes_Button = new javax.swing.JButton();
        Login_Megse_Button = new javax.swing.JButton();
        Login_Password_Input = new javax.swing.JPasswordField();
        Regisztracio_Button = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        Hatterkep_Label = new javax.swing.JLabel();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Üdvözöllek az erősítőszintezés gyakorló programban!");
        setFocusTraversalPolicyProvider(true);
        setIconImages(null);
        setLocation(new java.awt.Point(200, 80));
        setMinimumSize(new java.awt.Dimension(900, 600));
        setResizable(false);
        setSize(new java.awt.Dimension(900, 600));
        getContentPane().setLayout(null);

        Bejelentkezes_Button.setText("Bejelentkezés");
        Bejelentkezes_Button.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Bejelentkezes_Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Bejelentkezes_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Bejelentkezes_ButtonActionPerformed(evt);
            }
        });
        getContentPane().add(Bejelentkezes_Button);
        Bejelentkezes_Button.setBounds(680, 460, 120, 22);

        Regisztracios_Panel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.gray));
        Regisztracios_Panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/szintez_Icon/icons8-contacts-24.png"))); // NOI18N
        jLabel2.setText("Vezetéknév");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        Regisztracios_Panel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 100, -1));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/szintez_Icon/icons8-contacts-24.png"))); // NOI18N
        jLabel3.setText("Keresztnév");
        jLabel3.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        Regisztracios_Panel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/szintez_Icon/icons8-secured-letter-24.png"))); // NOI18N
        jLabel4.setText("Email");
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        Regisztracios_Panel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, -1, -1));

        Veznev_Input.setFont(new java.awt.Font("Leelawadee UI Semilight", 0, 14)); // NOI18N
        Veznev_Input.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        Veznev_Input.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                Veznev_InputKeyTyped(evt);
            }
        });
        Regisztracios_Panel.add(Veznev_Input, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 104, -1));

        Kernev_Input.setFont(new java.awt.Font("Leelawadee UI Semilight", 0, 14)); // NOI18N
        Kernev_Input.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        Kernev_Input.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                Kernev_InputKeyTyped(evt);
            }
        });
        Regisztracios_Panel.add(Kernev_Input, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 80, 104, -1));

        Email_Input.setFont(new java.awt.Font("Leelawadee UI Semilight", 0, 14)); // NOI18N
        Email_Input.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        Email_Input.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Email_InputKeyReleased(evt);
            }
        });
        Regisztracios_Panel.add(Email_Input, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 130, 104, -1));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/szintez_Icon/icons8-password-24.png"))); // NOI18N
        jLabel1.setText("Jelszó");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        Regisztracios_Panel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, -1, -1));

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/szintez_Icon/icons8-validation-24.png"))); // NOI18N
        jLabel5.setText("Jelszó ell");
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        Regisztracios_Panel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, -1, -1));

        Reg_Elkuld_Button.setText("Elküld");
        Reg_Elkuld_Button.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Reg_Elkuld_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Reg_Elkuld_ButtonActionPerformed(evt);
            }
        });
        Reg_Elkuld_Button.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Reg_Elkuld_ButtonKeyPressed(evt);
            }
        });
        Regisztracios_Panel.add(Reg_Elkuld_Button, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 270, 104, -1));

        Reg_Megse_Button.setText("Mégse");
        Reg_Megse_Button.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Reg_Megse_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Reg_Megse_ButtonActionPerformed(evt);
            }
        });
        Reg_Megse_Button.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Reg_Megse_ButtonKeyPressed(evt);
            }
        });
        Regisztracios_Panel.add(Reg_Megse_Button, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 310, -1, -1));

        Password1_Input.setFont(new java.awt.Font("Leelawadee UI Semilight", 0, 14)); // NOI18N
        Password1_Input.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        Regisztracios_Panel.add(Password1_Input, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 180, 105, -1));

        Password2_Input.setFont(new java.awt.Font("Leelawadee UI Semilight", 0, 14)); // NOI18N
        Password2_Input.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        Password2_Input.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Password2_InputKeyPressed(evt);
            }
        });
        Regisztracios_Panel.add(Password2_Input, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 230, 105, -1));

        Email_Check_jelzes.setEnabled(false);
        Regisztracios_Panel.add(Email_Check_jelzes, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 130, 20, 20));
        Regisztracios_Panel.add(Jelszo_Ell, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 200, 50, 30));

        getContentPane().add(Regisztracios_Panel);
        Regisztracios_Panel.setBounds(90, 20, 303, 380);

        Bejelentkezo_Panel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.gray));
        Bejelentkezo_Panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/szintez_Icon/icons8-secured-letter-24.png"))); // NOI18N
        jLabel6.setText("Email");
        jLabel6.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        Bejelentkezo_Panel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/szintez_Icon/icons8-password-24.png"))); // NOI18N
        jLabel7.setText("Jelszó");
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        Bejelentkezo_Panel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 60, 70, -1));

        Login_Email_Input.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        Bejelentkezo_Panel.add(Login_Email_Input, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, 120, 25));

        Login_Bejelentkezes_Button.setText("Bejelentkezés");
        Login_Bejelentkezes_Button.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Login_Bejelentkezes_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Login_Bejelentkezes_ButtonActionPerformed(evt);
            }
        });
        Login_Bejelentkezes_Button.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Login_Bejelentkezes_ButtonKeyPressed(evt);
            }
        });
        Bejelentkezo_Panel.add(Login_Bejelentkezes_Button, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 110, -1, -1));

        Login_Megse_Button.setText("Mégse");
        Login_Megse_Button.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Login_Megse_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Login_Megse_ButtonActionPerformed(evt);
            }
        });
        Login_Megse_Button.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Login_Megse_ButtonKeyPressed(evt);
            }
        });
        Bejelentkezo_Panel.add(Login_Megse_Button, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 140, -1, -1));

        Login_Password_Input.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        Login_Password_Input.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Login_Password_InputKeyPressed(evt);
            }
        });
        Bejelentkezo_Panel.add(Login_Password_Input, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, 120, 25));

        getContentPane().add(Bejelentkezo_Panel);
        Bejelentkezo_Panel.setBounds(490, 70, 240, 210);

        Regisztracio_Button.setText("Regisztráció");
        Regisztracio_Button.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Regisztracio_Button.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        Regisztracio_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Regisztracio_ButtonActionPerformed(evt);
            }
        });
        getContentPane().add(Regisztracio_Button);
        Regisztracio_Button.setBounds(90, 460, 110, 22);
        getContentPane().add(jPanel3);
        jPanel3.setBounds(1566, 627, 10, 10);

        Hatterkep_Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Hatterkep_Label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/szintez_image/MT1000.jpg"))); // NOI18N
        Hatterkep_Label.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Hatterkep_Label.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        getContentPane().add(Hatterkep_Label);
        Hatterkep_Label.setBounds(40, 0, 800, 511);

        jMenuBar2.setBorder(null);
        jMenuBar2.setAlignmentX(0.0F);

        jMenu3.setText("File");

        jMenuItem1.setText("Admin");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem1);

        jMenuItem2.setText("Kilépés");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem2);

        jMenuItem4.setText("Kapcsolat");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);

        jMenuBar2.add(jMenu3);

        setJMenuBar(jMenuBar2);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Regisztracio_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Regisztracio_ButtonActionPerformed
       urit();//A regisztrációs gomb minden kattintásra törli a mezők tartalmát , hogy üresek legyenek
       Email_Check_jelzes.setText(ures);//ures a jelzes, csak akkor ír ki ok-ot ha az email megfelel a regex-nek.
       Regisztracios_Panel.setVisible(true);//A kezdoAblak Regisztráció gombja elindítja a Regisztráció Panelt
       Regisztracio_Button.setVisible(false);//Eltűnik a regisztrációs gomb
    }//GEN-LAST:event_Regisztracio_ButtonActionPerformed

    private void Bejelentkezes_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Bejelentkezes_ButtonActionPerformed
        Regisztracios_Panel.setVisible(false);//A bejelentkezés gomb megnyomására eltűnik a regisztrációs panel,
        Bejelentkezo_Panel.setVisible(true);//és láthatóvá válik a bejelentkező panel.
        Regisztracio_Button.setVisible(false);//Eltűnik a regisztrációs gomb
        Bejelentkezes_Button.setVisible(false);//Eltűnik a Bejelentkezés gomb
    }//GEN-LAST:event_Bejelentkezes_ButtonActionPerformed

    private void Reg_Megse_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Reg_Megse_ButtonActionPerformed
      Regisztracios_Panel.setVisible(false);//A regisztrációs panel Mégse gombja megnyomásával a panel bezáródik
      Regisztracio_Button.setVisible(true);//és előjön a regisztráció gomb
    }//GEN-LAST:event_Reg_Megse_ButtonActionPerformed

    private void Login_Megse_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Login_Megse_ButtonActionPerformed
     Bejelentkezo_Panel.setVisible(false);//Bejelentkező panel mégse-gomb bezárja a Login panelt
     Bejelentkezes_Button.setVisible(true);//A kezdoAblak Bejelentkezés gombja elindítja a Bejelentkezés Panelt
     Regisztracio_Button.setVisible(true);
    }//GEN-LAST:event_Login_Megse_ButtonActionPerformed

    private void Reg_Elkuld_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Reg_Elkuld_ButtonActionPerformed
        Reg_Button();
                 
    }//GEN-LAST:event_Reg_Elkuld_ButtonActionPerformed
        
    private void Login_Bejelentkezes_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Login_Bejelentkezes_ButtonActionPerformed
              
        try {
            login();//Login gomb elindítja a bejelentkezést a login metoduson keresztű 101sor
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(kezdoAblak.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(kezdoAblak.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(kezdoAblak.class.getName()).log(Level.SEVERE, null, ex);
        }
               
    }//GEN-LAST:event_Login_Bejelentkezes_ButtonActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
    adminAutentikacio belep=new adminAutentikacio();//Elindítja az admin felület autentikációját
    belep.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
    System.exit(0);//kilép a programból ablak menügomb
      
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void Veznev_InputKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Veznev_InputKeyTyped
      //Vetetéknév leütött karakterek ellenőrzése (validálás) jTextField1
      char c=evt.getKeyChar();
      if (Character.isDigit(c)||!Character.isJavaIdentifierPart(c)||(c == java.awt.event.KeyEvent.VK_BACK_SPACE)) {
         evt.consume(); 
      }
    }//GEN-LAST:event_Veznev_InputKeyTyped

    private void Kernev_InputKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kernev_InputKeyTyped
        //Keresztnév leütött karakterek ellenőrzése (validálás) jTextField2
      char c=evt.getKeyChar();
      if (Character.isDigit(c)||!Character.isJavaIdentifierPart(c)||(c == java.awt.event.KeyEvent.VK_BACK_SPACE)) {
         evt.consume();  
      }
    }//GEN-LAST:event_Kernev_InputKeyTyped

    private void Email_InputKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Email_InputKeyReleased
        try {
            emailcheck(); //Ellenőrzi az adatbázist létezik e az email cím 128.sor
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(kezdoAblak.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(kezdoAblak.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_Email_InputKeyReleased

    private void Reg_Elkuld_ButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Reg_Elkuld_ButtonKeyPressed
       if(evt.getKeyCode()==KeyEvent.VK_ENTER){//Enter gomb megnyomására Reg_Button()metódus elindítja a regisztrációs folyamatot
           Reg_Button();
       }
    }//GEN-LAST:event_Reg_Elkuld_ButtonKeyPressed

    private void Reg_Megse_ButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Reg_Megse_ButtonKeyPressed
     if(evt.getKeyCode()==KeyEvent.VK_ENTER){//Enter gomb megnyomására 
      Regisztracios_Panel.setVisible(false);//Regisztrációs panel bezár
      Regisztracio_Button.setVisible(true);//Regisztrációs gomb előjön
       }
    }//GEN-LAST:event_Reg_Megse_ButtonKeyPressed

    private void Password2_InputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Password2_InputKeyPressed
         if(evt.getKeyCode()==KeyEvent.VK_ENTER){
          Reg_Button();//Enter gomb megnyomására Reg_Button()metódus elindítja a regisztrációs folyamatot
         }
    }//GEN-LAST:event_Password2_InputKeyPressed

    private void Login_Password_InputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Login_Password_InputKeyPressed
       if(evt.getKeyCode()==KeyEvent.VK_ENTER){try {
           //Enter gomb megnyomására
           login();//Login gomb elindítja a bejelentkezést a login metoduson keresztű 101sor
           } catch (ClassNotFoundException ex) {
               Logger.getLogger(kezdoAblak.class.getName()).log(Level.SEVERE, null, ex);
           } catch (SQLException ex) {
               Logger.getLogger(kezdoAblak.class.getName()).log(Level.SEVERE, null, ex);
           } catch (IOException ex) {
               Logger.getLogger(kezdoAblak.class.getName()).log(Level.SEVERE, null, ex);
           }
       } 
    }//GEN-LAST:event_Login_Password_InputKeyPressed

    private void Login_Bejelentkezes_ButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Login_Bejelentkezes_ButtonKeyPressed
       if(evt.getKeyCode()==KeyEvent.VK_ENTER){try {
           //Enter gomb megnyomására
           login();//Login gomb elindítja a bejelentkezést a login metoduson keresztű 101sor
           } catch (ClassNotFoundException ex) {
               Logger.getLogger(kezdoAblak.class.getName()).log(Level.SEVERE, null, ex);
           } catch (SQLException ex) {
               Logger.getLogger(kezdoAblak.class.getName()).log(Level.SEVERE, null, ex);
           } catch (IOException ex) {
               Logger.getLogger(kezdoAblak.class.getName()).log(Level.SEVERE, null, ex);
           }
       } 
    }//GEN-LAST:event_Login_Bejelentkezes_ButtonKeyPressed

    private void Login_Megse_ButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Login_Megse_ButtonKeyPressed
      if(evt.getKeyCode()==KeyEvent.VK_ENTER){//Enter gomb megnyomására 
     Bejelentkezo_Panel.setVisible(false);//Bejelentkező panel mégse-gomb bezárja a Login panelt
     Bejelentkezes_Button.setVisible(true);//A kezdoAblak Bejelentkezés gombja elindítja a Bejelentkezés Panelt
     Regisztracio_Button.setVisible(true);//Regisztrációs gomb előjön
       }
    }//GEN-LAST:event_Login_Megse_ButtonKeyPressed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
kapcsolat g=new kapcsolat();
g.setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed
           
   
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
            java.util.logging.Logger.getLogger(kezdoAblak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(kezdoAblak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(kezdoAblak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(kezdoAblak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new kezdoAblak().setVisible(true);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(kezdoAblak.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(kezdoAblak.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(kezdoAblak.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Bejelentkezes_Button;
    private javax.swing.JPanel Bejelentkezo_Panel;
    private javax.swing.JLabel Email_Check_jelzes;
    private javax.swing.JTextField Email_Input;
    private javax.swing.JLabel Hatterkep_Label;
    private javax.swing.JLabel Jelszo_Ell;
    private javax.swing.JTextField Kernev_Input;
    private javax.swing.JButton Login_Bejelentkezes_Button;
    private javax.swing.JTextField Login_Email_Input;
    private javax.swing.JButton Login_Megse_Button;
    private javax.swing.JPasswordField Login_Password_Input;
    private javax.swing.JPasswordField Password1_Input;
    private javax.swing.JPasswordField Password2_Input;
    private javax.swing.JButton Reg_Elkuld_Button;
    private javax.swing.JButton Reg_Megse_Button;
    private javax.swing.JButton Regisztracio_Button;
    private javax.swing.JPanel Regisztracios_Panel;
    private javax.swing.JTextField Veznev_Input;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel3;
    // End of variables declaration//GEN-END:variables
 
    }
        
    

