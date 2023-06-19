package com.spring.jpa.chpa05_practice.api;


import com.spring.jpa.chpa05_practice.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //레스트방식만 사용할꺼다. 리액트니까. 서버는 view페이지를 만들어주지않는다. 화면단에서 다 할것!
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts") //컨트롤러는 요청을 받아야하니 저렇게 적어주자.
public class PostApiController {

    //규칙을 정하고 로직을 만들자
    //리소스 : 여기서 처리해야 할 자원은, 게시물이다. (게시물은 Post라는 이름)
     /*
        약속된 url요청에 따라 일관된 동작을 처리할 것이다.
        게시물 목록 조회 : /posts   ->  get방식으로 처리할 거다. posts라는 요청이 get으로 오면 목록조회이다.
        게시물 개별 조회 : /posts/{id} -> 글번호가 get방식으로 오면, 개별조회이다.
        게시물 등록 :     /posts -> post방식으로 오면 등록요청이다.
        게시물 수정 :     /posts/{id} -> put아니면 Patch로 오면 수정.
        게시물 삭제 :     /posts/{id} 가 오는데, delete방식으로 오면 삭제.

        즉, 최대한 REST방식으로 하자.
     */



    //서비스주입받을꺼잖아
    private final PostService postService; //final 싫으면 오토와이어드 달아도됨.





}
