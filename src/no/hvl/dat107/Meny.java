package no.hvl.dat107;

import no.hvl.dat107.eao.*;
import no.hvl.dat107.entity.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * Meny
 */
public class Meny {

    //brukar nextLine() og parseInt i staden for nextInt() + nextLine() då eg ofte får problem med det...

    //burde sjekke meir om eg får null tilbake osv

    private boolean ferdig;
    private AnsattEAO ansEAO;
    private AvdelingEAO avdEAO;
    private ProsjektEAO proEAO;
    private Scanner lesar;
    
    public Meny() {
        ferdig = false;
        ansEAO = new AnsattEAO();
        avdEAO = new AvdelingEAO();
        proEAO = new ProsjektEAO();
        lesar = new Scanner(System.in);
    }

    public void start() {

        System.out.println("------ Programmet startar ------");
        skrivUtValAlternativ();

        String valStr;
        int valVerdi;

        while (!ferdig) {
            System.out.println("\nSkriv inn eit tal mellom 0 og 13, 0 for å avslutte, eit anna tal for å få alternativa på nytt");
            try {
                valStr = lesar.nextLine();
                valVerdi = Integer.parseInt(valStr);
                valAlternativ(valVerdi);
            } catch (NumberFormatException e) {
                System.out.println("Det som vart skreve inn var ikkje eit tal! Prøv igjen.");
            }
        }
    }

    private void valAlternativ(int val) {
        String input = "";
        int id;
        Ansatt ans = null;
        Avdeling avd = null;
        switch (val) {
            case 0:
                ferdig = true;
                System.out.println("\n------ Programmet avsluttar ------");
                lesar.close();
                break;

            case 1:
                System.out.println("\n------ Søkjer etter ansatt med ID ------" + "\nSkriv inn ansatt-ID:");
                id = Integer.parseInt(lesar.nextLine());
                ans = ansEAO.finnAnsattMedId(id);
                System.out.println("\n" + ans);
                break;

            case 2:
                System.out.println("\n------ Søkjer etter ansatt med brukarnamn ------" + "\nSkriv inn brukarnamnet:");
                input = lesar.nextLine();
                ans = ansEAO.finnAnsattMedBN(input);
                System.out.println("\n" + ans);
                break;

            case 3:
                List<Ansatt> ansatte = ansEAO.finnAlleAnsatte();
                if(ansatte != null) {
                    System.out.println("\n------ Alle ansatte i databasen ------");
                    for (Ansatt a : ansatte) {
                        System.out.println(a);
                    }
                } else {
                    System.out.println("\n------ Ingen ansatte ------");
                }
                break;

            case 4:
                System.out.println("\n------ Oppdatere ansattinfo ------");
                System.out.println("Skriv inn id til ansatt som skal oppdaterast:");
                id = Integer.parseInt(lesar.nextLine());
                System.out.println("Oppdatere lønn? Y/N");
                input = lesar.nextLine();
                BigDecimal lonn = null;
                if(input.equalsIgnoreCase("Y")) {
                    System.out.println("Skriv inn ny lønn:");
                    input = lesar.nextLine();
                    lonn = BigDecimal.valueOf(Double.parseDouble(input));
                }

                System.out.println("Oppdatere stilling? Y/N");
                input = lesar.nextLine();
                String stilling = null;
                if(input.equalsIgnoreCase("Y")) {
                    System.out.println("Skriv inn ny stilling:");
                    stilling = lesar.nextLine();
                }
                ansEAO.oppdaterAnsattStillingLonn(id, stilling, lonn);
                break;

            case 5:
                ans = lesInnAnsatt();
                System.out.println("\n------ Ny ansatt er lagt inn i databasen ------");
                ansEAO.leggTilAnsatt(ans);
                break;

            case 6:
                System.out.println("\n------ Søkjer etter avdeling med ID ------");
                System.out.println("Skriv inn avdelingsid:");
                id = Integer.parseInt(lesar.nextLine());
                System.out.println(avdEAO.finnAvdelingMedId(id));
                break;

            case 7:
                System.out.println("\n------ Skriv ut alle ansatte på ein avdeling ------");
                System.out.println("Skriv inn avdelingsid:");
                id = Integer.parseInt(lesar.nextLine());
                avd = avdEAO.finnAvdelingMedId(id);
                System.out.println(avd + "\nSjef:\n" + avd.getSjef() + "\nAnsatte: ");
                List<Ansatt> aliste = avd.getAnsatte();
                for (Ansatt a : aliste) {
                    System.out.println(a);
                }

                //sjef blir skreve ut 2 ganger, ok?

                break;

            case 8:
                System.out.println("\n------ Oppdaterer avdelinga for ein ansatt ------");
                System.out.println("Skriv inn id til ansatt som skal oppdaterast:");
                int ansID = Integer.parseInt(lesar.nextLine());
                ans = ansEAO.finnAnsattMedId(ansID);

                if(ans == null) {
                    System.out.println("Fant ikkje den ansatte!");
                    break;
                } 

                if(ans.erSjef()) { //denne ansatte er ein sjef
                    System.out.println("Den ansatte er ein sjef, kan ikkje oppdatere avdelinga.");
                    break;
                }
                
                System.out.println("Skriv inn ID-en til den nye avdelinga til den ansatte:");
                int avdID = Integer.parseInt(lesar.nextLine());
                avd = avdEAO.finnAvdelingMedId(avdID);

                ansEAO.oppdaterAnsattAvdeling(ans, avd);
                break;

            case 9:
                lesInnAvdeling();
                System.out.println("\n------ Ny avdeling er lagt inn i databasen ------");
                break;

            case 10:
                Prosjekt p = lesInnProsjekt();
                proEAO.leggTilNyttProsjekt(p);
                System.out.println("\n------ Nytt prosjekt er lagt inn i databasen ------");
                break;

            case 11:
                regProsjektDeltakelse();
                //TODO
                //IKKJE FERDIG
                break;

            case 12:
                //TODO
                break;

            case 13:
                //TODO
                break;

            default:
                skrivUtValAlternativ();
                break;
        }
    }

    private Ansatt lesInnAnsatt() {
        System.out.println("\n------ Les inn ein ny ansatt ------");
        
        System.out.println("Skriv inn brukarnamn:"); //sjekke at det er berre 4 teikn i lengde???!?
        String brukarnamn = lesar.nextLine();
        System.out.println("Skriv inn fornamn:");
        String fornamn = lesar.nextLine();
        System.out.println("Skriv inn etternamn:");
        String etternamn = lesar.nextLine();
        System.out.println("Skriv inn dato (ISO-format: yyyy-mm-dd):");
        String tilsettDatoStr = lesar.nextLine();
        LocalDate dato = LocalDate.parse(tilsettDatoStr);
        System.out.println("Skriv inn stilling:");
        String stilling = lesar.nextLine();
        System.out.println("Skriv inn månadslønna (bruk . for komma):");
        String lonnStr = lesar.nextLine();
        BigDecimal lonn = BigDecimal.valueOf(Double.parseDouble(lonnStr));

        System.out.println("Skriv inn avdeling-ID:"); //søkjer etter id eller namn?
        int avdID = Integer.parseInt(lesar.nextLine());
        Avdeling avd = avdEAO.finnAvdelingMedId(avdID);

        Ansatt a = new Ansatt(brukarnamn, fornamn, etternamn, dato, stilling, lonn, avd);
        return a;
    }

    private void lesInnAvdeling() {
        System.out.println("\n------ Skriv inn ein ny avdeling ------");
        System.out.println("Skriv inn avdelingsnamn:");
        String avdNamn = lesar.nextLine();
        
        Ansatt sjef;
        boolean ferdig = false;
        System.out.println("Skriv inn ansatt-ID til sjefen for den nye avdelinga (må vere ein eksisterande ansatt):");
        do {
            int ansID = Integer.parseInt(lesar.nextLine());
            sjef = ansEAO.finnAnsattMedId(ansID);
            if(sjef == null) {
                System.out.println("Fant ikkje ansatt! Skriv inn ein ny ID:");
            } else {
                ferdig = !sjef.erSjef();
                if(!ferdig) {
                System.out.println("Den ansatte er ein sjef. Skriv inn ein ny ID:");
                }
            }
        } while (!ferdig);
        
        Avdeling avd = new Avdeling(avdNamn, sjef);

        avdEAO.leggTilNyAvdeling(avd, sjef);
    }

    private Prosjekt lesInnProsjekt() {
        System.out.println("\n------ Skriv inn eitt nytt prosjekt ------");
        System.out.println("Skriv inn prosjektnamnet:");
        String proNamn = lesar.nextLine();
        System.out.println("Skriv inn prosjektbeskrivinga:");
        String proBesk = lesar.nextLine();

        Prosjekt p = new Prosjekt(proNamn, proBesk);

        return p;
    }

    private void regProsjektDeltakelse() {

    }

    private void skrivUtValAlternativ() {
        System.out.println("\n------ Valalternativ ------");
        System.out.println("1 -- Søkje etter ansatt med ID");
        System.out.println("2 -- Søkje etter ansatt med brukarnamn");
        System.out.println("3 -- Skriv ut alle ansatte");
        System.out.println("4 -- Oppdatera ein ansatt sin stilling og/eller lønn");
        System.out.println("5 -- Leggje inn ein ny ansatt");
        System.out.println("6 -- Søkjer etter avdeling med ID");
        System.out.println("7 -- Skrive ut alle ansatte på ein avdeling"); //utheving av sjef!
        System.out.println("8 -- Oppdatere kva avdeling ein ansatt jobbar på (går ikkje på sjefar)");
        System.out.println("9 -- Leggje inn ein ny avdeling"); //må velje ein ny sjef, overføre nye sjefen til den nye avdelinga.
        System.out.println("10 - Leggje inn eit nytt prosjekt");
        System.out.println("11 - Registrera prosjektdeltaking"); //tilsett med rolle i prosjekt
        System.out.println("12 - Føre timar for ein ansatt på eit prosjekt"); //leggje til timar (plusse på)?
        System.out.println("13 - Utskrift av info om prosjekt"); //liste av deltakarar med rolle og timar + totalt timetal
    }
}