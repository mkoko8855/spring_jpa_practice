package com.spring.jpa.chap05_practice.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Page;

@Setter
@Getter
@ToString
public class PageResponseDTO<T> {

    private int startPage;

    private int endPage;

    private int currentPage;

    private boolean prev;

    private boolean next;

    private int totalCount; //총게시물


    //한 페이지에 배치할 페이지 버튼 수(1~10 // 11~20)
    private static final int PAGE_COUNT = 10;



    //제네릭 타입을 <T>로 선언하면, 전달되는 객체의 제네릭 타입에 따라 T가 결정이 된다 -> 클래스에도 제네릭 타입 T를 선언해야 한다.
    public PageResponseDTO(Page<T> pageData) { //Page타입의 객체를 받는다. (이 생성자는 Postservice에 의해 생성자로 생성했음).

        //startPage, endPage, currentPage, prev 등등을 쓰자
        this.totalCount = (int)pageData.getTotalElements(); //총게시물

        //현재페이지
        this.currentPage = pageData.getPageable().getPageNumber() + 1; //+1을 해줘야 원하는 현재 페이지의 결과가 나옴. 왜냐면 페이지 전달할 때, 페이저블로 전달했으니까.

        //끝페이지
        this.endPage = (int)Math.ceil((double) currentPage / PAGE_COUNT) * PAGE_COUNT;
        
        //시작페이지
        this.startPage = endPage - PAGE_COUNT + 1;

        
        
        
        //보정 부분
        int realEnd = pageData.getTotalPages(); //총페이지 수가 몇인지 알려준다. 얘가 있기에, 우리가 끝페이지 보정이 필요X. 378에서 getTotalPages부르면 38페이지로 바로 값나오겠지.
        
        if(realEnd < this.endPage){ //공식에 의해서 끝페이지 보정해보자 realEnd가 37이고 this.endPage가 40이면, 37이 realEnd페이지가 된다.
            this.endPage = realEnd; //끝페이지 보정.
        }
        this.prev = startPage > 1;     //시작페이지가 1보다 크면 이전버튼
        this.next = endPage < realEnd; //끝페이지가 진짜끝페이지보다 작으면 다음 버튼 있어야함.



    }





    //위 것들이 pageInfo가 된다.
}
