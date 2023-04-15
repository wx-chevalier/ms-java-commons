package cn.msjc.utils.bean;

import java.time.LocalDateTime;

public interface HasTimeFields {

  LocalDateTime getCreatedAt();

  LocalDateTime getUpdatedAt();
}
