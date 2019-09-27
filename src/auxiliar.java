import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class auxiliar {
	public static boolean mostrarTexto7 = false;
	
	public static void encerrarPrograma() {
		System.out.println("");
		System.out.println("Encerrando o programa.");
		System.exit(0);
	}
	
	@SuppressWarnings("resource")
	public static String escolherEntrada(String type) {
		Scanner s = new Scanner(System.in);
		int operacao;
		String path = new String();
		System.out.println("Escolha um arquivo de entrada: ");
		
		if(type.equals("decipher")) {
			System.out.println("0 - voltar ");
			System.out.println("1 - tarefa 1 ");
			System.out.println("2 - tarefa 2 ");
			System.out.println("3 - tarefa 3 ");
			System.out.println("4 - tarefa 4 ");
			if(mostrarTexto7) {
				System.out.println("7 - tarefa 7 (após cifrar tarefa 5 ou 6) ");
			}
			
		} else {
			System.out.println("0 - voltar ");
			System.out.println("5 - tarefa 5 ");
			System.out.println("6 - tarefa 6 ");
		}
		operacao = s.nextInt();
		
		switch(operacao) {
		case 1:
			path = "inputs/tarefa_1.txt";
			break;
		case 2:
			path = "inputs/tarefa_2.txt";
			break;
		case 3:
			path = "inputs/tarefa_3.txt";
			break;
		case 4:
			path = "inputs/tarefa_4.txt";
			break;
		case 5:
			path = "inputs/tarefa_5.txt";
			break;
		case 6:
			path = "inputs/tarefa_6.txt";
			break;
		case 7:
			path = "inputs/tarefa_7.txt";
			break;
		default:
			path = "voltar";
			break;
		}
		return path;
	}
	
	public static String[] lerEntrada(String arquivoEntrada) throws Exception {
		List<String> linhasArquivo = null;
		String[] entrada = new String[2];
		try {
			Path path = Paths.get(arquivoEntrada);

	        linhasArquivo = Files.readAllLines(path);
			
		} catch (Exception e) {
			return null;
		}
		
		return linhasArquivo.toArray(entrada);
	}
	
	public static String obterInfo(String[] array, String info) {
		String[] linhas;
		String retInfo = new String();
		switch(info) {
		case "tipo":
			linhas = array[0].split(":");
			retInfo = linhas[0].split(" ")[0];
			break;
		case "chave":
			linhas = array[0].split(":");
			retInfo = linhas[1].trim();
			break;
		case "texto":
			linhas = array[1].split(":");
			retInfo = linhas[1].trim().substring(32);
			if(array[1].contains("Plain")) {
				retInfo = linhas[1].trim();
			}
			break;
		}
		
		return retInfo;
	}
	
	public static byte[] obterVetorInicializacao(String[] entrada) {
		String textoCifrado = entrada[1].split(":")[1].trim();
		byte[] array = new byte[16];
		array = Arrays.copyOf(AES.toByteArray(textoCifrado),16);
		return array;
	}
	
	public static void imprimirSaidaCifrar(String tipo, String chave, String texto, byte[] ciphered, String vetorInicializacao) {
		System.out.println("Tipo: " + tipo.toUpperCase());
		System.out.println("Chave: " + chave);
		System.out.println("Vetor incializacao: " + vetorInicializacao);
		System.out.println("Texto decifrado: " + texto);
		System.out.println("Texto cifrado: " + AES.toHexString(ciphered));
		System.out.println("");
	}
	
	public static void imprimirSaidaDecifrar(String tipo, String chave, String texto, byte[] deciphered, String vetorInicializacao) {
		System.out.println("Tipo: " + tipo.toUpperCase());
		System.out.println("Chave: " + chave);
		System.out.println("Vetor incializacao: " + vetorInicializacao);
		System.out.println("Texto cifrado: " + texto);
		System.out.println("Texto decifrado: " + (new String(deciphered)));
		System.out.println("");
	}
	
	public static void gravarTextoCifrado(String modoOperacao, String chave, String vetorInicializacao, String textoCifrado) throws IOException {
		System.out.println("Gerando tarefa 7...");
		
		FileWriter writer = new FileWriter(new File("inputs/tarefa_7.txt"),false);
	    PrintWriter gravarArq = new PrintWriter(writer);
	 
	    gravarArq.printf(String.format("%s key: %s\n", modoOperacao.toUpperCase(), chave));
	    
	    gravarArq.printf(String.format("%s Ciphertext: %s%s", modoOperacao.toUpperCase(), vetorInicializacao, textoCifrado));
	    System.out.println("Gravando arquivo...");
	    writer.close();
	    mostrarTexto7 = true;
	    
	    System.out.println("Geração concluída...\n");
	}
}
