package jpabook.jpql02;

import jpabook.jpql02.domain.JPQL2Member;
import jpabook.jpql02.domain.JPQL2Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JPQLMain03 {

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

            String query = "select t From JPQL2Team t";

            // <property name="hibernate.default_batch_fetch_size" value="100" /> 로 멤버 뽑아낼때 N + 1 방지하고 in 쿼리
            List<JPQL2Team> resultList = em.createQuery(query, JPQL2Team.class)
                    .setFirstResult(0)
                    .setMaxResults(2)
                    .getResultList();

            System.out.println("resultList.size() = " + resultList.size());

            for (JPQL2Team team : resultList) {
                System.out.println("team = " + team.getName() + "|members=" + team.getMembers().size());
                for (JPQL2Member member : team.getMembers()) {
                    System.out.println("-> member = " + member);
                }
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
