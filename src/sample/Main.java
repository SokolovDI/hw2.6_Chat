
/**
 1. Разобраться с кодом.
 2. Добавить отключение неавторизованных пользователей по таймауту
 (120 сек. ждём после подключения клиента, и если он не авторизовался за это время, закрываем соединение).
 3. Хранить blacklist в БД
 4. Сделать блокировку личных сообщений если пользователь в blacklist
 5. История сообщений (хранить в БД в новой таблице)
 6. Добавить проверки на null при взаимодействии по сети
 */

package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Chat");
        primaryStage.setScene(new Scene(root, 600, 450));
        primaryStage.show();
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                Controller controller = loader.getController();
                controller.disconnect();
            }
        });
    }
    public static void main(String[] args) {
        launch(args);
    }
}