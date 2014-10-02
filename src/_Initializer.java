import java.awt.Graphics;


public class _Initializer {
	public static void main(String[] cucumbers){
		KeyboardHandler keyboard_handler = new KeyboardHandler();
		
		Window window = new Window(Data.WINDOW_WIDTH,Data.WINDOW_HEIGHT);
		window.addKeyboardHandler(keyboard_handler);
		
		StateManager state_manager = new StateManager(keyboard_handler);
		
		TickLoop tick_loop = new TickLoop(state_manager,keyboard_handler);
		
		System.out.println(Data.images);
		
		state_manager.initialize();
		
		new Thread(tick_loop).start();
		
		//starting render loop
		
		final int TICKS_PER_SECOND = 30;
		final int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
		
		long next_game_tick = System.currentTimeMillis();
		
		int sleep_time = 0;
		
		long prevTPSTime = System.currentTimeMillis();
		int count = 0;
		Graphics g = null;
		
		while(true){
//			System.out.println("_Initializer - rendering");
			
			g = window.getGraphics();
			
			state_manager.render(g);	
			
			window.draw();
			g.dispose();
			
//			System.out.println("RenderingLoop - still rendering "+Math.random());
			
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
//				System.out.println("Rendering Loop - Late");
			}	
		}
	}	
}
	
