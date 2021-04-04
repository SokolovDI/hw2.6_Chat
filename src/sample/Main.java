
/**
 Разобраться с кодом
 Организовать запрет аутентификации с одной учетной записи.
 Т.е. под логином и пароль может сидеть только на одном ПК.

 Организовать отправку личных сообщений.
 Например, пользователь nick1 вводит:
 @nick2 привет
 У пользователя nick1 и nick2 должно быть в чате:
 nick1: [Отправлено для nick2] привет
 Остальные пользователи не должны увидеть это сообщение
 */

package sample;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Chat");
        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.show();
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event -> {
            Controller controller = loader.getController();
            controller.disconnect();
        });

    }
    public static void main(String[] args) {
        launch(args);
    }
}
