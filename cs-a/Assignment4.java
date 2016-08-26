package com.shpp.dmarkov.cs;

/**
 * Created by Denis on 22.05.2016.
 */

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.util.RandomGenerator;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;
import java.awt.event.MouseEvent;

/* Breakout game */
public class Assignment4 extends WindowProgram {
    /**
     * Width and height of application window in pixels
     */
    public static final int APPLICATION_WIDTH = 400;
    public static final int APPLICATION_HEIGHT = 600;

    /**
     * Dimensions of game board (usually the same)
     */
    private static final int WIDTH = APPLICATION_WIDTH;
    private static final int HEIGHT = APPLICATION_HEIGHT;

    /**
     * Dimensions of the paddle
     */
    private static final int PADDLE_WIDTH = 60;
    private static final int PADDLE_HEIGHT = 10;

    /**
     * Offset of the paddle up from the bottom
     */
    private static final int PADDLE_Y_OFFSET = 30;

    /**
     * Number of bricks per row
     */
    private static final int NBRICKS_PER_ROW = 10;

    /**
     * Number of rows of bricks
     */
    private static final int NBRICK_ROWS = 10;

    /**
     * Separation between bricks
     */
    private static final int BRICK_SEP = 4;

    /**
     * Width of a brick
     */
    private static final int BRICK_WIDTH =
            (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

    /**
     * Height of a brick
     */
    private static final int BRICK_HEIGHT = 8;

    /**
     * Radius of the ball in pixels
     */
    private static final int BALL_RADIUS = 10;

    /**
     * Offset of the top brick row from the top
     */
    private static final int BRICK_Y_OFFSET = 70;

    /**
     * Number of turns
     */
    private static final int NTURNS = 3;

    /**
     * Radius attempt ball in right corner
     **/
    private static final int NTURNS_BALL_RADIUS = 6;

    /**
     * Distance between attempt balls
     **/
    private static final int NTURNS_BALL_SEP = 8;

    /**
     * Timeout between ball moves
     **/
    private static final double ballSpeed = 20.0;

    /**
     * Delta y at start game
     **/
    private static final double BALL_START_DELTA_Y = 3.0;

    /**
     * Margin new game label from y center
     **/
    private static final double NEW_GAME_LABEL_Y_MARGIN = 3 * BALL_RADIUS;

    /**
     * Margin result game label from y center
     **/
    private static final double RESULT_GAME_LABEL_Y_MARGIN =  - BALL_RADIUS;

    /**
     * Paddle coordinates, initialized in class, because set in method addPaddle() and used
     * in mouseMoved method, which not called from other method in this class.
     */
    private double paddleX, paddleY;

    /* In this method run endless loop, for start new game after end previous */
    public void run() {
        while (true) {
            addPaddle(); //setup paddle in the middle of program on X coordinate and predetermined Y coordinate
            addBricks(); //draw bricks
            drawCountRemainedAttempts(); // draw balls in right bottom corner, which represent count user attempts
            addMouseListeners(); // super method, that responds mouse
            ballMove(); // run game
        }
    }

    /* Listen for mouse moved and change paddle position */
    @Override
    public void mouseMoved(MouseEvent e) {
        GObject paddle = getElementAt(paddleX, paddleY);
        if (paddle != null) {
            paddleX = e.getX() - paddle.getWidth() / 2.0;
            /* Inspection mouse position and while it out from game window, set paddle in side of program*/
            if (getWidth() - e.getX() - PADDLE_WIDTH / 2 < 0) {
                paddleX = getWidth() - PADDLE_WIDTH;
            }
            if ((e.getX() - PADDLE_WIDTH / 2) < 0) {
                paddleX = 0;
            }
            paddle.setLocation(paddleX, paddleY);
        }
    }

    /* Created rect, which use as paddle */
    private void addPaddle() {
        paddleX = getWidth() / 2 - PADDLE_WIDTH / 2;
        paddleY = getHeight() - PADDLE_Y_OFFSET;
        GRect paddle = addRect(paddleX, paddleY, PADDLE_WIDTH, PADDLE_HEIGHT, Color.BLACK);
        add(paddle);
    }

    /* Draw rect with preset params of coordinates, width, height and color */
    private GRect addRect(double rectX, double rectY, int rectWidth, int rectHeight, Color rectColor) {
        GRect rect = new GRect(rectX, rectY, rectWidth, rectHeight);
        rect.setFilled(true);
        rect.setFillColor(rectColor);
        return rect;
    }

    /* Creat ball in the middle of game window */
    private GOval addBall() {
        double ballX = getWidth() / 2 - BALL_RADIUS;
        double ballY = getHeight() / 2 - BALL_RADIUS;
        GOval ball = new GOval(ballX, ballY, 2 * BALL_RADIUS, 2 * BALL_RADIUS);
        ball.setFilled(true);
        ball.setFillColor(Color.BLACK);
        return ball;
    }

    /* Run ball moves and events when ball touch for something */
    private void ballMove() {
        /* Initialized users score, attempts and add ball */
        int bricksCount = NBRICKS_PER_ROW * NBRICK_ROWS;
        int score = 0;
        RandomGenerator rgen = RandomGenerator.getInstance();
        GOval ball = addBall();
        GLabel scoreLabel = drawScoreLabel(String.valueOf(score));
        add(scoreLabel);
        /* Loop for each users attempts */
        for (int remainedAttempts = NTURNS; remainedAttempts > 0; remainedAttempts--) {
            add(ball); // create game Ball
            /* Get ball coordinates */
            waitForClick(); // stop program until user click, ball run after click
            /* Set start ball course */
            double vx = rgen.nextDouble(1.0, 3.0);
            if (rgen.nextBoolean(0.5)) {
                vx = -vx;
            }
            double vy = BALL_START_DELTA_Y;
            GObject paddle = getElementAt(paddleX, paddleY); //get paddle
            /* Move ball until user have attempts and bricks !=0 */
            while (true) {
                /* Reverse ball course when it touch to walls */
                if ((ball.getX() + 2 * BALL_RADIUS) > getWidth() || ball.getX() < 0) {
                    vx = -vx;
                }
                if (ball.getY() <= 0) {
                    vy = -vy;
                }
                if(loseBall(ball, remainedAttempts)) break;
                /* Move ball */
                ball.move(vx, vy);
                pause(ballSpeed);
                /* Reverse ball course when it touch to paddle or bricks */
                GObject collider = getCollidingObject(ball.getX(), ball.getY());
                if (collider != null && collider.equals(paddle)) {
                    if (vy > 0) {
                        vy = -vy;
                    }
                } else {
                    if (collider != null) {
                        remove(collider); // remove brick
                        vy = -vy;
                        score = scorePlus(collider, score); //increases user score
                        scoreLabel.setLabel(String.valueOf(score)); // change score on label
                        bricksCount--;
                        if(win(bricksCount)) break;
                    }
                }
            }
            if (bricksCount == 0) { // end attempt loop when user win
                break;
            }
        }
        /* when user win or lose show label whith game result and erase all object, prepare to a new game */
        GLabel newGameLabel = drawGameResultLabel("Click twice to start a new game", NEW_GAME_LABEL_Y_MARGIN);
        add(newGameLabel);
        waitForClick(); // stop loop and start new game when user click
        removeAll();
    }

    /* end game when user win */
    private boolean win(int bricksCount) {
        if (bricksCount == 0) {
            GLabel win = drawGameResultLabel("You win!", RESULT_GAME_LABEL_Y_MARGIN);
            add(win);
            return true;
        }
        return false;
    }

    /* End attempt when balls go under flo and end game when all attempts is used */
    private boolean loseBall(GOval ball, int remainedAttempts) {
        if ((ball.getY()) > getHeight()) {
            ball.setLocation(getWidth() / 2 - BALL_RADIUS, getHeight() / 2 - BALL_RADIUS);
            removeAttemptsBall(remainedAttempts);
            if (remainedAttempts == 1) {
                GLabel lose = drawGameResultLabel("You lose!", RESULT_GAME_LABEL_Y_MARGIN);
                add(lose);
            }
            return true; // start next attempt or end game when all attempts is used
        }
        return false;
    }

        /* Found object which ball touch to */
    private GObject getCollidingObject(double ballX, double ballY) {
        /* Check ball is higher then paddle */
        if ((ballY + 2 * BALL_RADIUS) <= paddleY + PADDLE_HEIGHT) {
            if (getElementAt(ballX, ballY) != null) {
                return getElementAt(ballX, ballY);
            }
            if (getElementAt(ballX + 2 * BALL_RADIUS, ballY) != null) {
                return getElementAt(ballX + 2 * BALL_RADIUS, ballY);
            }
            if (getElementAt(ballX, ballY + 2 * BALL_RADIUS) != null) {
                return getElementAt(ballX, ballY + 2 * BALL_RADIUS);
            }
            if (getElementAt(ballX + 2 * BALL_RADIUS, ballY + 2 * BALL_RADIUS) != null) {
                return getElementAt(ballX + 2 * BALL_RADIUS, ballY + 2 * BALL_RADIUS);
            }
        }
        return null;
    }

    /* Draw bricks table */
    private void addBricks() {
        double brickY = BRICK_Y_OFFSET;
        /* Setup preset color in massive and use it to set bricks color */
        Color brickColors[] = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN};
        int j = 0; // bricks line color index
        for (int i = 1; i <= NBRICKS_PER_ROW; i++) {
            drawBricksLines(brickY, brickColors[j]);
            brickY += BRICK_HEIGHT + BRICK_SEP;
            if (i % 2 == 0) { // change bricks line color every two lines
                j++;
            }
        }
    }

    /* Draw bricks line */
    private void drawBricksLines(double brickY, Color color) {
        double brickX = BRICK_SEP / 2;
        for (int i = 0; i < NBRICK_ROWS; i++) {
            GRect brick = addRect(brickX, brickY, BRICK_WIDTH, BRICK_HEIGHT, color);
            add(brick);
            brickX += BRICK_WIDTH + BRICK_SEP;
        }
    }

    /* Set user score label to actual score and place it in the middle game window under the paddle */
    private GLabel drawScoreLabel(String text) {
        GLabel scoreLabel = label(text);
        double scoreLabelX = getWidth() / 2 - scoreLabel.getWidth() / 2;
        double scoreLabelY = getHeight() - scoreLabel.getHeight() / 2;
        scoreLabel.setLocation(scoreLabelX, scoreLabelY);
        return scoreLabel;
    }

    /* Increase user score depending on the color of bricks */
    private int scorePlus(GObject brick, int score) {
        double bricksLineY = BRICK_Y_OFFSET + BRICK_HEIGHT + BRICK_SEP;
        for (int i = 5; i > 0; i--) {
            if (brick.getY() <= bricksLineY) {
                score += i;
                return score;
            }
            bricksLineY += 2 * (BRICK_HEIGHT + BRICK_SEP);
        }
        return score;
    }

    /* Create label whits message of the end game and place it higher ball start position */
    private GLabel drawGameResultLabel(String text, double margin) {
        GLabel result = label(text);
        double resultX = getWidth() / 2 - result.getWidth() / 2;
        double resultY = getHeight() / 2 + margin - result.getHeight() / 2;
        result.setLocation(resultX, resultY);
        return result;
    }

    /* Draw any label */
    private GLabel label(String text) {
        GLabel label = new GLabel(text);
        label.setColor(Color.BLACK);
        return label;
    }

    /* Draw balls in right bottom corner, which represent how many user attempts left */
    private void drawCountRemainedAttempts() {
        double lastAttemptsBallX = getWidth() - 2 * NTURNS_BALL_RADIUS - NTURNS_BALL_SEP;
        double attemptsBallY = getHeight() - 2 * NTURNS_BALL_RADIUS - NTURNS_BALL_SEP;
        double attemptsBallX = lastAttemptsBallX;
        for (int j = 0; j < NTURNS; j++) {
            GOval attempt = new GOval(attemptsBallX, attemptsBallY, 2 * NTURNS_BALL_RADIUS, 2 * NTURNS_BALL_RADIUS);
            attempt.setFilled(true);
            attempt.setFillColor(Color.gray);
            add(attempt);
            attemptsBallX = attemptsBallX - 2 * NTURNS_BALL_RADIUS - NTURNS_BALL_SEP;
        }
    }

    /* Remove attempts ball when user lose ball */
    private void removeAttemptsBall(int remainedAttempts) {
        double removeAttemptsBallX = getWidth() - NTURNS_BALL_RADIUS - NTURNS_BALL_SEP;
        double removeAttemptsBallY = getHeight() - NTURNS_BALL_RADIUS - NTURNS_BALL_SEP;
        for (int i = 1; i < remainedAttempts; i++) {
            removeAttemptsBallX = removeAttemptsBallX - 2 * NTURNS_BALL_RADIUS - NTURNS_BALL_SEP;
        }
        GObject removeBall = getElementAt(removeAttemptsBallX, removeAttemptsBallY);
        if (removeBall != null) {
            remove(removeBall);
        }
    }

}