package levels;

import static utils.HelpMethods.GetPlayerSpawn;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import main.Game;
import objects.Potion;
import objects.Spike;
import objects.GameContainer;
import utils.LoadSave;
import utils.HelpMethods;

public class Level {

	public int[][] levelData;
	public int[][] levelDataBG;
	public int levelNow;

	private ArrayList<Potion> potions;
	private ArrayList<GameContainer> containers;
	private ArrayList<Spike> spikes;

	public BufferedImage[] levelSprite;
	public BufferedImage[] levelSpriteBackground;
	private BufferedImage img, imgBG;
	private Point playerSpawn;

	private int lvlTileWide;
	private int maxTileOffset;
	private int maxLvlOffsetX;

	public Level(int levelNow) {
		this.levelNow = levelNow;
		getData();
		importSprite();
		createPotions();
		createContainers();
		createSpikes();
		calcLvlOffsets();
		calcPlayerSpawn();
	}

	private void createSpikes() {
		spikes = HelpMethods.getSpikes(levelData);
	}

	private void createContainers() {
		containers = HelpMethods.getContainers(levelData);
	}

	private void createPotions() {
		potions = new ArrayList<>();
	}

	private void calcPlayerSpawn() {
		playerSpawn = GetPlayerSpawn(levelData);
	}

	private void calcLvlOffsets() {
		lvlTileWide = levelData[0].length;
		maxTileOffset = lvlTileWide - Game.TILES_IN_WIDTH;
		maxLvlOffsetX = maxTileOffset * Game.TILES_SIZE;

		maxTileOffset = lvlTileWide - Game.TILES_IN_WIDTH;
		maxLvlOffsetX = maxTileOffset * Game.TILES_SIZE;
	}

	public void getData() {
		switch (levelNow) {
			case 0:
				levelData = LoadSave.GetLevelData(LoadSave.LEVEL_ONE);
				levelDataBG = LoadSave.GetLevelData(LoadSave.LEVEL_ONE_BACKGROUND);
				img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS_0);
				imgBG = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS_0_BACKGROUND);
				levelSprite = new BufferedImage[56];
				levelSpriteBackground = new BufferedImage[120];
				break;
			case 1:
				levelData = LoadSave.GetLevelData(LoadSave.LEVEL_TWO);
				levelDataBG = LoadSave.GetLevelData(LoadSave.LEVEL_TWO_BACKGROUND);
				img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS_1);
				imgBG = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS_1_BACKGROUND);
				levelSprite = new BufferedImage[256];
				levelSpriteBackground = new BufferedImage[1024];
				break;
			default:
				levelData = LoadSave.GetLevelData(LoadSave.LEVEL_ONE);
				levelDataBG = LoadSave.GetLevelData(LoadSave.LEVEL_ONE_BACKGROUND);
				img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS_0);
				imgBG = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS_0_BACKGROUND);
				levelSprite = new BufferedImage[56];
				levelSpriteBackground = new BufferedImage[120];
				break;

		}
	}

	public void printLevel() {
		System.out.println(levelNow);
		for (int j = 0; j < levelData.length; j++) {
			// This will print the row number
			System.out.print("Row " + j + ": ");

			for (int i = 0; i < levelData[j].length; i++) {
				// This will print each element in the row followed by a space
				System.out.print(levelData[j][i] + " ");
			}

			// This will move to the next line after printing all elements in a row
			System.out.println();
		}
	}

	private void importSprite() {

		for (int j = 0; j < img.getHeight() / 32; j++) {
			for (int i = 0; i < img.getWidth() / 32; i++) {
				int index = j * (img.getWidth() / 32) + i;
				levelSprite[index] = img.getSubimage(i * 32, j * 32, 32, 32);
			}
		}

		for (int j = 0; j < imgBG.getHeight() / 32; j++) {
			for (int i = 0; i < imgBG.getWidth() / 32; i++) {
				int index = j * (imgBG.getWidth() / 32) + i;
				levelSpriteBackground[index] = imgBG.getSubimage(i * 32, j * 32, 32, 32);
			}
		}

	}

	public int[][] getLevelData() {
		return levelData;
	}

	public int[][] getLevelDataBG() {
		return levelDataBG;
	}

	public int getLvlTileWide() {
		return lvlTileWide;
	}

	public int getMaxTileOffset() {
		return maxTileOffset;
	}

	public int getMaxLvlOffsetX() {
		return maxLvlOffsetX;
	}

	public Point getPlayerSpawn() {
		return playerSpawn;
	}

	public ArrayList<Potion> getPotions() {
		return potions;
	}

	public ArrayList<GameContainer> getContainers() {
		return containers;
	}

	public ArrayList<Spike> getSpikes() {
		return spikes;
	}
}
