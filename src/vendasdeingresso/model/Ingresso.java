/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vendasdeingresso.model;

/**
 *
 * @author tassi
 */
public class Ingresso {
    private String tipoIngresso; // Ex: VIP, Pista, Camarote
    private double preco;
    private String codigoUnico; 
    private Evento eventoAssociado;
    
    public Ingresso(String tipoIngresso, double preco, String codigoUnico, Evento eventoAssociado) {
        this.tipoIngresso = tipoIngresso;
        this.preco = preco;
        this.codigoUnico = codigoUnico;
        this.eventoAssociado = eventoAssociado;
    }
    
     public String getTipoIngresso() {
        return tipoIngresso;
    }

    public double getPreco() {
        return preco;
    }

    public String getCodigoUnico() {
        return codigoUnico;
    }

    public Evento getEventoAssociado() {
        return eventoAssociado;
    }
    
    public void setTipoIngresso(String tipoIngresso) {
        this.tipoIngresso = tipoIngresso;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setCodigoUnico(String codigoUnico) {
        this.codigoUnico = codigoUnico;
    }

    public void setEventoAssociado(Evento eventoAssociado) {
        this.eventoAssociado = eventoAssociado;
    }
    
    @Override
    public String toString() {
        return "Ingresso (" + tipoIngresso + ") para " + eventoAssociado.getNomeEvento() + " - R$" + String.format("%.2f", preco) + " - Código: " + codigoUnico;
    }
    
    
}
