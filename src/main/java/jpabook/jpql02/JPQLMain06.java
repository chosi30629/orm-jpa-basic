package jpabook.jpql02;

import jpabook.jpql02.domain.JPQL2Member;
import jpabook.jpql02.domain.JPQL2Team;

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

            JPQL2Team teamA = new JPQL2Team();
            teamA.setName("팀A");
            em.persist(teamA);

            JPQL2Team teamB = new JPQL2Team();
            teamB.setName("팀A");
            em.persist(teamB);

            JPQL2Member member1 = new JPQL2Member();
            member1.setUsername("회원1");
            member1.setAge(0);
            member1.setTeam(teamA);
            em.persist(member1);

            JPQL2Member member2 = new JPQL2Member();
            member2.setUsername("회원2");
            member2.setAge(0);
            member2.setTeam(teamA);
            em.persist(member2);

            JPQL2Member member3 = new JPQL2Member();
            member3.setUsername("회원3");
            member3.setAge(0);
            member3.setTeam(teamB);
            em.persist(member3);

            // FLUSH 자동 호출
            int resultCount = em.createQuery("update JPQL2Member m set m.age = 20")
                    .executeUpdate();

            em.clear(); // 하고 안하고의 차이

            JPQL2Member findMember = em.find(JPQL2Member.class, member1.getId());
            System.out.println("findMember.getAge() = " + findMember.getAge());

            System.out.println("resultCount = " + resultCount);

            System.out.println("member1.getAge() = " + member1.getAge());
            System.out.println("member2.getAge() = " + member2.getAge());
            System.out.println("member3.getAge() = " + member3.getAge());

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
