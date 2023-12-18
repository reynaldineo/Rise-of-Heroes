package utils;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import entities.Crabby;
import main.Game;
import objects.GameContainer;
import objects.Potion;
import objects.Spike;

public class HelpMethods {

	public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
		return !IsSolid(x, y, lvlData) ||
				!IsSolid(x + width, y, lvlData) ||
				!IsSolid(x, y + height, lvlData) ||
				!IsSolid(x, y + height / 2, lvlData) ||
				!IsSolid(x + width, y + height / 2, lvlData) ||
				!IsSolid(x + width, y + height, lvlData);
	}

	private static boolean IsSolid(float x, float y, int[][] lvlData) {
		int maxWidth = lvlData[0].length * Game.TILES_SIZE;
		int maxHeight = lvlData.length * Game.TILES_SIZE;
		if (x < 0 || x >= maxWidth)
			return false;
		if (y < 0 || y >= Game.GAME_HEIGHT)
			return false;

		float xIndex = x / Game.TILES_SIZE;
		float yIndex = y / Game.TILES_SIZE;

		return IsTileSolid((int) xIndex, (int) yIndex, lvlData);
	}

	public static boolean IsTileSolid(int xIndex, int yIndex, int[][] lvlData) {
		int value = lvlData[yIndex][xIndex];

		if (value >= 256 || value < 0 || value == 15 || value == 38 || value == 31)
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
			int yOffset = (int) (Game.TILES_SIZE - hitbox.height / 2);
			return tileYpos + yOffset + 4;
		} else
			return currentTile * Game.TILES_SIZE + 3;

	}

	public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
		if (IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData)
				&& IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
			return false;
		return true;
	}

	public static Point GetPlayerSpawn(int[][] lvlData) {
		for (int j = 0; j < lvlData.length; j++)
			for (int i = 0; i < lvlData[0].length; i++)
				if (lvlData[j][i] == -2)
					return new Point(i * Game.TILES_SIZE, j * Game.TILES_SIZE);

		return new Point(200 * Game.TILES_SIZE, 300 * Game.TILES_SIZE);

	}

	public static ArrayList<Potion> getPotions(int[][] lvlData) {
		ArrayList<Potion> potions = new ArrayList<>();

		for (int j = 0; j < lvlData.length; j++) {
			for (int i = 0; i < lvlData[0].length; i++) {
				if (lvlData[j][i] == -5) {
					Random rand = new Random();
					potions.add(new Potion(i * Game.TILES_SIZE, j * Game.TILES_SIZE, rand.nextInt(3)));
				}
			}
		}
		return potions;
	}

	public static ArrayList<GameContainer> getContainers(int[][] lvlData) {
		ArrayList<GameContainer> containers = new ArrayList<>();

		for (int j = 0; j < lvlData.length; j++) {
			for (int i = 0; i < lvlData[0].length; i++) {
				if (lvlData[j][i] == -5) {
					Random rand = new Random();
					containers.add(new GameContainer(i * Game.TILES_SIZE, j * Game.TILES_SIZE, rand.nextInt(2) + 4));
				}
			}
		}
		return containers;
	}

	public static ArrayList<Spike> getSpikes(int[][] levelData) {
		ArrayList<Spike> spikes = new ArrayList<>();

		for (int j = 0; j < levelData.length; j++) {
			for (int i = 0; i < levelData[0].length; i++) {
				if (levelData[j][i] == 31) {
					spikes.add(new Spike(i * Game.TILES_SIZE, j * Game.TILES_SIZE, 7));
				}
			}
		}

		return spikes;
	}

	public static ArrayList<Crabby> getCrabs(int[][] levelData) {
		ArrayList<Crabby> crab = new ArrayList<>();
		for (int j = 0; j < levelData.length; j++)
			for (int i = 0; i < levelData[0].length; i++) {
				if (levelData[j][i] == -3)
					crab.add(new Crabby(i * Game.TILES_SIZE, j * Game.TILES_SIZE));
			}
		return crab;
	}

	public static boolean IsFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] lvlData) {
		return IsSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
	}

	public static boolean IsAllTilesWalkable(int xStart, int xEnd, int y, int[][] lvlData) {
		for (int i = 0; i < xEnd - xStart; i++) {
			if (IsTileSolid(xStart + i, y, lvlData))
				return false;
			if (!IsTileSolid(xStart + i, y + 1, lvlData))
				return false;
		}

		return true;
	}

	public static boolean IsSightClear(int[][] lvlData, Rectangle2D.Float firstHitbox, Rectangle2D.Float secondHitbox,
			int yTile) {
		int firstXTile = (int) (firstHitbox.x / Game.TILES_SIZE);
		int secondXTile = (int) (secondHitbox.x / Game.TILES_SIZE);

		if (firstXTile > secondXTile)
			return IsAllTilesWalkable(secondXTile, firstXTile, yTile, lvlData);
		else
			return IsAllTilesWalkable(firstXTile, secondXTile, yTile, lvlData);

	}
}
