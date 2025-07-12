package efub.cpbr.crumble.global.exception.dto;

public record ErrorDto(String errorCode,
                       String timestamp,
                       String message,
                       int status,
                       String path) {
}
