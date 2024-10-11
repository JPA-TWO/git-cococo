package com.ohgiraffers.springdatajpa.menu.service;

import com.ohgiraffers.springdatajpa.menu.dto.MenuDTO;
import com.ohgiraffers.springdatajpa.menu.entity.Menu;
import com.ohgiraffers.springdatajpa.menu.repository.MenuRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {
    private final ModelMapper modelMapper;
    MenuRepository menuRepository;

//    @Autowired
    public MenuService(MenuRepository menuRepository, ModelMapper modelMapper) {
        this.menuRepository = menuRepository;
        this.modelMapper = modelMapper;
    }

    public List<MenuDTO> findByMenuPrice(Integer menuPrice) {
        List<Menu> menus = menuRepository.findByMenuPriceGreaterThan(menuPrice);

        return  menus.stream().map(menu -> modelMapper.map(menu,MenuDTO.class)).toList();
    }
}
