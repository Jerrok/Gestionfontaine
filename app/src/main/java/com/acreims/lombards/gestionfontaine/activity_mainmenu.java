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

import java.util.concurrent.TimeUnit;

/**
 * Created by ThomasSIM-ASUS on 20/03/2018.
 */

public class activity_mainmenu extends Activity {

    // Liens sur composants graphiques d'IHM
    private Button bt_afficher_conso, bt_redemmarrage;
    private Switch sw_etat_pompe, sw_etat_eclairage;

    // Création d'objets
    private SmsManager ref_smsManager = SmsManager.getDefault();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_mainmenu);

        // Etablir un lien entre identifiant de composant d'IHM et le composant graphique
        sw_etat_pompe = findViewById(R.id.sw_etat_fontaine);
        sw_etat_eclairage = findViewById(R.id.sw_etat_eclairage);
        bt_afficher_conso = findViewById(R.id.bt_afficher_conso);
        bt_redemmarrage = findViewById(R.id.bt_redemarrage);


        //mise a jour des switches avec acces à la bdd


        Classe_communiquant_vers_BDD ref_Classe_BDD = new Classe_communiquant_vers_BDD();
        Thread ref_thread;

        // Création d'objets
        Utilisateur ref_utilisateur = new Utilisateur();
        ref_thread = new Thread(ref_Classe_BDD);
        ref_Classe_BDD.initConnection("v-vaudey_fontaine_bdd",ref_utilisateur);

        ref_thread.start();

        while (ref_thread.isAlive());

        String etat_pompe = ref_Classe_BDD.getEtat_pompe();
        String etat_eclairage = ref_Classe_BDD.getEtat_eclairage();

        if (etat_pompe.equals("allume"))
            sw_etat_pompe.toggle();
        if (etat_eclairage.equals("allume"))
            sw_etat_eclairage.toggle();


        // affiche l'IHM contenant le graphique des consommations d'eau, d'électricité, ainsi que le niveau de batterie
        bt_afficher_conso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_mainmenu.this, activity_graph.class);
                startActivity(intent);
            }
        });

        // envoi de la commande d'allumage / extinction de la fontaine
        sw_etat_pompe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
}
