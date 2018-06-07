package com.acreims.lombards.gestionfontaine;

import android.app.Activity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Random;

public class activity_graph extends Activity {

    private Classe_communiquant_vers_BDD ref_Classe_BDD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        Random random = new Random();
        ref_Classe_BDD = new Classe_communiquant_vers_BDD();

        Utilisateur ref_utilisateur;
        Thread ref_thread;

        // Liens sur les classes métiers

        // Création d'objets
        ref_utilisateur = new Utilisateur();
        ref_thread = new Thread(ref_Classe_BDD);

        // Connexion à la BDD
        ref_Classe_BDD.initConnection("v-vaudey_fontaine_bdd",ref_utilisateur);

        ref_thread.start();

        while (ref_thread.isAlive());

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_graph);

        //ref_Classe_BDD.voir_tension();
        // Création du tableau contenant les différents tableaux de valeurs
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        // Création du tableau de valeurs (valeurs de type "float") --> consommation d'eau ici
        ArrayList<Entry> MainVoltage = new ArrayList<>();
        ArrayList<Entry> BatteryCurrent = new ArrayList<>();
        ArrayList<Entry> MaxPowerToday = new ArrayList<>();
        int i = 0;
        float f = 1f;
        //int i2 = (random.nextInt(100 - 0 + 1)+1);  // rend les valeurs de la variable aléatoires
        while (i != 30)
        {
            MainVoltage.add(new Entry(f, ref_Classe_BDD.tabRes[i][0]));
            BatteryCurrent.add(new Entry(f, ref_Classe_BDD.tabRes[i][1]));
            MaxPowerToday.add(new Entry(f, ref_Classe_BDD.tabRes[i][2]));
            i++;
            f++;
            //i2 = (random.nextInt(100 - 0 + 1)+1);
        }


        // Création du tableau de valeurs (valeurs de type "float") --> consommation d'électricité ici
//        ArrayList<Entry> entries2 = new ArrayList<>();
//        entries2.add(new Entry(1f,20));
//        entries2.add(new Entry(2f,15));
//        entries2.add(new Entry(3f,10));
//        entries2.add(new Entry(4f,10));
//        entries2.add(new Entry(5f,30));
//        entries2.add(new Entry(6f,50));

        LineDataSet Tension_principale = new LineDataSet(MainVoltage, "Tension principale");
        Tension_principale.setColor();

        // Ajout du tableau de consommation d'eau
        dataSets.add(Tension_principale);
        dataSets.add(new LineDataSet(BatteryCurrent, "Courant de batterie"));
        dataSets.add(new LineDataSet(MaxPowerToday, "Puissance max/jour"));
        MainVoltage.

        // Ajout du tableau de consommation d'électricité
//        dataSets.add(new LineDataSet(entries2, "Consommation d'électricité"));

        // Appel du composant graphique d'IHM "LineChart"
        LineChart chart = findViewById(R.id.chart);

        // Création des lignes et utilisation dans le composant graphique d'IHM
        LineData data = new LineData(dataSets);
        chart.setData(data);


    }


    public void setLienGraphToSGBD(Classe_communiquant_vers_BDD classe_communiquant_vers_bdd)

    {

        this.ref_Classe_BDD = classe_communiquant_vers_bdd;

    }

}
