package br.edu.ifpb.poo;
import lombok.*;

/** 
Pré-condição: Não existir interface física com o mesmo nome

1) Usuário solicita cadastro de interface física
2) Sistema pede o nome da interface e endereço IP
3) Sistema verifica se não existe interface com este nome
4) Se não existir, sistema cadastra interface
**/
@Data
@AllArgsConstructor
public class InterfaceFisica
{
    private String nome;
    private String endereco;


    public String toString()
    {
        return "Nome: " + nome + "  --  " + "Endereço: " + endereco + "\n";
    }
}
