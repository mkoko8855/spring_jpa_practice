package com.spring.jpa.chap05_practice.dto;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
//@NoArgsConstructor 매개값을 받지 않는 생성자는 직접쓸꺼라 이건 안쓴다.
@AllArgsConstructor
@Builder
public class PageDTO {  //얜 테이블 생성하는 엔터티가 아니다!!

    private int page;
    private int size;


    //생성자를 직접 뽑아보자(art + insert)
    public PageDTO() { //기본값 설정 해주려고 만들었음
        this.page = 1;
        this.size = 10;

    }
}
