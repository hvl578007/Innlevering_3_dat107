package no.hvl.dat107.eao;

import no.hvl.dat107.entity.*;

import javax.persistence.*;

/**
 * AvdelingEAO
 */
public class AvdelingEAO {

    private EntityManagerFactory emf;

    public AvdelingEAO() {
        emf = Persistence.createEntityManagerFactory("firmaPersistence");
    }

    public Avdeling finnAvdelingMedId(int id) {
        Avdeling avd = null;
        EntityManager em = emf.createEntityManager();

        try {
            avd = em.find(Avdeling.class, id);
        } finally {
            em.close();
        }

        return avd;
    }

    public void leggTilNyAvdeling(Avdeling avd, Ansatt sjef) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            
            em.persist(avd); //avd er managed

            sjef = em.merge(sjef);
            sjef.getAvdeling().fjernAnsatt(sjef);
            sjef.setAvdeling(avd);
            avd.leggTilAnsatt(sjef);
            
            tx.commit();
        } catch (Throwable e) {
            if(tx.isActive()) {
                tx.rollback();
            }
        } finally {
            em.close();
        }
    }
}