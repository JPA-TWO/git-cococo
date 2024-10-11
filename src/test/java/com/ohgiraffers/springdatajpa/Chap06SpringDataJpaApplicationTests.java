package com.ohgiraffers.springdatajpa;

import com.jayway.jsonpath.JsonPath;
import com.ohgiraffers.springdatajpa.menu.dto.MenuDTO;
import com.ohgiraffers.springdatajpa.menu.service.MenuService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class Chap06SpringDataJpaApplicationTests {

    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    @Autowired
    private MenuService menuService;
    @Autowired
    private ModelMapper modelMapper;

    @BeforeAll
    public static void initFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpatest");
    }

    @BeforeEach
    public void initManager() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterAll
    public static void closeFactory() {
        entityManagerFactory.close();
    }

    @AfterEach
    public void closeManager() {
        entityManager.close();
    }

    // selectAll Test

    // selectByCode Test

    // querySelect Test

    @Test
    public void 쿼리문을_이용한_테스트(){
        int menuPrice = 10000;
        List<MenuDTO> menu= menuService.findByMenuPrice(menuPrice);

        System.out.println(menuPrice+"원 초과 메뉴 리스트");
        menu.forEach(System.out::println);
    }

    // insert Test

    // update Test
    @Test
    public void 메뉴이름_수정_테스트(){
        int menuCode =10;

        Menu menu = entityManager.find(Menu.class, menuCode);
        System.out.println("수정전 menu = " + menu);
        menu.setMenuName("이런식의 수정이 들어가요구르트");
        System.out.println("수정후 menu = " + menu);
        menuService.modifyMenu(modelMapper.map(menu, MenuDTO.class));

        Menu updateMenu = entityManager.find(Menu.class, menuCode);
        assertEquals(menu, updateMenu);
        System.out.println("updateMenu = " + updateMenu);
    }


    // delete Test
}
