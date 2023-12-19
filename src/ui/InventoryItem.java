package ui;

import static utils.Constans.ObjectConstants.APPLE;
import static utils.Constans.ObjectConstants.BLUE_POTION;
import static utils.Constans.ObjectConstants.RED_POTION;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import gamestates.Playing;
import utils.LoadSave;

public class InventoryItem {

    protected int objType;
    protected BufferedImage[][] itemImg, clikedImg, itemInfoImg;
    protected int xIndex, yIndex;
    protected int x, y;
    protected boolean mouseOver, mousePressed;
    protected Rectangle bounds;
    protected String[][] nama;
    protected Playing playing;

    public InventoryItem(int objType, Playing playing) {
        this.playing = playing;
        setType(objType);
        loadImgs();
        setInfo();
    }

    public void setInfo() {
        nama = new String[5][10];
        nama[0][0] = "Red Potion";
        nama[1][0] = "Blue Potion";
        nama[2][0] = "Apple";
        nama[0][1] = "Restore large";
        nama[0][2] = "amount of hp";
        nama[1][1] = "Restore power";
        nama[2][1] = "Restore a litle";
        nama[2][2] = "amount of hp";
    }

    private void setType(int objType) {
        this.objType = objType;
        switch (objType) {
            case RED_POTION:
                xIndex = 9;
                yIndex = 0;
                break;
            case BLUE_POTION:
                xIndex = 9;
                yIndex = 1;
                break;
            case APPLE:
                xIndex = 14;
                yIndex = 0;
                break;

            default:
                xIndex = 0;
                yIndex = 0;
                break;
        }

    }

    public void loadImgs() {
        BufferedImage itemSprite = LoadSave.GetSpriteAtlas(LoadSave.ITEM);
        itemImg = new BufferedImage[22][15];
        for (int j = 0; j < itemImg.length; j++)
            for (int i = 0; i < itemImg[j].length; i++)
                itemImg[j][i] = itemSprite.getSubimage(i * 32, j * 32, 32, 32);

        BufferedImage clikedSprite = LoadSave.GetSpriteAtlas(LoadSave.ITEM_CLICKED);
        clikedImg = new BufferedImage[22][15];
        for (int j = 0; j < clikedImg.length; j++)
            for (int i = 0; i < clikedImg[j].length; i++) {
                clikedImg[j][i] = clikedSprite.getSubimage(i * 32, j * 32, 32, 32);
            }

        BufferedImage infoSprite = LoadSave.GetSpriteAtlas(LoadSave.ITEM_INFO);
        itemInfoImg = new BufferedImage[22][15];
        for (int j = 0; j < itemInfoImg.length; j++)
            for (int i = 0; i < itemInfoImg[j].length; i++) {
                itemInfoImg[j][i] = infoSprite.getSubimage(i * 32, j * 32, 32, 32);
            }

    }

    public void draw(Graphics g, int x, int y) {
        this.x = x;
        this.y = y;
        if (mouseOver || mousePressed)
            g.drawImage(clikedImg[xIndex][yIndex], x, y, 52, 52, null);
        else
            g.drawImage(itemImg[xIndex][yIndex], x, y, 52, 52, null);
    }

    public void drawInfo(Graphics g) {
        g.drawImage(itemInfoImg[xIndex][yIndex], 195, 230, 52, 52, null);
        g.setFont(new Font("SansSerif", Font.BOLD, 17));
        switch (objType) {
            case 0:
                g.drawString(nama[objType][0], 175, 300);
                g.drawString(nama[objType][1], 168, 345);
                g.drawString(nama[objType][2], 168, 365);
                break;
            case 1:
                g.drawString(nama[objType][0], 172, 300);
                g.drawString(nama[objType][1], 160, 345);
                break;
            case 2:
                g.drawString(nama[objType][0], 195, 300);
                g.drawString(nama[objType][1], 167, 345);
                g.drawString(nama[objType][2], 168, 365);
                break;
        }
    }

    public void update() {
    }

    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public Rectangle getBounds() {
        return bounds = new Rectangle(x, y, 52, 52);
    }

    public void useItem() {
        Random rand = new Random();
        switch (objType) {
            case RED_POTION:
                System.out.println("Red Potion");
                playing.getPlayer().changeHealth(40 + (rand.nextInt(4) + 10));
                break;
            case BLUE_POTION:
                System.out.println("Blue Potion");
                playing.getPlayer().changePower(50 + (rand.nextInt(3) + 10));
                break;
            case APPLE:
                System.out.println("Apple");
                playing.getPlayer().changeHealth(10 + (rand.nextInt(4) + 5));
                break;
        }
    }
}