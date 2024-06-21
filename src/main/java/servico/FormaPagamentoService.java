package servico;

import java.math.BigDecimal;
import java.util.List;

import dao.FormaPagamentoDao;
import entidades.FormaPagamento;

public class FormaPagamentoService {
	private FormaPagamentoDao formaPagamentoDao;

	public FormaPagamentoService() {
		this.formaPagamentoDao = new FormaPagamentoDao();
	}

	public void salvarFormaPagamento(FormaPagamento formaPagamento) {
		formaPagamentoDao.adicionarFormaPagamento(formaPagamento);
	}

	public void removerFormaPagamento(int id) {
		formaPagamentoDao.removerFormaPagamento(id);
	}

	public List<FormaPagamento> listarFormasPagamento() {
		return formaPagamentoDao.listarFormasPagamento();
	}

	public FormaPagamento buscarFormaPagamentoPorId(int id) {
		return formaPagamentoDao.getFormaPagamentoPorId(id);
	}

	public BigDecimal calcularTotalComTaxa(FormaPagamento formaPagamento, BigDecimal total) {
		BigDecimal taxa = BigDecimal.valueOf(formaPagamento.getTaxa());
        BigDecimal taxaPercentual = taxa.divide(BigDecimal.valueOf(100));
        BigDecimal totalComTaxa = total.add(total.multiply(taxaPercentual));

        return totalComTaxa;
	}
}