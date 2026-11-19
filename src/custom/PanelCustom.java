package custom;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Path2D;
import javax.swing.JPanel;

public class PanelCustom extends JPanel {

    private int roundTopLeft = 0;
    private int roundTopRight = 0;
    private int roundBottomLeft = 0;
    private int roundBottomRight = 0;

    private Color strokeColor = Color.WHITE;
    private int strokeWidth = 0;   // 0 = tanpa garis

    public PanelCustom() {
        setOpaque(false);
    }

    // ===== Getter & Setter =====
    public int getRoundTopLeft() { return roundTopLeft; }
    public void setRoundTopLeft(int v) { this.roundTopLeft = v; repaint(); }

    public int getRoundTopRight() { return roundTopRight; }
    public void setRoundTopRight(int v) { this.roundTopRight = v; repaint(); }

    public int getRoundBottomLeft() { return roundBottomLeft; }
    public void setRoundBottomLeft(int v) { this.roundBottomLeft = v; repaint(); }

    public int getRoundBottomRight() { return roundBottomRight; }
    public void setRoundBottomRight(int v) { this.roundBottomRight = v; repaint(); }

    public Color getStrokeColor() { return strokeColor; }
    public void setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
        repaint();
    }

    public int getStrokeWidth() { return strokeWidth; }
    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
        repaint();
    }

    // ===== Painting =====
    @Override
    protected void paintComponent(Graphics graphic) {
        Graphics2D g2 = (Graphics2D) graphic.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Shape shape = createShape();

        // Isi
        g2.setColor(getBackground());
        g2.fill(shape);

        // Garis mengikuti bentuk yang sama
        if (strokeWidth > 0 && strokeColor != null) {
            g2.setStroke(new BasicStroke(strokeWidth));
            g2.setColor(strokeColor);
            g2.draw(shape);
        }

        g2.dispose();
        super.paintComponent(graphic);
    }

    private Shape createShape() {
        // Geser setengah lebar garis supaya garis tidak terpotong di tepi komponen
        double inset = strokeWidth / 2.0;
        double x = inset;
        double y = inset;
        double w = getWidth() - strokeWidth;
        double h = getHeight() - strokeWidth;

        // Nilai round dianggap diameter (sama seperti sebelumnya), dijadikan radius
        double maxR = Math.min(w, h) / 2.0;
        double tl = Math.min(roundTopLeft / 2.0, maxR);
        double tr = Math.min(roundTopRight / 2.0, maxR);
        double bl = Math.min(roundBottomLeft / 2.0, maxR);
        double br = Math.min(roundBottomRight / 2.0, maxR);

        final double k = 0.5522847498; // konstanta untuk mendekati busur lingkaran

        Path2D.Double p = new Path2D.Double();
        p.moveTo(x + tl, y);

        // sisi atas -> sudut kanan atas
        p.lineTo(x + w - tr, y);
        p.curveTo(x + w - tr + tr * k, y, x + w, y + tr - tr * k, x + w, y + tr);

        // sisi kanan -> sudut kanan bawah
        p.lineTo(x + w, y + h - br);
        p.curveTo(x + w, y + h - br + br * k, x + w - br + br * k, y + h, x + w - br, y + h);

        // sisi bawah -> sudut kiri bawah
        p.lineTo(x + bl, y + h);
        p.curveTo(x + bl - bl * k, y + h, x, y + h - bl + bl * k, x, y + h - bl);

        // sisi kiri -> sudut kiri atas
        p.lineTo(x, y + tl);
        p.curveTo(x, y + tl - tl * k, x + tl - tl * k, y, x + tl, y);

        p.closePath();
        return p;
    }
}