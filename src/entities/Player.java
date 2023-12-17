package entities;

import static utils.Constans.Directions.DOWN;
import static utils.Constans.Directions.LEFT;
import static utils.Constans.Directions.RIGHT;
import static utils.Constans.Directions.UP;
import static utils.Constans.PlayerConstants.*;
import static utils.HelpMethods.*;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import gamestates.Playing;
import main.Game;
import utils.LoadSave;
import static utils.Constans.GRAVITY;

public class Player extends Entity {
	private BufferedImage[][] animations;
	private BufferedImage[][] animationsLeft;
	private Playing playing;

	private int aniSpeed = 30;
	private boolean moving = false, attacking = false;
	private boolean left, right, jump;
	private boolean lastDir = true;
	private int[][] lvlData;
	private float xDrawOffset = 39 * Game.SCALE;
	private float yDrawOffset = 50 * Game.SCALE;

	// Jumping / Gravity
	private float jumpSpeed = -2.25f * Game.SCALE;
	private float fallSpeedAfterCollision = 0.5f * Game.SCALE;

	private boolean attackChecked;

	public Player(float x, float y, int width, int height, Playing playing) {
		super(x, y, width, height);
		this.playing = playing;
		this.walkSpeed = 1.4f * Game.SCALE;
		this.state = IDLE;
		loadAnimations();
		initHitbox(28, 55);
		initAttackBox();
	}

	private void initAttackBox() {
		attackBox = new Rectangle2D.Float(x, y, (int) (20 * Game.SCALE), (int) (20 * Game.SCALE));
	}

	public void setSpawn(Point spawn) {
		this.x = spawn.x;
		this.y = spawn.y;
		hitbox.x = x;
		hitbox.y = y;
	}

	public void update() {
		updateAttackBox();

		updatePos();

		if (moving)
			checkPotionTouched();

		if (attacking)
			checkAttack();

		updateAnimationTick();
		setAnimation();
	}

	private void checkAttack() {
		if (attackChecked || aniIndex != 1)
			return;
		attackChecked = true;
		// playing.checkEnemyHit(attackBox);
		playing.checkObjectHit(attackBox);
	}

	private void updateAttackBox() {
		if (right)
			attackBox.x = hitbox.x + hitbox.width + (int) (Game.SCALE * 10);
		else if (left)
			attackBox.x = hitbox.x - hitbox.width - (int) (Game.SCALE * 10);

		attackBox.y = hitbox.y + (Game.SCALE * 10);
	}

	private void checkPotionTouched() {
		playing.checkPotionTouched(hitbox);

	}

	public void render(Graphics g, int xLvlOffset) {
		if (lastDir)
			g.drawImage(animations[state][aniIndex], (int) (hitbox.x - xDrawOffset) - xLvlOffset,
					(int) (hitbox.y - yDrawOffset),
					width, height, null);
		else
			g.drawImage(animationsLeft[state][aniIndex], (int) (hitbox.x - xDrawOffset) - xLvlOffset,
					(int) (hitbox.y - yDrawOffset),
					width, height, null);
	}

	private void updateAnimationTick() {

		aniTick++;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= GetSpriteAmount(state)) {
				aniIndex = 0;
				attacking = false;
				attackChecked = false;
			}
		}
	}

	private void setAnimation() {
		int startAni = state;

		if (moving) {
			state = RUNNING;
			aniSpeed = 15;
		} else {
			state = IDLE;
			aniSpeed = 20;
		}

		if (inAir) {
			if (airSpeed < 0)
				state = JUMP;
			else
				state = FALLING;
		}

		if (attacking) {
			state = ATTACK;
			if (startAni != ATTACK) {
				aniIndex = 1;
				aniTick = 0;
				return;
			}
		}

		if (startAni != state) {
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

		if (!inAir)
			if ((!left && !right) || (left && right))
				return;

		float xSpeed = 0;

		if (left) {
			xSpeed -= walkSpeed;
			lastDir = false;
		}
		if (right) {
			xSpeed += walkSpeed;
			lastDir = true;
		}

		if (!inAir)
			if (!IsEntityOnFloor(hitbox, lvlData))
				inAir = true;

		if (inAir) {
			if (!CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
				hitbox.y += airSpeed;
				airSpeed += GRAVITY;
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

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public void resetDirBooleans() {
		left = false;
		right = false;

	}

	public void setAttacking(Boolean attacking) {
		this.attacking = attacking;
	}

	public void setJump(Boolean jump) {
		this.jump = jump;
	}

	public void resetAll() {
		resetDirBooleans();
		inAir = false;
		attacking = false;
		moving = false;
		state = IDLE;

		hitbox.x = x;
		hitbox.y = y;

		if (!IsEntityOnFloor(hitbox, lvlData))
			inAir = true;
	}

	public void changeHealth(int value) {
		// currentHealth += value;

		// if(currentHealth > maxHealth)
		// currentHealth = maxHealth;
		// else if(currentHealth < 0)
		// currentHealth = 0;

	}

	public void changePower(int bluePotionValue) {
		System.out.println("changePower");
	}

}
