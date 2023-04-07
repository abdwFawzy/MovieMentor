import java.util.*;
public class MovieRunnerSimilarRatings{

    public void printAverageRatings()
    {
        FourthRatings tr = new FourthRatings("data/ratings.csv");
        RaterDatabase.initialize("ratings_short.csv");
        System.out.println("Number of raters: " + RaterDatabase.size());

        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("Number of movies: " + MovieDatabase.size());

        int minimalRaters = 35;
        ArrayList<Rating> ratings = tr.getAverageRatings(minimalRaters);
        System.out.println("Number of movies with ratings: " + ratings.size());

        Collections.sort(ratings);
        for (Rating rating : ratings) {
            System.out.println(rating.getValue() + " " + MovieDatabase.getTitle(rating.getItem()));
        }
    }

    public void printAverageRatingsByYearAfterAndGenre() {
//        FourthRatings thirdRatings = new FourthRatings("data/ratings.csv");
//        RaterDatabase.initialize("ratings_short.csv");
//        int minimalRaters = 8;
//        int year = 1990;
//        String genre = "Drama";
//
//        AllFilters allFilters = new AllFilters();
//        allFilters.addFilter(new YearAfterFilter(year));
//        allFilters.addFilter(new GenreFilter(genre));
//
//        ArrayList<Rating> ratings = thirdRatings.getAverageRatingsByFilter(minimalRaters, allFilters);
//        Collections.sort(ratings);
//
//        System.out.println(ratings.size() + " movie(s) matched");
//
//        for (Rating rating : ratings) {
//            System.out.println(rating.getValue() + " " + MovieDatabase.getYear(rating.getItem())
//                    + " " + MovieDatabase.getTitle(rating.getItem()));
//            System.out.println("    " + MovieDatabase.getGenres(rating.getItem()));
//        }
    }
    public void printSimilarRatings() {
    int numSimilarRaters = 20;
    int minimalRaters = 5;
    String raterID = "71";

    FourthRatings fr = new FourthRatings();
    RaterDatabase.initialize("ratings.csv");
    MovieDatabase.initialize("ratedmoviesfull.csv");

    ArrayList<Rating> similarRatings = fr.getSimilarRatings(raterID, numSimilarRaters, minimalRaters);

    if (similarRatings.isEmpty()) {
        System.out.println("No movies recommended for rater " + raterID);
    } else {
        System.out.println("Recommended movies:");
        for (Rating rating : similarRatings) {
            System.out.println(MovieDatabase.getTitle(rating.getItem()) + " : " + rating.getValue());
            }
        }
    }
    public void printSimilarRatingsByGenre() {
        FourthRatings fourthRatings = new FourthRatings();
        RaterDatabase.initialize("ratings.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");

        ArrayList<Rating> similarRatings = fourthRatings.getSimilarRatingsByFilter(
            "964",
            20,
            5,
            new GenreFilter("Mystery")
        );

        if (similarRatings.size() == 0) {
            System.out.println("No movies found.");
        } else {
            System.out.println("Recommended movies:");
            for (Rating rating : similarRatings) {
                Movie movie = MovieDatabase.getMovie(rating.getItem());
                System.out.println(movie.getTitle() + " : " + rating.getValue());
                System.out.println("   " + movie.getGenres());
            }
        }
    }

    public void printSimilarRatingsByDirector() {
        FourthRatings fr = new FourthRatings();
        RaterDatabase.initialize("ratings.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        
        ArrayList<String> directors = new ArrayList<String>(
            Arrays.asList("Clint Eastwood","J.J. Abrams","Alfred Hitchcock","Sydney Pollack","David Cronenberg","Oliver Stone","Mike Leigh")
        );
        Filter filter = new DirectorsFilter(directors);
        
        ArrayList<Rating> ratings = fr.getSimilarRatingsByFilter("120", 10, 2, filter);
        if (ratings.size() == 0) {
            System.out.println("No movies found.");
        } else {
            System.out.println("Movies recommended by similarity ratings and directors:");
            for (Rating rating : ratings) {
                System.out.println("Rating: " + rating.getValue() + "\n" + MovieDatabase.getTitle(rating.getItem()));
                System.out.println("Directors: " + MovieDatabase.getDirector(rating.getItem()) + "\n");
            }
        }
    }
    public void printSimilarRatingsByGenreAndMinutes() {
        FourthRatings fr = new FourthRatings();

        // Read movie and ratings data from files
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");

        // Set filters
        AllFilters allFilters = new AllFilters();
        allFilters.addFilter(new GenreFilter("Drama"));
        allFilters.addFilter(new MinutesFilter(80, 160));

        // Get recommended movies and their similarity ratings
        ArrayList<Rating> ratings = fr.getSimilarRatingsByFilter("168", 10, 3, allFilters);

        if (ratings.size() == 0) {
            System.out.println("No movies found.");
        } else {
            System.out.println("Recommended movies:");
            for (Rating rating : ratings) {
                String movieID = rating.getItem();
                Movie movie = MovieDatabase.getMovie(movieID);
                double similarity = rating.getValue();

                System.out.println(movie.getTitle() + " (" + movie.getMinutes() + " minutes)");
                System.out.println("Genres: " + movie.getGenres());
                System.out.println("Similarity rating: " + similarity);
                System.out.println();
            }
        }
    }

    public void printSimilarRatingsByYearAfterAndMinutes() {
        FourthRatings fourthRatings = new FourthRatings();

        // Read in the movie and ratings data
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");

        // Set the filters
        Filter yearAfterFilter = new YearAfterFilter(1975);
        Filter minutesFilter = new MinutesFilter(70, 200);
        AllFilters allFilters = new AllFilters();
        allFilters.addFilter(yearAfterFilter);
        allFilters.addFilter(minutesFilter);

        // Get the similar ratings
        ArrayList<Rating> similarRatings = fourthRatings.getSimilarRatingsByFilter(
            "314",
            10,
            5,
            allFilters
        );

        if (similarRatings.size() == 0) {
            System.out.println("No movies found.");
        } else {
            System.out.println("Recommended movies:");
            for (Rating rating : similarRatings) {
                String movieID = rating.getItem();
                String movieTitle = MovieDatabase.getTitle(movieID);
                int movieYear = MovieDatabase.getYear(movieID);
                int movieMinutes = MovieDatabase.getMinutes(movieID);
                double similarity = rating.getValue();

                System.out.printf("%s (%d) %d min - %.2f\n", movieTitle,
                        movieYear, movieMinutes, similarity);
            }
        }
    }

    public static void main(String[] args)
    {
        MovieRunnerSimilarRatings mra = new MovieRunnerSimilarRatings();
//        mra.printAverageRatings();
//        mra.printAverageRatingsByYearAfterAndGenre();
//        mra.printSimilarRatings();
//        mra.printSimilarRatingsByGenre();
//        mra.printSimilarRatingsByDirector();
//        mra.printSimilarRatingsByGenreAndMinutes();
        mra.printSimilarRatingsByYearAfterAndMinutes();
    }
}

