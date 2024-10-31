package main.domain;

public class BirthdayCake<ID> implements Identifiable<ID> {
    private ID id;
    private final String name;
    private final String flavor;
    private final double price;
    private final Integer layers;

    public BirthdayCake(ID id, String name, String flavor, double price, Integer layers) {
        this.id = id;
        this.name = name;
        this.flavor = flavor;
        this.price = price;
        this.layers = layers;
    }

    public BirthdayCake(String name, String flavor, double price, int layers) {
        this.name = name;
        this.flavor = flavor;
        this.price = price;
        this.layers = layers;
    }

    @Override
    public ID getId() {
        return id;
    }

    @Override
    public void setId(ID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getFlavor() {
        return flavor;
    }

    public double getPrice() {
        return price;
    }

    public Integer getLayers() {
        return layers;
    }

    @Override
    public String toString() {
        return "BirthdayCake [id=" + id + ", name=" + name + ", flavor=" + flavor + ", price=" + price + ", layers=" + layers + "]";
    }
}
