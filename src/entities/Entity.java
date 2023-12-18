package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import main.Game;

public abstract class Entity {
    protected float x, y;
    protected int width, height;
    protected Rectangle2D.Float hitbox, attackBox;
    protected int aniTick, aniIndex;
    protected int state;
    protected float airSpeed;
    protected boolean inAir = false;
    protected float walkSpeed;

    protected Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    protected void drawAttackBox(Graphics g, int xLvlOffset) {
        g.setColor(Color.red);
        g.drawRect((int) (attackBox.x - xLvlOffset), (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
    }

    protected void drawHitbox(Graphics g, int xLvlOffset) {
        g.setColor(Color.blue);
        g.drawRect((int) hitbox.x - xLvlOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);

    }

    protected void initHitboxEnemy(float x, float y, int width, int height) {
        hitbox = new Rectangle2D.Float(x, y, width, height);
    }

    protected void initHitbox(int width, int height) {
        hitbox = new Rectangle2D.Float((int) x, (int) y, (int) (width * Game.SCALE), (int) (height * Game.SCALE));
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

}