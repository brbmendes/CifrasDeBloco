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
			System.out.println("Informe a operacao desejada: ");
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
				String caminho = auxiliar.escolherEntrada("cipher");
				if(caminho.equals("voltar")) {
					System.out.println("");
					continue;
				}
				String[] entrada = auxiliar.lerEntrada(caminho);
				
				// Obtem o vetor de inicializacao (IV)
				byte[] vetorInicializacao = auxiliar.obterVetorInicializacao(entrada);
				
				// obtem o tipo de modo de operacao, a chave e o texto SEM o vetor de inicializacao
				String tipo = auxiliar.obterInfo(entrada, "tipo");
				String chave = auxiliar.obterInfo(entrada, "chave");				
				String texto = auxiliar.obterInfo(entrada, "texto");
				
				// Converte o vetor de inicializacao em bytes, e obtem o ivSpec 
				//byte[] iv = AES.toByteArray(vetorInicializacao);
				IvParameterSpec ivParameterSpec = new IvParameterSpec(vetorInicializacao);
				
				// Converte a chave em bytes, e obtem a keySpec
				SecretKeySpec sKeySpec = AES.getSecretKey(chave);
				//byte[] byteChave = AES.toByteArray(chave);
				//SecretKeySpec sKeySpec = new SecretKeySpec(byteChave, 0, byteChave.length, "AES");
				
				// Define o modo de operacao e padding, se necessario
				if(tipo.equals("cbc")) {
					cripto = "AES/CBC/PKCS5Padding";
				} else {
					cripto = "AES/CTR/NoPadding";
				}
				
				Cipher cipher = Cipher.getInstance(cripto);
				
				// Inicializa o cipher passando a chave e, se necessario, o vetor de inicializacao
				if(tipo.equals("cbc")) {
					cipher.init(Cipher.ENCRYPT_MODE, sKeySpec, ivParameterSpec);
				} else {
					cipher.init(Cipher.ENCRYPT_MODE, sKeySpec);
				}
				
				// Cifra o texto
				byte[] ciphered = cipher.doFinal(texto.getBytes("UTF-8"));				
				
				auxiliar.imprimirSaidaCifrar(tipo, chave, texto, ciphered);
			} else if(operacao == 2) {
				// Decifrar
				System.out.println("");
				
				String cripto = "AES";
				
				// Define o arquivo de entrada e converte para um array
				String caminho = auxiliar.escolherEntrada("decipher");
				if(caminho.equals("voltar")) {
					System.out.println("");
					continue;
				}
				String[] entrada = auxiliar.lerEntrada(caminho);
				
				// Obtem o vetor de inicializacao (IV)
				byte[] vetorInicializacao = auxiliar.obterVetorInicializacao(entrada);
				
				// obtem o tipo de modo de operacao, a chave e o texto SEM o vetor de inicializacao
				String tipo = auxiliar.obterInfo(entrada, "tipo");
				String chave = auxiliar.obterInfo(entrada, "chave");
				String texto = auxiliar.obterInfo(entrada, "texto");
				
				// Converte o vetor de inicializacao em bytes, e obtem o ivSpec
				//byte[] iv = AES.toByteArray(vetorInicializacao);
				IvParameterSpec ivParameterSpec = new IvParameterSpec(vetorInicializacao);
				
				// Converte a chave em bytes, e obtem a keySpec
				SecretKeySpec sKeySpec = AES.getSecretKey(chave);

				// Define o modo de operacao e o padding, se necessario
				if(tipo.equals("cbc")) {
					cripto = "AES/CBC/PKCS5Padding";
				}  else {
					cripto = "AES/CTR/NoPadding";
				}
				
				Cipher cipher = Cipher.getInstance(cripto);
				
				// Inicializa o cipher passando a chave e, se necessario, o vetor de inicializacao
				if(tipo.equals("cbc")) {
					cipher.init(Cipher.ENCRYPT_MODE, sKeySpec, ivParameterSpec);
				} else {
					cipher.init(Cipher.ENCRYPT_MODE, sKeySpec);
				}
				
				// Decifra o texto
				byte[] deciphered = cipher.doFinal(AES.toByteArray(texto));
				
				auxiliar.imprimirSaidaDecifrar(tipo, chave, texto, deciphered, AES.toHexString(vetorInicializacao));
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
				
				auxiliar.imprimirSaidaCifrarManual(senha, texto, ciphered);
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
				
				auxiliar.imprimirSaidaDecifrarManual(senha, texto, deciphered);
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
				
				auxiliar.imprimirSaidaCifrarManual(senha, texto, ciphered);
				
				System.out.println("Digite o texto a ser decifrado:");
				texto = scan.nextLine();
				
				cipher.init(Cipher.DECRYPT_MODE, sKeySpec, ivParameterSpec);
				
				byte[] deciphered = cipher.doFinal(AES.toByteArray(texto));
				
				auxiliar.imprimirSaidaDecifrarManual(senha, texto, deciphered);
			} else if(operacao == 6) {
				break;
			} else {
				System.out.println("Operacao invalida\n");
			}
		}
		auxiliar.encerrarPrograma();
	}
	
	
}
