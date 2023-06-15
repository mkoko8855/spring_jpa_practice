package com.spring.jpa.chap01_basic.repository;

import com.spring.jpa.chap01_basic.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long > { //제네릭에서 첫번째 제너릭타입은 엔터티, 두번째는 프라이머리키 타입을 작성해주면된다. long이었지? 까먹었으면 Product파일가보면됨.


}
