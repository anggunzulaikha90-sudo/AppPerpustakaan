package custom;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class PanelShadow extends JPanel {

    private int shadowSize = 44;      
    private int blurRadius = 22;      
    private int offsetY = 16;         
    private int shadowAlpha = 50;     
    private int roundedCorner = 32;
    private Color shadowColor = new Color(45, 22, 140);

    private BufferedImage shadowImage;

    public PanelShadow() {
        setOpaque(false);
        setBorder(new EmptyBorder(shadowSize, shadowSize, shadowSize, shadowSize));
    }

    public int getShadowSize() { return shadowSize; }
    public void setShadowSize(int v) {
        shadowSize = v;
        setBorder(new EmptyBorder(v, v, v, v));
        refresh();
    }

    public int getBlurRadius() { return blurRadius; }
    public void setBlurRadius(int v) { blurRadius = Math.max(1, v); refresh(); }

    public int getOffsetY() { return offsetY; }
    public void setOffsetY(int v) { offsetY = v; refresh(); }

    public int getShadowAlpha() { return shadowAlpha; }
    public void setShadowAlpha(int v) { shadowAlpha = Math.max(0, Math.min(255, v)); refresh(); }

    public int getRoundedCorner() { return roundedCorner; }
    public void setRoundedCorner(int v) { roundedCorner = v; refresh(); }

    public Color getShadowColor() { return shadowColor; }
    public void setShadowColor(Color c) { shadowColor = c; refresh(); }

    private void refresh() {
        shadowImage = null;
        repaint();
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    private BufferedImage createShadow(int w, int h) {
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB_PRE);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(shadowColor.getRed(), shadowColor.getGreen(),
                shadowColor.getBlue(), shadowAlpha));
        g2.fillRoundRect(shadowSize, shadowSize + offsetY,
                w - shadowSize * 2, h - shadowSize * 2, roundedCorner, roundedCorner);
        g2.dispose();

        // blur 2x (horizontal + vertical) supaya hasilnya mendekati gaussian
        int size = blurRadius * 2 + 1;
        float[] data = new float[size];
        for (int i = 0; i < size; i++) data[i] = 1f / size;
        ConvolveOp horizontal = new ConvolveOp(new Kernel(size, 1, data), ConvolveOp.EDGE_NO_OP, null);
        ConvolveOp vertical = new ConvolveOp(new Kernel(1, size, data), ConvolveOp.EDGE_NO_OP, null);
        for (int i = 0; i < 2; i++) {
            img = horizontal.filter(img, null);
            img = vertical.filter(img, null);
        }
        return img;
    }

    @Override
    protected void paintComponent(Graphics g) {
        int w = getWidth();
        int h = getHeight();

        if (shadowImage == null || shadowImage.getWidth() != w || shadowImage.getHeight() != h) {
            shadowImage = createShadow(w, h);
        }

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.drawImage(shadowImage, 0, 0, null);

        g2.setColor(getBackground());
        g2.fillRoundRect(shadowSize, shadowSize,
                w - shadowSize * 2, h - shadowSize * 2, roundedCorner, roundedCorner);
        g2.dispose();

        super.paintComponent(g);
    }
}