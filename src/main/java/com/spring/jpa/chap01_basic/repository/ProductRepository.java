package com.spring.jpa.chap01_basic.repository;

import com.spring.jpa.chap01_basic.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long > { //제네릭에서 첫번째 제너릭타입은 엔터티, 두번째는 프라이머리키 타입을 작성해주면된다. long이었지? 까먹었으면 Product파일가보면됨.

  /*  즉, 우리가 Spring-Data-JPA(스프링에서사용하는전용JPA)를 사용하고있는데,
    순수JPA를 사용하는건 위처럼 선언안하고, EntityManager를 사용한다
    순수JPA는 메서드도 save가 아니라 persist() 로선언한다.
    아무튼, 위처럼 제네릭설정하는데, 첫번째는 엔티티클래스타입을 선언해주고, 뒤에는 PK의 타입을 적더주면된다는 것이다.
    findAll(): 모든 엔터티를 찾아 반환. 같은 것을 사용할 수 있다.  즉, 제네릭 설정해줘야 여러 메서드들을 사용할 수 있다.

    */



}
