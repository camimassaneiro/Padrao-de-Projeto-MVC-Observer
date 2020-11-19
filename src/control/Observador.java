package control;

import model.Produto;

/**
 *
 * @author adilsonv
 */
public interface Observador {

    void notificarIniciouNovaVenda();
    void notificarLimparCampos();
    void notificarProdutoAdicionado(String dadosProduto);
    void notificarProdutoEncontrado(Produto p);
    void notificarTotalVenda(double totalVenda);
}
