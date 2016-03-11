var Incidentes = function () {
    
    ///*
    // Esta função é usada para carregar os tipos de incidente em uma caixa de seleção (combobox)
    // Ele deve requisitar os tipos e adicionar ao elemento $('#tipo')
    ///*
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
    
    ///*
    // Esta função é usada para carregar os times em uma caixa de seleção (combobox)
    // Ele deve requisitar os times e adicionar ao elemento $('#time')
    ///*
    var carregarTimes = function() {
      
       /// uai?
        
    };

    ///*
    // Esta função é usada para carregar os tipos de incidente em uma caixa de seleção (combobox)
    // Ele deve requisitar os tipos e adicionar ao elemento $('#tipo')
    ///*
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

    ///*
    // Esta função é usada limpar a tela, voltando os controles para os valores padrão
    ///*
    var limparFormulario = function () {
        $('#id-incidente').val('');
        //limpar algo mais?
    };

    ///*
    // Esta função é usada para carregar os valores do incidente nos controles da tela
    // Ele deve requisitar um incidente usando o valor do id que está no atributo 'id-objeto'
    ///*
    var editarItem = function () {
        
        // neste caso $this é o link que foi clicado
        var $this = $(this);
        
        // recupera o valor do id-objeto
        var $id = $this.attr('id-objeto');

        $.ajax({
            async: true,
            type: "GET",
            url: API_URL + '/incidentes/v1/incidentes/' + $id,
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                
                // quando o serviço recupera com sucesso um objeto incidente
                // o valor da variável 'data' virá com os dados do objeto
                // em seguida usamos os valores para aprentar nos controles da tela                
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

    ///*
    // Esta função é usada para salvar o incidente no banco de dados do serviço
    // Ele deve requisitar o serviço de persistência, porém, precisamos antes encontrar as coordenadas
    // a partir do endereço. Para isso, chamamos a função obterCoordenadas e passamos a função de callback
    // que será chamada assim que a função obterCoordenadas retornar o resultado
    ///*
    var cadastrarItem = function () {

        obterCoordenadas(persistirItem);

    };
    
    ///*
    // Esta função é usada para salvar o incidente no banco de dados do serviço
    // Ela já recebe os valores das coordenadas que vieram da função obterCoordenadas (chamada pela cadastrarItem)
    ///*
    var persistirItem = function (latlong) {
        
        /// criação do objeto que deve ser persistido no serviço
        /// esta estrutura de dados JSON recebe os valores dos controles na tela        
        var item = {
            idTipoIncidente:'',
            gravidade: '',
            logradouro: '',
            numero: '',
            cidade: '', 
            estado: '',
            idTime: '',
            descricao: '',
            data: new Date().toJSON().slice(0,10),
            localizacao: {
                latitude: '0', // uai?!
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

    ///*
    // Esta função é usada para remover o incidente no banco de dados do serviço
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
            
                /// ajax deleção
                
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
    // Esta função é usada para acionar a sirene
    // Ela precisa dos parametros luzAmarela, luzVermelha, sirene e a base (id da sirene)
    // cada um deles é representado por 1 ou 0
    // onde 1 seguinifica que queremos acionar a função e 0 que não queremos (para cada componente, luz ou som)
    ///*
    var acionarSirene = function (luzAmarela, luzVermelha, sirene, base){
                       
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
            url: API_SIRENE_URL,
            headers: {
                'id': base,
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
    
    ///*
    // Esta função converte os valores da gravidade em texto
    ///*
    var obterNomeGravidade = function(gravidade) {
        switch (gravidade) {
            case 1 :
                return "Pouco grave";                
            case 2 :
                return "Grave";                
            case 3 :
                return "Muito grave";                
            default:
                break;
        }
    }

    ///*
    // Esta função é usada para carregar os incidentes na lista da página (tabela)    
    ///*
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
                        tr.append("<td>" + item.gravidade + "</td>");
                        tr.append("<td>" + item.logradouro + ", " + item.numero + "</td>");                        
                        tr.append("<td>" + item.time.nome + "</td>");
                        
                        var template = "<td>";
                        template += "<div class='btn-group btn-group-xs btn-group-show-label'>";
                        template += "<button type='button' name='botao-editar' id='botao-editar' id-objeto='" + item.id + "' class='btn btn-default'><span class='glyphicon glyphicon glyphicon-pencil' aria-hidden='true'>Editar</span></button>";
                        template += "<button type='button' name='botao-remover' id='botao-remover' id-objeto='" + item.id + "' class='btn btn-danger'><span class='glyphicon glyphicon glyphicon-remove' aria-hidden='true'>Remover</span></button>";
                        template += "</div>";
                        template += "</td>";

                        tr.append(template);

                        /// linha pronta? e agora? cade a tabela?

                    });
                    
                    // a função de adicionar eventos precisa ser chamada após a lista ser preenchida
                    // pois os links que foram criados precisam agora receber o comportamento de click
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
        }
    };
} ();