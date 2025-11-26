package br.edu.ifpb.poo;
import lombok.*;

/** 
Pré-condição: Não há
1) Usuário solicita cadastro de rota
2) Sistema pede o endereço de destino, máscara de subrede, roteador (gateway) de destino e interface física
3) Usuário informa dados
4) Sistema verifica se já há uma rota idêntica, se houver informa erro, senão, registra a rota
**/

@Data
@AllArgsConstructor
public class Rota
{
    private String destino;
    private String  subMasc;
    private String gateway;
    private InterfaceFisica interfaceFisica;

    public String getInterfaceFisica()
    {
        return interfaceFisica.toString();
    }
    public String getInterfaceFisicaNome()
    {
        return interfaceFisica.getNome();
    }

    public String getInterfaceFisicaEndereco()
    {
        return interfaceFisica.getEndereco();
    }
    
    public String toString()
    {
        return "Destino: " + destino+ "  " + "Gateway: "+ gateway+"  "+"Máscara: " + subMasc + "  "+  interfaceFisica.getNome();
    }

}
