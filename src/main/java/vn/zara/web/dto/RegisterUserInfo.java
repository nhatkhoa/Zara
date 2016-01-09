// Copyright (c) 2015 KhoaHoang.
package vn.zara.web.dto;


import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;
import vn.zara.domain.common.validation.FieldMatch;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@FieldMatch(firstField = "password", secondField = "confirmPassword")
@Data
@ToString
public class RegisterUserInfo {

    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "Username must be not contains sensitive characters")
    @NotEmpty
    @Size(min = 6, max = 50, message = "The length of username must be between 6 and 50")
    private String username;

    @NotEmpty
    @Size(min = 6, max = 50, message = "The length of password must be between 6 and 50")
    private String password;

    @NotEmpty
    private String confirmPassword;

    private boolean boy;


}
