package jpabook.jpql02.domain;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@NamedQuery(
        name = "Member.findByUsername",
        query = "select m from JPQL2Member m where m.username = :username"
)
public class JPQL2Member {

    @Id @GeneratedValue
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "TEAM_ID")
    private JPQL2Team team;

    @Enumerated(EnumType.STRING)
    private Member2Type type;

    public void changeTeam(JPQL2Team team) {
        this.team = team;
        team.getMembers().add(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public JPQL2Team getTeam() {
        return team;
    }

    public void setTeam(JPQL2Team team) {
        this.team = team;
    }

    public Member2Type getType() {
        return type;
    }

    public void setType(Member2Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "JPQLMember{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
                '}';
    }
}
