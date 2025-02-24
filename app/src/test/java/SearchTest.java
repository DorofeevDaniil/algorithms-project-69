import hexlet.code.SearchEngine;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchTest {

    @Test
    void testSearch() {
        var doc1 = "I can't shoot straight unless I've had a pint!";
        var doc2 = "Don't shoot shoot shoot that thing at me.";
        var doc3 = "I'm your shooter.";

        // создание документа
        // документ имеет два атрибута "id" и "text"
        List<Map<String, String>> docs = List.of(
                Map.of("id", "doc1", "text", doc1),
                Map.of("id", "doc2", "text", doc2),
                Map.of("id", "doc3", "text", doc3)
        );

        // поисковый движок проводит поиск
        List<String> result = SearchEngine.search(docs, "shoot");

        System.out.println(result); // => ["doc1", "doc2"]
        assertEquals(List.of("doc1", "doc2"), result);

        // Документы пусты
        result = SearchEngine.search(new ArrayList<>(), "shoot"); // []

        System.out.println(result);
        assertEquals(List.of(), result);
    }

    @Test
    void testWithMarks() {
        var doc1 = "I can't shoot straight unless I've had a pint!";

        List<Map<String, String>> docs = List.of(
                Map.of("id", "doc1", "text", doc1));

        List<String> result = SearchEngine.search(docs, "pint");
        System.out.println(result); // => ["doc1"]
        assertEquals(List.of("doc1"), result);

        result = SearchEngine.search(docs, "pint!");
        System.out.println(result); // => ["doc1"]
        assertEquals(List.of("doc1"), result);
    }
}
