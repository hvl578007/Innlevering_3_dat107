package no.hvl.dat107.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

/**
 * Ansatt
 */
@Entity
@Table(name="ansatt", schema="innlevering_3")
public class Ansatt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ansattID;
    private String brukarnamn;
    private String fornamn;
    private String etternamn;
    private LocalDate tilsettDato;
    private String stilling;
    private BigDecimal maanadslonn;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "avdelingid", referencedColumnName = "avdelingid")
    private Avdeling avdeling;

    @OneToMany(mappedBy = "ansatt", fetch = FetchType.EAGER)
    private List<Prosjektdeltakelse> deltakingar ;

    public Ansatt() {}

    public Ansatt(String brukarnamn, String fornamn, String etternamn, LocalDate tilsettDato, String stilling, BigDecimal maanadslonn, Avdeling avdeling) {
        this.brukarnamn = brukarnamn;
        this.fornamn = fornamn;
        this.etternamn = etternamn;
        this.tilsettDato = tilsettDato;
        this.stilling = stilling;
        this.maanadslonn = maanadslonn;
        this.avdeling = avdeling;
        deltakingar = new ArrayList<Prosjektdeltakelse>();
    }

    public boolean erSjef() {
        return this.ansattID == avdeling.getSjef().getAnsattID();
    }

    public void leggTilProsjektDeltakelse(Prosjektdeltakelse pd) {
        deltakingar.add(pd);
    }

    public void fjernProsjektDeltakelse(Prosjektdeltakelse pd) {
        deltakingar.remove(pd);
    }

    //get- og settere
    public void setAnsattID(int ansattID) {
        this.ansattID = ansattID;
    }

    public int getAnsattID() {
        return ansattID;
    }

    public void setBrukarnamn(String brukarnamn) {
        this.brukarnamn = brukarnamn;
    }

    public String getBrukarnamn() {
        return brukarnamn;
    }

    public void setEtternamn(String etternamn) {
        this.etternamn = etternamn;
    }

    public String getEtternamn() {
        return etternamn;
    }

    public void setFornamn(String fornamn) {
        this.fornamn = fornamn;
    }

    public String getFornamn() {
        return fornamn;
    }

    public void setMaanadslonn(BigDecimal maanadslonn) {
        this.maanadslonn = maanadslonn;
    }

    public BigDecimal getMaanadslonn() {
        return maanadslonn;
    }

    public void setStilling(String stilling) {
        this.stilling = stilling;
    }

    public String getStilling() {
        return stilling;
    }

    public void setTilsettDato(LocalDate tilsettDato) {
        this.tilsettDato = tilsettDato;
    }

    public LocalDate getTilsettDato() {
        return tilsettDato;
    }

    public void setAvdeling(Avdeling avdeling) {
        this.avdeling = avdeling;
    }

    public Avdeling getAvdeling() {
        return avdeling;
    }

    @Override
    public String toString() {
        return "{ID: " + ansattID + ", " + brukarnamn + ". " + fornamn + " " + etternamn + ". Tilsett: " + tilsettDato.toString() + ". Stilling: " + stilling + ". Månadslønn: " + maanadslonn.toString() + ". Avdeling: " + avdeling.getAvdelingsnamn() + "}";
    }
}