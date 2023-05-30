package model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Game {

    private final PlayingField playingField;
    private final Player playerOne;
    private final Player playerTwo;
    private Player currentPlayer;
    public boolean gameOver;
    private final List<Observer> observers;
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

    public String getPlayingFieldAsJson() {
        Token[][] matrix = playingField.getMatrix();
        int rows = matrix.length;
        int cols = matrix[0].length;
        List<Color> colors = new ArrayList<>();
        for (int col = 0; col < cols; col++) {
            for (Token[] tokens : matrix) {
                Token token = tokens[col];
                Color color = new Color(0, 0, 0);
                if (token != null) {
                    if (token == Token.X) {
                        color = new Color(255, 0, 0);
                    } else {
                        color = new Color(0, 0, 255);
                    }
                }
                colors.add(color);
            }
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(colors);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
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
