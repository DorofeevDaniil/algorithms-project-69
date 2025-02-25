package hexlet.code;

import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.MatchResult;
import java.util.stream.Collectors;

public class SearchEngine {

    public static List<String> search(List<Map<String, String>> inputList, String searchStr) {
        List<String> resultList = new ArrayList<>();
        List<Map<Integer, String>> relevanceList = new ArrayList<>();

        var term = getCleanStr(searchStr.toLowerCase());
        for (Map<String, String> mp : inputList) {
            String[] textArray = mp.get("text").toLowerCase().split("\\W+");
            int currRelevance = 0;

            for (String str : textArray) {
                if (getCleanStr(str).equals(term)) {
                    currRelevance++;
                }
            }
            if (currRelevance > 0) {
                relevanceList.add(Map.of(currRelevance, mp.get("id")));
            }

        }

        for (Map<Integer, String> curr : getSortedKeyList(relevanceList)) {
            resultList.add(curr.get(curr.entrySet().iterator().next().getKey()));
        }


        return resultList;
    }

    private static String getCleanStr(String inputStr) {
        return Pattern.compile("\\w+")
                .matcher(inputStr)
                .results()
                .map(MatchResult::group)
                .collect(Collectors.joining());
    }

    private static List<Map<Integer, String>> getSortedKeyList(List<Map<Integer, String>> unsortedList) {
        unsortedList.sort(Comparator.comparing(
                m -> m.entrySet().iterator().next().getKey(),
                Comparator.nullsLast(Comparator.reverseOrder()))
        );

        return unsortedList;
    }
}
