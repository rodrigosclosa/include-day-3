var Incidentes = function () {
    
    var carregarTipos = function() {
      
        $.ajax({
            async: true,
            type: "GET",
            url: API_URL + '/tipoincidente/v1/tipoincidente/',
            dataType: "JSON",
            processData: true,
            success: function (data) {

                var option = $('<option/>');
                        option.attr("value", "");
                        option.html("-- Selecione uma opção --");
                        $('#tipo').append(option);

                if (data != null && data.items.length > 0) {
                        
                    $.each(data.items, function (i, item) {
                        var option = $('<option/>');
                        option.attr("value", item.id);
                        option.html(item.descricao);
                        $('#tipo').append(option);
                    });
                    
                }

            },
            error: function (xhr) {
                alert("Ocorreu um erro ao carregar a lista.");
            }
        });
        
    };
    
    var carregarTimes = function() {
      
        $.ajax({
            async: true,
            type: "GET",
            url: API_URL + '/times/v1/times/',
            dataType: "JSON",
            processData: true,
            success: function (data) {

                var option = $('<option/>');
                        option.attr("value", "");
                        option.html("-- Selecione uma opção --");
                        $('#time').append(option);

                if (data != null && data.items.length > 0) {
                        
                    $.each(data.items, function (i, item) {
                        var option = $('<option/>');
                        option.attr("value", item.id);
                        option.html(item.nome);
                        $('#time').append(option);
                    });
                    
                }

            },
            error: function (xhr) {
                alert("Ocorreu um erro ao carregar a lista.");
            }
        });
        
    };

    var obterCoordenadas = function (cb) {

        var logradouro = $('#logradouro').val();
        var numero = $('#numero').val();
        var cidade = $('#cidade').val();
        var estado = $('#estado').val();

        var address = logradouro + " , " + numero + " , " +  cidade + " , " + estado;

        var geocoder = new google.maps.Geocoder();
        geocoder.geocode({ 'address': address }, function (results, status) {
            if (status === google.maps.GeocoderStatus.OK) {
                // console.log(results[0].geometry.location.lat() + " : " + results[0].geometry.location.lng());
                cb(
                    {
                    lat: results[0].geometry.location.lat(),
                    long: results[0].geometry.location.lng()
                    }
                );                
            } else {
                cb(null);                
            }
        });

    }

    var limparFormulario = function () {
        $('#id-incidente').val('');
        $('#tipo').val('');
        $('#gravidade').val('');
        $('#logradouro').val('');
        $('#numero').val('');
        $('#cidade').val('');
        $('#estado').val('');
        $('#time').val('');
        $('#descricao').val('');
    };

    var editarItem = function () {
        var $this = $(this);
        var $id = $this.attr('id-objeto');

        $.ajax({
            async: true,
            type: "GET",
            url: API_URL + '/incidentes/v1/incidentes/' + $id,
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                $('#id-incidente').val(data.id);
                $('#tipo').val(data.idTipoIncidente);
                $('#gravidade').val(data.gravidade);
                $('#logradouro').val(data.logradouro);
                $('#numero').val(data.numero);
                $('#cidade').val(data.cidade);
                $('#estado').val(data.estado);
                $('#time').val(data.idTime);
                $('#descricao').val(data.descricao);
            },
            error: function (xhr) {
                bootbox.alert(xhr.responseJSON.error.message);
            }
        });
    };

    var cadastrarItem = function () {

        obterCoordenadas(persistirItem);

    };
    
    var persistirItem = function (latlong) {
        
        var item = {
            idTipoIncidente: $('#tipo').val(),
            gravidade: $('#gravidade').val(),
            logradouro: $('#logradouro').val(),
            numero: $('#numero').val(),
            cidade: $('#cidade').val(),
            estado: $('#estado').val(),
            idTime: $('#time').val(),
            descricao: $('#descricao').val(),
            data: new Date().toJSON().slice(0,10),
            localizacao: {
                latitude: latlong.lat.toString(),
                longitude: latlong.long.toString()
            } 
        };
        
        if ($('#id-incidente').val() != '') {
            item.id = $('#id-incidente').val();
        };

        $.ajax({
            async: true,
            type: "POST",
            data: JSON.stringify(item),
            url: API_URL + '/incidentes/v1/incidentes?alt=json',
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                limparFormulario();
                carregarLista();
                
                acionarSirene(1, 0, 1);
                
                bootbox.alert('Cadastrado/Editado com sucesso!');
            },
            error: function (xhr) {
                bootbox.alert(xhr.responseJSON.error.message);
            }
        });
        
    }

    var removerItem = function () {
        var $this = $(this);
        var $id = $this.attr('id-objeto');

        bootbox.confirm("Tem certeza que deseja remover?", function (result) {

            if (result) {
                $.ajax({
                    async: true,
                    type: "DELETE",
                    url: API_URL + '/incidentes/v1/incidentes/' + $id,
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
    
    var acionarSirene= function (luzAmarela, luzVermelha, sirene){
        
        var API_SIRENE = "http://api.iot.ciandt.com/v2/data";
        
        var request = {
            "content" : {
                "sirene": sirene,
                "led_amarelo": luzAmarela,
                "led_vermelho": luzVermelha
            },
            "firmwareVersion" : 1.0
        };
        
        $.ajax({
            async: true,
            type: "POST",
            url: API_SIRENE,
            headers: {
                'id':'19:fe:34:e0:3d:9e',
                'access_token':'rOxsxbXsj5Zm'
            },
            dataType: "JSON",
            contentType: 'application/json',
            data: JSON.stringify(request),
            processData: true,
            success: function (data) {
                console.log(data);
            },
            error: function (xhr) {
                bootbox.alert(xhr.responseJSON.error.message);
            }
        });
        
    }
    
    var obterNomeGravidade = function(gravidade) {
        switch (gravidade) {
            case 1 :
                return "Pouco grave";
                break;
            case 2 :
                return "Grave";
                break;
            case 3 :
                return "Muito grave";
                break;
            default:
                break;
        }
    }

    var carregarLista = function () {

        $.ajax({
            async: true,
            type: "GET",
            url: API_URL + '/incidentes/v1/incidentes/',
            dataType: "JSON",
            processData: true,
            success: function (data) {

                Utils.limparLista();

                if (data != null && data.items.length > 0) {

                    $.each(data.items, function (i, item) {
                        
                        if (item.tipoIncidente == null) return true;
                        if (item.time == null) return true;
                        
                        var tr = $('<tr/>');
                        tr.append("<td>" + item.tipoIncidente.descricao + "</td>");
                        tr.append("<td>" + obterNomeGravidade(item.gravidade) + "</td>");
                        tr.append("<td>" + item.logradouro + ", " + item.numero + "</td>");
                        
                        tr.append("<td>" + item.time.nome + "</td>");
                        
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
            carregarTipos();
            carregarTimes();
        }
    };
} ();