package network;

import Gui.GameWindow.ButtonObserver;
import Gui.GameWindow.GameWindow;
import model.Token;

public class Client2 implements Runnable, ButtonObserver {

    GameWindow gameWindow;
    boolean keepAlive;
    boolean receivedMsg;
    int lastUserAction = -1;

    public Client2(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        keepAlive = true;//set this to false once the connection is close
        receivedMsg = false;//set this to true once a new unprocessed msg was received
    }

    @Override
    public void run() {
        while (keepAlive) {
            //Do the socket thing
            String msg = "";//read from Buffer
            boolean isWaitingForAnswerTurn = true;//Read from Msg
            if (receivedMsg) {
                Token[][] matrix = parseMatrixFromMsg(msg);
                gameWindow.updateMatrix(matrix);
                int answer;
                if (isWaitingForAnswerTurn) {
                    gameWindow.setActive(true);
                    while (true) {
                        if (lastUserAction != -1) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            answer = lastUserAction;
                            gameWindow.setActive(false);
                            lastUserAction = -1;
                            break;
                        }
                    }
                    sendAnswer();//Send Answer to Client
                }
            }
        }
    }

    //do some sending
    private void sendAnswer() {
    }

    //Parse Msg from String or whatever is sent through network
    private Token[][] parseMatrixFromMsg(String msg) {
        return null;
    }

    @Override
    public void notify(int selection) {
        lastUserAction = selection;
    }
}
