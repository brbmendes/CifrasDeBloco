import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class main {
	public static final String CIPHER = "cipher";
	public static final String DECIPHER = "decipher";
	
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
			if(operacao == 3) {
				break;
			} else if(operacao != 1 && operacao != 2) {
				System.out.println("Operacao invalida\n");
			} else {
				System.out.println("");
				
				String caminho = "";
				
				//Define o arquivo de entrada
				if(operacao == 1) { // cifrar
					caminho = auxiliar.escolherEntrada(CIPHER);
				} else {
					caminho = auxiliar.escolherEntrada(DECIPHER);
				}

				if(caminho.equals("voltar")) {
					System.out.println("");
					continue;
				}
				// Converte para um array
				String[] entrada = auxiliar.lerEntrada(caminho);
				
				// Obtem o vetor de inicializacao (IV)
				byte[] vetorInicializacao = auxiliar.obterVetorInicializacao(entrada);
				
				// obtem o tipo de modo de operacao, a chave e o texto SEM o vetor de inicializacao
				String tipo = auxiliar.obterInfo(entrada, "tipo");
				String chave = auxiliar.obterInfo(entrada, "chave");				
				String texto = auxiliar.obterInfo(entrada, "texto");
				
				// Converte o vetor de inicializacao em bytes, e obtem o ivSpec 
				IvParameterSpec ivParameterSpec = new IvParameterSpec(vetorInicializacao);
				
				// Converte a chave em bytes, e obtem a keySpec
				byte[] byteChave = AES.toByteArray(chave);
				SecretKeySpec sKeySpec = new SecretKeySpec(byteChave, 0, byteChave.length, "AES");
				
				String cripto = "AES";
				// Define o modo de operacao e padding
				if(tipo.equals("CBC")) {
					cripto = "AES/CBC/PKCS5Padding";
				} else {
					cripto = "AES/CTR/NoPadding";
				}
				
				Cipher cipher = Cipher.getInstance(cripto);
				
				// Inicializa o cipher passando a chave e o vetor de inicializacao
				if(operacao == 1) { // cifrar
					cipher.init(Cipher.ENCRYPT_MODE, sKeySpec, ivParameterSpec);	
				} else {
					cipher.init(Cipher.DECRYPT_MODE, sKeySpec, ivParameterSpec);
				}
				
				
				// Cifra o texto ou decifra o texto, de acordo com a opcao
				if(operacao == 1) { // cifrar
					// cifra o texto
					byte[] ciphered = cipher.doFinal(AES.toByteArray(texto));
					
					// imprime saida do texto cifrado
					auxiliar.imprimirSaidaCifrar(tipo, chave, texto, ciphered, AES.toHexString(vetorInicializacao));
					
					// grava tarefa_7 para ser decifrada
					auxiliar.gravarTextoCifrado(tipo, chave, AES.toHexString(vetorInicializacao), AES.toHexString(ciphered));
				} else {
					// decifra o texto
					byte[] deciphered = cipher.doFinal(AES.toByteArray(texto));
					
					// imprime saida do texto decifrado
					auxiliar.imprimirSaidaDecifrar(tipo, chave, texto, deciphered, AES.toHexString(vetorInicializacao));
				}
			}
		}
		auxiliar.encerrarPrograma();
	}
	
	
}
