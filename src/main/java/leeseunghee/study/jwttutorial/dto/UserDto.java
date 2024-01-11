package leeseunghee.study.jwttutorial.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import leeseunghee.study.jwttutorial.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserDto {

        @NotNull
        @Size(min = 3, max = 50)
        private String username;

        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @NotNull
        @Size(min = 3, max = 100)
        private String password;

        @NotNull
        @Size(min = 3, max = 50)
        private String nickname;

        public UserDto(final User user) {
                this.username = user.getUsername();
                this.password = user.getPassword();
                this.nickname = user.getUsername();
        }
}
