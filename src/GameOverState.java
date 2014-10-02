import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class GameOverState extends State {
	int wait_limit = 60*5;
	int wait_counter = wait_limit;
	
	int score = 0;
	int lines = 0;
	
	public GameOverState(StateManager state_manager,
			KeyboardHandler keyboard_handler) {
		super(state_manager, keyboard_handler);
		
	}
	
	
	public void initialize(){
		
	}
	@Override
	public void tick() {
//		System.out.println("GameOverState - ticking - wait_counter = "+wait_counter);
		if(wait_counter == 0){
			wait_counter = wait_limit;
			state_manager.goToState(Data.GAME_STATE, 0, 0);
		}
		else{
			wait_counter--;
		}	
	}
	@Override
	public void render(Graphics g) {
		g.drawImage(Data.images.background_game_over_state, 0, 0, Data.WINDOW_WIDTH, Data.WINDOW_HEIGHT,null);
	}

	@Override
	public void reset(int passed_data, int passed_data2) {
		score = passed_data;
		lines = passed_data2;
		
	}
	
}
