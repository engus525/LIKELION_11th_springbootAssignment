package likelion.springbootkdh.repository;

import likelion.springbootkdh.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>
{
}
