package no.hvl.dat107.eao;

import no.hvl.dat107.entity.*;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.*;

/**
 * AnsattEAO
 */
public class AnsattEAO {

    private EntityManagerFactory emf;

    public AnsattEAO () {
        emf = Persistence.createEntityManagerFactory("firmaPersistence");
    }

    public Ansatt finnAnsattMedId(int id) {
        Ansatt a = null;
        EntityManager em = emf.createEntityManager();

        try {
            a = em.find(Ansatt.class, id);
        } finally {
            em.close();
        }
        return a;
    }

    public Ansatt finnAnsattMedBN(String brukarnamn) {
        Ansatt a = null;
        EntityManager em = emf.createEntityManager();

        try {
            TypedQuery<Ansatt> query = em.createQuery("SELECT a FROM Ansatt a WHERE a.brukarnamn LIKE :brukarnamn", Ansatt.class);
            query.setParameter("brukarnamn", brukarnamn);
            a = query.getSingleResult();

        } finally {
            em.close();
        }

        return a;
    }

    public List<Ansatt> finnAlleAnsatte() {
        EntityManager em = emf.createEntityManager();

        List<Ansatt> ansatte = null;
        try {
            TypedQuery<Ansatt> query = em.createQuery("SELECT a FROM Ansatt a", Ansatt.class);
            ansatte = query.getResultList();
 
        } finally {
            em.close();
        }
        return ansatte;
    }

    public void oppdaterAnsattStillingLonn(int ansattID, String stilling, BigDecimal lonn) { //kunne godt hatt to separate altså eller sendt med ansatt-objekt
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Ansatt a = em.find(Ansatt.class, ansattID); //evt kall til den andre metoden
            if(stilling != null) {
                a.setStilling(stilling);
            }
            if(lonn != null) {
                a.setMaanadslonn(lonn);
            }
            tx.commit();
            
        } catch (Throwable e) {
            if(tx.isActive()) {
                tx.rollback();
            }
        } finally {
            em.close();
        }

    }

    public void leggTilAnsatt(Ansatt a) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(a);
            
            Avdeling avd = a.getAvdeling();
            avd = em.merge(avd);
            avd.leggTilAnsatt(a);

            tx.commit();
            
        } catch (Throwable e) {
            if(tx.isActive()) {
                tx.rollback();
            }
        } finally {
            em.close();
        }
    }

    public void oppdaterAnsattAvdeling(Ansatt ans, Avdeling avd) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            ans = em.merge(ans);
            avd = em.merge(avd);

            Avdeling gmlAvd = ans.getAvdeling(); //må fjerne i frå den gamle avdelinga
            gmlAvd = em.merge(gmlAvd);

            gmlAvd.fjernAnsatt(ans);;
            avd.leggTilAnsatt(ans);
            ans.setAvdeling(avd);

            tx.commit();
            
        } catch (Throwable e) {
            if(tx.isActive()) {
                tx.rollback();
            }
        } finally {
            em.close();
        }
    }

    public void foreTimarPaaProsjekt(int ansattID, int prosjektID, int timar) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
    
        String queryStr = "SELECT pd FROM Prosjektdeltakelse pd WHERE pd.ansatt.ansattID = :ansattID AND pd.prosjekt.prosjektID = :prosjektID";

        try {
            tx.begin();
            
            TypedQuery<Prosjektdeltakelse> query = em.createQuery(queryStr, Prosjektdeltakelse.class);
            query.setParameter("ansattID", ansattID);
            query.setParameter("prosjektID", prosjektID);

            Prosjektdeltakelse pd = query.getSingleResult();

            pd.leggTilTimar(timar);

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