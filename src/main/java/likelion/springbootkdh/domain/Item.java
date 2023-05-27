package likelion.springbootkdh.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity//JPA entity임을 명시한다. DB의 테이블과 매칭될 Class이다.
@Data//lombok 애노테이션으로 getter,setter,toString 등의 메서드를 자동 생성한다.
public class Item {
    @Id//모든 entity는 식별자를 가져야한다. DB에서의 PK와 매칭될 필드임을 명시한다.
    @GeneratedValue//식별자 값, 즉 PK를 자동 생성해준다. 전략또한 지정할 수 있으며, default 전략은 auto이다.
    @Column(name = "item_id")//DB에서의 column명을 지정한다.
    private Long id;
    private String brand;
    private String name;
    private Integer price;
    private Integer stockQuantity;

    /**
     * 해당 entity와 OrderItem entity의 1:N 연관관계를 매핑한다.
     * XToMany 의 경우 기본 fetch 전략이 lazy이므로 생략한다.
     */
    @OneToMany(mappedBy = "item")
    private List<OrderItem> orderItemList = new ArrayList<>();

    /**Business Logic
     *
     * Why here?
     * 필드값 변경에 관한 로직을 entity Class에 구현함으로써,
     * 도메인 객체가 스스로 자신의 상태를 제어하게하여 응집도를 높임과 동시에
     * DB의 정확성을 높여 데이터의 일관성을 유지한다.
     */

    public void addStock(int quantity)
    {
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity)
    {
        int restStock = this.stockQuantity - quantity;
        if(restStock < 0) throw new IllegalStateException("need more stock");
        this.stockQuantity = restStock;
    }
}
