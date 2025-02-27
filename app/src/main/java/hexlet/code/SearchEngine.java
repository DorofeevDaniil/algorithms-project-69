package hexlet.code;

import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.MatchResult;
import java.util.stream.Collectors;

public class SearchEngine {
    private static final HashMap<String, HashMap<String, Double>> indexMap = new HashMap<>();

    public SearchEngine (List<Map<String, String>> inputList) {
        getInitialIndexMap(inputList);
    }

    public HashMap<String, HashMap<String, Double>> getIndexMap() {
        return  indexMap;
    }

    public static List<String> search(List<Map<String, String>> inputList, String searchStr) {
        if (indexMap.isEmpty()) getInitialIndexMap(inputList);

        List<Map<Double, String>> relevanceList = new ArrayList<>();
        List<String> resultList = new ArrayList<>();

        for (Map<String, String> mp : inputList) {
            Double docRelevance = 0.0;
            HashMap<Double, String> relevanceMap = new HashMap<>();
            for (String checkStr : getCleanStr(searchStr.toLowerCase()).split(" ")) {
                docRelevance += indexMap.get(checkStr).get(mp.get("id"));
            }

            if (docRelevance != 0.0) {
                relevanceMap.put(docRelevance, mp.get("id"));
                relevanceList.add(relevanceMap);
            }
        }

        for (Map<Double, String> curr : getSortedDocKeyList(relevanceList)) {
            resultList.add(curr.values().stream().toList().get(0));
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

    private static List<Map<Double, String>> getSortedDocKeyList(List<Map<Double, String>> unsortedList) {
        Collections.sort(unsortedList, new Comparator<Map<Double, String>>() {

            @Override
            public int compare(Map<Double, String> o1, Map<Double, String> o2) {
                return o2.keySet().stream().toList().get(0).compareTo(o1.keySet().stream().toList().get(0));
            }
        });

        return unsortedList;
    }

    public static HashMap<String, HashMap<String, Double>> getInitialIndexMap(List<Map<String, String>> inputList) {

        for (Map<String, String> mp : inputList) {
            for(String word : mp.get("text").toLowerCase().split("\\W+")) {
                if (!indexMap.containsKey(word)) {
                    setWordTFIDList(inputList, word);
                }
            }
        }

        return indexMap;
    }

    private static void setWordTFIDList(List<Map<String, String>> inputList, String checkWord) {
        HashMap<String, Double> innerMap = new HashMap<>();
        int collectionVolume = 0;
        for (Map<String, String> mp : inputList) {

            if (!getCleanStr(mp.get("text").toLowerCase()).contains(checkWord)) {
                innerMap.put(mp.get("id"), 0.0);
            }

            collectionVolume++;

            int documentVolume = 0;
            String[] textArray = getCleanStr(mp.get("text").toLowerCase()).split("\\W+");
            int docSize = textArray.length;

            for(String word : textArray) {
                if (word.equals(getCleanStr(checkWord))) {
                    documentVolume++;
                }
            }

            innerMap.put(mp.get("id"), (double) documentVolume / docSize);
        }

        for (String key : innerMap.keySet()) {
            innerMap.put(key, innerMap.get(key) * Math.log(((double) collectionVolume / inputList.size() + 0.5)));
        }

        indexMap.put(checkWord, innerMap);
    }
}
