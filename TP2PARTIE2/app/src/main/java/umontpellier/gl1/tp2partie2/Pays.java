package umontpellier.gl1.tp2partie2;

public class Pays {
    private int id;  // Ajout du champ id
    private String name;
    private String description;
    private String latitude;
    private String longitude;
    private String imagePath;

    // Constructeur
    public Pays(int id, String name, String description, String latitude, String longitude, String imagePath) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imagePath = imagePath;
    }


    public Pays(String name, String description, String latitude, String longitude, String imagePath) {

        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imagePath = imagePath;
    }

    // Getter et Setter pour l'id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter et Setter pour le name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter et Setter pour la description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter et Setter pour la latitude
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    // Getter et Setter pour la longitude
    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    // Getter et Setter pour l'image
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
