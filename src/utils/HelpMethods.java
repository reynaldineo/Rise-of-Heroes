package utils;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import javax.sound.midi.Receiver;

import main.Game;

public class HelpMethods {

	public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
		return !IsSolid(x, y, lvlData) ||
				!IsSolid(x + width, y, lvlData) ||
				!IsSolid(x, y + height, lvlData) ||
				!IsSolid(x + width, y + height, lvlData);
	}

	private static boolean IsSolid(float x, float y, int[][] lvlData) {
		if (x < 0 || x >= Game.GAME_WIDTH)
			return false;
		if (y < 0 || y >= Game.GAME_HEIGHT)
			return false;

		float xIndex = x / Game.TILES_SIZE;
		float yIndex = y / Game.TILES_SIZE;

		int value = lvlData[(int) yIndex][(int) xIndex];

		if (value >= 56 || value < 0 || value == 15 || value == 38 || value == 31)
			return true;
		return false;
	}

	public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
		int currentTile = (int) (hitbox.x / Game.TILES_SIZE);
		if (xSpeed > 0) {
			int tileXpos = currentTile * Game.TILES_SIZE;
			int xOffset = (int) (Game.TILES_SIZE - hitbox.width);
			return tileXpos + xOffset - 1;
		} else
			return currentTile * Game.TILES_SIZE;

	}

	public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
		int currentTile = (int) (hitbox.y / Game.TILES_SIZE);
		if (airSpeed > 0) {
			int tileYpos = currentTile * Game.TILES_SIZE;
			int yOffset = (int) (Game.TILES_SIZE - hitbox.height);
			return tileYpos + yOffset - 1;
		} else
			return currentTile * Game.TILES_SIZE;

	}

	public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
		if (!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData))
			if (!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
				return false;
		return true;
	}
}
