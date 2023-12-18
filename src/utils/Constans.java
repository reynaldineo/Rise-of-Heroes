package utils;

import main.Game;

public class Constans {
	public static final float GRAVITY = 0.04f * Game.SCALE;

	public static class EnemyConstants {
		public static final int CRABBY = 0;

		public static final int IDLE = 0;
		public static final int RUNNING = 1;
		public static final int ATTACK = 2;
		public static final int HIT = 3;
		public static final int DEAD = 4;

		public static final int CRABBY_WIDTH_DEFAULT = 72;
		public static final int CRABBY_HEIGHT_DEFAULT = 32;

		public static final int CRABBY_WIDTH = (int) (CRABBY_WIDTH_DEFAULT * Game.SCALE);
		public static final int CRABBY_HEIGHT = (int) (CRABBY_HEIGHT_DEFAULT * Game.SCALE);

		public static final int CRABBY_DRAWOFFSET_X = (int) (26 * Game.SCALE);
		public static final int CRABBY_DRAWOFFSET_Y = (int) (9 * Game.SCALE);

		public static int GetSpriteAmount(int enemy_type, int enemy_state) {

			switch (enemy_type) {
				case CRABBY:
					switch (enemy_state) {
						case IDLE:
							return 9;
						case RUNNING:
							return 6;
						case ATTACK:
							return 7;
						case HIT:
							return 4;
						case DEAD:
							return 5;
					}
			}

			return 0;

		}

	}

	public static class ObjectConstants {

		public static final int RED_POTION = 0;
		public static final int BLUE_POTION = 1;
		public static final int APPLE = 2;
		public static final int BARREL = 4;
		public static final int BOX = 5;
		public static final int CHEST = 6;
		public static final int SPIKE = 7;

		public static final int RED_POTION_VALUE = 15;
		public static final int BLUE_POTION_VALUE = 10;

		public static final int CONTAINER_WIDTH_DEFAULT = 60;
		public static final int CONTAINER_HEIGHT_DEFAULT = 45;
		public static final int CONTAINER_WIDTH = (int) (Game.SCALE * CONTAINER_WIDTH_DEFAULT);
		public static final int CONTAINER_HEIGHT = (int) (Game.SCALE * CONTAINER_HEIGHT_DEFAULT);

		public static final int POTION_WIDTH_DEFAULT = 32;
		public static final int POTION_HEIGHT_DEFAULT = 32;
		public static final int POTION_WIDTH = (int) (Game.SCALE * POTION_WIDTH_DEFAULT);
		public static final int POTION_HEIGHT = (int) (Game.SCALE * POTION_HEIGHT_DEFAULT);

		public static final int SPIKE_WIDTH_DEFAULT = 32;
		public static final int SPIKE_HEIGHT_DEFAULT = 24;
		public static final int SPIKE_WIDTH = (int) (Game.SCALE * SPIKE_WIDTH_DEFAULT);
		public static final int SPIKE_HEIGHT = (int) (Game.SCALE * SPIKE_HEIGHT_DEFAULT);

		public static int GetSpriteAmount(int object_type) {
			switch (object_type) {
				case CHEST:
					return 7;
				case BARREL, BOX:
					return 8;
				default:
					return 0;

			}
		}
	}

	public static class UI {
		public static class Buttons {
			public static final int B_WIDTH_DEFAULT = 140;
			public static final int B_HEIGHT_DEFAULT = 56;
			public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE);
			public static final int B_HEIGT = (int) (B_HEIGHT_DEFAULT * Game.SCALE);
		}

		public static class PausedButtons {
			public static final int SOUND_SIZE_DEFAULT = 42;
			public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * Game.SCALE);
		}

		public static class UrmButtons {
			public static final int URM_SIZE_DEFAULT = 56;
			public static final int URM_SIZE = (int) (URM_SIZE_DEFAULT * Game.SCALE);
		}

		public static class VolumeButtons {
			public static final int VOLUME_SIZE_DEFAULT_WIDTH = 28;
			public static final int VOLUME_SIZE_DEFAULT_HEIGHT = 44;
			public static final int SLIDER_SIZE_DEFAULT_WIDTH = 215;

			public static final int VOLUME_WIDTH = (int) (VOLUME_SIZE_DEFAULT_WIDTH * Game.SCALE);
			public static final int VOLUME_HEIGHT = (int) (VOLUME_SIZE_DEFAULT_HEIGHT * Game.SCALE);
			public static final int SLIDER_WIDTH = (int) (SLIDER_SIZE_DEFAULT_WIDTH * Game.SCALE);
		}
	}

	public static class Directions {
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}

	public static class PlayerConstants {
		public static final int IDLE = 0;
		public static final int ATTACK = 1;
		public static final int RUNNING = 2;
		public static final int JUMP = 3;

		public static final int FALLING = 4;
		public static final int DAMAGE = 5;
		public static final int DEATH = 6;
		public static final int SPELL = 8;
		public static final int CROUCH = 9;
		public static final int SHIELD = 10;

		public static int GetSpriteAmount(int player_action) {

			switch (player_action) {

				case IDLE:
					return 6;
				case ATTACK:
					return 8;
				case RUNNING:
					return 8;
				case JUMP:
					return 8;
				case FALLING:
					return 8;
				case DAMAGE:
					return 4;
				case DEATH:
					return 8;
				case SPELL:
					return 8;
				case CROUCH:
					return 3;
				case SHIELD:
					return 3;
				default:
					return 1;
			}
		}
	}
}
