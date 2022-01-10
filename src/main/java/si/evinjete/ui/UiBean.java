package si.evinjete.ui;

import si.evinjete.uporabniki.Uporabnik;
import si.evinjete.vinjete.Vinjeta;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;

@Named
@SessionScoped
public class UiBean implements Serializable {

    WebTarget wb;

    public UiBean(){}

    String name;
    String surname;
    String email;
    Integer type;
    String password;
    String numberPlate;
    Integer id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void registerUser() {
        Uporabnik uporabnik = new Uporabnik();
        uporabnik.setName(this.name);
        uporabnik.setSurname(this.surname);
        uporabnik.setEmail(this.email);
        uporabnik.setPassword(this.password);
        uporabnik.setType(this.type);

        Client client = ClientBuilder.newClient();
        wb = client.target("http://uporabniki-service.default.svc.cluster.local:8080/v1/uporabniki");
        String response = wb.request(MediaType.APPLICATION_JSON).post(Entity.json(uporabnik), String.class);

        System.out.println("INFO -- user " + response + " registered.");

        this.name = null;
        this.surname = null;
        this.email = null;
        this.password = null;
        this.type = null;
        this.id = null;
    }

    public String loginUser() {
        Client client = ClientBuilder.newClient();
        wb = client.target("http://uporabniki-service.default.svc.cluster.local:8080/v1/uporabniki/verify");
        Response response = wb
                .queryParam("user", this.email)
                .queryParam("password", this.password)
                .request().get();

        if (response.getStatus() == 406) {
            this.email = null;
            this.password = null;
            return "wrong";
        }

        if (response.getStatus() == 200) {
            Uporabnik uporabnik = response.readEntity(Uporabnik.class);
            System.out.println("INFO -- user " + uporabnik.getName() + " logged-in.");
            this.name = uporabnik.getName();
            this.surname = uporabnik.getSurname();
            this.type = uporabnik.getType();
            this.id = uporabnik.getId();

            switch (this.type) {
                case 0:
                    return "user";
                case 1:
                    return "officer";
                case 2:
                    return "admin";
                default:
                    return "welcome";
            }
        }

        this.email = null;
        this.password = null;
        return "wrong";
    }

    public String purchase() {
        Vinjeta vinjeta = new Vinjeta();
        vinjeta.setNumberPlate(this.numberPlate);
        vinjeta.setClientId(this.id);

        Client client = ClientBuilder.newClient();
        wb = client.target("http://vinjete-service.default.svc.cluster.local:8080/v1/vinjete");
        Response response = wb.request(MediaType.APPLICATION_JSON).post(Entity.json(vinjeta));

        if (response.getStatus() == 406) {
            this.numberPlate = null;
            return "fail";
        }

        if (response.getStatus() == 200) {
            return "purchase_success";
        }

        return "fail";
    }
}
