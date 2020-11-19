package com.unionfab.cloud.analytics.stat.mes.service;

import com.unionfab.analytics.common.data.WorkOrderId;
import com.unionfab.cloud.analytics.stat.mes.infrastructure.dmr.WorkOrderMapper;
import com.unionfab.cloud.analytics.stat.mes.requires.StatMesAnalyticsService;
import java.time.LocalDate;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkOrderStatServiceImpl implements WorkOrderStatService {

  final StatMesAnalyticsService analyticService;
  final WorkOrderMapper workOrderMapper;

  @Override
  public void statDaily(LocalDate date) {
    log.info("stat work order daily: {}", date);

    // .... 统计

    // 记录到分析模块
    analyticService.recordDailyWorkOrder(
        // mocked data
        new WorkOrderId(UUID.randomUUID()), 10);
  }
}
