package si.evinjete.ui;

import java.io.Serializable;
import java.util.Date;

public class Prekrsek implements Serializable {

    private Integer id;
    private String numberPlate;
    private String location;
    private Date timestamp;
    private Integer imageId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) { this.id = id; }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public String getLocation() {
        return location;
    }

    public void  setLocation(String location) {
        this.location = location;
    }

    public Integer getImageId() { return imageId; }

    public void setImageId(Integer imageId) { this.imageId = imageId; }
}
