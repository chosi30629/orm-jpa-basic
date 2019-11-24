package jpabook.jpql01;

import jpabook.jpashop.domain.Item;
import jpabook.jpql01.domain.JPQLMember;
import jpabook.jpql01.domain.JPQLTeam;
import jpabook.jpql01.domain.MemberType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JPQLMain09 {

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
            member.setType(MemberType.ADMIN);

            em.persist(member);

            em.flush();
            em.clear();

            String query = "select m.username, 'HELLO', TRUE from JPQL2Member m " +
                            "where m.type = jpabook.jpql01.domain.MemberType.ADMIN";
            List<Object[]> resultList = em.createQuery(query)
                    .getResultList();

            for (Object[] objects : resultList) {
                System.out.println("objects[0] = " + objects[0]);
                System.out.println("objects[1] = " + objects[1]);
                System.out.println("objects[2] = " + objects[2]);
            }

            String query1 = "select m.username, 'HELLO', TRUE from JPQL2Member m " +
                    "where m.type = :userType";
            List<Object[]> resultList1 = em.createQuery(query1)
                    .setParameter("userType", MemberType.ADMIN)
                    .getResultList();

            // DTYPE
            em.createQuery("select i from Item i where type(i) = Book ", Item.class)
                    .getResultList();

            String query2 = "select m.username, 'HELLO', TRUE from JPQL2Member m " +
                    "where m.username is not null";
            List<Object[]> resultList2 = em.createQuery(query2)
                    .getResultList();

            String query3 = "select m.username, 'HELLO', TRUE from JPQL2Member m " +
                    "where m.age between 0 and 10";
            List<Object[]> resultList3 = em.createQuery(query3)
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
