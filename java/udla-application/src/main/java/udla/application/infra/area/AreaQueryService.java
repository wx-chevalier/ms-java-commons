package udla.application.infra.area;

import java.util.List;
import udla.domain.infra.area.Area;

public interface AreaQueryService {

  /** 通过父级code获取区域列表 */
  List<Area> getByParentCode(String code);
}
