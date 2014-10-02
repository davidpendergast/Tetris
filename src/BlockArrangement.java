import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class BlockArrangement {
	
	//takes an int from 0-6 indicating type of arrangement.  also indicates color.
	Block block;
	Block ghost_block;
	boolean[][] block_locations;	//[r_loc][c_loc]
	private BufferedImage image;
	private BufferedImage ghost_image;
	int size;
	int type;
	
	public BlockArrangement(int type){
		block 		= new Block(type, 0);
		ghost_block	= new Block(type, 1);
		
		if(type < 2){
			size = 4;
		}
		else{
			size = 3;
		}
		
		block_locations = new boolean[size][size];
		image = new BufferedImage(size*16,size*16,BufferedImage.TYPE_INT_ARGB);
		
		this.type = type;
		
		fillBlockLocations(type);
		updateImage();
		
	}
	
	public BufferedImage getImage(){
		return image;
	}
	public BufferedImage getGhostImage(){
		return ghost_image;
	}
	public void updateImage(){
		image 		= new BufferedImage(size*16,size*16,BufferedImage.TYPE_INT_ARGB);
		ghost_image = new BufferedImage(size*16,size*16,BufferedImage.TYPE_INT_ARGB);
		Graphics g1 = image.getGraphics();
		Graphics g2 = ghost_image.getGraphics();
		
		for(int r = 0; r < size; r++){
			for(int c = 0; c < size; c++){
				if(block_locations[r][c] == true){
					block.render(g1, r, c);
					ghost_block.render(g2, r, c);
				}
			}
		}
	}
	
	public static BlockArrangement getRandomArrangement(){
		int type = (int)(Math.random() * 7);
		return new BlockArrangement(type);
	}
	
	public boolean[][] rotateCW(){
		boolean[][] new_block_locations = new boolean[block_locations.length][block_locations[0].length];
		
		int r_len = block_locations.length;
		int c_len = block_locations[0].length;
		
		for(int r = 0; r < r_len; r++){
			for(int c = 0; c < c_len; c++){
				if(block_locations[r][c] == true){
					new_block_locations[c][c_len - r - 1] = true;
				}
			}
		}
		
		return new_block_locations;
	}
	
	public boolean[][] rotateCCW(){
		boolean[][] new_block_locations = new boolean[block_locations.length][block_locations[0].length];
		
		int r_len = block_locations.length;
		int c_len = block_locations[0].length;
		
		for(int r = 0; r < r_len; r++){
			for(int c = 0; c < c_len; c++){
				if(block_locations[r][c] == true){
					new_block_locations[c_len - c- 1][r] = true;
				}
			}
		}
		
		return new_block_locations;
	}
	
	private void fillBlockLocations(int type){
		switch(type){
		case 0:			//line piece
			block_locations[2][0] = true;
			block_locations[2][1] = true;
			block_locations[2][2] = true;
			block_locations[2][3] = true;
			break;
		case 1:			//square piece
			block_locations[1][1] = true;
			block_locations[1][2] = true;
			block_locations[2][1] = true;
			block_locations[2][2] = true;
			break;
		case 2:			//L-piece
			block_locations[1][0] = true;
			block_locations[1][1] = true;
			block_locations[1][2] = true;
			block_locations[2][2] = true;
			break;
		case 3:			//reverse L-piece
			block_locations[1][0] = true;
			block_locations[1][1] = true;
			block_locations[1][2] = true;
			block_locations[0][2] = true;
			break;
		case 4:			//S-piece
			block_locations[1][1] = true;
			block_locations[1][2] = true;
			block_locations[2][0] = true;
			block_locations[2][1] = true;
			break;
		case 5:			//reverse S-piece
			block_locations[1][0] = true;
			block_locations[1][1] = true;
			block_locations[2][1] = true;
			block_locations[2][2] = true;
			break;
		case 6:			//T-piece
			block_locations[1][0] = true;
			block_locations[1][1] = true;
			block_locations[1][2] = true;
			block_locations[2][1] = true;
			break;
		}
	}
}
