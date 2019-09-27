# CifrasDeBloco
Trabalho 2 da disciplina de Segurança de Sistemas - PUCRS 2019-2

Neste trabalho você tem que implementar um sistema para cifrar/decifrar usando a cifra de blocos AES, com os modos de operação CBC e CTR. Em todos os casos o IV de 16 bytes é escolhido de maneira aleatória e anexada na frente do texto cifrado.

Para o modo de operação CBC, usar PKCS5.

Implementar a cifragem e decifragem. Abaixo são dados as chaves para o AES, e o texto cifrado ou texto claro. Sua tarefa é recuperar o texto claro para aqueles que estão cifrados e cifrar o texto claro. Todos os textos estão em hexadecimal.

Os arquivos estão no formato abaixo (desconsidere "Tarefa x"

**Tarefa 1**
CBC key: 140b41b22a29beb4061bda66b6747e14
CBC Ciphertext: 
4ca00ff4c898d61e1edbf1800618fb2828a226d160dad07883d04e008a7897ee2e4b7465d5290d0c0e6c6822236e1daafb94ffe0c5da05d9476be028ad7c1d81


**Tarefa 2**
CBC key: 140b41b22a29beb4061bda66b6747e14
CBC Ciphertext:
5b68629feb8606f9a6667670b75b38a5b4832d0f26e1ab7da33249de7d4afc48e713ac646ace36e872ad5fb8a512428a6e21364b0c374df45503473c5242a253

**Tarefa 3**
CTR key: 36f18357be4dbd77f050515c73fcf9f2
CTR Ciphertext: 
69dda8455c7dd4254bf353b773304eec0ec7702330098ce7f7520d1cbbb20fc388d1b0adb5054dbd7370849dbf0b88d393f252e764f1f5f7ad97ef79d59ce29f5f51eeca32eabedd9afa9329

**Tarefa 4**
CTR key: 36f18357be4dbd77f050515c73fcf9f2
CTR Ciphertext: 
770b80259ec33beb2561358a9f2dc617e46218c0a53cbeca695ae45faa8952aa0e311bde9d4e01726d3184c34451

**Tarefa 5**
CTR key: 36f18357be4dbd77f050515c73fcf9f2
CTR Plaintext: 
5468697320697320612073656e74656e636520746f20626520656e63727970746564207573696e672041455320616e6420435452206d6f64652e

**Tarefa 6**
CBC key: 140b41b22a29beb4061bda66b6747e14
CBC Plaintext:
4e657874205468757273646179206f6e65206f66207468652062657374207465616d7320696e2074686520776f726c642077696c6c2066616365206120626967206368616c6c656e676520696e20746865204c696265727461646f72657320646120416d6572696361204368616d70696f6e736869702e

