package no.hvl.dat107.eao;

import no.hvl.dat107.entity.*;

import javax.persistence.*;

/**
 * ProsjektEAO
 */
public class ProsjektEAO {

    private EntityManagerFactory emf;
    
    public ProsjektEAO() {
        emf = Persistence.createEntityManagerFactory("firmaPersistence");
    }

    public void leggTilNyttProsjekt(Prosjekt p) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            em.persist(p);

            tx.commit();
        } catch (Throwable e) {
            if(tx.isActive()) {
                tx.rollback();
            }
        } finally {
            em.close();
        }
    }

    public void registrerProsjektDeltakelse(Ansatt a, Prosjekt p, Prosjektdeltakelse pd) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            a = em.merge(a); //a og p finst allereie
            p = em.merge(p);

            em.persist(pd); //pd allereie oppretta med rett ansatt, prosjekt og id-ar
            
            a.leggTilProsjektDeltakelse(pd);
            p.leggTilProsjektDeltakelse(pd);

            tx.commit();
        } catch (Throwable e) {
            if(tx.isActive()) {
                tx.rollback();
            }
        } finally {
            em.close();
        }
    }

    public Prosjekt finnProsjekt(int prosjektID) {
        EntityManager em = emf.createEntityManager();
        Prosjekt p = null;

        try {
            p = em.find(Prosjekt.class, prosjektID);
        } finally {
            em.close();
        }
        return p;
    }
}