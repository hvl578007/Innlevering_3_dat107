package no.hvl.dat107.entity;

import java.util.*;

import javax.persistence.*;

/**
 * Prosjekt
 */
@Entity
@Table(name="prosjekt", schema ="innlevering_3")
public class Prosjekt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int prosjektID;
    private String prosjektNamn;
    private String prosjektBeskrivelse;


    @OneToMany(mappedBy = "prosjekt", fetch = FetchType.EAGER)
    private List<Prosjektdeltakelse> deltakingar;

    public Prosjekt() {}

    public Prosjekt(String prosjektNamn, String prosjektBeskrivelse) {
        this.prosjektNamn = prosjektNamn;
        this.prosjektBeskrivelse = prosjektBeskrivelse;
        deltakingar = new ArrayList<Prosjektdeltakelse>();
    }

    public void leggTilProsjektDeltakelse(Prosjektdeltakelse pd) {
        deltakingar.add(pd);
    }

    public void fjernProsjektDeltakelse(Prosjektdeltakelse pd) {
        deltakingar.remove(pd);
    }

    public int getProsjektID() {
        return prosjektID;
    }

    public void setProsjektNamn(String prosjektNamn) {
        this.prosjektNamn = prosjektNamn;
    }

    public String getProsjektNamn() {
        return prosjektNamn;
    }

    public void setProsjektBeskrivelse(String prosjektBeskrivelse) {
        this.prosjektBeskrivelse = prosjektBeskrivelse;
    }

    public String getProsjektBeskrivelse() {
        return prosjektBeskrivelse;
    }

    @Override
    public String toString() {
        String ut = "ID: " + prosjektID + " - " + prosjektNamn + ": " + prosjektBeskrivelse + "\nTilsette:\n";
        int totalTimar = 0;

        for (Prosjektdeltakelse pd : deltakingar) {
            Ansatt a = pd.getAnsatt();
            ut += a.getFornamn() + " " + a.getEtternamn() + " - Rolle: " + pd.getRolle() + " - Timar: " + pd.getArbeidstimar() + "\n";
            totalTimar += pd.getArbeidstimar();
        }

        ut += "Timar totalt: " + Integer.toString(totalTimar);
        return ut;
    }
}