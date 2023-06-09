package com.party.controller;

import com.party.dto.MemberFormDto;
import com.party.entity.Member;
import com.party.sevice.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;


    @GetMapping(value = "/detail/{id}")
    public String SelectOne(@PathVariable("id") Integer id, Model model) {  // 상세 보기
        Member member = memberService.SelectOne(id);
        model.addAttribute("member", member);
        return "member/memberDetail";

    }

    @GetMapping(value = "/list")
    public String SelectAll(Model model){
        List<Member> memberList = memberService.SelectAll();
        model.addAttribute("list", memberList) ;
        return "member/memberList" ;
    }

    @GetMapping("/new")
    public String memberForm(Model model){
        //타임 리프에서 사용할 객체 memberFormDto를 바인딩 합니다.
        model.addAttribute("memberFormDto",new MemberFormDto());
        return "member/memberForm";

    }
    @PostMapping("/new")
    public String newMember(@Valid MemberFormDto memberFormDto, BindingResult bindingResult,Model model){
        if(bindingResult.hasErrors()){
            return "member/memberForm";
        }
        try {
            Member member =Member.createMember(memberFormDto,passwordEncoder);
            memberService.saveMember(member);
        }catch (IllegalStateException e){
            model.addAttribute("errMessage",e.getMessage());
            return "/member/memberForm";

        }
        System.out.println("포스트 방식 요청 들어옴");
        return "redirect:/"; //메인 페이지로 가주세요
    }
    // form 태그와 SecurityConfig.java파일에 정의되어 있습니다.
    @GetMapping(value = "/login")
    public String loginMember(Model model){
        return "/member/memberForm";

    }
    @GetMapping(value = "/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg","이메일 또는 비밀번호를 확인해 주세요");
        return "/member/memberForm";

    }
}
