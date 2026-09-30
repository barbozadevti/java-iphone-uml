import java.util.ArrayList;
import java.util.List;

/**
 * "Um iPod, um telefone e um comunicador de internet. Não são três aparelhos: é um só."
 * O iPhone assume os três papéis implementando as três interfaces.
 */
public class IPhone implements ReprodutorMusical, AparelhoTelefonico, NavegadorInternet {

    // --- Reprodutor musical ---
    private String musicaAtual;
    private boolean tocando;

    // --- Aparelho telefônico ---
    private String chamadaEmCurso;

    // --- Navegador na internet ---
    private final List<String> abas = new ArrayList<>(List.of("about:blank"));
    private int abaAtual;

    @Override
    public void selecionarMusica(String musica) {
        if (musica == null || musica.isBlank()) {
            System.out.println("Escolha uma música.");
            return;
        }
        musicaAtual = musica.trim();
        tocando = false;
        System.out.println("Música selecionada: " + musicaAtual);
    }

    @Override
    public void tocar() {
        if (musicaAtual == null) {
            System.out.println("Nenhuma música selecionada.");
            return;
        }
        tocando = true;
        System.out.println("Tocando: " + musicaAtual);
    }

    @Override
    public void pausar() {
        if (!tocando) {
            System.out.println("Nada tocando no momento.");
            return;
        }
        tocando = false;
        System.out.println("Pausado: " + musicaAtual);
    }

    @Override
    public void ligar(String numero) {
        String digitos = numero == null ? "" : numero.replaceAll("[^0-9]", "");
        if (digitos.length() < 3 || digitos.length() > 13) {
            System.out.println("Número inválido: " + numero);
            return;
        }
        if (tocando) {
            pausar();
        }
        chamadaEmCurso = digitos;
        System.out.println("Ligando para " + digitos + "...");
    }

    @Override
    public void atender() {
        if (tocando) {
            pausar();
        }
        if (chamadaEmCurso != null) {
            System.out.println("Chamada com " + chamadaEmCurso + " encerrada.");
        }
        chamadaEmCurso = "chamada recebida";
        System.out.println("Chamada recebida atendida.");
    }

    @Override
    public void iniciarCorreioVoz() {
        System.out.println("Correio de voz: você não tem novas mensagens.");
    }

    @Override
    public void exibirPagina(String url) {
        if (url == null || url.isBlank()) {
            System.out.println("Digite um endereço.");
            return;
        }
        String endereco = url.trim().contains("://") ? url.trim() : "https://" + url.trim();
        abas.set(abaAtual, endereco);
        System.out.println("Exibindo página: " + endereco + " (aba " + (abaAtual + 1) + ")");
    }

    @Override
    public void adicionarNovaAba() {
        abas.add("about:blank");
        abaAtual = abas.size() - 1;
        System.out.println("Nova aba aberta. Abas: " + abas.size());
    }

    @Override
    public void atualizarPagina() {
        System.out.println("Atualizando: " + abas.get(abaAtual));
    }

    public boolean isTocando() {
        return tocando;
    }

    public String getChamadaEmCurso() {
        return chamadaEmCurso;
    }

    public int getQuantidadeDeAbas() {
        return abas.size();
    }
}
