package com.acreims.lombards.gestionfontaine;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class activity_graph extends Activity {

    private Classe_communiquant_vers_BDD ref_Classe_BDD;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Resources res = getResources();
        int rouge = res.getColor(R.color.red);
        int bleu = res.getColor(R.color.blue);
        int vert = res.getColor(R.color.vert);

        ref_Classe_BDD = new Classe_communiquant_vers_BDD();

        Utilisateur ref_utilisateur;
        Thread ref_thread;

        // Création d'objets
        ref_utilisateur = new Utilisateur();
        ref_thread = new Thread(ref_Classe_BDD);

        // Connexion à la BDD
        ref_Classe_BDD.initConnection("v-vaudey_fontaine_bdd",ref_utilisateur);

        ref_thread.start();

        while (ref_thread.isAlive());

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_graph);

        // Création du tableau contenant les différents tableaux de valeurs
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        // Création du tableau de valeurs (valeurs de type "float") --> consommation d'eau ici
        ArrayList<Entry> MainVoltage = new ArrayList<>();
        ArrayList<Entry> BatteryCurrent = new ArrayList<>();
        ArrayList<Entry> MaxPowerToday = new ArrayList<>();
        int i = 0;
        float f = 1f;
        while (i != 30)
        {
            MainVoltage.add(new Entry(f, ref_Classe_BDD.tabRes[i][0]));
            BatteryCurrent.add(new Entry(f, ref_Classe_BDD.tabRes[i][1]));
            MaxPowerToday.add(new Entry(f, ref_Classe_BDD.tabRes[i][2]));
            i++;
            f++;
        }

        LineDataSet Tension_principale = new LineDataSet(MainVoltage, "Tension principale");
        Tension_principale.setColor(rouge);

        LineDataSet Courrant_batterie = new LineDataSet(BatteryCurrent, "Courant de batterie");
        Courrant_batterie.setColor(bleu);

        LineDataSet Puissance_maximum = new LineDataSet(MaxPowerToday, "Puissance max/jour");
        Puissance_maximum.setColor(vert);

        // Ajout des tableau de d'entrées de valeurs
        dataSets.add(Tension_principale);
        dataSets.add(Courrant_batterie);
        dataSets.add(Puissance_maximum);

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
