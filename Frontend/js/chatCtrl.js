var Chat = function () {

    var template = "<div class='media'><div class='media-body'>[[MENSAGEM]]<br /><small class='text-muted'>[[NOME]]</small><hr /></div></div>";
    var chatHub;
    
    var receberMensagem = function () {

         

    };
    
    var enviarMensagem = function (nome, mensagem) {
        
        chatHub.server.send($('#text-nome').val(), $('#text-mensagem').val());                    
        $('#text-mensagem').val('').focus();  
        
    };
    
    var adicionarEventos = function () {
        
        $('#btn-enviar').click( enviarMensagem ); 
        
    };

    return {
        //Função principal que inicializa o módulo
        inicializar: function () {
            
            chatHub = $.connection.chatHub;
            
            $.connection.hub.url = 'http://chat-site.azurewebsites.net/signalr';
                                        
            $.connection.hub.start()
                .done(function(){ adicionarEventos(); console.log('Estamos conectados! Seu ID=' + $.connection.hub.id); })                    
                .fail(function(){ console.log('Não foi possível conectar!'); });
            
            chatHub.client.broadcastMessage = function (nome, mensagem) {
                                
                // Add the message to the page.
                $('#chat-container').append(template.replace('[[NOME]]', nome).replace('[[MENSAGEM]]', mensagem));
                
            };
                                  
        }, 
        enviarMensagem,
        receberMensagem        
    };
}();