package likelion.springbootkdh.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.*;
import static likelion.springbootkdh.domain.DeliveryStatus.ESTABLISHED;
import static lombok.AccessLevel.*;


@Entity//JPA entity임을 명시한다. DB의 테이블과 매칭될 Class이다.

//lombok 애노테이션으로 인자 없는 생성자 자동 생성한다.
//접근제어자를 protected로 설정하여 외부에서 함부로 생성하지 못하도록 막는다.
@NoArgsConstructor(access = PROTECTED)
public class Delivery
{
    @Id//모든 entity는 식별자를 가져야한다. DB에서의 PK와 매칭될 필드임을 명시한다.
    @GeneratedValue//식별자 값, 즉 PK를 자동 생성해준다. 전략또한 지정할 수 있으며, default 전략은 auto이다.
    @Column(name = "delivery_id")//DB에서의 column명을 지정한다.
    private Long id;

    @Enumerated(EnumType.STRING)//enum type 필드를 매핑할 때 사용한다. EnumType.STRING을 통해 필드의 값을 문자열로 변환하여 DB에 저장한다.
    private DeliveryStatus deliveryStatus;

    @Embedded//Embeddable Class를 내장했음을 의미한다.
    private Address address;

    /**
     * 해당 entity와 Order entity의 1:1 연관관계를 매핑한다.
     * DB n+1문제를 방지하기 위해 fetch 전략은 반드시 lazy를 사용한다.
     */
    @OneToOne(fetch = LAZY,mappedBy = "delivery")
    public Order order;

    //Delivery 객체 생성 메서드
    public static Delivery createDelivery(Order order, Address address)
    {
        Delivery delivery = new Delivery();
        delivery.order = order;
        delivery.deliveryStatus = ESTABLISHED;
        delivery.address = address;
        return delivery;
    }
}
