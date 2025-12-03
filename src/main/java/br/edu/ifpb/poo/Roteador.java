package br.edu.ifpb.poo;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Roteador
{
    private boolean exibirCIDR = false;

    private List<Rota> rotas = new ArrayList<>();   

    public void configuraExibicao()
    {
        Scanner sc = new Scanner(System.in);
    
        System.out.println("\n=== Configurar exibição das rotas ===");
        System.out.println("1 - Exibir usando Máscara de Subrede (255.255.255.0)");
        System.out.println("2 - Exibir usando notação CIDR (/24)");
        System.out.print("Escolha: ");
    
        String escolha = sc.nextLine();
    
        if (escolha.equals("1"))
        {
            exibirCIDR = false;
            System.out.println("Exibição configurada para: Máscara de Subrede.\n");
        }
        else if (escolha.equals("2"))
        {
            exibirCIDR = true;
            System.out.println("Exibição configurada para: Notação CIDR.\n");
        }
        else
        {
            System.out.println("Opção inválida. Mantendo configuração atual.\n");
        }
    }

    public boolean getExibirCIDR()
    {
        return exibirCIDR;
    }

    private boolean verificaSeRotaIndentica(Rota rota)
    {
        return rotas.stream().anyMatch(r -> 
            r.getDestino().equalsIgnoreCase(rota.getDestino()) &&
            r.getSubMasc().equalsIgnoreCase(rota.getSubMasc()) &&
            r.getGateway().equalsIgnoreCase(rota.getGateway()) &&
            r.getInterfaceFisica().equalsIgnoreCase(rota.getInterfaceFisica())
        );
    }

    public void addRota(Rota rota) throws Exception
    {
        if(this.verificaSeRotaIndentica(rota))
        {
            throw new Exception("A interface não foi adicionada, pois já existe uma idêntica no sistema.");
        }
        rotas.add(rota);
    }

    public String toString()
    {
        if (rotas.isEmpty())
        {
            return "Tabela de rotas vazia";
        }
    
        StringBuilder tabela = new StringBuilder();
    
        for (Rota r : rotas)
        {
            tabela.append(r.toString(exibirCIDR));
        }
    
        return tabela.toString();
    }
    
    private int auxEscolhaIndex()
    {
        Scanner sc = new Scanner(System.in);

        System.out.println(toString());
    
        System.out.print("\nEscolha a rota: ");
    
        int escolha = sc.nextInt();
        sc.nextLine();
    
        while (escolha < 1 || escolha > rotas.size())
        {
            System.out.print("Valor inválido. Tente novamente: ");
            escolha = sc.nextInt();
            sc.nextLine();
        }
        return escolha - 1;
    }
    
    private Rota auxAlteraRota(Rota rotaAntiga)
{
    Scanner sc = new Scanner(System.in);

    // mostrar rota atual
    System.out.println("\n=== Dados atuais da rota ===");
    System.out.println(rotaAntiga);

    System.out.println("\nPressione ENTER para manter o valor atual.");

    // DESTINO
    System.out.print("Novo destino (" + rotaAntiga.getDestino() + "): ");
    String novoDestino = sc.nextLine();
    if (novoDestino.isBlank()) {
        novoDestino = rotaAntiga.getDestino();
    }

    // MÁSCARA
    System.out.print("Nova máscara (" + rotaAntiga.getSubMasc() + "): ");
    String novaMascara = sc.nextLine();
    if (novaMascara.isBlank()) {
        novaMascara = rotaAntiga.getSubMasc();
    }

    // GATEWAY
    System.out.print("Novo gateway (" + rotaAntiga.getGateway() + "): ");
    String novoGateway = sc.nextLine();
    if (novoGateway.isBlank()) {
        novoGateway = rotaAntiga.getGateway();
    }

    // INTERFACE — NOME
    System.out.print("Novo nome da interface (" + rotaAntiga.getInterfaceFisicaNome() + "): ");
    String novoNomeInter = sc.nextLine();
    if (novoNomeInter.isBlank()) {
        novoNomeInter = rotaAntiga.getInterfaceFisicaNome();
    }

    // INTERFACE — ENDEREÇO
    System.out.print("Novo endereço IP da interface (" + rotaAntiga.getInterfaceFisicaEndereco() + "): ");
    String novoEndInter = sc.nextLine();
    if (novoEndInter.isBlank()) {
        novoEndInter = rotaAntiga.getInterfaceFisicaEndereco();
    }

    InterfaceFisica novaInterface = new InterfaceFisica(novoNomeInter, novoEndInter);
    return new Rota(novoDestino, novaMascara, novoGateway, novaInterface);
}


    public void alteraRota() throws Exception
    {
        if (rotas.isEmpty())
        {
            throw new Exception("Não há nenhuma rota cadastrada.");
        }

        int index = auxEscolhaIndex();
    
        Rota rotaAntiga = rotas.get(index);
    
        Rota rotaNova = auxAlteraRota(rotaAntiga);
    
        rotas.set(index, rotaNova);
    
        System.out.println("Rota atualizada com sucesso!\n" + rotaNova);
    }

    public Rota removeRota(Rota rota) throws Exception
    {
        if(rotas.isEmpty())
        {
            throw new Exception("A tabela de rotas está vazia.");
        }
        Scanner sc = new Scanner(System.in);

        int index = auxEscolhaIndex();
        Rota rotaRemovida = rotas.get(index);

        System.out.println("Deseja remover a rota permanentemente? (Y/N): ");
        String escolha = sc.nextLine();
        while (!(escolha.equalsIgnoreCase("n") || escolha.equalsIgnoreCase("y")))
        {
            System.out.println("Resposta inválida. Tente novamente: ");
            escolha = sc.nextLine();
        }

        if (escolha.equalsIgnoreCase("n"))
        {
            return null;
        }

        rotas.remove(rotaRemovida);
        System.out.println("\n\n"+rotaRemovida);


        System.out.println("Rota removida com sucesso!");
        return rotaRemovida;
    }

    public Rota longestMatch(String ipDestino) throws Exception
    {
        if (rotas.isEmpty())
        {
            throw new Exception("A tabela de rotas está vazia.");
        }
    
        int ipInt = IpUtils.ipStringParaInt(ipDestino);
        Rota melhorRota = null;
        int maiorPrefixo = -1;
    
        for (Rota r : rotas)
        {
            int prefixoInt = IpUtils.ipStringParaInt(r.getDestino());
            int maskInt = IpUtils.ipStringParaInt(r.getSubMasc());
    
            boolean combina = (ipInt & maskInt) == prefixoInt;
    
            if (combina)
            {
                // usa o utilitário para obter o comprimento do prefixo (CIDR)
                int prefixoAtual = IpUtils.mascaraParaCIDR(r.getSubMasc());

                if (prefixoAtual > maiorPrefixo)
                {
                    maiorPrefixo = prefixoAtual;
                    melhorRota = r;
                }
            }
        }
        return melhorRota;
    }

    public void rotearDatagrama() throws Exception
    {
        if (rotas.isEmpty())
        {
            throw new Exception("Não há rotas cadastradas na tabela.");
        }

        Scanner sc = new Scanner(System.in);

        System.out.print("Informe o IPv4 de destino que deseja rotear: ");
        String ipDestino = sc.nextLine();

        try
        {
            IpUtils.ipStringParaInt(ipDestino);
        }
        catch (Exception e)
        {
            throw new Exception("O IPv4 informado é inválido.");
        }

        Rota melhorRota = longestMatch(ipDestino);

        if (melhorRota == null)
        {
            System.out.println("Nenhuma rota compatível foi encontrada para o IP informado.");
        }
        else
        {
            System.out.println("\nMelhor rota encontrada:");
            System.out.println(melhorRota.toString(exibirCIDR));
        }
    }

    public void resetarTabela() throws Exception
    {
        Scanner sc = new Scanner(System.in);

        System.out.print("Tem certeza que deseja resetar toda a tabela de rotas? (Y/N): ");
        String resp = sc.nextLine().trim().toLowerCase();

        while (!(resp.equals("y") || resp.equals("n")))
        {
            System.out.print("Valor inválido. Digite apenas Y ou N: ");
            resp = sc.nextLine().trim().toLowerCase();
        }

        if (resp.equals("y"))
        {
            rotas.clear();
            System.out.println("Tabela de rotas resetada com sucesso.");
        }
        else
        {
            System.out.println("Operação cancelada.");
        }
    }
}
