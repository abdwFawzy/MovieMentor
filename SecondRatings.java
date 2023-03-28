
/**
 * Write a description of SecondRatings here.
 * 
 * @author (abalrhman fawzy) 
 * @version (3/27/2023)
 */

import java.util.*;
import edu.duke.*;
import org.apache.commons.csv.*;
public class SecondRatings extends FirstRatings {
    private ArrayList<Movie> myMovies;
    private ArrayList<Rater> myRaters;
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
        FileResource fr = new FileResource(ratersFileName);
        for (CSVRecord currRec : fr.getCSVParser())
        {
            if (currRec.get("movie_id").equals(movie_id))
                count += Double.parseDouble(currRec.get("rating"));
        }
        return count;
    }
    public double getAverageByID(String movieID, int minimalRatings)
    {
        if (!(get_movie_rating_count(ratersFileName, movieID) > minimalRatings))
            return 0;
        return find_sum_of_ratings_for_movie(movieID) / (double) get_movie_rating_count(ratersFileName, movieID);
    }
    public ArrayList<Rating> getAverageRatings(int minimalRatings)
    {
        ArrayList<Rating> ratings = new ArrayList<Rating>();
        for (Movie movie : myMovies)
        {
            String movie_id = movie.getID();
            double rating = getAverageByID(movie_id, minimalRatings);
            if (rating > 0)
                ratings.add(new Rating(movie_id, rating));
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
