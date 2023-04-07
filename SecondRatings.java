
/**
 * Write a description of SecondRatings here.
 * The SecondRatings class is a class that reads movie and rating data from CSV files and provides methods to access information about movies
 * and their ratings. It extends the FirstRatings class and contains an ArrayList of movies and an ArrayList of efficient raters.
 * It also includes methods to get the size of the movie and rater lists, to find the average rating of a movie with a specified ID,
 * to get a set of all ratings above a specified minimal rating, to get the title of a movie with a specified ID,
 * and to get the ID of a movie with a specified title.
 * This class is used in conjunction with the MovieRunnerAverage class to provide information about movies and their ratings.
 * @author (abalrhman fawzy) 
 * @version (3/27/2023)
 */

import java.util.*;
import edu.duke.*;
import org.apache.commons.csv.*;
public class SecondRatings extends FirstRatings {
    private ArrayList<Movie> myMovies;
    public ArrayList<EfficientRater> myRaters;
    private String ratersFileName;
    private String moviesFileName;
    public SecondRatings() {
        // default constructor
        this("data/ratedmoviesfull.csv", "data/ratings.csv");
    }
    public SecondRatings(String movieFile, String ratingsFile)
    {
        myMovies = loadMovies(movieFile);
        myRaters = loadRaters(ratingsFile);
        ratersFileName = ratingsFile;
        moviesFileName = movieFile;
    }
    public int getMovieSize()
    {
        return myMovies.size();
    }
    public int getRaterSize()
    {
        return myRaters.size();
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
        if (!(get_movie_rating_count(ratersFileName, movieID) > minimalRatings))
            return 0;
        return find_sum_of_ratings_for_movie(movieID) / (double) get_movie_rating_count(ratersFileName, movieID);
    }
    public HashSet<Rating> getAverageRatings(int minimalRatings)
    {
        HashSet<Rating> ratings = new HashSet<Rating>();
        HashSet<String> movies = new HashSet<String>();
        for (EfficientRater rater : myRaters)
        {
            for (String movie_id : rater.getItemsRated())
            {
                if (!movies.contains(movie_id))
                {
                    double rating = getAverageByID(movie_id, minimalRatings);
                    if (rating > 0)
                        ratings.add(new Rating(movie_id, rating));
                    movies.add(movie_id);
                }
            }
        }
        return ratings;
    }
    private int getMovieIndex(String movie_id)
    {
        for (int i = 0; i < myMovies.size(); i++) 
        {
            if (myMovies.get(i).getID().equals(movie_id))
                return i;
        }
        return -1;
    }
    public String getTitle(String id)
    {
        FileResource fr = new FileResource(ratersFileName);
        for (CSVRecord currRec : fr.getCSVParser())
        {
            if (id.equals(currRec.get("movie_id")))
                return  myMovies.get(getMovieIndex(currRec.get("movie_id"))).getTitle();
        }
        return "ID not Found";
    }
    public String getID(String title) {
        for (Movie movie : myMovies) {
            if (movie.getTitle().equals(title)) {
                return movie.getID();
            }
        }
        return "NO SUCH TITLE.";
    }
}
