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
import utils.LoadSave;

import static utils.Constans.PlayerConstants.*;
import static utils.Constans.Directions.*;
import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;
import levels.*;


public class GamePanel extends JPanel {

	private MouseInputs mouseInputs;
	private float xDelta = 100, yDelta = 100;
	private BufferedImage img;
	private BufferedImage[][] animations;
	private int aniTick, aniIndex, aniSpeed = 15;
	private int playerAction = IDLE;
	private int playerDir = -1;
	private boolean moving = false;
	private LevelManager levelManager;

	public GamePanel() {
		mouseInputs = new MouseInputs(this);
		
		importImg();
		loadAnimations();
		
		setPanelSize();
		addKeyListener(new KeyboardInputs(this));
		addMouseListener(mouseInputs);
		addMouseMotionListener(mouseInputs);
		
		levelManager = new LevelManager(this);

	}

	private void loadAnimations() {
		animations = new BufferedImage[11][8];
		
		for(int j = 0; j < animations.length; j++)
			for(int i = 0; i < animations[j].length; i++)
				animations[j][i] = img.getSubimage(i*56, j*56, 56, 56);
		
	}

	private void importImg() {
		img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
	}

	private void setPanelSize() {
		// 56 x 40 = 2240 wide
		// 56 x 25 = 1400 height
		
		Dimension size = new Dimension(GAME_WIDTH,GAME_HEIGHT);
		setPreferredSize(size);
		System.out.println("size: " + GAME_WIDTH + " : " + GAME_HEIGHT);
		
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

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		updateAnimationTick();
		
		setAnimation();
		updatePos();
		levelManager.draw(g);
		g.drawImage(animations[playerAction][aniIndex], (int)xDelta, (int)yDelta, 118, 118,  null);
	}
}