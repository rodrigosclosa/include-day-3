var Index = function () {

    var carregarLista = function () {

        $.ajax({
            async: true,
            type: "GET",
            url: API_URL + '/incidentes/v1/incidentes/',
            dataType: "JSON",
            processData: true,
            success: function (data) {

                if(data != null && data.items.length > 0){

                    $.each(data.items, function (i, item) {
                        
                        var template = "<div class='col-sm-4 col-lg-4 col-md-4'>";
                        template += ("<div class='thumbnail'>");
                        template += ("<img src='https://maps.googleapis.com/maps/api/staticmap?center=" + item.localizacao.latitude +","+ item.localizacao.longitude + "&zoom=16&size=320x150&key=AIzaSyCOfscKjGzCub_QJrbXTV8PWS2TP9ayPs4' alt=''>")
                        template += ("<div class='caption'>");
                        template += ("<h4><a href='#'>" + item.tipoIncidente.descricao + "</a></h4>");
                        template += ("<p>" + item.descricao + "</p>");
                        template += ("</div>");
                        template += ("<div class='ratings'>");
                        template += ("<p class='pull-right'>"+ item.cidade + " - " + item.estado + "</p>");
                        template += ("<p>");
                        
                        if (item.gravidade == 1) {
                            template += ("<span class='glyphicon glyphicon-star'></span>");
                            template += ("<span class='glyphicon glyphicon-star-empty'></span>");
                            template += ("<span class='glyphicon glyphicon-star-empty'></span>");
                        } else if (item.gravidade == 2){
                            template += ("<span class='glyphicon glyphicon-star'></span>");
                            template += ("<span class='glyphicon glyphicon-star'></span>");
                            template += ("<span class='glyphicon glyphicon-star-empty'></span>");
                        } else {
                            template += ("<span class='glyphicon glyphicon-star'></span>");
                            template += ("<span class='glyphicon glyphicon-star'></span>");
                            template += ("<span class='glyphicon glyphicon-star'></span>");
                        }
                        
                        template += ("</p>");
                        template += ("</div>");
                        template += ("</div>");
                        template += ("</div>");

                        $('#incidentes-result').append(template);
                    });
                    
                    
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
            carregarLista();
        }
    };
} ();