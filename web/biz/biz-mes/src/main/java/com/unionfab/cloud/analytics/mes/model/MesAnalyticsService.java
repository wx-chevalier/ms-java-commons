package com.unionfab.cloud.analytics.mes.model;

import com.unionfab.analytics.common.data.WorkOrderId;

public interface MesAnalyticsService {
  void recordDailyWorkOrder(WorkOrderId workOrderId, int count);
}
