
public class TickLoop implements Runnable {

	private StateManager state_manager;
	private KeyboardHandler keyboard_handler;
	
	public TickLoop(StateManager state_manager, KeyboardHandler keyboard_handler){
		this.state_manager = state_manager;
		this.keyboard_handler = keyboard_handler;
	}
	@Override
	public void run() {
		tickLoop();
		
	}
	
	public void tickLoop(){

		final int TICKS_PER_SECOND = 60;
		final int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
		
		long next_game_tick = System.currentTimeMillis();
		
		int sleep_time = 0;
		
//		long prevTPSTime = System.currentTimeMillis();
//		int count = 0;
		while(true){
//			System.out.println("TickLoop - ticking" );
			state_manager.tick();	
			keyboard_handler.clearEvents();
			
			next_game_tick += SKIP_TICKS;
			sleep_time = (int)(next_game_tick - System.currentTimeMillis());
			
			if(sleep_time >= 0){
				try {
					Thread.sleep(sleep_time);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
			else{
//				//already late, do nothing
			}	
		}

	}
}
