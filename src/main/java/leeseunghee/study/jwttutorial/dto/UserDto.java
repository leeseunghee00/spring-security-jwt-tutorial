package leeseunghee.study.jwttutorial.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserDto (
        @NotNull
        @Size(min = 3, max = 50)
        String username,

        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @NotNull
        @Size(min = 3, max = 100)
        String password,

        @NotNull
        @Size(min = 3, max = 50)
        String nickname
) {
}
