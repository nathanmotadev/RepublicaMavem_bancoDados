package dominioRepublica;

import java.time.LocalDate;
import java.time.Period;

public class Morador {

    public String nome;
    public int id;
    public LocalDate dataNascimento;
    public char sexo;
    public LocalDate dataEntrada;

    public Morador(String nome, int id, LocalDate dataNascimento, char sexo, LocalDate dataEntrada) {
        this.nome = nome;
        this.id = id;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
        this.dataEntrada = dataEntrada;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public int getIdade() {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDate dataEntrada) {
        this.dataEntrada = dataEntrada;
    }
}
