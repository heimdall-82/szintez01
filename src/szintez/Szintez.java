/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szintez;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author heimdall_rh
 */
public class Szintez {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        
        
     
        //A program elindítja a kezdoabakot amin lehetőségunk van regisztrálásra és bejelentkezésre.
        // Az ablak menü kilépés és admin autentikációra ad lehetőséget.
        
        
     
       kezdoAblak frame = null;
        try {
            frame = new kezdoAblak();
        } catch (SQLException ex) {
            Logger.getLogger(Szintez.class.getName()).log(Level.SEVERE, null, ex);
        }
        frame.setVisible(true);

        
        
        
}

    
}
