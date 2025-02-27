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

//     @Test
//    void searchMultipleWordsTest() {
//                List<String> result = SearchEngine.search(DOCS, "shoot me");
//                List<String> correct = List.of("doc4", "doc2", "doc1", "doc3");
//
//                assertThat(result).isEqualTo(correct);
//        }
//
//    @Test
//    void searchWithEmptyTest() {
//                List<String> result1 = SearchEngine.search(new ArrayList<>(), "shoot");
//                List<String> result2 = SearchEngine.search(DOCS, "");
//                List<String> result3 = SearchEngine.search(DOCS, "qwerty");
//
//                assertThat(result1).isEmpty();
//                assertThat(result2).isEmpty();
//                assertThat(result3).isEmpty();
//        }
//
//     @Test
//     void searchWithRelevanceOrder() {
//                List<String> result = SearchEngine.search(DOCS, "me");
//                List<String> correct = List.of("doc4", "doc3", "doc2", "doc1");
//
//                assertThat(result).isEqualTo(correct);
//        }
}