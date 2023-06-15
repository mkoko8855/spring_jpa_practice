package com.spring.jpa.chap01_basic.repository;

import com.spring.jpa.chap01_basic.entity.Product;
import org.hibernate.boot.TempTableDdlTransactionHandling;
import org.hibernate.hql.internal.ast.tree.ExpectedTypeAwareNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import static com.spring.jpa.chap01_basic.entity.Product.Category.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest //테스트넣어주고
@Transactional
@Rollback(false) //테이블이 테스트 전과 후가 같게 설정하는 것. 데이터가 테이블에 남아있으려면 false. 우린 공부해야되니까 false로하자. 나중엔 트루로해줘야돼.
class ProductRepositoryTest {

    @Autowired //객체를 주입받을꺼야. jpa가 갖다줄것임
    ProductRepository productRepository;

    @BeforeEach
        //테스트 돌리기 전에 실행해야함
    void insertDummyDate() { //안에 더미데이터를 쫙 작성하고, 수정 조회 삭제 메서드 등을 돌려 볼 수 있겠다
        Product p1 = Product.builder().Name("아이폰").category(ELECTRONIC).price(1200000).build(); //이 모양대로 빌드해줘~
        /*원래는 p1.setName("아이폰")이런식으로 했었는데, @Builder를 이용해서 객체 초기화를 쉽게 할 수 있다. */
        //즉, @Builder를 Product파일 에 써줬는데, 클래스 이름을 따서 빌더 라는 객체를 만들어 준다. 빌더는 모든 필드의 값을 채워 줄 수 있는 메서드를 다 가져올 수 있다. 그거를 메서드 부르면서 값 채워넣고 마지막에 build해달라고 얘기하면, build는 Product를 리턴해준다.

        Product p2 = Product.builder().Name("탕수육").category(FOOD).price(20000).build();
        Product p3 = Product.builder().Name("구두").category(FASHION).price(100000).build();
        Product p4 = Product.builder().Name("음식물쓰레기").category(FOOD).build();//price는 안줘도돼. 내 맘대로 build.
    }


    //테스트 메서드 선언(메모장에 tdd로 설정해서 단축언어 tdd만쳐서 만듦)
    @Test
    @DisplayName("상품 4개를 데이터베이스에 저장해야 한다.")
    void testSave() {
        //given

        //Product p1 = new Product(); 그리고 @Builder를 쓰면 객체 생성도 이렇게 안씀.
        Product p1 = Product.builder().Name("아이폰").category(ELECTRONIC).price(1200000).build(); //이 모양대로 빌드해줘~
        /*원래는 p1.setName("아이폰")이런식으로 했었는데, @Builder를 이용해서 객체 초기화를 쉽게 할 수 있다. */
        //즉, @Builder를 Product파일 에 써줬는데, 클래스 이름을 따서 빌더 라는 객체를 만들어 준다. 빌더는 모든 필드의 값을 채워 줄 수 있는 메서드를 다 가져올 수 있다. 그거를 메서드 부르면서 값 채워넣고 마지막에 build해달라고 얘기하면, build는 Product를 리턴해준다.

        Product p2 = Product.builder().Name("탕수육").category(FOOD).price(20000).build();
        Product p3 = Product.builder().Name("구두").category(FASHION).price(100000).build();
        Product p4 = Product.builder().Name("음식물쓰레기").category(FOOD).build();//price는 안줘도돼. 내 맘대로 build.


        //when
        productRepository.save(p1);
        productRepository.save(p2);
        productRepository.save(p3);
        productRepository.save(p4);
        //이 프로덕트레파지토리는 ProductRepository다. 저 파일로 가보면 어떠한 메서드도 선언한 적이 없다. save는 insert명령이다. sql쓴거없다! -> db가서 조회해보면 값이 아이폰, 탕수육, 구두, 음식물쓰레기가 제대로 들어감.

        /* 웬절을 바꾸면 다음과 같다.
        Product save1 = productRepository.save(p1);
        Product save2 = productRepository.save(p2);
        Product save3 = productRepository.save(p3);
        Product save4 = productRepository.save(p4);
        */


        //Product product = Product.builder().Name("정장").price(100).category(FASHION).build();
        //Product saved = productRepository.save(product); //지연변수를 일부로 준비하면서 검증해보려고.
        //assertNotNull(saved); //saved가 아니라는 것을 나는 단언한다.
    }


    //이번엔 삭제해보자
    @Test
    @DisplayName("id가 2번인 데이터를 삭제해야 한다.")
    void testRemove() { //검증안하고 그냥해보자..
        //given
        long id = 2L;
        //when
        productRepository.deleteById(id);
        //then
    }

        //이번엔 조회를 해보자
        @Test
        @DisplayName("상품 전체 조회를 하면 상품의 개수가 4개여야 한다.")
        void testFindAll () {
            //given


            //when
            List<Product> products = productRepository.findAll();

            //then

//            for (Product product : products){ // iter라고 작성하고 자동완성하면 향상된 for문을 자동으로 해줌)
//            } 이거 대신에 모던하게 아래껄로 간결하고 이쁘게 쓰자.
            products.forEach(System.out::println);

            assertEquals( 4, products.size());


        }


        @Test
        @DisplayName("3번 상품을 조회하면 상품명이 '구두'여야 한다.")
        void testFindOne() {
            //given
            long id = 3L; //PK주고
            
            //when
            Optional<Product> product = productRepository.findById(id); //근데 프로덕트가 아니라 옵셔널이 왔다. 옵셔널은 널체크 안해도됨.

            //then
            //옵셔널 타입이 위에 product로 되어 있으니
            product.ifPresent(p -> {
                assertEquals("구두", p.getName());
            }); //ifPresent는 안쪽에 함수를 전달해야한다.

            Product foundProduct = product.get(); //이렇게 바로 꺼낼 수도 있음
            assertNotNull(foundProduct);


        }


    }




