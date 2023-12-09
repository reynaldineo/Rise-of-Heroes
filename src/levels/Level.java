package levels;

import utils.LoadSave;

public class Level {

	public int[][] levelData;
	public int[][] levelDataBG;

	public Level() {
		levelData = LoadSave.GetLevelData(LoadSave.LEVEL_ONE);
		levelDataBG = LoadSave.GetLevelData(LoadSave.LEVEL_ONE_BACKGROUND);
	}

	public int[][] getLevelData() {
		return levelData;
	}

	public int[][] getLevelDataBG() {
		return levelDataBG;
	}
}
