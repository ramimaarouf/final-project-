import javax.swing.ImageIcon;

public class AlienShip extends Ship {
    public AlienShip() {
        super();
    }

    public AlienShip(int xV, int yV, int w, int h) {
        super(xV, yV, new ImageIcon("ailen.png"), 50, 50);
    }

    public void setdx(int dx1) {
        super.setX(dx1);
    }

    public void setdy(int dy1) {
        super.setY(dy1);
    }
public boolean hit(ShipMissile sm) {
if((sm.getY()+sm.getH())>getY()&&sm.getY()<getY()+super.getH()&&
		sm.getX()<(getX()+super.getW())&&(sm.getX()+sm.getW())>getX())
{
System.out.println("got em!!!");
return true;
}
return false;
}


}