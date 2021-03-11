# Regular expression testing tools
Regular expression testing tool, the use of Java development, convenient multi-platform offline use.Regular expression implementation engines are currently supported in Java, Python, and C#, and the current version is in beta.Using JavaFX 11 components development, follow the BSD protocol, you can obtain and modify this program for free, completely open source.
## How to use the code
**1.** The project is based on IDEA, and you can also use Eclipse or another IDE. <br/>
**2.** It is written based on the JavaFX library. JavaFX comes in different versions for different operating systems. There is no need to download the JavaFX SDK separately before you run the ".jar" package. <br/>
**3.** You can use Maven's OpenJFX plugin, javafx:run, to run it, or you can simply run “regexpTool.main.main ()”. <br/>
**4.** The code structures. <br/>
```console
\SRC
├─main
│  ├─java
│  │  └─RegExpTool
│  │          Controller.java
│  │          Main.java
│  │          Notepad.java
│  │          package-info.java
│  │          Setting.java
│  │          Tools.java
│  │
│  └─resources
│      └─RegExpTool
│              mainWindow.fxml
│
└─test
└─java
```
#### Controller
Defines how the windows handles events.
Almost all of the window event code is here.
#### Main
used for window startup.
#### Notepad
A standalone SWT program to solve cross-platform editing problems.
#### Setting
Used to customize the default behavior of the window.
#### Tools
Some tool classes which can be stripped and reused.
##  How to Run ".jar" Package.
You don't need to look at the following if you don't want to run the jar package.<br/>
JavaFX has been removed since Java8, which means that JavaFX is a third-party library, so the JVM needs to be told where the SDK is located.I don't know how avoid it.<br/>
#### (1) Get Windows [JAVAFX SDK here](https://gluonhq.com/products/javafx/) or download [openjfx-11.0.2_windows-x64_bin-sdk.zip](https://download2.gluonhq.com/openjfx/11.0.2/openjfx-11.0.2_windows-x64_bin-sdk.zip)
#### (2) unzip JAVAFX SDK and modify the "runregexptool.bat",change the "--module-path".
#### (3) Double click the "runregexptool.bat" in Windows explorer to run the ".jar" package.
#### (4) The ".jar" package is created by maven, you can use the jar plugin in maven to creat it again.
If you're struggling with which graphics library to use to implement your Windows, I would suggest you take a look at software based on this library. IDEA uses Swing, VS Code uses Electron, Visual Studio uses WPF, and you can also see that your KDE Plasma desktop is based on Qt. 
