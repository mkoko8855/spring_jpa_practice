package com.spring.jpa.chap02_querymethod.repository;

import com.spring.jpa.chap02_querymethod.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest //이거하면 다 끌고옴
@Transactional
@Rollback(false) //데이터 남아야 하니 true말고 false로~
class StudentRepositoryTest {

    @Autowired
    StudentRepository studentRepository;

    @BeforeEach
    void insertData() {
        Student s1 = Student.builder()
                .name("춘식이")
                .city("서울시")
                .major("수학과")
                .build();


        Student s2 = Student.builder()
                .name("언년이")
                .city("부산시")
                .major("수학교육과")
                .build();
        Student s3 = Student.builder()
                .name("대길이")
                .city("한양도성")
                .major("체육")
                .build();


        studentRepository.save(s1);
        studentRepository.save(s2);
        studentRepository.save(s3);


    }


    @Test
    @DisplayName("이름이 춘식이인 학생의 정보를 조회해야 한다.")
    void testFindByName() {
        //given
        String name = "춘식이";
        //when
        List<Student> students = studentRepository.findByName(name);
        //then
        //Assertions.assertEquals(); 일단 이렇게 완성시키고 알트엔터누르면 임포트해준다. 그리고 assertEquals로 써준다.
        assertEquals(1, students.size());

        System.out.println("students.get(0) = " + students.get(0));

    }


    @Test
    @DisplayName("testFindByCityAndMajor")
    void testFindByCityAndMajor() {
        //given
        //부산시에 살면서 수학과인 사람 (언년이가 해당된다)
        String city = "부산시";
        String major = "수학교육과";
        //when
        List<Student> students = studentRepository.findByCityAndMajor(city, major);

        //then
        assertEquals(1, students.size());
        assertEquals("언년이", students.get(0).getName()); //하나더 단언해보자.
    }


    @Test
    @DisplayName("testFindByMajorContaining") //그냥 귀찮아서 이렇게 씀
    void testFindByMajorContaining() {
        //given
        String major = "수학";
        //when
        List<Student> students = studentRepository.findByMajorContaining(major);

        //then
        assertEquals(2, students.size());
        System.out.println("\n\n\n");

        //student를 다 까보자
        //students.forEach(stu -> System.out.println(stu)); 말고 더 간단하게면
        students.forEach(System.out::println);

        System.out.println("\n\n\n");
    }


    @Test
    @DisplayName("testNativeSQL")
    void testNativeSQL(){
        //given
        String name = "대길이";
        //when
        Student student = studentRepository.findNameWithSQL(name);

        //then
        assertEquals("한양도성", student.getCity());
        System.out.println("\n\n\n");


        System.out.println("student = " + student);

        System.out.println("\n\n\n");
    }


    //네이티브가 아닌 JPQL로 돌려보자. 즉, StudentRepository 가보면 JPQL은 테이블명(구조도)필요없고 엔터티 구성(구조)만 알면 JPA가 알아서 쿼리준다.
    @Test
    @DisplayName("testFindCityWithJPQL")
    void testFindCityWithJPQL(){
        //given
        String city = "서울시";
        //when
        List<Student> students = studentRepository.getByCityWithJPQL(city); //city는 pk도 uk도 아니니 여러개가 올 수 있으니 List로받자

        //then
        assertEquals("춘식이", students.get(0).getName());

        System.out.println("\n\n\n");

        System.out.println("student = " + students.get(0));

        System.out.println("\n\n\n");
    }




    @Test
    @DisplayName("testSearchNameJPQL")
    void testSearchNameJPQL(){

        //given
        String name = "이";
        //when
        List<Student> students = studentRepository.searchByNamesWithJPQL(name); //city는 pk도 uk도 아니니 여러개가 올 수 있으니 List로받자

        //then
        assertEquals(3, students.size());

        System.out.println("\n\n\n");
        students.forEach(System.out::println);
        System.out.println("\n\n\n");
    }



    //JPQL로 삭제하기를 해보자.
    @Test
    @DisplayName("JPQL로 삭제하기")
    void testDeleteWithJPQL() {
        //given
        String name = "대길이";
        //when
        studentRepository.deleteByNameWithJPQL(name);
        //then
        List<Student> students = studentRepository.findByName(name);
        assertEquals(0, students.size());
    }








}


