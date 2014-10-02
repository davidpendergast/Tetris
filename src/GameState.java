import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;


public class GameState extends State {
	
	//inherited vars:
		//StateManager state_manager;
		//KeyboardHandler keyboard_handler;
	BufferedImage background;
	
	BlockArrangement active_piece;
	
	int active_piece_r;
	int active_piece_c;
	
	BlockArrangement ghost;
	boolean show_ghost;
	int ghost_r;
	
	int next_blocks_size = 4;
	BlockArrangement[] next_blocks;
	Point[] next_block_locations = {new Point(1,16),new Point(6,16),new Point(11,16), new Point(16,16)};
	
	BlockGrid block_grid;
	
	Rectangle block_grid_area = new Rectangle(2*80,2*16,2*10*16,2*20*16);
	
	int level;// = 15;
	int ticks_per_shift;// = 30;
	int shift_counter;// = ticks_per_shift;
	int lock_delay = 30;
	int lock_counter = lock_delay;
	
	int score;
	int lines;
	boolean last_clear_was_4_lines = false;
	
	public GameState(StateManager state_manager, KeyboardHandler keyboard_handler) {
		super(state_manager, keyboard_handler);
		
		next_blocks = new BlockArrangement[next_blocks_size];
		for(int i = 0 ; i < next_blocks_size; i++){
			next_blocks[i] = BlockArrangement.getRandomArrangement();
		}
		
		block_grid = new BlockGrid(20,10);
		
		reset(1,-1);
			//test stuff
//		for(int r = 10; r < block_grid.r_size; r++){
//			for(int c = 0; c < block_grid.c_size; c++){
//				block_grid.block_array[r][c] = new Block((int)(Math.random()*7), 0);
//			}
//		}
	}
	
	public BlockArrangement getNextPiece(){
		BlockArrangement result = next_blocks[0];
		for(int i = 1; i < next_blocks.length; i++){
			next_blocks[i - 1] = next_blocks[i];
		}
		next_blocks[next_blocks.length-1] = BlockArrangement.getRandomArrangement();
		return result;
//		return BlockArrangement.getRandomArrangement();
	}
	
	public void setGhostR(){
		ghost_r = active_piece_r;
		while(block_grid.collides(ghost_r + 1, active_piece_c, active_piece.block_locations) == false){
			ghost_r++;
		}
		if(ghost_r - active_piece_r < 3){
//			show_ghost = false;
		}
		
	}
	
	public void handleLineClears(){
		int[] full_lines = block_grid.getFullLines();
		if(full_lines == null)
			return;
		
		switch(full_lines.length){	//handling scoring
			case 1:
				score += 100 * level;
				last_clear_was_4_lines = false;
				break;
			case 2:
				score += 300 * level;
				last_clear_was_4_lines = false;
				break;
			case 3:
				score += 500 * level;
				last_clear_was_4_lines = false;
				break;
			case 4:
				score += last_clear_was_4_lines ? 3*400 * level : 800 * level;
				last_clear_was_4_lines = true;
				break;
		}
//		System.out.println("GameState - score = "+score);
		lines += full_lines.length;
		if(lines % 10 == 0 && level != 15){
			System.out.println("GameState - level = "+level);
			setLevel(level + 1);
		}
		block_grid.removeLines(full_lines);
	}


	@Override
	public void tick() {
		if(active_piece == null){
			handleLineClears();
			active_piece = getNextPiece();
			active_piece_r = -2;
			active_piece_c = 4;
			if(block_grid.collides(active_piece_r, active_piece_c, active_piece.block_locations)){
				endGame();
			}
			show_ghost = true;
		}
		else{
				//rotation handling
			boolean[][] next_rotation = getNextRotation();	
			if(next_rotation != null){
				boolean rotation_successful = resolveRotCollisions(next_rotation);	
				if(rotation_successful){
					lock_counter = lock_delay;
				}
			}
				//movement handling
			Point next_position = getNextPosition();
			if(next_position != null){
				if(block_grid.collides(next_position.x, next_position.y, active_piece.block_locations) == false){
					active_piece_r = next_position.x;
					active_piece_c = next_position.y;
				}
			}
				//shift handling
			if(shift_counter == 0){
//				System.out.println("GameState - shifting");
				if(block_grid.collides(active_piece_r + 1, active_piece_c, active_piece.block_locations)){
					//do not shift.  do not do anything
				}
				else{
					active_piece_r++;
				}
				shift_counter = ticks_per_shift;
			}
			else{
				shift_counter--;
			}
			
			//lock handling
			if(block_grid.collides(active_piece_r + 1, active_piece_c, active_piece.block_locations)){	//if it is resting directly above blocks
					if(lock_counter == 0){
						if(block_grid.collides(active_piece_r, active_piece_c, active_piece.block_locations)){
							endGame();
						}
						block_grid.addArrangement(active_piece_r, active_piece_c,active_piece.block_locations,active_piece.block);
						active_piece = null;
						lock_counter = lock_delay;
					}
					else{
						lock_counter--;
					}
			}
		}
	}
	
	public boolean[][] getNextRotation(){
		if(keyboard_handler.new_keys.contains(Data.CCW_ROT) && !keyboard_handler.new_keys.contains(Data.CW_ROT)){
			return active_piece.rotateCCW();
		}
		if(keyboard_handler.new_keys.contains(Data.CW_ROT)  && !keyboard_handler.new_keys.contains(Data.CCW_ROT)){
			return active_piece.rotateCW();
		}
		return null;
	}
		
		//returns boolean true = successful rotation, false = unsuccessful
	public boolean resolveRotCollisions(boolean[][] next_rotation){
			//if it isn't colliding
		if(block_grid.collides(active_piece_r, active_piece_c, next_rotation) == false){
			active_piece.block_locations = next_rotation;
		}
		else{	//it is colliding
			if(block_grid.collides(active_piece_r - 1, active_piece_c, next_rotation) == false){		//shift up
				active_piece_r--;
				active_piece.block_locations = next_rotation;
			}
			else if(block_grid.collides(active_piece_r + 1, active_piece_c, next_rotation) == false){	//shift down
				active_piece_r++;
				active_piece.block_locations = next_rotation;
			}
			else if(block_grid.collides(active_piece_r, active_piece_c - 1, next_rotation) == false){	//shift left
				active_piece_c--;
				active_piece.block_locations = next_rotation;
			}
			else if(block_grid.collides(active_piece_r, active_piece_c + 1, next_rotation) == false){	//shift right
				active_piece_c++;
				active_piece.block_locations = next_rotation;
			}
			else{
				//don't do anything, rotation was not possible
				return false;
			}
			
			active_piece.updateImage();
		}
		return true;
	}
	public Point getNextPosition(){
		Point new_location = new Point(active_piece_r,active_piece_c);
		if(keyboard_handler.new_keys.contains(Data.UP)){
			while(block_grid.collides(new_location.x + 1, active_piece_c,active_piece.block_locations) == false){
				new_location.x++;
			}
			lock_counter = 0;
			return new_location;
		}
		if(keyboard_handler.new_keys.contains(Data.DOWN)){
			new_location.x++;
		}
		if(keyboard_handler.new_keys.contains(Data.LEFT)){
			new_location.y--;
		}
		if(keyboard_handler.new_keys.contains(Data.RIGHT)){
			new_location.y++;
		}
		
		if((new_location.x == active_piece_r && new_location.y == active_piece_c) == false){
			lock_counter = lock_delay;
			return new_location;
		}
		else return null;
	}
	
	@Override
	public void render(Graphics g) {

		g.drawImage(Data.images.background_game_state, 0, 0, Data.WINDOW_WIDTH, Data.WINDOW_HEIGHT,null);
		g.setColor(new Color(128,128,128));
		g.setFont(new Font("Arial", Font.PLAIN, 32));
		g.drawString(""+score, 32, 64);
		g.drawString(""+lines, 32, 96);
		g.drawString(""+level, 32, 128);
		
		block_grid.updateImage();
		
		BufferedImage grid_image = block_grid.getImage();
		g.drawImage(grid_image, block_grid_area.x, block_grid_area.y,block_grid_area.width,block_grid_area.height,null);
		
		try{
			
			active_piece.updateImage();
			BufferedImage piece_image;
			int dx1,dx2,dy1,dy2;
			setGhostR();
//			System.out.println("GameState - "+active_piece_r + "\t"+ghost_r);
			if(show_ghost){
				piece_image = active_piece.getGhostImage();
				dx1 = block_grid_area.x + active_piece_c*16*2;
				dy1 = block_grid_area.y + ghost_r*16*2;
				dx2 = dx1 + piece_image.getWidth()*2;
				dy2 = dy1 + piece_image.getHeight()*2;
				g.drawImage(piece_image, dx1, dy1, dx2, dy2, 0, 0, piece_image.getWidth(), piece_image.getHeight(), null);
			}
			
			piece_image = active_piece.getImage();
			dx1 = block_grid_area.x + active_piece_c*16*2;
			dy1 = block_grid_area.y + active_piece_r*16*2;
			dx2 = dx1 + piece_image.getWidth()*2;
			dy2 = dy1 + piece_image.getHeight()*2;
			g.drawImage(piece_image, dx1, dy1, dx2, dy2, 0, 0, piece_image.getWidth(), piece_image.getHeight(), null);
				
		}catch(NullPointerException e){
			//do nothing
		}
		
		for(int i = 0; i < next_blocks.length; i++){
			try{
				BlockArrangement next_piece = next_blocks[i];
				BufferedImage piece_image = next_piece.getImage();
				int dx1 = (next_block_locations[i].y + 1)*16*2;
				int dy1 = (next_block_locations[i].x + 1)*16*2;
				int dx2 = dx1 + piece_image.getWidth()*2;
				int dy2 = dy1 + piece_image.getHeight()*2;
				g.drawImage(piece_image, dx1, dy1, dx2, dy2, 0, 0, piece_image.getWidth(), piece_image.getHeight(), null);
			}catch(NullPointerException e){
				//do nothing
			}
		}
		
		
	}

	@Override
	public void reset(int passed_data, int passed_data2) {
		block_grid = new BlockGrid(20,10);
		setLevel(passed_data);
		score = 0;
		lines = 0;
		active_piece = null;
		last_clear_was_4_lines = false;
	}
	
	public void endGame(){
		System.out.println("GameState - Ending Game");
		state_manager.goToState(Data.GAME_OVER_STATE, score, lines);
	}

	private void setLevel(int i) {
		level = i;
		ticks_per_shift = Math.max(51 - level*3, 2);	
		shift_counter = ticks_per_shift;
	}

}
