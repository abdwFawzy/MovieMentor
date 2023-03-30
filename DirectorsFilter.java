import java.util.*;
public class DirectorsFilter implements Filter{
    private ArrayList<String> directors;
    public DirectorsFilter(ArrayList<String> directors)
    {
        this.directors = new ArrayList<String>(directors);
    }
    @Override
    public boolean satisfies(String id)
    {
        String[] movieDirectors = MovieDatabase.getDirector(id).split(",");
        for (String director : directors)
        {
            for (String movieDirector : movieDirectors)
            {
                if (director.equals(movieDirector.trim()))
                {
                    return true;
                }
            }
        }
        return false;
    }
}
