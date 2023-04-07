
/**
 * Write a description of FourthRatings here.
 * 
 * @author (abalrhman fawzy) 
 * @version (3/27/2023)
 */
import java.text.*;
import java.util.*;
import edu.duke.*;
import org.apache.commons.csv.*;
public class FourthRatings{
    public FourthRatings()
    {
        RaterDatabase.initialize("ratings.csv");
    }
    public FourthRatings(String filename)
    {
        RaterDatabase.initialize(filename);
    }
    private int get_movie_rating_count(String item)
    {
        int count =0;
        for (Rater rater : RaterDatabase.getRaters())
        {
            rater = (EfficientRater) rater;
            if (rater.hasRating(item))
                count ++;
        }
        return count;
    }
    private double find_sum_of_ratings_for_movie(String movie_id)
    {
        double count = 0;
        for (Rater rater : RaterDatabase.getRaters())
        {
            rater = (EfficientRater) rater;
            if (rater.hasRating(movie_id))
                count += rater.getRating(movie_id);
        }
        return count;
    }
    public double getAverageByID(String movieID, int minimalRatings)
    {
        if (!(get_movie_rating_count(movieID) >= minimalRatings))
            return 0;
        return find_sum_of_ratings_for_movie(movieID) / (double) get_movie_rating_count(movieID);
    }
    public ArrayList<Rating> getAverageRatings(int minimalRatings)
    {
        ArrayList<Rating> ratings = new ArrayList<Rating>();
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        for (String movie_id : movies)
        {
            double rating = getAverageByID(movie_id, minimalRatings);
            if (rating > 0)
                ratings.add(new Rating(movie_id, rating));
        }
        return ratings;
    }
    
    public double dotProduct(Rater me, Rater other) {
        ArrayList<String> sharedMovies = getSharedMovies(me, other);
        if (sharedMovies.size() == 0) {
            return 0.0;
        }
        double sum = 0.0;
        double sumOfSquares1 = 0.0;
        double sumOfSquares2 = 0.0;
        for (String movie : sharedMovies) {
            double myRating = me.getRating(movie);
            double otherRating = other.getRating(movie);
            sum += myRating * otherRating;
            sumOfSquares1 += Math.pow(myRating, 2);
            sumOfSquares2 += Math.pow(otherRating, 2);
        }
        double denominator = Math.sqrt(sumOfSquares1) * Math.sqrt(sumOfSquares2);
        if (denominator == 0.0) {
            return 0.0;
        }
        return sum / denominator;
    }

    private ArrayList<String> getSharedMovies(Rater me, Rater otherRater) {
        ArrayList<String> myMovies = me.getItemsRated();
        ArrayList<String> otherMovies = otherRater.getItemsRated();
        ArrayList<String> sharedMovies = new ArrayList<>();
        for (String movie : myMovies) {
            if (otherMovies.contains(movie)) {
                sharedMovies.add(movie);
            }
        }
        return sharedMovies;
    }

    private ArrayList<Rating> getSimilarities(String id)
    {
        Rater me =  RaterDatabase.getRater(id);
        ArrayList<Rating> ratings = new ArrayList<Rating>();
        for (Rater rater : RaterDatabase.getRaters())
        {
            rater = (EfficientRater) rater;
            me = (EfficientRater) me;
            if (rater.getID().equals(me.getID()))
                continue;
            if (dotProduct(me, rater) <= 0)
                continue;
            Rating rating = new Rating(rater.getID(), dotProduct(me, rater));
            ratings.add(rating);
        }
        Collections.sort(ratings, Collections.reverseOrder());
        return ratings; 
    }

    public ArrayList<Rating> getSimilarRatingsByFilter(String raterID, int numSimilarRaters, int minimalRaters, Filter filterCriteria) {

        ArrayList<Rating> ratings = new ArrayList<Rating>();

        // Check if the raterID exists in the RaterDatabase
        if (RaterDatabase.getRater(raterID) == null) {
            System.out.println("EfficientRater " + raterID + " was not found");
            return ratings;
        }

        // Get similar raters sorted by similarity rating
        ArrayList<Rating> similarRaters = getSimilarities(raterID);

        // Get movies filtered by the filterCriteria
        ArrayList<String> movies = MovieDatabase.filterBy(filterCriteria);

        // Iterate through each movie and compute the weighted average rating
        for (String movieID : movies) {
            double accum = 0;
            int numRaters = 0;

            // Iterate through the top numSimilarRaters raters and accumulate their ratings
            for (int i = 0; i <= numSimilarRaters; i++) {
                String raterID2 = similarRaters.get(i).getItem();

                // Check if the other rater has rated the movie
                EfficientRater otherRater = (EfficientRater) RaterDatabase.getRater(raterID2);
                double rating = otherRater.getRating(movieID);
                if (rating != -1) {
                    accum += rating * similarRaters.get(i).getValue();
                    numRaters++;
                }
            }

            // Check if there are enough minimalRaters to include the movie in the final ratings list
            if (numRaters >= minimalRaters) {
                double weightedAverageRating = accum / numRaters;
                ratings.add(new Rating(movieID, weightedAverageRating));
            }
        }

        // Sort the ratings by weighted average rating in descending order
        Collections.sort(ratings, Collections.reverseOrder());

        return ratings;
    }

    public ArrayList<Rating> getSimilarRatings(String raterID, int numSimilarRaters, int minimalRaters)
    {
        ArrayList<Rating> ratings = new ArrayList<Rating>();
        ArrayList<Rating> similarRaters = getSimilarities(raterID);
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        int i = 0;
        double accum = 0;
        // for each movie in rater movies 
        for (String movieID : movies)
        {
            // for each rater in similarRaters within numSimilarRaters
            for (int k = 0; k < similarRaters.size(); k++)
            {
                EfficientRater rater =(EfficientRater) RaterDatabase.getRater(similarRaters.get(k).getItem());
                if (rater.getRating(movieID) != -1)
                {
                    accum += rater.getRating(movieID) * similarRaters.get(k).getValue();
                    i++;
                }
            }
            if (i >= minimalRaters)
            {
                ratings.add(new Rating(movieID,  accum / (double) i));
            }
            i = 0;
            accum = 0;
        }
        Collections.sort(ratings);
        return ratings;
    }

}


