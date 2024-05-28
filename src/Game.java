import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.io.*;

public class Game extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener {


    //tests
    private BufferedImage back;
    private Background_Image bi;
    private Background_Image yep;
    private Background_Image hep;
    private Background_Image bep;
    private int key, count;
    private ImageIcon background;
    private ImageIcon background2;
    private ImageIcon background3;
    private ImageIcon background4;
    private ImageIcon alienImage1;
    private ImageIcon alienImage2;
    private Player play;
    private ArrayList<AlienShip> aliens;
    private ArrayList<ShipMissile> sMissiles;
    private ArrayList<AlienMissile> aMissiles;
    private boolean start, win, moveRight;
    private char screen;
    private char winn;
    private char loss;
    private PlayerShip player;
    private int lives;
    private boolean infiniteLives;
    private boolean freezeAliens;
    private boolean hardMode; 
    private boolean easyMode;
    private boolean hardModeCompleted;
    private int score;

    public Game() {
        new Thread(this).start();
        this.addMouseListener(this);
        this.addKeyListener(this);
        this.addMouseMotionListener(this);
        play = new Player();
        bi = new Background_Image();
        yep = new Background_Image();
        hep = new Background_Image();
        sMissiles = new ArrayList<>();
        aMissiles = new ArrayList<>();
        win = false;
        moveRight = true;
        count = 0;
        screen = 'S';
        lives = 3;
        infiniteLives = false;
        hardMode = false; 
        easyMode = false;
        hardModeCompleted = false;
        score=0;
        background = new ImageIcon("background.png");
        background2 = new ImageIcon("PlanetA.png");
        background3 = new ImageIcon("planetB.png");
        background4 = new ImageIcon("PlanetC.png");
        aliens = setAliens();
        player = new PlayerShip(400, 500, 75, 100);
    }

    public ArrayList<AlienShip> setAliens() {
        ArrayList<AlienShip> temp = new ArrayList<>();
        int x = 75;
        int y = 100;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 7; j++) {
                temp.add(new AlienShip(x, y, 50, 50));
                x += 100;
            }
            y += 50;
            x = 75;
        }
        return temp;
    }

    public void run() {
        try {
            while (true) {
                Thread.sleep(5);
                repaint();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void paint(Graphics g) {
        Graphics2D twoDgraph = (Graphics2D) g;
        if (back == null)
            back = (BufferedImage) (createImage(getWidth(), getHeight()));

        Graphics g2d = back.createGraphics();
        g2d.clearRect(0, 0, getSize().width, getSize().height);
        g2d.drawImage(new ImageIcon(bi.getBackground()).getImage(), 0, 0, getWidth(), getHeight(), this);
        g2d.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);

        g2d.setFont(new Font("Broadway", Font.BOLD, 50));
        g2d.setColor(Color.white);
        screen(g2d);

        if (infiniteLives) {
            g2d.drawString("Lives: Infinite", 20, 50);
        } else {
            g2d.drawString("Lives: " + lives, 20, 50);
        }
        if (hardMode) {
            g2d.drawString("Hard Mode", getWidth() - 300, 50); // Indicate hard mode
        }
        g2d.drawString("Score: " + score, 1031, 100);        
    
        twoDgraph.drawImage(back, null, 0, 0);
    }
   

    public void screen(Graphics g2d) {
        switch (screen) {
            case 'S':
                drawStartScreen(g2d);
                break;

            case 'G':
                drawAliens(g2d);
                drawPlayer(g2d);
                moveAliens();
                drawSM(g2d);
                drawAlienMissile(g2d);
                alienHit();
                count++;
                if (count % (hardMode ? 50 : 100) == 0) { // Increase frequency of alien missile in hard mode
                    getAlienMissile(g2d);
                }
                checkPlayerHit();
                if (aliens.isEmpty()) {
                    screen = 'W';
                }
                break;

            case 'W':
                drawWinScreen(g2d);
                break;

            case 'L':
                drawLossScreen(g2d);
                break;

            case 'P':
                g2d.drawImage(background2.getImage(), 0, 0, getWidth(), getHeight(), this);
                drawAliens(g2d);
                drawPlayer(g2d);
                moveAliens();
                drawSM(g2d);
                drawAlienMissile(g2d);
                alienHit();
                count++;
                if (count % (hardMode ? 50 : 100) == 0) {
                    getAlienMissile(g2d);
                }
                checkPlayerHit();
                if (aliens.isEmpty()) {
                    screen = 'W';
                }
                break;

            case 'R':
                g2d.drawImage(background3.getImage(), 0, 0, getWidth(), getHeight(), this);
                drawAliens1(g2d);
                changeAliensPic("AlienB.png");
                drawPlayer(g2d);
                moveAliens();
                drawSM(g2d);
                drawAlienMissile(g2d);
                alienHit();
                count++;
                if (count % (hardMode ? 50 : 100) == 0) {
                    getAlienMissile(g2d);
                }
                checkPlayerHit();
                if (aliens.isEmpty()) {
                    screen = 'W';
                }
                break;

            case 'O':
                g2d.drawImage(background4.getImage(), 0, 0, getWidth(), getHeight(), this);
                drawAliens2(g2d);
                changeAliensPic("AlienCC.png");
                drawPlayer(g2d);
                moveAliens();
                drawSM(g2d);
                drawAlienMissile(g2d);
                alienHit();
                count++;
                if (count % (hardMode ? 50 : 100) == 0) {
                    getAlienMissile(g2d);
                }
                checkPlayerHit();
                if (aliens.isEmpty()) {
                    screen = 'W';
                }
                break;
            case 'i':
        }
    }

    public void changeAliensPic(String path) {
        for (AlienShip alien : aliens) {
            alien.setPic(path);
        }
    }

    private void drawWinScreen(Graphics g2d) {
        g2d.setFont(new Font("Broadway", Font.BOLD, 50));
        g2d.setColor(Color.white);
        g2d.drawString("Congratulations! You have won!!", 20, 400);
        g2d.drawString("Press M to play again", 200, 600);
        if (hardModeCompleted) {
            // Draw a star in the bottom left corner if hard mode is completed
            g2d.setColor(Color.yellow);
            g2d.setFont(new Font("Arial", Font.BOLD, 30));
            g2d.drawString("*", 50, getHeight() - 50);
        }
    
    }

    private void drawLossScreen(Graphics g2d) {
        g2d.setFont(new Font("Broadway", Font.BOLD, 50));
        g2d.setColor(Color.white);
        g2d.drawString("Aw shucks you died", 100, 400);
        g2d.drawString("Press R to play again", 200, 600);
    }

    public void drawStartScreen(Graphics g2d) {
        g2d.setFont(new Font("Broadway", Font.BOLD, 50));
        g2d.setColor(Color.white);
        g2d.drawString("Welcome to Space Invaders", 300, 100);
        g2d.drawString("Press 1 , 2, 3 to the corresponding letter planet to begin", 50, 670);
        g2d.drawString("Use mouse to move and left click to shoot", 200, 1000);
        g2d.drawString("Press H to start hard mode", 200, 1100); // Instruction for hard mode
    }

    public void drawAliens(Graphics g2d) {
        for (AlienShip a : aliens) {
            a.setPic("AlienAC.png");
            g2d.drawImage(a.getPic().getImage(), a.getX(), a.getY(), a.getW(), a.getH(), this);
        }
    }

    public void drawAliens1(Graphics g2d) {
        for (AlienShip a : aliens) {
            a.setPic("AlienBB.png");
            g2d.drawImage(a.getPic().getImage(), a.getX(), a.getY(), a.getW(), a.getH(), this);
        }
    }

    public void drawAliens2(Graphics g2d) {
        for (AlienShip a : aliens) {
            a.setPic("AlienCC.png");
            g2d.drawImage(a.getPic().getImage(), a.getX(), a.getY(), a.getW(), a.getH(), this);
        }
    }

    public void changeDyAlien(int dy) {
        for (AlienShip a : aliens) {
            a.setdy(dy);
        }
    }

    public void moveAliens() {
        if (!freezeAliens) {
            if (checkAlienWall() || checkAlienBottom()) {
                changeDyAlien(30);
            }
            for (AlienShip a : aliens) {
                if (moveRight) {
                    a.setdx(hardMode ? 4 : 2); // Increase speed in hard mode
                } else {
                    a.setdx(hardMode ? -4 : -2);
                }
            }
        }
    }

    public boolean checkAlienBottom() {
        for (AlienShip a : aliens) {
            if (a.getY() + a.getH() >= getHeight()) {
                screen = 'L';
                return true;
            }
        }
        return false;
    }

    public boolean checkAlienWall() {
        for (AlienShip a : aliens) {
            if (a.getX() + a.getW() >= getWidth() || a.getX() <= 0) {
                moveRight = !moveRight;
                return true;
            }
        }
        return false;
    }

    public void drawPlayer(Graphics g2d) {
        g2d.drawImage(player.getPic().getImage(), player.getX(), player.getY(), player.getW(), player.getH(), this);
    }

    public void playerMissile(int x) {
        sMissiles.add(new ShipMissile(x, player.getY(), 30, 30));
    }

    public void drawSM(Graphics g2d) {
        for (ShipMissile sm : sMissiles) {
            g2d.drawImage(sm.getPic().getImage(), sm.getX(), sm.getY(), sm.getH(), sm.getW(), this);
            sm.setdy(-5);
        }
    }

    public void getAlienMissile(Graphics g2d) {
        int randAlien = (int) (Math.random() * aliens.size());
        aMissiles.add(new AlienMissile(aliens.get(randAlien).getX() + (aliens.get(randAlien).getW()) / 2, aliens.get(randAlien).getY() + aliens.get(randAlien).getH(), 30, 30));
    }

    public void drawAlienMissile(Graphics g2d) {
        for (AlienMissile am : aMissiles) {
            g2d.drawImage(am.getPic().getImage(), am.getX(), am.getY(), am.getW(), am.getH(), this);
            am.setdy(2);
        }
    }

    public boolean alienHit() {
        if (!aliens.isEmpty()) {
            for (int i = 0; i < aliens.size(); i++) {
                for (int j = 0; j < sMissiles.size(); j++) {
                    if (aliens.get(i).hit(sMissiles.get(j))) {
                        aliens.remove(i);
                        sMissiles.remove(j);
                        score += 100;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean checkPlayerHit() {
        if (!infiniteLives) {
            for (AlienMissile am : aMissiles) {
                if (player.hit(am)) {
                    aMissiles.remove(am);
                    lives--;
                    if (lives <= 0) {
                        screen = 'L';
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == 32) {
            screen = 'G';
        } else if (key == 77 && screen == 'W') {
            resetGame();
        } else if (key == 82 && screen == 'L') {
            resetGame();
        } else if (key == 112) {
            infiniteLives = !infiniteLives;
        } else if (key == 121) {
            freezeAliens = !freezeAliens;
        } else if (key == 49) {
            screen = 'P';
        } else if (key == 50) {
            screen = 'R';
        } else if (key == 51) {
            screen = 'O';
        } else if (key == 72) {
            hardMode = true;
            resetGame();
            screen = 'G'; 
            hardModeCompleted = false;
        
    } else if (key == 85) { 
    easyMode = true;
    resetGame(); 
    screen = 'G'; 
    }
}

    public void resetGame() {
        sMissiles.clear();
        aMissiles.clear();
        aliens = setAliens();
        player = new PlayerShip(400, 500, 75, 100);
        screen = 'S';
        count = 0;
        moveRight = true;
        lives = hardMode ? 1 : (easyMode ? 5 : 3); // Set fewer lives for hard mode
        hardModeCompleted = false;
    }

    // Other methods required by interfaces (not implemented)
    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        player.setX(e.getX());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
        if (arg0.getButton() == 1) {
            playerMissile(arg0.getX() + (player.getW() / 4));
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
