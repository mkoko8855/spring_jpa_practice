package com.spring.jpa.chap05_practice.entity;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString(exclude = {"hashTags"}) //post를 제외해주겠다.
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_post")
public class Post {

    //게시글 번호
    @Id //PK
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_no") //객체는 id로 운영할꺼고 컬럼명은 post_no로 들어 갈 것이다.
    private long id;


    //작성자
    @Column(nullable = false) //널불가.
    private String writer;

    
    //제목
    @Column(nullable = false)
    private String title;



    //내용
    private String content;

    
    //작성시간
    @CreationTimestamp
    @Column(updatable = false) //한번 INSERT 되면 변경불가. 작성시간은 한번 들어가면 변경안됨.
    private LocalDateTime createDate; //어차피 @Column안써도 create_date로들어가긴함


    //수정시간
    @UpdateTimestamp //일단 시간이 들어가긴함. 수정안됐으면 null일 거 같지만, 일단 시간들어감..
    private LocalDateTime updateDate;

    
    //양방향 설정할거임. 여긴 기준을 보면 1이고 쟤가 N이니 원투매니.
    @OneToMany(mappedBy = "post" ) //맵드바이에는 해쉬태그 엔터티보면 Post post;라고 변수선언해줬으니, 저거적자.
    @Builder.Default //이 값을 우선시한다는 의미이다. 즉, 특정 필드를 지정한 값으로 초기화한다.(0620)
    private List<HashTag> hashTags = new ArrayList<>(); //널 방지를 위해 초기화.  조회 용도로사용한다!



    //양방향 매핑에서 리스트쪽에 데이터를 추가하는 편의 메서드 생성
    public void addHashTag(HashTag hashTag){
        //매개값으로 전달받은 hashTag에 add하겠다.
        hashTags.add(hashTag); //핵심.

        if(this != hashTag.getPost()){ //혹시모를상황을위해 작성. -> 해쉬태그 부른 이 객체가 해쉬태그가부른 getPost내용과 다르다면 맞춰주겠다.
            hashTag.setPost(this);
        }
    }


}
