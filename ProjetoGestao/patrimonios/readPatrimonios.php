<?php
    include '../functions.php';

    // Conecta ao banco
    $pdo = pdo_connect_pgsql();
    // Obter a página via solicitação GET (parâmetro URL: page), se não existir, defina a página como 1 por padrão
    $page = isset($_GET['page']) && is_numeric($_GET['page']) ? (int)$_GET['page'] : 1;
    // Número de registros para mostrar em cada página
    $records_per_page = 8;

    // Preparar a instrução SQL e obter registros da tabela contacts, LIMIT irá determinar a página
    $stmt = $pdo->prepare('SELECT * FROM patrimonios ORDER BY id_patrimonio OFFSET :offset LIMIT :limit');
    $stmt->bindValue(':offset', ($page - 1) * $records_per_page, PDO::PARAM_INT);
    $stmt->bindValue(':limit', $records_per_page, PDO::PARAM_INT);
    $stmt->execute();
    // Buscar os registros para exibi-los em nosso modelo.
    $patrimonios = $stmt->fetchAll(PDO::FETCH_ASSOC);

    // Obter o número total de contatos, isso é para determinar se deve haver um botão de próxima e anterior
    $nPatrimonios = $pdo->query('SELECT COUNT(*) FROM patrimonio')->fetchColumn();

?>