package com.od.oh;

import com.od.oh.model.domain.OldDriver;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EntityManagerStateMergeTest {

    private EntityManagerFactory emf;

    //#1 Merge a Transient state Object
    //#2 Merge a detached state Object
    //#3 Merge a deleted state Object
    //#4.1 Merge a persist state Object
    //#4.2 Merge a persist state Object with a different entity manager
    @Test
    public void testMerge1() {
        OldDriver od1 = new OldDriver();
        od1.setId(999L);//set to a not existing id, the id would not take effect in the merge
        System.out.println("od1:" + od1);
        od1.setName("Edward");
        od1.setAge(33);
        od1.setDate();
        od1.setRemark("work in oTMS.");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        OldDriver od2 = em.merge(od1);
        em.flush();

        Assert.assertNotEquals(od1, od2);
        Assert.assertFalse(em.contains(od1));
        Assert.assertTrue(em.contains(od2));

        tx.commit();
        em.close();
    }

    @Test
    public void testMerge2() {
        EntityManager em1 = emf.createEntityManager();
        EntityTransaction tx1 = em1.getTransaction();
        tx1.begin();

        OldDriver od0 = new OldDriver();
        od0.setName("Edward");
        od0.setDate();
        od0.setRemark("New object for obtaining an ID from database");

        em1.persist(od0);
        em1.flush();
        tx1.commit();

        OldDriver od1 = new OldDriver();
        od1.setId(od0.getId());//set to an existing id
        System.out.println("od1:" + od1);
        od1.setName("Edward");
        od1.setAge(33);
        od1.setDate();
        od1.setRemark(od0.getRemark() + "comment from the merge operation.");
        EntityManager em2 = emf.createEntityManager();
        EntityTransaction tx2 = em2.getTransaction();

        tx2.begin();
        OldDriver od2 = em2.merge(od1);
        em2.flush();

        Assert.assertFalse(em2.contains(od0));
        Assert.assertFalse(em2.contains(od1));
        Assert.assertTrue(em2.contains(od2));

        Assert.assertTrue(em1.contains(od0));
        Assert.assertFalse(em1.contains(od1));
        Assert.assertFalse(em1.contains(od2));

        Assert.assertNotEquals(od1, od2);

        tx2.commit();
        em2.close();

    }

    @Test
    public void testMerge4_2() {
        EntityManager em1 = emf.createEntityManager();
        EntityTransaction tx1 = em1.getTransaction();
        tx1.begin();

        OldDriver od0 = new OldDriver();
        od0.setName("Edward");
        od0.setDate();
        od0.setRemark("New object for obtaining an ID from database");

        em1.persist(od0);

        tx1.commit();

        OldDriver od1 = new OldDriver();
        od1.setId(od0.getId());//set to an existing id
        System.out.println("od1:" + od1);
        od1.setName("Edward");
        od1.setAge(33);
        od1.setDate();
        od1.setRemark(od0.getRemark() + "testMerge4_2()_>em2.");
        EntityManager em2 = emf.createEntityManager();
        EntityTransaction tx2 = em2.getTransaction();

        tx2.begin();
        OldDriver od2 = em2.merge(od1);
        em2.flush();
        tx2.commit();

        tx1 = em1.getTransaction();
        tx1.begin();
        em1.refresh(od0);
        od0.setRemark(od0.getRemark() + "testMerge4_2()->em1.");
        em1.flush();
        tx1.commit();

        Assert.assertFalse(em2.contains(od0));
        Assert.assertFalse(em2.contains(od1));
        Assert.assertTrue(em2.contains(od2));

        Assert.assertNotEquals(od1, od2);

        em1.close();
        em2.close();
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
