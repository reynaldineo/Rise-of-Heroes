package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import main.Game;

public class Menu extends State implements Statemethods{

	public Menu(Game game) {
		super(game);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics g) {
		
		g.setColor(Color.black);
		g.drawString("MENU", Game.GAME_WIDTH / 2, Game.GAME_HEIGHT / 2 - 100 );
		g.drawString("PLAY: CLICK ENTER", Game.GAME_WIDTH / 2, Game.GAME_HEIGHT / 2 - 50);
		g.drawString("PAUSE: CLICK BACKSPACE", Game.GAME_WIDTH / 2, Game.GAME_HEIGHT / 2 );

		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
			Gamestate.state = Gamestate.PLAYING;		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
