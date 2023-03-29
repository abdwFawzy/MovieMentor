
/**
 * Write a description of class EfficientRater here.
 * 
 * @author abdalrhmanFawzy 
 * @version 3/28/23
 */

import java.util.*;

public class EfficientRater implements Rater{
    private String myID;
    private HashMap<String,Rating> myRatings;

    public EfficientRater(String id) {
        myID = id;
        myRatings = new HashMap<String,Rating>();
    }

    public void addRating(String item, double rating) {
        myRatings.put(item, new Rating(item,rating));
    }

    public boolean hasRating(String item) {
        if(myRatings.containsKey(item))
            return true;
        return false;
    }

    public String getID() {
        return myID;
    }

    public double getRating(String item) {
        if (myRatings.containsKey(item))
            return myRatings.get(item).getValue();
        return -1;
    }

    public int numRatings() {
        return myRatings.size();
    }

    public ArrayList<String> getItemsRated() {
        ArrayList<String> answer = new ArrayList<String>();
        for (String key : myRatings.keySet())
        {
            answer.add(key);
        }
        return answer;
    }
}

