public class ServerStatus {
    private int players = 0;
    public boolean isGameStarted = false;
    private int Bank = 0;

    public void increasePlayers() {
        players++;
    }

    public void decreasePlayers() {
        players--;
    }

    public int getPlayers() {
        return players;
    }

    public void increaseBank(int money) {
        Bank = Bank + money;
    }

    public void decreaseBank(int money) {
        Bank = Bank - money;
    }

    public void setBank(int money){
        Bank = money;
    }
}
