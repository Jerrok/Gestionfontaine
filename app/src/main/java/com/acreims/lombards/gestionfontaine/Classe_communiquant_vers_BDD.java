package com.acreims.lombards.gestionfontaine;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by ThomasSIM-ASUS on 02/02/2018.
 */

public class Classe_communiquant_vers_BDD implements Runnable{

    // Attributs
    public int[][] tabRes   = new int[30][3];
    private  String url;
    private  String log;
    private  String pass;
    private  String resultat_identifiant = "";
    private  String resultat_nom_str = "";
    private  String resulat_passwd_str = "";
    private  boolean resultat_nom;
    private  boolean resultat_passwd;

    private activity_graph act = new activity_graph();

    // Création d'objets
    private Statement stmt;
    private Connection connection;
    private ResultSet rs;

    // Constructeur
    public Classe_communiquant_vers_BDD() {
        resultat_nom = false;
        resultat_passwd = false;
    }

    // Mutateurs et accesseur
    public String getResultat_identifiant() {

        return resultat_identifiant;
    }


//    public String getResultat_requete_sql(){
//        return resultat_requete_sql;
//    }

    // Méthode pour rassembler les données pour la connexion à la BDD
    public void initConnection(String adresse, Utilisateur u){
        url = "jdbc:mysql://"+"mysql-v-vaudey.alwaysdata.net"+"/"+adresse;

        this.log=u.getIdentifiant();
        this.pass=u.getMdp();
    }


    // Connexion à la BDD et comparaison des identifiants saisis avec ceux de la BDD
    // Renvoi un string vers le MainActivity
    @Override
    public void run() {
        try {

            act.setLienGraphToSGBD(this);

            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, "v-vaudey", "fontaine");
            stmt = connection.createStatement();

            String query = "SELECT `identifiant`, `mdp` from `user`";


            rs = stmt.executeQuery(query);

            resultat_identifiant = "";
            while (rs.next()) {
                resultat_nom_str = rs.getString("identifiant");
                resulat_passwd_str = rs.getString("mdp");

                if (resultat_nom_str.equals(log)) {
                    resultat_nom = true;
                } else resultat_nom = false;

                if (resulat_passwd_str.equals(pass)) {
                    resultat_passwd = true;
                } else resultat_passwd = false;


                if (resultat_nom && resultat_passwd) {
                    resultat_identifiant = "connecté";

                    resultat_nom = false;
                    resultat_passwd = false;
                }

            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        voir_donnees_regulateur();

    }

//    public boolean voir_etat_pompe_eclairage(){
//
//    }

    private void voir_donnees_regulateur(){

        String query = "SELECT `V`,`I`,`H21`,`Date` FROM `solaire` ORDER BY `Date` asc limit 300";
        int tension, battery_current, max_power_today;
        String date, datememo="";
        String[]dateAvantSplit;
        System.out.println(query);
        try {

            rs = stmt.executeQuery(query);



            int count = 0;
            while (rs.next()) {
                tension = rs.getInt("V");
                battery_current = rs.getInt("I");
                max_power_today = rs.getInt("H21");
                dateAvantSplit = rs.getString("Date").split(" ");
                date = dateAvantSplit[0];
                if (!datememo.equals(date) && count<30){
                    datememo=date;
                    tabRes[count][0] = tension;
                    tabRes[count][1] = battery_current;
                    tabRes[count][2] = max_power_today;
                    System.out.println(tabRes[count][0]);
                    count++;
                }

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        //return tabRes;
    }


}
