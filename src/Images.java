import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Images {
	
	BufferedImage background_game_state;
	BufferedImage background_game_over_state;
	BufferedImage background_high_score_state;
	
	BufferedImage tile_sheet;
	BufferedImage[][] tile_array;
	
	public Images(){
		tile_array = new BufferedImage[7][3];
	}
	public void initialize(){
		try {
			background_game_state 		= ImageIO.read(new File("assets/game_background.png"));
			tile_sheet 					= ImageIO.read(new File("assets/blocks.png"));
			background_game_over_state 	= ImageIO.read(new File("assets/game_over_background.png"));
			background_high_score_state	= ImageIO.read(new File("assets/high_score_background.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(int x = 0; x < 7; x++){
			for(int y = 0; y < 3; y++){
				BufferedImage img = tile_sheet.getSubimage(x * 16, y * 16, 16, 16);
				tile_array[x][y] = img;
				System.out.println(img);
			}
		}
	}
	public BufferedImage getImage(int color, int id){
		return tile_array[color][id];
	}
}
