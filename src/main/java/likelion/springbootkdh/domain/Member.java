package likelion.springbootkdh.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity//JPA entity임을 명시한다. DB의 테이블과 매칭될 Class이다.
@Data//lombok 애노테이션으로 getter,setter,toString 등의 메서드를 자동 생성한다.
public class Member {
    @Id//모든 entity는 식별자를 가져야한다. DB에서의 PK와 매칭될 필드임을 명시한다.
    @GeneratedValue//식별자 값, 즉 PK를 자동 생성해준다. 전략또한 지정할 수 있으며, default 전략은 auto이다.
    @Column(name = "member_id")//DB에서의 column명을 지정한다.
    private Long id;
    private String name;

    @Embedded//Embeddable Class를 내장했음을 의미한다.
    private Address address;

    /**
     * 해당 entity와 Order entity의 1:N 연관관계를 매핑한다.
     * XToMany 의 경우 기본 fetch 전략이 lazy이므로 생략한다.
     */
    @OneToMany(mappedBy = "member")
    private List<Order> orderList = new ArrayList<>();

    //Member 객체 생성 메서드
    public static Member createMember(String name, Address address)
    {
        Member member = new Member();
        member.name = name;
        member.address = address;
        return member;
    }
}

