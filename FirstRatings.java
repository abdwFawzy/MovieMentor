import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;
public  class FirstRatings {
	private	String filenameShort;
	private	String filenameFull; 
	public ArrayList<Movie> shortMovies;
	private ArrayList<Movie> fullMovies;
    public HashSet<String> uniqueMovies;
    private ArrayList<EfficientRater>  raters;
	public FirstRatings(){
		filenameShort = "data/ratedmovies_short.csv";
		filenameFull = "data/ratedmoviesfull.csv";
	   	fullMovies = loadMovies(filenameFull);
	   	fullMovies = loadMovies(filenameShort);
        raters =  new ArrayList<EfficientRater>();
        raters = loadRaters("data/ratings.csv");
        uniqueMovies = new HashSet<String>();
        for (EfficientRater rater : raters) {
            ArrayList<String> ratedMovies = rater.getItemsRated();
            for (String movie : ratedMovies) {
                uniqueMovies.add(movie);
            }
       }
	}
//    private boolean checkRaters(ArrayList<EfficientRater> raters, String rater_id)
//    {
//        for (EfficientRater rater : raters)
//        {
//            if (rater.getID().equals(rater_id))
//                return true;
//                    
//        }
//        return false;
//    }
       public ArrayList<EfficientRater> loadRaters(String filename) {
            ArrayList<EfficientRater> raters = new ArrayList<EfficientRater>();
            FileResource fr = new FileResource(filename);
            CSVParser parser = fr.getCSVParser();
            for (CSVRecord record : parser) {
                String raterID = record.get("rater_id");
                String movieID = record.get("movie_id");
                double rating = Double.parseDouble(record.get("rating"));
                boolean alreadyAdded = false;
                for (EfficientRater rater : raters) {
                    if (rater.getID().equals(raterID)) {
                        rater.addRating(movieID, rating);
                        alreadyAdded = true;
                        break;
                    }
                }
                if (!alreadyAdded) {
                    EfficientRater rater = new EfficientRater(raterID);
                    rater.addRating(movieID, rating);
                    raters.add(rater);
                }
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
    public int get_movie_rating_count(String item)
    {
        FileResource fr = new FileResource(filenameFull);
        int count = 0;
		for (CSVRecord currRec : fr.getCSVParser())
		{
            if (item.equals(currRec.get("movie_id")))
                count++;
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
    public int find_number_of_ratings_for_rater(String rater_id)
    {
       int index = getEfficientRaterIndex(raters, rater_id);
       if (index != -1) 
           return raters.get(index).numRatings();
        return -1;
    }
    public HashMap<String, Integer> find_maximum_ratings_by_rater() {
        HashMap<String, Integer> ratersCount = new HashMap<String, Integer>();
        int maxCount = 0;
        for (EfficientRater rater : raters) {
            int numRatings = find_number_of_ratings_for_rater(rater.getID());
            ratersCount.put(rater.getID(), numRatings);
            maxCount = Math.max(maxCount, numRatings);
        }

        HashMap<String, Integer> maxCountEfficientRaters = get_raters_with_max_ratings(ratersCount, maxCount);

        System.out.println("The maximum number of ratings by any rater is: " + maxCount);
        System.out.println("There are " + maxCountEfficientRaters.size() + " raters with this maximum number of ratings."+ maxCountEfficientRaters.toString());

        return maxCountEfficientRaters;
    }
    private HashMap<String, Integer> get_raters_with_max_ratings(HashMap<String, Integer> ratersCount, int maxCount) {
        HashMap<String, Integer> maxCountEfficientRaters = new HashMap<String, Integer>();
        for (String raterID : ratersCount.keySet()) {
            int numRatings = ratersCount.get(raterID);
            if (numRatings == maxCount) {
                maxCountEfficientRaters.put(raterID, numRatings);
            }
        }
        return maxCountEfficientRaters;
    }
    private int getEfficientRaterIndex(ArrayList<EfficientRater>  raters, String raterID) 
    {
        for (int i = 0; i < raters.size(); i++) {
            if (raters.get(i).getID().equals(raterID)) {
                return i;
            }
        }
        return -1;
    }
    public ArrayList<Movie> loadMovies(String filename) {
        ArrayList<Movie> movieList = new ArrayList<Movie>();
        FileResource fr = new FileResource(filename);
        CSVParser parser = fr.getCSVParser();
        
        for (CSVRecord record : parser) {
            String id = record.get("id");
            String title = record.get("title");
            String year = record.get("year");
            String genres = record.get("genre");
            String director = record.get("director");
            String country = record.get("country");
            String poster = record.get("poster");
            int minutes = Integer.parseInt(record.get("minutes"));
            
            Movie movie = new Movie(id, title, year, genres, director, country, poster, minutes);
            movieList.add(movie);
        }
        
        return movieList;
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
    public void testLoadEfficientRaters() {
        System.out.println("Total number of raters: " + raters.size());

        // Print each rater's ID and ratings
        for (EfficientRater rater : raters) {
            System.out.println("EfficientRater ID: " + rater.getID() + " | Number of ratings: " + rater.numRatings());
            for (String item : rater.getItemsRated()) {
                System.out.println("Movie ID: " + item + " | Rating: " + rater.getRating(item));
            }
            System.out.println();
        }

        // Find number of ratings for a particular rater
        int numRatingsForEfficientRater2 = find_number_of_ratings_for_rater("2");
        System.out.println("Number of ratings for rater 2: " + numRatingsForEfficientRater2);

        // Find maximum number of ratings by any rater
        find_maximum_ratings_by_rater();

        // Find number of ratings for a particular movie
        int numRatingsForMovie1798709 = get_movie_rating_count("data/ratings.csv", "1798709");
        System.out.println("Number of ratings for movie 1798709: " + numRatingsForMovie1798709);

        // Find number of different movies rated by all raters
        // Create a HashSet to store unique movie IDs
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
}
