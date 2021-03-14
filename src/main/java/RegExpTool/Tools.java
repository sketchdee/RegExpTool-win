package RegExpTool;

//regexp
import java.io.*;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.regex.*;

//internet
import java.net.MalformedURLException;
import java.net.URL;


public class Tools {
    //正则匹配工具函数未设置异常处理，这也是异常出现最多的地方，应为正则表达式不规范就会报错
    public static LinkedList<String> match(String regexp, String source){
        LinkedList<String> LL = new LinkedList<String>();
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(source);
        while(matcher.find()){
            LL.add(matcher.group());
        }
        return LL;
    }

    //链表转字符数组工具
    public static String []  fetchresult(LinkedList<String> LL){
        String [] outputtext=new String [LL.size()];

        for(int i =0;i<LL.size();i++) {
            if (LL.get(i).length() > 0) {
                outputtext[i] = (LL.get(i) + "\n");
                //System.out.print(outputtext[i]);
            }
        }
        return outputtext;
    }

    //从本地文本读取到字符串
    //设置一下换行符类型
    public static String fetchlocaldata(String position){
        File file = new File(position);
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                //sbf.append(tempStr+"\n"); 带有一般换行符的读取，\r\t \t\n \r\n换行符种类不止一种
                sbf.append(tempStr+"\n");
            }
            reader.close();
            return sbf.toString();
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
        return sbf.toString();
    }

    public static String fetchlocaldata(File file){
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        try {
            reader = new BufferedReader(new FileReader(file,Charset.forName ( "utf-8" )));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr+"\n");
            }
            reader.close();
            return sbf.toString();
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
        return sbf.toString();
    }

    //从网络下载网页解析为纯文本格式
    public static String fetchinternetdata(String hyperlink) {
        String content="";
        try {
            //创建一个URL实例
            URL url = new URL(hyperlink);
            //通过URL的openStream方法获取URL对象所表示的资源的字节输入流
            InputStream is = url.openStream();
            //将字节输入流转换为字符输入流
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            //为字符输入流添加缓冲
            BufferedReader br = new BufferedReader(isr);

            String line="";
            while ((line = br.readLine()) != null) {//循环读取数据
                content += line;
            }
            br.close();
            isr.close();
            is.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * Wirte content to file,old content will be covered.
     * */
    public static boolean writeinfile(String path,String content) {
        try{

            PrintWriter out= new PrintWriter(new BufferedWriter(new
                    OutputStreamWriter(new FileOutputStream(path), "UTF-8")));
            out.write(content);
            out.flush();
            out.close();

            //将写入转化为流的形式
            //BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            //一次写一行
            //bw.write(content);
            //bw.newLine();  //换行用

            //关闭流
            //bw.close();
            //System.out.println("写入成功");
            return true;
        }catch(IOException e)
        {
            return false;
        }catch(Exception e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean writeintail(String path,String content){
        FileOutputStream fos = null;
        OutputStreamWriter osw=null;
        try{
            File file = new File(path);
            if(!file.exists()){
                file.createNewFile();
                fos = new FileOutputStream(file,true);
            }else{
                fos = new FileOutputStream(file,true);
            }
            osw = new OutputStreamWriter(fos, "UTF-8");
            osw.write(content);
            osw.close();
            fos.close();
        }catch(Exception e){
            fos = null;
            osw = null;
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //写入Python脚本，获取python表达式结构、复杂的结构没法写入，智能考虑设置中间文件了。
    //Python考虑的很简单，将正则表达式写入py文件，再执行，在这之前，匹配的文本已存入文件.sourcetmp给python去读取,这也是解释型语言的优点。
    public static String WritePy(String regexp)  {
        String path = Controller.resdir.get("PyRegexp");
        LinkedList<String> LL = new LinkedList<String>();
        String tmpline;
        try{
            BufferedReader bfr =
                    new BufferedReader(new FileReader(new File(path)));
            while((tmpline=bfr.readLine())!=null){
                LL.add(tmpline);
            }
            LL.set(1,"source =open(r\""+Controller.resdir.get(".sourcetmp")+"\",encoding='UTF-8').read()");
            LL.set(2,"regexp=r\""+regexp+"\"");

            bfr.close();
            //将写入转化为流的形式
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            //一次写一行
            for(int i=0;i<LL.size();i++){
                bw.write(LL.get(i));
                bw.newLine();  //换行用
            }
            bw.close();
        }
        catch(FileNotFoundException FE){
            System.out.print(FE.getMessage());
        }
        catch(IOException IOE){
            System.out.print(IOE.getMessage());
        }
        return path;
    }

    //使用Runtime类来执行Console，并可以process类来读取命令行输出，以控制台为中间媒介来调用python和C#的正则表达式类，虽然最后的结论是：
    //python java C# 的正则表达式并没有什么不同，可以放心随表挑一个测试就好了，不过C#的正则表达式有些怪异，会报错，会匹配不到，后面得进行讨论
    public static LinkedList<String> ConsoleExec() {
        LinkedList<String> res = new LinkedList<String>();
        Runtime r = Runtime.getRuntime();
        Process process = null;
        try {
            process = r.exec("python"+" "+Controller.resdir.get("PyRegexp"));
            //process = r.exec("python"+" "+Controller.Dirpath+File.separator+"PyRegexp.py");
            System.out.println("python"+" "+Controller.resdir.get("PyRegexp"));
            String line = "";
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK")); //控制台得到的是GBK流
            while ((line = bufferedReader.readLine()) != null) {
                res.add(line + "\n");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return res;
    }

    //控制台执行命令的完全概化版本
    public static LinkedList<String> ConsoleExec(String cmd) {
        LinkedList<String> res = new LinkedList<String>();
        Runtime r = Runtime.getRuntime();
        Process process = null;
        try {
            process = r.exec(cmd);
            String line = "";
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(),"GBK"));
            while ((line = bufferedReader.readLine()) != null) {
                res.add(line + "\n");
            }

        } catch (Exception e) {
            System.out.println("Error:"+e.getMessage());
        }
        return res;
    }

    //编写了一个C#控制台命令程序，主要是利用main函数中string []args中存有控制台传进去的参数，以空格隔开，
    //linux 会议一些特定的-l等表示动作，这样就不用刻意的去在意顺序了，这个有空再总结吧，现在没必要，自己写自己用。
    public static LinkedList<String> CsharpReg(String regexp)
    {
        String cmd = "dotnet "+Controller.resdir.get("RegTestapp") + " -\""+regexp+"\""+" -\""+Controller.resdir.get(".sourcetmp")+"\"";
        System.out.println(cmd);
        LinkedList<String> LL4= Tools.ConsoleExec(cmd);
        return LL4;
    }
}

