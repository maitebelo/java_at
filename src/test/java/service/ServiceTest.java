package service;

import dto.UsuarioDTOInput;
import org.junit.jupiter.api.Test;
import service.UsuarioService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServiceTest {

    @Test
    public void testInserirUsuario() {
        UsuarioService usuarioService = new UsuarioService();

        UsuarioDTOInput usuarioDTOInput = new UsuarioDTOInput();
        usuarioDTOInput.setId(1);
        usuarioDTOInput.setNome("Teste1");
        usuarioDTOInput.setSenha("teste1");

        usuarioService.inserirUsuario(usuarioDTOInput);

        assertEquals(1, usuarioService.listarUsuarios().size());
    }
}
