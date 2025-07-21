import processing.core.PApplet;


public class MySuperMainClassProcessing {
    public static void main(String[] args) throws Exception {
        System.out.println( "Heute ist " + PApplet.day() + "/" + PApplet.month() + "/" + PApplet.year() );

        // Run the Game sketch
        PApplet.main( "dac.Game" );
    }
}
