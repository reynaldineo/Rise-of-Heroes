package entities;

import static utils.Constans.Directions.DOWN;
import static utils.Constans.Directions.LEFT;
import static utils.Constans.Directions.RIGHT;
import static utils.Constans.Directions.UP;
import static utils.Constans.PlayerConstants.GetSpriteAmount;
import static utils.Constans.PlayerConstants.IDLE;
import static utils.Constans.PlayerConstants.RUNNING;
import static utils.Constans.PlayerConstants.ATTACK;
import static utils.HelpMethods.CanMoveHere;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import main.Game;
import utils.LoadSave;

public class Player extends Entity {
	private BufferedImage[][] animations;
	private int aniTick, aniIndex, aniSpeed = 30;
	private int playerAction = IDLE;
	private boolean moving = false, attacking = false;
	private boolean left, up, right, down;
	private float playerSpeed = 3.0f;
	private int[][] lvlData;
	private float xDrawOffset = 39 * Game.SCALE;
	private float yDrawOffset = 50 * Game.SCALE;

	public Player(float x, float y, int width, int height) {
		super(x, y, width, height);
		loadAnimations();
		initHitbox(x, y, 28 * Game.SCALE, 59 * Game.SCALE);
	}

	public void update() {
		updateAnimationTick();
		setAnimation();
		updatePos();
	}

	public void render(Graphics g) {
		g.drawImage(animations[playerAction][aniIndex], (int) (hitbox.x-xDrawOffset), (int) (hitbox.y-yDrawOffset),
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
		if (!left && !right && !up && !down)
			return;

		float xSpeed = 0, ySpeed = 0;

		if (left && !right)
			xSpeed = -playerSpeed;
		else if (!left && right)
			xSpeed = playerSpeed;

		if (up && !down)
			ySpeed = -playerSpeed;
		else if (!up && down)
			ySpeed = playerSpeed;

		if (!CanMoveHere(hitbox.x + xSpeed, hitbox.y + ySpeed, hitbox.width, hitbox.height, lvlData)) {
			hitbox.x += xSpeed;
			hitbox.y += ySpeed;
			moving = true;
		}

	}

	private void loadAnimations() {
		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
		animations = new BufferedImage[11][8];

		for (int j = 0; j < animations.length; j++)
			for (int i = 0; i < animations[j].length; i++)
				animations[j][i] = img.getSubimage(i * 56, j * 56, 56, 56);

	}

	public void loadLevelData(int[][] lvlData) {
		this.lvlData = lvlData;
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