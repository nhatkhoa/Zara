
package vn.zara.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.zara.domain.user.User;
import vn.zara.domain.user.UserService;
import vn.zara.domain.util.SecurityUtil;
import vn.zara.infras.security.xauth.Token;
import vn.zara.infras.security.xauth.TokenProvider;
import vn.zara.web.dto.RegisterUserInfo;
import vn.zara.web.dto.UserDetail;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api")
public class AccountRest {
    private static Logger logger = LoggerFactory.getLogger(AccountRest.class);

    private final UserService userService;

    private final TokenProvider tokenProvider;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    @Autowired
    public AccountRest(UserService userService, TokenProvider tokenProvider,
                       AuthenticationManager authenticationManager,
                       UserDetailsService userDetailsService) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    @RequestMapping(value = "/register", method = POST)
    public void registerAccount(@Valid @RequestBody(required = true) RegisterUserInfo user) {
        logger.debug(user.toString());
        userService.registerUser(user.getUsername(), user.getPassword(), user.isBoy());
    }

    @RequestMapping(value = "/login", method = POST)
    public Token login(@RequestParam String username,
                       @RequestParam String password) {
        logger.debug(String.format("Login: %s - %s", username, password));
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                username, password);
        Authentication authentication = authenticationManager
                .authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails details = userDetailsService.loadUserByUsername(username);

        return tokenProvider.createToken(details);
    }

    /**
     * check if the user is authenticated, and return its login.
     */
    @RequestMapping(value = "/authenticate", method = GET)
    public String isAuthenticated() {
        return SecurityUtil.getCurrentLogin();
    }

    @RequestMapping(value = "/account", method = GET)
    public UserDetail getAccount() {
        return userService.getUserInfo();
    }

}
