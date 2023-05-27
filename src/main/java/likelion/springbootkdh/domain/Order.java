package likelion.springbootkdh.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.*;
import static lombok.AccessLevel.*;

@Entity//JPA entity임을 명시한다. DB의 테이블과 매칭될 Class이다.
@Data//lombok 애노테이션으로 getter,setter,toString 등의 메서드를 자동 생성한다.
@Table(name = "orders")//order은 DB 시스템상 예약어로 사용되어 테이블명으로 사용할 수 없다. orders로 이름지어 DB table을 생성한다.

//lombok 애노테이션으로 인자 없는 생성자 자동 생성한다.
//접근제어자를 protected로 설정하여 외부에서 함부로 생성하지 못하도록 막는다.@NoArgsConstructor(access = PROTECTED)
public class Order
{
    @Id//모든 entity는 식별자를 가져야한다. DB에서의 PK와 매칭될 필드임을 명시한다.
    @GeneratedValue//식별자 값, 즉 PK를 자동 생성해준다. 전략또한 지정할 수 있으며, default 전략은 auto이다.
    @Column(name = "order_id")//DB에서의 column명을 지정한다.
    private Long id;

    @ManyToOne(fetch = LAZY)//FK를 매핑할 때 사용하며 member_id, 즉 Member 엔티티의 PK를 기준으로 매핑할 것임을 명시한다.
    @JoinColumn(name = "member_id")
    private Member member;


    /**
     * 해당 entity와 Delivery entity의 1:1 연관관계를 매핑한다.
     * DB n+1문제를 방지하기 위해 fetch 전략은 반드시 lazy를 사용한다.
     * cascade = ALL을 통해 모든 상태 변화를 Delevery와 같이한다.
     */
    @OneToOne(fetch = LAZY,cascade = ALL)
    @JoinColumn(name = "delivery_id")//FK를 매핑할 때 사용하며 delibery_id, 즉 Delivery 엔티티의 PK를 기준으로 매핑할 것임을 명시한다.
    private Delivery delivery;

    /**
     * 해당 entity와 OrderItem entity의 1:N 연관관계를 매핑한다.
     * XToMany 의 경우 기본 fetch 전략이 lazy이므로 생략한다.
     * mappedBy 를 통해 OrderItem의 주인을 지정하여 양방향 연관관계를 설정한다.
     */
    @OneToMany(mappedBy = "order",cascade = ALL)
    private List<OrderItem> orderItemList = new ArrayList<>();

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)//enum type 필드를 매핑할 때 사용한다. EnumType.STRING을 통해 필드의 값을 문자열로 변환하여 DB에 저장한다.
    private OrderStatus orderStatus;

    //Business Logic
    public void setMember(Member member)
    {
        //연관관계 편의 메서드로서, 보통 양방향 연관관계에서 상호 참조를 편하게 관리할 때 사용한다.
        //일반적으로 주인 Class에서 선언하는게 편리하다.

        this.member = member;//Order 측에서는 member를 참조하고,
        member.getOrderList().add(this);//Member 측에서는 order를 참조한다.
    }

    public static Order createOrder(Member member, OrderItem... orderItems)
    {
        /*
          일반적으로 객체 생성 시 복잡한 로직이 있을 때 가독성 및 유지보수를 위해 생성자 대신 사용한다.
          이또한 연관관계 편의 메서드로서 order과 orderitem 측 모두에서의 참조를 설정한다.
         */

        Order order = new Order();
        order.setMember(member);
        order.orderDate = LocalDateTime.now();
        order.orderStatus = OrderStatus.ORDERED;
        order.delivery = Delivery.createDelivery(order, member.getAddress());
        for (OrderItem orderItem : orderItems)
        {
            order.orderItemList.add(orderItem);
            orderItem.setOrder(order);
        }
        return order;
    }


}