package udla.infra.tunnel.db.infra.kv;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import lombok.NonNull;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import udla.common.data.shared.EntityType;
import udla.common.data.shared.id.EntityId;
import udla.infra.converter.PageConverter;
import udla.infra.tunnel.db.Helper;
import udla.infra.tunnel.db.mapper.infra.kv.TsKvMapper;

@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TsKvTunnel extends ServiceImpl<TsKvMapper, TsKvDO> {

  public boolean exists(EntityId entityId, String key, Long ts) {
    return count(
            new LambdaQueryWrapper<TsKvDO>()
                .eq(TsKvDO::getEntityType, entityId.getEntityType().name())
                .eq(TsKvDO::getEntityId, entityId.getId())
                .eq(TsKvDO::getKey, key)
                .eq(TsKvDO::getTs, ts))
        != 0;
  }

  public Page<TsKvDO> listWithLimit(
      @NonNull Long entityId,
      @NonNull EntityType entityType,
      @NonNull List<String> keys,
      Long startTs,
      Long endTs,
      Pageable pageable) {
    return PageConverter.toPage(
        page(
            PageConverter.toIPage(pageable),
            Helper.tsKvQ()
                .eq(TsKvDO::getEntityId, entityId)
                .eq(TsKvDO::getEntityType, entityType)
                .in(!CollectionUtils.isEmpty(keys), TsKvDO::getKey, keys)
                .gt(startTs != null, TsKvDO::getTs, startTs)
                .le(endTs != null, TsKvDO::getTs, endTs)),
        pageable);
  }

  @Transactional
  public void delete(
      @NonNull Long entityId,
      @NonNull EntityType entityType,
      @NonNull List<String> keys,
      Long startTs,
      Long endTs) {
    remove(
        Helper.tsKvQ()
            .eq(TsKvDO::getEntityId, entityId)
            .eq(TsKvDO::getEntityType, entityType)
            .in(!CollectionUtils.isEmpty(keys), TsKvDO::getKey, keys)
            .gt(startTs != null, TsKvDO::getTs, startTs)
            .le(endTs != null, TsKvDO::getTs, endTs));
  }
}
