package udla.domain.infra.area;

import lombok.Data;
import lombok.EqualsAndHashCode;
import udla.common.data.shared.id.AreaId;
import udla.domain.shared.IdBasedEntity;

@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Area extends IdBasedEntity<AreaId, Area> {

  private Integer level;

  private String parentCode;

  private String areaCode;

  private String name;

  private String shortName;

  private String mergerName;

  private String zipCode;

  private String cityCode;

  private String pinyin;

  private String lng;

  private String lat;
}
