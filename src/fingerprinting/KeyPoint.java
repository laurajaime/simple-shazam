package fingerprinting;

import java.io.Serializable;

public class KeyPoint implements Serializable{
    
    private final String songId;
    private final int timestamp;
    
    public KeyPoint(String songId, int timestamp){
        this.songId = songId;
        this.timestamp = timestamp;
    }

    public String getSongId() {
        return songId;
    }

    public int getTimestamp() {
        return timestamp;
    }
}
