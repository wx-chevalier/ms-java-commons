package cn.msjc.utils.misc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.common.collect.Sets;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class BitToolsTest {

  @Test
  void testBitString() {
    String[] strs = new String[] {"ABCD", "CDEF", "MMMM", "XXXX", "EE"};
    HashSet<String> s1 = Sets.newHashSet("XXXX", "EE");
    String bitS = BitTools.convertToBitString(s1, strs);
    System.out.println(bitS);
    Set<String> s2 = BitTools.convertFromBitString(bitS, strs);
    assertEquals(s1, s2);
  }
}
