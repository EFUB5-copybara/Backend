package efub.cpbr.crumble.shop.paper.controller;

import efub.cpbr.crumble.shop.paper.service.PaperService;
import efub.cpbr.crumble.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/use/papers")
@RequiredArgsConstructor
public class PaperUseController {

    private final PaperService paperService;

    @PostMapping("/{paperId}")
    public void applyFont(@AuthenticationPrincipal User user,
                          @PathVariable Long paperId) {
        paperService.applyPaper(user, paperId);
    }
}
