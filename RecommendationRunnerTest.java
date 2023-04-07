import java.util.ArrayList;

public class RecommendationRunnerTest {
    public void testGetItemsToRate() {
        RecommendationRunner rr = new RecommendationRunner();
        ArrayList<String> itemsToRate = rr.getItemsToRate();
        System.out.println("Items to rate: " + itemsToRate);
    }
    
    public void testPrintRecommendationsFor() {
        RecommendationRunner rr = new RecommendationRunner();
        String webRaterID = "133";
        rr.printRecommendationsFor(webRaterID);
    }
    
    public static void main(String[] args) {
        RecommendationRunnerTest test = new RecommendationRunnerTest();
        test.testGetItemsToRate();
        test.testPrintRecommendationsFor();
    }
}

