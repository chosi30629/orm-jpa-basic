package jpabook.jpql01;

import jpabook.jpql01.domain.JPQLMember;
import jpabook.jpql01.domain.JPQLTeam;
import jpabook.jpql01.domain.MemberType;

import javax.persistence.*;
import java.util.List;

public class JPQLMain11 {

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
            member.setUsername("choseongil");
            member.setAge(10);
            member.changeTeam(team);
            member.setType(MemberType.ADMIN);

            JPQLMember member1 = new JPQLMember();
            member1.setUsername("kkk");
            member1.setAge(10);
            member1.changeTeam(team);
            member1.setType(MemberType.ADMIN);

            em.persist(member);
            em.persist(member1);

            em.flush();
            em.clear();

            String query2 =
//                    "select 'a' || 'b' from JPQLMember m";
//                    "select concat('a', 'b') from JPQLMember m";
//                    "select substring(m.username, 2, 3) from JPQLMember m";
//                    "select locate('de', 'abcdefg') from JPQLMember m"; // Integer 타입
//                    "select size(t.members) from JPQLTeam t";
//                    "select index(t.members) from JPQLTeam t";
//                    "select function('group_concat', m.username) from JPQLMember m";
                    "select group_concat(m.username) from JPQL2Member m";    // hibernate 사용 시

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
