package objects;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Playing;
import levels.Level;
import utils.LoadSave;
import static utils.Constans.ObjectConstants.*;

public class ObjectManager {

    private long lastCallTime = 0;

    private Playing playing;
    private BufferedImage[][] containerImg, chestImg, potionImg;
    private ArrayList<Potion> potions;
    private ArrayList<GameContainer> containers;
    private ArrayList<Spike> spikes;

    public ObjectManager(Playing playing) {
        this.playing = playing;
        potions = new ArrayList<>();
        containers = new ArrayList<>();
        spikes = new ArrayList<>();
        loadImgs();
    }

    public void checkSpikesTouched(Rectangle2D.Float hitbox) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastCallTime < 1500) {
            return;
        }
        lastCallTime = currentTime;

        if (spikes != null)
            for (Spike s : spikes)
                if (s.getHitbox().intersects(hitbox)) {
                    playing.getPlayer().changeHealth(-10);
                    System.out.println("spike hit");
                    return;
                }
    }

    public void checkObjectTouched(Rectangle2D.Float hitbox) {
        if (potions != null)
            for (Potion p : potions)
                if (p.isActive())
                    if (hitbox.intersects(p.getHitbox())) {
                        p.setActive(false);
                        applyEffectToPlkayer(p);
                    }
    }

    public void applyEffectToPlkayer(Potion p) {
        if (p.getObjType() == RED_POTION || p.getObjType() == BLUE_POTION) {
            playing.getPlayer().changeHealth(RED_POTION_VALUE);
        } else {
            playing.getPlayer().changePower(BLUE_POTION_VALUE);
        }
    }

    public void checkObjectHit(Rectangle2D.Float attackbox) {
        if (containers != null)
            for (GameContainer gc : containers)
                if (gc.isActive() && !gc.doAnimation)
                    if (attackbox.intersects(gc.getHitbox())) {
                        gc.setAnimation(true);
                        int type = 0;
                        if (gc.getObjType() == BARREL)
                            type = 1;
                        potions.add(new Potion((int) (gc.getHitbox().x + gc.getHitbox().width / 2),
                                (int) (gc.getHitbox().y - gc.getHitbox().height / 2), type));

                    }
    }

    public void loadObjects(Level currentLevel) {
        potions = new ArrayList<>(currentLevel.getPotions());
        containers = new ArrayList<>(currentLevel.getContainers());
        spikes = currentLevel.getSpikes();
    }

    public void loadImgs() {

        BufferedImage containerSprite = LoadSave.GetSpriteAtlas(LoadSave.CONTAINER);
        containerImg = new BufferedImage[2][8];
        for (int j = 0; j < containerImg.length; j++)
            for (int i = 0; i < containerImg[j].length; i++)
                containerImg[j][i] = containerSprite.getSubimage(i * 40, j * 30, 40, 30);

        BufferedImage potionSprite = LoadSave.GetSpriteAtlas(LoadSave.OBJECT);
        potionImg = new BufferedImage[22][15];
        for (int j = 0; j < potionImg.length; j++)
            for (int i = 0; i < potionImg[j].length; i++) {
                potionImg[j][i] = potionSprite.getSubimage(i * 32, j * 32, 32, 32);
            }

    }

    public void update() {
        if (potions != null)
            for (Potion p : potions)
                if (p.isActive())
                    p.update();
        if (containers != null)
            for (GameContainer gc : containers)
                if (gc.isActive())
                    gc.update();

    }

    public void draw(Graphics g, int xLvlOffset) {
        drawPotions(g, xLvlOffset);
        drawContainers(g, xLvlOffset);
        // drawTraps(g, xLvlOffset);
    }

    private void drawTraps(Graphics g, int xLvlOffset) {
        if (spikes != null)
            for (Spike s : spikes) {
                s.drawHitbox(g);
                System.out.println("drawn");
            }
    }

    private void drawContainers(Graphics g, int xLvlOffset) {
        for (GameContainer gc : containers)
            if (gc.isActive()) {
                int index = 1;
                int offsetY = -5;
                if (gc.getObjType() == BOX) {
                    index = 0;
                    offsetY = 6;
                }
                g.drawImage(containerImg[index][gc.getAniIndex()],
                        (int) (gc.getHitbox().x - gc.getxDrawOffset() - xLvlOffset),
                        (int) (gc.getHitbox().y - gc.getyDrawOffset()) + offsetY,
                        CONTAINER_WIDTH,
                        CONTAINER_HEIGHT,
                        null);
                // gc.drawHitbox(g);
            }
    }

    private void drawPotions(Graphics g, int xLvlOffset) {
        if (potions != null)
            for (Potion p : potions)
                if (p.isActive()) {
                    if (p.getObjType() == RED_POTION) {
                        g.drawImage(
                                potionImg[9][0],
                                (int) (p.getHitbox().x - p.getxDrawOffset() - xLvlOffset),
                                (int) (p.getHitbox().y - p.getyDrawOffset()),
                                POTION_WIDTH,
                                POTION_HEIGHT,
                                null);
                    } else if (p.getObjType() == BLUE_POTION) {
                        g.drawImage(potionImg[9][1],
                                (int) (p.getHitbox().x - p.getxDrawOffset() - xLvlOffset),
                                (int) (p.getHitbox().y - p.getyDrawOffset()),
                                POTION_WIDTH,
                                POTION_HEIGHT,
                                null);
                    } else {
                        g.drawImage(potionImg[14][0],
                                (int) (p.getHitbox().x - p.getxDrawOffset() - xLvlOffset),
                                (int) (p.getHitbox().y - p.getyDrawOffset()),
                                POTION_WIDTH,
                                POTION_HEIGHT,
                                null);
                    }
                    // p.drawHitbox(g);
                }
    }

    public void resetAll() {
        loadObjects(playing.getLevelManager().getCurrentLevel());
        if (potions != null)
            for (Potion p : potions)
                p.reset();
        if (containers != null)
            for (GameContainer gc : containers)
                gc.reset();
    }
}
