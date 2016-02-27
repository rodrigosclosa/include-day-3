var Times = function () {

    var limparLista = function () {
        $('.table-result').empty();
    };

    var limparFormulario = function () {
        $('#id-time').val('');
        $('#tipo').val('');
    };

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
                $('#tipo').val(data.descricao);
                $('#id-time').val(data.id);
            },
            error: function (xhr) {
                bootbox.alert(xhr.responseJSON.error.message);
            }
        });
    };

    var cadastrarItem = function () {

        var item = {
            descricao: $('#tipo').val()
        };

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

    var removerItem = function () {
        var $this = $(this);
        var $id = $this.attr('id-time');

        bootbox.confirm("Tem certeza que deseja remover?", function (result) {

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

    var carregarLista = function () {

        $.ajax({
            async: true,
            type: "GET",
            url: API_URL + '/times/v1/times/',
            dataType: "JSON",
            processData: true,
            success: function (data) {

                if (data != null) {

                    $.each(data.items, function (i, item) {
                        var tr = $('<tr/>');
                        tr.append("<td>" + item.id + "</td>");
                        tr.append("<td>" + item.nome + "</td>");
                        tr.append("<td>" + item.integrantes + "</td>");
                        tr.append("<td>" + item.cidade + "</td>");

                        var template = "<td>";
                        template += "<div class='btn-group btn-group-xs'>";
                        template += "<button type='button' id-objeto='" + item.id + "' class='btn btn-default'><span class='glyphicon glyphicon glyphicon-pencil' aria-hidden='true'></span></button>";
                        template += "<button type='button' id-objeto='" + item.id + "' class='btn btn-danger'><span class='glyphicon glyphicon glyphicon-remove' aria-hidden='true'></span></button>";
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