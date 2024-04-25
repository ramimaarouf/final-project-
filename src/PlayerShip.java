import javax.swing.*;

public class PlayerShip extends Ship {

    public PlayerShip() {
        super();
    }

    public PlayerShip(int xV, int yV, int w, int h) {
        super(xV, yV, new ImageIcon("playership.png"), w, h);
    }

    public void setX(int x1) {
        super.setX(x1 - super.getX());
    }

    public void setY(int y1) {
        super.setY(y1 - super.getY());
    }

    public boolean hit(AlienMissile am) {
        if (am.getY() + am.getH() > getY() && am.getY() < getY() + super.getH() &&
                am.getX() < getX() + super.getW() && am.getX() + am.getW() > getX()) {
            System.out.println("You're hit!!");
            return true; // Collision detected
        }
        return false; // No collision
    }
}
