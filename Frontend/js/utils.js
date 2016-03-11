var Utils = function () {
    
    var construirMenu = function() {
        
        var template = "<a href='[URL]' [CLASS] accesskey='[ACCESSKEY]'>[NOME]</a>";
       
        $.each(MENU.links, function (i, item) {
            var link = $(template.replace("[URL]", item.url).replace("[NOME]", item.nome).replace("[CLASS]", "class='list-group-item'").replace("[ACCESSKEY]", item.accesskey));
            $('#menu-lateral').append(link);
        });
        
        $.each(MENU.links, function (i, item) {            
            var link = template.replace("[URL]", item.url).replace("[NOME]", item.nome).replace("[CLASS]", "").replace("[ACCESSKEY]", item.accesskey);
            $('#menu-topo').append("<li>" + link + "</li>");
        });
        
    }
    
    var limparLista = function () {
        $('.table-result').empty();
    };

    return {
        inicializar: function () {
            construirMenu();
        },        
        limparLista: limparLista
    };
} ();