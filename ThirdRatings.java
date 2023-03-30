
/**
 * Write a description of ThirdRatings here.
 * 
 * @author (abalrhman fawzy) 
 * @version (3/27/2023)
 */

import java.util.*;
import edu.duke.*;
import org.apache.commons.csv.*;
public class ThirdRatings{
    public ArrayList<EfficientRater> myRaters;
    public ThirdRatings() {
        this("ratings.csv");
    }
    public ThirdRatings(String ratingsFile)
    {
        FirstRatings fr = new FirstRatings();
        myRaters = fr.loadRaters(ratingsFile);
    }
    public int getRaterSize()
    {
        return myRaters.size();
    }
    private int get_movie_rating_count(String item)
    {
        int count =0;
        for (EfficientRater rater : myRaters)
        {
            if (rater.hasRating(item))
                count ++;
        }
        return count;
    }
    private double find_sum_of_ratings_for_movie(String movie_id)
    {
        double count = 0;
        for (EfficientRater rater : myRaters)
        {
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
    public ArrayList<Rating> getAverageRatingsByFilter(int minimalRatings, Filter filterCriteria)
    {
        ArrayList<Rating> ratings = new ArrayList<Rating>();
        ArrayList<String> movies = MovieDatabase.filterBy(filterCriteria);
        for (String movie_id : movies)
        {
            double rating = getAverageByID(movie_id, minimalRatings);
            if (rating > 0)
                ratings.add(new Rating(movie_id, rating));
        }
        return ratings;
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
}

