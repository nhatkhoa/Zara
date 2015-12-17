/**
 * Copyright @ 2015 by Khoa Khoa - khoahoang.sa@gmail.com
 * Created by Khoa Hoang on 23/11/2015 - 6:32 AM at ZaraApi.
 */

package vn.zara.domain.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotEnoughPokemon extends ZaraException {
    public NotEnoughPokemon(String messageKey, String... messageArgs) {
        super(messageKey, messageArgs);
    }

}
