import javax.swing.*;

public class ShipMissile extends Missile{
	
	
	private int width, height;
	public ShipMissile()
	{
		super();
	}
	public ShipMissile(int xV, int yV, int w, int h) {
		super(xV,yV,w,h,new ImageIcon("miss.png"));
	}
	public void sety() {
		super.setdy(-5);
	}
	   public boolean hit(ShipMissile sm) {
		   if ((sm.getY() + sm.getH() > super.getY() && sm.getY() < super.getY() + super.getH() && sm.getX() < super.getX() + super.getW() && sm.getX() + sm.getW() > super.getX())) {
			    
			    return true;
			}

	    		
	    	
	    	return false;
	    }
	}

