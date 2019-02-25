package controllers;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import models.Post;
import models.User;
import models.daoModel;
import models.daoModelImpl;

public class PostsViewController {
	@FXML
	private Button logoutButton;
	@FXML
	private Label userPostsLabel;
	@FXML
	private Button uploadButton;
	@FXML
	private ListView<String> postsListView;
	@FXML
	private TextField newPost;
	
	public void userInteraction(final models.LoginManager loginManager, User session)
			throws Exception {
		daoModel dao  = new daoModelImpl();

		userPostsLabel.setText("Welcome " + session.getUsername() + "!");
		logoutButton.setOnAction(e -> loginManager.logout());
		uploadButton.setOnAction(e -> {
			try {
				if (newPost.getText() == null || newPost.getText().trim().equals("")) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("ERROR");
					alert.setHeaderText("Upload post error");
					alert.setContentText("You must write a new post");
					alert.showAndWait();
				} else {
					Post p = new Post (newPost.getText(), session.getUsername());
					dao.insertPost(p);
					showPosts(dao);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		showPosts(dao);
	}
	private ObservableList<String> list = FXCollections.observableArrayList();

	public void showPosts(daoModel dao) throws Exception {
		list.removeAll(list);
		List<Post> posts =dao.getPosts();
		for (int i = 0; i<posts.size(); i++) {
			String user = posts.get(i).getUsername();
			String post = posts.get(i).getPost();
			String poste = user + ": " + post;
			list.add(poste);
		}
		postsListView.getItems().clear();
		postsListView.getItems().addAll(list);

	}

}
