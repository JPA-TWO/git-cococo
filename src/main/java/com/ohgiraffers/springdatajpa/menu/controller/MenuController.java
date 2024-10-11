package com.ohgiraffers.springdatajpa.menu.controller;

import com.ohgiraffers.springdatajpa.menu.dto.MenuDTO;
import com.ohgiraffers.springdatajpa.menu.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/* 설명. @Slf4j(Simple Logging Facade for Java):
 *  Lombok 라이브러리의 어노테이션으로 클래스에 자동으로 SLF4J Logger 인스턴스를 추가해준다.
 *  따라서 개발자는 코드에 별도의 Logger 객체 선언 없이 'log' 변수를 사용해 바로 로깅 가능하다.
 *  로깅 프레임워크에 종속되지 않는 방식으로 로깅 인터페이스를 사용할 수 있게 해준다.
 * */
@Slf4j
@Controller
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/{menuCode}")
    public String findMenuByCode(@PathVariable int menuCode, Model model) {

        MenuDTO menu = menuService.findMenuByCode(menuCode);

        model.addAttribute("menu", menu);

        return "menu/detail";
    }
}
