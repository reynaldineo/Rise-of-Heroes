package objects;

import main.Game;

public class Spike extends GameObject {

    public Spike(int x, int y, int objType) {
        super(x, y, objType);

        initHitbox(32, 24);
        xDrawOffset = (int) (0 * Game.SCALE);
        yDrawOffset = (int) (8 * Game.SCALE);
        hitbox.y += yDrawOffset;
    }

}
