package fr.epita.datamodel;

public class Contact {
    private String id;
    private String name;
    private String city;
    private String company;

    public Contact(String id, String name, String city, String company) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.company = company;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", company='" + company + '\'' +
                '}';
    }
}
