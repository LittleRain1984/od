package com.od.oh;

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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JPQLTest {

    private final Logger logger = LoggerFactory.getLogger(JPQLTest.class);

    private EntityManagerFactory emf;


    //if we specify the id of the entity, the persist would throw a PersistException: detached entity passed to persist
    @Test
    public void testPolymorphicQuery() {
        logger.info("Start testPolymorphicQuery.");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object> cq = cb.createQuery();
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