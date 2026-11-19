/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package custom;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Icon;
import javax.swing.JButton;

/**
 *
 * @author ANGGUN Z
 */
public class ButtonCustom extends JButton {

    private boolean over;
    private Color fill;
    private Icon icon;
    
    private Color fillOriginal;
    private Color fillOver;
    private Color fillClick;
    private int strokeWidth;
    private int roundedCorner;
    private int fontSize;
    private GayaFont fontGaya;
    
    
    public enum GayaFont {
    PLAIN(java.awt.Font.PLAIN),
    BOLD(java.awt.Font.BOLD),
    ITALIC(java.awt.Font.ITALIC),
    BOLD_ITALIC(java.awt.Font.BOLD | java.awt.Font.ITALIC);

    private final int nilai;

    GayaFont(int nilai) {
        this.nilai = nilai;
    }

    
    public int getNilai() {
        return nilai;
    }
}

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
        repaint();
    }

    public Color getFillOriginal() {
        return fillOriginal;
    }

    public void setFillOriginal(Color fillOriginal) {
        this.fillOriginal = fillOriginal;
        if(!over){
            fill = fillOriginal;
            repaint();
        }
    }

    public Color getFillOver() {
        return fillOver;
    }

    public void setFillOver(Color fillOver) {
        this.fillOver = fillOver;
        if(over){
            fill = fillOver;
            repaint();
        }
    }

    public Color getFillClick() {
        return fillClick;
    }

    public void setFillClick(Color fillClick) {
        this.fillClick = fillClick;
        if(over){
            fill = fillClick;
            repaint();
        }
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
        repaint();
    }

    public int getRoundedCorner() {
        return roundedCorner;
    }

    public void setRoundedCorner(int roundedCorner) {
        this.roundedCorner = roundedCorner;
        repaint();
    }
    
    public int getFontSize() {
    return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
        aturFont();
    }

    public GayaFont getFontGaya() {
        return fontGaya;
    }

    public void setFontGaya(GayaFont fontGaya) {
        this.fontGaya = fontGaya;
        aturFont();
    }

    private void aturFont() {
        setFont(getFont().deriveFont(fontGaya.getNilai(), (float) fontSize));
        repaint();
    }

    private Color strokeColor = new Color(255, 255, 255);

    public Color getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
    Graphics2D g2d = (Graphics2D) g.create();
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    float s = strokeWidth;
    // Stroke digambar di tengah garis path, jadi digeser setengah lebar garis
    // agar tidak terpotong di tepi komponen
    java.awt.geom.RoundRectangle2D.Float shape = new java.awt.geom.RoundRectangle2D.Float(
            s / 2f, s / 2f,
            getWidth() - s, getHeight() - s,
            roundedCorner, roundedCorner);

    // Isi
    g2d.setColor(fill);
    g2d.fill(shape);

    // Garis border mengikuti lengkungan
    if (strokeWidth > 0 && strokeColor != null) {
        g2d.setStroke(new java.awt.BasicStroke(s));
        g2d.setColor(strokeColor);
        g2d.draw(shape);
    }

    g2d.dispose();
    super.paintComponent(g);
}
    
    @Override
    public void setBackground(Color bg) {
    super.setBackground(bg);
    if (fillOriginal != null) {   
        setFillOriginal(bg);
    }
}
    
    
    
    public ButtonCustom() {
        fillOriginal = new Color(47,22,138);
        fillOver = new Color(38,17,112);
        fillClick = new Color(28,12,82);
        strokeWidth = 2;
        roundedCorner =20;
        fill = fillOriginal;
        icon = null;
        
        setOpaque(false);
        setBorder(null);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBackground(fillOriginal);
        setForeground(Color.WHITE);
        fontSize = 14;
        fontGaya = GayaFont.PLAIN;
        aturFont();
        
        addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e) {
                fill = fillOver;
                over = true;
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                fill = fillClick;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(over){
                    fill = fillOver;
                }else {
                    fill = fillOriginal;
                }
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                fill = fillOriginal;
                over =  false;
                repaint();
            }
           
            
        });
    }
    
    
}
