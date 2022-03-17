package com.psbc.rpa.context;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.psbc.rpa.model.RpaUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author dahua
 * @time 2022/3/7 15:19
 */
@Component
public class TokenContext {

    private Cache<String, RpaUser> users;

    public TokenContext(@Value("${rpa.token.expire}") String expire) {
        users = CacheBuilder.newBuilder()
                .expireAfterWrite(Long.parseLong(expire), TimeUnit.SECONDS)
                .build();
    }

    public void putUser(String token, RpaUser rpaUser) {
        users.put(token, rpaUser);
    }

    public RpaUser getUser(String token) {
        return users.getIfPresent(token);
    }

    public void removeUser(String token) {
        users.invalidate(token);
    }

    public long getSize() {
        return users.size();
    }
}
