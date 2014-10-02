import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Window {
	private JFrame frame;
	private JPanel panel;
	public Dimension size;
	
	public Image game_screen;
	
	KeyboardHandler keyboard_handler = null;
	
	public Window(int width, int height){
		size = new Dimension(width,height);
		panel = new JPanel();
		panel.setPreferredSize(size);
		
		frame = new JFrame();
		frame.add(panel);
		frame.pack();		//fits frame to panel
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setLayout(null);
		frame.setVisible(true);	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		game_screen = panel.createVolatileImage(width, height);
	}
	
	public void addKeyboardHandler(KeyboardHandler keyboard_handler){
		this.keyboard_handler = keyboard_handler;
		frame.addKeyListener(keyboard_handler);
	}
	
	public Graphics getGraphics(){
		return game_screen.getGraphics();
	}
	
	public void draw(){
		Graphics g = panel.getGraphics();
		g.drawImage(game_screen, 0, 0, null);
	}

}
