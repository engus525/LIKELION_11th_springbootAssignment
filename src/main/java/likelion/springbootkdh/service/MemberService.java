package likelion.springbootkdh.service;

import likelion.springbootkdh.domain.Item;
import likelion.springbootkdh.domain.Member;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MemberService
{
    @Transactional
    public Member save(Member member);

    @Transactional(readOnly = true)
    public List<Member> findAll();

    @Transactional(readOnly = true)
    public Member findById(Long id);
}
