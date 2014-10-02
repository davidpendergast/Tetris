import java.awt.Graphics;


public abstract class State {
	
	StateManager state_manager;
	KeyboardHandler keyboard_handler;
	
	public State(StateManager state_manager, KeyboardHandler keyboard_handler){
		this.state_manager = state_manager;
		this.keyboard_handler = keyboard_handler;
	}
	
	public abstract void reset(int passed_data, int passed_data2);
	public abstract void tick();
	public abstract void render(Graphics g);

}
