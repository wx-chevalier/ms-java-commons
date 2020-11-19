package cn.msjc.utils.bean.pageable;

import io.vavr.collection.List;
import java.util.function.Function;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;

@Data
@RequiredArgsConstructor
public class PageNumBasedPageData<T> {

  final List<T> data;
  final PageNumBasedPageLink pageLink;
  final Integer totalPage;
  final Long totalElements;

  @Nullable
  public PageNumBasedPageLink getNextPageLink() {
    if (pageLink != null && pageLink.getPageNum() < totalPage) {
      return pageLink.nextPage();
    } else {
      return null;
    }
  }

  /**
   * 未分页数据
   *
   * @param data 所有数据
   */
  public PageNumBasedPageData(List<T> data) {
    this.data = data;
    this.pageLink = new PageNumBasedPageLink(0, data.size());
    this.totalPage = 1;
    this.totalElements = (long) data.size();
  }

  public <U> PageNumBasedPageData<U> map(Function<T, U> mapper) {
    return new PageNumBasedPageData<>(data.map(mapper), pageLink, totalPage, totalElements);
  }
}
