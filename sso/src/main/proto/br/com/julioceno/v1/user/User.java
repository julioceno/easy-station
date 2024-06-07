package br.com.julioceno.v1.user;
import java.util.*;
import java.lang.*;
import io.protostuff.Tag;
public class User  {
    @Tag(1)
    String id;
    @Tag(2)
    String email;
    @Tag(3)
    String role;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}
