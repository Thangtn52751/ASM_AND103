package dev.md19303.asm_and103.Model;

public class CakeModel {
    private String _id;
    private String name;
    private String description;
    private int price;

    public CakeModel(String _id, String description, String name, int price) {
        this._id = _id;
        this.description = description;
        this.name = name;
        this.price = price;
    }

    public CakeModel(String description, String name, int price) {
        this.description = description;
        this.name = name;
        this.price = price;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
