package levels;

import utils.LoadSave;


public class Level {
	
	public int[][] levelData;
	
	public Level() {
		levelData = LoadSave.GetLevelData(LoadSave.LEVEL_ONE);
	}
}
