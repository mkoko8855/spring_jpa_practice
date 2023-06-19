package com.spring.jpa.chap05_practice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.jpa.chap05_practice.entity.HashTag;
import com.spring.jpa.chap05_practice.entity.Post;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDetailResponseDTO { //게시물 상세보기 요청

    private String author; //작성자      포스트에는 writer, dto에는 author라고 작성되어있다. JSON으로 던질 때는 author라고 던져야함.

    private String title;

    private String content;

    private List<String> hashTags; //문자열

    //게시물 마다 시간도
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDateTime regDate; //연월일시분초 를 다 알수가있지만, regDate는 시분초는 필요가없고 연월일만 요청한다면 위에다 적자.


    //위 전부는 JSON으로 전부 변환된다. 전부 키값들로 들어갈 것..


    //생성자 선언(엔터티를 DTO로 변환하는 생성자 선언). 포스트서비스에서는 객체생성되면 바로바로 일할 수 있게.
    public PostDetailResponseDTO(Post post) {
        this.author = post.getWriter();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.regDate = post.getCreateDate();
        //this.hashTags = post.getHashTags(); //변환해서전달해줘야지? 즉, 가공해서 집어넣어야한다. 리스트 타입이 서로 다르니까..(포스트의 해쉬태그랑 dto의 해쉬태그가 서로 타입이 다름!)


    /*
        //변환하자.
        List<String list> = new ArrayList<>();
        for (HashTag ht : post.getHashTags()){
            list.add(ht.getTagName()); //객체를 하나씩 받아와서 객체 안에있는 태그네임을 하나씩 꺼내서 리스트에 담겠다.
        }
        this.hashTags = list; //this.hashTags는 스트링타입이다.
    }   그러나 위처럼 쓰면, 성능상 이슈가 발생할 수 있음.
    */

        //이렇게 쓰자
        this.hashTags = post.getHashTags()
                                .stream()
                                .map(HashTag::getTagName).collect(Collectors.toList());
        //즉, 지금 post.getHashTags는 list다. 해쉬태그 타입의 리스튼데, 거기에서 스트림 객체를 리턴받았다. (스트림은, 컬렉션 데이터를 함수선언 형식으로 처리할 수 있게 해주는 객체)
        //그 다음에, map이라는 함수를 선언해서, 해쉬태그라는 객체의 getTagName을 다 불러서, 부른 결과를 새로운 리스트에 담을 것이다. 그 리스트를 새로운 hashTags에 대입한다.(this.hashTags).


    }
}