<?php
    include '../functions.php';
    $pdo = pdo_connect_pgsql();
    $msg = '';
    // Verifica se os dados POST não estão vazios
    if (!empty($_POST)) {
        // Se os dados POST não estiverem vazios, insere um novo registro
        // Configura as variáveis que serão inserid_contatoas. Devemos verificar se as variáveis POST existem e, se não existirem, podemos atribuir um valor padrão a elas.
        // Verifica se a variável POST "nome" existe, se não existir, atribui o valor padrão para vazio, basicamente o mesmo para todas as variáveis
        $nome_do_ativo = isset($_POST['nome_do_ativo']) ? $_POST['nome_do_ativo'] : '';
        $data_compra = isset($_POST['data_compra']) ? $_POST['data_compra'] : '';
        $descricao = isset($_POST['descricao']) ? $_POST['descricao'] : '';
        $status = isset($_POST['status']) ? $_POST['status'] : '';
        $pertence = isset($_POST['pertence']) ? $_POST['pertence'] : '';
        // Insere um novo registro na tabela contacts
        if($nome_do_ativo != '' && $data_compra != '' && $descricao !='' && $status != '' && $pertence !=''){
            try {
                $stmt = $pdo->prepare('INSERT INTO patrimonios (nome_do_ativo, data_compra, descricao, status, pertence) VALUES (?, ?, ?, ?, ?)');
                $stmt->execute([$nome_do_ativo, $data_compra, $descricao, $status, $pertence]);
            } catch (Exception $e) {
                $msg = $e;
            }
        } else {
            
        }
        
    
        // Mensagem de saída
        $msg = 'Cliente cadastrado com Sucesso!';
    }
?>