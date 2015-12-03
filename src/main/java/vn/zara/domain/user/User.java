/**
 * Copyright @ 2015 by Khoa Khoa - khoahoang.sa@gmail.com
 * Created by Khoa Hoang on 23/11/2015 - 6:14 AM at ZaraApi.
 */

package vn.zara.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import vn.zara.domain.common.AbstractCollection;


@Document(collection = "users")
@Data
public class User extends AbstractCollection {
    protected static Logger Logger = LoggerFactory.getLogger(User.class);

    public enum Role {
        ADMIN,
        USER,
        ANONYMOUS;

        public String getAuthority() {
            return "ROLE_" + name();
        }
    }

    @Indexed
    private String username;

    @JsonIgnore
    private String password;

    private Role role;


}
