package entidades;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "produtos")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "modelo")
    private String modelo;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "preco")
    private Double preco;

    @Column(name = "amarzenamento")
    private Integer amarzenamento;

    @Column(name = "estoque")
    private Integer estoque;

    @Column(name = "desconto")
    private Double descontoseminovo;

    @Column(name = "cor")
    private String cor;

    @Column(name = "caminho_imagem") // Adicionando o campo caminho_imagem
    private String caminhoImagem;

    @OneToMany(mappedBy = "produto")
    private List<ItemCarrinho> itensCarrinho;

    public Produto() {

    }

    public Produto(String modelo, String descricao, double preco, int amarzenamento, int estoque, String cor, String caminhoImagem) {
        this.modelo = modelo;
        this.descricao = descricao;
        this.preco = preco;
        this.amarzenamento = amarzenamento;
        this.estoque = estoque;
        this.descontoseminovo = this.getPreco() * 0.65;
        this.cor = cor;
        this.caminhoImagem = caminhoImagem;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public Integer getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getAmarzenamento() {
        return amarzenamento;
    }

    public void setAmarzenamento(int amarzenamento) {
        this.amarzenamento = amarzenamento;
    }

    public Double getDescontoseminovo() {
        return descontoseminovo;
    }

    public void setDescontoseminovo(double descontoseminovo) {
        this.descontoseminovo = descontoseminovo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getCaminhoImagem() {
        return caminhoImagem;
    }

    public void setCaminhoImagem(String caminhoImagem) {
        this.caminhoImagem = caminhoImagem;
    }

	@Override
	public String toString() {
		return "ID: " + id + " " + modelo + " | Amarzenamento: " + amarzenamento +  " | Preco: " + preco + "R$ | Estoque: " + estoque + "  | Cor: " + cor + "";
	}
    
    
}
