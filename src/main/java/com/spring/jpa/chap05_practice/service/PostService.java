package com.spring.jpa.chap05_practice.service;

import com.spring.jpa.chap05_practice.dto.*;
import com.spring.jpa.chap05_practice.entity.HashTag;
import com.spring.jpa.chap05_practice.entity.Post;
import com.spring.jpa.chap05_practice.repository.HashTagRepository;
import com.spring.jpa.chap05_practice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor //이거써야 final로 선언가능. final변수로 선언되어있다는건, post서비스 객체가 생성될때마다 무조건 얘네를 받아야한다는 것이다.
@Transactional //JPA 레파지토리는 트랜잭션 단위로 동작하기 때문에 작성해야함! 안붙이면 SQL이 동작안할 수 있다.
public class PostService {

    //JPA레파지토리를 구현하는 애들이 여기에 주입되어야 서비스가 레파지토리를 부르겠지.
    //@AutoWired를 일일이 붙여도 되지만, final붙여도됨.
    private final PostRepository postRepository;
    private final HashTagRepository hashTagRepository;


    public PostListResponseDTO getPosts(PageDTO dto) { //컨트롤러가 PageDTO를 넘겨준다! 우리는 게시글 전체가 아니라 PageDTO를 JPA에 전달하고 그 전달한 거에 맞는 글 목록만 받아오고 싶다. 그래서 페이저블 객체를 전달하기 위해 페이저블 객체를 생성한다. -> DB 게시물 목록 조회에서 함.
        //getPosts메서드는 먼저, DB에서 게시물 목록을 조회해와야한다.
        //위에 있는 postRepository를 이용하면 되겠지.
        //그러나 다 가지고 오면 안되고, 페이징을 해서 가져와야한다.
        //누굴 생성해서 전달해야겠지.
        //Pageable 객체를 생성해줘야겠지.

        //Pageable 객체 생성
        Pageable pageable = PageRequest.of(
                dto.getPage() - 1,
                dto.getSize(), //한 화면에서의 게시물의 개수
                Sort.by("createDate").descending() //정렬
        );

            //DB에서 게시물 목록 조회
            Page<Post> posts = postRepository.findAll(pageable); //findall을 부르면서 페이지 정보를 전달하고, jpa가 우리에게 page파일의 객체를 리턴해준다.


            //게시물 정보만 꺼내기 -> 게시물 정보는 위의 페이지 객체인 posts에서 posts.getContent하면 페이지 게시물 정보를 꺼낼 수 있음. 근데 그냥 줄 수 없으니, <Post>타입을 <PostDetailResponseDTO>로 변환해야 하기 위해, 생성자를 하나 선언해야한다.
            List<Post> postList = posts.getContent(); //조회한 내용을 꺼낼 수 있음. 그러나 <Post>는 엔터티타입이다. List<Post>를 아래 .posts()에 전달해야 하니, <Post>는 엔터티타입이 아니라 <PostDetailREsponseDTO>타입이다. 저걸로 바꿔줘야한다.

            //바꿔주자. 이걸 생성자에서 처리할것이다. PostDetailREsponseDTO로가자. 차곡차곡 적어주되, 해쉬태그는 map과 stream을 이용해서 적어주고 다시 와서 여기 적어주자.
            List<PostDetailResponseDTO> detailList = postList.stream()
                    .map(post -> new PostDetailResponseDTO(post))
                    .collect(Collectors.toList());
            //즉, 엔터티 객체를 DTO 객체로 변환한 결과의 게시물 리스트다. 이제 아래 posts에다가 detailList를 넣어도됨.







            //DB에서 조회한 정보(엔터티)를 (지금 위 <Post>는 엔터티다) JSON 형태에 맞는 DTO로 변환하자. (변.완)
                     return PostListResponseDTO.builder()
                    .count(detailList.size()) //총 게시물 수가 아니라 우리가 pageable주면서 가지고 오는 정보이기 때문에, 페이징 조건에 맞게 조회된 게시물이다.
                    .pageInfo(new PageResponseDTO(posts)) //생성자에게 Page정보가 담긴 객체를 그대로 주자. 즉, 우리가 위에서 얻어왔던 Page<Post> posts를 넣자. 알아서 뽀개서 써라! 빨간불이니 생성자하나뽑아야겠지. PostDetailResponseDTO.java가보면 마지막에 생성자 하나 생성됐다.
                    .posts(detailList)
                    .build();


        }

        //개별조회(1개)
    public PostDetailResponseDTO getDetail(long id) {
      //  Post postEntity = postRepository.findById(id)   //findById는 리턴타입이 옵셔널이다. 옵셔널은 널체크다. 글번호로 줬는데 만약 널이왔다면? orElseThrow쓰자 -> 옵셔널로안받고 Post로받았다.
         //       .orElseThrow(
                        //만약 findById하면서 id줬는데 널이왔다면, id가잘못된거겠지. 조회가안되니까.
                     //   () -> new RuntimeException(id + "번 게시물이 존재하지 않습니다.")
               // );


        Post postEntity = getPost(id);

        return new PostDetailResponseDTO(postEntity);//PostDetailREsponseDTO가 생성자 선언해놨으니 알아서 변환해준다. 엔터티타입을 줬으니 dto로 변환해주겠지~
    }


    private Post getPost(long id) {
        Post postEntity = postRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException(id + "번 게시물이 존재하지 않습니다.")
                );
        return postEntity;
    }



    //게시물 저장 (해시태그는 저장을 따로해야함)
    public PostDetailResponseDTO insert(final PostCreateDTO dto) throws RuntimeException { //final선언하면 값못바꿈. 서비스가 못건들고 바로 값때려넣게. 값을 뽑을 순 있지만 값변경은못함.


        //일단 때려넣어야되니 save -> save매개값으로 dto인데 엔터티로바꾸는 작업이 필요하다. -> 작업해주고 와서 save()에 작성해주자.
        Post saved = postRepository.save(dto.toEntity());//변환하면 엔터티 객체로 리턴이 된다!
        //saved에는 해시태그없음.


        //해시태그 저장
        List<String> hashTags = dto.getHashTags();

        //게시글 인서트가 진행되는데, 해시태그를 안썼을수도있잖아. 안썼으면 굳이 저장할필요가없지
        if(hashTags != null && hashTags.size() > 0 ) { //이러면 해시태그가 존재하니까
            hashTags.forEach(ht -> { //문자열이 ht로 가고, 받을 때마다 해쉬태그생성.
                HashTag savedTag = hashTagRepository.save( //이 save()는 해쉬태그 엔터티를 받겠지.
                HashTag.builder()
                        .tagName(ht)
                        .post(saved) //포스트는 우리가 미리 준비해논, 위에 //게시물 저장  의 postRepository.save(dto.toEntity)가서 지역변수삽입하자. 그럼 saved로 바꿨다.
                        .build()

                );
                //Post Entity는 DB에 save를 진행할 때, HashTag에 대한 내용을 갱신하지 않는다.
                //HashTag Entity는 따로 save를 진행한다.
                //HasgTag는 연관관계의 주인이기 때문에 save를 진행할 때, 위 .post(saved) 즉, post가 같이 전달하기에
                //DB와 entity와의 상태가 동일하지만, Post는 HashTag의 정보가 비어있는 상태이기 때문에
                //Post entity에 상태를 맞춰주는(동기화)를 할 수 있는 연관관계 편의메서드를 선언해야한다.
                //해야 추후에 진행되는 과정에서 문자 발생X (연관관계의 주인이 아니기 때문에 실시간으로 반영이 안되는 것이다. 해쉬태그가)
                saved.addHashTag(savedTag); //이 saved는 위에있는 Post Saved(//게시물 저장) 이다.

            });
        }
        return new PostDetailResponseDTO(saved); //엔터티를 dto를 변환해서 리턴하겠다.
    }




    public PostDetailResponseDTO modify(PostModifyDTO dto) {
        //수정 전 데이터를 조회
        Post postEntity = getPost(dto.getPostNo());

        //수정 시작 -> jpa가 엔터티가 바뀌고 있구나라고 인식함. 그게 save된다면 update문 때리자.
        postEntity.setTitle(dto.getTitle());
        postEntity.setContent(dto.getContent());

        //수정 완료
        Post modifiedPost = postRepository.save(postEntity);

        return new PostDetailResponseDTO(modifiedPost);


    }


    //삭제 메서드
    public void delete(long id) {

        postRepository.deleteById(id);
    }





}