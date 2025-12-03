package br.edu.ifpb.poo;

public class Main {
    public static void main(String[] args) {

        try {

            // ============================================
            // UC01 – Cadastrar Interface Física
            // (as interfaces são usadas no construtor das rotas)
            // ============================================
            InterfaceFisica if1 = new InterfaceFisica("eth0", "192.168.1.1");
            InterfaceFisica if2 = new InterfaceFisica("eth1", "10.0.0.1");
            InterfaceFisica if3 = new InterfaceFisica("wlan0", "172.16.0.1");

            // Criação do roteador (vazio)
            Roteador roteador = new Roteador();


            // ============================================
            // UC02 – Cadastrar Rota
            // ============================================
            Rota r1 = new Rota("192.168.1.0", "255.255.255.0", "192.168.1.1", if1);
            Rota r2 = new Rota("10.0.0.0", "255.0.0.0", "10.0.0.1", if2);
            Rota r3 = new Rota("172.16.0.0", "255.240.0.0", "172.16.0.1", if3);
            Rota r4 = new Rota("192.168.1.128", "255.255.255.128", "192.168.1.1", if1); // mais específica

            roteador.addRota(r1);
            roteador.addRota(r2);
            roteador.addRota(r3);
            roteador.addRota(r4);

            System.out.println("\n--- UC03 – Exibir tabela de rotas (depois de adicionar) ---");
            System.out.println(roteador);


            // ============================================
            // UC02 – Testar rota duplicada
            // ============================================
            try {
                System.out.println("\n--- Testando rota duplicada (UC02) ---");
                Rota duplicada = new Rota("192.168.1.0", "255.255.255.0", "192.168.1.1", if1);
                roteador.addRota(duplicada);
            } catch (Exception e) {
                System.out.println("OK - rota duplicada bloqueada: " + e.getMessage());
            }


            // ============================================
            // UC07 – Longest Match (teste automático)
            // ============================================
            System.out.println("\n--- UC07 – Roteamento via Longest Match ---");

            String ipTeste = "192.168.1.150";
            System.out.println("IP testado: " + ipTeste);

            Rota melhor = roteador.longestMatch(ipTeste);

            if (melhor != null) {
                System.out.println("Melhor rota encontrada:");
                System.out.println(melhor.toString(roteador.getExibirCIDR()));
            } else {
                System.out.println("Nenhuma rota encontrada para este IP.");
            }


            // ============================================
            // UC04 – Alterar rota
            // ATENÇÃO: este método é interativo (Scanner)
            // ============================================
            System.out.println("\n--- UC04 – Alterar rota (interativo) ---");
            roteador.alteraRota();

            System.out.println("\nTabela após alteração:");
            System.out.println(roteador);


            // ============================================
            // UC05 – Remover rota
            // ============================================
            System.out.println("\n--- UC05 – Remover rota (removendo r2) ---");
            roteador.removeRota(r2);

            System.out.println("\nTabela após remoção:");
            System.out.println(roteador);


            // ============================================
            // UC06 – Configurar exibição (CIDR / Máscara)
            // ATENÇÃO: este método é interativo (Scanner)
            // ============================================
            System.out.println("\n--- UC06 – Configurar modo de exibição ---");
            roteador.configuraExibicao();

            System.out.println("\nTabela após mudança de exibição:");
            System.out.println(roteador);


            // ============================================
            // UC07 – Roteamento interativo (rotearDatagrama)
            // ATENÇÃO: este método é interativo (Scanner)
            // ============================================
            System.out.println("\n--- UC07 – Roteamento interativo (rotearDatagrama) ---");
            roteador.rotearDatagrama();


            // ============================================
            // UC08 – Resetar tabela de rotas
            // ATENÇÃO: este método pede confirmação
            // ============================================
            System.out.println("\n--- UC08 – Resetar tabela de rotas ---");
            roteador.resetarTabela();

            System.out.println("\nTabela após UC08:");
            System.out.println(roteador);


        } catch (Exception e) {
            System.out.println("\nErro durante execuçao dos testes:");
            e.printStackTrace();
        }
    }
}
