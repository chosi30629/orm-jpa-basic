package jpabook.jpql01.domain;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
public class JPQLMember {

    @Id @GeneratedValue
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "TEAM_ID")
    private JPQLTeam team;

    public void changeTeam(JPQLTeam team) {
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

    public JPQLTeam getTeam() {
        return team;
    }

    public void setTeam(JPQLTeam team) {
        this.team = team;
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
