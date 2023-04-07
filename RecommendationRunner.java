import java.util.*;

public class RecommendationRunner implements Recommender{
    public ArrayList<String> getItemsToRate() {
        FourthRatings fr = new FourthRatings();
        ArrayList<String> itemsToRate = new ArrayList<String>();

        // Select recent movies
        int minYear = 2015;
        ArrayList<String> recentMovies = MovieDatabase.filterBy(new YearAfterFilter(minYear));
        Collections.shuffle(recentMovies);
        itemsToRate.addAll(recentMovies.subList(0, Math.min(5, recentMovies.size())));

        // Select highly rated movies
        int minRatings = 100;
        ArrayList<Rating> avgRatings = fr.getAverageRatings(minRatings);
        Collections.sort(avgRatings, Collections.reverseOrder());
        ArrayList<String> highlyRatedMovies = new ArrayList<String>();
        for (Rating r : avgRatings) {
            highlyRatedMovies.add(r.getItem());
        }
        Collections.shuffle(highlyRatedMovies);
        itemsToRate.addAll(highlyRatedMovies.subList(0, Math.min(5, highlyRatedMovies.size())));

        // Select popular directors
        String[] popularDirectors = {"Christopher Nolan", "Quentin Tarantino", "Martin Scorsese"};
        ArrayList<String> directorNames = new ArrayList<String>(Arrays.asList(popularDirectors));
        ArrayList<String> directorMovies = new ArrayList<String>();
        for (String director : directorNames) {
            ArrayList<String> movies = MovieDatabase.filterBy(new DirectorsFilter(new ArrayList<String>(Arrays.asList(director))));
            Collections.shuffle(movies);
            directorMovies.addAll(movies.subList(0, Math.min(5, movies.size())));
        }
        Collections.shuffle(directorMovies);
        itemsToRate.addAll(directorMovies.subList(0, Math.min(10, directorMovies.size())));

        return itemsToRate;
    }

    public void printRecommendationsFor(String webRaterID) {
        FourthRatings fr = new FourthRatings();
        int numSimilarRaters = 20;
        int minimalRaters = 3;
        int numMovies = 20;

        ArrayList<Rating> recommendedMovies = fr.getSimilarRatings(webRaterID, numSimilarRaters, minimalRaters);
        Rater rater = RaterDatabase.getRater(webRaterID);

        if (recommendedMovies.size() == 0) {
            System.out.println("<p>Sorry, no recommendations found.</p>");
        } else {
            ArrayList<Rating> averageRatings = fr.getAverageRatings(minimalRaters);
            Collections.sort(averageRatings, Collections.reverseOrder());
            System.out.println("<table>");
            System.out.println("<tr><th>Rank</th><th>Title</th><th>Genres</th><th>Year</th><th>Rating</th></tr>");
            int count = 0;
            for (Rating rating : averageRatings) {
                String movieId = rating.getItem();
                if (!rater.hasRating(movieId)) {
                    for (Rating recRating : recommendedMovies) {
                        if (recRating.getItem().equals(movieId) && !rater.hasRating(movieId)) {
                            Movie movie = MovieDatabase.getMovie(movieId);
                            double value = rating.getValue();
                            value = Math.round(value * 100.0) / 100.0; // Round to two decimal places
                            System.out.print("<tr><td>" + (++count) + "</td>");
                            System.out.print("<td><a href=\"https://www.imdb.com/title/" + movieId + "/\">" + movie.getTitle() + "</a></td>");
                            System.out.print("<td>" + movie.getGenres() + "</td>");
                            System.out.print("<td>" + movie.getYear() + "</td>");
                            System.out.print("<td>" + value + "</td></tr>");
                        }
                    }
                    if (count == numMovies) break;
                }
            }
            System.out.println("</table>");
        }
    }
}
