package hexlet.code;

import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.MatchResult;
import java.util.stream.Collectors;

public class SearchEngine {

    private static HashMap<String, ArrayList<String>> indexMap;

    public static List<String> search(List<Map<String, String>> inputList, String searchStr) {
        List<String> resultList = new ArrayList<>();
        List<Map<String, Object>> relevanceList = new ArrayList<>();

        String[] requestTerm = searchStr.toLowerCase().split(" ");

        for (Map<String, String> mp : inputList) {
            HashMap<String, Object> innerMap = new HashMap<>();

            int wordCount = 0;
            int entryCount = 0;

            for (String checkStr : requestTerm) {

                String[] textArray = mp.get("text").toLowerCase().split("\\W+");
                int currRelevance = 0;

                for (String str : textArray) {
                    if (getCleanStr(str).equals(getCleanStr(checkStr))) {
                        currRelevance++;
                    }
                }

                if (currRelevance > 0) {
                    entryCount += currRelevance;
                    wordCount++;
                    innerMap.put("wordCount", wordCount);
                    innerMap.put("entryCount", entryCount);
                }
            }

            if (!innerMap.isEmpty()) {
                innerMap.put("id", mp.get("id"));
                relevanceList.add(innerMap);
            }

        }

        for (Map<String, Object> curr : getSortedKeyList(relevanceList)) {
            resultList.add(curr.get("id").toString());
        }


        return resultList;
    }

    private static String getCleanStr(String inputStr) {
        return Pattern.compile("[\\s,\\w+]")
                .matcher(inputStr)
                .results()
                .map(MatchResult::group)
                .collect(Collectors.joining());
    }

    private static List<Map<String, Object>> getSortedKeyList(List<Map<String, Object>> unsortedList) {
        Collections.sort(unsortedList, new Comparator<Map<String, Object>>() {

            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {

                int compareVolume = ((Integer) o2.get("wordCount")).compareTo(((Integer) o1.get("wordCount")));

                if (compareVolume == 0) {
                    compareVolume = ((Integer) o2.get("entryCount")).compareTo(((Integer) o1.get("entryCount")));
                }

                return compareVolume;
            }
        });

        return unsortedList;
    }

    public static Map<String, ArrayList<String>> getIndexMap(List<Map<String, String>> inputList, String searchStr) {
        if (indexMap != null) {
            return indexMap;
        }

        indexMap = new HashMap<>();
        String[] requestTerm = searchStr.toLowerCase().split(" ");

        for (String checkStr : requestTerm) {
            ArrayList<String> innerList = new ArrayList<>();
            for (Map<String, String> mp : inputList) {
                String[] textArray = mp.get("text").toLowerCase().split("\\W+");
                Optional<String> present = Arrays
                                            .stream(textArray)
                                            .filter(x -> getCleanStr(x).equals(getCleanStr(checkStr)))
                                            .findFirst();
                if (present.isPresent()) innerList.add(mp.get("id"));
            }

            if (!innerList.isEmpty()) indexMap.put(checkStr, innerList);
        }

        return indexMap;
    }
}
