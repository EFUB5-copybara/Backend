package efub.cpbr.crumble.community.post.service;

import efub.cpbr.crumble.community.comment.domain.Comment;
import efub.cpbr.crumble.community.comment.repository.CommentRepository;
import efub.cpbr.crumble.community.post.domain.Post;
import efub.cpbr.crumble.community.post.dto.Response.PostCommentDto;
import efub.cpbr.crumble.community.post.dto.Response.PostListResponseDto;
import efub.cpbr.crumble.community.post.dto.Response.PostResponseDto;
import efub.cpbr.crumble.community.post.dto.Response.PostSummaryDto;
import efub.cpbr.crumble.community.post.repository.PostRepository;
import efub.cpbr.crumble.global.exception.CustomException;
import efub.cpbr.crumble.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    // 인기순 조회
    @Transactional(readOnly = true)
    public PostListResponseDto getPopularPosts() {
        List<Post> posts = postRepository.findPopularPosts();
        List<PostSummaryDto> postSummaryDtos = posts.stream()
                .map(PostSummaryDto::from)
                .toList();
        return PostListResponseDto.from(postSummaryDtos);
    }

    // 최신순 조회
    @Transactional(readOnly = true)
    public PostListResponseDto getNewPosts() {
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
        List<PostSummaryDto> postSummaryDtos = posts.stream()
                .map(PostSummaryDto::from)
                .toList();
        return PostListResponseDto.from(postSummaryDtos);
    }

    // 게시글 상세 조회
    @Transactional
    public PostResponseDto getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        post.increaseViewCount();
        List<Comment> comments = commentRepository.findAllByPostIdOrderByCreatedAt(postId);
        PostCommentDto postCommentDto = PostCommentDto.from(postId, comments);
        return PostResponseDto.from(post, postCommentDto);
    }
}
