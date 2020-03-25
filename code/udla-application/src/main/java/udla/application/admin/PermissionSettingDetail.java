package udla.application.admin;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import udla.domain.admin.PermissionSetting;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PermissionSettingDetail extends PermissionSetting {

  private String nickname;

  PermissionSettingDetail(PermissionSetting permissionSetting, String nickname) {
    BeanUtils.copyProperties(permissionSetting, this);
    this.nickname = nickname;
  }
}
