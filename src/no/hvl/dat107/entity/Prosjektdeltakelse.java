package no.hvl.dat107.entity;

import javax.persistence.*;

/**
 * Prosjektdeltakelse
 */
@Entity
@Table(name="prosjektdeltakelse", schema="innlevering_3")
public class Prosjektdeltakelse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int prosjektDelID; //evt eigen PK klasse... endre?
    private int arbeidstimar;
    private String rolle;

    @ManyToOne
    @JoinColumn(name="ansattid", referencedColumnName = "ansattid")
    private Ansatt ansatt;
    
    @ManyToOne
    @JoinColumn(name="prosjektid", referencedColumnName = "prosjektid")
    private Prosjekt prosjekt;

    public Prosjektdeltakelse() {}

    public Prosjektdeltakelse(int arbeidstimar, String rolle, Ansatt ansatt, Prosjekt prosjekt) {
        this.arbeidstimar = arbeidstimar;
        this.rolle = rolle;
        this.ansatt = ansatt;
        this.prosjekt = prosjekt;
    }

    public void leggTilTimar(int timar) {
        arbeidstimar += timar;
    }
    
    public void setAnsatt(Ansatt ansatt) {
        this.ansatt = ansatt;
    }

    public Ansatt getAnsatt() {
        return ansatt;
    }

    public void setArbeidstimar(int arbeidstimar) {
        this.arbeidstimar = arbeidstimar;
    }

    public int getArbeidstimar() {
        return arbeidstimar;
    }

    public void setProsjekt(Prosjekt prosjekt) {
        this.prosjekt = prosjekt;
    }

    public Prosjekt getProsjekt() {
        return prosjekt;
    }

    public int getProsjektDelID() {
        return prosjektDelID;
    }

    public void setRolle(String rolle) {
        this.rolle = rolle;
    }

    public String getRolle() {
        return rolle;
    }

    public String tilStringEnkelInfo() { //fjerne?
        return "Ansatt ID: " + ansatt.getAnsattID() + ". Prosjekt ID: " + prosjekt.getProsjektID() + ". Timar: " + arbeidstimar + ". Rolle: " + rolle;
    }

    @Override
    public String toString() {
        return "Ansatt ID: " + ansatt.getAnsattID() + ". Prosjekt ID: " + prosjekt.getProsjektID() + ". Timar: " + arbeidstimar + ". Rolle: " + rolle;
    }
}