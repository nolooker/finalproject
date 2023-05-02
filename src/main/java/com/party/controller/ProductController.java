package com.party.controller;

import com.party.dto.ProductFormDto;
import com.party.dto.ProductSearchDto;
import com.party.entity.Product;
import com.party.sevice.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ProductController {

    @GetMapping(value = "/admin/products/new")
    public String productForm(Model model) {
        model.addAttribute("productFormDto", new ProductFormDto());

        return "/product/prInsertForm";
    }

    private final ProductService productService;

    @PostMapping(value = "/admin/products/new")
    public String productNew(@Valid ProductFormDto dto, BindingResult error, Model model,
                             @RequestParam("productImageFile") List<MultipartFile> uploadedFile) {
        if (error.hasErrors()) {
            return "/product/prInsertForm";
        }
        if (uploadedFile.get(0).isEmpty() && dto.getId() == null) {
            model.addAttribute("errorMessage", "첫 번째 이미지는 필수 입력 값입니다.");
            return "/product/prInsertForm";
        }

        try {
            productService.saveProduct(dto, uploadedFile);


        } catch (Exception err) {
            err.printStackTrace();
            model.addAttribute("errorMessage", "예외가 발생했습니다.");
            return "/product/prInsertForm";

        }
        return "redirect:/"; //메인페이지로 이동
    }

    @GetMapping(value = {"/admin/products","/admin/products/{page}"})
    public String productMange(ProductSearchDto dto, @PathVariable("page") Optional<Integer> page, Model model) {

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0,3) ;
        Page<Product> products = productService.getAdminProductPage(dto, pageable) ;

        model.addAttribute("products", products) ;
        model.addAttribute("searchDto", dto) ; // 검색 조건 유지를 위하여
        model.addAttribute("maxPage", 5) ; // 하단에 보여줄 최대 페이지 번호

        return "product/prList" ;
    }

}