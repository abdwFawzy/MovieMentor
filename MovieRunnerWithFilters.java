import java.util.*;
public class MovieRunnerWithFilters{
    public void getAverageRatingOneMovie() {
//          String movieTitle = "Vacation";
//          ThirdRatings sr = new ThirdRatings();
//          String movieID = sr.getID(movieTitle);
//          if (movieID.equals("NO SUCH TITLE")) {
//              System.out.println(movieID);
//              return;
//          }
//          double avgRating = sr.getAverageByID(movieID, 1);
//          System.out.printf("The average rating for \"%s\" is %.4f\n", movieTitle, avgRating);
    }
    public void printAverageRatings()
    {
        ThirdRatings tr = new ThirdRatings("data/ratings.csv");
        System.out.println("Number of raters: " + tr.getRaterSize());

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
    public void printAverageRatingsByYear()
    {
        ThirdRatings tr = new ThirdRatings("data/ratings.csv");
        System.out.println("Number of raters: " + tr.getRaterSize());

        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("Number of movies: " + MovieDatabase.size());

        int minimalRaters =20;
        ArrayList<Rating> ratings = tr.getAverageRatingsByFilter(minimalRaters, new YearAfterFilter(2000));
        System.out.println("Number of movies with ratings: " + ratings.size());

        Collections.sort(ratings);
        for (Rating rating : ratings) {
            System.out.println(rating.getValue() + " " + MovieDatabase.getTitle(rating.getItem()));
        }
    }
        
    public void printAverageRatingsByGenre(int minimalRaters, String genre) {
        ThirdRatings tr = new ThirdRatings("data/ratings.csv");
        System.out.println("Number of raters: " + tr.getRaterSize());

        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("Number of movies: " + MovieDatabase.size());

        GenreFilter filter = new GenreFilter(genre);
        ArrayList<Rating> averageRatings = tr.getAverageRatingsByFilter(minimalRaters, filter);

        Collections.sort(averageRatings);

        System.out.println("Number of movies found: " + averageRatings.size());
        for (Rating rating : averageRatings) {
            System.out.printf("%.2f %s\n%s\n",
                    rating.getValue(), MovieDatabase.getTitle(rating.getItem()),
                    MovieDatabase.getGenres(rating.getItem()));
        }
    }
    public void printAverageRatingsByMinutes() {
        ThirdRatings tr = new ThirdRatings("data/ratings.csv");
        System.out.println("Number of raters: " + tr.getRaterSize());

        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("Number of movies: " + MovieDatabase.size());

        int minNumOfRatings = 5;
        int minMinutes = 105;
        int maxMinutes = 135;

        MinutesFilter filter = new MinutesFilter(minMinutes, maxMinutes);
        ArrayList<Rating> ratings = tr.getAverageRatingsByFilter(minNumOfRatings,
                filter);
        System.out.println("Number of movies found: " + ratings.size());

        Collections.sort(ratings);

        for (Rating rating : ratings) {
            String movieID = rating.getItem();
            int movieMinutes = MovieDatabase.getMinutes(movieID);
            System.out.println(rating.getValue() + " " + "Time: " + movieMinutes
                    + " " + MovieDatabase.getTitle(movieID));
        }
    }
    public void printAverageRatingsByDirectors() {
        ThirdRatings thirdRatings = new ThirdRatings("data/ratings.csv");
        ArrayList<String> directors = new ArrayList<String>();
        String DIRECTORS = "Clint Eastwood,Joel Coen,Martin Scorsese,Roman Polanski,Nora Ephron,Ridley Scott,Sydney Pollack";
        for (String director : DIRECTORS.split(","))
        {
            directors.add(director);
        }
        

        int minNumOfRatings = 4;
        DirectorsFilter filter = new DirectorsFilter(directors);

        ArrayList<Rating> ratings = thirdRatings.getAverageRatingsByFilter(minNumOfRatings, filter);
        Collections.sort(ratings);

        System.out.println(ratings.size() + " movie(s) matched");

        for (Rating rating : ratings) {
            String movieID = rating.getItem();
            String movieTitle = MovieDatabase.getTitle(movieID);
            String directorsList = MovieDatabase.getDirector(movieID);

            System.out.println(rating.getValue() + " " + movieTitle);
            System.out.println("    " + directorsList);
        }
    }
    public void printAverageRatingsByYearAfterAndGenre() {
        ThirdRatings thirdRatings = new ThirdRatings("data/ratings.csv");
        int minimalRaters = 8;
        int year = 1990;
        String genre = "Drama";

        AllFilters allFilters = new AllFilters();
        allFilters.addFilter(new YearAfterFilter(year));
        allFilters.addFilter(new GenreFilter(genre));

        ArrayList<Rating> ratings = thirdRatings.getAverageRatingsByFilter(minimalRaters, allFilters);
        Collections.sort(ratings);

        System.out.println(ratings.size() + " movie(s) matched");

        for (Rating rating : ratings) {
            System.out.println(rating.getValue() + " " + MovieDatabase.getYear(rating.getItem())
                    + " " + MovieDatabase.getTitle(rating.getItem()));
            System.out.println("    " + MovieDatabase.getGenres(rating.getItem()));
        }
    }

    public void printAverageRatingsByDirectorsAndMinutes() {
        ThirdRatings thirdRatings = new ThirdRatings("data/ratings.csv");

        int minMinutes = 90;
        int maxMinutes = 180;

        ArrayList<String> directors = new ArrayList<String>();
        String DIRECTORS = "Clint Eastwood,Joel Coen,Tim Burton,Ron Howard,Nora Ephron,Sydney Pollack";
        for (String director : DIRECTORS.split(","))
        {
            directors.add(director);
        }
        AllFilters allFilters = new AllFilters();
        allFilters.addFilter(new DirectorsFilter(directors));
        allFilters.addFilter(new MinutesFilter(minMinutes, maxMinutes));

        int minimalRaters = 3;
        ArrayList<Rating> avgRatings = thirdRatings.getAverageRatingsByFilter(minimalRaters, allFilters);

        Collections.sort(avgRatings);
        System.out.println(avgRatings.size() + " movie(s) matched");

        for (Rating rating : avgRatings) {
            String movieID = rating.getItem();
            String title = MovieDatabase.getTitle(movieID);
            int minutes = MovieDatabase.getMinutes(movieID);
            System.out.println(rating.getValue() + " " + "Time: " + minutes + " " + title);
            System.out.println("\t" + MovieDatabase.getDirector(movieID));
        }
    }

    public static void main(String[] args)
    {
        MovieRunnerWithFilters mra = new MovieRunnerWithFilters();
        //mra.getAverageRatingOneMovie();
//        mra.printAverageRatings();
//        mra.printAverageRatingsByYear();
//        mra.printAverageRatingsByGenre(20, "Comedy");
//        mra.printAverageRatingsByMinutes();
//        mra.printAverageRatingsByDirectors();
//        mra.printAverageRatingsByYearAfterAndGenre();
        mra.printAverageRatingsByDirectorsAndMinutes();
    }
}

