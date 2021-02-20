package cn.msjc.utils.bean.pageable;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class PageNumBasedPageLink {

  // 0 页开始
  final int pageNum;

  // 每页大小
  final int pageSize;

  @JsonCreator
  public PageNumBasedPageLink nextPage() {
    return new PageNumBasedPageLink(getPageNum() + 1, getPageSize());
  }
}
