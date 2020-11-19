package com.unionfab.cloud.analytics.stat.mes.requires;

import com.unionfab.analytics.common.data.WorkOrderId;

public interface StatMesAnalyticsService {

  void recordDailyWorkOrder(WorkOrderId workOrderId, int count);
}
