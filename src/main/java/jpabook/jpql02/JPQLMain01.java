package jpabook.jpql02;

import jpabook.jpql02.domain.JPQL2Member;
import jpabook.jpql02.domain.JPQL2Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Collection;
import java.util.List;

public class JPQLMain01 {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            JPQL2Team team = new JPQL2Team();
            team.setName("team1");
            em.persist(team);

            JPQL2Member member = new JPQL2Member();
            member.setUsername("team1");
            member.setAge(10);
            member.changeTeam(team);

            em.persist(member);

            em.flush();
            em.clear();

//            String query = "select m.team.name From JPQL2Member m"; 묵시적 조인
            String query = "select m.username From JPQL2Team t join t.members m";   // 명시적 조인

            List<String> resultList = em.createQuery(query, String.class)
                    .getResultList();

            System.out.println("size = " + resultList.size());
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
