package efub.cpbr.crumble.hint.entity;

import efub.cpbr.crumble.question.entity.Question;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(toBuilder = true)
public class Hint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="hint_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;
}
