package cn.msjc.utils.ds;

import cn.hutool.core.util.NumberUtil;
import java.math.BigDecimal;
import java.util.Objects;

/** 对象工具类，包括判空、克隆、序列化等操作 */
public class ObjectUtils {

  /**
   * 比较两个对象是否相等，此方法是 {@link #equal(Object, Object)}的别名方法。<br>
   * 相同的条件有两个，满足其一即可：<br>
   *
   * <ol>
   *   <li>obj1 == null &amp;&amp; obj2 == null
   *   <li>obj1.equals(obj2)
   *   <li>如果是 BigDecimal 比较，0 == obj1.compareTo(obj2)
   * </ol>
   *
   * @param obj1 对象1
   * @param obj2 对象2
   * @return 是否相等
   * @see #equal(Object, Object)
   */
  public static boolean equals(Object obj1, Object obj2) {
    return equal(obj1, obj2);
  }

  /**
   * 比较两个对象是否相等。<br>
   * 相同的条件有两个，满足其一即可：<br>
   *
   * <ol>
   *   <li>obj1 == null &amp;&amp; obj2 == null
   *   <li>obj1.equals(obj2)
   *   <li>如果是BigDecimal比较，0 == obj1.compareTo(obj2)
   * </ol>
   *
   * @param obj1 对象1
   * @param obj2 对象2
   * @return 是否相等
   * @see Objects#equals(Object, Object)
   */
  public static boolean equal(Object obj1, Object obj2) {
    // 首先判断是否为 BigDecimal，如果是则进行模糊判断
    if (obj1 instanceof BigDecimal && obj2 instanceof BigDecimal) {
      return NumberUtil.equals((BigDecimal) obj1, (BigDecimal) obj2);
    }

    return Objects.equals(obj1, obj2);
  }
}
