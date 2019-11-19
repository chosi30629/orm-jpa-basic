package jpabook.jpql01;

import jpabook.jpql01.domain.JPQLMember;
import jpabook.jpql01.domain.JPQLTeam;
import jpabook.jpql01.domain.MemberType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JPQLMain10 {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            JPQLTeam team = new JPQLTeam();
            team.setName("관리자");
            em.persist(team);

            JPQLMember member = new JPQLMember();
            member.setUsername(null);
            member.setAge(10);
            member.changeTeam(team);
            member.setType(MemberType.ADMIN);

            em.persist(member);

            em.flush();
            em.clear();

            String query =
                    "select " +
                            "case when m.age <= 10 then '학생요금' " +
                            "     when m.age >= 60 then '경로요금' " +
                            "     else '일반요금' " +
                            "end " +
                    "from JPQLMember m";

            List<String> result = em.createQuery(query, String.class)
                    .getResultList();

            for (String s : result) {
                System.out.println("s = " + s);
            }

            String query1 =
                    "select coalesce(m.username, '이름 없는 회원') " +
                            "from JPQLMember m";

            List<String> result1 = em.createQuery(query1, String.class)
                    .getResultList();

            for (String s : result1) {
                System.out.println("s = " + s);
            }

            String query2 =
                    "select nullif(m.username, '관리자') " +
                            "from JPQLMember m";

            List<String> result2 = em.createQuery(query2, String.class)
                    .getResultList();

            for (String s : result2) {
                System.out.println("s = " + s);
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
