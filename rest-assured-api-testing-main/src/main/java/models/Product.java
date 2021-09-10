package models;

import org.json.simple.JSONObject;

public class Product {

    public String nome;
    public int preco;
    public String descricao;
    public int quantidade;
    public String productID;
    public String name;
    public String email;
    public String password;
    public String isAdmin;


    public void User(String name, String email, String password, String isAdmin){
        this.name = name;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public Product(String name, int preco, String descricao, int quantidade){
        this.nome = name;
        this.preco = preco;
        this.descricao = descricao;
        this.quantidade = quantidade;
    }

    public void setProductId(String productID){
        this.productID = productID;
    }
    public String getProductId(){
        JSONObject userJsonRepresentation = new JSONObject();
        userJsonRepresentation.put("nome",this.nome);
        userJsonRepresentation.put("preco",this.preco);
        userJsonRepresentation.put("descricao",this.descricao);
        userJsonRepresentation.put("quantidade",this.quantidade);
        return userJsonRepresentation.toJSONString();
    }

    public String getUserId(){
        JSONObject userJsonRepresentation = new JSONObject();
        userJsonRepresentation.put("nome",this.name);
        userJsonRepresentation.put("email",this.email);
        userJsonRepresentation.put("password",this.password);
        userJsonRepresentation.put("administrador",this.isAdmin);
        return userJsonRepresentation.toJSONString();
    }

    public String getProductCredentialsAsJson(){
        JSONObject productJsonRepresentation = new JSONObject();
        productJsonRepresentation.put("email",this.email);
        productJsonRepresentation.put("password",this.password);
        return productJsonRepresentation.toJSONString();
    }

}
