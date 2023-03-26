import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;
public class FirstRatings {
	private	String filenameShort;
	private	String filenameFull; 
	private ArrayList<Movie> shortMovies;
	private ArrayList<Movie> fullMovies;
	public FirstRatings(){
		filenameShort = "data/ratedmovies_short.csv";
		filenameFull = "data/ratedmoviesfull.csv";
	    	shortMovies = loadMovies(filenameShort);
	    	fullMovies = loadMovies(filenameFull);
	}
//    private boolean checkRaters(ArrayList<Rater> raters, String rater_id)
//    {
//        for (Rater rater : raters)
//        {
//            if (rater.getID().equals(rater_id))
//                return true;
//                    
//        }
//        return false;
//    }
    public  ArrayList<Rater> loadRaters(String FileName)
    {
		ArrayList<Rater> raters = new ArrayList<Rater>();
        FileResource fr = new FileResource(FileName);
		for (CSVRecord currRec : fr.getCSVParser())
		{
            String rater_id = currRec.get("rater_id");
            // you need to check if that rater_id is in raters or not 
            Rater rater;
            int index = getRaterIndex(raters, rater_id);
            if (index == -1)
            {
                rater =  new Rater(rater_id);
                raters.add(rater);
                index = getRaterIndex(raters, rater_id);
            }
            else
            {
                index =  getRaterIndex(raters, rater_id);
            }
            String movie = currRec.get("movie_id");
            double rating = Double.parseDouble(currRec.get("rating"));
            raters.get(index).addRating(movie, rating);
		}
		return raters;
    }
    public int count_movies(String FileName)
    {
        ArrayList<String> movies = new ArrayList<String>();
        int count = 0;
        FileResource fr = new FileResource(FileName);
		for (CSVRecord currRec : fr.getCSVParser())
		{
            if (!movies.contains(currRec.get("movie_id")))
            {
                movies.add(currRec.get("movie_id"));
                count = get_movie_rating_count(FileName, currRec.get("movie_id"));
            }
                
        }
        return count;
    }
    public int get_movie_rating_count(String FileName, String item)
    {
        FileResource fr = new FileResource(FileName);
        int count = 0;
		for (CSVRecord currRec : fr.getCSVParser())
		{
            if (item.equals(currRec.get("movie_id")))
                count++;
        }
        return count;
    }
    public int find_number_of_ratings_for_rater(ArrayList<Rater> raters, String rater_id)
    {
       int index = getRaterIndex(raters, rater_id);
       if (index != -1) 
           return raters.get(index).numRatings();
        return -1;
    }
    public HashMap<String, Integer> find_maximum_ratings_by_rater(ArrayList<Rater> raters) {
        HashMap<String, Integer> ratersCount = new HashMap<String, Integer>();
        int maxCount = 0;
        for (Rater rater : raters) {
            int numRatings = find_number_of_ratings_for_rater(raters, rater.getID());
            ratersCount.put(rater.getID(), numRatings);
            maxCount = Math.max(maxCount, numRatings);
        }

        HashMap<String, Integer> maxCountRaters = get_raters_with_max_ratings(ratersCount, maxCount);

        System.out.println("The maximum number of ratings by any rater is: " + maxCount);
        System.out.println("There are " + maxCountRaters.size() + " raters with this maximum number of ratings."+ maxCountRaters.toString());

        return maxCountRaters;
    }
    private HashMap<String, Integer> get_raters_with_max_ratings(HashMap<String, Integer> ratersCount, int maxCount) {
        HashMap<String, Integer> maxCountRaters = new HashMap<String, Integer>();
        for (String raterID : ratersCount.keySet()) {
            int numRatings = ratersCount.get(raterID);
            if (numRatings == maxCount) {
                maxCountRaters.put(raterID, numRatings);
            }
        }
        return maxCountRaters;
    }
    private int getRaterIndex(ArrayList<Rater> raters, String raterID) 
    {
        for (int i = 0; i < raters.size(); i++) {
            if (raters.get(i).getID().equals(raterID)) {
                return i;
            }
        }
        return -1;
    }
    public  ArrayList<Movie> loadMovies(String FileName)
	{
		ArrayList<Movie> movies = new ArrayList<Movie>();
		FileResource fr = new FileResource(FileName);
		for (CSVRecord currRec : fr.getCSVParser())
		{
			movies.add(new Movie(currRec));
		}
		return movies;
	}
	public HashMap<String, ArrayList<Movie>> directorFilter() {
	    HashMap<String, ArrayList<Movie>> directorsMap = new HashMap<String, ArrayList<Movie>>();
	    HashMap<String, Integer> directorCount = new HashMap<String, Integer>();
	    int maxCount = 0;
	    for (Movie movie : fullMovies) {
		for (String director : movie.getDirector().split(",")) {
		    if (directorsMap.containsKey(director)) {
			directorsMap.get(director).add(movie);
		    } else {
			directorsMap.put(director, new ArrayList<Movie>());
			directorsMap.get(director).add(movie);
		    }
		    int count = directorCount.getOrDefault(director, 0) + 1;
		    directorCount.put(director, count);
		    maxCount = Math.max(maxCount, count);
		}
	    }
	    HashMap<String, ArrayList<Movie>> maxDirectorsMap = new HashMap<String, ArrayList<Movie>>();
	    for (String director : directorCount.keySet()) {
		int count = directorCount.get(director);
		if (count == maxCount) {
		    maxDirectorsMap.put(director, directorsMap.get(director));
		}
	    }
	    System.out.println("Maximum number of movies by any director: " + maxCount);
	    System.out.println("Directors who directed that many movies:");
	    for (String director : maxDirectorsMap.keySet()) {
		System.out.println("- " + director);
	    }
	    return directorsMap;
	}
	public ArrayList<Movie> minutesFilter(int length)
	{
		ArrayList<Movie> movies = new ArrayList<Movie>();
		for (Movie movie : fullMovies)
		{
			if (movie.getMinutes() > length)
				movies.add(movie);
		}
		return movies;
	}
	public ArrayList<Movie> genreFilter(String factor)
	{
		ArrayList<Movie> movies = new ArrayList<Movie>();
		for (Movie movie : fullMovies)
		{
			if (movie.getGenres().indexOf(factor) != -1)
				movies.add(movie);
		}
		return movies;
	}
    public void testLoadRaters() {
        ArrayList<Rater> raters = loadRaters("data/ratings.csv");
        System.out.println("Total number of raters: " + raters.size());

        // Print each rater's ID and ratings
        for (Rater rater : raters) {
            System.out.println("Rater ID: " + rater.getID() + " | Number of ratings: " + rater.numRatings());
            for (String item : rater.getItemsRated()) {
                System.out.println("Movie ID: " + item + " | Rating: " + rater.getRating(item));
            }
            System.out.println();
        }

        // Find number of ratings for a particular rater
        int numRatingsForRater2 = find_number_of_ratings_for_rater(raters, "2");
        System.out.println("Number of ratings for rater 2: " + numRatingsForRater2);

        // Find maximum number of ratings by any rater
        find_maximum_ratings_by_rater(raters);

        // Find number of ratings for a particular movie
        int numRatingsForMovie1798709 = get_movie_rating_count("data/ratings.csv", "1798709");
        System.out.println("Number of ratings for movie 1798709: " + numRatingsForMovie1798709);

        // Find number of different movies rated by all raters
        // Create a HashSet to store unique movie IDs
        HashSet<String> uniqueMovies = new HashSet<String>();
        for (Rater rater : raters) {
            ArrayList<String> ratedMovies = rater.getItemsRated();
            for (String movie : ratedMovies) {
                uniqueMovies.add(movie);
       }
}
System.out.println("The number of different movies rated is: " + uniqueMovies.size());
    }
	public void testLoadMovies() {
	    // Load short movies file
	    ArrayList<Movie> fullMovies = loadMovies(filenameFull);
	    System.out.println("Number of movies in " + filenameFull + ": " + fullMovies.size());
	    
	    // Print each movie
//	    for (Movie movie : fullMovies) {
//		System.out.println(movie);
//	    }
	    
	    // Test directorFilter method
	    HashMap<String, ArrayList<Movie>> directorsMap = directorFilter();
	    System.out.println("Number of directors: " + directorsMap.size());
	    for (String director : directorsMap.keySet()) {
		System.out.println(director + ": " + directorsMap.get(director).size() + " movies");
	    }
	    
	    // Test minutesFilter method
	    int length = 150;
	    ArrayList<Movie> longMovies = minutesFilter(length);
	    System.out.println("Number of movies longer than " + length + " minutes: " + longMovies.size());
	    
	    // Test genreFilter method
	    String factor = "Comedy";
	    ArrayList<Movie> comedyMovies = genreFilter(factor);
	    System.out.println("Number of " + factor + " movies: " + comedyMovies.size());
	}
public static void main(String[] args) 
	{
		FirstRatings fr = new FirstRatings();
		fr.testLoadMovies();
        fr.testLoadRaters();
   	}
}
