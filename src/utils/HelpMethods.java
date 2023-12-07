package utils;

import main.Game;

public class HelpMethods {

	public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
		return !IsSolid(x, y, lvlData) ||
				!IsSolid(x + width, y, lvlData) ||
				!IsSolid(x, y + height, lvlData) ||
				!IsSolid(x + width, y + height, lvlData);
	}

	private static boolean IsSolid(float x, float y, int[][] lvlData) {
		if (x < 0 || x > Game.GAME_WIDTH)
			return false;
		if (y < 0 || y > Game.GAME_HEIGHT)
			return false;

		float xIndex = x / Game.TILES_SIZE;
		float yIndex = y / Game.TILES_SIZE;

		int value = lvlData[(int) yIndex][(int) xIndex];

		if (value >= 56 || value < 0 || value == 15 || value == 38 || value == 31)
			return true;
		return false;
	}
}
