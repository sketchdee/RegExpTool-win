package RegExpTool;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.lang.System;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;


public class Controller {

    @FXML
    private BorderPane RootPane;

    /*
    * Menu items
    * */
    @FXML
    private MenuItem MenuFileClose;

    @FXML
    private MenuItem MenuEditRegexpHistory;
    @FXML
    private MenuItem MenuEditMypattens;
    @FXML
    private MenuItem MenuEditSettings;

    @FXML
    private MenuItem MenuHelpHandbook;
    @FXML
    private MenuItem MenuHelpAbout;



/**
 * left sid TabPane
 * */
    @FXML
    private VBox sidVBox;
    @FXML
    private TabPane sidTabPane;
    @FXML
    private Tab SettingTab;
    @FXML
    private Tab MyPatternsTab;
    @FXML
    private Tab CheatSheetTab;
    @FXML
    private Tab RegExpRefTab;
    //assist double click stow away side pane
    private int count=0;
    private Label settinglabel;
    private Label mypatternslabel;
    private Label cheatsheetlabel;
    private Label regexreflabel;


    /**
     * settings Tab Item.
     * */
    @FXML
    private CheckBox setAutocheck;
    @FXML
    private CheckBox setTextfromTabFirst;
    @FXML
    private CheckBox setGetTextFromLRun;
    @FXML
    private CheckBox setShowMutipleResult;
    @FXML
    private CheckBox ShowConsoleInfo;
    @FXML
    private CheckBox SaveHistory;
    @FXML
    private CheckBox AutoSaveHis2MyPatterns;
    @FXML
    private ChoiceBox<String> StartSidetabChoiceBox;
    @FXML
    private ChoiceBox<String> SingleResultChoiceBox;
    @FXML
    private Button SaveSettingButton;

    /**
     * My Patterns Tab
     * */
    @FXML
    private TableView<mpLine> MyPatternsTableView;

    /**
     * Cheat Sheet Tab
     * */
    @FXML
    private TableView<mpLine> CheatSheetTableView;


    /**
     * Tab RegExRefTab;
     * */
    @FXML
    private TableView<mpLine> RegExpRefTableView;

/** end left sid pane **/


/**
 * Regex pane
 * */

    /**Regexp TextField*/
    @FXML
    private TextField RegexpTextField;
    /**Check Button*/
    @FXML
    private Button CheckButton;
    /**auto check box*/
    @FXML
    private CheckBox isautocheck;


    /**Text TabPane*/
    @FXML
    private TabPane TextTabPane;
    /**first Tab*/
    @FXML
    private TextArea MatchTextArea;
    /**Second Tab*/
    @FXML
    private TextArea MatchTextArea2;
    @FXML
    private Button FromfileButton;
    @FXML
    private Button FromurlButton;

    /**Result TabPane*/
    @FXML
    private TabPane ResultTabPane;
    @FXML
    private TableView MutipleResultTableView;
    @FXML
    private TableView SingleResultTableView;

/**end Regex pane*/
/**windows item define end */

/**
* tool class and tool functions.
* */
    /**tool class*/
    public static class mpLine {
        private final SimpleStringProperty character;
        private final SimpleStringProperty description;

        private mpLine( String character, String description) {
            this.character = new SimpleStringProperty(character);
            this.description = new SimpleStringProperty(description);
        }

        public String getCharacter() {
            return this.character.get();
        }

        public void setCharacter(String character) {
            this.character.set(character);
        }

        public String getDescription() {
            return description.get();
        }

        public void setDescription(String fName) {
            this.description.set(fName);
        }
    }
    public static class singleLine {
        private final SimpleStringProperty character;
        private singleLine( String character) {
            this.character = new SimpleStringProperty(character);
        }
        public String getCharacter() {
            return this.character.get();
        }
        public void setCharacter(String character) {
            this.character.set(character);
        }
    }
    public static class multipleLine{
        private final SimpleStringProperty javacell;
        private final SimpleStringProperty pythoncell;
        private final SimpleStringProperty cscell;

        private multipleLine( String javacell, String pythoncell,String cscell) {
            this.javacell = new SimpleStringProperty(javacell);
            this.pythoncell = new SimpleStringProperty(pythoncell);
            this.cscell = new SimpleStringProperty(cscell);
        }
        public String getJavacell() {
            return this.javacell.get();
        }
        public void setJavacell(String javacell) {
            this.javacell.set(javacell);
        }

        public String getPythoncell() {
            return pythoncell.get();
        }
        public void setPythoncell(String pythoncell) {
            this.pythoncell.set(pythoncell);
        }

        public String getCscell() {
            return this.cscell.get();
        }
        public void setCscell(String cscell) {
            this.cscell.set(cscell);
        }
    }

    /**tool function*/
    /**
     * this function is used to print output.java to console or file,and will not add next-line in end.
     * if "s.showconsoleinfo=false" all the infomation will be writed to log file.
     * use "System.out.print()" to print to console,and use "Tools.Writeinfile()" to write into file.
     *
     * @param  out
     * @return null
     *
     * */
    protected void print(Object out){
        if(this.settings.showconsoleinfo){
            java.lang.System.out.print(out);
        }else{
            log(out.toString(),"Error");
        }
    }
    /**
     * this function is used to print output.java to console or file,and will not add next-line in end.
     * if "s.showconsoleinfo=false" all the infomation will be writed to log file.
     * use "System.out.print()" to print to console,and use "Tools.Writeinfile()" to write into file.
     *
     * @param out
     * @return null
     *
     * */
    protected void println(Object out) {
        if(this.settings.showconsoleinfo){
            java.lang.System.out.println(out);
        }else{
            log(out.toString(),"Error");
        }
    }
    public static void log(String logstr,String logtype){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy");
        String logpath=Controller.resdir.get("log")+formatter.format(date)+".log";

        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String [] LogType ={"Info","Warning","Error"};
        String logline=formatter2.format(date)+", "+logtype+"               "+"RegExpTool   "+logstr+"\n";


        //Tools.writeintail() is a tool can use in more situations.
        FileOutputStream fos = null;
        OutputStreamWriter osw=null;
        try{
            File file = new File(logpath);
            if(!file.exists()){
                file.createNewFile();
                fos = new FileOutputStream(file,true);
                osw = new OutputStreamWriter(fos, "UTF-8");
                Map<String, String> env = System.getenv();
                String loghead="";
                loghead+=("******************************System Enviroment******************************"+"\n");
                for (Map.Entry<String,String> entry : env.entrySet()) {
                    loghead+=("* "+entry.getKey() + ": " + entry.getValue()+"\n");
                }
                loghead+=("*****************************************************************************"+"\n");
                osw.write(loghead);
            }else{
                fos = new FileOutputStream(file,true);
                osw = new OutputStreamWriter(fos, "UTF-8");
            }
            osw.write(logline);
            osw.close();
            fos.close();
        }catch(Exception e){
            log(Thread.currentThread().getStackTrace()[1].getMethodName()+": " + e.getMessage(),"Error");
            e.printStackTrace();
        }

    }
    private void ApplySetting(){
        if(!this.settings.isRightful()){
            this.settings.fixMiss();
        }
        if(this.settings.defaultautocheck==true){
            this.isautocheck.setSelected(true);
            this.setAutocheck.setSelected(true);
        }
        if(this.settings.defaultshow2==true){
            this.TextTabPane.getSelectionModel().select(1);
            this.setTextfromTabFirst.setSelected(true);
        }
        if(this.settings.defultgettextfromlr){
            this.setGetTextFromLRun.setSelected(true);
        }
        if(this.settings.defaultshowmutipleresult==false){
            this.ResultTabPane.getSelectionModel().select(1);
            this.setShowMutipleResult.setSelected(false);
        }
        if(this.settings.showconsoleinfo==true) {
            this.ShowConsoleInfo.setSelected(true);
            }
        if(this.settings.savehistory==true){
            this.SaveHistory.setSelected(true);
        }
        if(this.settings.autosavehis2mypatterns==true){
            this.AutoSaveHis2MyPatterns.setSelected(true);
        }
        if(this.settings.defaultsidetab==4)
            this.sidTabPane.setPrefWidth(30);
        else{
            this.sidTabPane.getSelectionModel().select(this.settings.defaultsidetab);
            this.StartSidetabChoiceBox.getSelectionModel().select(this.settings.defaultsidetab);
        }
        this.SingleResultChoiceBox.getSelectionModel().select(this.settings.defaultsingleresult);
        this.SingleResultChoiceBox.getSelectionModel().select(this.settings.defaultsingleresult);
        log(Thread.currentThread().getStackTrace()[1].getMethodName()+": " + "ApplySetting","Info");
    }

    /*//添加静态的成员用来存储需要用到的资源路径。
    public static String WorkDir=System.getProperty("user.dir");
    public static String WorkDir="";
    public static HashMap<String,String> resdir = new HashMap<String,String>(){
        {
            put("handbook",WorkDir+ File.separator+"exresource"+File.separator+"helpdocument"+File.separator+"Regexp-handbook.html");
            put("RegTestapp",WorkDir+File.separator+"exresource"+File.separator+"netcoreapp3.1"+File.separator+"RegTestapp.exe");
            put("PyRegexp",WorkDir+File.separator+"exresource"+File.separator+"regpython"+File.separator+"PyRegexp.py");
            put(".sourcetmp",WorkDir+File.separator+"exresource"+File.separator+".sourcetmp");
            put("History",WorkDir+File.separator+"exresource"+File.separator+"History.txt");
            put("sampletext",WorkDir+File.separator+"exresource"+File.separator+"sampletext");
            put("settings",WorkDir+File.separator+"exresource"+File.separator+"settings");
            put("mypatterns",WorkDir+File.separator+"exresource"+File.separator+"mypatterns");
            put("log",WorkDir+File.separator+"log"+File.separator);
        }
    };*/
    public static HashMap<String,String> resdir = new HashMap<String,String>(){
        {
            put("handbook","exresource"+File.separator+"helpdocument"+File.separator+"Regexp-handbook.html");
            put("RegTestapp","exresource"+File.separator+"netcoreapp3.1"+File.separator+"RegTestapp.exe");
            put("PyRegexp","exresource"+File.separator+"regpython"+File.separator+"PyRegexp.py");
            put(".sourcetmp","exresource"+File.separator+".sourcetmp");
            put("History","exresource"+File.separator+"History.txt");
            put("sampletext","exresource"+File.separator+"sampletext");
            put("settings","exresource"+File.separator+"settings");
            put("mypatterns","exresource"+File.separator+"mypatterns");
            put("log","log"+File.separator);
        }
    };

    /** value */
    private Setting settings;
    private ObservableList<singleLine> singleResult=FXCollections.observableArrayList();
    private ObservableList<multipleLine> multipleResult = FXCollections.observableArrayList();
    public LinkedList<String> history=new LinkedList<String>();
    private double sidepanewith=400;


    /**controller action code*/
    @FXML
    public void initialize(){
        try{
            log(Thread.currentThread().getStackTrace()[1].getMethodName() + ": " + "Start RegExp Tool.", "Info");
            //sideTabPane With
            sidTabPane.setPrefWidth(this.sidepanewith);

            //read settings
            if(Setting.CheckSettingFile(Controller.resdir.get("settings")))
                Tools.writeinfile(Controller.resdir.get("settings"),Setting.getDefaultSetting());
            this.settings = Setting.ReadSetting(Controller.resdir.get("settings"));
            if(settings==null){
                this.settings = new Setting();
                System.out.println("Setting is null and restore to defaultdefault.");
                }

            //regexp TextField listener,auto check is auto press
            this.RegexpTextField.setOnKeyTyped((KeyEvent e) -> {
                if(e.getCharacter()!=null&&this.isautocheck.isSelected()){
                    CheckButtonOnAction(new ActionEvent());
                }
            });
            this.RegexpTextField.setOnKeyReleased((KeyEvent e) -> {
                if (e.getCode()== KeyCode.ENTER) {
                    CheckButtonOnAction(new ActionEvent());         //Enter pressed event
                }
            });
            //get text from last run.
            if(this.settings.defultgettextfromlr==true){
                this.MatchTextArea.setText(Tools.fetchlocaldata(new File(Controller.resdir.get(".sourcetmp"))));
                this.MatchTextArea2.setText(Tools.fetchlocaldata(new File(Controller.resdir.get(".sourcetmp"))));
            }

            //Match TextArea auto next line(WrapText)
            this.MatchTextArea.setWrapText(true);
            this.MatchTextArea2.setWrapText(true);

            //tabpane pane size
            this.MyPatternsTableView.setPrefSize(this.sidVBox.getPrefHeight(),this.sidepanewith);
            this.CheatSheetTableView.setPrefSize(this.sidVBox.getPrefHeight(),this.sidepanewith);
            this.RegExpRefTableView.setPrefSize(this.sidVBox.getPrefHeight(),this.sidepanewith);

            //if value not in .fxml ,it will not be initialize automatic.This table is used to add to sidetab.
            settinglabel = new Label("settings");
            settinglabel.setPrefHeight(30);
            //settinglabel.setPrefWidth(100);
            mypatternslabel = new Label("My Patterns");
            mypatternslabel.setPrefHeight(30);
            cheatsheetlabel = new Label("Cheat Sheet");
            cheatsheetlabel.setPrefHeight(30);
            regexreflabel = new Label("RegExp Reference");
            regexreflabel.setPrefHeight(30);

            /*
            Make the bound label display vertically,label.setRotate() can't  implement it,because  if the root which we add controller in it
            is not vertical, even the control changed to vertical will be displayed as a horizontal control.
            */
            sidTabPane.setRotateGraphic(true);

            //the four TabPane have same code,use .setGraphic bound label.
            SettingTab.setGraphic(settinglabel);
            settinglabel.setOnMouseClicked(e -> {
                if (count == 0) {
                    sidTabPane.setPrefWidth(30);
                    count += 1;
                } else if (count == 1) {
                    sidTabPane.setPrefWidth(this.sidepanewith);
                    count = 0;
                }
            });

            MyPatternsTab.setGraphic(mypatternslabel);
            mypatternslabel.setOnMouseClicked(e -> {
                if (count == 0) {
                    sidTabPane.setPrefWidth(30);
                    count += 1;
                } else if (count == 1) {
                    sidTabPane.setPrefWidth(this.sidepanewith);
                    count = 0;
                }
            });

            CheatSheetTab.setGraphic(cheatsheetlabel);
            cheatsheetlabel.setOnMouseClicked(e -> {
                if (count == 0) {
                    sidTabPane.setPrefWidth(30);
                    count += 1;
                } else if (count == 1) {
                    sidTabPane.setPrefWidth(this.sidepanewith);
                    count = 0;
                }
            });

            RegExpRefTab.setGraphic(regexreflabel);
            regexreflabel.setOnMouseClicked(e -> {
                if (count == 0) {
                    sidTabPane.setPrefWidth(30);
                    count += 1;
                } else if (count == 1) {
                    sidTabPane.setPrefWidth(this.sidepanewith);
                    count = 0;
                }
            });

            //Capture mouse-click TAB events,.addListener will no change the Original action.
            sidTabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    //sidTabPane.setPrefWidth(this.sidepanewith);
                    count = 1;
                }
            });

            //MyPatternsTabView,add data to it ,and let it editable.
            //Maintenance a ObservableList,mpLine is the Line class defined in Controller scope.
            TableColumn MyPatternsTablecol1 = new TableColumn("character");     //add sheet head.
            TableColumn MyPatternsTablecol2 = new TableColumn("description");
            ObservableList<mpLine> mypatterns = FXCollections.observableArrayList();
            //read mypatterns data
            String [] mypatternsarry=Tools.fetchlocaldata(new File(Controller.resdir.get("mypatterns"))).split("\n");
            for(int i=0;i<mypatternsarry.length-1;i++){
                if(i%2==0)
                    mypatterns.add(new mpLine(mypatternsarry[i],mypatternsarry[i+1]));
            }
            //A row is added if the trailing row is not empty, which is used to automatically add row.
            if (mypatterns.get(mypatterns.size() - 1).getCharacter() != "" || mypatterns.get(mypatterns.size() - 1).getDescription() != "")
                mypatterns.add(new mpLine("", ""));
            MyPatternsTablecol1.setCellValueFactory(
                    new PropertyValueFactory<>("character")
            );
            MyPatternsTablecol2.setCellValueFactory(
                    new PropertyValueFactory<>("description")
            );
            this.MyPatternsTableView.setEditable(true);
            MyPatternsTablecol1.setCellFactory(TextFieldTableCell.<mpLine>forTableColumn());
            MyPatternsTablecol2.setCellFactory(TextFieldTableCell.<mpLine>forTableColumn());

            MyPatternsTablecol1.prefWidthProperty().bind(MyPatternsTableView.widthProperty().multiply(0.5));
            MyPatternsTablecol2.prefWidthProperty().bind(MyPatternsTableView.widthProperty().multiply(0.5));
            MyPatternsTablecol1.setResizable(false);
            MyPatternsTablecol2.setResizable(false);
            MyPatternsTableView.getColumns().addAll(MyPatternsTablecol1, MyPatternsTablecol2);
            MyPatternsTablecol1.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<mpLine, String>>() {
                //set on change commit,taype and ENTER pressed.
                @Override
                public void handle(TableColumn.CellEditEvent<mpLine, String> t) {
                    mpLine p = t.getRowValue();
                    p.setCharacter(t.getNewValue());
                    if (mypatterns.get(mypatterns.size() - 1).getCharacter() != "" ||
                            mypatterns.get(mypatterns.size() - 1).getDescription() != "") {
                        mypatterns.add(new mpLine("", ""));
                    }
                    String content = "";
                    for (mpLine mpl : mypatterns) content += (mpl.getCharacter() + "\n" + mpl.getDescription() + "\n");
                    Tools.writeinfile(Controller.resdir.get("mypatterns"), content);
                }
            });
            MyPatternsTablecol2.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<mpLine, String>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<mpLine, String> t) {
                    mpLine p = t.getRowValue();
                    p.setDescription(t.getNewValue());
                    if (mypatterns.get(mypatterns.size() - 1).getCharacter() != "" || mypatterns.get(mypatterns.size() - 1).getDescription() != "") {
                        mypatterns.add(new mpLine("", ""));
                    }
                }
            });
            MyPatternsTableView.setItems(mypatterns);

            //bound Cheat Sheet data.
            String[] cheatsheetstr = {
                    "User Name", "/^[a-z0-9_-]{3,16}$/",
                    "Password", "/^[a-z0-9_-]{6,18}$/",
                    "Hexadecimal", "/^#?([a-f0-9]{6}|[a-f0-9]{3})$/",
                    "Email", "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*",
                    "URL", "/^(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?$/",
                    "IP Address", "\\d+\\.\\d+\\.\\d+\\.\\d+",
                    "HTML Label", "/^<([a-z]+)([^<]+)*(?:>(.*)<\\/\\1>|\\s+\\/>)$/",
                    "Code Comments", "(?<!http:|\\S)//.*$",
                    "Unicode汉字范围", "/^[\\u2E80-\\u9FFF]+$/"};
            TableColumn cheatsheetTablecol1 = new TableColumn("character");
            TableColumn cheatsheetTablecol2 = new TableColumn("description");
            CheatSheetTableView.getColumns().addAll(cheatsheetTablecol1, cheatsheetTablecol2);
            ObservableList<mpLine> cheatsheet = FXCollections.observableArrayList();
            for (int i = 0; i < 9; i++) {
                cheatsheet.add(new mpLine(cheatsheetstr[2 * i], cheatsheetstr[2 * i + 1]));
            }
            cheatsheetTablecol1.setCellValueFactory(
                    new PropertyValueFactory<>("character")
            );
            cheatsheetTablecol2.setCellValueFactory(
                    new PropertyValueFactory<>("description")
            );
            cheatsheetTablecol1.prefWidthProperty().bind(CheatSheetTableView.widthProperty().multiply(0.3));
            cheatsheetTablecol2.prefWidthProperty().bind(CheatSheetTableView.widthProperty().multiply(0.7));
            cheatsheetTablecol1.setResizable(false);
            cheatsheetTablecol2.setResizable(false);
            CheatSheetTableView.setItems(cheatsheet);

            //bound RegexpRef TableView data
            String[] refs = {
                    "\\", "标记一个特殊字符、原义字符、向后引用、八进制转义符。",
                    "^", "匹配输入字符串的开始位置。",
                    "$", "匹配输入字符串的结束位置。",
                    "*", "匹配前面的子表达式零次或多次。",
                    "+", "匹配前面的子表达式一次或多次。",
                    "?", "匹配前面的子表达式零次或一次。",
                    "{n}", "n是一个非负整数。匹配确定的n次。",
                    "{n,}", "n是一个非负整数。至少匹配n次。",
                    "{n,m}", "m和n均为非负整数，其中n<=m。最少匹配n次且最多匹配m次。",
                    "?", "(*,+,?，{n}，{n,}，{n,m})匹配模式是非贪婪的。",
                    ".", "匹配除“\\n”之外的任何单个字符。“(.|\\n)匹配包括“\\n””。",
                    "x|y", "匹配x或y。“(z|f)ood”则匹配“zood”或“food”。",
                    "[xyz]", "字符集合。匹配所包含的任意一个字符。",
                    "[^xyz]", "负值字符集合。匹配未包含的任意字符。",
                    "[a-z]", "字符范围。匹配指定范围内的任意字符。",
                    "[^a-z]", "负值字符范围。匹配任何不在指定范围内的任意字符。",
                    "\\b", "匹配一个单词边界，也就是指单词和空格间的位置。",
                    "\\B", "匹配非单词边界。",
                    "\\cx", "匹配由x指明的控制字符。例如，\\cM匹配一个Control-M或回车符。",
                    "\\d", "匹配一个数字字符。等价于[0-9]。",
                    "\\D", "匹配一个非数字字符。等价于[^0-9]。",
                    "\\f", "匹配一个换页符。等价于\\x0c和\\cL。",
                    "\\n", "匹配一个换行符。等价于\\x0a和\\cJ。",
                    "\\r", "匹配一个回车符。等价于\\x0d和\\cM。",
                    "\\s", "匹配任何空白字符，包括空格、制表符、换页符等。等价于[\\f\\n\\r\\t\\v]。",
                    "\\S", "匹配任何非空白字符。等价于[^\\f\\n\\r\\t\\v]。",
                    "\\t", "匹配一个制表符。等价于\\x09和\\cI。",
                    "\\v", "匹配一个垂直制表符。等价于\\x0b和\\cK。",
                    "\\w", "匹配包括下划线的任何单词字符。等价于“[A-Za-z0-9_]”。",
                    "\\W", "匹配任何非单词字符。等价于“[^A-Za-z0-9_]”。",
                    "\\xn", "匹配n，其中n为十六进制转义值。.",
                    "\\num", "匹配num，其中num是一个正整数。对所获取的匹配的引用。",
                    "\\n", "标识一个八进制转义值或一个向后引用。",
                    "\\nm", "标识一个八进制转义值或一个向后引用。",
                    "\\nml", "匹配一个八进制转义值nml。n（0-3）,m和l（0-7）。",
                    "\\un", "匹配一个Unicode字符。例如，\\u00A9匹配版权符号（©）",
            };
            TableColumn RegexpRefTablecol1 = new TableColumn("character");
            TableColumn RegexpRefTablecol2 = new TableColumn("description");
            RegExpRefTableView.getColumns().addAll(RegexpRefTablecol1, RegexpRefTablecol2);
            ObservableList<mpLine> regexpreftable = FXCollections.observableArrayList();
            for (int i = 0; i < refs.length / 2; i++) {
                regexpreftable.add(new mpLine(refs[2 * i], refs[2 * i + 1]));
            }
            RegexpRefTablecol1.setCellValueFactory(
                    new PropertyValueFactory<>("character")
            );
            RegexpRefTablecol2.setCellValueFactory(
                    new PropertyValueFactory<>("description")
            );
            RegexpRefTablecol1.prefWidthProperty().bind(RegExpRefTableView.widthProperty().multiply(0.15));
            RegexpRefTablecol2.prefWidthProperty().bind(RegExpRefTableView.widthProperty().multiply(0.85));
            RegexpRefTablecol1.setResizable(false);
            RegexpRefTablecol2.setResizable(false);
            RegExpRefTableView.setItems(regexpreftable);

            //Match Result:
            //Match multiple result binding,JAVA Python C#
            TableColumn resTablecol1 = new TableColumn("JAVA matches");
            TableColumn resTablecol2 = new TableColumn("Python matches");
            TableColumn resTablecol3 = new TableColumn("C# matches");
            resTablecol1.setCellValueFactory(
                    new PropertyValueFactory<>("javacell")
            );
            resTablecol2.setCellValueFactory(
                    new PropertyValueFactory<>("pythoncell")
            );
            resTablecol3.setCellValueFactory(
                    new PropertyValueFactory<>("cscell")
            );
            resTablecol1.prefWidthProperty().bind(MutipleResultTableView.widthProperty().multiply(0.33));
            resTablecol2.prefWidthProperty().bind(MutipleResultTableView.widthProperty().multiply(0.33));
            resTablecol3.prefWidthProperty().bind(MutipleResultTableView.widthProperty().multiply(0.33));
            resTablecol1.setResizable(false);
            resTablecol2.setResizable(false);
            resTablecol3.setResizable(false);
            MutipleResultTableView.getColumns().addAll(resTablecol1, resTablecol2, resTablecol3);
            MutipleResultTableView.setItems(this.multipleResult);           //use a global this.multipleResult to make data change automaticly.

            //single result
            TableColumn singleTablecol1 = new TableColumn((new String[]{"JAVA Matches", "Python Matches", "C# Matches"})[this.settings.defaultsingleresult]);
            singleTablecol1.prefWidthProperty().bind(SingleResultTableView.widthProperty().multiply(1));
            singleTablecol1.setResizable(false);
            this.SingleResultTableView.getColumns().addAll(singleTablecol1);
            singleTablecol1.setCellValueFactory(
                    new PropertyValueFactory<>("character")
            );
            this.SingleResultTableView.setItems(this.singleResult);

            //Setting TabPane,add listener to make program know the change and dynamic change the value of this.settings .
            setAutocheck.selectedProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    if (setAutocheck.isSelected() == true) {
                        settings.defaultautocheck = true;
                    } else {
                        settings.defaultautocheck = false;
                    }
                }
            });
            setTextfromTabFirst.selectedProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    if (setTextfromTabFirst.isSelected() == true) {
                        settings.defaultshow2 = true;
                    } else {
                        settings.defaultshow2 = false;
                    }
                }
            });
            setGetTextFromLRun.selectedProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    if (setGetTextFromLRun.isSelected() == true) {
                        settings.defultgettextfromlr = true;
                    } else {
                        settings.defultgettextfromlr = false;
                    }
                }
            });
            setShowMutipleResult.selectedProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    if (setShowMutipleResult.isSelected() == true) {
                        settings.defaultshowmutipleresult = true;
                    } else {
                        settings.defaultshowmutipleresult = false;
                    }
                }
            });
            ShowConsoleInfo.selectedProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    if (ShowConsoleInfo.isSelected() == true) {
                        settings.showconsoleinfo = true;
                    } else {
                        settings.showconsoleinfo = false;
                    }
                }
            });
            SaveHistory.selectedProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    if (SaveHistory.isSelected() == true) {
                        settings.savehistory = true;
                    } else {
                        settings.savehistory = false;
                    }
                }
            });
            AutoSaveHis2MyPatterns.selectedProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    if (AutoSaveHis2MyPatterns.isSelected() == true) {
                        settings.autosavehis2mypatterns = true;
                    } else {
                        settings.autosavehis2mypatterns = false;
                    }
                }
            });
            //default startup sideTab setting code
            ObservableList<String> sidetab = FXCollections.observableArrayList("settings", "My Patterns", "Cheat Sheet", "RegExp Referebce");
            this.StartSidetabChoiceBox.setItems(FXCollections.observableArrayList(sidetab));
            this.StartSidetabChoiceBox.setTooltip(new Tooltip("startup side Tab"));
            this.StartSidetabChoiceBox.getSelectionModel().select(0);
            this.StartSidetabChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observableValue, Object o, Object t1) {
                    settings.defaultsidetab = StartSidetabChoiceBox.getSelectionModel().getSelectedIndex();
                }
            });
            //default startup single result
            ObservableList<String> options = FXCollections.observableArrayList("JAVA", "Python", "C#");
            this.SingleResultChoiceBox.setItems(FXCollections.observableArrayList(options));
            this.SingleResultChoiceBox.setTooltip(new Tooltip("Single reslut"));
            this.SingleResultChoiceBox.getSelectionModel().select(0);
            this.SingleResultChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observableValue, Object o, Object t1) {
                    settings.defaultsingleresult = SingleResultChoiceBox.getSelectionModel().getSelectedIndex();
                    singleTablecol1.setText((new String[]{"JAVA Matches", "Python Matches", "C# Matches"})[settings.defaultsingleresult]);
                    CheckButtonOnAction(new ActionEvent());
                }
            });

            //Prevent console writing
            if(this.settings.showconsoleinfo!=true){
                System.setOut(new PrintStream(new OutputStream() {@Override public void write(int b) throws IOException {}}));
            }

            //In the end of initialize,we aplly setting and write log.
            ApplySetting();
            log(Thread.currentThread().getStackTrace()[1].getMethodName() + ": " + "initialize program end.", "Info");

        }catch(Exception e){
            e.printStackTrace();
            log(Thread.currentThread().getStackTrace()[1].getMethodName()+": " + e.getMessage(),"Error");

        }
    }

    @FXML
    void CheckButtonOnAction(ActionEvent event) {
        String MatchText="";
        if(this.TextTabPane.getSelectionModel().getSelectedIndex()==0)
            MatchText=this.MatchTextArea.getText();
        else if(this.TextTabPane.getSelectionModel().getSelectedIndex()==1)
            MatchText=this.MatchTextArea2.getText();
        try {
            //java matchs
            LinkedList<String> javares = Tools.match(this.RegexpTextField.getText(), MatchText);

            //java matchs
            Tools.WritePy(this.RegexpTextField.getText());
            Tools.writeinfile(Controller.resdir.get(".sourcetmp"), MatchText);
            LinkedList<String> pyres = Tools.ConsoleExec();

            //C# matchs
            Tools.writeinfile(Controller.resdir.get(".sourcetmp"), MatchText);
            LinkedList<String> csres;
            if (this.RegexpTextField.getText() == null)
                csres = Tools.CsharpReg( "\\\\g");
            else
                csres = Tools.CsharpReg(this.RegexpTextField.getText());

            int linenum = 0;
            if (javares.size() >= pyres.size() && javares.size() >= csres.size())
                linenum = javares.size();
            else if (pyres.size() >= javares.size() && pyres.size() >= csres.size())
                linenum = pyres.size();
            else if (csres.size() >= javares.size() && csres.size() >= pyres.size())
                linenum = pyres.size();

            this.multipleResult.remove(0,multipleResult.size());
            for (int i = 0; i < linenum; i++) {
                String ljavacell = "";
                String lpycell = "";
                String lcscell = "";
                if (i < javares.size())
                    ljavacell = javares.get(i);
                if (i < pyres.size())
                    lpycell = pyres.get(i);
                if (i < csres.size())
                    lcscell = csres.get(i);
                this.multipleResult.add(new multipleLine(ljavacell, lpycell, lcscell));
            }

            this.singleResult.remove(0,singleResult.size());
            LinkedList<String> sglres;
            switch(this.settings.defaultsingleresult){
                case 0:
                    sglres=javares;
                    break;
                case 1:
                    sglres=pyres;
                    break;
                case 2:
                    sglres=csres;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + this.settings.defaultsingleresult);
            }
            for(int i=0;i<sglres.size();i++)
            {
                this.singleResult.add(new singleLine(sglres.get(i)));
            }
            log(Thread.currentThread().getStackTrace()[1].getMethodName()+": " + "Regexp is "+this.RegexpTextField.getText(),
                    "Info");

            //save last match RegExp to this.history,when windows close normally,it will be save to History.txt
            this.history.add(this.RegexpTextField.getText());
            //save his to mypatterns
            if(this.settings.autosavehis2mypatterns&&this.isautocheck.isSelected()==false){
                this.MyPatternsTableView.getItems().add(
                        this.MyPatternsTableView.getItems().size()-1,
                        new mpLine(RegexpTextField.getText(),""));
            }
            System.out.println("----------match execute end----------");

         }catch(java.util.regex.PatternSyntaxException pse){
                System.out.print(pse.getMessage());
            log(Thread.currentThread().getStackTrace()[1].getMethodName()+": " + pse.getClass()+" "+ pse.getDescription(),
                    "Error");

        }catch(Exception e){
            System.out.println(event.getSource().toString());
            log(Thread.currentThread().getStackTrace()[1].getMethodName()+": " + e.getMessage(),"Error");
        }
    }

    @FXML
    void FromfileButtonOnAction(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            Stage stage = new Stage();
            this.FromfileButton.setDisable(true);
            File file = fileChooser.showOpenDialog(stage);
            log(Thread.currentThread().getStackTrace()[1].getMethodName() + ": " + "show open dialog", "Info");
            if (file != null) {
                this.MatchTextArea2.setText(Tools.fetchlocaldata(file));
                log(Thread.currentThread().getStackTrace()[1].getMethodName() + ": " + "get text from " + file.getPath(), "Info");
            }
            this.FromfileButton.setDisable(false);
        }catch(Exception e){
            this.FromfileButton.setDisable(true);
            log(Thread.currentThread().getStackTrace()[1].getMethodName()+": " + e.getMessage(),"Error");
        }
    }

    @FXML
    void FromurlButtonOnAction(ActionEvent event) {
        AtomicReference<String> urlstr= new AtomicReference<>("");
        try {
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Input URL");

            VBox vbox = new VBox();
            vbox.setPadding(new Insets(5));
            vbox.setSpacing(10);
            vbox.setAlignment(Pos.BOTTOM_RIGHT);
            TextField urlTextField = new TextField();
            urlTextField.setPrefWidth(500);
            Button confirmButton = new Button("confirm");
            //confirmbutton.s
            vbox.getChildren().addAll(urlTextField, confirmButton);

            confirmButton.setOnAction(e->{
                try {
                    if ( urlTextField.getText ( ).equals ( "" ) ) {
                        System.out.println ( "url is empty!" );
                        primaryStage.close ( );
                        return;
                    } else if ( urlTextField.getText ( ).contains ( "https://" ) || urlTextField.getText ( ).contains ( "http://" ) )
                        urlstr.set ( urlTextField.getText ( ) );
                    else
                        urlstr.set ( "http://" + urlTextField.getText ( ) );
                    System.out.println("get html data from: "+urlstr.get());
                    String content = "";
                    URL url = new URL(urlstr.get());
                    InputStream is = url.openStream();
                    InputStreamReader isr = new InputStreamReader(is, "utf-8");
                    BufferedReader br = new BufferedReader(isr);
                    String line = "";
                    while ((line = br.readLine()) != null) {
                        content += line;
                    }
                    br.close();
                    isr.close();
                    is.close();
                    this.MatchTextArea2.setText(content);
                }catch(Exception ex){
                    log(Thread.currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage(), "Error");
                }
                primaryStage.close();
            });



            Scene scene = new Scene(vbox);

            primaryStage.setScene(scene);
            //primaryStage.setAlwaysOnTop(true);
            primaryStage.setResizable(false);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.initStyle(StageStyle.UTILITY);

            this.FromurlButton.setDisable(true);
            primaryStage.show();
            this.FromurlButton.setDisable(false);

            String pagecontent="";
            URL url = new URL(urlstr.get());
            InputStream is = url.openStream();
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String line="";
            while ((line = br.readLine()) != null) {
                pagecontent += line;
            }
            //System.out.print(pagecontent);
            br.close();
            isr.close();
            is.close();

            this.MatchTextArea2.setText(pagecontent);
            log(Thread.currentThread().getStackTrace()[1].getMethodName() + ": " + "get text from url at "+urlstr.get(), "Info");
        }catch(Exception e){
            this.FromurlButton.setDisable(false);
            log(Thread.currentThread().getStackTrace()[1].getMethodName() + ": " + e.getMessage(), "Error");
        }
    }

    @FXML
    void SaveSettingButtonOnAction(ActionEvent event){
        this.settings.write2file(Controller.resdir.get("settings"));
        log(Thread.currentThread().getStackTrace()[1].getMethodName()+": " + "save setting to file","Info");

        //new Alert(Alert.AlertType.NONE, "xxx不存在", new ButtonType[]{ButtonType.CLOSE}).show();
        Alert ale = new Alert(Alert.AlertType.INFORMATION, "Settings are saved!",  new ButtonType[]{});
        ale.show();
        //ale.close();
        System.out.println("Setting is writed.");
    }

    //menu
    @FXML
    void MenuFileCloseOnAction(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void MenuEditRegexpHistoryOnAcion(ActionEvent event) throws IOException {
        Notepad notp = new Notepad (new File(Controller.resdir.get("History")));
        log(Thread.currentThread().getStackTrace()[1].getMethodName() + ": " + "Edit History file.", "Info");
    }

    @FXML
    void MenuEditMypattensOnAction(ActionEvent event) throws IOException {
        Notepad notp = new Notepad (new File(Controller.resdir.get("mypatterns")));
        log(Thread.currentThread().getStackTrace()[1].getMethodName() + ": " + "Edit mypatterns file.", "Info");
    }

    @FXML
    void MenuEditSettingsOnAction(ActionEvent event) throws IOException {
        Notepad notp = new Notepad (new File(Controller.resdir.get("settings")));
        log(Thread.currentThread().getStackTrace()[1].getMethodName() + ": " + "Edit Setting file.", "Info");
    }

    @FXML
    void MenuHelpHandbookOnAction(ActionEvent event) {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("RegExp handbook");
        BorderPane bop = new BorderPane();


        WebView helpWebView=new WebView();
        java.net.CookieHandler.setDefault(null);
        WebEngine webEngine=new WebEngine();
        webEngine = helpWebView.getEngine();
        webEngine.load("file://"+ new File(Controller.resdir.get("handbook")).getAbsolutePath ());
        System.out.println("open handbook:"+new File(Controller.resdir.get("handbook")).getAbsolutePath ());

        bop.setCenter(helpWebView);
        Scene scene = new Scene(bop,1200,600);

        primaryStage.setScene(scene);
        //primaryStage.setAlwaysOnTop(true);
        primaryStage.show();
    }

    @FXML
    void MenuHelpAboutOnAction(ActionEvent event) {
        Stage primaryStage = new Stage();
        BorderPane bop = new BorderPane();

        Label about = new Label();
        about.setText("This is a tool for Regular expression.\n" +
                "Author: sketchdee.\n" +
                "Last build time: 2021.2.28\n"+
                "We all hope for a better world.");
        bop.setCenter(about);
        Scene scene = new Scene(bop,300,100);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.initStyle(StageStyle.UTILITY);

        primaryStage.setScene(scene);
        //primaryStage.setAlwaysOnTop(true);
        primaryStage.show();
    }

}