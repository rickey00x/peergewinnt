package model;

public interface Observer {
    void updateMove();
    void notifyWin(String playerName);
}
