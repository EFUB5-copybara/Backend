package efub.cpbr.crumble.item.repository;

import efub.cpbr.crumble.answer.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FortuneRepository extends JpaRepository<Answer, Long>{
    //user의 모든 답변을 조회
    //List<Answer> findByUserId(Long userId);
    @Query("SELECT a FROM Answer a WHERE a.user.id = :userId")
    List<Answer> findByUserId(@Param("userId") Long userId);

}
