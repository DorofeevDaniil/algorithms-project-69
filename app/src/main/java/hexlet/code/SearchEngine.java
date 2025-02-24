package hexlet.code;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchEngine {

    public static void main(String[] args) {
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

        // Документы пусты
        List<String> result1 = SearchEngine.search(new ArrayList<Map<String, String>>(), "shoot"); // []

        System.out.println(result1);
    }

    public static List<String> search(List<Map<String, String>> inputList, String searchStr) {
        List<String> resultList = new ArrayList<>();

        for (Map<String, String> mp : inputList) {
            String[] textArray = mp.get("text").split(" ");
            for (String str : textArray) {
                if (str.equals(searchStr)) {
                    resultList.add(mp.get("id"));
                    break;
                }
            }
        }

        return resultList;
    }
}
