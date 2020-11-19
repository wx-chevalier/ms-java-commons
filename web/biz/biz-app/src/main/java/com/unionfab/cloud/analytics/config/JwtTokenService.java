package com.unionfab.cloud.analytics.config;

import com.unionfab.analytics.common.data.SecurityUser;

public interface JwtTokenService {

  SecurityUser parseBearerToken(String token);
}
