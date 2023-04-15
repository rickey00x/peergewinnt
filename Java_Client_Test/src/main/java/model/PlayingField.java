package model;

import java.util.Arrays;

public class PlayingField {

    final Token[][] matrix;

    //Does Playingfield need to know who won?

    public PlayingField() {
        matrix = new Token[7][6];
        setupMatrix();
    }

    public boolean makeMove(Token token, int row){
        for(int i=0;i<6;i++){
            if(matrix[i][row]!= Token.NONE) continue;
            matrix[i][row] = token;
            return true;
        }
        return false;
    }

    public boolean connectedFour(){
        // Check horizontal
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length - 3; col++) {
                if (matrix[row][col] != Token.NONE && matrix[row][col] == matrix[row][col+1] &&
                        matrix[row][col] == matrix[row][col+2] && matrix[row][col] == matrix[row][col+3]) {
                    return true;
                }
            }
        }
        // Check vertical
        for (int row = 0; row < matrix.length - 3; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                if (matrix[row][col] != Token.NONE && matrix[row][col] == matrix[row+1][col] &&
                        matrix[row][col] == matrix[row+2][col] && matrix[row][col] == matrix[row+3][col]) {
                    return true;
                }
            }
        }
        // Check diagonal from top-left to bottom-right
        for (int row = 0; row < matrix.length - 3; row++) {
            for (int col = 0; col < matrix[row].length - 3; col++) {
                if (matrix[row][col] != Token.NONE && matrix[row][col] == matrix[row+1][col+1] &&
                        matrix[row][col] == matrix[row+2][col+2] && matrix[row][col] == matrix[row+3][col+3]) {
                    return true;
                }
            }
        }
        // Check diagonal from bottom-left to top-right
        for (int row = 3; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length - 3; col++) {
                if (matrix[row][col] != Token.NONE && matrix[row][col] == matrix[row-1][col+1] &&
                        matrix[row][col] == matrix[row-2][col+2] && matrix[row][col] == matrix[row-3][col+3]) {
                    return true;
                }
            }
        }
        // No winner yet
        return false;
    }

    public Token[][] getMatrix() {
        return matrix;
    }

    public void resetMatrix(){
        setupMatrix();
    }

    private void setupMatrix() {
        for (var e : matrix
        ) {
            Arrays.fill(e, Token.NONE);
        }
    }
}
