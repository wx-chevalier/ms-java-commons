package cn.msjc.utils.lang.clone;

import cn.hutool.core.clone.CloneRuntimeException;

/**
 * 克隆支持类，提供默认的克隆方法
 *
 * @param <T>
 */
public class AutoCloneable<T> implements Cloneable<T> {

  @SuppressWarnings("unchecked")
  @Override
  public T clone() {
    try {
      return (T) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new CloneRuntimeException(e);
    }
  }
}
