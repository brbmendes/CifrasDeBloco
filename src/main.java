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
			System.out.println("3 - Sair");
			
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
				//byte[] vetorInicializacao = AES.toByteArray("4e657874205468757273646179206f6e");
				// obtem o tipo de modo de operacao, a chave e o texto SEM o vetor de inicializacao
				String tipo = auxiliar.obterInfo(entrada, "tipo");
				String chave = auxiliar.obterInfo(entrada, "chave");				
				String texto = auxiliar.obterInfo(entrada, "texto");
				
				// Converte o vetor de inicializacao em bytes, e obtem o ivSpec 
				//byte[] iv = AES.toByteArray(vetorInicializacao);
				IvParameterSpec ivParameterSpec = new IvParameterSpec(vetorInicializacao);
				
				// Converte a chave em bytes, e obtem a keySpec
				//SecretKeySpec sKeySpec = AES.getSecretKey(chave);
				byte[] byteChave = AES.toByteArray(chave);
				SecretKeySpec sKeySpec = new SecretKeySpec(byteChave, 0, byteChave.length, "AES");
				
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
					cipher.init(Cipher.ENCRYPT_MODE, sKeySpec, ivParameterSpec);
				}
				
				// Cifra o texto
				byte[] ciphered = cipher.doFinal(AES.toByteArray(texto));				
				
				auxiliar.imprimirSaidaCifrar(tipo, chave, texto, ciphered, AES.toHexString(vetorInicializacao));
				auxiliar.gravarTextoCifrado(tipo, chave, AES.toHexString(vetorInicializacao), AES.toHexString(ciphered));
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
				//SecretKeySpec sKeySpec = AES.getSecretKey(chave);
				byte[] byteChave = AES.toByteArray(chave);
				SecretKeySpec sKeySpec = new SecretKeySpec(byteChave, 0, byteChave.length, "AES");
				
				// Define o modo de operacao e o padding, se necessario
				if(tipo.equals("cbc")) {
					cripto = "AES/CBC/PKCS5Padding";
				}  else {
					cripto = "AES/CTR/NoPadding";
				}
				
				Cipher cipher = Cipher.getInstance(cripto);
				
				// Inicializa o cipher passando a chave e, se necessario, o vetor de inicializacao
				if(tipo.equals("cbc")) {
					cipher.init(Cipher.DECRYPT_MODE, sKeySpec, ivParameterSpec);
				} else {
					cipher.init(Cipher.DECRYPT_MODE, sKeySpec, ivParameterSpec);
				}
				
				// Decifra o texto
				byte[] deciphered = cipher.doFinal(AES.toByteArray(texto));
				
				auxiliar.imprimirSaidaDecifrar(tipo, chave, texto, deciphered, AES.toHexString(vetorInicializacao));
			} else if(operacao == 3) {
				break;
			} else {
				System.out.println("Operacao invalida\n");
			}
		}
		auxiliar.encerrarPrograma();
	}
	
	
}
