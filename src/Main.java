
import java.util.ArrayList;
import java.util.Random;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    
    double orgSceneX, orgSceneY;
    int nodeCount = 0;
    
    Pane canvas = new AnchorPane();
    Graph graph = new Graph();
    //boolean firstClick, secondClick;
    ArrayList<Circle> selected = new ArrayList<>();
    
    @Override
    public void start(Stage primaryStage) {
        
        Random r = new Random();
        BorderPane border = new BorderPane();
        HBox bottomMenu = bottomMenu();

        border.setBottom(bottomMenu);
        border.setCenter(canvas);
        
        EventHandler<MouseEvent> selectNode = (MouseEvent event) -> {
            
            Circle circle = (Circle)event.getSource();
            
            circle.setFill(Color.RED);
        };
        
        canvas.setOnMouseClicked(e -> {

            if (e.getButton() == MouseButton.PRIMARY) {
                
                
                if(selected.size() == 2){
                    boolean isConnected = false;
                    
                    
                    int node1 = Integer.parseInt(selected.get(0).getId());
                    int node2 = Integer.parseInt(selected.get(1).getId());
                    
                    //ArrayList<Node> neighbour = graph.graph.get(node1);
                    
                    for(int i = 0; i < graph.graph.get(node1).size(); i++){
                        if(graph.graph.get(node1).get(i).ID == node2){
                            isConnected = true;
                            break;
                        }
                    }
                    
                    if(!isConnected)
                        bottomMenu.getChildren().get(2).setVisible(true);
                    else
                        bottomMenu.getChildren().get(2).setVisible(false);
                }else{

                    bottomMenu.getChildren().get(2).setVisible(false);
                }
                
                //create a node everytime mouse click on the screen
                if(!(e.getTarget() instanceof Circle) && !(e.getTarget() instanceof Text)){
                    
                    graph.addNode();
                    createCircle(e.getSceneX(), e.getSceneY(), 50, Color.rgb(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
                    nodeCount++;
                }
            } else if (e.getButton() == MouseButton.SECONDARY) {
 
                //System.out.println("Right-Click");
                //canvas.getChildren().remove(e.getTarget());
            }
 
        });
        
        border.setMinSize(600, 600);
        Scene scene = new Scene(border);
        
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
        
    }

    public static void main(String[] args) {
        
        launch(args);
    }
    
    public HBox bottomMenu(){
        

        
        HBox bottomMenu = new HBox();
        
        bottomMenu.setPadding(new Insets(15, 12, 15, 12));
        bottomMenu.setSpacing(10);
        bottomMenu.setStyle("-fx-background-color: #336699;");
        
        Button resetButt = new Button("Reset");
        resetButt.setPrefSize(100, 20);
        
        resetButt.setOnMouseClicked((e) ->{
            
            canvas.getChildren().clear();
            graph = new Graph();
            selected.clear();
            nodeCount = 0;
        });
        
        Button connectButt = new Button("Connect");
        connectButt.setVisible(false);
        
        connectButt.setOnMouseClicked((e) -> {
            
            int node1 = Integer.parseInt(selected.get(0).getId());
            int node2 = Integer.parseInt(selected.get(1).getId());
            
            InputValue value = new AlertBox().connectAlert(checkConnection(node2, node1));
            
            if(value.submit == true){
                
                if(selected.size() == 2){
                    
                    createEdge(selected.get(0), selected.get(1), value.distance, value.timeTaken);
                    graph.connectNode(node1 , node2, value.timeTaken, value.distance);
                    
                    if(value.isBi){
                        createEdge(selected.get(1), selected.get(0), value.distance, value.timeTaken);
                        graph.connectNode(node2 , node1, value.timeTaken, value.distance);
                    }
                }
                
                
            }
        });

        
        Button sendButt = new Button("Send Message");
        sendButt.setPrefSize(100,20);
        sendButt.setOnMouseClicked((e) -> {
            
            int node1 = Integer.parseInt(selected.get(0).getId());
            int node2 = Integer.parseInt(selected.get(1).getId());
            
            shortestPath path = graph.findShortestPath(node1, node2);
            
            AlertBox.shortestMessage(path.ShortestPath, path.distance, path.timeTaken, node1, node2);
            
        });
        
        bottomMenu.getChildren().addAll(resetButt, sendButt, connectButt);
        
        return bottomMenu;
    }
    
    
    
    private void createCircle(double x, double y, double r, Color color) {
      
        Circle circle = new Circle(x, y, r, color);
        circle.setId(Integer.toString(nodeCount));
        
        Text text = new Text(circle.getId());
        text.setId(Integer.toString(nodeCount));
        
        text.xProperty().bind((circle.centerXProperty()));
        text.yProperty().bind(circle.centerYProperty());
        text.setFont(new Font("Arial",30));
        
        
        
        
        
        
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(1);
        
        circle.setCursor(Cursor.HAND);

        circle.setOnMousePressed((t) -> {
            
          orgSceneX = t.getSceneX();
          orgSceneY = t.getSceneY();

          Circle c = (Circle) (t.getSource());
          
          //circle click
          if(selected.size() >= 2){
            for(int i = 0; i < selected.size(); i++){
                selected.get(i).setStroke(Color.BLACK);
                selected.get(i).setStrokeWidth(1);
            }
            
            selected.clear();
          }
          
          if(!selected.contains(c)){
            selected.add(c);
          }
          
          c.setStroke(Color.BLUE);
          c.setStrokeWidth(5);

          System.out.println(selected.size());

          c.toFront();
          text.toFront();
        });
        circle.setOnMouseDragged((t) -> {
          double offsetX = t.getSceneX() - orgSceneX;
          double offsetY = t.getSceneY() - orgSceneY;

          Circle c = (Circle) (t.getSource());

          c.setCenterX(c.getCenterX() + offsetX);
          c.setCenterY(c.getCenterY() + offsetY);

          orgSceneX = t.getSceneX();
          orgSceneY = t.getSceneY();
        });
        
        canvas.getChildren().addAll(circle,text);
    }
    
    private void createEdge(Circle node1, Circle node2, int distance, int timeTaken){
        
        Random r = new Random();
        
        boolean isBi;
        
        int R = r.nextInt(155);
        int G = r.nextInt(155);
        int B = r.nextInt(155);
        
        isBi = checkConnection(Integer.parseInt(node2.getId()), Integer.parseInt(node1.getId()));
        
        Text value = new Text("Node " + node1.getId() + " To " + "Node " + node2.getId() + "\nDistance: " + distance + ", Time taken: " + timeTaken);
        value.setFill(Color.rgb(R,G,B));
        
        Line line = new Line();
        
        line.setStrokeWidth(2);
        line.setStroke(Color.rgb(R,G,B));
        
        
        
        if(!isBi){
            line.startXProperty().bind(node1.centerXProperty().add(20));
            line.startYProperty().bind(node1.centerYProperty().subtract(20));

            line.endXProperty().bind(node2.centerXProperty().add(20));
            line.endYProperty().bind(node2.centerYProperty().subtract(20));
            
            
            value.translateYProperty().bind(node1.centerYProperty().add(node2.centerYProperty()).divide(2).subtract(50));
        }else{
            line.startXProperty().bind(node1.centerXProperty().subtract(20));
            line.startYProperty().bind(node1.centerYProperty().add(20));

            line.endXProperty().bind(node2.centerXProperty().subtract(20));
            line.endYProperty().bind(node2.centerYProperty().add(20));
            
            value.translateYProperty().bind(node1.centerYProperty().add(node2.centerYProperty()).divide(2).add(50));
        }
        
        value.translateXProperty().bind(node1.centerXProperty().add(node2.centerXProperty()).divide(2).subtract(10));
        //value.translateXProperty().bind(node1.centerXProperty().add(node2.centerXProperty()).divide(2));
        //value.translateYProperty().bind(node1.centerYProperty().add(node2.centerYProperty()).divide(2));
        

        canvas.getChildren().addAll(line, value);
        line.toBack();
    }
    
    private boolean checkConnection(int node1, int node2){
        
        boolean isConnected = false;
        
        for(int i = 0; i < graph.graph.get(node1).size(); i++){
            
            if(graph.graph.get(node1).get(i).ID == node2){
                isConnected = true;
                
            }
        }
        
        return isConnected;
        
    }
    
    
    
     

}