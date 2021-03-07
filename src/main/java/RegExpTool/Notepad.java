//This is a open source project form get-hub,author is six519.
//https://github.com/six519/Java-Notepad
//modify by sketchdee,used by regexp tool.
package RegExpTool;

import javafx.util.Pair;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.*;
import java.util.Stack;

public class Notepad extends JFrame implements ActionListener, WindowListener {

    private JTextArea jta = new JTextArea ();
    private File fnameContainer;
    private Container con;
    private JMenuBar jmb;
    private Stack<Pair<String, Integer>> history = new Stack<> ( );
    private Stack<Pair<String, Integer>> forward = new Stack<> ( );
    private boolean isChangedbyUndo=false;

    public Notepad () throws IOException {
        this(new File(".Untitled").exists ()?reCreatNewFile ( ".Untitled" ):creatNewFile(".Untitled"));
    }
    public static File creatNewFile(String path) throws IOException {new File(path).createNewFile ();return new File(path);}
    public static File reCreatNewFile(String path) throws IOException {new File(path).delete ();new File(path).createNewFile ();return new File(path);}

    public Notepad(File file) throws IOException {
        this.con = getContentPane ();

        //Menubar
        this.jmb = new JMenuBar ();
        JMenu jmfile = new JMenu ("File");
        JMenu jmedit = new JMenu ("Edit");
        JMenu jmhelp = new JMenu ("Help");
        createMenuItem (jmfile, "New");
        createMenuItem (jmfile, "Open");
        createMenuItem (jmfile, "Save");
        jmfile.addSeparator ();
        createMenuItem (jmfile, "Exit");
        createMenuItem (jmedit, "Cut");
        createMenuItem (jmedit, "Copy");
        createMenuItem (jmedit, "Paste");
        createMenuItem (jmhelp, "About Notepad");
        jmb.add (jmfile);
        jmb.add (jmedit);
        jmb.add (jmhelp);
        setJMenuBar (jmb);


        con.setLayout (new BorderLayout ());
        //add scrollbar
        JScrollPane sbrText = new JScrollPane (jta);
        sbrText.setVerticalScrollBarPolicy (JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        sbrText.setVisible (true);

        jta.setFont(new java.awt.Font("Arial Unicode MS", 0, 14));
        jta.setLineWrap (true);
        jta.setWrapStyleWord (true);
        jta.addMouseListener ( new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }
            @Override
            public void mousePressed(MouseEvent e) {
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isMetaDown()) {
                    showPopupMenu(e.getComponent(), e.getX(), e.getY());
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                // 鼠标进入组件区域
            }
            @Override
            public void mouseExited(MouseEvent e) {
                // 鼠标离开组件区域
            }
        });
        jta.getDocument ().addDocumentListener ( new DocumentListener ( ) {
            @Override
            public void insertUpdate (DocumentEvent e) {
                if(!isChangedbyUndo){
                    if(history.size ()==0||!history.lastElement ().equals ( jta.getText ()))
                        history.add(new Pair<> (jta.getText (),jta.getSelectionStart ()));
                    System.out.println ("insertupdate");
                }
            }

            @Override
            public void removeUpdate (DocumentEvent e) {
                if(!isChangedbyUndo){
                    if(history.size ()==0||!history.lastElement ().equals ( jta.getText ()))
                       history.add(new Pair<> (jta.getText (),jta.getSelectionStart ()));
                    System.out.println ("removeupdate");
                }
            }

            @Override
            public void changedUpdate (DocumentEvent e) {
                if(!isChangedbyUndo){
                    if(history.size ()==0||!history.lastElement ().equals ( jta.getText ()))
                        history.add(new Pair<> (jta.getText (),jta.getSelectionStart ()));
                    System.out.println ("changedupdate");
                }
            }
        } );
        jta.addKeyListener ( new KeyListener ( ) {
            @Override
            public void keyTyped (KeyEvent e) {

            }
            @Override
            public void keyPressed (KeyEvent e) {
                if (e.isControlDown ()&&e.getKeyCode ()==KeyEvent.VK_Z) {
                    if(history.size()>1)undo();
                }else if(e.isControlDown ()&&e.getKeyCode()==KeyEvent.VK_Y){
                    if(!forward.empty ()&&!forward.isEmpty ())redo();
                } else if(e.isControlDown ()&&e.getKeyCode()==KeyEvent.VK_S){
                    try {
                        SaveFile (fnameContainer.getAbsolutePath ());
                    } catch (IOException ioException) {
                        ioException.printStackTrace ();
                    }
                }
            }

            @Override
            public void keyReleased (KeyEvent e) {

            }
        } );

        con.add (sbrText);
        //setIconImage (Toolkit.getDefaultToolkit ().getImage ("notepad.gif"));
        addWindowListener (this);

        this.fnameContainer=file;
        setSize (500, 500);
        setTitle (file.getName ()+" - Notepad");
        OpenFile(file.getAbsolutePath ());

        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) screensize.getWidth() / 2 - getWidth() / 2;
        int y = (int) screensize.getHeight() / 2 - getHeight() / 2;
        setLocation(x, y);

        jta.setCaretPosition ( 0 );
        history.removeAllElements ();
        history.add(new Pair<>(jta.getText (),0));
        setVisible (true);
    }

    public void createMenuItem (JMenu jm, String txt) {
        JMenuItem jmi = new JMenuItem (txt);
        jmi.addActionListener (this);
        jm.add (jmi);
    }

    public void actionPerformed (ActionEvent e) {
        JFileChooser jfc = new JFileChooser ();

        if (e.getActionCommand ().equals ("New")) {
            //new
            this.setTitle ("Untitled.txt - Notepad");
            jta.setText ("");
            fnameContainer = null;
        } else if (e.getActionCommand ().equals ("Open")) {
            //open
            int ret = jfc.showDialog (null, "Open");

            if (ret == JFileChooser.APPROVE_OPTION) {
                try {
                    File fyl = jfc.getSelectedFile ();
                    OpenFile (fyl.getAbsolutePath ());
                    this.setTitle (fyl.getName () + " - Notepad");
                    fnameContainer = fyl;
                } catch (IOException ers) {
                    ers.printStackTrace ();
                }
            }

        } else if (e.getActionCommand ().equals ("Save")) {
            //save
            if (fnameContainer != null) {
                jfc.setCurrentDirectory (fnameContainer);
                jfc.setSelectedFile (fnameContainer);
            } else {
                //jfc.setCurrentDirectory(new File("."));
                jfc.setSelectedFile (new File ("Untitled.txt"));
            }

            int ret = jfc.showSaveDialog (null);

            if (ret == JFileChooser.APPROVE_OPTION) {
                try {

                    File fyl = jfc.getSelectedFile ();
                    SaveFile (fyl.getAbsolutePath ());
                    this.setTitle (fyl.getName () + " - Notepad");
                    fnameContainer = fyl;

                } catch (Exception ers2) {
                }
            }

        } else if (e.getActionCommand ().equals ("Exit")) {
            //exit
            Exiting ();
        } else if (e.getActionCommand ().equals ("Copy")) {
            //copy
            jta.copy ();
        } else if (e.getActionCommand ().equals ("Paste")) {
            //paste
            jta.paste ();
        } else if (e.getActionCommand ().equals ("About Notepad")) {
            //about
            JOptionPane.showMessageDialog (this, "          JAVA Notepad         \na cross-platform editing solution.", "Notepad", JOptionPane.INFORMATION_MESSAGE);
        } else if (e.getActionCommand ().equals ("Cut")) {
            jta.cut ();
        }
    }

    public void OpenFile (String fname) throws IOException {
        //open file code here
        BufferedReader d = new BufferedReader (new InputStreamReader (new FileInputStream (fname)));
        String l;
        //clear the textbox
        jta.setText ("");

        setCursor (new Cursor (Cursor.WAIT_CURSOR));
        setTitle (fname+" - Notepad");

        String content="";
        while ((l = d.readLine ()) != null) {
            if(l.trim ().length()!=0)
                content+=l+"\n";
        }
        jta.setText ( content );

        setCursor (new Cursor (Cursor.DEFAULT_CURSOR));
        d.close ();
    }

    public void SaveFile (String fname) throws IOException {
        setCursor (new Cursor (Cursor.WAIT_CURSOR));
        DataOutputStream o = new DataOutputStream (new FileOutputStream (fname));
        o.writeBytes (jta.getText ());
        o.close ();
        setCursor (new Cursor (Cursor.DEFAULT_CURSOR));
    }

    public void windowDeactivated (WindowEvent e) {
    }

    public void windowActivated (WindowEvent e) {
    }

    public void windowDeiconified (WindowEvent e) {
    }

    public void windowIconified (WindowEvent e) {
    }

    public void windowClosed (WindowEvent e) {
    }

    public void windowClosing (WindowEvent e) {
        Exiting ();
    }

    public void windowOpened (WindowEvent e) {
    }

    public void Exiting () {
        //Don't use System.exit(),it will destroy root windows,it's a JVM Operation.
        //"dispose()" only destroy current JFrame window.
        //"setVisible(false)" can hide the window without close it,cost a little more memory but fast.

        // setVisible(false);
        dispose();
    }

    private void redo(){
        if(!forward.isEmpty ()){
            Pair<String,Integer> content =forward.pop();
            this.isChangedbyUndo=true;
            this.jta.setText (content.getKey ());
            jta.setCaretPosition (content.getValue ());
            history.add(content);
            this.isChangedbyUndo=false;

        }
    }
    private void undo () {
        if(!history.isEmpty()){
            this.forward.add (history.pop());
            Pair<String,Integer> content =history.pop();
            if(content.getKey ().equals (jta.getText ()))
                return;
            this.isChangedbyUndo=true;
            this.jta.setText (content.getKey ());
            jta.setCaretPosition (content.getValue ());
            this.isChangedbyUndo=false;
            this.history.add(new Pair<>(jta.getText (),jta.getSelectionStart ()));
        }
    }
    private void cut (JTextArea jta) throws BadLocationException {
        if(jta.getSelectionEnd ()==jta.getSelectionStart ())return;
        setClipboardText (jta.getSelectedText ());
        delete (jta);
    }
    private void copy (JTextArea jta) {
        setClipboardText (jta.getSelectedText ());
    }
    private void paste (JTextArea jta) throws BadLocationException, IOException, UnsupportedFlavorException {
        /*
        * abcdefghij        content
        * 0123456789        index
        * abc|----|j        startp=3 endp=9
        * (1)startp=endp
        * abc+clipstr+j
        * .getText(0,3)+clipstr+.getText(endp,.getText.length() - endp)
        * (2)startp!=endp
        * ...
        * */
        String clipstr=getClipText ();
        int startp=jta.getSelectionStart ();
        int endp=jta.getSelectionEnd ();

        if(jta.getSelectionStart ()!=jta.getSelectionEnd ()) {
                jta.setText ( jta.getText(0,startp)+
                        clipstr+
                        jta.getText (endp,jta.getText ().length ()-endp));
                jta.setCaretPosition (startp+clipstr.length ());
        }
        if(startp==endp){
            jta.setText(jta.getText ( 0,startp )+
                    clipstr+
                    jta.getText ( endp,jta.getText ().length ()-endp));
            jta.setCaretPosition (startp+clipstr.length ());
        }
    }
    private void delete (JTextArea jta) throws BadLocationException {
        int startp=jta.getSelectionStart ();//when use setText it will changed automatic.
        jta.setText ( jta.getText ().replace ( jta.getSelectedText (),"" ) );
        jta.setCaretPosition (startp);
    }
    private void selectAll (JTextArea jta) {
        jta.select ( 0,jta.getText ().length () );
    }

    public void showPopupMenu(Component invoker, int x, int y) {
        // 创建 弹出菜单 对象
        JPopupMenu popupMenu = new JPopupMenu();

        // 创建 一级菜单
        JMenuItem redoMenuItem      = new JMenuItem("Redo            Ctrl+Y");
        JMenuItem undoMenuItem      = new JMenuItem("Undo            Ctrl+Z");
        JMenuItem cutMenuItem       = new JMenuItem("Cut               Ctrl+X");
        JMenuItem copyMenuItem      = new JMenuItem("Copy            Ctrl+C");
        JMenuItem pasteMenuItem     = new JMenuItem("Paste           Ctrl+V");
        JMenuItem deleteMenuItem    = new JMenuItem("Delete");
        JMenuItem selectAllMenuItem = new JMenuItem("Select All     Ctrl+A");
        JMenuItem openInMenuItem = new JMenu("Open In");

        //二级菜单
        JMenuItem explorerMenuItem = new JMenuItem("Show in Explorer");
        JMenuItem systemEditorMenuItem = new JMenuItem("System Editor");
        JMenuItem terminalhMenuItem = new JMenuItem("Terminal");
        openInMenuItem.add(explorerMenuItem);
        openInMenuItem.add(systemEditorMenuItem);
        openInMenuItem.add(terminalhMenuItem);

        //不符合要求就不可用
        if(this.forward.isEmpty ())redoMenuItem.setEnabled ( false );
        if(this.history.size()<2)undoMenuItem.setEnabled ( false );
        if(this.jta.getSelectionStart ()==this.jta.getSelectionEnd ())deleteMenuItem.setEnabled ( false );
        // 添加 一级菜单 到 弹出菜单
        popupMenu.add(redoMenuItem);
        popupMenu.add(undoMenuItem);
        popupMenu.addSeparator();
        popupMenu.add(cutMenuItem);
        popupMenu.add(copyMenuItem);
        popupMenu.add(pasteMenuItem);
        popupMenu.add(deleteMenuItem);
        popupMenu.addSeparator();
        popupMenu.add(selectAllMenuItem);
        popupMenu.addSeparator();
        popupMenu.add(openInMenuItem);

        // 添加菜单项的点击监听器
        redoMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                redo();
            }
        });

        undoMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                undo();
            }
        });
        cutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    cut(jta);
                } catch (BadLocationException badLocationException) {
                    badLocationException.printStackTrace ( );
                }
            }
        });
        copyMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                copy(jta);
            }
        });
        pasteMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    paste(jta);
                } catch (BadLocationException | IOException | UnsupportedFlavorException badLocationException) {
                    badLocationException.printStackTrace ( );
                }
            }
        });
        deleteMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    delete ( jta );
                } catch (BadLocationException badLocationException) {
                    badLocationException.printStackTrace ( );
                }
            }
        });
        selectAllMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectAll(jta);
            }
        });

        explorerMenuItem.addActionListener ( new ActionListener ( ) {
            @Override
            public void actionPerformed (ActionEvent e) {
                exeCmd ("cmd /c start explorer "+fnameContainer.getParentFile ());
            }
        } );
        systemEditorMenuItem.addActionListener ( new ActionListener ( ) {
            @Override
            public void actionPerformed (ActionEvent e) {
                exeCmd("cmd /c start explorer "+fnameContainer.getAbsolutePath ());
            }
        } );
        terminalhMenuItem.addActionListener ( new ActionListener ( ) {
            @Override
            public void actionPerformed (ActionEvent e) {
                exeCmd("cmd /c "+fnameContainer.getAbsolutePath ());
            }
        } );

        // 在指定位置显示弹出菜单
        popupMenu.show(invoker, x, y);
    }
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

    public static void exeCmd (String cmd) {
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec(cmd);
        } catch (IOException ioException) {
            ioException.printStackTrace ( );
        }
    }
    public static void main (String[] args) {
        try{
            new Notepad ();
        }catch(Exception ex){System.out.print(ex.getMessage ());}
    }
}

