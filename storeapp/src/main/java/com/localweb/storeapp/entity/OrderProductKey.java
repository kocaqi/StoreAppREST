
package com.localweb.storeapp.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OrderProductKey implements Serializable {

    long order_id;
    long product_id;

    public OrderProductKey() {
    }

    public OrderProductKey(long order_id, long product_id) {
        this.order_id = order_id;
        this.product_id = product_id;
    }

    public long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(long order) {
        this.order_id = order;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product) {
        this.product_id = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderProductKey that = (OrderProductKey) o;
        return order_id == that.order_id && product_id == that.product_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(order_id, product_id);
    }

    @Override
    public String toString() {
        return "OrderProductKey{" +
                "order=" + order_id +
                ", product=" + product_id +
                '}';
    }
}

