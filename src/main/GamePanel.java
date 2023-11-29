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
	private float xDelta = 100, yDelta = 100;
	private BufferedImage img;
	private BufferedImage[][] animations;
	private int aniTick, aniIndex, aniSpeed = 30;
	private int playerAction = IDLE;
	private int playerDir = -1;
	private boolean moving = false;

	public GamePanel() {
		mouseInputs = new MouseInputs(this);
		
		importImg();
		loadAnimations();
		
		setPanelSize();
		addKeyListener(new KeyboardInputs(this));
		addMouseListener(mouseInputs);
		addMouseMotionListener(mouseInputs);

	}

	private void loadAnimations() {
		animations = new BufferedImage[11][8];
		
		for(int j = 0; j < animations.length; j++)
			for(int i = 0; i < animations[j].length; i++)
				animations[j][i] = img.getSubimage(i*56, j*56, 56, 56);
		
	}

	private void importImg() {
		InputStream is = getClass().getResourceAsStream("/Char/generic_char/png/blue/char_blue_1.png");	
	
		try {
			img = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void setPanelSize() {
		// 56 x 40 = 2240 wide
		// 56 x 25 = 1400 height
		
		Dimension size = new Dimension(2240,1400);
		setPreferredSize(size);
		
	}

	public void setDirection(int direction) {
		this.playerDir = direction;
		moving = true;
	}
	
	public void setMoving(boolean moving) {
		this.moving = moving;
	}
	
	private void updateAnimationTick() {
		
		aniTick++;
		if(aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if(aniIndex >= GetSpriteAmount(playerAction)) {
				aniIndex = 0;
			}
		}
	}
	
	private void setAnimation() {
		
		if(moving)
			playerAction = RUNNING;
		else 
			playerAction = IDLE;
		
	}
	
	private void updatePos() {
		
		if(moving) {
			switch(playerDir) {
			case LEFT:
				xDelta -= 1;
				break;
			case UP:
				yDelta -= 1;
				break;
			case RIGHT:
				xDelta += 1;
				break;
			case DOWN:
				yDelta += 1;
				break;
			}
		}
		
	}
	
	public void updateGame() {
		updateAnimationTick();
		setAnimation();
		updatePos();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		
		g.drawImage(animations[playerAction][aniIndex], (int)xDelta, (int)yDelta, 118, 118,  null);
	}

	



}