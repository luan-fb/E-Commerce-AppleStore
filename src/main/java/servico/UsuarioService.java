package servico;

import java.util.List;
import java.util.regex.Pattern;

import dao.UsuarioDao;
import entidades.Produto;
import entidades.Usuario;
import exception.DadoInvalidoException;
import exception.DadoNuloException;

public class UsuarioService {
    UsuarioDao usuarioDao = new UsuarioDao();

    // Outros métodos...

    public void loginUsuario(String email, String senha) throws DadoNuloException, DadoInvalidoException {
        if (email == null || email.trim().isEmpty()) {
            throw new DadoNuloException("O email não pode ser vazio");
        }
        if (senha == null || senha.trim().isEmpty()) {
            throw new DadoNuloException("A senha não pode ser vazia");
        }

        Usuario usuarioExistente = usuarioDao.getUsuarioByEmail(email);
        if (usuarioExistente == null) {
            throw new DadoInvalidoException("Email não cadastrado, por favor faça o cadastro");
        }

        if (!usuarioExistente.getSenha().equals(senha)) {
            throw new DadoInvalidoException("Senha incorreta");
        }
    }

    public void adicionarUsuario(Usuario usuario) throws DadoNuloException, DadoInvalidoException {
        if (usuario == null) {
            throw new DadoNuloException("O usuário não pode ser nulo");
        }
        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            throw new DadoNuloException("O nome não pode ser vazio");
        }
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            throw new DadoNuloException("O email não pode ser vazio");
        }
        if (!isValidEmail(usuario.getEmail())) {
            throw new DadoInvalidoException("Formato de email inválido");
        }
        if (usuario.getSenha() == null || usuario.getSenha().length() < 6) {
            throw new DadoInvalidoException("A senha deve ter pelo menos 6 caracteres");
        }

        Usuario usuarioExistente = usuarioDao.getUsuarioByEmail(usuario.getEmail());
        if (usuarioExistente != null) {
            throw new DadoInvalidoException("Este email já está cadastrado");
        }

        usuarioDao.adicionarUsuario(usuario);
    }

    public void removerUsuario(int id) throws DadoNuloException, DadoInvalidoException {
        Usuario usuarioExistente = usuarioDao.getUsuarioById(id);
        if (usuarioExistente == null) {
            throw new DadoNuloException("Usuário não encontrado");
        }
        usuarioDao.removerUsuario(id);
    }

    private boolean isValidEmail(String email) {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(email).matches();
    }

    public boolean isAdmin(String email) {
        Usuario usuario = usuarioDao.getUsuarioByEmail(email);
        return usuario != null && "admin".equals(usuario.getTipo());
    }

    public Usuario getUsuarioByEmail(String email) {
        return usuarioDao.getUsuarioByEmail(email);
    }

    public Usuario getUsuarioLogado(String email) {
        return usuarioDao.getUsuarioByEmail(email);
    }
    public List<Usuario> listarUsuarios() {
        return usuarioDao.listarUsuarios();
    }
}
