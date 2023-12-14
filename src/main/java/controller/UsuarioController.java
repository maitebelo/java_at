package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.UsuarioDTOInput;
import dto.UsuarioDTOOutput;
import service.UsuarioService;

import java.util.List;

import static spark.Spark.*;

public class UsuarioController {
    private UsuarioService usuarioService;
    private ObjectMapper objectMapper;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
        this.objectMapper = new ObjectMapper();
        initializeRoutes();
    }

    private void initializeRoutes() {
        get("/usuarios", (req, res) -> {
            res.type("application/json");

            List<UsuarioDTOOutput> usuariosDTO = usuarioService.listarUsuarios();

            for (UsuarioDTOOutput usuario : usuariosDTO) {
                System.out.println("Usuário: " + usuario.getId() + ", Nome: " + usuario.getNome());
            }

            return objectMapper.writeValueAsString(usuariosDTO);
        });

        get("/usuarios/:id", (req, res) -> {
            res.type("application/json");
            int id = Integer.parseInt(req.params(":id"));
            UsuarioDTOOutput usuarioDTOOutput = usuarioService.buscarUsuario(id);

            if (usuarioDTOOutput != null) {
                return objectMapper.writeValueAsString(usuarioDTOOutput);
            } else {
                res.status(404);
                return "{\"error\": \"Usuário não encontrado para o ID: " + id + "\"}";
            }
        });


        delete("/usuarios/:id", (req, res) -> {
            res.type("application/json");
            int id = Integer.parseInt(req.params(":id"));
            usuarioService.excluirUsuario(id);
            res.status(204);
            return "OK 204";
        });

        post("/usuarios", (req, res) -> {
            res.type("application/json");

            UsuarioDTOInput usuarioDTOInput = objectMapper.readValue(req.body(), UsuarioDTOInput.class);
            usuarioService.inserirUsuario(usuarioDTOInput);

            res.status(201);
            return "Inserido com sucesso.";
        });

        put("/usuarios", (req, res) -> {
            res.type("application/json");

            UsuarioDTOInput usuarioDTOInput = objectMapper.readValue(req.body(), UsuarioDTOInput.class);
            usuarioService.alterarUsuario(usuarioDTOInput);

            res.status(200);
            return "Alterado com sucesso.";
        });

    }

    public static void main(String[] args) {
        UsuarioService usuarioService = new UsuarioService();
        UsuarioController usuarioController = new UsuarioController(usuarioService);
    }
}
