package com.ohgiraffers.springdatajpa.menu.controller;

import com.ohgiraffers.springdatajpa.menu.dto.MenuDTO;
import com.ohgiraffers.springdatajpa.menu.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/* 설명. @Slf4j(Simple Logging Facade for Java):
 *  Lombok 라이브러리의 어노테이션으로 클래스에 자동으로 SLF4J Logger 인스턴스를 추가해준다.
 *  따라서 개발자는 코드에 별도의 Logger 객체 선언 없이 'log' 변수를 사용해 바로 로깅 가능하다.
 *  로깅 프레임워크에 종속되지 않는 방식으로 로깅 인터페이스를 사용할 수 있게 해준다.
 * */
@Slf4j
@Controller
@RequestMapping("/menu")
public class MenuController {

    private MenuService menuService;

//    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    //리다이렉트
    @GetMapping("/querymethod")
    public void queryMethodPage() {}

    //가격으로 서치 (일정 가격 "초과" 목록 조회) => 다중 조회
    @GetMapping("/search")
    public String finfByMenu(@RequestParam Integer menuPrice, Model m){
        //form으로 온 데이터 받기
        //받은 데이터로 처리한 뒤 모델에 적용
        List<MenuDTO> menus = menuService.findByMenuPrice(menuPrice);
        m.addAttribute("menuPrice", menuPrice);
        m.addAttribute("menuList", menus);

        // 리다이렉트

        return "menu/searchResult";
    }
}
