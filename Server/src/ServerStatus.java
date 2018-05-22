import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ServerStatus {
    private int players = 0;
    public boolean isGameStarted = false;

    public void increasePlayers() {
        players++;
    }

    public void decreasePlayers() {
        players--;
    }

    public int getPlayers() {
        return players;
    }

    public Map<Integer, Socket> ClientMap = new HashMap(8);
}
