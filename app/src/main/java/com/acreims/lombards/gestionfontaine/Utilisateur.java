package com.acreims.lombards.gestionfontaine;

public class Utilisateur {

    private String mdp;
    private String identifiant;

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getMdp() {

        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    // Constructeur

    public Utilisateur ( )
        {
         this.mdp ="1234";
         this.identifiant = "Toto";
        }
}
