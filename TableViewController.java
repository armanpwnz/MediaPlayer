package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

public class TableViewController implements Initializable {
	ObservableList<TableItem> items = FXCollections.observableArrayList();
	
	
	@FXML
	private Button playButton;
	@FXML
	private Button pauseButton;
	@FXML
	private Button stopButton;
	@FXML
	private Slider balanceSlider;
	@FXML
	private Slider volumeSlider;
	@FXML
	private TableColumn<TableItem, String> columnName;
	@FXML
	private TableView<TableItem> tableView;
	
	private MediaPlayer mediaPlayer;
	private MediaView mediaView;
	
	
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub
		columnName.setCellValueFactory(new PropertyValueFactory<TableItem, String>("name"));
		tableView.setItems(items);
	

	}

	public void tableViewClicked(MouseEvent event) {
		
		TableItem selectedItem = tableView.getSelectionModel().getSelectedItem();
		
		selectedItem.getUri();
		
	}
	
	public void openSlozka(ActionEvent event) {
		DirectoryChooser chooser = new DirectoryChooser();
		File directory = chooser.showDialog(null);
		
		for(File file : directory.listFiles()) {
			if(file.isFile()) {
				System.out.println(file.toURI().toString());
				initPlayer(file.toURI().toString());
			}
		}
	}
	
	
	private void initPlayer(String uri) {
		if (uri == null) {
			return;
		} 
		Media media = new Media(uri);
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setAutoPlay(true);
		
		volumeSlider.valueProperty().bindBidirectional(mediaPlayer.volumeProperty());
	}
	
	public void openItem(ActionEvent event) {
		
		System.out.println("Klikaju");
		FileChooser fc = new FileChooser();
		File f = fc.showOpenDialog(null);
		if (f != null) {
		System.out.println(f.toURI().toString());
		initPlayer(f.toURI().toString());
		}
		
	
	}
	
	
	public void positionSliderMoved (MouseEvent event) {
		if(mediaPlayer != null) {
			mediaPlayer.seek(mediaPlayer.getTotalDuration().multiply(balanceSlider.getValue()));
			
		}
	}
	
	public void closedItem(ActionEvent event) {
		System.out.println("Aplikacka zavrena");
		Platform.exit();
	}
	
	public void playButton() {
		mediaPlayer.play();
	
	}
	
	public void pauseButton() {
		mediaPlayer.pause();
		
	}
	
	public void stopButton() {
		mediaPlayer.stop();

	}
	
}
