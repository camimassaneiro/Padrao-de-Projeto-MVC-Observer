package control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Produto;
import model.Venda;

public class MercadoImpl implements MercadoControle {

    private List<Observador> obss = new ArrayList<>();

    @Override
    public void addObservador(Observador obs) {
        obss.add(obs);
    }

    @Override
    public void removerObservador(Observador obs) {
        obss.remove(obs);
    }

    private Venda venda;

    private Map<Integer, Produto> bd = new HashMap<>();

    public MercadoImpl() {
        bd.put(1, new Produto(1, "Brahma", 2));
        bd.put(2, new Produto(2, "Skol", 1.8));
        bd.put(3, new Produto(3, "Eisenbahn", 5));
        bd.put(4, new Produto(4, "Heineken", 4.5));
    }

    public void iniciarNovaVenda() {
        this.venda = new Venda();

        for (Observador obs : obss) {
            obs.notificarIniciouNovaVenda();
        }
    }

    @Override
    public void buscar(int codigo) throws Exception {
        Produto p = buscarInterno(codigo);

        for (Observador obs : obss) {
            obs.notificarProdutoEncontrado(p);
        }

    }

    private Produto buscarInterno(int codigo) throws Exception {
        Produto p = bd.get(codigo);
        if (p == null) {
            throw new Exception("Produto Inexistente");
        }
        return p;
    }

    @Override
    public void adicionar(int codigo) throws Exception {

        Produto p = buscarInterno(codigo);
        this.venda.addProduto(p);

        double totalVenda = this.venda.getTotal();

        for (Observador obs : obss) {
            obs.notificarProdutoAdicionado(p.getNome()
                    + " | "
                    + p.getPrecoUnit());
            obs.notificarTotalVenda(totalVenda);
        }
    }

    private List<Venda> vendas = new ArrayList<>();

    @Override
    public void finalizarVenda() {
        this.vendas.add(this.venda);

        for (Observador obs : obss) {
            obs.notificarLimparCampos();
        }
    }

    @Override
    public double getTotalGeralVendas() {
        double total = 0;
        for (Venda v : vendas) {
            total += v.getTotal();
        }
        return total;
    }

}
