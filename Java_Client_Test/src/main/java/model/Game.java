package model;

import java.util.ArrayList;
import java.util.List;

public class Game {

    PlayingField playingField;
    Player playerOne;
    Player playerTwo;
    Player currentPlayer;
    boolean gameOver;
    List<Observer> observers;
    public Game(String namePlayerOne, String namePlayerTwo) {
        playerOne = new Player(namePlayerOne,Token.X);
        playerTwo = new Player(namePlayerTwo,Token.O);
        currentPlayer = playerOne;
        playingField = new PlayingField();
        gameOver = false;
        this.observers = new ArrayList<>();
    }

    public void subscribe(Observer observer){
        observers.add(observer);
    }
    public void move(int col) throws InvalidMoveException{
        if(gameOver) throw new InvalidMoveException("Cant make a move, the game has ended");
        if(!playingField.makeMove(currentPlayer.getToken(),col)){
            throw new InvalidMoveException("Column already full!");
        }
        if(playingField.connectedFour()){
            notifyOberserversGameOver();
            gameOver = true;
        }else{
            notifyObserversTurnHappend();
            changeTurn();
        }
    }

    private void notifyObserversTurnHappend() {
        for (var o:observers
        ) {
            o.updateMove();
        }
    }

    private void notifyOberserversGameOver() {
        for (var o: observers
             ) {
            o.notifyWin(currentPlayer.getName());
        }
    }

    public String getCurrentPlayer() {
        return currentPlayer.name;
    }

    public Token[][] getPlayingField(){
        return playingField.getMatrix();
    }
    public void resetGame(){
        if(currentPlayer.equals(playerOne)){
            currentPlayer = playerTwo;
        }else{
            currentPlayer = playerOne;
        }
        playingField.resetMatrix();
        gameOver = false;
        notifyObserversTurnHappend();
    }

    private void changeTurn(){
        if(currentPlayer.equals(playerOne)) currentPlayer = playerTwo;
        else currentPlayer = playerOne;
    }
}
