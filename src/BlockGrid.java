import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Arrays;


public class BlockGrid {
	int r_size;
	int c_size;
	
	public Block[][] block_array;
	private BufferedImage image;
	
	public BlockGrid(int r_size, int c_size){
		this.r_size = r_size;
		this.c_size = c_size;
		
		block_array = new Block[this.r_size][this.c_size];
		image = new BufferedImage(c_size * 16, (r_size - 2) * 16, BufferedImage.TYPE_INT_ARGB);
	}
	
	public boolean collides(int r_offset, int c_offset, boolean[][] boolean_array){
		for(int r = 0; r < boolean_array.length; r++){
			for(int c = 0; c < boolean_array[0].length; c++){
				if(boolean_array[r][c] == true){
					if(isValid(r + r_offset, c + c_offset) == false){		//position is out of bounds
						if(r + r_offset < block_array.length && c + c_offset >= 0 && c + c_offset < block_array[0].length){
							//
						}
						else return true;
					}
					else if(block_array[r + r_offset][c + c_offset] != null){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean isValid(int r, int c){
		return r >= 0 && r < block_array.length && c >= 0 && c < block_array[0].length;
	}
	
	public BufferedImage getImage(){
		return image;
	}
	
	public void updateImage(){
		image = new BufferedImage(c_size * 16, (r_size) * 16, BufferedImage.TYPE_INT_ARGB);
		drawBlocks();
	}
	
	private void drawBlocks(){
		Block temp;
		Graphics g = image.getGraphics();
		
		for(int r = 0; r < r_size; r++){
			for(int c = 0; c < c_size; c++){
				temp = block_array[r][c];
				if(temp != null){
					temp.render(g,r,c);
				}
			}
		}
	}

	public void addArrangement(int r_offset, int c_offset, boolean[][] boolean_array, Block block) {
		for(int r = 0; r < boolean_array.length; r++){
			for(int c = 0; c < boolean_array[0].length; c++){
				if(boolean_array[r][c] == true){
					if(isValid(r + r_offset, c + c_offset) == true){
						block_array[r + r_offset][c + c_offset] = block;
					}
				}
			}
		}
	}

	public int[] getFullLines() {
		int[] result = new int[4];
		int i = 0;
		boolean is_full;
r_array:for(int r = 0; r < 20; r++){
			is_full = true;
c_array:	for(int c = 0; c < 10; c++){
				if(block_array[r][c] == null){
					is_full = false;
					continue r_array;
				}
			}
			if(is_full == true){
				result[i] = r;
				System.out.println("BlockGrid - "+r+" is full");
				i++;
			}
		}
		
		if(i == 0){
			return null;
		}
		else{
			System.out.println("BlockGrid - "+i);
			result = Arrays.copyOfRange(result,0,i);
			return result;
		}
	}

	public void removeLines(int[] full_lines) {
		for(int i = 0; i < full_lines.length; i++){
			removeLine(full_lines[i]);
		}
	}
	private void removeLine(int row){
		for(int r = row - 1; r >= 0 ; r--){
			for(int c = 0; c < 10; c++){
				block_array[r+1][c] = block_array[r][c];
			}
		}
		for(int c = 0; c < 10; c++){	//removes top row
			block_array[0][c] = null;
		}
	}
}
