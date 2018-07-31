package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.modelLibrary.DatasourceLib;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        controller.listCategories();

        primaryStage.setTitle("My Library Database");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.show();
    }

    @Override
    public void init() throws Exception {
        super.init();
        DatasourceLib.getInstance().open();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        DatasourceLib.getInstance().close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
