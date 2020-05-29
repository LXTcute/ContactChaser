package com.example.myapplication;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

import java.lang.reflect.Proxy;

public class BasicIdent2 implements Ident {

    private Element s, r, P, Ppub, Su, Qu, V, T1, T2;
    private Field G1, Zr;
    private Pairing pairing;

    public BasicIdent2() {
        init();
    }

    /**
     * initialization
     */
    private void init() {
        System.out.println( android.os.Environment.getExternalStorageDirectory().getAbsolutePath());
        pairing = PairingFactory.getPairing("assets/a.properties");//
        PairingFactory.getInstance().setUsePBCWhenPossible(true);
        checkSymmetric(pairing);
        //Initialize the variable r to an element in Zr
        Zr = pairing.getZr();
        r = Zr.newElement();
        //Initialize the variables Ppub, Qu, Su, V to the elements in G1, which is an additive group
        G1 = pairing.getG1();
        Ppub = G1.newElement();
        Qu = G1.newElement();
        Su = G1.newElement();
        V = G1.newElement();
        //initialize the variables T1, T2V to the element in GT, which is the multiplicative group
        Field GT = pairing.getGT();
        T1 = GT.newElement();
        T2 = GT.newElement();
    }

    /**
     * Judge whether the pairing is symmetric, and output error information if it is asymmetric
     *
     * @param pairing
     */
    private void checkSymmetric(Pairing pairing) {
        if (!pairing.isSymmetric()) {
            throw new RuntimeException("Key asymmetry!");
        }
    }

    @Override
    public void buildSystem() {
        System.out.println("-------------------System establishment phase----------------------");
        s = Zr.newRandomElement().getImmutable();// //Generate the master key at random s
        P = G1.newRandomElement().getImmutable();// Generate the generator P for G1
        Ppub = P.mulZn(s);// Calculate Ppub=sP and pay attention to the order
        System.out.println("P=" + P);
        System.out.println("s=" + s);
        System.out.println("Ppub=" + Ppub);
    }

    @Override
    public void extractSecretKey() {
        System.out.println("-------------------Key extraction stage----------------------");
        Qu = pairing.getG1().newElement().setFromHash("IDu".getBytes(), 0, 3)
                .getImmutable();// //Determine the user U generated public key Qu from the Hash value IDu of length 3
        Su = Qu.mulZn(s).getImmutable();
        System.out.println("Qu=" + Qu);
        System.out.println("Su=" + Su);
    }

    @Override
    public void encrypt() {
        //System.out.println("-------------------Encryption phase----------------------");
        r = Zr.newRandomElement().getImmutable();
        T1 = pairing.pairing(Ppub, Qu).getImmutable();// calculation e（Ppub,Qu）
        T1 = T1.powZn(r).getImmutable();
        //System.out.println("r=" + r);
        //System.out.println("V=" + V);
        System.out.println("T1=e（Ppub,Qu）^r=" + T1);
    }

    @Override
    public void decrypt() {
        System.out.println("-------------------decryption phase----------------------");
        V = P.mulZn(r);

        T2 = pairing.pairing(V, Su).getImmutable();
        System.out.println("e(V,Su)=" + T2);
        int byt = V.getLengthInBytes();// Find the length of V bytes, assuming the message length is 128 bytes
        System.out.println("The length of the text" + (byt + 128));
    }


}

