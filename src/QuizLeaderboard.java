import java.net.*;
import java.io.*;
import java.util.*;
public class QuizLeaderboard {
    public static void main(String[] args) throws Exception {
        String baseUrl = "https://devapigw.vidalhealthtpa.com/srm-quiz-task";
        String regNo = "2024CS101";
        Set<String> seen = new HashSet<>();
        Map<String, Integer> scores = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            URL url = new URL(baseUrl + "/quiz/messages?regNo=" + regNo + "&poll=" + i);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String data = br.readLine();
            br.close();
            if (data == null) continue;
            String[] tokens = data.split("\\{");
            for (String t : tokens) {
                if (t.contains("roundId") && t.contains("participant") && t.contains("score")) {
                    String roundId = getValue(t, "roundId");
                    String participant = getValue(t, "participant");
                    String scoreStr = getValue(t, "score");
                    if (roundId == null || participant == null || scoreStr == null) continue;
                    String key = roundId + "_" + participant;
                    if (!seen.contains(key)) {
                        seen.add(key);
                        int score = Integer.parseInt(scoreStr);
                        scores.put(participant,
                                scores.getOrDefault(participant, 0) + score);
                    }
                }
            }
            Thread.sleep(5000);
        }
        // leaderboard
        List<String> list = new ArrayList<>(scores.keySet());
        list.sort((a, b) -> scores.get(b) - scores.get(a));
        System.out.println("Leaderboard:");
        for (String p : list) {
            System.out.println(p + " -> " + scores.get(p));
        }
    }
    static String getValue(String text, String key) {
        try {
            int i = text.indexOf(key);
            if (i == -1) return null;
            int start = text.indexOf(":", i) + 1;
            int end = text.indexOf(",", start);
            if (end == -1) end = text.length();
            return text.substring(start, end)
                    .replace("\"", "")
                    .trim();
        } catch (Exception e) {
            return null;
        }
    }
}
