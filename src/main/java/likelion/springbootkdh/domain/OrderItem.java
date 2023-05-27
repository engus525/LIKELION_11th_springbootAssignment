package likelion.springbootkdh.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.*;
import static lombok.AccessLevel.*;

@Entity//JPA entity임을 명시한다. DB의 테이블과 매칭될 Class이다.

//lombok 애노테이션으로 인자 없는 생성자 자동 생성한다.
//접근제어자를 protected로 설정하여 외부에서 함부로 생성하지 못하도록 막는다.@NoArgsConstructor(access = PROTECTED)
@Getter//lombok 애노테이션으로 getter를 자동 생성한다.
public class OrderItem
{
    @Id//모든 entity는 식별자를 가져야한다. DB에서의 PK와 매칭될 필드임을 명시한다.
    @GeneratedValue//식별자 값, 즉 PK를 자동 생성해준다. 전략또한 지정할 수 있으며, default 전략은 auto이다.
    @Column(name = "order_item_id")//DB에서의 column명을 지정한다.
    private Long id;

    @ManyToOne(fetch = LAZY)//해당 entity와 Order entity의 N:1 연관관계를 매핑한다. DB n+1문제를 방지하기 위해 fetch 전략은 반드시 lazy를 사용한다.
    @JoinColumn(name = "order_id")//FK를 매핑할 때 사용하며 order_id, 즉 Order 엔티티의 PK를 기준으로 매핑할 것임을 명시한다.
    private Order order;


    @ManyToOne(fetch = LAZY)//해당 entity와 Item entity의 N:1 연관관계를 매핑한다.
    @JoinColumn(name = "item_id")
    private Item item;

    private Integer price;
    private Integer count;

    public void setOrder(Order order)
    {
        this.order = order;
    }
}
