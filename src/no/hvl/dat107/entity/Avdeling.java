package no.hvl.dat107.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

/**
 * Avdeling
 */
@Entity
@Table(name="avdeling", schema="innlevering_3")
public class Avdeling {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int avdelingID;
    private String avdelingsnamn;

    @OneToOne
    @JoinColumn(name="sjefid", referencedColumnName = "ansattid")
    private Ansatt sjef;

    @OneToMany(mappedBy = "avdeling", fetch = FetchType.EAGER)
    private List<Ansatt> ansatte;

    public Avdeling() {}

    public Avdeling(String namn, Ansatt sjef) {
        avdelingsnamn = namn;
        ansatte = new ArrayList<Ansatt>();
        this.sjef = sjef;
    }

    public int getAvdelingID() {
        return avdelingID;
    }

    public void setAvdelingsnamn(String avdelingsnamn) {
        this.avdelingsnamn = avdelingsnamn;
    }

    public String getAvdelingsnamn() {
        return avdelingsnamn;
    }

    public List<Ansatt> getAnsatte() {
        return ansatte;
    }

    public void setSjef(Ansatt sjef) {
        this.sjef = sjef;
    }

    public Ansatt getSjef() {
        return sjef;
    }

    public void leggTilAnsatt(Ansatt a) {
        ansatte.add(a);
    }

    public void fjernAnsatt(Ansatt a) {
        ansatte.remove(a);
    }

    @Override
    public String toString() {
        return "Avdeling: " + avdelingsnamn + " - ID: " + avdelingID;
    }
}