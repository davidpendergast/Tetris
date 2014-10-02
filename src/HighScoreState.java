import java.awt.Graphics;


public class HighScoreState extends State {
	
	int current_score = 0;
	int current_lines = 0;
	String current_name = "AAA";
	
	String[] names;
	int[] scores;
	int[] lines;
	
	boolean input_mode = false;
	
	public HighScoreState(StateManager state_manager,
			KeyboardHandler keyboard_handler) {
		super(state_manager, keyboard_handler);

		names 	= new String[5];
		scores	= new int[5];
		lines	= new int[5];
	}
	
	public void initialize(){
//		loadFile();
		
	}
	
	public void loadFile(){
		String text = TextFileReader.readFile(Data.high_score_filename);
		System.out.println("HighScoreState - "+text);
		String[] text_lines = text.split("\n");
		
		for(int i = 0; i < Math.min(text_lines.length, 5); i++){
			String[] split_lines = text_lines[i].split("\t");
			names[i] = split_lines[0];
			scores[i]= Integer.parseInt(split_lines[2]);
			lines[i] = Integer.parseInt(split_lines[1]);
		}
	}

	@Override
	public void reset(int passed_data, int passed_data2) {
		loadFile();
		
		int current_score = passed_data;
		int current_lines = passed_data2;
		
		int score_placement = -1;
		
		for(int i = 0; i < 5; i++){
			if(current_score > scores[i]){
				score_placement = i;
				break;
			}
		}
		
		input_mode = (score_placement != -1);
		if(input_mode == false)
			return;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Data.images.background_high_score_state, 0, 0, Data.WINDOW_WIDTH, Data.WINDOW_WIDTH, null);
	}

}
