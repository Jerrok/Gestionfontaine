package com.acreims.lombards.gestionfontaine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Liens sur composants graphiques d'IHM
    EditText et_identifiant, et_passwd;
    Button bt_connexion;


    // Création des objets composés : relation de composition
    Classe_communiquant_vers_BDD ref_Classe_BDD = new Classe_communiquant_vers_BDD();

    //Connecteur_SGBD ref_Connecteur_SGBD = new Connecteur_SGBD();

    // Attributs
    public String identifiant;
    public String passwd;
    private String resultat;

    // Méthode : point d'entrée exécution de l'IHM
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Etablir un lien entre identifiant de composant d'IHM et le composant graphique
        et_identifiant = findViewById(R.id.et_identifiant);
        et_passwd = findViewById(R.id.et_passwd);
        bt_connexion = findViewById(R.id.bt_connexion);



        bt_connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Variables locales

                // Liens sur des classes de librairies
                Utilisateur ref_utilisateur;
                Thread ref_thread;

                // Liens sur les classes métiers

                // Création d'objets
                ref_utilisateur = new Utilisateur();
                ref_thread = new Thread(ref_Classe_BDD);

                // Extraction de données depuis l'IHM
                identifiant = et_identifiant.getText().toString();
                passwd = et_passwd.getText().toString();

                // Ecriture vers le stockage de l'identifiant et du mdp saisi par l'utilisatuer
                ref_utilisateur.setIdentifiant(identifiant);
                ref_utilisateur.setMdp(passwd);

                // Connexion à la BDD
                ref_Classe_BDD.initConnection("v-vaudey_fontaine_bdd",ref_utilisateur);

                ref_thread.start();

                while (ref_thread.isAlive());  // permet de faire vivre le thread

                resultat = ref_Classe_BDD.getResultat_identifiant();

                if (resultat.contentEquals("connecté")) {
                    Toast.makeText(MainActivity.this, "Vous êtes connecté", Toast.LENGTH_SHORT).show();

                    // Démarre la deuxième interface graphique
                    Intent intent = new Intent(MainActivity.this, activity_mainmenu.class);
                    startActivity(intent);
                    resultat = "";
                }
                else {
                    if (identifiant.isEmpty() || passwd.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Veuillez renseigner tous les champs", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Identifiant et / ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
                    }
                }

//                reponse_serv.setText(bdd.getResultat());

//                reponse_sql.setText(bdd.getResultat2());

//                tv_test.setText(bdd.getNom());


            }
        });





    }
}
