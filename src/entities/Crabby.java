package entities;

import static utils.Constans.EnemyConstants.*;

import main.Game;

public class Crabby extends Enemy {

    public Crabby(float x, float y) {
        super(x, y, CRABBY_WIDTH, CRABBY_HEIGHT, CRABBY);
        initHitboxEnemy(x, y, (int) (22 * Game.SCALE), (int) (19 * Game.SCALE));
    }

}