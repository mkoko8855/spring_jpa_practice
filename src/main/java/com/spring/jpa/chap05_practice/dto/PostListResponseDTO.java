package com.spring.jpa.chap05_practice.dto;

import lombok.*;

import java.util.List;


@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostListResponseDTO {   //응답용도로 사용하는 DTO. PageDTO 와는 다르다. 이걸 통해 아래 3가지를 리턴할 것이다.

    private int count; //총 게시물 수 (완전 게시물 총 수 는 아닐거고, 페이징이 완료된 총 게시물 수가 되려나?)

    private PageResponseDTO pageInfo;   //페이지 렌더링 정보. Object로 선언하면 다 받을 수 있잖아. (next, 시작페이지, 끝페이지 등등의 정보가 있겠다. )

    //게시물이 모여있는 객체
    private List<PostDetailResponseDTO> posts; //게시물 렌더링 정보. 다받을 수 있게 Object. 그러나 게시물의 정보니 Object말고 디테일어쩌고..하고 클래스만들것임


}
