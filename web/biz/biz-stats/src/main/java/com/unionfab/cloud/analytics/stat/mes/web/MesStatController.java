package com.unionfab.cloud.analytics.stat.mes.web;

import com.unionfab.cloud.analytics.stat.mes.service.WorkOrderStatService;
import io.swagger.annotations.ApiOperation;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class MesStatController {

  final WorkOrderStatService workOrderStatService;

  @ApiOperation("触发当日工单统计")
  @PostMapping("/stat/work_order")
  public void statWorkOrder() {
    workOrderStatService.statDaily(LocalDate.now());
  }
}
