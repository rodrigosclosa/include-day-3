var Times = function () {
    
    ///*
    // Esta função é usada limpar a tela, voltando os controles para os valores padrão
    ///*
    var limparFormulario = function () {
        $('#id-time').val('');
        $('#nome').val('');
        $('#integrantes').val('');
        $('#baseCiandt').val('');
    };

    ///*
    // Esta função é usada para carregar os valores do time nos controles da tela
    // Ele deve requisitar time usando o valor do id que está no atributo 'id-objeto'
    ///*
    var editarItem = function () {
        var $this = $(this);
        var $id = $this.attr('id-objeto');

        $.ajax({
            async: true,
            type: "GET",
            url: API_URL + '/times/v1/times/' + $id,
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                $('#nome').val(data.nome);
                $('#integrantes').val(data.integrantes);
                $('#baseCiandt').val(data.baseCiandt);
                $('#id-time').val(data.id);
            },
            error: function (xhr) {
                bootbox.alert(xhr.responseJSON.error.message);
            }
        });
    };

    ///*
    // Esta função é usada para salvar o time no banco de dados do serviço
    ///*
    var cadastrarItem = function () {

        // cria a estrutura de dados que será postada para o serviço
        var item = {
            nome: $('#nome').val(),
            integrantes: $('#integrantes').val(),
            baseCiandt: $('#baseCiandt').val()
        };

        // faz o teste se há um id definido
        // se houver id, significa que é uma edição então o objeto precisa 
        // ser postado com o mesmo id, para atualizar os valores
        // caso contrário irá criar um novo item
        if ($('#id-time').val() != '') {
            item.id = $('#id-time').val();
        };

        $.ajax({
            async: true,
            type: "POST",
            data: JSON.stringify(item),
            url: API_URL + '/times/v1/times?alt=json',
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                limparFormulario();
                carregarLista();
                bootbox.alert('Cadastrado/Editado com sucesso!');
            },
            error: function (xhr) {
                bootbox.alert(xhr.responseJSON.error.message);
            }
        });

    };

    ///*
    // Esta função é usada para remover o tipo de incidente no banco de dados do serviço
    // Ela precisa do 'id-objeto' que está no atributo do link
    ///*
    var removerItem = function () {
        var $this = $(this);
        var $id = $this.attr('id-objeto');

        // esta função cria um popup de confirmação para o usuário
        // permitindo que ele desista ou confirme a ação de remoção
        bootbox.confirm("Tem certeza que deseja remover?", function (result) {

            // Ao responder (sim ou não) no popup, o resultado é enviado para a variável 'result'
            // result = true se o usuário disse que SIM, confirma a ação
            // result = false se o usuário desistiu e disse que NÃO confirma a ação
            if (result) {
                $.ajax({
                    async: true,
                    type: "DELETE",
                    url: API_URL + '/times/v1/times/' + $id,
                    dataType: "JSON",
                    processData: true,
                    success: function (data) {
                        carregarLista();
                        bootbox.alert('Removido com sucesso!');
                    },
                    error: function (xhr) {
                        bootbox.alert(xhr.responseJSON.error.message);
                    }
                });
            }
        });

    };

    ///*
    // Esta função é usada habilitar os eventos de click na tela
    // ela deve ser executada, assim que a tela estiver pronta para ser utilizada (geralmente no contrutor)      
    ///*
    var adicionarEventos = function () {

        $('.table-result .btn-default').each(function () {
            var $this = $(this);
            $this.click(editarItem);
        });

        $('.table-result .btn-danger').each(function () {
            var $this = $(this);
            $this.click(removerItem);
        });

    };

    ///*
    // Esta função é usada para carregar os times de incidentes na lista da página (tabela)    
    ///*
    var carregarLista = function () {

        $.ajax({
            async: true,
            type: "GET",
            url: API_URL + '/times/v1/times/',
            dataType: "JSON",
            processData: true,
            success: function (data) {

                Utils.limparLista();

                if(data != null && data.items.length > 0){

                    $.each(data.items, function (i, item) {
                        var tr = $('<tr/>');
                        tr.append("<td>" + item.nome + "</td>");
                        tr.append("<td>" + item.integrantes + "</td>");
                        tr.append("<td>" + item.baseCiandt + "</td>");

                        var template = "<td>";
                        template += "<div class='btn-group btn-group-xs btn-group-show-label'>";
                        template += "<button type='button' name='botao-editar' id='botao-editar' id-objeto='" + item.id + "' class='btn btn-default'><span class='glyphicon glyphicon glyphicon-pencil' aria-hidden='true'>Editar</span></button>";
                        template += "<button type='button' name='botao-remover' id='botao-remover' id-objeto='" + item.id + "' class='btn btn-danger'><span class='glyphicon glyphicon glyphicon-remove' aria-hidden='true'>Remover</span></button>";
                        template += "</div>";
                        template += "</td>";

                        tr.append(template);

                        $('.table-result').append(tr);
                    });
                    
                    adicionarEventos();
                    
                }
            },
            error: function (xhr) {
                alert("Ocorreu um erro ao carregar a lista.");
            }
        });

    };

    return {
        //Função principal que inicializa o módulo
        inicializar: function () {
            $('#btn-cadastrar').click(cadastrarItem);
            carregarLista();
        }
    };
} ();