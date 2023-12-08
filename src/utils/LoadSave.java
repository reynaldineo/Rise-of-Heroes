package utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.io.BufferedReader;


import javax.imageio.ImageIO;

public class LoadSave {

	public static final String PLAYER_ATLAS = "Char/generic_char/png/blue/char_blue_1.png";
	public static final String LEVEL_ATLAS = "Enviroment/CastleTiles.png";
	public static final String LEVEL_ATLAS_BACKGROUND = "Background_IMG/Background_2.png";
	public static final String LEVEL_ONE = "LevelOne.txt";
	public static final String LEVEL_ONE_BACKGROUND = "LevelOneBG.txt";
	public static final String MENU_BUTTONS = "Menu/button_atlas.png";
	public static final String MENU_BACKGROUND = "Menu/menu_background.png";
	
	public static BufferedImage GetSpriteAtlas(String fileName) {
		BufferedImage img = null;
		InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);
		
		try {
			img = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return img;
	}
	
	public static int[][] GetLevelData(String fileName) {
	    String filePath = "/Map/" + fileName;

	    List<int[]> result = new ArrayList<>();

	    try (InputStream is = LoadSave.class.getResourceAsStream(filePath);
	         BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)))) {

	        String line;
	        while ((line = reader.readLine()) != null) {
	            String[] numbers = line.split(",");
	            int[] intArray = new int[numbers.length];

	            for (int i = 0; i < numbers.length; i++) {
	                intArray[i] = Integer.parseInt(numbers[i].trim());
	            }

	            result.add(intArray);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    return result.toArray(new int[0][]);
	}
}
