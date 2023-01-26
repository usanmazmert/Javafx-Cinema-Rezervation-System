

import java.util.HashMap;

public abstract class Cinema {
    private static HashMap<String, Hall> halls = new HashMap<String, Hall>();
    public static HashMap<String, Hall> getHalls(){
        return halls;
    }
}
