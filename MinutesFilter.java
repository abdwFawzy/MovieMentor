import java.util.*;
public class MinutesFilter implements Filter{
    private int minMinuts;    
    private int maxMinuts;    
    public MinutesFilter(int minMinuts,int maxMinuts)
    {
        this.minMinuts = minMinuts;
        this.maxMinuts = maxMinuts;
    }
    @Override
    public boolean satisfies(String id)
    {
        int time = MovieDatabase.getMinutes(id);
        if (time >= minMinuts && time <= maxMinuts)
            return true;
        return false;
    }
}
