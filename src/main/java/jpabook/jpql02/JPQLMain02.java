package jpabook.jpql02;

import jpabook.jpql02.domain.JPQL2Member;
import jpabook.jpql02.domain.JPQL2Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JPQLMain02 {

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

            /*
            String query = "select m from JPQL2Member m";

            List<JPQL2Member> resultList = em.createQuery(query, JPQL2Member.class)
                    .getResultList();

            for (JPQL2Member member : resultList) {
                System.out.println("member = " + member.getUsername() + ", " + member.getTeam().getName());
                // 회원1, 팀A(SQL)
                // 회원2, 팀A(1차 캐시)
                // 회원3, 팀B(SQL)

                // 회원 100명 -> N + 1
            }
            */

            /*
            String query = "select m From JPQL2Member m join fetch m.team";

            List<JPQL2Member> resultList = em.createQuery(query, JPQL2Member.class)
                    .getResultList();

            for (JPQL2Member member : resultList) {
                System.out.println("member = " + member.getUsername() + ", " + member.getTeam().getName());
            }
            */

            String query = "select distinct t From JPQL2Team t join fetch t.members";   // 만약 fetch 가 아닌 일반 조인이면 팀의 정보만 가져옴

            List<JPQL2Team> resultList = em.createQuery(query, JPQL2Team.class)
                    .getResultList();

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
