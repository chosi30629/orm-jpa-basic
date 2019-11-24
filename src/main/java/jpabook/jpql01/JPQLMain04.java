package jpabook.jpql01;

import jpabook.jpql01.domain.JPQLMember;

import javax.persistence.*;
import java.util.List;

public class JPQLMain04 {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            JPQLMember member = new JPQLMember();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            // 반환타입 명확
            TypedQuery<JPQLMember> query1 = em.createQuery("select m from JPQLMember m", JPQLMember.class);
            TypedQuery<String> query2 = em.createQuery("select m.username from JPQLMember m", String.class);

            // 반환타입 미명확
            Query query3 = em.createQuery("select m.username, m.age from JPQL2Member m");

            TypedQuery<JPQLMember> query4 = em.createQuery("select m from JPQLMember m", JPQLMember.class);
            List<JPQLMember> resultList = query4.getResultList();
            for (JPQLMember jpqlMember : resultList) {
                System.out.println("jpqlMember = " + jpqlMember);
            }

            TypedQuery<JPQLMember> query5 = em.createQuery("select m from JPQLMember m where m.id = 10", JPQLMember.class);
            JPQLMember singleResult = query5.getSingleResult();
            System.out.println("singleResult = " + singleResult);

            JPQLMember query6 = em.createQuery("select m from JPQLMember m where m.username = :username", JPQLMember.class)
                    .setParameter("username", "member1")
                    .getSingleResult();
            System.out.println("singleResult1 = " + query6.getUsername());


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }

}
