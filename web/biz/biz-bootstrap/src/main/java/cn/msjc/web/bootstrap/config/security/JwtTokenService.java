package cn.msjc.web.bootstrap.config.security;

import com.msjc.analytics.common.data.SecurityUser;

public interface JwtTokenService {

  SecurityUser parseBearerToken(String token);
}
