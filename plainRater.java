
/**
 * a description of class plainRater here.
 * The plainRater class is a Java implementation of the Rater interface,
 * which represents a rater who rates items with a certain value.
 * The class stores the rater's ID and a list of Rating objects,
 * where each Rating object represents the rating of an item with a specific value.
 * The class provides methods to add a new rating, check if the rater has rated a certain item,
 * get the ID of the rater, get the rating value of a specific item,
 * get the total number of items rated by the rater,
 * and get a list of all the items rated by the rater.
 * @author abdalrhmanFawzy 
 * @version 3/28/23
 */

import java.util.*;

public class plainRater implements Rater{
    private String myID;
    private ArrayList<Rating> myRatings;

    public plainRater(String id) {
        myID = id;
        myRatings = new ArrayList<Rating>();
    }

    public void addRating(String item, double rating) {
        myRatings.add(new Rating(item,rating));
    }

    public boolean hasRating(String item) {
        for(int k=0; k < myRatings.size(); k++){
            if (myRatings.get(k).getItem().equals(item)){
                return true;
            }
        }
        
        return false;
    }

    public String getID() {
        return myID;
    }

    public double getRating(String item) {
        for(int k=0; k < myRatings.size(); k++){
            if (myRatings.get(k).getItem().equals(item)){
                return myRatings.get(k).getValue();
            }
        }
        
        return -1;
    }

    public int numRatings() {
        return myRatings.size();
    }

    public ArrayList<String> getItemsRated() {
        ArrayList<String> list = new ArrayList<String>();
        for(int k=0; k < myRatings.size(); k++){
            list.add(myRatings.get(k).getItem());
        }
        
        return list;
    }
}
