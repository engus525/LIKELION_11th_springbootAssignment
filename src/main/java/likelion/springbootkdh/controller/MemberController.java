package likelion.springbootkdh.controller;


import java.util.List;

import likelion.springbootkdh.domain.Member;
import likelion.springbootkdh.service.MemberService;
import likelion.springbootkdh.service.MemberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * 채우시오
 **/
@Controller
@RequestMapping("members")
public class MemberController
{
    /*
     * 채우시오.
     **/
    private final MemberService memberService;

    /*
     * (예시)
     * 이 것은 생성자입니다.
     * @Autowired라는 어노테이션은 MemberController 객체를 실행해야 할 때 필요한 의존성을 주입해달라고 선언하기 위해 명시하는 어노테이션이며, 생성자 주입 방식을 선언하고 있습니다.
     * MemberController의 필드를 MemberService 타입으로 선언하였지만, 생성자 paramer에는 MemberServiceImpl이 주입되게 함으로써 느슨한 결합(Loosen Coupling)을 구현하였습니다.
     * @참고 : 실제로는 MemberController 생성자의 파라미터에 MemberServiceImpl이 아니라 MemberService로 쓰여있어도 스프링이 알아서 구현체 클래스의 인스턴스 (MemberServiceImpl memberserviceimpl)를 넣어주게 됩니다.
     *       즉, public MemberController(MemberService memberService) {this.memberService = memberService;} 와 같이 작성해도 에러가 없고, 이게 사실 정석입니다.
     *       아래처럼 작성해 둔 이유는, 실제로는 아래와 같이 동작한다는 것을 여러분 눈으로 먼저 보길 바랐던 제 마음이었습니다.
     *       지금, MemberController의 필드가 MemberService 타입의 데이터인데, 생성자로 주입되는 것은 MemberServiceImpl 타입이라는 것을 충분히 음미하시길 바랍니다.
     **/
    @Autowired
    public MemberController(MemberServiceImpl memberServiceImpl)
    {
        this.memberService = memberServiceImpl;
    }

    /*
     * 채우시오
     **/
    @GetMapping("new")
    public String createForm(Model model)
    {
        model.addAttribute("memberForm", new Member());
        return "members/createMemberForm";
    }

    /*
     * 채우시오
     **/
    @PostMapping("new")
    public String create(Member member)
    {
        memberService.save(member);
        return "redirect:/";
    }

    /*
     * 채우시오
     **/
    @GetMapping("")
    public String findAll(Model model)
    {
        List<Member> memberList = memberService.findAll();
        model.addAttribute("memberList", memberList);
        return "members/memberList";
    }
}