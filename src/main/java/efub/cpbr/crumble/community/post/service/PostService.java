package efub.cpbr.crumble.community.post.service;

import efub.cpbr.crumble.community.post.domain.Post;
import efub.cpbr.crumble.community.post.dto.Response.PostListResponseDto;
import efub.cpbr.crumble.community.post.dto.Response.PostResponseDto;
import efub.cpbr.crumble.community.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    // 인기순 조회
    @Transactional(readOnly = true)
    public PostListResponseDto getPopularPosts() {
        List<Post> posts = postRepository.findPopularPosts();
        List<PostResponseDto> postResponseDtos = posts.stream()
                .map(PostResponseDto::from)
                .toList();
        return PostListResponseDto.from(postResponseDtos);
    }

    // 최신순 조회
    @Transactional(readOnly = true)
    public PostListResponseDto getNewPosts() {
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
        List<PostResponseDto> postResponseDtos = posts.stream()
                .map(PostResponseDto::from)
                .toList();
        return PostListResponseDto.from(postResponseDtos);
    }

    // 게시글 상세 조회
    @Transactional
    public PostResponseDto getPost(Long postId) {
        postRepository.updatePostViewCount(postId);
        //Post post = findByPostId(postId);
        //return PostResponseDto.from(post);
        Post post = postRepository.findById(postId).get();
        return PostResponseDto.from(post);
    }

    /*
    @Transactional(readOnly = true)
    public Post findByPostId(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(()-> new crumbleException(ExceptionCode.POST_NOT_FOUND));
    }*/
}
