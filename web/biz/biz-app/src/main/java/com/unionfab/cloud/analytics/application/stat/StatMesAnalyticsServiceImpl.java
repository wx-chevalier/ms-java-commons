package com.unionfab.cloud.analytics.application.stat;

import com.unionfab.analytics.common.data.WorkOrderId;
import com.unionfab.cloud.analytics.mes.model.MesAnalyticsService;
import com.unionfab.cloud.analytics.stat.mes.requires.StatMesAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatMesAnalyticsServiceImpl implements StatMesAnalyticsService {

  final MesAnalyticsService analyticsService;

  @Override
  public void recordDailyWorkOrder(WorkOrderId workOrderId, int count) {
    analyticsService.recordDailyWorkOrder(workOrderId, count);
  }
}
