package com.party.controller;

import com.party.dto.ListProductDto;
import com.party.dto.ProductSearchDto;
import com.party.sevice.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class ListController {
    private  final ProductService productService;



    @PostMapping(value = "/")
    public String main(HttpSession httpSession) {
        System.out.println("세션영역에 데이터를 바인딩 합니다.");
        httpSession.setAttribute("id", "1");
        return "index" ;
    }

    @RequestMapping(value = "/")
    public String main(ProductSearchDto dto, Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent()? page.get() : 0,3);
        if (dto.getSearchQuery()==null){
            dto.setSearchQuery("");

        }

        Page<ListProductDto> products=productService.getMainProductPage(dto,pageable);

        model.addAttribute("products",products);
        model.addAttribute("searchDto",dto);
        model.addAttribute("maxPage",5);


        return "List";
    }
}
