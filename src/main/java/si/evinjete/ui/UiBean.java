package si.evinjete.ui;

import si.evinjete.uporabniki.Uporabnik;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;

@Named
@SessionScoped
public class UiBean implements Serializable {

    WebTarget wb;

    public UiBean(){}

    String name;
    String surname;
    String email;
    String type;
    String password;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void registerUser() {
        Uporabnik uporabnik = new Uporabnik();
        uporabnik.setName(this.name);
        uporabnik.setSurname(this.surname);
        uporabnik.setEmail(this.email);
        uporabnik.setPassword(this.password);
        uporabnik.setType(Integer.parseInt(this.type));

        Client client = ClientBuilder.newClient();
        wb = client.target("http://uporabniki-service.default.svc.cluster.local:8080/v1/uporabniki");
        String response = wb.request(MediaType.APPLICATION_JSON).post(Entity.json(uporabnik), String.class);

        System.out.println("INFO -- user " + response + " registered.");

        this.name = null;
        this.surname = null;
        this.email = null;
        this.password = null;
        this.type = null;
    }

    public String loginUser() {
        Client client = ClientBuilder.newClient();
        wb = client.target("http://uporabniki-service.default.svc.cluster.local:8080/v1/uporabniki/verify");
        String response = wb
                .queryParam("user", this.email)
                .queryParam("password", this.password)
                .request().get(String.class);

        System.out.println("INFO -- user " + response + " logged-in.");

        String email = this.email;

        this.email = null;
        this.password = null;

        if (response.equals(email)) {
            return "user";
        }

        return "wrong";
    }
}
