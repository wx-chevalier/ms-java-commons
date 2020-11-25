package com.msjc.cloud.analytics.mes.model;

import com.msjc.analytics.common.data.WorkOrderId;

public interface MesAnalyticsService {
  void recordDailyWorkOrder(WorkOrderId workOrderId, int count);
}
