package jpabook.jpql01.domain;

import javax.persistence.*;

@Entity
@Table(name = "JPQLORDERS")
public class JPQLOrder {

    @Id @GeneratedValue
    private Long id;
    private int orderAmount;

    @Embedded
    private JPQLAddress address;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private JPQLProduct product;

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

    public JPQLAddress getAddress() {
        return address;
    }

    public void setAddress(JPQLAddress address) {
        this.address = address;
    }

    public JPQLProduct getProduct() {
        return product;
    }

    public void setProduct(JPQLProduct product) {
        this.product = product;
    }
}
