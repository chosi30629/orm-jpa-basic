package jpabook.jpql01;

import jpabook.jpql01.domain.JPQLMember;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JPQLMain06 {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            for (int i = 1; i <= 100; i++) {
                JPQLMember member = new JPQLMember();
                member.setUsername("member" + i);
                member.setAge(i);
                em.persist(member);
            }

            em.flush();
            em.clear();

            List<JPQLMember> result = em.createQuery("select m from JPQLMember m order by m.age desc", JPQLMember.class)
                    .setFirstResult(10)
                    .setMaxResults(20)
                    .getResultList();
            System.out.println("result.size() = " + result.size());
            for (JPQLMember jpqlMember : result) {
                System.out.println("jpqlMember = " + jpqlMember);
            }

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
