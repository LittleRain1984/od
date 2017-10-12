package com.od.oh;

import com.od.oh.model.domain.Car;
import com.od.oh.model.domain.OldDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.*;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EntityManager2LCacheTest {

    private final Logger logger = LoggerFactory.getLogger(EntityManager2LCacheTest.class);

    private EntityManagerFactory emf;


    @Test
    public void testPersist() {
        EntityManagerFactory ef = Persistence.createEntityManagerFactory("writeUnit", null);

        EntityManager em = ef.createEntityManager();
        EntityTransaction ex = em.getTransaction();
        ex.begin();

        logger.info("car1 cached?: " + em.getEntityManagerFactory().getCache().contains(Car.class, 52L));
        Car car1 = em.find(Car.class, 52L);
        logger.info("car1: " + car1);
        logger.info("car1 cached?: " + em.getEntityManagerFactory().getCache().contains(Car.class, 52L));



        EntityManager em2 = ef.createEntityManager();
        EntityTransaction ex2 = em2.getTransaction();
        ex2.begin();





        Car car2 = em2.find(Car.class, 52L);
        car2.getBrand();
        logger.info("car2: " + car2);

        logger.info("car2 cached?: " + em2.getEntityManagerFactory().getCache().contains(Car.class, 52L));


        Car car3 = em2.find(Car.class, 52L);
        car3.getBrand();
        logger.info("car3: " + car3);

        ex.commit();
        em.close();
        ex2.commit();
        em2.close();

        ef.close();

    }


    private EntityManager getEntityManager() {
        return emf.createEntityManager();

    }

    //@Before
    public void preparation() {
        emf = Persistence.createEntityManagerFactory("writeUnit", null);
    }

    //@After
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