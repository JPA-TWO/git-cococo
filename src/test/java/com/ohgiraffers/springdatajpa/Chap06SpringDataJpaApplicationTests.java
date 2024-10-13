package com.ohgiraffers.springdatajpa;

import com.ohgiraffers.springdatajpa.menu.dto.MenuDTO;
import com.ohgiraffers.springdatajpa.menu.service.MenuService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    @Test
    public void 메뉴코드로_메뉴_조회_테스트() {

        int menuCode = 9;

        Menu foundMenu = entityManager.find(Menu.class, menuCode);

        assertNotNull(foundMenu);
        assertEquals(menuCode, foundMenu.getMenuCode());

        System.out.println("foundMenu = " + foundMenu);
    }

    // querySelect Test

    @Test
    public void 쿼리문을_이용한_테스트(){
        int menuPrice = 10000;
        List<MenuDTO> menu= menuService.findByMenuPrice(menuPrice);

        System.out.println(menuPrice+"원 초과 메뉴 리스트");
        menu.forEach(System.out::println);
    }

    // insert Test
    @Test
    public void 신규메뉴_추가_테스트() {

        Menu newMenu = new Menu();

        newMenu.setMenuName("테스트 메뉴");
        newMenu.setMenuPrice(10000);
        newMenu.setCategoryCode(12);
        newMenu.setOrderableStatus("Y");

        System.out.println(newMenu.getMenuCode());

        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        try {
            entityManager.persist(newMenu);
            entityTransaction.commit();
            System.out.println(newMenu.getMenuCode());
            System.out.println("newMenu = " + newMenu);
        } catch (Exception e) {
            entityTransaction.rollback();
            e.printStackTrace();
        }

        assertTrue(entityManager.contains(newMenu));

    }

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
