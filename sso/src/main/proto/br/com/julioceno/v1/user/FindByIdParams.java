package br.com.julioceno.v1.user;
import java.util.*;
import java.lang.*;
import io.protostuff.Tag;
public class FindByIdParams  {
    @Tag(1)
    String id;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
}
