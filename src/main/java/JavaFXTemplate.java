import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;


public class JavaFXTemplate extends Application {
	Server serverConnection;
	HashMap<String, Scene> sceneMap;
	TextField portField;
	Button onButton, offButton;
	Button gameInfoButton;
	Label numberOfClientsLabel;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
		
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		sceneMap = new HashMap<String, Scene>();
		primaryStage.setTitle("Baccarat Server");
		portField = new TextField();
		portField.setPromptText("Enter Port");

		portField.setStyle("-fx-pref-width: 300px");
		onButton = new Button("ON");
		onButton.setStyle("-fx-pref-height: 300px");
		onButton.setStyle("-fx-pref-width: 350px");
		offButton = new Button("OFF");
		offButton.setStyle("-fx-pref-height: 300px");
		offButton.setStyle("-fx-pref-width: 350px");
		HBox buttonBox = new HBox(10,onButton,offButton);
		gameInfoButton = new Button("Game Info");
		Label connectLabel = new Label();
		connectLabel.setStyle("-fx-font-size: 20px");
		VBox serverIntroVBox = new VBox(10,portField, buttonBox,gameInfoButton, connectLabel);
		serverIntroVBox.setAlignment(Pos.CENTER);
		BorderPane container = new BorderPane();
		container.setPadding(new Insets(100));
		container.setCenter(serverIntroVBox);
		Scene serverIntroScene = new Scene(container, 500,500);
		sceneMap.put("serverIntro",serverIntroScene);
		portField.getParent().requestFocus();
		sceneMap.put("gameInfo", createGameInfoGUI(primaryStage));

		primaryStage.setScene(sceneMap.get("serverIntro"));
		primaryStage.show();

		gameInfoButton.setOnAction(event -> primaryStage.setScene(sceneMap.get("gameInfo")));
		onButton.setOnAction(event -> {
			String port = portField.getText();
			try{
				ConnectionInfo.setPORT(Integer.parseInt(port));
				serverConnection = new Server(data -> {
					Platform.runLater(()->{
						updateGUI((HashMap<Integer, BaccaratInfo>) data);
					});
				});
			}catch (Exception e){
				connectLabel.setText("Can't Start Port. Try Again.");
				connectLabel.setTextFill(Color.web("#b30404"));
				return;
			}
			connectLabel.setText("Server is running!");
			connectLabel.setTextFill(Color.web("#009603"));

		});
		offButton.setOnAction(event -> {
			connectLabel.setText("This feature doesn't work yet :(");
			connectLabel.setTextFill(Color.web("#000000"));
		});
	}

	Label c1StatusLabel;
	Label c2StatusLabel;
	ListView c1GameInfoBox;
	ListView c2GameInfoBox;

	public void updateGUI(HashMap<Integer,BaccaratInfo> baccaratInfoHashMap){
		numberOfClientsLabel.setText("Number of clients " + serverConnection.count);
		c1StatusLabel.setText(baccaratInfoHashMap.get(1).clientConnected);
		c2StatusLabel.setText(baccaratInfoHashMap.get(2).clientConnected);
		c1GameInfoBox.getItems().clear();
		if(c1StatusLabel.getText().equals("Connected")){
			c1GameInfoBox.getItems().add("Bet: " + baccaratInfoHashMap.get(1).bet);
			c1GameInfoBox.getItems().add("Total: " +baccaratInfoHashMap.get(1).totalEarnings);
			c1GameInfoBox.getItems().add("Playing Another Hand?: " +baccaratInfoHashMap.get(1).playingAnotherHand);

		}
		c2GameInfoBox.getItems().clear();
		if(c2StatusLabel.getText().equals("Connected")){
			c2GameInfoBox.getItems().add("Bet: " +baccaratInfoHashMap.get(2).bet);
			c2GameInfoBox.getItems().add("Total: " +baccaratInfoHashMap.get(2).totalEarnings);
			c2GameInfoBox.getItems().add("Playing Another Hand?: " +baccaratInfoHashMap.get(2).playingAnotherHand);
		}
	}
	public Scene createGameInfoGUI(Stage primaryStage){
		numberOfClientsLabel = new Label("Number of Clients: 0");
		numberOfClientsLabel.setStyle("-fx-font-size: 20px");
		numberOfClientsLabel.setTextFill(Color.web("#ffffff"));

		Label c1 = new Label("Client 1: ");
		c1.setStyle("-fx-font-size: 20px");
		c1.setTextFill(Color.web("#ffffff"));
		c1StatusLabel = new Label("Disconnected");
		c1StatusLabel.setStyle("-fx-font-size: 20px");
		c1StatusLabel.setTextFill(Color.web("#ffffff"));
		c1GameInfoBox = new ListView();
		c1GameInfoBox.setStyle("-fx-pref-height: 300px");
		ArrayList c1GameInfo = new ArrayList();

		Label c2 = new Label("Client 2: ");
		c2.setStyle("-fx-font-size: 20px");
		c2.setTextFill(Color.web("#ffffff"));
		c2StatusLabel = new Label("Disconnected");
		c2StatusLabel.setStyle("-fx-font-size: 20px");
		c2StatusLabel.setTextFill(Color.web("#ffffff"));
		c2GameInfoBox = new ListView();
		c2GameInfoBox.setStyle("-fx-pref-height: 300px");
		ArrayList c2GameInfo = new ArrayList();

		Button homeButton = new Button("Back to Server");
		homeButton.setOnAction(event -> primaryStage.setScene(sceneMap.get("serverIntro")));

		HBox h1 = new HBox(c1,c1StatusLabel);
		h1.setAlignment(Pos.CENTER);
		HBox h2 = new HBox(c2,c2StatusLabel);
		h2.setAlignment(Pos.CENTER);
		VBox v1 = new VBox(h1,c1GameInfoBox);
		VBox v2 = new VBox(h2,c2GameInfoBox);
		HBox h3 = new HBox(10,v1,v2);
		VBox v3 = new VBox(20,numberOfClientsLabel, h3, homeButton);
		v3.setAlignment(Pos.CENTER);
		BorderPane gameInfoContainer = new BorderPane();
		gameInfoContainer.setCenter(v3);
		gameInfoContainer.setStyle("-fx-background-color: darkgreen");
		gameInfoContainer.setPadding(new Insets(10));

		Scene scene = new Scene(gameInfoContainer, 500,500);
		return scene;
	}
}

