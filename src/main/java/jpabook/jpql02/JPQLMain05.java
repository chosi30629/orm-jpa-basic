package jpabook.jpql02;

import jpabook.jpql02.domain.JPQL2Member;
import jpabook.jpql02.domain.JPQL2Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JPQLMain05 {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            JPQL2Team teamA = new JPQL2Team();
            teamA.setName("팀A");
            em.persist(teamA);

            JPQL2Team teamB = new JPQL2Team();
            teamB.setName("팀A");
            em.persist(teamB);

            JPQL2Member member1 = new JPQL2Member();
            member1.setUsername("회원1");
            member1.setTeam(teamA);
            em.persist(member1);

            JPQL2Member member2 = new JPQL2Member();
            member2.setUsername("회원2");
            member2.setTeam(teamA);
            em.persist(member2);

            JPQL2Member member3 = new JPQL2Member();
            member3.setUsername("회원3");
            member3.setTeam(teamB);
            em.persist(member3);

            em.flush();
            em.clear();

            List<JPQL2Member> resultList = em.createNamedQuery("Member.findByUsername", JPQL2Member.class)
                    .setParameter("username", "회원1")
                    .getResultList();

            for (JPQL2Member member : resultList) {
                System.out.println("member = " + member);
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
