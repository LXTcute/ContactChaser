package com.example.myapplication;
import it.unisa.dia.gas.plaf.jpbc.field.curve.CurveElement;
import it.unisa.dia.gas.plaf.jpbc.pairing.a.TypeACurveGenerator;
import it.unisa.dia.gas.jpbc.*;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
//import it.unisa.dia.gas.plaf.jpbc.pbc.curve.PBCTypeDCurveGenerator;
import it.unisa.dia.gas.plaf.jpbc.pbc.curve.PBCTypeDCurveGenerator;


import java.math.BigInteger;

public class ECCjpbc {

    public static String paramspath= "assets/a.properties";
    public static Pairing pairing = PairingFactory.getPairing(paramspath);
    public Element e1 =pairing.getG1().newRandomElement().getImmutable();;
    public Element e2 = pairing.getZr().newRandomElement().getImmutable();

    public Element e3 =pairing.getG2().newRandomElement().getImmutable();;
    public Element e4 = pairing.getZr().newRandomElement().getImmutable();

    public Element e5 =pairing.getGT().newRandomElement().getImmutable();;
    public Element e6 = pairing.getZr().newRandomElement().getImmutable();

    public Element T1 =pairing.getG1().newRandomElement().getImmutable();;
    public Element T2 = pairing.getG2().newRandomElement().getImmutable();

    public  void startECC(){//G1  scalar expo;

        //System.out.println(e1);
        //System.out.println(e2);
        Element e11=e1.mulZn(e2);
        //System.out.println(e3);
    }

    public  void startECC2(){//G2  scalar expo;
        Element e22=e3.mulZn(e4);
    }

    public  void startECC3(){//GT  scalar expo;
        Element eT=e5.mulZn(e6);
    }

    public  void startPairing(){

        Element T3 = pairing.pairing(T1, T2).getImmutable();

    }

}
