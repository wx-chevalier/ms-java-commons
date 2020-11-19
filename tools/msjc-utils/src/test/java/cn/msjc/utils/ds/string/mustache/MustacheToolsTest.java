package cn.msjc.utils.ds.string.mustache;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.vavr.API;
import org.junit.jupiter.api.Test;

class MustacheToolsTest {

  @Test
  void test_mustache_template_file() {
    String res =
        MustacheTools.mustacheTemplateFile(
            "mustache/t1.mustache",
            API.Map("name", "unionfab", "description", "Industrial Internet of things platform"));
    assertEquals("unionfab, Industrial Internet of things platform", res);
  }

  @Test
  void test_mustache_template() {
    String res =
        MustacheTools.mustache(
            "{{name}}, {{description}}",
            API.Map("name", "unionfab", "description", "Industrial Internet of things platform"));
    assertEquals("unionfab, Industrial Internet of things platform", res);
  }

  @Test
  void test_mustache_lack_of_value() {
    String res = MustacheTools.mustache("{{name}}, {{description}}", API.Map("name", "unionfab"));
    assertEquals("unionfab, ", res);
  }
}
