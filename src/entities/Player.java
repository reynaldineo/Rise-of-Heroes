package entities;

import static utils.Constans.Directions.DOWN;
import static utils.Constans.Directions.LEFT;
import static utils.Constans.Directions.RIGHT;
import static utils.Constans.Directions.UP;
import static utils.Constans.PlayerConstants.GetSpriteAmount;
import static utils.Constans.PlayerConstants.IDLE;
import static utils.Constans.PlayerConstants.RUNNING;
import static utils.Constans.PlayerConstants.ATTACK;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Player extends Entity {
	private BufferedImage[][] animations;
	private int aniTick, aniIndex, aniSpeed = 30;
	private int playerAction = IDLE;
	private boolean moving = false, attacking = false;
	private boolean left,up,right,down;
	private float playerSpeed = 2.0f;
	
	public Player(float x, float y) {
        super(x, y);
        loadAnimations();
    }


	public void update() {
		updateAnimationTick();
		setAnimation();
		updatePos();
	}
	
	public void render(Graphics g) {
		g.drawImage(animations[playerAction][aniIndex], (int)x, (int)y, 118, 118,  null);
	}
	
	private void updateAnimationTick() {
		
		aniTick++;
		if(aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if(aniIndex >= GetSpriteAmount(playerAction)) {
				aniIndex = 0;
				attacking = false;
			}
		}
	}
	
	private void setAnimation() {
		int startAni = playerAction;
		
		if(moving)
			playerAction = RUNNING;
		else 
			playerAction = IDLE;
		
		if(attacking)
			playerAction = ATTACK;
		
		if(startAni != playerAction) {
			resetAni();
		}
		
	}
	
	private void resetAni() {
		aniTick = 0;
		aniIndex = 0;
		
	}


	private void updatePos() {
		moving = false;
		
		if(left && !right) {
			x -= playerSpeed; 
			moving = true;
		}else if(!left && right) {
			x += playerSpeed; 
			moving = true;
		}
		
		if(up && !down) {
			y -= playerSpeed; 
			moving = true;
		}else if(!up && down) {
			y += playerSpeed; 
			moving = true;
		}
		
		
	}
	
	private void loadAnimations() {
		InputStream is = getClass().getResourceAsStream("/Char/generic_char/png/blue/char_blue_1.png");	
		try {
			BufferedImage img = ImageIO.read(is);
			animations = new BufferedImage[11][8];
			
			for(int j = 0; j < animations.length; j++)
				for(int i = 0; i < animations[j].length; i++)
					animations[j][i] = img.getSubimage(i*56, j*56, 56, 56);
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


	public boolean isLeft() {
		return left;
	}


	public void setLeft(boolean left) {
		this.left = left;
	}


	public boolean isUp() {
		return up;
	}


	public void setUp(boolean up) {
		this.up = up;
	}


	public boolean isRight() {
		return right;
	}


	public void setRight(boolean right) {
		this.right = right;
	}


	public boolean isDown() {
		return down;
	}


	public void setDown(boolean down) {
		this.down = down;
	}


	public void resetDirBoooleands() {
		left = false;
		right = false;
		up = false;
		down = false;
	}
	
	public void setAttacking(Boolean attacking) {
		this.attacking = attacking;
	}
	
}
