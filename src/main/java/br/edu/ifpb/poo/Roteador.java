package br.edu.ifpb.poo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Roteador
{
    private List<Rota> rotas = new ArrayList<>();   

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
        if(rotas.isEmpty())
        {
            return "Tabela de rotas vazia";
        }
        int cont = 1;
        StringBuilder tabela = new StringBuilder();
        for (Rota r : rotas)
        {
            tabela.append("Rota " + cont+ ": " + r.toString() + "\n");
            cont++;
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

    

}
