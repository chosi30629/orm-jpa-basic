package jpabook.jpql01;

import jpabook.jpql01.domain.JPQLMember;
import jpabook.jpql01.domain.JPQLTeam;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JPQLMain07 {

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

//            String query = "select m from JPQLMember m inner join m.team t";  // inner 생략 가능
//            String query = "select m from JPQLMember m left outer join m.team t";   // outer 생략 가능
//            String query = "select m from JPQLMember m, JPQLTeam t where m.username = t.name";  // 세타 조인
//            String query = "select m from JPQLMember m left join m.team t on t.name = 'team1'";   // 조인 대상 필터링
            String query = "select m from JPQLMember m left join JPQLTeam t on m.username = t.name";    // 연관관계 없는 엔티티 외부 조인, 내부도 가능
            List<JPQLMember> resultList = em.createQuery(query, JPQLMember.class)
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
