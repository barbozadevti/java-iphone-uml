#!/usr/bin/env bash
# Compila e confere o comportamento do iPhone em cada um dos três papéis.
set -euo pipefail
cd "$(dirname "$0")/.."
rm -rf out && mkdir -p out
javac -encoding UTF-8 -d out src/*.java
JAVA="java -Dstdout.encoding=UTF-8 -cp out"

falhas=0
ok()    { echo "ok    $1"; }
falha() { echo "FALHA $1"; echo "  $2"; falhas=$((falhas + 1)); }

# A classe IPhone implementa as três interfaces.
cat > out/Conferir.java <<'JAVA'
import java.util.Arrays;
public class Conferir {
    public static void main(String[] a) {
        System.out.println(Arrays.stream(IPhone.class.getInterfaces()).map(Class::getSimpleName).sorted().toList());
    }
}
JAVA
javac -encoding UTF-8 -cp out -d out out/Conferir.java
[[ "$($JAVA Conferir | tr -d '\r')" == "[AparelhoTelefonico, NavegadorInternet, ReprodutorMusical]" ]] \
  && ok "IPhone implementa ReprodutorMusical, AparelhoTelefonico e NavegadorInternet" || falha "interfaces" "$($JAVA Conferir)"

saida=$($JAVA Main | tr -d '\r')
confere() { grep -qF "$2" <<< "$saida" && ok "$1" || falha "$1" "não encontrou: $2"; }
confere "seleciona a música"                    "Música selecionada: Ode à Alegria - Beethoven"
confere "toca a música"                         "Tocando: Ode à Alegria - Beethoven"
confere "abre o correio de voz"                 "Correio de voz: você não tem novas mensagens."
confere "pausa a música ao ligar"               "Pausado: Ode à Alegria - Beethoven"
confere "liga só com os dígitos do número"      "Ligando para 27998765432..."
confere "atende e encerra a chamada anterior"   "Chamada com 27998765432 encerrada."
confere "completa o endereço com https://"      "Exibindo página: https://www.dio.me (aba 1)"
confere "abre nova aba"                         "Nova aba aberta. Abas: 2"
confere "exibe a página na aba nova"            "Exibindo página: https://github.com/barbozadevti (aba 2)"
confere "atualiza a página da aba atual"        "Atualizando: https://github.com/barbozadevti"

# Casos de borda, usando o iPhone direto.
cat > out/Bordas.java <<'JAVA'
public class Bordas {
    public static void main(String[] a) {
        IPhone i = new IPhone();
        i.tocar();
        i.pausar();
        i.selecionarMusica("  ");
        i.ligar("12");
        i.exibirPagina("");
    }
}
JAVA
javac -encoding UTF-8 -cp out -d out out/Bordas.java
saida=$($JAVA Bordas | tr -d '\r')
confere "não toca sem música selecionada"       "Nenhuma música selecionada."
confere "não pausa se nada está tocando"        "Nada tocando no momento."
confere "recusa música em branco"               "Escolha uma música."
confere "recusa número curto demais"            "Número inválido: 12"
confere "recusa endereço vazio"                 "Digite um endereço."

echo
if [[ $falhas -eq 0 ]]; then echo "Todos os testes passaram."; else echo "$falhas teste(s) falharam."; exit 1; fi
