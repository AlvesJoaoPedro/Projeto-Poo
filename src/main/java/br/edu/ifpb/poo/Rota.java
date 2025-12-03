package br.edu.ifpb.poo;
import lombok.*;

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
    

    public String getMascaraFormatada(boolean exibirCIDR)
    {
        if (!exibirCIDR)
        {
            return subMasc; // exibe a máscara original
        }
    
        int cidr = IpUtils.mascaraParaCIDR(subMasc);
        return "/" + cidr;
    }

    public String toString(boolean exibirCIDR)
    {
        return "Destino: " + destino +
               "  " + getMascaraFormatada(exibirCIDR) +
               "  --> Gateway: " + gateway +
               "  IF: " + interfaceFisica.getNome() + "\n";
    }
}
