package efub.cpbr.crumble.community.comment.service;

import efub.cpbr.crumble.community.comment.domain.Comment;
import efub.cpbr.crumble.community.comment.dto.request.CommentCreateRequestDto;
import efub.cpbr.crumble.community.comment.dto.response.CommentResponseDto;
import efub.cpbr.crumble.community.comment.repository.CommentRepository;
import efub.cpbr.crumble.community.post.domain.Post;
import efub.cpbr.crumble.community.post.repository.PostRepository;
import efub.cpbr.crumble.user.entity.User;
import efub.cpbr.crumble.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 댓글 생성
    @Transactional
    public CommentResponseDto createComment(Long postId, CommentCreateRequestDto requestDto, Long  commentatorId) {
        User commentator = userRepository.findById(commentatorId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        Comment newComment = requestDto.toEntity(commentator, post);
        commentRepository.save(newComment);

        return CommentResponseDto.from(newComment);
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

}
