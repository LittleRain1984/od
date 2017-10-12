package com.od.oh;

import com.od.oh.model.domain.OldDriver;
import org.hibernate.HibernateException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.*;

import org.hibernate.PersistentObjectException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EntityManagerStatePersistTest {

    private final Logger logger = LoggerFactory.getLogger(EntityManagerStatePersistTest.class);

    private EntityManagerFactory emf;


    //if we specify the id of the entity, the persist would throw a PersistException: detached entity passed to persist
    @Test
    public void testPersist() {
        logger.info("Start testPersist.");
        OldDriver od1 = new OldDriver();
        //od1.setId(999L);//set to a not existing id, the id would not take effect in the merge
        od1.setName("Edward");
        od1.setAge(33);
        od1.setDate();
        od1.setRemark("work in oTMS.");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            em.persist(od1);
            em.flush();
            tx.commit();
        } catch (PersistenceException exception) {
            System.out.println("catch PersistenceException" + exception);
            tx.rollback();
        } catch (Exception exp) {
            System.out.println("catch Exception");
            tx.rollback();
        } finally {
            em.close();
        }
    }


    private EntityManager getEntityManager() {
        return emf.createEntityManager();

    }

    @Before
    public void preparation() {
        emf = Persistence.createEntityManagerFactory("writeUnit", null);
    }

    @After
    public void destroy() {
        if (emf.isOpen()) {
            try {
                emf.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}