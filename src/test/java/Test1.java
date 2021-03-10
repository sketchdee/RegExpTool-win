
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;

public class Test1 extends Application {
    public static String getClipText () throws IOException, UnsupportedFlavorException {
        String clipstr = "";
        Clipboard sysClip = Toolkit.getDefaultToolkit ( ).getSystemClipboard ( );
        Transferable cliptf = sysClip.getContents ( null );
        if ( cliptf != null ) {
            if ( cliptf.isDataFlavorSupported ( DataFlavor.stringFlavor ) ) {
                clipstr = (String) cliptf.getTransferData ( DataFlavor.stringFlavor );
            }
        }
        return clipstr;
    }
    public static void setClipboardText(String writeInstr) {
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable tText = new StringSelection (writeInstr);
        clip.setContents(tText, null);
    }

    public static void main(String [] args) {

    }

    @Override
    public void start (Stage stage) throws Exception {
        TextFlow TF= new TextFlow ( );

        Text text1 = new Text("Hello ");
        text1.setFill( javafx.scene.paint.Color.RED );

        Text text2 = new Text("Bold");
        text2.setFill( Color.BLUE );

        Text text3 = new Text(" World");
        text3.setFill( Color.ORANGE );
        TF.getChildren ().addAll ( text1,text2,text3 );

        Group group = new Group(TF);
        Scene scene =new Scene (group);
        stage.setScene ( scene );
        stage.show();
    }
}
