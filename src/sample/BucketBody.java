package sample;

public class BucketBody {

    private int y_top;
    private int y_bottom;
    private int x_left;
    private double x_right;
    private int largeur_bandouliere;
    private Float marge;

    public BucketBody(int x_left, double x_right, int y_top, int y_bottom, int largeur_b, Float marge) {
        this.y_top = y_top;
        this.y_bottom = y_bottom;
        this.x_left = x_left;
        this.x_right = x_right;
        this.largeur_bandouliere = largeur_b;
        this.marge = marge;
    }

    public BucketBody(){}

    @Override
    public String toString(){
        return String.format("M %d %d L %d %d L %.2f %d L %.2f %d L %.2f %d L %.2f %d L %.2f %d L %.2f %d L %.2f %d L %.2f %d L %.2f %d L %.2f %d L %d %d z",
                x_left, y_top,
                x_left, y_bottom,
                x_right, y_bottom,
                x_right, y_top,
                getAxisBandouliereRight() + (largeur_bandouliere / 2), y_top,
                getAxisBandouliereRight() + (largeur_bandouliere / 2), y_top - 50,
                getAxisBandouliereRight() - (largeur_bandouliere / 2), y_top - 50,
                getAxisBandouliereRight() - (largeur_bandouliere / 2), y_top,
                getAxisBandouliereLeft()  + (largeur_bandouliere / 2), y_top,
                getAxisBandouliereLeft()  + (largeur_bandouliere / 2), y_top - 50,
                getAxisBandouliereLeft()  - (largeur_bandouliere / 2), y_top - 50,
                getAxisBandouliereLeft()  - (largeur_bandouliere / 2), y_top,
                x_left, y_top).replace(',', '.');
    }


    public double getAxis_x() {
        return x_left + (x_right - x_left) / 2;
    }

    public double getAxisBandouliereLeft(){
        return x_left + (getAxis_x() - x_left) / 2;
    }

    public double getAxisBandouliereRight(){
        return getAxis_x() + (x_right - getAxis_x()) / 2;
    }

    public double getY_top() {
        return y_top;
    }

    public void setY_top(int y_top) {
        this.y_top = y_top;
    }

    public double getY_bottom() {
        return y_bottom;
    }

    public void setY_bottom(int y_bottom) {
        this.y_bottom = y_bottom;
    }

    public double getX_left() {
        return x_left;
    }

    public void setX_left(int x_left) {
        this.x_left = x_left;
    }

    public double getX_right() {
        return x_right;
    }

    public void setX_right(double x_right) {
        this.x_right = x_right;
    }

    public int getLargeur_bandouliere() {
        return largeur_bandouliere;
    }

    public void setLargeur_bandouliere(int largeur_bandouliere) {
        this.largeur_bandouliere = largeur_bandouliere;
    }


    public Float getMarge() {
        return marge;
    }

    public void setMarge(Float marge) {
        this.marge = marge;
    }

    public double getAxisMargeLeft() {
        return x_left + marge;
    }

    public double getAxisMargeRight() {
        return x_right - marge;
    }
}
