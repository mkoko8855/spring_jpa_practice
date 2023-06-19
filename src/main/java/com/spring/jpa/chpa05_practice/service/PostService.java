package com.spring.jpa.chpa05_practice.service;

import com.spring.jpa.chpa05_practice.repository.HashTagRepository;
import com.spring.jpa.chpa05_practice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor //이거써야 final로 선언가능. final변수로 선언되어있다는건, post서비스 객체가 생성될때마다 무조건 얘네를 받아야한다는 것이다.
@Transactional //JPA 레파지토리는 트랜잭션 단위로 동작하기 때문에 작성해야함! 안붙이면 SQL이 동작안할 수 있다.
public class PostService {

    //JPA레파지토리를 구현하는 애들이 여기에 주입되어야 서비스가 레파지토리를 부르겠지.
    //@AutoWired를 일일이 붙여도 되지만, final붙여도됨.
    private final PostRepository postRepository;
    private final HashTagRepository hashTagRepository;


}
