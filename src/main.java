import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class main {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		Scanner s = new Scanner(System.in);
		int operacao;
		
		while(true) {
			System.out.println("Informe a operação desejada: ");
			System.out.println("1 - Cifrar texto entrada");
			System.out.println("2 - Decifrar texto entrada");
			//System.out.println("3 - Cifrar texto manual");
			//System.out.println("4 - Deifrar texto manual");
			System.out.println("5 - Sair");
			
			operacao = s.nextInt();
			if(operacao == 1) {
				// Cifrar
				System.out.println("");
				
				String cripto = "AES";
				// Define o arquivo de entrada e converte para um array
				String caminho = escolherEntrada("cipher");
				String[] entrada = lerEntrada(caminho);
				
				// Obtem o vetor de inicializacao
				String vetorInicializacao = obterVetorInicializacao(entrada);
				byte[] iv = AES.toByteArray(vetorInicializacao);
				IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
				
				// obtem o tipo de modo de operacao, a chave e o texto SEM o vetor de inicializacao
				String tipo = obterInfo(entrada, "tipo");
				String chave = obterInfo(entrada, "chave");
				String texto = obterInfo(entrada, "texto");
				
				SecretKeySpec sKeySpec = AES.getSecretKey(chave);
				
				if(tipo.equals("cbc")) {
					cripto = "AES/CBC/PKCS5Padding";
				} else {
					cripto = "AES/CTR/NoPadding";
				}
				Cipher cipher = Cipher.getInstance(cripto);
				
				cipher.init(Cipher.ENCRYPT_MODE, sKeySpec, ivParameterSpec);
				
				byte[] ciphered = cipher.doFinal(texto.getBytes());
				
				System.out.println("Tipo: " + tipo.toUpperCase());
				System.out.println("Chave: " + chave);
				System.out.println("Texto decifrado: " + texto);
				System.out.println("Texto cifrado: " + AES.toHexString(ciphered));
				System.out.println("");
			} else if(operacao == 2) {
				// Decifrar
				System.out.println("");
				
				String cripto = "AES";
				
				// Define o arquivo de entrada e converte para um array
				String caminho = escolherEntrada("decipher");
				String[] entrada = lerEntrada(caminho);
				
				// Obtem o vetor de inicializacao
				String vetorInicializacao = obterVetorInicializacao(entrada);
				byte[] iv = AES.toByteArray(vetorInicializacao);
				IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
				
				// obtem o tipo de modo de operacao, a chave e o texto SEM o vetor de inicializacao
				String tipo = obterInfo(entrada, "tipo");
				String chave = obterInfo(entrada, "chave");
				String texto = obterInfo(entrada, "texto");
				
				SecretKeySpec sKeySpec = AES.getSecretKey(chave);
				
				if(tipo.equals("cbc")) {
					cripto = "AES/CBC/PKCS5Padding";
				}
				Cipher cipher = Cipher.getInstance(cripto);
				
				cipher.init(Cipher.DECRYPT_MODE, sKeySpec, ivParameterSpec);
				
				byte[] deciphered = cipher.doFinal(AES.toByteArray(texto));
				
				System.out.println("Tipo: " + tipo.toUpperCase());
				System.out.println("Chave: " + chave);
				System.out.println("Texto cifrado: " + texto);
				System.out.println("Texto decifrado: " + (new String(deciphered)));
				System.out.println("");
			} else if(operacao == 3) {
				System.out.println("");
				System.out.println("Digite a senha:");
				System.out.println("");
				Scanner scan = new Scanner(System.in);
				
				String temp = scan.nextLine();
				
				System.out.println("");
				System.out.println("Digite o texto a ser cifrado:");
				String temp2 = scan.nextLine();
				
				SecretKeySpec sKeySpec = AES.getSecretKey(temp);
				
				Cipher cipher = Cipher.getInstance("AES");
				
				cipher.init(Cipher.ENCRYPT_MODE, sKeySpec);
				
				byte[] ciphered = cipher.doFinal(temp2.getBytes());
				
				System.out.println("Senha: " + temp);
				System.out.println("Texto decifrado: " + temp2);
				System.out.println("Texto cifrado: " + AES.toHexString(ciphered));
				System.out.println("");
			} else if(operacao == 4) {
				System.out.println("Digite a senha:");
				System.out.println("");
				Scanner scan = new Scanner(System.in);
				
				String temp = scan.nextLine();
				
				System.out.println("Digite o texto a ser decifrado:");
				String temp2 = scan.nextLine();
				
				SecretKeySpec sKeySpec = AES.getSecretKey(temp);
				
				Cipher cipher = Cipher.getInstance("AES");
				
				cipher.init(Cipher.DECRYPT_MODE, sKeySpec);
				
				byte[] deciphered = cipher.doFinal(AES.toByteArray(temp2));
				
				System.out.println("");
				System.out.println("Senha: " + temp);
				System.out.println("Texto cifrado: " + temp2);
				System.out.println("Texto decifrado: " + (new String(deciphered)));
				System.out.println("");
			} else if(operacao == 5) {
				break;
			} else {
				System.out.println("Operação inválida\n");
			}
		}
		EncerrarPrograma();
	}
	
	private static void EncerrarPrograma() {
		System.out.println("");
		System.out.println("Encerrando o programa.");
		System.exit(0);
	
	}
	
	@SuppressWarnings("resource")
	private static String escolherEntrada(String type) {
		Scanner s = new Scanner(System.in);
		int operacao;
		String path = new String();
		System.out.println("Escolha um arquivo de entrada: ");
		
		if(type.equals("decipher")) {
			System.out.println("1 - tarefa 1 ");
			System.out.println("2 - tarefa 2 ");
			System.out.println("3 - tarefa 3 ");
			System.out.println("4 - tarefa 4 ");
		} else {
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
		}
		return path;
	}
	
	private static String[] lerEntrada(String arquivoEntrada) throws Exception {
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
	
	private static String obterInfo(String[] array, String info) {
		String[] linhas;
		String retInfo = new String();
		switch(info) {
		case "tipo":
			linhas = array[0].split(" ");
			retInfo = linhas[0].split("_")[0];
			break;
		case "chave":
			linhas = array[0].split(" ");
			retInfo = linhas[1];
			break;
		case "texto":
			linhas = array[1].split(" ");
			retInfo = linhas[1].substring(32);
			break;
		}
		
		return retInfo;
	}
	
	private static String obterVetorInicializacao(String[] entrada) {
		String textoCifrado = entrada[1].split(" ")[1];
		return textoCifrado.substring(0,32);
	}
}
