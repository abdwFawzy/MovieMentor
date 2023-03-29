    
/**
 * Write a description of class plainRater here.
 * 
 * @author abdalrhmanFawzy 
 * @version 3/28/23
 */
import java.util.*;
public interface Rater {
    public void addRating(String item, double rating); 
    public boolean hasRating(String item); 
    public String getID(); 
    public double getRating(String item); 
    public int numRatings(); 
    public ArrayList<String> getItemsRated(); 
}

