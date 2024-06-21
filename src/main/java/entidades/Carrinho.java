package entidades;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "carrinho")
public class Carrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    private Usuario usuario;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCarrinho> itens = new ArrayList<>();

    @Column(name = "total", precision = 10, scale = 2)
    private BigDecimal total;

    @Column(name = "desconto", precision = 10, scale = 2)
    private BigDecimal desconto;

    public Carrinho() {
        this.total = BigDecimal.ZERO;
        this.desconto = BigDecimal.ZERO;
    }

    public Carrinho(Usuario usuario) {
        this.usuario = usuario;
        this.total = BigDecimal.ZERO;
        this.desconto = BigDecimal.ZERO;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<ItemCarrinho> getItens() {
        return itens;
    }

    public void setItens(List<ItemCarrinho> itens) {
        this.itens = itens;
        calcularTotal();
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public void adicionarItem(ItemCarrinho item) {
        this.itens.add(item);
        calcularTotal();
    }

    public void removerItem(ItemCarrinho item) {
        this.itens.remove(item);
        calcularTotal();
    }

    public void calcularTotal() {
        BigDecimal totalCalculado = BigDecimal.ZERO;
        for (ItemCarrinho item : itens) {
            BigDecimal precoUnitario = BigDecimal.valueOf(item.getPrecoUnitario());
            BigDecimal subtotalItem = precoUnitario.multiply(BigDecimal.valueOf(item.getQuantidade()));
            totalCalculado = totalCalculado.add(subtotalItem);
        }

        if (desconto != null) {
            totalCalculado = totalCalculado.subtract(desconto);
        }

        this.total = totalCalculado;
    }

    public void aplicarDesconto(BigDecimal valorDesconto) {
        if (valorDesconto != null && valorDesconto.compareTo(BigDecimal.ZERO) > 0) {
            this.desconto = valorDesconto;
        } else {
            this.desconto = BigDecimal.ZERO;
        }
        calcularTotal(); // Recalcula o total após aplicar o desconto
    }
}