<?php
    include '../functions.php';
    $pdo = pdo_connect_pgsql();
    $msg = '';
    // Verifica se os dados POST não estão vazios
    if (!empty($_POST)) {
        // Se os dados POST não estiverem vazios, insere um novo registro
        // Configura as variáveis que serão inserid_contatoas. Devemos verificar se as variáveis POST existem e, se não existirem, podemos atribuir um valor padrão a elas.
        // Verifica se a variável POST "nome" existe, se não existir, atribui o valor padrão para vazio, basicamente o mesmo para todas as variáveis
        $nome = isset($_POST['nome']) ? $_POST['nome'] : '';
        $cargo = isset($_POST['cargo']) ? $_POST['cargo'] : '';
        $setor = isset($_POST['setor']) ? $_POST['setor'] : '';
        $email = isset($_POST['email']) ? $_POST['email'] : '';
        // Insere um novo registro na tabela contacts
        if($id_fun != '' && $nome != '' && $cargo !='' && $setor != '' && $email !=''){
            try {
                $stmt = $pdo->prepare('INSERT INTO patrimonios (nome, cargo, setor, email) VALUES (?, ?, ?, ?)');
                $stmt->execute([$nome, $cargo, $setor, $email]);
                
        // Mensagem de saída
        $msg = 'Cliente cadastrado com Sucesso!';
            } catch (Exception $e) {
                $msg = $e;
            }
        } else {
            $msg = 'Falha ao cadastrar';
        }
        

        return $msg;
    }
?>