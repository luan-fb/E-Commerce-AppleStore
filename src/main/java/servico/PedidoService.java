package servico;
import java.util.List;

import dao.PedidoDao;
import entidades.Pedido;

public class PedidoService {
    private PedidoDao pedidoDao;

    public PedidoService() {
        this.pedidoDao = new PedidoDao();
    }

    public void adicionarPedido(Pedido pedido) {
        try {
            pedidoDao.adicionarPedido(pedido);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao adicionar pedido.");
        }
    }

    public void removerPedido(int id) {
        try {
            pedidoDao.removerPedido(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao remover pedido.");
        }
    }

    public List<Pedido> listarPedidos() {
        try {
            return pedidoDao.listarPedidos();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao listar pedidos.");
        }
    }

    public Pedido getPedidoPorId(int id) {
        try {
            return pedidoDao.getPedidoPorId(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar pedido por ID.");
        }
    }
}