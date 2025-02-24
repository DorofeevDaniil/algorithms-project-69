package hexlet.code;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.MatchResult;
import java.util.stream.Collectors;

public class SearchEngine {

    public static List<String> search(List<Map<String, String>> inputList, String searchStr) {
        List<String> resultList = new ArrayList<>();

        var term = getTrunkStr(searchStr);
        for (Map<String, String> mp : inputList) {
            String[] textArray = mp.get("text").split(" ");
            for (String str : textArray) {
                if (getTrunkStr(str).equals(term)) {
                    resultList.add(mp.get("id"));
                    break;
                }
            }
        }

        return resultList;
    }

    private static String getTrunkStr(String inputStr) {
        return Pattern.compile("\\w+")
                .matcher(inputStr)
                .results()
                .map(MatchResult::group)
                .collect(Collectors.joining());
    }
}
