package entities;

import static utils.Constans.Directions.DOWN;
import static utils.Constans.Directions.LEFT;
import static utils.Constans.Directions.RIGHT;
import static utils.Constans.Directions.UP;
import static utils.Constans.PlayerConstants.GetSpriteAmount;
import static utils.Constans.PlayerConstants.*;
import static utils.HelpMethods.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import main.Game;
import utils.LoadSave;

public class Player extends Entity {
	private BufferedImage[][] animations;
	private BufferedImage[][] animationsLeft;
	private int aniTick, aniIndex, aniSpeed = 30;
	private int playerAction = IDLE;
	private boolean moving = false, attacking = false;
	private boolean left, up, right, down, jump;
	private float playerSpeed = 1.0f * Game.SCALE;
	private boolean lastDir = true;
	private int[][] lvlData;
	private float xDrawOffset = 39 * Game.SCALE;
	private float yDrawOffset = 50 * Game.SCALE;
	private boolean inAir = false;

	// Jumping / Gravity
	private float airSpeed = 0.0f;
	private float gravity = 0.04f * Game.SCALE;
	private float jumpSpeed = -2.25f * Game.SCALE;
	private float fallSpeedAfterCollision = 0.5f * Game.SCALE;

	public Player(float x, float y, int width, int height) {
		super(x, y, width, height);
		loadAnimations();
		initHitbox(x, y, (int) (28 * Game.SCALE), (int) (59 * Game.SCALE));

	}

	public void update() {
		updateAnimationTick();
		setAnimation();
		updatePos();
	}

	public void render(Graphics g) {
		if(lastDir)
			g.drawImage(animations[playerAction][aniIndex], (int) (hitbox.x - xDrawOffset), (int) (hitbox.y - yDrawOffset),
				width, height, null);
		else
			g.drawImage(animationsLeft[playerAction][aniIndex], (int) (hitbox.x - xDrawOffset), (int) (hitbox.y - yDrawOffset),
					width, height, null);
		drawHitbox(g);
	}

	private void updateAnimationTick() {

		aniTick++;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= GetSpriteAmount(playerAction)) {
				aniIndex = 0;
				attacking = false;
			}
		}
	}

	private void setAnimation() {
		int startAni = playerAction;

		if (moving) {
			playerAction = RUNNING;
			aniSpeed = 15;
		} else {
			playerAction = IDLE;
			aniSpeed = 20;
		}

		if (inAir) {
			if (airSpeed < 0)
				playerAction = JUMP;
			else
				playerAction = FALLING;
		}

		if (attacking) {
			playerAction = ATTACK;
			aniSpeed = 15;
		}

		if (startAni != playerAction) {
			resetAni();
		}

	}

	private void resetAni() {
		aniTick = 0;
		aniIndex = 0;

	}

	private void updatePos() {
		moving = false;

		if (jump)
			jump();
		
		if (!left && !right && !inAir)
			return;

		float xSpeed = 0;

		if (left) {
			xSpeed -= playerSpeed;
			lastDir = false;
		}
		if (right) {
			xSpeed += playerSpeed;
			lastDir = true;
		}

		if (!inAir)
			if (!IsEntityOnFloor(hitbox, lvlData))
				inAir = true;

		if (inAir) {
			if (!CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
				hitbox.y += airSpeed;
				airSpeed += gravity;
				updateXpos(xSpeed);
			} else {
				hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
				if (airSpeed > 0)
					resetInAir();
				else
					airSpeed = fallSpeedAfterCollision;
				updateXpos(xSpeed);
			}
		} else
			updateXpos(xSpeed);
		moving = true;

	}

	private void jump() {
		if (inAir)
			return;
		inAir = true;
		airSpeed = jumpSpeed;

	}

	private void resetInAir() {
		inAir = false;
		airSpeed = 0;
	}

	private void updateXpos(float xSpeed) {
		if (!CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData))
			hitbox.x += xSpeed;
		else
			hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
	}

	private void loadAnimations() {
		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
		animations = new BufferedImage[11][8];

		for (int j = 0; j < animations.length; j++)
			for (int i = 0; i < animations[j].length; i++)
				animations[j][i] = img.getSubimage(i * 56, j * 56, 56, 56);
		
		 img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS_LEFT);
		animationsLeft = new BufferedImage[11][8];
		

		for (int j = 0; j < animationsLeft.length; j++)
			for (int i = 0; i < animationsLeft[j].length; i++)
				animationsLeft[j][i] = img.getSubimage(i * 56, j * 56, 56, 56);
	}

	public void loadLevelData(int[][] lvlData) {
		this.lvlData = lvlData;
		if (!IsEntityOnFloor(hitbox, lvlData))
			inAir = true;
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

	public void setJump(Boolean jump) {
		this.jump = jump;
	}

}
