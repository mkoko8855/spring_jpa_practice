package com.spring.jpa.chap02_querymethod.repository;

import com.spring.jpa.chap02_querymethod.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, String> { //uuid니까 String이겠지.

    List<Student> findByName(String name);

    //이번에는 다른걸로 조회해보자. 이번엔 조건 2개걸고싶다.
    List<Student> findByCityAndMajor(String city, String major); //카멜케이스 정확히 적어야한다. findByCityAndMajor로. And조건은 city=? and major =? 과 같다.


    //LIKE절도 종류가 여러개다.
    List<Student> findByMajorContaining(String major); // WHERE major LIKE '%major%' 이렇게 들어간다. 그럼 만약 '%major'는 무슨뜻? major로 끝난다면? 이라는 뜻이다. 뒤에붙는건 'major%' (메이저로 시작한다면)이다. 뒤에붙는건 findByMajorStartingWith 이라고 주면 된다. major로 끝난다면 findByMajorEndingWith 이다.


    //근데 쿼리와 조건이 많아서 질린다면, 쌩 쿼리를 날릴 수도 있다. (네이티브 쿼리 사용) -> 근데 복잡하게, 쌩 쿼리도 쉽게 해줄 수 있음 -> 나중에 배움.
    @Query(value = "SELECT * FROM tbl_student WHERE stu_name = :nm", nativeQuery = true)
    //아래 매개변수에 맞게 name이라고 적어야겠다? 꼭 그런건 아니다. 여기 name을 nm으로 써주고, 아래 매개변수에 @Param("nm")이라고 적어주면된다. 그리고 nativeQuery=true라고 얘기해줘야 한다. 이건 '내가쓰겠다' 라고 말하는것과같음.
    Student findNameWithSQL(@Param("nm") String name);



    /*
    네이티브 쿼리보다 조인 쿼리문에서 JPQL이 쉽다.

    JPQL은 엔터티클래스명 기준으로 쿼리작성하는데,

    SELECT 별칭 FROM 엔터티클래스명 AS 별칭
    WHERE 별칭.필드명 = ? 이렇게 쓴다.

    즉,

    원래 native-sql은
    SELECT * FROM tbl_student WHERE stu_name = ? 으로하는데,
    JPQL은
    SELECT st FROM Student AS st WEHRE st.name = ? 이렇다.
     */


    //JPQL 사용해보자
    //도시이름으로 학생을 조회해보자
    @Query("SELECT s FROM Student s WHERE s.city = ?1")
    //city는 컬럼명이 아니다. 지금은 컬럼명도 city긴한데, 결국 String city의 city이다. 그리고 ?1이라고 적었는데, ?는 우리가 알고있는 물음표가 맞고 1는 첫번째 자리라는 뜻이다.
    List<Student> getByCityWithJPQL(String city);


    //이번엔 이름에 특정글자가 들어가면 조회해라 를 보자(다시말하지만, FROM Student에서 스튜던트는 테이블 명이 아니다.)
    @Query("SELECT st FROM Student st WHERE st.name LIKE %:nm%")
    //물음표 싫어서 네이티브에서 썼던 Param을 써보자.
    List<Student> searchByNamesWithJPQL(@Param("nm") String name);





    //이번엔 JPQL로 수정, 삭제 쿼리 써보자. 굳이 써보자는 것이다.
    //메서드 선언할 필요 없이 JPA에서 제공하는 메서드가 있다
    //studentRepository.deleteById 이런 메서드를 준다.

    //그러나 조회가 아닌 경우에는 Modifying을 추가해야줘야됨
    @Modifying //조회가 아닌 경우에는 무조건 붙여줘야함. 즉, 인서트 업데이트 딜리트에는 무조건 붙여주자.
    @Query("DELETE FROM Student s WHERE s.name = ?1")
    void deleteByNameWithJPQL(String name);




}