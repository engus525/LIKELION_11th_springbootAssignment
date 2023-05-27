package likelion.springbootkdh.domain;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable//다른 Class에서 해당 Class를 내장할 수 있음을 의미한다.
@Data//lombok 애노테이션으로 getter,setter,toString 등의 메서드를 자동 생성한다.
@NoArgsConstructor//lombok 애노테이션으로 인자 없는 생성자 자동 생성한다.
public class Address
{
    //주소에 대한 속성을 담은 embeddable Class이다.
    private String city;
    private String state;
    private String street;
    private String zipcode;
}
