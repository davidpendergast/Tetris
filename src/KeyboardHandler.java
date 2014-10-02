import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.TreeSet;


public class KeyboardHandler implements KeyListener {
	
	public TreeSet<Integer> new_keys;
	public TreeSet<Integer> held_keys;

	public KeyboardHandler(){
		new_keys = new TreeSet<Integer>();
		held_keys = new TreeSet<Integer>();
	}
	
	public void clearEvents(){
		new_keys.clear();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		Integer key_code = e.getKeyCode();
		
//		if(held_keys.contains(key_code) == false){
			new_keys.add(key_code);
			held_keys.add(key_code);
//		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		Integer key_code = e.getKeyCode();
		
		held_keys.remove(key_code);
			//new_keys is only cleared manually, by clearEvents(), so no inputs are lost.
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	

}
