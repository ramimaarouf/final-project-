import javax.swing.*;

public class Ship{
	private int x,y,width,height;
	private ImageIcon pic;
	
	public Ship()
	{
		x=0;
		y=0;
		pic=new ImageIcon();
		width=100;
		height=100;
	}
	public Ship (int xV, int yV, ImageIcon p , int w , int h)
	{
x=xV;
y=yV;
pic=p;
width=w;
height=h;
}
//add getters and setters
		// TODO Auto-generated method stub
		
	
public int getX() 
{
	return x;
}
public int getY()
{
	return y;
}
public ImageIcon getPic()
{
return pic;
}
public int getW()
{
	return width;
}
public int getH()
{
	return height;
}
public void setX(int xV)
{
	x+=xV;
}
public void setY(int yV)
{
	y+=yV;
}



}
