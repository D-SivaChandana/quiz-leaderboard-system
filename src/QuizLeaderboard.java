import java.net.http.*;
import java.net.URI;
import java.util.*;

public class QuizLeaderboard {
    public static void main(String[] args) throws Exception {

        Set<String> seen = new HashSet<>();
        Map<String, Integer> scores = new HashMap<>();

        HttpClient client = HttpClient.newHttpClient();

        for (int i = 0; i < 10; i++) {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://devapigw.vidalhealthtpa.com/srm-quiz-task/quiz/messages?regNo=2024CS101&poll=" + i))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println(response.body()); // (for testing)

            Thread.sleep(5000);
        }
    }
}
