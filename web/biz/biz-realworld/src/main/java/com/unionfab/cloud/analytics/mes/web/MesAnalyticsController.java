package com.msjc.cloud.analytics.mes.web;

import com.msjc.analytics.common.data.SecurityUser;
import com.msjc.analytics.common.spring.SecurityContextHelper;
import com.msjc.cloud.analytics.mes.requires.MesAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class MesAnalyticsController {

  final MesAccountService accountService;

  @GetMapping("/hello")

  public String hello() {
    SecurityUser u =
        SecurityContextHelper.getSecurityUser()
            .getOrElseThrow(() -> new BadCredentialsException("bad credentials"));
    return "hello " + u;
  }
}
