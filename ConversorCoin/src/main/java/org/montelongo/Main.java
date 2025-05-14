package org.montelongo;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scan = new Scanner(System.in);


        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .create();

        while(true){
            System.out.println("Escribe salir, si ya no quieres usar el programada");
            System.out.println("Escribe el titula de a buscar: ");

            var Moneda = scan.nextLine();
            var direccion = "https://v6.exchangerate-api.com/v6/"
                    + "f2d5af2d3c0f8a1f91488c06" //Key ID
                    +"/latest/"
                    + Moneda; // Divisa

            if (Moneda.equalsIgnoreCase("salir")){
                break;
            }

            try{
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(direccion))
                        .build();
                HttpResponse<String> response = client
                        .send(request, HttpResponse.BodyHandlers.ofString());
                String json = response.body();

                System.out.println(json);

               // Moneda miTituloOmdb= gson.fromJson(json, Moneda.class);
                //Titulo miTitulo = new Titulo(miTituloOmdb);
                //System.out.println(miTitulo);

                //listTitulo.add(miTitulo);
            }catch (NumberFormatException e){
                System.out.println("Ocurrior un error: "+ e.getMessage());
            }catch (IllegalArgumentException e){
                System.out.println("Error en la URI, verifique la direccion");
            }
        }
        //System.out.println(listTitulo);

        System.out.println("Programacion Finalizada");
    }
}