package likelion.springbootkdh.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Item {
    @Id
    @GeneratedValue
    private Long id;
    private String brand;
    private String name;
    private int price;
    private int stockQuantity;

}
