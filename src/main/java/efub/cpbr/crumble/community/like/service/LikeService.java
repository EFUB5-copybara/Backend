package efub.cpbr.crumble.community.like.service;

import efub.cpbr.crumble.community.comment.domain.Comment;
import efub.cpbr.crumble.community.like.domain.Like;
import efub.cpbr.crumble.community.like.dto.response.LikeResponseDto;
import efub.cpbr.crumble.community.like.repository.LikeRepository;
import efub.cpbr.crumble.community.post.domain.Post;
import efub.cpbr.crumble.community.post.repository.PostRepository;
import efub.cpbr.crumble.global.exception.CustomException;
import efub.cpbr.crumble.global.exception.ErrorCode;
import efub.cpbr.crumble.user.entity.User;
import efub.cpbr.crumble.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 좋아요 생성
    @Transactional
    public LikeResponseDto createLike(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if(likeRepository.existsByPostAndLiker(post, user)){
            throw new CustomException(ErrorCode.ALREADY_LIKED);
        }

        Like newLike = new Like(post, user);
        likeRepository.save(newLike);

        post.updateLikeCount();
        postRepository.save(post);

        return LikeResponseDto.from(newLike);
    }

    // 좋아요 삭제
    @Transactional
    public void deleteLike(Long likeId) {
        Like like = likeRepository.findById(likeId)
                        .orElseThrow(() -> new CustomException(ErrorCode.LIKE_NOT_FOUND));

        Post post = postRepository.findById(like.getPost().getId())
                        .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        post.getLikeList().remove(like);
        likeRepository.delete(like);
        post.updateLikeCount();
        postRepository.save(post);
    }

}
