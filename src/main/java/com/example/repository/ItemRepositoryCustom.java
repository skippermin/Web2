package com.example.repository;

import com.example.dto.ItemSearchDto;
import com.example.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.dto.MainItemDto;


public interface ItemRepositoryCustom {
    // 상품 조회 조건을 담고 있는 ItemSearchDto 객체와 페이징 정보를 담고 있는 pageable 객체를 파라미터로 받는 getAdminItemPage 메소드 정의.
    // 반환 데이터 Page<Item>
    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}
