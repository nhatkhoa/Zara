// Copyright (c) 2015 KhoaHoang.
package vn.zara.web.dto;


import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import vn.zara.domain.common.validation.FieldMatch;
import vn.zara.domain.user.User;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@FieldMatch(firstField = "password", secondField = "confirmPassword")
@Data
@ToString
public class RegisterUserInfo {

    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "{validation.username.character}")
    @NotEmpty
    @Size(min = 6, max = 50, message = "{validation.username.size}")
    private String username;

    @NotEmpty
    @Size(min = 6, max = 50, message = "{validation.password.size}")
    private String password;

    @NotEmpty
    private String confirmPassword;


}
