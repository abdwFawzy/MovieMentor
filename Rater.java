import java.util.ArrayList;

public interface Rater {
    /**
     * Adds a rating for an item.
     * 
     * @param item   the ID of the item being rated
     * @param rating the rating for the item
     */
    public void addRating(String item, double rating);

    /**
     * Returns whether this rater has a rating for the given item.
     * 
     * @param item the ID of the item being checked
     * @return true if the rater has rated the item, false otherwise
     */
    public boolean hasRating(String item);

    /**
     * Returns the ID of this rater.
     * 
     * @return the ID of this rater
     */
    public String getID();

    /**
     * Returns the rating of an item by this rater.
     * 
     * @param item the ID of the item being rated
     * @return the rating of the item, or -1 if the rater has not rated the item
     */
    public double getRating(String item);

    /**
     * Returns the number of items rated by this rater.
     * 
     * @return the number of items rated by this rater
     */
    public int numRatings();

    /**
     * Returns a list of all items rated by this rater.
     * 
     * @return a list of all items rated by this rater
     */
    public ArrayList<String> getItemsRated();
}

