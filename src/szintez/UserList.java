/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szintez;

/**
 *
 * @author heimdall_rh 2018.08.20.
 */
//ez az osztály az adatbázis és admin kapcolatot és műveleteket segíti
public class UserList{
    private int personid;
    private String veznev;
    private String kernev;
    private String email;

public UserList(int Id,String Veznev,String Kernev,String Email){
            this.personid=Id;
            this.veznev=Veznev;
            this.kernev=Kernev;
            this.email=Email;

}
public int getId(){
    return personid;
}
public String getVeznev(){
    return veznev;
}
public String getKernev(){
    return kernev;
}
public String getEmail(){
    return email;
}

}
