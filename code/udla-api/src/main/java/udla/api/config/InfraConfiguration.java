package udla.api.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import udla.api.config.properties.*;
import udla.infra.service.aliyun.AliOssSetting;
import udla.infra.service.dingtalk.DingTalkSetting;
import udla.infra.service.email.EmailSetting;
import udla.infra.service.kv.TimeSeriesSetting;
import udla.infra.service.localfilestore.LocalFileStoreSetting;
import udla.infra.service.sms.AliSmsSetting;
import udla.infra.service.sms.SmsSetting;
import udla.infra.service.wechat.WechatSetting;

@Slf4j
@Configuration
public class InfraConfiguration {

  private ApplicationProperties applicationProperties;

  public InfraConfiguration(ApplicationProperties applicationProperties) {
    this.applicationProperties = applicationProperties;
  }

  @Bean
  public LocalFileStoreSetting localFileStoreSetting() {
    FileStoreProperties fileStoreProperties = applicationProperties.getFileStore();
    return new LocalFileStoreSetting(
        fileStoreProperties.getPath(), fileStoreProperties.getBaseUrl());
  }

  @Bean
  public AliOssSetting aliOssSetting() {
    OSSProperties ossProperties = applicationProperties.getOss();
    return new AliOssSetting(
        ossProperties.getAccessKeyId(),
        ossProperties.getAccessKeySecret(),
        ossProperties.getEndpoint(),
        ossProperties.getBucketName(),
        ossProperties.getExpiration(),
        ossProperties.getMaxSize(),
        ossProperties.getCallbackUrl());
  }

  @Bean
  public AliSmsSetting aliSmsSetting() {
    SMSProperties sms = applicationProperties.getSms();
    return new AliSmsSetting(
        sms.getRegionId(), sms.getSignName(), sms.getAccessKeyId(), sms.getAccessKeySecret());
  }

  @Bean
  @ConditionalOnProperty(prefix = "application", name = "ding-talk.access-token")
  public DingTalkSetting dingTalkSetting() {
    DingTalkProperties dingTalk = applicationProperties.getDingTalk();
    log.info("Creating DingTalkSetting: {}", dingTalk);
    return new DingTalkSetting(dingTalk.getAccessToken());
  }

  @Bean
  @ConditionalOnProperty(prefix = "application", name = "wechat.token")
  public WechatSetting wechatSetting() {
    WechatProperties wechatProperties = applicationProperties.getWechat();
    log.info("Creating wechatProperties: {}", wechatProperties);
    return new WechatSetting()
        .setToken(wechatProperties.getToken())
        .setAppSecret(wechatProperties.getAppSecret())
        .setAppID(wechatProperties.getAppID());
  }

  @Bean
  public SmsSetting smsSetting() {
    return new SmsSetting();
  }

  @Bean
  public TimeSeriesSetting timeSeriesSetting() {
    return new TimeSeriesSetting(applicationProperties.getTimeSeries().getMaxTsIntervals());
  }

  @Bean
  public EmailSetting emailSetting() {
    MailProperties mail = applicationProperties.getMail();
    return new EmailSetting(
        mail.getHost(), mail.getUsername(), mail.getPassword(), mail.getPort(), "WX");
  }
}
