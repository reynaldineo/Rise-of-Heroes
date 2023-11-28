package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

public class GamePanel extends JPanel {

	private MouseInputs mouseInputs;
	private float xDelta = 100, yDelta = 100;
	private BufferedImage img;

	public GamePanel() {
		mouseInputs = new MouseInputs(this);
		
		importImg();
		
		setPanelSize();
		addKeyListener(new KeyboardInputs(this));
		addMouseListener(mouseInputs);
		addMouseMotionListener(mouseInputs);

	}

	private void importImg() {
		InputStream is = getClass().getResourceAsStream("/hero.png");	
	
		try {
			img = ImageIO.read(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setPanelSize() {
		// 56 x 40 = 2240 wide
		// 56 x 25 = 1400 height
		
		Dimension size = new Dimension(2240,1400);
		setPreferredSize(size);
		
	}

	public void changeXDelta(int value) {
		this.xDelta += value;

	}

	public void changeYDelta(int value) {
		this.yDelta += value;

	}

	public void setRectPos(int x, int y) {
		this.xDelta = x;
		this.yDelta = y;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawImage(img, 0, 0, null);
	}


}