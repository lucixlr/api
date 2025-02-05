package org.example.servicios;

import com.google.gson.Gson;
import org.example.dao.LibroDAOInterface;
import org.example.entidades.Libro;
import spark.Spark;

import java.util.List;

public class LibrosAPIREST {
    private LibroDAOInterface dao;
    private Gson gson = new Gson();

    public LibrosAPIREST(LibroDAOInterface implementacion) {
        String puerto = System.getenv("PORT");

        //int port = Integer.parseInt(puerto);
        Spark.port(8080);

        dao = implementacion;


        // Endpoint para obtener todos los libros
        Spark.get("/libros", (request, response) -> {
            List<Libro> libros = dao.devolverTodos();
            response.type("application/json");
            return gson.toJson(libros);
        });

        // Endpoint para obtener un libro por su ID
        Spark.get("/libros/id/:id", (request, response) -> {
            long id = Long.parseLong(request.params(":id"));
            Libro libro = dao.buscarPorId(id);
            response.type("application/json");
            if (libro != null) {
                return gson.toJson(libro);
            } else {
                response.status(404);
                return "Libro no encontrado";
            }
        });

        // Endpoint para crear un nuevo libro
        Spark.post("/libros", (request, response) -> {
            String body = request.body();
            Libro nuevoLibro = gson.fromJson(body, Libro.class);

            Libro creado = dao.create(nuevoLibro);
            response.type("application/json");
            return gson.toJson(creado);
        });

        // Endpoint para actualizar un libro
        Spark.put("/libros/id/:id", (request, response) -> {
            Long id = Long.parseLong(request.params(":id"));
            String body = request.body();
            Libro libroActualizado = gson.fromJson(body, Libro.class);
            libroActualizado.setId(id);

            Libro actualizado = dao.actualizar(libroActualizado);
            if (actualizado != null) {
                return gson.toJson(actualizado);
            } else {
                response.status(404);
                return "No se ha podido realizar la actualización";
            }
        });

        // Endpoint para eliminar un libro
        Spark.delete("/libros/id/:id", (request, response) -> {
            long id = Long.parseLong(request.params(":id"));
            boolean eliminado = dao.deleteById(id);
            if (eliminado) {
                response.status(200);
                return "Libro eliminado correctamente";
            } else {
                response.status(404);
                return "Libro no encontrado para eliminar";
            }
        });
    }
}


