package RegExpTool;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        /*
        *Static way of initialize a window,it also mean we unable fetch data here.
        Parent root = FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
        primaryStage.setTitle("RegExp Tool");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.show();
         */
        URL location = getClass().getResource("mainWindow.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        System.out.println (new File("mainWindow.fxml").getAbsolutePath ());
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("RegExpTool");
        Scene scene = new Scene(root, 1000, 600);
        //load css
        //scene.getStylesheets().add(getClass().getResource("style1.css").toExternalForm());
        primaryStage.setScene(scene);
        Controller controller = (Controller) fxmlLoader.getController();   //get Controller Object.
        //CloseRequest event,it will be execute when close pressed.
        primaryStage.setOnCloseRequest(e->{
            String content="";
            for(String s:controller.history)content+=(s+"\n");
            Tools.writeintail(Controller.resdir.get("History"),content);
            System.out.println("regexp history: "+controller.history);
            System.out.println("clos request get.");
            Controller.log(Thread.currentThread().getStackTrace()[1].getMethodName() + ": " + "RegexpTool closed.", "Info");
            //use to make all program end,at same time
            java.lang.Runtime.getRuntime().exit(0);
            //System.exit(0);
        });
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
