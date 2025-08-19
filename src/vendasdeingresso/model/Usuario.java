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
public class Usuario {
    private int idUsuario;
    private String nomeUsuario;
    private String email;
    private String tipoUsuario; 
    private LocalDate dataNascimento;
    
     public Usuario(int idUsuario, String nomeUsuario, String email, String tipoUsuario, LocalDate dataNascimento) {
        this.idUsuario = idUsuario;
        this.nomeUsuario = nomeUsuario;
        this.email = email;
        this.tipoUsuario = tipoUsuario;
        this.dataNascimento = dataNascimento;
    }
     
    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public String getEmail() {
        return email;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
    
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    @Override
    public String toString() {
        return "Usuário: " + nomeUsuario + " (ID: " + idUsuario + ") - Tipo: " + tipoUsuario + " - Email: " + email;
    }

}
