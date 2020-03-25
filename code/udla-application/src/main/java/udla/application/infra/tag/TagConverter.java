package udla.application.infra.tag;

import org.springframework.stereotype.Component;
import udla.common.data.common.AbstractConverter;
import udla.common.data.shared.id.*;
import udla.common.data.shared.id.TagId;
import udla.common.data.shared.id.UserId;
import udla.domain.infra.tag.Tag;
import udla.infra.tunnel.db.infra.tag.TagDO;

@Component
public class TagConverter extends AbstractConverter<Tag, TagDO> {

  @Override
  public TagDO convertTo(Tag tag) {
    return new TagDO()
        .setId(convertNullable(tag.getId(), TagId::getId))
        .setTenantId(tag.getTenantId().getId())
        .setTag(tag.getTag())
        .setCreatorId(tag.getCreatorId().getId())
        .setTagType(tag.getType());
  }

  @Override
  public Tag convertFrom(TagDO tagDO) {
    return new Tag(
        new TagId(tagDO.getId()),
        new TenantId(tagDO.getTenantId()),
        tagDO.getCreatedAt(),
        tagDO.getUpdatedAt(),
        tagDO.getTag(),
        new UserId(tagDO.getCreatorId()),
        tagDO.getTagType());
  }
}
