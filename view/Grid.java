package view;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Grid extends Pane {
    int x,y;
    public Cell myCells [][];
    VBox column;
    HBox[] row;

    Grid(int x, int y){
        this.x=x;
        this.y=y;
        myCells = new Cell[x][y];

        for(int i=0;i<x;i++){
            for(int j =0;j<y;j++){
                myCells[i][j] = new Cell(i,j,x);
            }
        }

        column =new VBox(0);
        row =new HBox[x];
        for(int i=0;i<x;i++){
            row[i] = new HBox(0);
            column.getChildren().add(row[i]);
            for(int j=0;j<y;j++){
                row[i].getChildren().add(myCells[i][j]);
            }
        }

        setMaxSize(750,750);
        setMinSize(750,750);
        getChildren().add(column);

    }

    public Cell getCells(int i, int j){
        return myCells[i][j];
    }
}
