package jpabook.jpql01;

import jpabook.jpql01.domain.JPQLAddress;
import jpabook.jpql01.domain.JPQLMember;
import jpabook.jpql01.domain.JPQLTeam;
import jpabook.jpql01.dto.MemberDTO;

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
            JPQLMember member = new JPQLMember();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            em.flush();
            em.clear();

            // 멤버 엔티티 프로젝션
            List<JPQLMember> result = em.createQuery("select m from JPQLMember m", JPQLMember.class)
                    .getResultList();

            JPQLMember findMember = result.get(0);
            findMember.setAge(20);

            // 팀 엔티티 프로젝션
            List<JPQLTeam> result1 = em.createQuery("select m.team from JPQLMember m", JPQLTeam.class)
                    .getResultList();   // 묵시적 조인

            List<JPQLTeam> result2 = em.createQuery("select t from JPQLMember m join m.team t", JPQLTeam.class)
                    .getResultList();   // 명시적 조인

            // 임베디드 프로젝션
            em.createQuery("select o.address from JPQLOrder o", JPQLAddress.class)
                    .getResultList();

            // 스칼라 타입 프로젝션
            List resultList = em.createQuery("select m.username, m.age from JPQL2Member m")
                    .getResultList();
            Object o = resultList.get(0);
            Object[] results = (Object[]) o;
            System.out.println("username = " + results[0]);
            System.out.println("age = " + results[1]);

            List<Object[]> resultList1 = em.createQuery("select m.username, m.age from JPQL2Member m")
                    .getResultList();
            Object[] results1 = resultList1.get(0);
            System.out.println("username = " + results1[0]);
            System.out.println("age = " + results1[1]);

            List<MemberDTO> resultList2 = em.createQuery("select new jpabook.jpql01.dto.MemberDTO(m.username, m.age) from JPQLMember m", MemberDTO.class)
                    .getResultList();
            MemberDTO memberDTO = resultList2.get(0);
            System.out.println("username = " + memberDTO.getUsername());
            System.out.println("age = " + memberDTO.getAge());

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
