package sample.modelLibrary;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Category {
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;

    public Category() {
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
   }

   //task.setOnducceeded(e->categoryTable.getItems().setAll(artistResults);

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }
}