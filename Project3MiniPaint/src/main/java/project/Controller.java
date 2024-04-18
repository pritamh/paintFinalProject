package project;

import java.util.List;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * @author Thomas Bolinger - bolitj01
 */

public class Controller {
	/**
	 * These Rectangle's function as the menu icon borders that get outlined in the menu ribbon
	 */
	@FXML
	private Rectangle circleMenuBorder;
	@FXML
	private Rectangle lineMenuBorder;
	@FXML
	private Rectangle rectangleMenuBorder;
	@FXML
	private Rectangle textMenuBorder;
	@FXML
	private Rectangle selectMenuBorder;
	@FXML
	private Rectangle animateMenuBorder;
	/**
	 * All the menu borders together to do checks 
	 * for avoiding animating them, etc.
	 */
	private List<Rectangle> menuBorders;

	/**
	 * Menu icon shapes that are colored by the sliders
	 */
	@FXML
	private Line menuLine;
	@FXML
	private Rectangle menuRectangle;
	@FXML
	private Circle menuCircle;
	@FXML
	private Text menuText;

	@FXML
	private CheckBox filledChk;

	@FXML
	private Slider redSlider;
	@FXML
	private Slider greenSlider;
	@FXML
	private Slider blueSlider;
	
	/**
	 * The drawing space in the center of the application
	 */
	@FXML 
	private Pane pane;

	//Cursor for interaction modes
	ImageCursor drawCursor;

	//The color that is determined by the combination of color Slider values
	private Color selectedColor;
	
	/**
	 * The node that is currently selected. It will have a border around it
	 * set by the select() method;
	 */
	private Node selectedNode;
	
	/**
	 * Mode can be any of the following:
	 * line: create a Line
	 * rectangle: create a Rectangle
	 * circle: create a Circle
	 * text: create a Text
	 * select: select any existing node
	 * animate: add animation to any selected node
	 */
	private String mode;
	
	//Flag to know if we are currently drawing a new shape (creating or resizing)
	private boolean drawing = false;

	//Store all shape animations
	ParallelTransition shapeTransitions;
	//Keep track of the transition currently being made
	TranslateTransition currentTransition;
	//Flicker the selectedNode to indicate that is the one being animated
	FadeTransition animationFader;

	//********* Extra credit variables
	//Distance from mouse in X and Y
	private double dX;
	private double dY;
	//********* End of extra credit

	@FXML
	void initialize() {
		//Put all menu borders together to check them with .contains later
		menuBorders = List.of(circleMenuBorder, lineMenuBorder,
				rectangleMenuBorder, textMenuBorder,
				selectMenuBorder, animateMenuBorder);

		//Avoid getting stuck on the checkbox when pressing spacebar
		filledChk.setFocusTraversable(false);

		//Put the same colorChange event on all sliders
		Slider[] sliders = {redSlider, greenSlider, blueSlider};
		for (Slider slider: sliders) {
			//TODO
		}

		//Initial slider colors
		redSlider.adjustValue(123);
		greenSlider.adjustValue(198);
		blueSlider.adjustValue(248);

		//Initialize image cursors for draw cursor
		/*
		 * The numbers shift the image a bit to put the tip of the pencil
		 * right where the mouse will actually click (called a cursor hot spot)
		 */
		Image drawImg = new Image(getClass().getResourceAsStream("draw-cursor.png"));
		drawCursor = new ImageCursor(drawImg, 70, 70);

		//Holds all other shape transitions
		shapeTransitions = new ParallelTransition();

		//Add all MousePress pane events (SceneBuilder can only assign one)
		pane.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
			createShape(e);
		});
		pane.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
			animateShape(e);
		});
	}

	/**
	 * When the color Slider's values change,
	 * Set the color of selectedColor and
	 * change the color of the menu icon shapes
	 * to match. 
	 */
	void colorChange() {
		//TODO
	}
	
	/**
	 * MousePress on the StackPane containing circleMenuBorder
	 * Assigned in the FXML
	 */
	@FXML
	void circleMode() {
		mode = "circle";
		pane.setCursor(drawCursor);
		select(circleMenuBorder);
	}

	/**
	 * MousePress on the StackPane containing lineMenuBorder
	 * Assigned in the FXML
	 */
	@FXML
	void lineMode() {
		mode = "line";
		pane.setCursor(drawCursor);
		select(lineMenuBorder);
	}

	/**
	 * MousePress on the StackPane containing rectangleMenuBorder
	 * Assigned in the FXML
	 */
	@FXML
	void rectangleMode() {
		mode = "rectangle";
		pane.setCursor(drawCursor);
		select(rectangleMenuBorder);
	}

	/**
	 * MousePress on the StackPane containing textMenuBorder
	 * Assigned in the FXML
	 */
	@FXML
	void textMode() {
		mode = "text";
		pane.setCursor(drawCursor);
		select(textMenuBorder);
	}

	/**
	 * MousePress on the StackPane containing selectMenuBorder
	 * Assigned in the FXML
	 */
	@FXML
	void selectMode() {
		mode = "select";
		pane.setCursor(null); //Cursor back to normal
		select(selectMenuBorder);
	}

	/**
	 * MousePress event on the StackPane containing animateMenuBorder
	 * Assigned in the FXML
	 */
	@FXML
	void animateMode() {
		mode = "animate";
		//Avoid null point and animating any menu borders
		if (selectedNode != null && !menuBorders.contains(selectedNode)) {
			/*
			 * Flicker the selectedNode
			 * Flicker duration is 1 second and repeats indefinitely until turned off
			 */
			//TODO
			
			
			/*
			 * Initialize the currentTransition to the selectedNode
			 * Animation duration is 1 second
			 */
			//TODO
			
			//No need to set the fromX or FromY as it will be the current node's X and Y by default
		}
	}

	/**
	 * KeyPress event on the entire application
	 * Assigned in the FXML
	 */
	@FXML
	void globalKeyEvents(KeyEvent e) {
//		//ESCAPE: Deselect shape or draw mode by returning to selectMode
//		if () {
//			//TODO
//		}
//		//Change text on a text
//		else if (){
//			//DELETE or BACK_SPACE: Remove last character of Text
//			//Any other key: Add character to Text
//			//TODO
//		}
//		//DELETE or BACK_SPACE: Delete shape
//		else if () {
//			//TODO
//		}
//		//SPACE: Reset and play animations
//		else if () {
//			//TODO
//		}

	}

	
	/**
	 * MousePress event to create a shape on the drawing space
	 */
	void createShape(MouseEvent e) {
		//Avoid null pointer
		if (selectedNode == null) {
			return;
		}

		//select and animate mode do not allow drawing a shape
		if (!(mode.equals("select") || mode.equals("animate"))) {
			//Get mouse coordinates
			//TODO

			//Set up the new Shape
			Shape newShape = null;

			//Make the correct kind of shape based on the mode
			//TODO
			
			//Add Mouse Press to select the shape
			newShape.addEventHandler(MouseEvent.MOUSE_PRESSED, e2 -> {
				//Don't select the shape if we are drawing a new one
				if (!drawing) {
					//TODO
				}
			});
			
			//Add Mouse Drag to move the shape
			newShape.setOnMouseDragged(e2 -> {
				//TODO
			});		
			drawing = true;
		}

	}

	@FXML
	/**
	 * MouseDrag event to size a shape after pressing to create the shape
	 * @param e
	 */
	void resizeShape(MouseEvent e) {
		//Avoid null pointer
		if (selectedNode == null) {
			return;
		}

		//Mouse coordinates
		//TODO

		//Are we still drawing a new shape?
		if (drawing) {

			//The last added shape is the one to change
			Shape lastShape = (Shape)pane.getChildren().get(pane.getChildren().size() - 1);

			//Update the shape based on what kind of shape it is
			//TODO
		}
	}

	@FXML
	/**
	 * MouseReleased event when the mouse is released after creating/resizing the 
	 * inserted shape.
	 * Should revert to select mode automatically
	 */
	void doneDrawing(MouseEvent e) {
		//If it is select or animate mode, we want to allow selecting shapes
		if (!(mode.equals("select") || mode.equals("animate"))) {
			drawing = false;
			//Back to select mode
			selectMode();
			//Select the last created node
			select(pane.getChildren().get(pane.getChildren().size() - 1));
		}
	}

	@FXML
	/**
	 * MouseDrag event added to a created shape.
	 * When the shape is dragged, it moves with the mouse
	 */
	void moveShape(MouseEvent e) {
		//Don't move shapes when we are drawing a new one 
		if (!drawing) {
			// Check event source for shape type
			Object source = e.getSource();
			//Get mouse coordinates
			//TODO
			
			//Move the shape based on what kind of shape it is
			//TODO 
		}
	}

	/**
	 * MousePress on the pane - Sets the animation destination 
	 * for the currently selected shape.
	 * Adds the animation to shapeAnimations.
	 */
	@FXML
	void animateShape(MouseEvent e) {
		//Avoid animating menu borders
		if (mode.equals("animate") && !menuBorders.contains(selectedNode)) {
			/*
			 * If animate mode is set, we know the currentTransition
			 * is ready to be finalized to where we are clicking now
			 */
			//Finalize the currentTransition depending on the shape
			//TODO
			
			//Add the animation to shapeTransitions
			//TODO
			System.out.println("Added animation"); //For debugging
			
			//Finish flickering since the animation is set
			//TODO
			
			//Go back to select mode
			selectMode();
		}
	}

	/**
	 * "Select" a given Node. This will deselect
	 * any Node that is currently selected, and
	 * add a border around the new Node.
	 * @param n
	 */
	void select(Node n) {
		deselect();
		selectedNode = n;
		addBorder(n);
	}

	/**
	 * "Deselect" the Node that is currently selected. 
	 * Takes off the selection border.
	 * selectedNode is nullified;
	 */
	void deselect() {
		removeBorder(selectedNode);
		selectedNode = null;
	}

	/**
	 * Puts a border around a Node by giving it the
	 * style class "selected" which is styled in
	 * style.css
	 * @param n the Node to put a border on
	 */
	void addBorder(Node n) {
		try {
			n.getStyleClass().add("selected");
		} catch (Exception e) {
			//Ignore when no node is selected
		}
	}

	/**
	 * Removes a border from a Node by removing the
	 * style class "selected" which is styled in
	 * style.css
	 * @param n the Node to remove a border from
	 */
	void removeBorder(Node n) {
		try {
			n.getStyleClass().remove("selected");
		} catch (NullPointerException e) {
			/*
			 * This will happen if no node is selected.
			 * In this case that is OK and expected.
			 */
		}
	}
}



























