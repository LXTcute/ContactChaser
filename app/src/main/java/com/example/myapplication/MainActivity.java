package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlertDialog;
import java.lang.reflect.Proxy;
import java.security.acl.Group;
import java.util.HashMap;
import it.unisa.dia.gas.jpbc.Element;

public class MainActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

   // public boolean res;
    public HashMap<String,Element> signature;
    public String msg;
    public GroupSig shortGroupSig;

    //////////////////////Group signature generation//////////////////
    public void onMyButtonClick(View view){

        int n=3;
        int user=1;
        shortGroupSig=new GroupSig();
        shortGroupSig.keygen(n);

        shortGroupSig.preCalculation(shortGroupSig.gsk[user]);
        System.out.println("------------------------------generate finish!");
        msg = "Hello World this is a message!";

        signature=shortGroupSig.sign( shortGroupSig.gsk[user], msg);
        System.out.println("------------------------------sign finish!");

        //boolean res=shortGroupSig.verify( msg, signature);
        //System.out.println("------------------------------verify finish!");
        //System.out.println("------------------------------result is: "+res);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("sign");
        builder.setPositiveButton("YES",null);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setMessage("Signature is"+signature);
        builder.show();

        System.out.println(shortGroupSig.gpk);
        System.out.println(shortGroupSig.gsk);
        System.out.println(shortGroupSig.gmsk);

    }

    //////////////////////verify key//////////////////
    public void onMyButton2Click(View view) {

        boolean res=shortGroupSig.verify( msg, signature);
        System.out.println("------------------------------verify finish!");
        System.out.println("------------------------------result is: "+res);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("verify");
        builder.setPositiveButton("YES",null);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setMessage("The verification result is "+res);
        builder.show();
    }

    //////////////////////Signature timing//////////////////
    public void onMyButton3Click(View view){

        int n=3;
        int user=1;
        shortGroupSig=new GroupSig();
        shortGroupSig.keygen(n);

        shortGroupSig.preCalculation(shortGroupSig.gsk[user]);
        System.out.println("------------------------------generate finish!");
        String msg = "Hello World this is a message!";

        long begin = System.currentTimeMillis();
        // for(int i=0;i<=20;i++)//the total time of running twenty times
        // {
            signature=shortGroupSig.sign( shortGroupSig.gsk[user], msg);
        // }
        long end = System.currentTimeMillis();

        System.out.println("------------------------------sign finish!");


        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Sign timing");
        builder.setPositiveButton("YES",null);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setMessage("Running time is："+(end- begin)+"ms");
        builder.show();
    }//////////////////////////////////////

    //////////////////////Verification signature timing//////////////////
    public void onMyButton4Click(View view){

        long begin = System.currentTimeMillis();
       // for(int i=0;i<=20;i++)//the total time of running twenty times
       // {
            boolean res = shortGroupSig.verify(msg, signature);
       // }
        long end = System.currentTimeMillis();

        System.out.println("------------------------------verify finish!");
        System.out.println("------------------------------result is: "+res);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Verify timing");
        builder.setPositiveButton("YES",null);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setMessage("Running time is："+(end- begin)+"ms");
        builder.show();
    }//////////////////////////////////////

/////////////////G1 scalar expo (JPBC)/////////////////////
    public void onMyButClick5(View view){
        ECCjpbc e=new ECCjpbc();
        long begin = System.currentTimeMillis();
        //for(int i=0;i<=20;i++)// the total time of running twenty times
        //{
            e.startECC();
       // }
        long end = System.currentTimeMillis();

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("G1 scalar multiplication timing");
        builder.setPositiveButton("YES",null);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setMessage("Running time is："+(end- begin)+"ms");
        builder.show();

    }

    /////////////////G2 scalar expo (JPBC)/////////////////////
    public void onMyButClick6(View view){
        ECCjpbc e2=new ECCjpbc();
        long begin = System.currentTimeMillis();
        //for(int i=0;i<=20;i++)// the total time of running twenty times
        //{
           e2.startECC2();
        // }
        long end = System.currentTimeMillis();

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("G2 scalar multiplication timing");
        builder.setPositiveButton("YES",null);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setMessage("Running time is："+(end- begin)+"ms");
        builder.show();

    }

    /////////////////GT scalar expo (JPBC)/////////////////////
    public void onMyButClick7(View view){
        ECCjpbc e3=new ECCjpbc();
        long begin = System.currentTimeMillis();
        //for(int i=0;i<=20;i++)// the total time of running twenty times
        //{
           e3.startECC3();
        // }
        long end = System.currentTimeMillis();

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("GT scalar multiplication timing");
        builder.setPositiveButton("YES",null);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setMessage("Running time is："+(end- begin)+"ms");
        builder.show();

    }


    /////////////////pairing timing (JPBC)/////////////////////
    public void onMyButClick8(View view){
        ECCjpbc f=new ECCjpbc();
        //BasicIdent2 ident = new BasicIdent2();
       // ident.buildSystem();
        //ident.extractSecretKey();

        long begin = System.currentTimeMillis();
        //for(int i=0;i<=20;i++)// the total time of running twenty times
        //{
           f.startPairing();
        //ident.encrypt();
        // }
        long end = System.currentTimeMillis();

        //ident.decrypt();

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("pairing timing");
        builder.setPositiveButton("YES",null);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setMessage("Running time is："+(end- begin)+"ms");
        builder.show();

    }
}


