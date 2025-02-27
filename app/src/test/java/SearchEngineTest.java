import hexlet.code.SearchEngine;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class SearchEngineTest {
  public static final String DOC1 = "I can't shoot straight unless I've had a pint! Pour me!";
  public static final String DOC2 = "Don't shoot shoot shoot that thing at me.";
  public static final String DOC3 = "I'm your shooter. It's me.";
  public static final String DOC4 = "shoot me, shoot me!";

  public static final List<Map<String, String>> DOCS = List.of(
    Map.of("id", "doc1", "text", DOC1),
    Map.of("id", "doc2", "text", DOC2),
    Map.of("id", "doc3", "text", DOC3),
    Map.of("id", "doc4", "text", DOC4)
  );

  @Test
  void searchOneWordTest() {
    List<String> result = SearchEngine.search(DOCS, "pint");
    List<String> correct = List.of("doc1");

    assertThat(result).isEqualTo(correct);
  }
}
