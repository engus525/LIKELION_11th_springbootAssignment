package likelion.springbootkdh.repository;

import likelion.springbootkdh.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long>
{
}
