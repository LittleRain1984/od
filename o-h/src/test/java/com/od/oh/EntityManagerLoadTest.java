package com.od.oh;

import com.od.oh.model.domain.OldDriver;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class EntityManagerLoadTest {

    private EntityManagerFactory emf;

    @Test
    public void stateTest1() {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        OldDriver od1 = new OldDriver();//Transient state
        od1.setName("od1");
        od1.setAge(33);
        od1.setDate();
        od1.setRemark("first time setting");

        em.persist(od1); // persist
        em.flush();
        tx.commit();
        System.out.println(od1);

        em.close();//detached

        em = emf.createEntityManager();
        tx = em.getTransaction();
        tx.begin();
        od1.setAge(44);//od1 is detached
        od1.setRemark("second time setting");

        em.merge(od1);//persist

        em.flush();
        tx.commit();
        em.close();

        System.out.println(od1);


    }


    @Test
    public void stateTest2() {
        EntityManager em = this.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        OldDriver od1 = new OldDriver();//Transient
        od1.setName("od2");
        od1.setAge(55);
        od1.setRemark("stateTest2");
        od1.setDate();
        System.out.println("stateTest2, 1 em contains:" + em.contains(od1));
        em.merge(od1);//Persist
        System.out.println("stateTest2, 2 em contains:" + em.contains(od1));
        em.flush();
        System.out.println("stateTest2, 3 em contains:" + em.contains(od1));
        tx.commit();
        System.out.println("stateTest2, 4 em contains:" + em.contains(od1));
        em.close();//detached
    }

    @Test
    public void testMerge()

    {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        //OldDriver od2 = new OldDriver();
//        od2.setId(1L);
//        od2.setDate();
        //od2.setRemark("XYZ");

        //em.persist(od2);

        OldDriver od = em.find(OldDriver.class,1L);
        od.setName("aac");

        em.flush();
        tx.commit();
        em.close();//detached



    }

    @Test
    public void testTransaction(){
        EntityManager em1 = getEntityManager();
        EntityTransaction tx1 = em1.getTransaction();

        EntityManager em2 = getEntityManager();
        EntityTransaction tx2 = em2.getTransaction();

        tx1.begin();
        OldDriver od1 = em1.find(OldDriver.class, 3102L);


        tx2.begin();
        od1.setName("problem coming!------");
        //em2.merge(od1);
        tx2.commit();
        System.out.println(od1);

        tx1.rollback();
        System.out.println(od1);

        em1.close();
        em2.close();
    }

    @Test
    public void testRemove() {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        OldDriver od1 = new OldDriver();
        od1.setId(1L);
        em.remove(od1);
        tx.commit();
        em.close();
    }

    private EntityManager getEntityManager() {
        emf = Persistence.createEntityManagerFactory("helloworld", null);
        return emf.createEntityManager();

    }


    @Test
    public void testMap() {
        Map hashMap = new HashMap<Integer, String>();
        String s1 = (String) hashMap.put(1, "hello");
        System.out.println(s1);
        String s = (String) hashMap.put(1, "teddy");
        System.out.println(s);
        System.out.println(hashMap.get(1));
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
