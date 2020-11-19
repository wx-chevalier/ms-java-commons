package com.unionfab.cloud.analytics.stat.mes.infrastructure.dmr;

import com.baomidou.mybatisplus.annotation.TableName;
import com.unionfab.analytics.common.data.TenantId;
import com.unionfab.analytics.common.data.WorkOrderId;
import lombok.Data;
import lombok.ToString;

@Data
@TableName("mes_work_order")
@ToString(callSuper = true)
public class WorkOrderDO {

  private TenantId tenantId;

  private WorkOrderId id;

  private String code;

  private String name;

  private String status;
}
