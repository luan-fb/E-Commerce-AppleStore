package controlador;

import entidades.Usuario;
import exception.DadoInvalidoException;
import exception.DadoNuloException;
import servico.UsuarioService;

public class UsuarioController {
    private UsuarioService usuarioService;
    private Usuario usuarioLogado;

    public UsuarioController() {
        usuarioService = new UsuarioService();
    }

    public void loginUsuario(String email, String senha) throws DadoNuloException, DadoInvalidoException {
        usuarioService.loginUsuario(email, senha);
        usuarioLogado = usuarioService.getUsuarioByEmail(email);
    }

    public void adicionarUsuario(Usuario usuario) throws DadoNuloException, DadoInvalidoException {
        usuarioService.adicionarUsuario(usuario);
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public boolean isAdmin() {
        return usuarioLogado != null && "admin".equals(usuarioLogado.getTipo());
    }
}
