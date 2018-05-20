public class ServerStatus {
    private int players = 0;

    public void increasePlayers(){
        players++;
    }

    public void decreasePlayers(){
        players--;
    }

    public int getPlayers(){ return players;}

}
