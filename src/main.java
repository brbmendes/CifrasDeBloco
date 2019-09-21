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
			//System.out.println("4 - Decifrar texto manual");
			System.out.println("5 - Cifra e Decifra texto manual - Op. Mode: CBC");
			System.out.println("6 - Sair");
			
			operacao = s.nextInt();
			if(operacao == 1) {
				// Cifrar
				System.out.println("");
				
				String cripto = "AES";
				// Define o arquivo de entrada e converte para um array
				String caminho = escolherEntrada("cipher");
				if(caminho.equals("voltar")) {
					System.out.println("");
					continue;
				}
				String[] entrada = lerEntrada(caminho);
				
				// Obtem o vetor de inicializacao
				String vetorInicializacao = obterVetorInicializacao(entrada);
				
				// obtem o tipo de modo de operacao, a chave e o texto SEM o vetor de inicializacao
				String tipo = obterInfo(entrada, "tipo");
				String chave = obterInfo(entrada, "chave");				
				String texto = obterInfo(entrada, "texto");
				
				// Converte o vetor de inicialização em bytes, e obtem o ivSpec
				byte[] iv = AES.toByteArray(vetorInicializacao);
				IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
				
				// Converte a chave em bytes, e obtem a keySpec
				SecretKeySpec sKeySpec = AES.getSecretKey(chave);
				//byte[] byteChave = AES.toByteArray(chave);
				//SecretKeySpec sKeySpec = new SecretKeySpec(byteChave, 0, byteChave.length, "AES");
				
				// Define o modo de operação e padding, se necessário
				if(tipo.equals("cbc")) {
					cripto = "AES/CBC/PKCS5Padding";
				} else {
					cripto = "AES/CTR/NoPadding";
				}
				
				Cipher cipher = Cipher.getInstance(cripto);
				
				// Inicializa o cipher passando a chave e, se necessário, o vetor de inicializacao
				if(tipo.equals("cbc")) {
					cipher.init(Cipher.ENCRYPT_MODE, sKeySpec, ivParameterSpec);
				} else {
					cipher.init(Cipher.ENCRYPT_MODE, sKeySpec);
				}
				
				// Cifra o texto
				byte[] ciphered = cipher.doFinal(texto.getBytes("UTF-8"));				
				
				imprimirSaidaCifrar(tipo, chave, texto, ciphered);
			} else if(operacao == 2) {
				// Decifrar
				System.out.println("");
				
				String cripto = "AES";
				
				// Define o arquivo de entrada e converte para um array
				String caminho = escolherEntrada("decipher");
				if(caminho.equals("voltar")) {
					System.out.println("");
					continue;
				}
				String[] entrada = lerEntrada(caminho);
				
				// Obtem o vetor de inicializacao
				String vetorInicializacao = obterVetorInicializacao(entrada);
				
				// obtem o tipo de modo de operacao, a chave e o texto SEM o vetor de inicializacao
				String tipo = obterInfo(entrada, "tipo");
				String chave = obterInfo(entrada, "chave");
				String texto = obterInfo(entrada, "texto");
				
				// Converte o vetor de inicialização em bytes, e obtem o ivSpec
				byte[] iv = AES.toByteArray(vetorInicializacao);
				IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
				
				// Converte a chave em bytes, e obtem a keySpec
				SecretKeySpec sKeySpec = AES.getSecretKey(chave);
				//byte[] byteChave = AES.toByteArray(chave);
				//SecretKeySpec sKeySpec = new SecretKeySpec(byteChave, 0, byteChave.length, "AES");
				
				// Define o modo de operação e o padding, se necessário
				if(tipo.equals("cbc")) {
					cripto = "AES/CBC/PKCS5Padding";
				}  else {
					cripto = "AES/CTR/NoPadding";
				}
				
				Cipher cipher = Cipher.getInstance(cripto);
				
				// Inicializa o cipher passando a chave e, se necessário, o vetor de inicializacao
				if(tipo.equals("cbc")) {
					cipher.init(Cipher.ENCRYPT_MODE, sKeySpec, ivParameterSpec);
				} else {
					cipher.init(Cipher.ENCRYPT_MODE, sKeySpec);
				}
				
				// Decifra o texto
				byte[] deciphered = cipher.doFinal(AES.toByteArray(texto));
				
				imprimirSaidaDecifrar(tipo, chave, texto, deciphered);
			} else if(operacao == 3) {
				System.out.println("");
				System.out.println("Digite a senha:");
				System.out.println("");
				Scanner scan = new Scanner(System.in);
				
				String senha = scan.nextLine();
				
				System.out.println("");
				System.out.println("Digite o texto a ser cifrado:");
				String texto = scan.nextLine();
				
				SecretKeySpec sKeySpec = AES.getSecretKey(senha);
				
				Cipher cipher = Cipher.getInstance("AES");
				
				cipher.init(Cipher.ENCRYPT_MODE, sKeySpec);
				
				byte[] ciphered = cipher.doFinal(texto.getBytes());
				
				imprimirSaidaCifrarManual(senha, texto, ciphered);
				System.out.println("");
			} else if(operacao == 4) {
				System.out.println("Digite a senha:");
				System.out.println("");
				Scanner scan = new Scanner(System.in);
				
				String senha = scan.nextLine();
				
				System.out.println("Digite o texto a ser decifrado:");
				String texto = scan.nextLine();
				
				SecretKeySpec sKeySpec = AES.getSecretKey(senha);
				
				Cipher cipher = Cipher.getInstance("AES");
				
				cipher.init(Cipher.DECRYPT_MODE, sKeySpec);
				
				byte[] deciphered = cipher.doFinal(AES.toByteArray(texto));
				
				imprimirSaidaDecifrarManual(senha, texto, deciphered);
				System.out.println("");
			} else if(operacao == 5) {
				//break;
				System.out.println("");
				System.out.println("Digite a senha:");
				System.out.println("");
				Scanner scan = new Scanner(System.in);
				
				String senha = scan.nextLine();
				SecretKeySpec sKeySpec = AES.getSecretKey(senha);
				
				System.out.println("");
				System.out.println("Digite o texto a ser cifrado:");
				String texto = scan.nextLine();
				
				String vetorInicializacao = "4e657874205468757273646179206f6e";
				byte[] iv = AES.toByteArray(vetorInicializacao);
				IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
				
				
				Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
				
				cipher.init(Cipher.ENCRYPT_MODE, sKeySpec, ivParameterSpec);
				
				byte[] ciphered = cipher.doFinal(texto.getBytes());
				
				imprimirSaidaCifrarManual(senha, texto, ciphered);
				
				System.out.println("Digite o texto a ser decifrado:");
				texto = scan.nextLine();
				
				cipher.init(Cipher.DECRYPT_MODE, sKeySpec, ivParameterSpec);
				
				byte[] deciphered = cipher.doFinal(AES.toByteArray(texto));
				
				imprimirSaidaDecifrarManual(senha, texto, deciphered);
			} else if(operacao == 6) {
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
			System.out.println("0 - voltar ");
			System.out.println("1 - tarefa 1 ");
			System.out.println("2 - tarefa 2 ");
			System.out.println("3 - tarefa 3 ");
			System.out.println("4 - tarefa 4 ");
			//System.out.println("8 - tarefa 8 ");
		} else {
			System.out.println("0 - voltar ");
			System.out.println("5 - tarefa 5 ");
			System.out.println("6 - tarefa 6 ");
			//System.out.println("7 - tarefa 7 ");
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
		case 8:
			path = "inputs/tarefa_8.txt";
			break;
		default:
			path = "voltar";
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
	
	private static void imprimirSaidaCifrar(String tipo, String chave, String texto, byte[] ciphered) {
		System.out.println("Tipo: " + tipo.toUpperCase());
		System.out.println("Chave: " + chave);
		System.out.println("Texto decifrado: " + texto);
		System.out.println("Texto cifrado: " + AES.toHexString(ciphered));
		System.out.println("");
	}
	
	private static void imprimirSaidaDecifrar(String tipo, String chave, String texto, byte[] deciphered) {
		System.out.println("Tipo: " + tipo.toUpperCase());
		System.out.println("Chave: " + chave);
		System.out.println("Texto cifrado: " + texto);
		System.out.println("Texto decifrado: " + (new String(deciphered)));
		System.out.println("");
	}
	
	private static void imprimirSaidaCifrarManual(String senha, String texto, byte[] ciphered) {
		System.out.println("Senha: " + senha);
		System.out.println("Texto decifrado: " + texto);
		System.out.println("Texto cifrado: " + AES.toHexString(ciphered));
		System.out.println("");
	}
	
	private static void imprimirSaidaDecifrarManual(String senha, String texto, byte[] deciphered) {
		System.out.println("Senha: " + senha);
		System.out.println("Texto cifrado: " + texto);
		System.out.println("Texto decifrado: " + (new String(deciphered)));
	}
}
