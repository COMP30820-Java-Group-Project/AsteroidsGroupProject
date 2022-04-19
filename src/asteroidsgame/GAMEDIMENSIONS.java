package asteroidsgame;
public enum GAMEDIMENSIONS{
    SCREENWIDTH(1000), 
    SCREENHEIGHT(900);

    private int val;

    private GAMEDIMENSIONS(int v) {
        this.val = v;
    }  

    public int getVal() {
        return val;
    }
}