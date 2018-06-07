package com.acreims.lombards.gestionfontaine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

/**
 * Created by ThomasSIM-ASUS on 20/03/2018.
 */

public class activity_mainmenu extends Activity {

    // Liens sur composants graphiques d'IHM
    Button bt_afficher_conso, bt_redemmarrage;
    Switch sw_etat_fontaine, sw_etat_eclairage;
    //TextView tv_test;

    String etat_fontaine = "";


    // Création d'objets
    SmsManager ref_smsManager = SmsManager.getDefault();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_mainmenu);

        // Etablir un lien entre identifiant de composant d'IHM et le composant graphique
        sw_etat_fontaine = findViewById(R.id.sw_etat_fontaine);
        sw_etat_eclairage = findViewById(R.id.sw_etat_eclairage);
        bt_afficher_conso = findViewById(R.id.bt_afficher_conso);
        bt_redemmarrage = findViewById(R.id.bt_redemarrage);
        //tv_test = findViewById(R.id.tv_test);


        // Vérification de l'état de la fontaine par SMS
        //ref_smsManager.sendTextMessage("+33769691960",null, "etat fontaine", null,null);



        // affiche l'IHM contenant le graphique des consommations d'eau, d'électricité, ainsi que le niveau de batterie
        bt_afficher_conso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_mainmenu.this, activity_graph.class);
                startActivity(intent);
            }
        });

        // envoi de la commande d'allumage / extinction de la fontaine
        sw_etat_fontaine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ref_smsManager.sendTextMessage("+33769691960", null, "mise en marche pompe", null, null);
                    Toast.makeText(activity_mainmenu.this, "Mise en marche de la pompe...", Toast.LENGTH_SHORT).show();
                }
                else {
                    ref_smsManager.sendTextMessage("+33769691960", null, "extinction pompe", null, null);
                Toast.makeText(activity_mainmenu.this, "Extinction de la pompe...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // envoi de la commande d'allumage / extinction de l'éclairage
        sw_etat_eclairage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    ref_smsManager.sendTextMessage("+33769691960", null,"allumer eclairage",null,null);
                    Toast.makeText(activity_mainmenu.this, "allumage de l'éclairage...", Toast.LENGTH_SHORT).show();
                }
                else{
                    ref_smsManager.sendTextMessage("+33769691960",null,"eteindre eclairage",null,null);
                    Toast.makeText(activity_mainmenu.this, "extinction de l'éclairage...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // envoi de la commande de redémarrage
        bt_redemmarrage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref_smsManager.sendTextMessage("+33769691960",null, "redemarrage systeme", null,null);
                Toast.makeText(activity_mainmenu.this, "Redemarrage du système de commande en cours...", Toast.LENGTH_SHORT).show();
            }
        });


    }

    // impossible car la méthode doit être dans une nouvelle classe (manque de temps)
    // lien : https://stackoverflow.com/questions/4637821/how-to-analyze-incoming-sms-on-android
    // lien 2 : https://stackoverflow.com/questions/17895465/the-method-abortbroadcast-is-undefined-for-the-type-smsreceiver

//    public void onReceive(Context context, Intent intent){
//        // empêche les notifications liées au message entrant
//        this.abortBroadcast();
//    }




}
