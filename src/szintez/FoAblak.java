
package szintez;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
/**
 *
 * @author heimdall_rh 2018.08.20.
 */
//A comtech erősítő bejövő below és behigh értékéből számolunk. az eq kártyák a kártya méretével csökkenti a low értékét.
//az at kártyák az értékükkel a low és high értékét csökkenti. a kimeneti értékhez fejben 30 at hozzá kell adni mert ennyit erősít. 
//Addig kell csökkentni amíg nem lesz a kilow és kihigh 90-100 között tehát 60-70 között.
// A kártyákat úgy kell helyezni hogy a high és low különbdsége minél kissebb legyen. Tilt -2és +2 között legyen.


public class FoAblak extends javax.swing.JFrame {
    private int tap1;
    private int sweeplow;
    private int sweephigh;
    private double tilt;
    private int kiLow;
    private int kiHigh;
    private int low,high,eq1,eq2,eq3,at1,at2,at3;
    private String email;
    String t="";//Üres mező
    PreparedStatement pst=null;
    ResultSet rs=null;
   int[]val={0,36,36};//tap választó combobox
   int [] values ={0,0,2,4,6,8,10,12,14,16,18,20};//Eq és At kártyák értéke(combobox 1,2,3,4,7,11)
   int [] valueslow={0,72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88};//Low érték beállítása. combobox5
   int [] valueshigh={0,72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88};//High érték beállítása. Combobox6
  ImageIcon ii;
public ImageIcon getImageIcon(File f){


        try
        {
            Image im = ImageIO.read(f);


            ii = new ImageIcon(im);


        }
        catch(IOException i)
        {

            i.printStackTrace();


        }
  
        finally
        {

            return ii;

        }


    }
public Connection getConnection() {
        Connection kapcsolat=null;
        try{
            kapcsolat=DriverManager.getConnection("jdbc:ucanaccess://szintezData.accdb");
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return kapcsolat;
    }
public FoAblak(String email){
        this.email=email;//Táblák összekapcsolásához kell. email alapján menti el a számításokat
        initComponents();
       jPanel1.setVisible(false);//Műszer alapértelmezetten nem jelenik meg csak a bekapcs gombbla(jButtn5)     
    }
public void urit(){
            Be_High_Box.setSelectedIndex(0);
            Be_Low_Box.setSelectedIndex(0);
            Eq1_Card.setSelectedIndex(0);
            At1_Card.setSelectedIndex(0);    
            Eq2_Card.setSelectedIndex(0);
            At2_Card.setSelectedIndex(0);          
            Vissz_Eq_Card.setSelectedIndex(0);
            Vissz_At_Card.setSelectedIndex(0);
}
public void saveToDatabase() throws ClassNotFoundException, SQLException, SQLException{
       
         int primkey=0;
                                   //
         Connection kapcsolat=getConnection(); //Adatbázis kapcsolat
             
       try{
           
           String sql="SELECT personid FROM person WHERE email=?";
           PreparedStatement parancs=kapcsolat.prepareStatement(sql);
           parancs.setString(1,email);
           ResultSet eredmeny=parancs.executeQuery();//mentésnél kiválasztja azt a persont akihez az id tartozik
           eredmeny.next();
           int personid=eredmeny.getInt(1); 
           System.out.println(personid);
             sql="INSERT INTO adatok(personid,behigh,below,eq1,at1,eq2,at2,eq3,at3,kilow,kihigh,tilt,sweeplow,sweephigh)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
             parancs=kapcsolat.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);//Az adatok táblában létrehozott
                 //int personid=Integer.parseInt(Id_Text.getText());                                                                                 //pid-et nem rendeli hozzá a person
                  int behigh=Be_High_Box.getSelectedIndex();      //Csak a beállított érték             //tábla felhasználóihoz.
                  int below=Be_Low_Box.getSelectedIndex();      //Csak a beállított érték 
                  eq1=Eq1_Card.getSelectedIndex();
                  at1=At1_Card.getSelectedIndex();
                                                                                        //
                  eq2=Eq2_Card.getSelectedIndex();                                      //Nem rendeli hozzá az adatok tábában a personidhoz azt aki elmentette.
                  at2=At2_Card.getSelectedIndex();                                      //
                  
                  eq3=Vissz_Eq_Card.getSelectedIndex();
                  at3=Vissz_At_Card.getSelectedIndex();
                  
        below=valueslow[below];         //
        behigh=valueshigh[behigh];      //
        eq1=values[eq1];                //
        at1=values[at1];                //Combobox tömb elemének értéke átadása
        eq2=values[eq2];                //
        at2=values[at2];                //
        eq3=values[eq3];                //
        at3=values[at3];                //      
        
         parancs.setInt(1, personid);
         parancs.setInt(2, behigh);  //Bemeneti high
         parancs.setInt(3, below);   //Bemeneti low
         parancs.setInt(4, eq1);     //
         parancs.setInt(5, at1);     //
         parancs.setInt(6, eq2);     //
         parancs.setInt(7, at2);     //Kártyák értéke 
         parancs.setInt(8, eq3);     //
         parancs.setInt(9, at3);     //
         parancs.setInt(10, kiLow);   //Kimeneti low
         parancs.setInt(11, kiHigh); //Kimeneti high
         parancs.setDouble(12, tilt);//High és low különbsége
         parancs.setInt(13,sweeplow);//Visszirány low
         parancs.setInt(14,sweephigh);//Visszirány high
       
        
         if (parancs.executeUpdate() > 0) {
                  java.sql.ResultSet generatedKeys = parancs.getGeneratedKeys();//A következő mentett rekordnak készít id-t
            if ( generatedKeys.next() ) {
                primkey = generatedKeys.getInt(1);
            }
         }
       
          

         int n=parancs.executeUpdate(sql);
         if (n==1) {
            kapcsolat.close();   
         }      
         }catch(Exception e){}
               
    }       
public void visszIrany(){
      at1=At1_Card.getSelectedIndex();//at1 combobox  a váklasztott értékekkl csökken a HIGH és a LOW
        at2=At2_Card.getSelectedIndex();//at2 combobox  a váklasztott értékekkl csökken a HIGH és a LOW
        at3=Vissz_At_Card.getSelectedIndex();//
        eq1=Eq1_Card.getSelectedIndex();//eq1 combobox. a választott értékkel CSÖKKEN a LOW,,,,aHIGH NEM Változik
        eq2=Eq2_Card.getSelectedIndex();//eq2 combobox. a választott értékkel CSÖKKEN a LOW,,,,aHIGH NEM Változik
        eq3=Vissz_Eq_Card.getSelectedIndex();
        
      low=Be_Low_Box.getSelectedIndex(); //LOW
      high=Be_High_Box.getSelectedIndex();  //HIGH
      low=valueslow[low];
      high=valueshigh[high];
      at1=values[at1];
      at2=values[at2];
      at3=values[at3];        
      eq1=values[eq1];
      eq2=values[eq2];
      eq3=values[eq3];
       
          kiLow=low-eq1-at1-at2-eq2;//A kilow értékének számolását adja meg.
          kiHigh=high-at1-at2;//kihigh-------||---------
          sweeplow=kiLow+at3+eq3-22;//-22:22db le kell vonni a visszirány így lesz helyes
          sweephigh=kiHigh+at3-22;
          tilt=kiHigh-kiLow;//a kilow és kihighkülönbsége . ez adja a dőlését.
          int i=1;
       do{// a ciklus a kijelzőn minden gombnyomásra csak egyszer íródik ki
           
       jTextArea4.setText(t);       
       jTextArea4.setText(t);       
       jTextArea4.append("\n     Sweep: "+sweeplow+" "+sweephigh);
       }while(i!=1);
  }     
public void muszer(){
    
      tap1=beKiVi.getSelectedIndex();
      at1=At1_Card.getSelectedIndex();//at1 combobox  a váklasztott értékekkl csökken a HIGH és a LOW
      at2=At2_Card.getSelectedIndex();//at2 combobox  a váklasztott értékekkl csökken a HIGH és a LOW
      at3=Vissz_At_Card.getSelectedIndex();//
      eq1=Eq1_Card.getSelectedIndex();//eq1 combobox. a választott értékkel CSÖKKEN a LOW,,,,aHIGH NEM Változik
      eq2=Eq2_Card.getSelectedIndex();//eq2 combobox. a választott értékkel CSÖKKEN a LOW,,,,aHIGH NEM Változik
      eq3=Vissz_Eq_Card.getSelectedIndex();      
      low=Be_Low_Box.getSelectedIndex(); //LOW
      high=Be_High_Box.getSelectedIndex();  //HIGH
      
      tap1=val[tap1];
      low=valueslow[low];
      high=valueshigh[high];
      at1=values[at1];
      at2=values[at2];
      at3=values[at3];        
      eq1=values[eq1];
      eq2=values[eq2];
      eq3=values[eq3];
     
       
          kiLow=low-eq1-at1-at2-eq2+tap1;
          kiHigh=high-at1-at2+tap1;
          
          tilt=kiHigh-kiLow;
     
     
        
  int i=1;
       do{
       jTextArea4.setText(t);
       jTextArea4.append("\n    Low: "+kiLow);
       jTextArea4.append("\n    High: "+kiHigh);          
   
     
       }while(i!=1);
       
  }
public void scan(){
        tap1=beKiVi.getSelectedIndex();
        at1=At1_Card.getSelectedIndex();//at1 combobox  a váklasztott értékekkl csökken a HIGH és a LOW
        at2=At2_Card.getSelectedIndex();//at2 combobox  a váklasztott értékekkl csökken a HIGH és a LOW
        at3=Vissz_At_Card.getSelectedIndex();//
        eq1=Eq1_Card.getSelectedIndex();//eq1 combobox. a választott értékkel CSÖKKEN a LOW,,,,aHIGH NEM Változik
        eq2=Eq2_Card.getSelectedIndex();//eq2 combobox. a választott értékkel CSÖKKEN a LOW,,,,aHIGH NEM Változik
        eq3=Vissz_Eq_Card.getSelectedIndex();
      low=Be_Low_Box.getSelectedIndex(); //LOW
      high=Be_High_Box.getSelectedIndex();  //HIGH
      tap1=val[tap1];
      low=valueslow[low];
      high=valueshigh[high];
      at1=values[at1];
      at2=values[at2];
      at3=values[at3];        
      eq1=values[eq1];
      eq2=values[eq2];
      eq3=values[eq3];
       
          kiLow=low-eq1-at1-at2-eq2+tap1;
          kiHigh=high-at1-at2+tap1;
          
          tilt=kiHigh-kiLow;
          
       int i=1;                                     //
       do{                                          //
       jTextArea4.setText(t);                      // A műszer kijelzőn megjelenő szöveg. 1x es ciklusban hogy a kijelző mindig,
       jTextArea4.setText(t);   
       jTextArea4.append("\n    Low: "+kiLow);
       jTextArea4.append("\n    High: "+kiHigh);     // A műszer kijelzőn megjelenő szöveg. 1x es ciklusban hogy a kijelző mindig,
       jTextArea4.append("\n     Tilt:  "+tilt);    // csak az aktuális mérés legyen látható. 
       }while(i!=1);                                //
  
}
public void Tilt(){
        at1=At1_Card.getSelectedIndex();//at1 combobox  a váklasztott értékekkl csökken a HIGH és a LOW
        at2=At2_Card.getSelectedIndex();//at2 combobox  a váklasztott értékekkl csökken a HIGH és a LOW
        at3=Vissz_At_Card.getSelectedIndex();//
        eq1=Eq1_Card.getSelectedIndex();//eq1 combobox. a választott értékkel CSÖKKEN a LOW,,,,aHIGH NEM Változik
        eq2=Eq2_Card.getSelectedIndex();//eq2 combobox. a választott értékkel CSÖKKEN a LOW,,,,aHIGH NEM Változik
        eq3=Vissz_Eq_Card.getSelectedIndex();
        
      low=Be_Low_Box.getSelectedIndex(); //LOW
      high=Be_High_Box.getSelectedIndex();  //HIGH
      low=valueslow[low];
      high=valueshigh[high];
      at1=values[at1];
      at2=values[at2];
      at3=values[at3];        
      eq1=values[eq1];
      eq2=values[eq2];
      eq3=values[eq3];
       
          kiLow=low-eq1-at1-at2-eq2;
          kiHigh=high-at1-at2;
          sweeplow=kiLow-at3-eq3-22;
          sweephigh=kiHigh-at3-22;
          tilt=kiHigh-kiLow;
          
       int i=1;                                     //
       do{                                          //
       jTextArea4.setText(t);                      // A műszer kijelzőn megjelenő szöveg. 1x es ciklusban hogy a kijelző mindig,
       jTextArea4.setText(t);                      // A műszer kijelzőn megjelenő szöveg. 1x es ciklusban hogy a kijelző mindig,
       jTextArea4.append("\n     Tilt:  "+tilt);    // csak az aktuális mérés legyen látható. 
       }while(i!=1);                                //
  }
public void kepCsere(){
     int tp1=beKiVi.getSelectedIndex();
       int v[]={0,1,2};  //A tp1,tp2,tp3 checkbox változtatása esetés felugrik az indexhez rendelt üzenet
       tp1=v[tp1];
       
       if(tp1==v[0]){
           erosito_kep_Label.setIcon(getImageIcon(new File("src/szintez_image/erositokep1.png")));
           String message = " A TP1 -30dB mérőpontot választottad!\nA bejövő értékekből le kell vonnod 30dB-t fejben. ";
               
                JOptionPane.showMessageDialog(null, message);
        }else if(tp1==v[1]){
            erosito_kep_Label.setIcon(getImageIcon(new File("src/szintez_image/erositokep2.png")));
           String message = " A TP2 -30dB mérőpontot választottad!\nAz erősítő ~36dB erősít.\nA kapott értékekből le kell vonnod 30dB-t fejben. ";
               
                JOptionPane.showMessageDialog(null, message);
        }else{
            erosito_kep_Label.setIcon(getImageIcon(new File("src/szintez_image/erositokep3.png")));
          String message = " A TP3 -30dB mérőpontot választottad!\nA visszirány -22dB-vel kevesebb és a statikus kártyák(Eq,At), fordítva működnek. ";
               
                JOptionPane.showMessageDialog(null, message);
       }
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        At1_Card = new javax.swing.JComboBox<>();
        At2_Card = new javax.swing.JComboBox<>();
        Eq1_Card = new javax.swing.JComboBox<>();
        Eq2_Card = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        Vissz_At_Card = new javax.swing.JComboBox<>();
        Vissz_Eq_Card = new javax.swing.JComboBox<>();
        kilepes_Gomb = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea4 = new javax.swing.JTextArea();
        Mérés = new java.awt.Button();
        jButton6 = new javax.swing.JButton();
        Muszer_Mentes_Gomb = new javax.swing.JButton();
        Tilt_Button = new java.awt.Button();
        Sweep_button = new java.awt.Button();
        scan_button = new java.awt.Button();
        jButton5 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        Be_High_Box = new javax.swing.JComboBox<>();
        Be_Low_Box = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        erosito_kep_Label = new javax.swing.JLabel();
        beKiVi = new javax.swing.JComboBox<>();

        setTitle("Erösítő szintezés gyakorlat");
        setBackground(javax.swing.UIManager.getDefaults().getColor("Button.darcula.selection.color1"));
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setLocation(new java.awt.Point(145, 20));
        setPreferredSize(new java.awt.Dimension(1110, 700));
        setResizable(false);
        getContentPane().setLayout(null);

        At1_Card.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        At1_Card.setMaximumRowCount(12);
        At1_Card.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AT", "J0", "AT2", "AT4", "AT6", "AT8", "AT10", "AT12", "AT14", "AT16", "AT18", "AT20" }));
        At1_Card.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        getContentPane().add(At1_Card);
        At1_Card.setBounds(180, 80, 70, 30);

        At2_Card.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        At2_Card.setMaximumRowCount(12);
        At2_Card.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AT", "J0", "AT2", "AT4", "AT6", "AT8", "AT10", "AT12", "AT14", "AT16", "AT18", "AT20" }));
        At2_Card.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        getContentPane().add(At2_Card);
        At2_Card.setBounds(270, 80, 70, 30);

        Eq1_Card.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        Eq1_Card.setMaximumRowCount(12);
        Eq1_Card.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "EQ", "J0", "EQ2", "EQ4", "EQ6", "EQ8", "EQ10", "EQ12", "EQ14", "EQ16", "EQ18", "EQ20" }));
        Eq1_Card.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        getContentPane().add(Eq1_Card);
        Eq1_Card.setBounds(110, 80, 70, 30);

        Eq2_Card.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        Eq2_Card.setMaximumRowCount(12);
        Eq2_Card.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "EQ", "J0", "EQ2", "EQ4", "EQ6", "EQ8", "EQ10", "EQ12", "EQ14", "EQ16", "EQ18", "EQ20" }));
        Eq2_Card.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        getContentPane().add(Eq2_Card);
        Eq2_Card.setBounds(400, 80, 66, 30);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 204));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/szintez_Icon/icons8-info-popup-32.png"))); // NOI18N
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                infoeq1(evt);
            }
        });
        getContentPane().add(jLabel1);
        jLabel1.setBounds(90, 40, 40, 30);

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 51, 204));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/szintez_Icon/icons8-info-popup-32.png"))); // NOI18N
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                infoat1(evt);
            }
        });
        getContentPane().add(jLabel6);
        jLabel6.setBounds(210, 30, 40, 32);

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 0, 204));
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/szintez_Icon/icons8-info-popup-32.png"))); // NOI18N
        jLabel7.setIconTextGap(2);
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                infoat2(evt);
            }
        });
        getContentPane().add(jLabel7);
        jLabel7.setBounds(340, 50, 40, 30);

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 0, 204));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/szintez_Icon/icons8-info-popup-32.png"))); // NOI18N
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                infoeq2(evt);
            }
        });
        getContentPane().add(jLabel8);
        jLabel8.setBounds(450, 40, 32, 30);

        Vissz_At_Card.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        Vissz_At_Card.setMaximumRowCount(12);
        Vissz_At_Card.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AT", "J0", "AT2", "AT4", "AT6", "AT8", "AT10", "AT12", "AT14", "AT16", "AT18", "AT20" }));
        Vissz_At_Card.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        getContentPane().add(Vissz_At_Card);
        Vissz_At_Card.setBounds(160, 320, 70, 30);

        Vissz_Eq_Card.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        Vissz_Eq_Card.setMaximumRowCount(12);
        Vissz_Eq_Card.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "EQ", "J0", "EQ2", "EQ4", "EQ6", "EQ8", "EQ10", "EQ12", "EQ14", "EQ16", "EQ18", "EQ20" }));
        Vissz_Eq_Card.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        getContentPane().add(Vissz_Eq_Card);
        Vissz_Eq_Card.setBounds(120, 280, 70, 30);

        kilepes_Gomb.setText("Kilépés");
        kilepes_Gomb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kilepes_GombActionPerformed(evt);
            }
        });
        getContentPane().add(kilepes_Gomb);
        kilepes_Gomb.setBounds(10, 590, 90, 32);

        jPanel1.setBackground(new java.awt.Color(98, 98, 98));
        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.gray, java.awt.Color.lightGray));
        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel1.setEnabled(false);
        jPanel1.setRequestFocusEnabled(false);
        jPanel1.setVerifyInputWhenFocusTarget(false);

        jTextArea4.setEditable(false);
        jTextArea4.setBackground(new java.awt.Color(56, 98, 62));
        jTextArea4.setColumns(10);
        jTextArea4.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jTextArea4.setRows(2);
        jTextArea4.setAutoscrolls(false);
        jTextArea4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.gray));
        jScrollPane4.setViewportView(jTextArea4);

        Mérés.setActionCommand("Mérés");
        Mérés.setBackground(new java.awt.Color(204, 204, 204));
        Mérés.setFont(new java.awt.Font("DialogInput", 1, 14)); // NOI18N
        Mérés.setForeground(new java.awt.Color(102, 102, 102));
        Mérés.setLabel("Level");
        Mérés.setPreferredSize(new java.awt.Dimension(40, 40));
        Mérés.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MérésActionPerformed(evt);
            }
        });

        jButton6.setText("Off");
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton6.setMaximumSize(new java.awt.Dimension(55, 45));
        jButton6.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jButton6.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        Muszer_Mentes_Gomb.setText("Mentés");
        Muszer_Mentes_Gomb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Muszer_Mentes_GombActionPerformed(evt);
            }
        });

        Tilt_Button.setActionCommand("Tilt");
        Tilt_Button.setBackground(new java.awt.Color(204, 204, 204));
        Tilt_Button.setFont(new java.awt.Font("DialogInput", 1, 14)); // NOI18N
        Tilt_Button.setForeground(new java.awt.Color(102, 102, 102));
        Tilt_Button.setLabel("Tilt");
        Tilt_Button.setPreferredSize(new java.awt.Dimension(48, 28));
        Tilt_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Tilt_ButtonActionPerformed(evt);
            }
        });

        Sweep_button.setBackground(new java.awt.Color(204, 204, 204));
        Sweep_button.setFont(new java.awt.Font("DialogInput", 1, 14)); // NOI18N
        Sweep_button.setForeground(new java.awt.Color(102, 102, 102));
        Sweep_button.setLabel("Sweep");
        Sweep_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Sweep_buttonActionPerformed(evt);
            }
        });

        scan_button.setBackground(new java.awt.Color(204, 204, 204));
        scan_button.setFont(new java.awt.Font("DialogInput", 1, 14)); // NOI18N
        scan_button.setForeground(new java.awt.Color(102, 102, 102));
        scan_button.setLabel("Scan");
        scan_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scan_buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Mérés, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(Tilt_Button, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(scan_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(2, 2, 2)
                .addComponent(Sweep_button, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(Muszer_Mentes_Gomb, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(scan_button, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(Tilt_Button, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(Mérés, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Sweep_button, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Muszer_Mentes_Gomb)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1);
        jPanel1.setBounds(790, 130, 240, 270);

        jButton5.setText("On");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton5);
        jButton5.setBounds(820, 590, 50, 32);

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setText("Műszer");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(820, 570, 70, 19);

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/szintez_image/AT_J0_2T75.jpg"))); // NOI18N
        getContentPane().add(jLabel4);
        jLabel4.setBounds(10, 430, 240, 140);

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/szintez_image/226623239_4.jpg"))); // NOI18N
        getContentPane().add(jLabel5);
        jLabel5.setBounds(720, -50, 360, 730);

        Be_High_Box.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Be HIGH Dbuv", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88" }));
        getContentPane().add(Be_High_Box);
        Be_High_Box.setBounds(310, 470, 106, 30);

        Be_Low_Box.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Be LOW Dbuv", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88" }));
        getContentPane().add(Be_Low_Box);
        Be_Low_Box.setBounds(310, 426, 107, 30);

        jLabel9.setBackground(new java.awt.Color(102, 102, 102));
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/szintez_Icon/icons8-info-popup-32.png"))); // NOI18N
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Info_Fo(evt);
            }
        });
        getContentPane().add(jLabel9);
        jLabel9.setBounds(210, 260, 30, 30);

        erosito_kep_Label.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        erosito_kep_Label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/szintez_image/erositokep1.png"))); // NOI18N
        erosito_kep_Label.setToolTipText("");
        erosito_kep_Label.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        erosito_kep_Label.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        erosito_kep_Label.setPreferredSize(new java.awt.Dimension(650, 375));
        erosito_kep_Label.setRequestFocusEnabled(false);
        erosito_kep_Label.setVerifyInputWhenFocusTarget(false);
        getContentPane().add(erosito_kep_Label);
        erosito_kep_Label.setBounds(0, 0, 710, 410);

        beKiVi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tp1 -30dB", "Tp2 -30dB", "Tp3 -30dB", "" }));
        beKiVi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                beKiViActionPerformed(evt);
            }
        });
        getContentPane().add(beKiVi);
        beKiVi.setBounds(450, 440, 110, 40);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void infoeq1(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoeq1
        JOptionPane.showMessageDialog(null, "EQ1 kártya helye!\nFeladata: A kiválasztott kártya,\n adott egységgel csökkenti a low értékét.","Információ",JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_infoeq1

    private void infoat1(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoat1
        JOptionPane.showMessageDialog(null, "AT1 kártya helye!\nFeladata: A kiválasztott kártya,\n adott egységgel csökkenti a Low és a High értékét.","Információ",JOptionPane.INFORMATION_MESSAGE);

    }//GEN-LAST:event_infoat1

    private void infoat2(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoat2
       JOptionPane.showMessageDialog(null, "AT2 kártya helye!\nFeladata: A kiválasztott kártya,\n adott egységgel csökkenti a Low és a High értékét.","Információ",JOptionPane.INFORMATION_MESSAGE);

    }//GEN-LAST:event_infoat2

    private void infoeq2(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoeq2
       JOptionPane.showMessageDialog(null, "EQ2 kártya helye!\nFeladata: A kiválasztott kártya,\n adott egységgel csökkenti a low értékét.","Információ",JOptionPane.INFORMATION_MESSAGE);

    }//GEN-LAST:event_infoeq2

    private void kilepes_GombActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kilepes_GombActionPerformed
             try {
                 // Kilépés gomb , ablak bezárása
     this.dispose();
                 kezdoAblak ablak=new kezdoAblak();
                 ablak.setVisible(true);
          
             } catch (ClassNotFoundException ex) {
                 Logger.getLogger(FoAblak.class.getName()).log(Level.SEVERE, null, ex);
             } catch (IOException ex) {
                 Logger.getLogger(FoAblak.class.getName()).log(Level.SEVERE, null, ex);
             } catch (SQLException ex) {
                 Logger.getLogger(FoAblak.class.getName()).log(Level.SEVERE, null, ex);
             }

    }//GEN-LAST:event_kilepes_GombActionPerformed

    private void MérésActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MérésActionPerformed
     muszer();
    }//GEN-LAST:event_MérésActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
    jPanel1.setVisible(true);//Műszer panel bekapcsolása      
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
   jPanel1.setVisible(false);        // műszer kikapcasolása
    }//GEN-LAST:event_jButton6ActionPerformed

    private void Muszer_Mentes_GombActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Muszer_Mentes_GombActionPerformed
    // Mentes_Email.setVisible(true);
         while(true){   
        try {
            saveToDatabase();//mentés gombra elindul a savetodatabase metodus ami elmenti a beirt adatokat az adatok táblába
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FoAblak.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(FoAblak.class.getName()).log(Level.SEVERE, null, ex);
        }
        break;
        //ha sikerült a mentés akkor kilép a ciklusból 
     }
     JOptionPane.showMessageDialog(null, "A feladat, kártya és számolási adatai elmentve.", "Mentés",JOptionPane.INFORMATION_MESSAGE);
       
    }//GEN-LAST:event_Muszer_Mentes_GombActionPerformed

    private void Tilt_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Tilt_ButtonActionPerformed
      Tilt();
    }//GEN-LAST:event_Tilt_ButtonActionPerformed

    private void Sweep_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Sweep_buttonActionPerformed
      visszIrany();  
    }//GEN-LAST:event_Sweep_buttonActionPerformed

    private void scan_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scan_buttonActionPerformed
    scan();
    }//GEN-LAST:event_scan_buttonActionPerformed

    private void Info_Fo(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Info_Fo
        JOptionPane.showMessageDialog(null, "Eq és At visszirányú kártyák.\nA kártyák fordítva működnek, az Eg és At növeli adott egységgel.\n", "Információ",JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_Info_Fo

    private void beKiViActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_beKiViActionPerformed
      kepCsere();
    }//GEN-LAST:event_beKiViActionPerformed

    
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
            java.util.logging.Logger.getLogger(FoAblak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FoAblak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FoAblak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FoAblak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
        
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> At1_Card;
    private javax.swing.JComboBox<String> At2_Card;
    private javax.swing.JComboBox<String> Be_High_Box;
    private javax.swing.JComboBox<String> Be_Low_Box;
    private javax.swing.JComboBox<String> Eq1_Card;
    private javax.swing.JComboBox<String> Eq2_Card;
    private javax.swing.JButton Muszer_Mentes_Gomb;
    private java.awt.Button Mérés;
    private java.awt.Button Sweep_button;
    private java.awt.Button Tilt_Button;
    private javax.swing.JComboBox<String> Vissz_At_Card;
    private javax.swing.JComboBox<String> Vissz_Eq_Card;
    private javax.swing.JComboBox<String> beKiVi;
    private javax.swing.JLabel erosito_kep_Label;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea jTextArea4;
    private javax.swing.JButton kilepes_Gomb;
    private java.awt.Button scan_button;
    // End of variables declaration//GEN-END:variables
}
