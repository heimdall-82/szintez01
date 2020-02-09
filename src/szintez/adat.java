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
//adatbázis és admin kapcsolatot segíti
public class adat {

  
   private int adatokid,personid,below,behigh,eq1,at1,eq2,at2,eq3,at3,kilow,kihigh,tilt,sweeplow,sweephigh;
public adat(int Adatokid,int Personid,int Behigh,int Below,int Eq1,int At1,int Eq2,int At2,int Eq3,int At3,int Kilow,int Kihigh,int Tilt,int Sweeplow,int Sweephigh){
        this.adatokid=Adatokid;
        this.personid=Personid;
        this.below=Below;
        this.behigh=Behigh;
        this.eq1=Eq1;
        this.at1=At1;
        this.eq2=Eq2;
        this.at2=At2;
        this.eq3=Eq3;
        this.at3=At3;
        this.kilow=Kilow;
        this.kihigh=Kihigh;
        this.tilt=Tilt;
        this.sweeplow=Sweeplow;
        this.sweephigh=Sweephigh;
    }
public int getAdatokid(){
    return adatokid;
}
public int getPersonid(){
    return personid;
}
    public int getBelow() {
        return below;
    }

    public int getBehigh() {
        return behigh;
    }

    public int getEq1() {
        return eq1;
    }

    public int getAt1() {
        return at1;
    }

    public int getEq2() {
        return eq2;
    }

    public int getAt2() {
        return at2;
    }

    public int getEq3() {
        return eq3;
    }

    public int getAt3() {
        return at3;
    }

    public int getKilow() {
        return kilow;
    }

    public int getKihigh() {
        return kihigh;
    }

    public int getTilt() {
        return tilt;
    }

    public int getSweeplow() {
        return sweeplow;
    }

    public int getSweephigh() {
        return sweephigh;
    }
    
}
