package service;

import dto.UsuarioDTOInput;
import dto.UsuarioDTOOutput;
import model.Usuario;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UsuarioService {
    private List<Usuario> listaUsuarios = new ArrayList<>();
    private ModelMapper modelMapper = new ModelMapper();

    public List<UsuarioDTOOutput> listarUsuarios() {
        List<UsuarioDTOOutput> usuariosDTO = listaUsuarios.stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDTOOutput.class))
                .collect(Collectors.toList());

        System.out.println("Listando usuários: " + usuariosDTO);

        return usuariosDTO;
    }

    public void inserirUsuario(UsuarioDTOInput usuarioDTOInput) {
        Usuario usuario = modelMapper.map(usuarioDTOInput, Usuario.class);
        listaUsuarios.add(usuario);
        System.out.println("Usuário adicionado: " + usuario);
    }

    public void alterarUsuario(UsuarioDTOInput usuarioDTOInput) {
        int id = usuarioDTOInput.getId();

        for (Usuario usuario : listaUsuarios) {
            if (usuario.getId() == id) {
                modelMapper.map(usuarioDTOInput, usuario);
                System.out.println("Usuário alterado: " + usuario);
                break;
            }
        }
    }


    public UsuarioDTOOutput buscarUsuario(int id) {
        Usuario usuario = listaUsuarios.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);

        if (usuario != null) {
            UsuarioDTOOutput usuarioDTO = modelMapper.map(usuario, UsuarioDTOOutput.class);
            System.out.println("Usuário encontrado: " + usuarioDTO);
            return usuarioDTO;
        } else {
            System.out.println("Usuário não encontrado para o ID: " + id);
            return null;
        }
    }

    public void excluirUsuario(int id) {
        listaUsuarios.removeIf(usuario -> usuario.getId() == id);
    }
}
