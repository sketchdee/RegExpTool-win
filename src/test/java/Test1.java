import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;

public class Test1 {
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
        setClipboardText ( "这是java程序设置的系统剪切板数据。" );
    }

}
