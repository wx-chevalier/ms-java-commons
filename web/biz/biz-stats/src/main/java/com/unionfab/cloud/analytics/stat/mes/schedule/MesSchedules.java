package com.unionfab.cloud.analytics.stat.mes.schedule;

import com.unionfab.cloud.analytics.stat.mes.service.WorkOrderStatService;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MesSchedules {

  final WorkOrderStatService workOrderStatService;

  // 每天凌晨 5 点进行统计
  @Scheduled(cron = "0 0 5 * * ?")
  public void stateWorkOrder() {
    workOrderStatService.statDaily(LocalDate.now());
  }
}
