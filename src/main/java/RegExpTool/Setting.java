package RegExpTool;

import java.io.*;
import java.util.LinkedList;

class Setting implements Serializable {
    public boolean defaultautocheck;
    public boolean defaultshow2;
    public boolean defultgettextfromlr;
    public boolean defaultshowmutipleresult;
    public boolean showconsoleinfo;
    public boolean savehistory;
    public boolean autosavehis2mypatterns;
    public int defaultsidetab;
    public int defaultsingleresult;
    public Setting(){
        this.defaultautocheck=true;
        this.defaultautocheck=true;
        this.defaultshow2=true;
        this.defultgettextfromlr=true;
        this.defaultshowmutipleresult=true;
        this.showconsoleinfo=true;
        this.savehistory=true;
        this.autosavehis2mypatterns=true;
        defaultsidetab=0;
        defaultsingleresult=0;
    }

    public static String getDefaultSetting(){
        return "#RegExpToll Setting file.\n" +
                "#The line start with '#' will be ignore.\n" +
                "\n" +
                "#Whether automatic checking is enabled by default on startup.\n" +
                "defaultautocheck=true\n" +
                "\n" +
                "#Whether show advace matching text\n" +
                "defaultshow2=true\n" +
                "\n" +
                "#Whether get text from last run,if not default matching text will be empty.\n" +
                "defultgettextfromlr=true\n" +
                "\n" +
                "#Whether show mutiple result first.\n" +
                "defaultshowmutipleresult=true\n" +
                "\n" +
                "#Whether show console informations.\n" +
                "showconsoleinfo=true\n" +
                "\n" +
                "#Whether save history Regexp to history file.\n" +
                "savehistory=true\n" +
                "\n" +
                "#Whether automatic save regexp to my patterns which is show and save in application.\n" +
                "autosavehis2mypatterns=true\n" +
                "\n" +
                "#startup side tab,\n" +
                "#0 settings tab\n" +
                "#1 MyPatterns tab\n" +
                "#2 Cheat Sheet tab\n" +
                "#3 RegExp Referance tab\n" +
                "#4 hide the side tab\n" +
                "defaultsidetab=0\n" +
                "\n" +
                "#0 JAVA,1 Python,2 C#\n" +
                "defaultsingleresult=0\n";
    }

    public void set(boolean defaultautocheck,
                    boolean defaultshow2,
                    boolean defultgettextfromlr,
                    boolean defaultshowmutipleresult,
                    boolean showconsoleinfo,
                    boolean savehistory,
                    boolean autosavehis2mypatterns,
                    int defaultsidetab,
                    int defaultsingleresult){
        this.defaultautocheck=defaultautocheck;
        this.defaultshow2=defaultshow2;
        this.defultgettextfromlr=defultgettextfromlr;
        this.defaultshowmutipleresult=defaultshowmutipleresult;
        this.showconsoleinfo=showconsoleinfo;
        this.savehistory=savehistory;
        this.autosavehis2mypatterns=autosavehis2mypatterns;
        this.defaultsidetab=defaultsidetab;
        this.defaultsingleresult=defaultsingleresult;
    }

    public Object get(String name){
        if(name.equals("defaultautocheck"))
            return this.defaultautocheck;
        if(name.equals("defaultshow2"))
            return this.defaultshow2;
        if(name.equals("defultgettextfromlr"))
            return this.defultgettextfromlr;
        if(name.equals("defaultshowmutipleresult"))
            return this.defaultshowmutipleresult;
        if(name.equals("showconsoleinfo"))
            return this.showconsoleinfo;
        if(name.equals("savehistory"))
            return this.savehistory;
        if(name.equals("autosavehis2mypatterns"))
            return this.autosavehis2mypatterns;
        if(name.equals("defaultsidetab"))
            return this.defaultsidetab;
        if(name.equals("defaultsingleresult"))
            return this.defaultsingleresult;
        return null;
    }

    public static Setting ReadSetting (String position){
        File file = new File(position);
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        LinkedList<String> LLr = new LinkedList<String>();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                if (!tempStr.equals("") && tempStr.charAt(0) != '#')
                    LLr.add(tempStr + "\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        LinkedList<String> s =LLr;
        String item;
        boolean [] bl=new boolean[7];
        int i8=0,i9=0;
        String [] sl={"defaultautocheck",
                "defaultshow2",
                "defultgettextfromlr",
                "defaultshowmutipleresult",
                "showconsoleinfo",
                "savehistory",
                "autosavehis2mypatterns"};
        System.out.println("Read Settings...");
        for (int i = 0; i < s.size(); i++) {
            item = s.get(i);
            System.out.print(s.get(i));
            for(int j=0;j<sl.length;j++){
                if (item.contains(sl[j])) {
                    if (item.contains("=true"))
                        bl[j]=true;
                    else if (item.contains("=false"))
                        bl[j]=false;
                }
            }
            if (item.contains("defaultsidetab")){
                i8=Integer.parseInt(item.split("=")[1].trim());
            }
            if (item.contains("defaultsingleresult")){
                i9=Integer.parseInt(item.split("=")[1].trim());
            }
        }
        Setting se = new Setting();
        se.set(bl[0],bl[1],bl[2],bl[3],bl[4],bl[5],bl[6],i8,i9);
        return se;
    }

    public static void Write2File(String position,Setting se){
        File file = new File(position);
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        String content="";
        String [] sl={"defaultautocheck",
                "defaultshow2",
                "defultgettextfromlr",
                "defaultshowmutipleresult",
                "showconsoleinfo",
                "savehistory",
                "autosavehis2mypatterns",
                "defaultsidetab",
                "defaultsingleresult"};
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            String item="";
            while ((tempStr = reader.readLine()) != null) {
                if (!tempStr.equals("") && tempStr.charAt(0) != '#'){
                    for(int j=0;j<sl.length;j++){
                        if(tempStr.contains(sl[j])) {
                            tempStr=sl[j]+"="+se.get(sl[j]).toString();
                        }
                    }
                }
                content+=(tempStr + "\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        Tools.writeinfile(position,content);
    }

    public void write2file(String path){
        Write2File(path,this);
    }

    public static boolean CheckSettingFile(String position){
        //need fix bug ,if contain item but the value is wrong.
        File file = new File(position);
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        LinkedList<String> LLr = new LinkedList<String>();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                if (!tempStr.equals("") && tempStr.charAt(0) != '#')
                    LLr.add(tempStr + "\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        LinkedList<String> s =LLr;
        String item;
        String [] sl={
                "defaultautocheck",
                "defaultshow2",
                "defultgettextfromlr",
                "defaultshowmutipleresult",
                "showconsoleinfo",
                "savehistory",
                "autosavehis2mypatterns",
                "defaultsidetab",
                "defaultsingleresult"};
        boolean [] IsIn=new boolean[9];
        for (int i = 0; i < s.size(); i++) {
            item = s.get(i);
            for(int j=0;j<sl.length;j++){
                if (item.contains(sl[j])){
                    IsIn[j]=true;
                }
            }
        }
        for(int i=0;i<9;i++)
        {
            if(IsIn[i])
                return false;
        }
        return true;
    }

    public boolean isRightful(){
        if(this.defaultsidetab>3||this.defaultsingleresult>2)
            return false;
        return true;
    }

    public void fixMiss(){
        if(this.defaultsidetab>4)
            this.defaultsidetab=0;
        if(this.defaultsingleresult>2)
            this.defaultsingleresult=0;
    }
}
