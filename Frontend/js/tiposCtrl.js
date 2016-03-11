var Tipos = function () {
    
    ///*
    // Esta função é usada limpar a tela, voltando os controles para os valores padrão
    ///*
    var limparFormulario = function () {
        $('#id-tipo').val('');
        $('#tipo').val('');
    };

    ///*
    // Esta função é usada para carregar os valores do tipo de incidente nos controles da tela
    // Ele deve requisitar um tipo de incidente usando o valor do id que está no atributo 'id-objeto'
    ///*
    var editarItem = function () {
        var $this = $(this);
        var $id = $this.attr('id-objeto');

        // executar AJAX?        
    };

    ///*
    // Esta função é usada para salvar o tipo de incidente no banco de dados do serviço
    ///*
    var cadastrarItem = function () {
        
        // cria a estrutura de dados que será postada para o serviço
        var item = {
            descricao: $('#tipo').val()
        };

        // faz o teste se há um id definido
        // se houver id, significa que é uma edição então o objeto precisa 
        // ser postado com o mesmo id, para atualizar os valores
        // caso contrário irá criar um novo item
        if ($('#id-tipo').val() != '') {
            item.id = $('#id-tipo').val();
        };

        $.ajax({
            async: true,
            type: "POST",
            data: JSON.stringify(item),
            url: API_URL + '/tipoincidente/v1/tipoincidente?alt=json',
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

        //executar AJAX?
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
    // Esta função é usada para carregar os tipos de incidentes na lista da página (tabela)    
    ///*
    var carregarLista = function () {

        $.ajax({
            async: true,
            type: "GET",
            url: API_URL + '/tipoincidente/v1/tipoincidente/',
            dataType: "JSON",
            processData: true,
            success: function (data) {

                /// hummmm? preenche itens na lista atual

                if (data != null && data.items.length > 0) {

                    $.each(data.items, function (i, item) {
                        var tr = $('<tr/>');
                        tr.append("<td>" + item.descricao + "</td>");

                        var template = "<td>";
                        template += "<div class='btn-group btn-group-xs btn-group-show-label'>";
                        template += "<button type='button' name='botao-editar' id='botao-editar' id-objeto='" + item.id + "' class='btn btn-default'><span class='glyphicon glyphicon glyphicon-pencil' aria-hidden='true'>Editar</span></button>";
                        template += "<button type='button' name='botao-remover' id='botao-remover' id-objeto='" + item.id + "' class='btn btn-danger'><span class='glyphicon glyphicon glyphicon-remove' aria-hidden='true'>Remover</span></button>";
                        template += "</div>";
                        template += "</td>";

                        $('.table-result').append(tr);
                    });

                    /// precisamos fazer algo com os links que foram criados?

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
            Utils.limparLista();
        }
    };
} ();