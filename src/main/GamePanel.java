package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import static utils.Constans.PlayerConstants.*;
import static utils.Constans.Directions.*;


public class GamePanel extends JPanel {

	private MouseInputs mouseInputs;
	private Game game;


	public GamePanel(Game game) {
		mouseInputs = new MouseInputs(this);
		this.game = game;
		
		setPanelSize();
		addKeyListener(new KeyboardInputs(this));
		addMouseListener(mouseInputs);
		addMouseMotionListener(mouseInputs);

	}

	private void setPanelSize() {
		// 56 x 40 = 2240 wide
		// 56 x 25 = 1400 height
		
		Dimension size = new Dimension(2240,1400);
		setPreferredSize(size);
		
	}

	public void updateGame() {

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		game.render(g);
	}
	
	public Game getGame() {
		return game;
	}

}