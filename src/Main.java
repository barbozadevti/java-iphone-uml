/**
 * Demonstração inspirada no lançamento de 2007: ouvir música, receber uma ligação
 * e navegar, tudo no mesmo aparelho. Cada bloco usa o iPhone por um papel diferente.
 */
public class Main {

    public static void main(String[] args) {
        IPhone iphone = new IPhone();

        System.out.println("== Reprodutor musical ==");
        usarComoReprodutor(iphone);

        System.out.println();
        System.out.println("== Aparelho telefônico ==");
        usarComoTelefone(iphone);

        System.out.println();
        System.out.println("== Navegador na internet ==");
        usarComoNavegador(iphone);
    }

    // Cada método só conhece a interface: é o polimorfismo que o diagrama mostra.
    static void usarComoReprodutor(ReprodutorMusical reprodutor) {
        reprodutor.selecionarMusica("Ode à Alegria - Beethoven");
        reprodutor.tocar();
    }

    static void usarComoTelefone(AparelhoTelefonico telefone) {
        telefone.iniciarCorreioVoz();
        telefone.ligar("(27) 99876-5432");
        telefone.atender();
    }

    static void usarComoNavegador(NavegadorInternet navegador) {
        navegador.exibirPagina("www.dio.me");
        navegador.adicionarNovaAba();
        navegador.exibirPagina("github.com/barbozadevti");
        navegador.atualizarPagina();
    }
}
