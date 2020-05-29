package com.example.myapplication;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;


import java.math.BigInteger;
import java.util.HashMap;

public class GroupSig {
    public static String paramspath= "assets/a.properties";
    public static Pairing pairing = PairingFactory.getPairing(paramspath);

    public HashMap<String,Element> gpk;
    public HashMap<String,Element> gmsk;
    public Element[][] gsk;
    public Element T3;
    public Element alpha;
    public Element beta;

    public Element R3p1pair;
    public Element R3p1pair2;
    public Element R3p2pair;
    public Element R3p3pair;

    public Element R3p1pairvery;
    public Element R3p2pairvery;
    public Element R3p3pairvery;
    public Element R3p4pairvery1;
    public Element R3p4pairvery2;



    public GroupSig(){

    }

    public void keygen(int n){
        //Element e1=G1.newRandomElement();
        Element g1=pairing.getG1().newRandomElement().getImmutable();
        Element g2=pairing.getG2().newRandomElement().getImmutable();
        Element h=pairing.getG1().newRandomElement().getImmutable();
        Element xi1=pairing.getZr().newRandomElement().getImmutable();
        Element xi2=pairing.getZr().newRandomElement().getImmutable();
//        System.out.println(h);
//        BigInteger test=xi1.not();

//        System.out.println(xi1);

        Element xi1iinv=pairing.getZr().newElement(xi1).getImmutable();
        Element xi2iinv=pairing.getZr().newElement(xi2).getImmutable();

//        Element t3=xi1iinv.powZn(xi2iinv);


        xi1iinv=xi1iinv.invert();
        xi2iinv=xi2iinv.invert();
        Element u=h.powZn(xi1iinv).getImmutable();
        Element v=h.powZn(xi2iinv).getImmutable();
//        System.out.println(xi1);
//        System.out.println(xi2);
//        BigInteger tb=new BigInteger("8629482700828807858443223333118149342809806519654755987528217861527");
//        Element t=pairing.getZr().newElement(tb);
//        Element ti=pairing.getZr().newZeroElement().sub(t);

        Element gamma=pairing.getZr().newRandomElement().getImmutable();
        Element w=g2.powZn(gamma).getImmutable();
        gpk = new HashMap();
        gpk.put("g1",g1);
        gpk.put("g2",g2);
        gpk.put("h",h);
        gpk.put("u",u);
        gpk.put("v",v);
        gpk.put("w",w);

        gmsk=new HashMap();
        gmsk.put("xi1",xi1);
        gmsk.put("xi2",xi2);

        Element[] x=new Element[n];
        for (int i=0;i<n;i++){
            x[i]=pairing.getZr().newRandomElement().getImmutable();
        }

        Element[] A=new Element[n];
        for (int i=0;i<n;i++){
            Element pindex=gamma.add(x[i]).getImmutable();
            Element pindexi=pindex.invert().getImmutable();
            A[i]=g1.powZn(pindexi).getImmutable();
        }

        gsk=new Element[n][2];
        for(int i=0;i<n;i++){
            gsk[i]=new Element[2];
            gsk[i][0] =A[i];
            gsk[i][1]=x[i];
        }



    }

    public void preCalculation(Element[] usk){
        Element A=usk[0].getImmutable();
        R3p2pair=pairing.pairing(gpk.get("h"),gpk.get("w")).getImmutable();
        R3p3pair=pairing.pairing(gpk.get("h"),gpk.get("g2")).getImmutable();
        R3p1pair2=pairing.pairing(A,gpk.get("g2")).getImmutable();

        R3p2pairvery=pairing.pairing(gpk.get("h"),gpk.get("w")).getImmutable();
        R3p3pairvery=pairing.pairing(gpk.get("h"),gpk.get("g2")).getImmutable();
        R3p4pairvery2=pairing.pairing(gpk.get("g1"),gpk.get("g2")).getImmutable();



    }

    public void preCalculationVery(HashMap<String,Element> sigma){

    }

    public HashMap<String, Element> sign(Element[] usk, String M){

        alpha=pairing.getZr().newRandomElement().getImmutable();
        beta=pairing.getZr().newRandomElement().getImmutable();
        Element A=usk[0].getImmutable();
        Element x=usk[1].getImmutable();
        Element T1=gpk.get("u").powZn(alpha).getImmutable();
        Element T2=gpk.get("v").powZn(beta).getImmutable();

        T3=A.mul(gpk.get("h").powZn(alpha.add(beta))).getImmutable();


        Element delta1=x.mulZn(alpha).getImmutable();
        Element delta2=x.mulZn(beta).getImmutable();
        Element[] r=new Element[5];
        for (int i=0;i<5;i++){
            r[i]=pairing.getZr().newRandomElement().getImmutable();
        }

        Element R1=gpk.get("u").powZn(r[0]).getImmutable();
        Element R2=gpk.get("v").powZn(r[1]).getImmutable();


//        R3p1pair=pairing.pairing(T3, gpk.get("g2")).getImmutable();
//        R3p1pair=R3p1pair2.mul(R3p3pair.powZn(alpha.add(beta))).getImmutable();
//
//        Element R3p1=R3p1pair.powZn(r[2]).getImmutable();
//
        Element minusr0e=pairing.getZr().newZeroElement().sub(r[0]).getImmutable();
//        Element R3p2=R3p2pair.powZn(minusr0e.sub(r[1])).getImmutable();
//
//
//        Element minusr3e=pairing.getZr().newZeroElement().sub(r[3]).getImmutable();
//        Element R3p3=R3p3pair.powZn(minusr3e.sub(r[4])).getImmutable();
        Element R3p1=R3p1pair2.powZn(r[2]).getImmutable();
        Element R3p2=R3p3pair.powZn(alpha.add(beta).mulZn(r[2]).sub(r[3]).sub(r[4])).getImmutable();
        Element R3p3=R3p2pair.powZn(minusr0e.sub(r[1]));
        Element R3=R3p1.mul(R3p2).mul(R3p3).getImmutable();

//        Element R4=T1.powZn(r[2]).mul(gpk.get("u").powZn(minusr3e)).getImmutable();
        Element R4=gpk.get("u").powZn(alpha.mulZn(r[2]).sub(r[3])).getImmutable();


//        Element minusr4e=pairing.getZr().newZeroElement().sub(r[4]).getImmutable();
//        Element R5=T2.powZn(r[2]).mul(gpk.get("v").powZn(minusr4e)).getImmutable();
        Element R5=gpk.get("v").powZn(beta.mulZn(r[2]).sub(r[4])).getImmutable();


//        String t1x=(CurveElement)T1.getX();

        String hashstr=T1.toString()+T2.toString()+T3.toString()+
                R1.toString()+R2.toString()+R3.toString()+
                R4.toString()+R5.toString();

        hashstr=hashstr.replace(",","");
        hashstr=hashstr.replace("{x=[","");
        hashstr=hashstr.replace("]y=[","");
        hashstr=hashstr.replace("]}","");
        hashstr=hashstr.replace(" ","");


        hashstr=M+hashstr;

        byte[] hashbytes=hashstr.getBytes();
        Element c=pairing.getZr().newElement();
        c.setFromHash(hashbytes,0,hashbytes.length).getImmutable();

        Element s1c=pairing.getZr().newElement(c).mulZn(alpha).getImmutable();
        Element s1=r[0].add(s1c).getImmutable();

        Element s2c=pairing.getZr().newElement(c).mulZn(beta).getImmutable();
        Element s2=r[1].add(s2c).getImmutable();

        Element s3c=pairing.getZr().newElement(c).mulZn(x).getImmutable();
        Element s3=r[2].add(s3c).getImmutable();

        Element s4c=pairing.getZr().newElement(c).mulZn(delta1).getImmutable();
        Element s4=r[3].add(s4c).getImmutable();

        Element s5c=pairing.getZr().newElement(c).mulZn(delta2).getImmutable();
        Element s5=r[4].add(s5c).getImmutable();

        HashMap<String,Element> res=new HashMap<String, Element>();

        res.put("T1",T1);
        res.put("T2",T2);
        res.put("T3",T3);
        res.put("c",c);
        res.put("s_alpha",s1);
        res.put("s_beta",s2);
        res.put("s_x",s3);
        res.put("s_delta1",s4);
        res.put("s_delta2",s5);

        return res;



    }

    public boolean verify(String M, HashMap<String,Element> sigma){

        boolean validSignature=false;

        Element c=sigma.get("c");
        Element t1=sigma.get("T1");
        Element t2=sigma.get("T2");
        Element t3=sigma.get("T3");

        Element s_alpha=sigma.get("s_alpha");
        Element s_beta=sigma.get("s_beta");

        Element s_x=sigma.get("s_x");
        Element s_delta1=sigma.get("s_delta1");
        Element s_delta2=sigma.get("s_delta2");



        Element minusc=pairing.getZr().newZeroElement().sub(c).getImmutable();
        Element R1_=gpk.get("u").powZn(s_alpha).mul(t1.powZn(minusc)).getImmutable();
        Element R2_=gpk.get("v").powZn(s_beta).mul(t2.powZn(minusc)).getImmutable();

        R3p1pairvery=pairing.pairing(t3.powZn(s_x),gpk.get("g2")).getImmutable();
//        R3p1pairvery=R3p1pair2.mul(R3p3pair.powZn(alpha.add(beta))).getImmutable();

        R3p4pairvery1=pairing.pairing(T3,gpk.get("w")).getImmutable();
        Element R3p1=R3p1pairvery;

        Element minussalpha=pairing.getZr().newZeroElement().sub(s_alpha).getImmutable();
        Element R3p2=R3p2pairvery.powZn(minussalpha.sub(s_beta)).getImmutable();

        Element minussdelta1=pairing.getZr().newZeroElement().sub(s_delta1).getImmutable();
        Element R3p3=R3p3pairvery.powZn(minussdelta1.sub(s_delta2)).getImmutable();
        Element R3p4=R3p4pairvery1.div(R3p4pairvery2).powZn(c).getImmutable();
        Element R3_=R3p1.mul(R3p2).mul(R3p3).mul(R3p4).getImmutable();

        Element R4_=t1.powZn(s_x).mul(gpk.get("u").powZn(minussdelta1)).getImmutable();


        Element minussdelta2=pairing.getZr().newZeroElement().sub(s_delta2).getImmutable();
        Element R5_=t2.powZn(s_x).mul(gpk.get("v").powZn(minussdelta2)).getImmutable();


        String hashstr=t1.toString()+t2.toString()+t3.toString()+
                R1_.toString()+R2_.toString()+R3_.toString()+
                R4_.toString()+R5_.toString();

        hashstr=hashstr.replace(",","");
        hashstr=hashstr.replace("{x=[","");
        hashstr=hashstr.replace("]y=[","");
        hashstr=hashstr.replace("]}","");
        hashstr=hashstr.replace(" ","");


        hashstr=M+hashstr;
        byte[] hashbytes=hashstr.getBytes();
        Element c_prime=pairing.getZr().newElement();
        c_prime.setFromHash(hashbytes,0,hashbytes.length).getImmutable();

        if(c.isEqual(c_prime))
            validSignature=true;

        return validSignature;
    }

    public Element open(String M,HashMap<String,Element> sigma){
        Element t1=sigma.get("T1");
        Element t2=sigma.get("T2");
        Element t3=sigma.get("T3");
        Element xi1=gmsk.get("xi1");
        Element xi2=gmsk.get("xi2");

        Element A_prime=t3.div(t1.powZn(xi1).mulZn(t2.powZn(xi2)));
        return A_prime;

    }
}
