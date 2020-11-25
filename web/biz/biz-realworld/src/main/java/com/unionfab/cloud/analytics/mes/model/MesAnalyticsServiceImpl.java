package com.msjc.cloud.analytics.mes.model;

import com.msjc.analytics.common.data.WorkOrderId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MesAnalyticsServiceImpl implements MesAnalyticsService {

  @Override
  public void recordDailyWorkOrder(WorkOrderId workOrderId, int count) {
    log.info("recording {}: {}", workOrderId, count);
  }
}
