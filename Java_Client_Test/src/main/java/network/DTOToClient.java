package network;

import model.Token;

import java.io.Serializable;

public record DTOToClient(boolean sendAnswer,boolean gameOver, Token[][] matrix) implements Serializable {
}
