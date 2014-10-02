import java.awt.event.KeyEvent;


public class Data {
	public static final int WINDOW_HEIGHT = 352*2;
	public static final int WINDOW_WIDTH = 352*2;
	
	public static final int LEFT 	= KeyEvent.VK_A;
	public static final int RIGHT 	= KeyEvent.VK_D;
	public static final int UP 		= KeyEvent.VK_W;
	public static final int DOWN 	= KeyEvent.VK_S;
	
	public static final int CW_ROT	= KeyEvent.VK_RIGHT;
	public static final int CCW_ROT	= KeyEvent.VK_LEFT;
	
	public static String high_score_filename = "assets/Highscores.txt";
	
	public static Images images;
	public static String game_over_background_filename = "game_over_background.png";
	public static String high_score_background_filename = "high_score_background.png";
	
	public static final String GAME_STATE			= "game_state";
	public static final String GAME_OVER_STATE 		= "game_over_state";
	public static final String HIGH_SCORE_STATE 	= "high_score_state";
	public static final String MENU_STATE			= "menu_state";
	
	static{
		images = new Images();
		images.initialize();
	}
	
	
	

}
