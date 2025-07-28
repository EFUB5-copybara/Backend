package efub.cpbr.crumble.shop.font.controller;

import efub.cpbr.crumble.shop.font.service.FontService;
import efub.cpbr.crumble.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/use/fonts")
@RequiredArgsConstructor
public class FontUseController {

    private final FontService fontService;

    @PostMapping("/{fontId}")
    public void applyFont(@AuthenticationPrincipal User user,
                          @PathVariable Long fontId) {
        fontService.applyFont(user.getUserId(), fontId);
    }
}
