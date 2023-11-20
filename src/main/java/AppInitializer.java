import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/Assets/Assets/fxml/DashBoardForm.fxml")));

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("SHOPPING MART");
        primaryStage.getIcons().add(new Image("Assets/Assets/images/icon_-shopping-mall.png"));
        primaryStage.show();
    }
}
