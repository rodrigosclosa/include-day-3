var Usuarios = function () {
    
    var limparLista = function () {
        $('.table-result').empty();
    };

    var editarItem = function () {
        var $this = $(this);
        console.log($this.attr('id-objeto'));
        // TODO
    };

    var cadastrarItem = function () {

        var item = {
            cidade: $('cidade').val(),
            email: $('email').val(),
            estado: $('estado').val(),            
            logradouro: $('logradouro').val(),
            numero: $('numero').val(),
            nome: $('nome').val(),
            localizacao: {
                "latitude": -19.8145989,
                "longitude": 44.0075468
            }
        } 

        $.ajax({
            async: true,
            type: "POST",
            data: JSON.stringify(item),
            url: API_URL + '/usuario/v1/usuarios?alt=json',
            dataType: "json",
            contentType: "application/json; charset=utf-8",            
            success: function (data) {
                carregarLista();
            },
            error: function (xhr) {
                alert("Ocorreu um erro ao cadastrar item.");
            }
        });

    };


    var removerItem = function () {
        var $this = $(this);
        var $id = $this.attr('id-objeto');

        bootbox.confirm("Tem certeza que deseja remover?", function (result) {

            $.ajax({
                async: true,
                type: "DELETE",
                url: API_URL + '/usuario/v1/usuarios/' + $id,
                dataType: "JSON",
                processData: true,
                success: function (data) {
                    carregarLista();
                },
                error: function (xhr) {
                    alert("Ocorreu um erro ao remover item.");
                }
            });

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
            url: API_URL + '/usuario/v1/usuarios',
            dataType: "JSON",
            processData: true,
            success: function (data) {

                limparLista();

                $.each(data.items, function (i, item) {
                    var tr = $('<tr/>');
                    tr.append("<td>" + item.id + "</td>");
                    tr.append("<td>" + item.nome + "</td>");
                    tr.append("<td>" + item.email + "</td>");
                    tr.append("<td>" + item.logradouro + "</td>");

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
}();