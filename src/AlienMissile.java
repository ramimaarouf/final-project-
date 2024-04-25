import javax.swing.ImageIcon;

public class AlienMissile extends Missile {

    public AlienMissile() {
        super();
    }

    public AlienMissile(int xV, int yV, int w, int h) {
        super(xV, yV, w, h, new ImageIcon("kk.png"));
    }

    public void sety() {
        super.setdy(-5);
    }

    public boolean hit(ShipMissile sm) {
    	 if (sm.getY() + sm.getH() > getY() && sm.getY() < getY() + super.getH() &&
                 sm.getX() < getX() + super.getW() && sm.getX() + sm.getW() > getX()) {
            return true; // Collision detected
        }
        return false; // No collision
    }
}
