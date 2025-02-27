import hexlet.code.SearchEngine;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.*;

class SearchEngineTest {
        String doc1 = "I can't shoot straight unless I've had a pint! Pour me!";
        String doc2 = "Don't shoot shoot shoot that thing at me.";
        String doc3 = "I'm your shooter. It's me.";
        String doc4 = "shoot me, shoot me!";

        List<Map<String, String>> docs = List.of(
                Map.of("id", "doc1", "text", doc1),
                Map.of("id", "doc2", "text", doc2),
                Map.of("id", "doc3", "text", doc3),
                Map.of("id", "doc4", "text", doc4)
        );

        @Test
        void searchOneWordTest() {
            List<String> result = SearchEngine.search(docs, "pint");
            List<String> correct = List.of("doc1");

            assertThat(result).isEqualTo(correct);
        }

        @Test
        void searchMultipleWordsTest() {
            List<String> result = SearchEngine.search(docs, "shoot me");
            List<String> correct = List.of("doc4", "doc2", "doc1", "doc3");

            assertThat(result).isEqualTo(correct);
        }

        @Test
        void searchWithEmptyTest() {
            List<String> result1 = SearchEngine.search(new ArrayList<>(), "shoot");
            List<String> result2 = SearchEngine.search(docs, "");
            List<String> result3 = SearchEngine.search(docs, "qwerty");

            assertThat(result1).isEmpty();
            assertThat(result2).isEmpty();
            assertThat(result3).isEmpty();
        }

        @Test
        void searchWithRelevanceOrder() {
            List<String> result = SearchEngine.search(docs, "me");
            List<String> correct = List.of("doc4", "doc3", "doc2", "doc1");

            assertThat(result).isEqualTo(correct);
        }
}
