/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vendasdeingresso.model;
import java.time.LocalDate;

/**
 *
 * @author tassi
 */
public class Venda {
    private int numeroVenda;
    private LocalDate dataVenda;
    private Usuario comprador; 
    private Ingresso ingressoVendido; 
    private int quantidade;
    private double valorTotal;
    
    public Venda(int numeroVenda, LocalDate dataVenda, Usuario comprador, Ingresso ingressoVendido, int quantidade) {
        this.numeroVenda = numeroVenda;
        this.dataVenda = dataVenda;
        this.comprador = comprador;
        this.ingressoVendido = ingressoVendido;
        this.quantidade = quantidade;
        this.valorTotal = ingressoVendido.getPreco() * quantidade;
        
        if (ingressoVendido.getEventoAssociado() != null) {
            ingressoVendido.getEventoAssociado().diminuirIngressos(quantidade);
        }
    }
    
     public int getNumeroVenda() {
        return numeroVenda;
    }

    public LocalDate getDataVenda() {
        return dataVenda;
    }

    public Usuario getComprador() {
        return comprador;
    }

    public Ingresso getIngressoVendido() {
        return ingressoVendido;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getValorTotal() {
        return valorTotal;
    }
    
    public void setNumeroVenda(int numeroVenda) {
        this.numeroVenda = numeroVenda;
    }

    public void setDataVenda(LocalDate dataVenda) {
        this.dataVenda = dataVenda;
    }

    public void setComprador(Usuario comprador) {
        this.comprador = comprador;
    }

    public void setIngressoVendido(Ingresso ingressoVendido) {
        this.ingressoVendido = ingressoVendido;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
        // Recalcula o valor total se a quantidade for alterada
        this.valorTotal = this.ingressoVendido.getPreco() * quantidade;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    @Override
    public String toString() {
        return "Venda #" + numeroVenda + " - Data: " + dataVenda + "\n" +
               "  Comprador: " + comprador.getNomeUsuario() + "\n" +
               "  Produto: " + ingressoVendido.getEventoAssociado().getNomeEvento() + " - Tipo Ingresso: " + ingressoVendido.getTipoIngresso() + "\n" +
               "  Quantidade: " + quantidade + " - Valor Total: R$" + String.format("%.2f", valorTotal);
    }
}
