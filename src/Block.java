import java.awt.Graphics;


public class Block {
	//blocks do not track their own position
	
	int color;
	int id;			//0 = normal, 1 = low opacity;
	
	public Block(int color, int id){
	
		this.color 	= color;
		this.id 	= id;
	}
	
	public void render(Graphics g, int r, int c){
		g.drawImage(	Data.images.getImage(color, id),
						c*16, 
						r*16, 
						(c+1)*16, 
						(r+1)*16, 
						0, 
						0, 
						16, 
						16,
						null);
	}

}
