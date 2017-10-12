package com.od.oh;

import com.od.oh.model.domain.OldDriver;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class EntityManagerRemoveTest {

    @Test
    public void contextLoads() {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("helloworld", null);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        OldDriver od = em.getReference(OldDriver.class, 1L);

        System.out.println("is loaded after get reference:" + emf.getPersistenceUnitUtil().isLoaded(od));

        em.remove(od);

        em.flush();
        tx.commit();
        em.close();

        od.getName();

        emf.close();
    }


}
