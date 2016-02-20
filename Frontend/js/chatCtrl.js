var Chat = function () {

    var template = "<div class='media'><div class='media-body'>[[MENSAGEM]]<small class='text-muted'> - [[NOME]]</small></div></div>";
    
    var chatHub;
    
    var receberMensagem = function (nome, mensagem) {
        
        $('#chat-container').append(template.replace('[[NOME]]', nome).replace('[[MENSAGEM]]', mensagem));

    };
    
    var enviarMensagem = function (nome, mensagem) {
        
        chatHub.server.send($('#text-nome').val(), $('#text-mensagem').val());                    
        $('#text-mensagem').val('').focus();  
        
    };
    
    var adicionarEventos = function () {
        
        $('#btn-enviar').click( enviarMensagem ); 
        
    };

    return {        
        inicializar: function () {
            
            chatHub = $.connection.chatHub;
            
            $.connection.hub.url = 'http://chat-site.azurewebsites.net/signalr';
                                        
            $.connection.hub.start()
                .done(function(){ adicionarEventos(); console.log('Estamos conectados! Seu ID=' + $.connection.hub.id); })                    
                .fail(function(){ console.log('Não foi possível conectar!'); });
            
            chatHub.client.broadcastMessage = receberMensagem;
            
            $('#chat-container').append("Bem vindo! <br /> Informe seu nome e envie mensagens à vontade.");
                                  
        }, 
        enviarMensagem,
        receberMensagem        
    };
    
}();