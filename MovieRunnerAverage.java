import java.util.*;
public class MovieRunnerAverage{
    public void getAverageRatingOneMovie() {
        String movieTitle = "Vacation";
        SecondRatings sr = new SecondRatings("data/ratedmoviesfull.csv","data/ratings.csv");
        String movieID = sr.getID(movieTitle);
        if (movieID.equals("NO SUCH TITLE")) {
            System.out.println(movieID);
            return;
        }
        double avgRating = sr.getAverageByID(movieID, 1);
        System.out.printf("The average rating for \"%s\" is %.4f\n", movieTitle, avgRating);
    }
    public void printAverageRatings()
    {
        SecondRatings sr = new SecondRatings("data/ratedmoviesfull.csv","data/ratings.csv");
        System.out.println(sr.getMovieSize());
        System.out.println(sr.getRaterSize());
        ArrayList<Rating> ratings = sr.getAverageRatings(12);
        Collections.sort(ratings);
        for (Rating rating : ratings)
        {
            System.out.println(rating.getValue() + "|" + sr.getTitle(rating.getItem()));
        }
    }
    public static void main(String[] args)
    {
        MovieRunnerAverage mra = new MovieRunnerAverage();
        mra.getAverageRatingOneMovie();
        mra.printAverageRatings();
    }
}
