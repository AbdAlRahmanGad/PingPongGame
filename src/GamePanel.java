import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import static java.lang.Math.abs;

public class GamePanel extends JPanel implements Runnable {
    private final int panelWidth = 700;
    private final int panelHeight = 400;

    private final int strockHeight = panelHeight / 5;
    private final int strockWidth = 10;
    private int strock2X = panelWidth - 30;
    private int strock2Y = panelHeight / 2;
    private int strock1Y = panelHeight / 2;
    private int strock1X = 20;
    private int movement = 5;
    private int playerOneScore = 0;
    private int playerTwoScore = 0;

    private int ballMovementX = 3;
    private int ballMovementY = 5;
    private int ballX = panelWidth / 2 - 7;
    private int fontSize = 40;
    private int ballY = panelHeight / 2 - 7;
    private final int ballWidth = 15;
    private final int ballHeight = 15;

    private int FPS = 60;
    private int padding = ballMovementX * 2;

    private Thread myGame;
    private final int screenMode;

    private boolean isPlayerOneUp, isPlayerOneDown, isPlayerTwoUp, isPlayerTwoDown;

    GamePanel(int scrMode) {
        controls();
        screenMode = scrMode;
        if (screenMode == 1) {
            setBackground(Color.black);
        } else {
            setBackground(Color.WHITE);
        }
        setPreferredSize(new Dimension(panelWidth, panelHeight));
        startGameThread();
        this.setFocusable(true);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (screenMode == 1) {
            g2.setColor(Color.BLACK);
            g2.setColor(Color.WHITE);
        } else {
            g2.setColor(Color.WHITE);
            g2.setColor(Color.BLACK);
        }

        g2.fillRect(strock1X, strock1Y, strockWidth, strockHeight);
        g2.fillRect(strock2X, strock2Y, strockWidth, strockHeight);
        g2.fillRect(ballX, ballY, ballWidth, ballHeight);
        g2.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
        g2.drawString(String.valueOf(playerOneScore), panelWidth / 2 - 60, fontSize + 10);
        g2.drawString(String.valueOf(playerTwoScore), panelWidth / 2 + 40, fontSize + 10);
        g2.drawLine(panelWidth / 2, 0, panelWidth / 2, panelHeight);
    }

    public void startGameThread() {
        myGame = new Thread(this);
        this.setFocusable(true);
        myGame.start();
    }

    @Override
    public void run() {
        double drawiInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        while (myGame != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawiInterval;

            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }

    }

    public void update() {
        if (isPlayerOneUp) {
            if (strock1Y > 0)
                strock1Y -= movement;
        }
        if (isPlayerOneDown) {
            if (strock1Y < panelHeight - strockHeight)
                strock1Y += movement;
        }
        if (isPlayerTwoUp) {
            if (strock2Y > 0)
                strock2Y -= movement;
        }
        if (isPlayerTwoDown) {
            if (strock2Y < panelHeight - strockHeight)
                strock2Y += movement;
        }
        moveBall();
    }

    private void moveBall() {
//        ballMovement
        ballX += ballMovementX;
        ballY += ballMovementY;
//        gameBounds
        if (ballY + ballHeight >= panelHeight) {
            ballMovementY = -abs(ballMovementY);
        } else if (ballY < 0) {
            ballMovementY = abs(ballMovementY);
        }

        movePlayerOne();
        movePlayerTwo();
        // player1  Gaol
        if (ballX + ballWidth <= 0) {
            playerTwoScored();
        }
        // player2 Gaol
        if (ballX >= panelWidth) {
            playerOneScored();
        }
        // game End
        if (playerOneScore == 10) {
            endGame(1);
        } else if (playerTwoScore == 10) {
            endGame(2);
        }
    }

    private void endGame(int winner) {
//        winner = 1 || 2
        myGame = null;
        Main.makeFrame(winner);
    }

    private void playerOneScored() {
        if (playerTwoScore == 0 && playerOneScore == 0) {
            movement = 8;
            ballMovementX = 5;
            ballMovementY = 5;
        }
        playerOneScore++;
        ballX = panelWidth / 2;
    }

    private void playerTwoScored() {
        if (playerTwoScore == 0 && playerOneScore == 0) {
            movement = 8;
            ballMovementX = 5;
            ballMovementY = 5;
        }
        playerTwoScore++;
        ballX = panelWidth / 2 - ballWidth;
    }

    private void movePlayerTwo() {
        //Payer 2 controls
        if (ballX + ballWidth >= strock2X
                && ballX + ballWidth <= strock2X + ballMovementX
                && (ballY >= strock2Y || ballY + ballHeight >= strock2Y)
                && ballY <= strock2Y + strockHeight) {
            ballMovementX *= -1;
            ///// KISS keep it simple
            if (ballY + ballHeight < strock2Y + (strockHeight / 2)) {
                ballMovementY = -abs(ballMovementY);
            } else {
                ballMovementY = abs(ballMovementY);
            }

            ///up

        } else if (ballX + ballWidth >= strock2X && ballX + ballWidth <= strock2X + strockWidth
                && (ballY + ballHeight >= strock2Y) &&
                ballY + ballHeight <= strock2Y + ballMovementY) {
            ballMovementX *= -1;
            ballMovementY = -abs(ballMovementY);

        } else if (ballX + ballWidth >= strock2X + strockWidth
                && (ballY + ballHeight >= strock2Y) &&
                ballY + ballHeight <= strock2Y + ballMovementY) {

            ballMovementY = -abs(ballMovementY);
        }
        ///down

        else if (ballX + ballWidth >= strock2X && ballX + ballWidth <= strock2X + strockWidth
                &&
                ballY >= strock2Y + strockHeight - padding
                &&
                ballY <= strock2Y + strockHeight) {

            ballMovementX *= -1;
            ballMovementY = abs(ballMovementY);
        } else if (ballX + ballWidth >= strock2X + strockWidth
                &&
                ballY >= strock2Y + strockHeight - padding
                &&
                ballY <= strock2Y + strockHeight) {

            ballMovementY = abs(ballMovementY);
        }
    }

    private void movePlayerOne() {
        if (ballX >= strock1X + strockWidth - ballMovementX && ballX - ballWidth <= strock1X && (ballY >= strock1Y || ballY + ballHeight >= strock1Y) && ballY <= strock1Y + strockHeight) {
            ballMovementX *= -1;
            if (ballY + ballHeight < strock1Y + (strockHeight / 2)) {
                ballMovementY = -abs(ballMovementY);
            } else {
                ballMovementY = abs(ballMovementY);
            }

        } else if (ballX >= strock1X && ballX <= strock1X + strockWidth &&
                ballY >= strock1Y + strockHeight - padding &&
                ballY <= strock1Y + strockHeight) {
            ballMovementX *= -1;
            ballMovementY = abs(ballMovementY);
        } else if (ballX <= strock1X
                &&
                ballY >= strock1Y + strockHeight - padding
                &&
                ballY <= strock1Y + strockHeight) {

            ballMovementY = abs(ballMovementY);

        } else if (ballX >= strock1X && ballX <= strock1X + strockWidth
                && (ballY + ballHeight >= strock1Y) &&
                ballY + ballHeight <= strock1Y + ballMovementY) {
            ballMovementX *= -1;
            if (ballY + ballHeight < strock1Y + (strockHeight / 2)) {
                ballMovementY = -abs(ballMovementY);
            } else {
                ballMovementY = abs(ballMovementY);
            }
        } else if (ballX <= strock1X &&
                (ballY + ballHeight >= strock1Y) &&
                ballY + ballHeight <= strock1Y + ballMovementY) {
            ballMovementY = -abs(ballMovementY);

        }
    }

    private void controls() {
        InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getActionMap();
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0, false), "p1UpPressed");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0, true), "p1UpReleased");
        am.put("p1UpPressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isPlayerOneUp = true;
            }
        });
        am.put("p1UpReleased", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isPlayerOneUp = false;
            }
        });
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "p1DownPressed");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "p1DownReleased");
        am.put("p1DownPressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isPlayerOneDown = true;
            }
        });
        am.put("p1DownReleased", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isPlayerOneDown = false;
            }
        });
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0, false), "p2UpPressed");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0, true), "p2UpReleased");
        am.put("p2UpPressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isPlayerTwoUp = true;
            }
        });
        am.put("p2UpReleased", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isPlayerTwoUp = false;
            }
        });
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_L, 0, false), "p2DownPressed");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_L, 0, true), "p2DownReleased");
        am.put("p2DownPressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isPlayerTwoDown = true;
            }
        });
        am.put("p2DownReleased", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isPlayerTwoDown = false;
            }
        });


    }

}