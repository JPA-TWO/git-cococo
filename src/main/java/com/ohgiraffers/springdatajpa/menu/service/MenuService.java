package com.ohgiraffers.springdatajpa.menu.service;

import com.ohgiraffers.springdatajpa.menu.dto.MenuDTO;
import com.ohgiraffers.springdatajpa.menu.entity.Menu;
import com.ohgiraffers.springdatajpa.menu.repository.MenuRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final ModelMapper modelMapper;

    public MenuService(MenuRepository menuRepository, ModelMapper modelMapper) {
        this.menuRepository = menuRepository;
        this.modelMapper = modelMapper;
    }

    public MenuDTO findMenuByCode(int menuCode) {

        Menu menu = menuRepository.findById(menuCode).orElseThrow(IllegalArgumentException::new);

        return modelMapper.map(menu, MenuDTO.class);
    }
    public List<MenuDTO> findByMenuPrice(Integer menuPrice) {
        List<Menu> menus = menuRepository.findByMenuPriceGreaterThan(menuPrice);

        return menus.stream().map(menu -> modelMapper.map(menu, MenuDTO.class)).toList();
    }

    public List<MenuDTO> findMenuList() {

        /* 설명. findAll 메소드는 이미 구현 되어 있으므로 인터페이스에 따로 정의할 필요가 없다.
         *  Sort(정렬) 기준을 전달하며 조회할 수도 있다.
         * */
        List<Menu> menuList = menuRepository.findAll(Sort.by("menuCode").descending());

        // 람다식 사용 버전
        return menuList
                .stream()
                .map(menu -> modelMapper.map(menu, MenuDTO.class))
                .collect(Collectors.toList());
    }

    /* 목차. Page -> 페이징 처리 후 */
    public Page<MenuDTO> findMenuList(Pageable pageable) {

        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
                    pageable.getPageSize(),
                    Sort.by("menuCode").descending());

        Page<Menu> menuList = menuRepository.findAll(pageable);
        return menuList.map(menu -> modelMapper.map(menu, MenuDTO.class));

    }

    @Transactional
    public void registNewMenu(MenuDTO newMenu) {
        menuRepository.save(modelMapper.map(newMenu, Menu.class));
    }

    @Transactional
    public void modifyMenu(MenuDTO modifyMenu) {

        Menu foundMenu = menuRepository.findById(modifyMenu.getMenuCode()).orElseThrow(IllegalArgumentException::new);
        foundMenu.setMenuName(modifyMenu.getMenuName());
    }

    @Transactional
    public void deleteMenu(Integer menuCode) {
        System.out.println("MenuDelete");
        menuRepository.deleteById(menuCode);
    }
}
