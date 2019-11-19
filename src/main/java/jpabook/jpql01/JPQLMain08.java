package jpabook.jpql01;

import jpabook.jpql01.domain.JPQLMember;
import jpabook.jpql01.domain.JPQLTeam;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JPQLMain08 {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            JPQLTeam team = new JPQLTeam();
            team.setName("team1");
            em.persist(team);

            JPQLMember member = new JPQLMember();
            member.setUsername("team1");
            member.setAge(10);
            member.changeTeam(team);

            em.persist(member);

            em.flush();
            em.clear();

            String query = "select (select avg(m1.age) from JPQLMember m1) as avgAge from JPQLMember m join JPQLTeam t on m.username = t.name";
            em.createQuery(query, JPQLMember.class)
                .getResultList();

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
