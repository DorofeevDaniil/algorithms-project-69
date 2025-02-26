import hexlet.code.SearchEngine;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchTest {

    @Test
    void testSearch() {
        var doc1 = "I can't shoot straight unless I've had a pint!";
        var doc2 = "Don't shoot shoot shoot that thing at me.";
        var doc3 = "I'm your shooter.";

        List<Map<String, String>> docs = List.of(
                Map.of("id", "doc1", "text", doc1),
                Map.of("id", "doc2", "text", doc2),
                Map.of("id", "doc3", "text", doc3)
        );

        // поисковый движок проводит поиск
        List<String> result = SearchEngine.search(docs, "shoot");

        assertEquals(List.of("doc2", "doc1"), result);
        System.out.println("PASSED: testSearch_1     " + result); // => ["doc1", "doc2"]

        // Документы пусты
        result = SearchEngine.search(new ArrayList<>(), "shoot"); // []

        assertEquals(List.of(), result);
        System.out.println("PASSED: testSearch_2     " + result);
    }

    @Test
    void testWithMarks() {
        var doc1 = "I can't shoot straight unless I've had a pint!";

        List<Map<String, String>> docs = List.of(
                Map.of("id", "doc1", "text", doc1));

        List<String> result = SearchEngine.search(docs, "pint");
        assertEquals(List.of("doc1"), result);
        System.out.println("PASSED: testWithMarks_1     " + result);

        result = SearchEngine.search(docs, "pint!");
        assertEquals(List.of("doc1"), result);
        System.out.println("PASSED: testWithMarks_2     " + result);
    }

    @Test
    void testWithRelevance() {
        var doc1 = "I can't shoot straight unless I've had a pint!";
        var doc2 = "Don't shoot shoot shoot that thing at me.";
        var doc3 = "I'm your shooter.";

        List<Map<String, String>> docs = List.of(
                Map.of("id", "doc1", "text", doc1),
                Map.of("id", "doc2", "text", doc2),
                Map.of("id", "doc3", "text", doc3)
        );

        List<String> result = SearchEngine.search(docs, "shoot");
        assertEquals(List.of("doc2", "doc1"), result);
        System.out.println("PASSED: testWithRelevance_1     " + result);

        doc1 = "I can't ... mmmm noodleSoup ... I've had a pint!";
        doc2 = "noodleSoup, noodleSoup";
        doc3 = "Beetlejuice, Beetlejuice ... noodleSoup, noodleSoup, noodleSoup";
        var doc4 = "noodleSoup, noodleSoup,noodleSoup, noodleSoup, noodleSoup";
        var doc5 = "test mb?";

        docs = List.of(
                Map.of("id", "doc2", "text", doc2),
                Map.of("id", "doc1", "text", doc1),
                Map.of("id", "doc3", "text", doc3),
                Map.of("id", "doc5", "text", doc5),
                Map.of("id", "doc4", "text", doc4)
        );

        result = SearchEngine.search(docs, "noodleSoup");
        assertEquals(List.of("doc4", "doc3", "doc2", "doc1"), result);
        System.out.println("PASSED: testWithRelevance_2     " + result);
    }

    @Test
    void testWithMultipleInput() {
        var doc1 = "I can't shoot straight unless I've had a pint!";
        var doc2 = "Don't shoot shoot shoot that thing at me.";
        var doc3 = "I'm your shooter.";
        var doc4 = "Don't shoot that thing at me.";

        List<Map<String, String>> docs = List.of(
                Map.of("id", "doc1", "text", doc1),
                Map.of("id", "doc2", "text", doc2),
                Map.of("id", "doc3", "text", doc3),
                Map.of("id", "doc4", "text", doc4)
        );

        List<String> result = SearchEngine.search(docs, "shoot at me");
        assertEquals(List.of("doc2", "doc4", "doc1"), result);
        System.out.println("PASSED: testWithMultipleInput_1     " + result);
    }

    @Test
    void testIndex() {
        List<Map<String, String>> docs = List.of(
                Map.of("id", "doc1", "text", "some text"),
                Map.of("id", "doc2", "text", "some text too")
        );

        Map<String, List<String>> index = Map.of(
                "some", List.of("doc1", "doc2"),
                "text", List.of("doc1", "doc2"),
                "too", List.of("doc2")
        );

        Map<String, ArrayList<String>> result = SearchEngine.getIndexMap(docs, "some text too");
        assertEquals(index, result);
        System.out.println("PASSED: testIndex     " + result);
    }
}
