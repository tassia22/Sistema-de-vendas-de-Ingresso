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

public class Evento {
   private int idEvento; 
    private String nomeEvento;
    private String local;
    private String descricao;
    private String tipo; 
    private LocalDate dataEvento; 
    private int ingressosDisponiveis; 

    public Evento(int idEvento, String nomeEvento, String local, String descricao, String tipo, LocalDate dataEvento, int ingressosDisponiveis) {
        this.idEvento = idEvento; 
        this.nomeEvento = nomeEvento;
        this.local = local;
        this.descricao = descricao;
        this.tipo = tipo;
        this.dataEvento = dataEvento;
        this.ingressosDisponiveis = ingressosDisponiveis;
    }

 
    public Evento(String nomeEvento, String local, String descricao, String tipo, LocalDate dataEvento, int ingressosDisponiveis) {
        this(-1, nomeEvento, local, descricao, tipo, dataEvento, ingressosDisponiveis); 
    }

    public int getIdEvento() {
        return idEvento;
    }

    public String getNomeEvento() {
        return nomeEvento;
    }

    public String getLocal() {
        return local;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public LocalDate getDataEvento() {
        return dataEvento;
    }

    public int getIngressosDisponiveis() {
        return ingressosDisponiveis;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public void setNomeEvento(String nomeEvento) {
        this.nomeEvento = nomeEvento;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setDataEvento(LocalDate dataEvento) {
        this.dataEvento = dataEvento;
    }

    public void setIngressosDisponiveis(int ingressosDisponiveis) {
        this.ingressosDisponiveis = ingressosDisponiveis;
    }

    /**
     * @param quantidade 
     */
    public void diminuirIngressos(int quantidade) {
        if (this.ingressosDisponiveis >= quantidade) {
            this.ingressosDisponiveis -= quantidade;
        } else {
            System.out.println("Erro: Não há ingressos suficientes para o evento " + nomeEvento);
        }
    }

    @Override
    public String toString() {
        return "Evento ID: " + idEvento + " - " + nomeEvento + " (" + tipo + ") - Local: " + local + " - Data: " + dataEvento + " - Ingressos Disponíveis: " + ingressosDisponiveis;
    }
}
