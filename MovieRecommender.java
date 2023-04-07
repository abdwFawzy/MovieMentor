import java.util.ArrayList;

public class MovieRecommender {
    private FourthRatings ratings;

    public MovieRecommender(String movieFile, String ratingFile) {
        ratings = new FourthRatings();
        MovieDatabase.initialize(movieFile);
        RaterDatabase.initialize(ratingFile);
    }

    public void printSimilarRatingsByGenre() {
        ArrayList<Rating> similarRatings = ratings.getSimilarRatingsByFilter("65", 20, 5, new GenreFilter("Action"));
        if (similarRatings.isEmpty()) {
            System.out.println("No movies found");
            return;
        }
        System.out.println("Movies recommended for you:");
        for (Rating rating : similarRatings) {
            String movieID = rating.getItem();
            Movie movie = MovieDatabase.getMovie(movieID);
            System.out.println(movie.getTitle() + " : " + rating.getValue());
            System.out.println("\t" + movie.getGenres());
        }
    }

    public void printSimilarRatingsByDirector() {
        ArrayList<Rating> similarRatings = ratings.getSimilarRatingsByFilter("1034", 10, 3, new TrueFilter());
        if (similarRatings.isEmpty()) {
            System.out.println("No movies found");
            return;
        }
        System.out.println("Movies recommended for you:");
        for (Rating rating : similarRatings) {
            String movieID = rating.getItem();
            Movie movie = MovieDatabase.getMovie(movieID);
            System.out.println(movie.getTitle() + " : " + rating.getValue());
            System.out.println("\t" + movie.getDirector());
        }
    }

    public void printSimilarRatingsByGenreAndMinutes() {
        AllFilters filters = new AllFilters();
        filters.addFilter(new GenreFilter("Adventure"));
        filters.addFilter(new MinutesFilter(100, 200));

        ArrayList<Rating> similarRatings = ratings.getSimilarRatingsByFilter("65", 10, 5, filters);
        if (similarRatings.isEmpty()) {
            System.out.println("No movies found");
            return;
        }
        System.out.println("Movies recommended for you:");
        for (Rating rating : similarRatings) {
            String movieID = rating.getItem();
            Movie movie = MovieDatabase.getMovie(movieID);
            System.out.println(movie.getTitle() + " : " + rating.getValue() + " : " + movie.getMinutes());
            System.out.println("\t" + movie.getGenres());
        }
    }

    public void printSimilarRatingsByYearAfterAndMinutes() {
        AllFilters filters = new AllFilters();
        filters.addFilter(new YearAfterFilter(2000));
        filters.addFilter(new MinutesFilter(80, 100));

        ArrayList<Rating> similarRatings = ratings.getSimilarRatingsByFilter("65", 10, 5, filters);
        if (similarRatings.isEmpty()) {
            System.out.println("No movies found");
            return;
        }
        System.out.println("Movies recommended for you:");
        for (Rating rating : similarRatings) {
            String movieID = rating.getItem();
            Movie movie = MovieDatabase.getMovie(movieID);
            System.out.println(movie.getTitle() + " : " + movie.getYear() + " : " + movie.getMinutes() + " : " + rating.getValue());
        }
    }
}

