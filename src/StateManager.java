import java.awt.Graphics;


public class StateManager {
	private KeyboardHandler keyboard_handler;
	
	public State active_state;
	public GameState game_state;
	public GameOverState game_over_state;
	public HighScoreState high_score_state;
	public MenuState menu_state;
	
	public StateManager(KeyboardHandler keyboard_handler){
		this.keyboard_handler = keyboard_handler;
		
		game_state = new GameState(this,keyboard_handler);
		game_over_state = new GameOverState(this,keyboard_handler);
		high_score_state = new HighScoreState(this,keyboard_handler);
		menu_state	= new MenuState(this,keyboard_handler);
	}
	
	public void goToState(String state_name, int passed_data, int passed_data2){
		State next_state = null;
		switch(state_name){
		case Data.GAME_OVER_STATE:
			next_state = game_over_state;
			break;
		case Data.GAME_STATE:
			next_state = game_state;
			break;
		case Data.HIGH_SCORE_STATE:
			next_state = high_score_state;
			break;
		default:
			next_state = menu_state;
			break;
		}
		
		next_state.reset(passed_data, passed_data2);
		active_state = next_state;
	}
	
	public void initialize(){
		active_state = game_state;
	}

	public void tick() {
		if(active_state != null){	//thread-safe, because only tick_loop will cause active_state to change
			active_state.tick();
		}
	}
	
	public void render(Graphics g){
//		System.out.println("StateManager - rendering");
		State temp = active_state;		//remains thread-safe, even if active state becomes null during rendering
		if(temp != null){
			temp.render(g);
		}
	}

}
