/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szintez;

import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
/**
 *
 * @author heimdall_rh 2018.08.20.
 */
public class database {//Az adatbázist itt hozza létre. A létező adatbátis esetén nem működik.
    public static void data() throws ClassNotFoundException, IOException, SQLException, ClassNotFoundException{
    
     File f=new File("szintezData.accdb");   //fájl létrehozás //fájl létrehozás
   if(!f.exists()){
       Database db=DatabaseBuilder.create(Database.FileFormat.V2010, new File("szintezData.accdb"));//adatbázis létrehozás
       FileReader fr=new FileReader("szintezDatabase.sql");           //sql fájlban a táblák létrehozása segéd
            BufferedReader br=new BufferedReader(fr);
            java.util.Properties prop = new java.util.Properties();
            prop.put("charSet", "ISO-8859-2");   //beállítja utf 8 kodolásura az ékezetek miatt
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection kapcsolat=DriverManager.getConnection("jdbc:ucanaccess://szintezData.accdb",prop);  //kapcsolat az adatbázissal
            Statement parancs=kapcsolat.createStatement();            
            String utasitas="";
            while(br.ready()){
                String sor=br.readLine();      
                utasitas+=sor;
                if(sor.endsWith(";")){
                    parancs.execute(utasitas);//Ucanaccess 4.0 tol keresni
                    utasitas="";
                }
            }
            br.close();
            fr.close();
   }
   
}
}
