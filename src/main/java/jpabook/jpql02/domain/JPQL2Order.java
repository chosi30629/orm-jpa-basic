package jpabook.jpql02.domain;

import javax.persistence.*;

@Entity
@Table(name = "JPQLORDERS")
public class JPQL2Order {

    @Id @GeneratedValue
    private Long id;
    private int orderAmount;

    @Embedded
    private JPQL2Address address;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private JPQL2Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }

    public JPQL2Address getAddress() {
        return address;
    }

    public void setAddress(JPQL2Address address) {
        this.address = address;
    }

    public JPQL2Product getProduct() {
        return product;
    }

    public void setProduct(JPQL2Product product) {
        this.product = product;
    }
}
