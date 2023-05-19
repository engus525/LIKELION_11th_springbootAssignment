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
 * @Controller :
 * Spring Bean으로 등록함과 동시에 Spring에게 Controller임을 인식시킨다.
 * 이를 통해 해당 Class의 메서드를 통해 http 요청을 처리할 수 있게된다.
 *
 * @RequestMapping :
 * 해당 Class의 메서드들이 요청을 주고받을 url 기본 경로를 설정한다.
 * 이 경우 모든 메서드는 "/members"의 url 경로를 기준으로 http 요청이 처리된다.
 **/
@Controller
@RequestMapping("members")
public class MemberController
{
    /*
     * private final : 외부로부터의 접근을 제한하고, 값이 불변함을 명시한다.
     * 이는 의존관계 주입을 통해 프로그램의 유연성을 높이기위함이다.
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
     * @GetMapping("new") : GET 요청을 처리할 url 경로를 지정한다. "/members/new"가 된다.
     *
     * model.addAttribute("memberForm", new Member()); :
     * model 객체에 Member를 저장하는데, 이때 model은 데이터를 View로 전달하는 데에 사용된다.
     * 즉, View에서는 Member의 정보를 사용할 수 있게된다.
     *
     * return "members/createMemberForm"; : 해당 View를 Client에게 전달한다.
     **/
    @GetMapping("new")
    public String createForm(Model model)
    {
        model.addAttribute("memberForm", new Member());
        return "members/createMemberForm";
    }

    /*
     * @PostMapping("new") :
     * POST 요청을 처리할 url 경로를 지정한다. "/members/new"가 된다.
     *
     * memberService.save(member); :
     * http로부터 받아온 데이터를 Member 객체에 매핑한 이후, 해당 코드를 통해 DB에 객체를 저장한다.
     *
     * return "redirect:/"; : Client가 Server로부터 상태 코드를 받으면, Client는 새로운 url로 redirect한다.
     **/
    @PostMapping("new")
    public String create(Member member)
    {
        memberService.save(member);
        return "redirect:/";
    }

    /*
     * List<Member> memberList = memberService.findAll(); :
     * DB 혹은 영속성 컨텍스트에서 모든 Member 엔티티를 조회하여 List에 저장한다.
     *
     * model.addAttribute("memberList", memberList); :
     * List를 model에 저장하여 View 계층으로 전달함으로써, 웹에서 모든 member의 정보를 조회할 수 있다.
     **/
    @GetMapping("")
    public String findAll(Model model)
    {
        List<Member> memberList = memberService.findAll();
        model.addAttribute("memberList", memberList);
        return "members/memberList";
    }
}