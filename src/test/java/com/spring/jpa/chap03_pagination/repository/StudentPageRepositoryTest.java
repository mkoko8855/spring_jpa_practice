package com.spring.jpa.chap03_pagination.repository;

import com.github.gavlyukovskiy.boot.jdbc.decorator.dsproxy.DataSourceProxyProperties;
import com.spring.jpa.chap02_querymethod.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import javax.lang.model.SourceVersion;
import javax.transaction.Transactional;
import java.sql.SQLOutput;
import java.util.List;

@SpringBootTest
@Transactional //JPA는 인서트, 업데이트, 딜레트 시에 트랜잭션을 기준으로 동작하는 경우가 많다. 그래서 기능을 보장 받기 위해 웬만하면 트랜잭션 기능을 함께 사용해야 한다. 나중에 MVC 구조에서 Service클래스에 아노테이션을 첨부하면 된다.
@Rollback(false) //롤백도 원래 무조건 트루가 맞다. 우린 연습이니까, 테스트끝났으면 롤백시켜놔야하니까..
class StudentPageRepositoryTest {

    @Autowired
    StudentPageRepository studentPageRepository;



    //페이징을 맛보려면 데이터가 들어 있어야겠지
    @BeforeEach
    void bulkInsert(){
        //학생을 147명 저장하자.
        for (int i=1; i<=147; i++){
            Student s = Student.builder()
                    .name("김테스트" + i)
                    .city("도시시" + i)
                    .major("전공공" + i)
                    .build();

            studentPageRepository.save(s);
        }
        
    }


    @Test
    @DisplayName("기본 페이징 테스트")
    void testBasicPagination() {
        //given
        int pageNo = 1;  //페이지 번호
        int amount = 10; //한 화면에 게시글에 보여질 것은 10개다.

        //페이지 정보 생성(페이지 번호가 zero-based이다. 1주면 2페이지다. 그래서 -1해줘야함)
        //페이저블(Pageable) 객체를 만들자 (PageRequest객체를 이용하여) > 아래 findAll에 페이저블을 전달할 수 있어야 하니까
        PageRequest pageInfo = PageRequest.of(pageNo - 1, amount, Sort.by("name").descending()); //PageRequest말고 Pageable 이라고 적어도됨. sort.by()는 뭘 기준으로 정렬할 건지 적자. 컬럼명 말고 필드명인 name을 내림차순 하겠다!    근데 조건이 여러개라면, Sort.by(Sort.Order.desc( property: "name"), Sort.Order.asc( property: "city")) 이런식으로 걸면 된다.
        //이제 findAll 메서드에 페이저블 객체를 보낼 수 있음


        //when
        Page<Student> students = studentPageRepository.findAll(pageInfo); //여기에서 학생 정보 얻을 수 있음

        //학생 정보를 꺼내자. (getContent는 페이징이 완료된 데이터셋의 결과를 얻어낼 때 사용한다)
        List<Student> studentList = students.getContent();




        //예외로(추가로), 다른 정보도 꺼내보자. 총 페이지 수를 구해보자. (메서드굉장히많지)
        int totalPages = students.getTotalPages();
        //총 학생수 같은것도
        long totalElements = students.getTotalElements();
        //다음 버튼의 여부도..
        boolean next = students.hasNext();
        //이전 버튼의 여부도.
        boolean prev = students.hasPrevious();
        


        //then
        //studentList만 출력(확인)해보자
        System.out.println("\n\n\n");
        studentList.forEach(System.out::println);
        System.out.println("\n\n\n");


        //추가 출력하자
        System.out.println("prev = " + prev);
        System.out.println("totalPages = " + totalPages);
        System.out.println("next = " + next);
        System.out.println("totalElements = " + totalElements);

        /*
        추가 출력결과 :
        prev = false
        totalPages = 15
        next = true
        totalElements = 147
         */



        //근데, 아쉬운게 정렬이 없다. 게시판은 내림차순으로 정렬을 해야되는데 정렬 조건도 3번째 매개값으로 주자. 위로가자.
        //이렇게 적자. sort.by()는 뭘 기준으로 정렬할 건지 적자. stu_name을 내림차순 하겠다!

    }

    
    
    
    
    @Test
    @DisplayName("이름 검색 + 페이징")
    void testSearchAndPagination() {
        //given
        int pageNo = 1;
        int size = 10;
        Pageable pageInfo =  PageRequest.of(pageNo-1, size); //페이저블 pageInfo한테 정보주기
        //when
        Page<Student> students =  studentPageRepository.findByNameContaining( "3", pageInfo);


        //다른 정보가 궁금하면 정보 좀 더 얻어도됨.
        //페이지 몇번이나 나오나 볼까? (전체가 아니라 이름에 3이 포함된게 몇명인지 조건이 걸려있다. 페이지 객체한테 물어보자)
        int totalPages = students.getTotalPages();
        long totalElements = students.getTotalElements();


        //then
        System.out.println("\n\n\n");
        students.getContent().forEach(System.out::println);
        System.out.println("\n\n\n");
        
        //추가정보써준것도 출력하자
        System.out.println("totalElements = " + totalElements);
        System.out.println("totalPages = " + totalPages);
    }

}