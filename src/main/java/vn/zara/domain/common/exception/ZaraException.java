/**
 * Copyright @ 2015 by Khoa Khoa - khoahoang.sa@gmail.com
 * Created by Khoa Hoang on 23/11/2015 - 6:31 AM at ZaraApi.
 */

package vn.zara.domain.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;

import java.util.Locale;

public class ZaraException extends RuntimeException {
    protected static Logger Logger = LoggerFactory.getLogger(ZaraException.class);
    private final String messageKey;
    private final String[] messageArgs;
    private MessageSource messageSource;

    public ZaraException(String messageKey, String... messageArgs) {
        this.messageKey = messageKey;
        this.messageArgs = messageArgs;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public String[] getMessageArgs() {
        return messageArgs;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String getMessage() {
        if (messageSource != null) {
            return messageSource.getMessage(messageKey, messageArgs, Locale.getDefault());
        }

        return String.format("{%s}", messageKey);
    }
}
