package efub.cpbr.crumble.grammar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class GrammarCheckRedisService {
    private final RedisTemplate<String, Boolean> redisTemplate;

    public void saveDailyGrammarCheck(Long userId){
        String redisKey = "daily_grammar_check_" + userId;
        redisTemplate.opsForValue().set(redisKey, true, Duration.ofSeconds(getSecondsUntilMidnight()));
    }

    // 자정까지 남은 시간 계산
    private long getSecondsUntilMidnight() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime midnight = now.toLocalDate().plusDays(1).atStartOfDay();
        return Duration.between(now, midnight).getSeconds();
    }

    public boolean isCheckedToday(Long userId) {
        String redisKey = "daily_grammar_check_" + userId;
        Boolean checked = redisTemplate.opsForValue().get(redisKey);
        return checked != null && checked;
    }
}
